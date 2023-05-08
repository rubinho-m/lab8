package app.lab8.server.network;

import  app.lab8.common.dataStructures.ParsedString;
import  app.lab8.common.networkStructures.Response;
import  app.lab8.common.structureClasses.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import  app.lab8.server.collectionManagement.CommandExecutor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Handler {
    CommandExecutor commandExecutor;
    //    OutputStream outputStream;
//    private boolean isServerCommand;
//    private String user;
    private List<Future> futureList = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(Handler.class);

    public Handler(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
//        this.outputStream = outputStream;
//        this.isServerCommand = isServerCommand;
//        this.user = user;
    }

//    public Handler(CommandExecutor commandExecutor, boolean isServerCommand) {
//        this.commandExecutor = commandExecutor;
//        this.isServerCommand = isServerCommand;
//    }

    public void handleCommand(ParsedString<ArrayList<String>, Ticket> parsedString, OutputStream outputStream, boolean isServerCommand, String user) throws Exception {
        Writer writer = new Writer();
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Response> futureResponse = executor.submit(() -> commandExecutor.execute(parsedString, user));
        futureList.add(futureResponse);
        logger.info("COMMAND HAS BEEN EXECUTED");
        if (parsedString.getArray().get(0).equals("save")) {
            logger.info("SAVED COLLECTION");
        }
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Set<Future> deleteFuture = new HashSet<>();
                    for (Future nowResponse : futureList) {

                        if (nowResponse.isDone()) {
                            deleteFuture.add(nowResponse);
                            Response response = (Response) nowResponse.get();
                            writer.write(response, outputStream);
                        }
                    }
                    for (Future delFuture: deleteFuture){
                        futureList.remove(delFuture);
                    }
                }
            } catch (IOException | InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
            }
        });
        thread.start();

    }
}
