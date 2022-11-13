package ui;

import domain.Friendship;
import domain.User;
import domain.exception.ValidationException;
import service.Service;
import domain.exception.DuplicatedException;
import domain.exception.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLI {
    private final Service srv;

    /**
     * Prints menu
     */
    public static void printMenu() {
        System.out.println("-----------------------");
        System.out.println("0 - Exit");
        System.out.println("1 - Add User");
        System.out.println("2 - Add Friend");
        System.out.println("3 - Show number of communities");
        System.out.println("4 - Most social community");
        System.out.println("5 - Show every User");
        System.out.println("6 - Show every Friendship");
        System.out.println("-----------------------");
        System.out.println();
    }

    /**
     * Reads string from stdin
     * @return string
     */
    public String readString() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            return reader.readLine();
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
        return "";
    }

    /**
     * Reads int from stdin
     * @return int
     */
    public int readInt() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while(true) {
                String string = reader.readLine();
                try {
                    return Integer.parseInt(string);
                } catch(NumberFormatException exception) {
                    System.out.println("Invalid format! Must be a valid integer; try again.");
                }
            }
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
        return -1;
    }

    /**
     * UI for adding an user
     */
    public void addUser() {
        System.out.print("Name: ");
        String name = readString();
        System.out.print("Handle: ");
        String handle = readString();

        try {
            srv.addUser(name, handle);
            System.out.println("User added successfully.");
        } catch (DuplicatedException | ValidationException e) {
            System.out.println("Couldn't add the user. " + e.getMessage());
        }
    }

    /**
     * UI for adding a friendship
     */
    public void addFriend() {
        System.out.print("First user's handle: ");
        String handle1 = readString();
        System.out.print("Second user's handle: ");
        String handle2 = readString();

        try {
            srv.addFriendship(handle1, handle2);
            System.out.println("Friendship added successfully.");
        } catch (DuplicatedException | NotFoundException | ValidationException e) {
            System.out.println("Couldn't add the friendship. " + e.getMessage());
        }
    }

    /**
     * Prints the number of communities
     */
    public void nrOfCommunities() {
        System.out.println("Number of communities: " + srv.nrOfCommunities());
    }

    /**
     * Prints the most social community's users
     */
    public void socialCommunity() {
        System.out.println("Most social community:");
        for(User user : srv.mostSocialCommunity()) {
            System.out.print(user.getHandle() + " ");
        }
        System.out.println();
    }

    /**
     * Prints every user
     */
    public void printUsers() {
        for(User user : srv.getUsers()) {
            System.out.println(user);
        }
    }

    /**
     * Prints every friendship
     */
    public void printFriendships() {
        for(Friendship fr : srv.getFriendships()) {
            User user1 = srv.getUserByUUID(fr.getUserId1());
            User user2 = srv.getUserByUUID(fr.getUserId2());
            if(user1 != null && user2 != null)
                System.out.println(user1 + " is friends with " + user2);
        }
    }

    /**
     * Runs CLI
     */
    public void run() {
        int x = 52;
        while(x != 0) {
            printMenu();
            System.out.println("Enter command:");
            x = readInt();
            switch (x) {
                case 1 -> addUser();
                case 2 -> addFriend();
                case 3 -> nrOfCommunities();
                case 4 -> socialCommunity();
                case 5 -> printUsers();
                case 6 -> printFriendships();
                default -> {
                    if(x != 0)
                        System.out.println("Invalid command, must be between 0 and 4.");
                }
            }
        }
    }

    /**
     * CLI constructor
     * @param service service
     */
    public CLI(Service service) {
        this.srv = service;
    }
}
