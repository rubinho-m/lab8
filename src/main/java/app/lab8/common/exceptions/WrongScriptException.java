/**
 * WrongScriptException is an Exception class that is thrown when program has incorrect script.
 */

package app.lab8.common.exceptions;

public class WrongScriptException extends Exception {
    public WrongScriptException() {
        super("There is mistake in your script. Please fix it");
    }
}
