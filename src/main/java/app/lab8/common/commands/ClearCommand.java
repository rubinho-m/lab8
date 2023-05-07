/**
 * The ClearCommand class implements the execute method of the Command interface
 * to delete all elements from the collection.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package app.lab8.common.commands;

import app.lab8.common.networkStructures.Response;
import app.lab8.server.collectionManagement.CollectionManager;
import app.lab8.server.databaseManagement.DatabaseHandler;

import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ClearCommand extends CommandTemplate implements CommandWithResponse{
    private StringBuilder output;
    public ClearCommand(CollectionManager collectionManager, DatabaseHandler dbHandler){
        super(collectionManager, dbHandler);
    }
    @Override
    public void execute(String user) throws SQLException {
        getDbHandler().deleteTickets(user);
        getCollectionManager().setCollection(getDbParser().loadCollection());
        output = new StringBuilder();
        output.append("User's tickets have been deleted");
    }


    @Override
    public Response getCommandResponse() {
        return new Response(output.toString());
    }
}
