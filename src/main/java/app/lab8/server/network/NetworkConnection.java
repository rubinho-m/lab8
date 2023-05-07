package app.lab8.server.network;

import app.lab8.common.commandParsing.CommandParser;
import app.lab8.common.dataStructures.ParsedString;
import app.lab8.common.exceptions.NoCommandException;
import app.lab8.common.exceptions.WrongCommandFormat;
import app.lab8.common.networkStructures.Request;
import app.lab8.common.networkStructures.Response;
import app.lab8.common.structureClasses.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import app.lab8.server.collectionManagement.CollectionManager;
import app.lab8.server.collectionManagement.CommandExecutor;
import app.lab8.server.databaseManagement.DatabaseHandler;
import app.lab8.server.databaseManagement.DatabaseParser;

import java.io.*;;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkConnection {
    private final int BUFFER_SIZE = 1024 * 1024;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private Set<String> serverCommands = new HashSet<>() {{
        add("exit");
    }};
    private int port;
    private CollectionManager collectionManager;
    private CommandExecutor commandExecutor;
    private Request request;
    private Response response;
    private DatabaseParser dbParser;
    private DatabaseHandler dbHandler;
    private static final Logger logger = LogManager.getLogger(NetworkConnection.class);

    public NetworkConnection(int port, CollectionManager collectionManager, CommandExecutor commandExecutor, DatabaseParser dbParser, DatabaseHandler dbHandler) throws IOException {
        this.port = port;
        this.collectionManager = collectionManager;
        this.commandExecutor = commandExecutor;
        this.dbHandler = dbHandler;
        this.dbParser = dbParser;

    }

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("SERVER STARTED ON PORT " + port);
        System.out.println("SERVER STARTED ON PORT " + port);
        System.out.println("LOGS IN 'logs' DIRECTORY");
        System.out.println("YOU CAN SAVE COLLECTION AND EXIT SERVER");
        ExecutorService executorService = Executors.newCachedThreadPool();
        Scanner scanner = new Scanner(System.in);
        CommandParser commandParser = new CommandParser();
        Handler handler = new Handler(commandExecutor);

        executorService.submit(() -> {
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            while (true) {
                try {
                    ParsedString<ArrayList<String>, Ticket> parsedString = commandParser.readCommand(scanner, true, true);
                    if (serverCommands.contains(parsedString.getArray().get(0))) {
                        handler.handleCommand(parsedString, null, false, null);
                    } else {
                        logger.info("NOT SERVER COMMAND");
                    }
                } catch (NoSuchElementException e) {
                    System.exit(0);
                } catch (NoCommandException | WrongCommandFormat ignored) {
                    logger.error("WRONG COMMAND HAS BEEN INPUT");
                } catch (Exception e) {
                    logger.error("ERROR WITH HANDLING COMMAND");
                }
            }
        });
        Reader reader = new Reader(serverSocket, commandExecutor, dbParser, dbHandler);
        reader.read();


    }


}
