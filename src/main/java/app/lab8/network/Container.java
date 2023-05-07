package app.lab8.network;

public class Container {
    private static String authResponse;
    private static String user;
    private static String password;
    private static String actualResponse;

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
