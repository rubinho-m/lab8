/**
 * The ExitCommand class implements the execute method of the Command interface
 * to exit the program.
 * Extends CommandTemplate class, which implements the Command interface
 * and provides access to the CollectionManager instance.
 */

package app.lab8.common.commands;


import app.lab8.common.networkStructures.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import app.lab8.server.network.Reader;

public class ExitCommand extends CommandTemplate implements CommandWithResponse {
    private static final Logger logger = LogManager.getLogger(ExitCommand.class);
    @Override
    public void execute(String user) {
        logger.info("EXIT SERVER");
        System.exit(0);
    }

    @Override
    public Response getCommandResponse() {
        return null;
    }
}
