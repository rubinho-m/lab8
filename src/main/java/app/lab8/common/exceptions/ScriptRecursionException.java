/**
 * ScriptRecursionException is an Exception class that is thrown when script has recursion and can't quit.
 */

package app.lab8.common.exceptions;

public class ScriptRecursionException extends Exception {
    public ScriptRecursionException() {
        super("Looping scripts, please fix it");
    }
}
