/**
 * The AddIfMinCommand class implements the execute method of the Command interface
 * to adding the new element into the collection if it's smaller than minimum of collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package app.lab8.common.commands;

import app.lab8.common.networkStructures.Response;
import app.lab8.server.collectionManagement.CollectionManager;
import app.lab8.server.collectionManagement.CommandExecutor;
import app.lab8.common.exceptions.EmptyCollectionException;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.server.databaseManagement.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AddIfMinCommand extends CommandTemplate implements CommandWithResponse{
    public AddIfMinCommand(CollectionManager collectionManager, DatabaseHandler dbHandler) {
        super(collectionManager, dbHandler);
    }

    @Override
    public void execute(String user) throws Exception {
        Ticket ticketToAdd = getTicket();
        Set<Ticket> tickets = getCollectionManager().getCollection();
        if (tickets.size() == 0){
            throw new EmptyCollectionException();
        }
        List<Ticket> sortedTickets = new ArrayList<>(tickets);
        Collections.sort(sortedTickets);
        Ticket minTicket = sortedTickets.get(0);
        if (ticketToAdd.compareTo(minTicket) < 0){
            Command addCommand = CommandExecutor.getCommandMap().get("add");
            addCommand.setTicket(ticketToAdd);
            addCommand.execute(user);
        }



    }

    @Override
    public Response getCommandResponse() {
        return new Response("Added");
    }
}
