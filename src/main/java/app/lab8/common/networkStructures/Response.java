package app.lab8.common.networkStructures;

import app.lab8.common.structureClasses.Ticket;

import java.io.Serializable;
import java.util.Set;

public class Response implements Serializable {
    private String output;
    private Set<Ticket> tickets;

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Response(String output) {
        this.output = output;
    }
}
