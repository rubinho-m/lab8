/**
 * The UpdateCommand class implements the execute method of the Command interface
 * to update the element in collection according to its id.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package app.lab8.common.commands;

import app.lab8.common.exceptions.EmptyCollectionException;
import app.lab8.common.networkStructures.Response;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.server.collectionManagement.CollectionManager;
import app.lab8.server.databaseManagement.DatabaseHandler;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UpdateCommand extends CommandTemplate implements CommandWithResponse{
    private String output = null;
    public UpdateCommand(CollectionManager collectionManager, DatabaseHandler dbHandler) {
        super(collectionManager, dbHandler);
    }

    @Override
    public void execute(String user) throws EmptyCollectionException, SQLException {
        CollectionManager collectionManager = getCollectionManager();
        Set<Ticket> tickets = collectionManager.getCollection();
        if (tickets.size() == 0){
            output = "Collection is empty";
        }
        Ticket removeTicket = null;
        for (Ticket ticketToUpdate: tickets){
            if (ticketToUpdate.getId() == Integer.parseInt(getArg())){
                removeTicket = ticketToUpdate;
                break;
            }
        }

        Ticket newTicket = getTicket();
        System.out.println(newTicket);
        if  (removeTicket != null)
//        getDbHandler().updateTicket(newTicket, (int) newTicket.getId());
        {
            int id = (int) newTicket.getId();

            int condition = getDbHandler().removeTicket(user, (int) newTicket.getId());

            if (condition == 0) {
                collectionManager.getCollection().remove(removeTicket);
                getDbHandler().addTicketWithId(newTicket, id);
                collectionManager.addToCollection(newTicket);

            } else {
                output = "Нет прав";
                System.out.println(output);
            }
        } else {
            output = "Нет такого билета";
        }


    }

    @Override
    public Response getCommandResponse() {
        return new Response(Objects.requireNonNullElse(output, "Element has been updated"));
    }
}
