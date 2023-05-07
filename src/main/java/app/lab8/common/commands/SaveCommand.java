/**
 * The SaveCommand class implements the execute method of the Command interface
 * to save the collection into file.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package app.lab8.common.commands;

import app.lab8.common.exceptions.XMLTroubleException;
import app.lab8.common.networkStructures.Response;
import app.lab8.server.collectionManagement.CollectionManager;

import java.io.IOException;

public class SaveCommand extends CommandTemplate implements CommandWithResponse {

    public SaveCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String user) {

    }

    @Override
    public Response getCommandResponse() {
        return null;
    }
}
