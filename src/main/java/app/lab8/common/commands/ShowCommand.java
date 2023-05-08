/**
 * The ShowCommand class implements the execute method of the Command interface
 * to show the item in collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package app.lab8.common.commands;

import app.lab8.common.networkStructures.Response;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.server.collectionManagement.CollectionManager;
import app.lab8.common.exceptions.EmptyCollectionException;

import java.util.Set;

public class ShowCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    private int BUFFER_SIZE = 512 * 512;
    private StringBuilder outputCollection;
    private Set<Ticket> tickets;
    public ShowCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String user) throws EmptyCollectionException {
        output = new StringBuilder();
        if (getCollectionManager().getCollection().size() == 0){
            output.append("Collection is empty, please add ticket");
        } else {
            output.append("show");
            tickets = getCollectionManager().getCollection();
        }
    }

    @Override
    public Response getCommandResponse() {
        if (output.toString().equals("show")){
            Response response = new Response(output.toString());
            response.setTickets(tickets);
            return response;
        }
        return new Response(output.toString());
    }
}
