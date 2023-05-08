package app.lab8.network;

import app.lab8.common.structureClasses.Ticket;

import java.util.HashSet;
import java.util.Set;

public class Container {
    private static String authResponse;
    private static String user;
    private static String password;
    private static String actualResponse = "";
    private static String lastResponse = "";
    private static Set<Ticket> tickets = new HashSet<>();

    public static Set<Ticket> getTickets() {
        return tickets;
    }

    public static void setTickets(Set<Ticket> tickets) {
        Container.tickets = tickets;
    }

    public static String getLastResponse() {
        return lastResponse;
    }

    public static void setLastResponse(String lastResponse) {
        Container.lastResponse = lastResponse;
    }

    public static String getActualResponse() {
        return actualResponse;
    }

    public static void setActualResponse(String actualResponse) {
        Container.actualResponse = actualResponse;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Container.password = password;
    }

    public static String getAuthResponse() {
        return authResponse;
    }

    public static void setAuthResponse(String authResponse) {
        Container.authResponse = authResponse;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Container.user = user;
    }
}
