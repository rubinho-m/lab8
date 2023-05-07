package app.lab8.server.network;

import app.lab8.common.dataStructures.ParsedString;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.networkStructures.Response;
import app.lab8.common.structureClasses.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import app.lab8.server.collectionManagement.CommandExecutor;
import app.lab8.server.databaseManagement.DatabaseHandler;
import app.lab8.server.databaseManagement.DatabaseParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Reader {
    private ServerSocket serverSocket;
    private CommandExecutor commandExecutor;
    private volatile DatabaseParser dbParser;
    private volatile DatabaseHandler dbHandler;


    private Set<String> serverCommands = new HashSet<>() {{
        add("save");
        add("exit");
    }};
    private static final Logger logger = LogManager.getLogger(Reader.class);

    public Reader(ServerSocket serverSocket, CommandExecutor commandExecutor, DatabaseParser dbParser, DatabaseHandler dbHandler) {
        this.serverSocket = serverSocket;
        this.commandExecutor = commandExecutor;
        this.dbParser = dbParser;
        this.dbHandler = dbHandler;
    }

    public void read() throws Exception {
//        serverSocket.setSoTimeout(1000);
        ExecutorService executorService = Executors.newCachedThreadPool();


        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
//                System.out.println(clientSocket.getPort() + " " + clientSocket.getLocalPort());

                logger.info("GOT CLIENT SOCKET");
                logger.info("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                logger.info("READY TO READ");
                Handler handler = new Handler(commandExecutor);

                Writer writer = new Writer();

                executorService.submit(() -> {
                    try (InputStream inputStream = clientSocket.getInputStream();
                         OutputStream outputStream = clientSocket.getOutputStream();
                         ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {

                        while (true) {
                            Request request = (Request) objectInputStream.readObject();
                            ArrayList<String> commandWithArguments = request.getCommandWithArguments();
                            final String header = "!<>!";

                            if (commandWithArguments.get(0).equals("reg") | commandWithArguments.get(0).equals("auth")) {
                                ArrayList<String> userData = request.getUserData();
                                Response response = null;
                                if (commandWithArguments.get(0).equals("reg")) {
                                    int condition = dbHandler.register(userData.get(0), userData.get(1));
                                    response = new Response(header + " " + condition);
                                } else {
                                    int condition = dbHandler.auth(userData.get(0), userData.get(1));
                                    response = new Response(header + " " + condition);
                                }
                                System.out.println(response);

                                writer.write(response, outputStream);
                            } else {
                                Ticket ticket = (Ticket) request.getTicket();
                                ArrayList<String> userData = request.getUserData();
                                int condition = dbHandler.auth(userData.get(0), userData.get(1));
                                if (condition != 2) {
                                    Response response = new Response("Неверный пароль");
                                    writer.write(response, outputStream);
                                } else {
                                    ParsedString<ArrayList<String>, Ticket> parsedString = new ParsedString<>(commandWithArguments, ticket);
                                    logger.info("REQUEST HAS BEEN PARSED");
//                                commandExecutor.setUser(userData.get(0));


                                    handler.handleCommand(parsedString,  outputStream, false, userData.get(0));
                                }


                            }
                        }


                    } catch (Exception e) {
                        logger.info("Client disconnected");
                        try {
                            clientSocket.close();
                            logger.info("CLIENT CONNECTION CLOSED");
                        } catch (IOException ex) {
                            logger.error("Failed to close client socket: " + ex.getMessage());
                        }
                    }

                });




            } catch (Exception e) {
                System.out.println(e.getMessage());

            }


        }
    }


}
