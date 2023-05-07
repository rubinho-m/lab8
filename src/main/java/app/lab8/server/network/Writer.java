package app.lab8.server.network;

import app.lab8.common.networkStructures.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Writer {
    private int BUFFER_SIZE = 1024 * 1024;
    private static final Logger logger = LogManager.getLogger(Writer.class);



    public void write(Response response, OutputStream outputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] newArray = byteArrayOutputStream.toByteArray();

//        ObjectOutputStream serverOutput = new ObjectOutputStream(outputStream);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            try {
                outputStream.write(newArray);
                outputStream.flush();

//                serverOutput.writeObject(response);
//                serverOutput.flush();
                logger.info("RESPONSE HAS BEEN SENT");
//                serverOutput.close();
//                outputStream.close();

                objectOutputStream.close();
                byteArrayOutputStream.close();
                outputStream.close();
            } catch (IOException e) {
                logger.error("Failed to send response: " + e.getMessage());
            }
        });

    }
}
