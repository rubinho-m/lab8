/**
 * The ShowCommand class implements the execute method of the Command interface
 * to show the item in collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package app.lab8.common.commands;

import app.lab8.common.networkStructures.Response;
import app.lab8.server.collectionManagement.CollectionManager;
import app.lab8.common.exceptions.EmptyCollectionException;

public class ShowCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    private int BUFFER_SIZE = 512 * 512;
    private StringBuilder outputCollection;
    public ShowCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String user) throws EmptyCollectionException {
        if (getCollectionManager().getCollection().size() == 0){
            output = new StringBuilder();
            output.append("Collection is empty, please add ticket");
        } else {
            output = getCollectionManager().printCollection();
            byte[] outputBytes = output.toString().getBytes();
            int n = BUFFER_SIZE;
            int step = 0;
            while (outputBytes.length > BUFFER_SIZE){
                step++;
                n = n / 2;
                output = getCollectionManager().printCollection(n, step);
                outputBytes = output.toString().getBytes();
            }
        }
    }

    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
