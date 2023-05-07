/**
 * The Command interface represents a command that can be executed on a ticket collection.
 * The interface contains methods for executing the command, getting and setting the command argument,
 * and getting and setting the ticket on which the command should be executed.
 */

package app.lab8.common.commands;

import app.lab8.common.exceptions.EmptyCollectionException;
import app.lab8.common.structureClasses.Ticket;
import app.lab8.server.databaseManagement.DatabaseHandler;
import app.lab8.server.databaseManagement.DatabaseParser;

public interface Command {
    /**
     * Executes the command.
     *
     * @throws EmptyCollectionException if the ticket collection is empty and the command requires a non-empty collection.
     * @throws Exception                if there is an error executing the command.
     */
    void execute(String user) throws Exception;

    /**
     * Gets the argument for the command.
     *
     * @return the argument for the command.
     */
    String getArg();

    /**
     * Sets the argument for the command.
     *
     * @param arg the argument for the command.
     */
    void setArg(String arg);

    /**
     * Gets the ticket on which the command should be executed.
     *
     * @return the ticket on which the command should be executed.
     */
    Ticket getTicket();

    /**
     * Sets the ticket on which the command should be executed.
     *
     * @param ticket the ticket on which the command should be executed.
     */
    void setTicket(Ticket ticket);

//    void setUser(String user);
//
//    String getUser();

    DatabaseHandler getDbHandler();

    void setDbHandler(DatabaseHandler dbHandler);
    void setDbParser(DatabaseParser dbParser);

    DatabaseParser getDbParser();

}
