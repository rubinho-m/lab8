package app.lab8.common.commands;

import app.lab8.common.networkStructures.Response;

public interface CommandWithResponse extends Command {
    Response getCommandResponse();
}
