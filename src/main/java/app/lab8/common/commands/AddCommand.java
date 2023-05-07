/**
 * The AddCommand class implements the execute method of the Command interface
 * to adding the new element into the collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package app.lab8.common.commands;

import app.lab8.common.networkStructures.Response;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.server.collectionManagement.CollectionManager;
import app.lab8.server.databaseManagement.DatabaseHandler;

import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AddCommand extends CommandTemplate implements CommandWithResponse {
    public AddCommand(CollectionManager collectionManager, DatabaseHandler dbHandler) {
        super(collectionManager, dbHandler);
    }

    @Override
    public void execute(String user) throws SQLException {
        getTicket().setUser(user);
        getTicket().setId((long) getDbHandler().addTicket(getTicket()));
        getCollectionManager().addToCollection(getTicket());
    }

    @Override
    public Response getCommandResponse() {
        return new Response("Added");
    }
}
