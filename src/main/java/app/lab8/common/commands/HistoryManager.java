/**
 * The HistoryManager class represents a manager for command history.
 */

package app.lab8.common.commands;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private static volatile List<String> historyCommands = new ArrayList<>();

    public static List<String> getHistoryCommands() {
        return historyCommands;
    }

    public static void addToHistory(String command) {
        historyCommands.add(command + "\n");
    }
}
