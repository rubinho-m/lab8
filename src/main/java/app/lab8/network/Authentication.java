package app.lab8.network;

import app.lab8.common.networkStructures.Request;

import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Authentication {
    private String login;
    private String password;
    private Scanner scanner = new Scanner(System.in);
    private String type;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void chooseFunction() {
        System.out.println("Введите 0 для регистрации, 1 для авторизации");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) {
                System.out.println("Регистрация:");
                type = "reg";
            } else if (choice == 1) {
                System.out.println("Авторизация:");
                type = "auth";
            } else {
                chooseFunction();
            }
            readUserData(scanner);
        } catch (NoSuchElementException e) {
            System.exit(0);
        } catch (Exception e) {
            chooseFunction();
        }


    }

    public void readPassword(Scanner scanner) {
        Console console = System.console();
        String insecurePassword;

        if (console != null) {
            insecurePassword = String.valueOf(console.readPassword());
        } else {
            insecurePassword = scanner.nextLine();
        }
        password = encodePassword(insecurePassword);
    }

    public String encodePassword(String insecurePassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = messageDigest.digest(insecurePassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void readLogin(Scanner scanner) {
        login = scanner.nextLine();
    }

    public void readUserData(Scanner scanner) {
        System.out.println("Введите логин");
        readLogin(scanner);
        System.out.println("Введите пароль");
        readPassword(scanner);
    }

    public Request getRequest() {
        ArrayList<String> commandWithArgs = new ArrayList<>();
        ArrayList<String> userData = new ArrayList<>();
        commandWithArgs.add(type);
        userData.add(login);
        userData.add(password);
        return new Request(commandWithArgs, userData);
    }

}
