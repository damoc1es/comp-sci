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

import static utils.Config.DATE_TIME_FORMATTER;

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
        System.out.println("3 - Delete User");
        System.out.println("4 - Remove Friend");
        System.out.println("5 - Update User");
        System.out.println("6 - Update Friendship");
        System.out.println("7 - Show number of communities");
        System.out.println("8 - Most social community");
        System.out.println("9 - Show every User");
        System.out.println("10 - Show every Friendship");
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
     * UI for deleting an user
     */
    public void deleteUser() {
        System.out.print("Handle of deleted user: ");
        String handle = readString();

        try {
            int k = srv.removeUser(handle);
            System.out.printf("User deleted successfully. (along with %d friendships)%n", k);
        } catch (NotFoundException e) {
            System.out.println("Couldn't delete the user. " + e.getMessage());
        }
    }

    /**
     * UI for removing a friendship
     */
    public void removeFriendship() {
        System.out.print("First user's handle: ");
        String handle1 = readString();
        System.out.print("Second user's handle: ");
        String handle2 = readString();

        try {
            srv.removeFriendship(handle1, handle2);
            System.out.println("Friendship removed successfully.");
        } catch (NotFoundException e) {
            System.out.println("Couldn't remove the friendship. " + e.getMessage());
        }
    }

    /**
     * UI for updating a friendship
     */
    public void updateFriendship() {
        printFriendships();
        System.out.println();

        System.out.print("Old first user's handle: ");
        String oldHandle1 = readString();
        System.out.print("Old second user's handle: ");
        String oldHandle2 = readString();


        System.out.print("New first user's handle: ");
        String newHandle1 = readString();
        System.out.print("New second user's handle: ");
        String newHandle2 = readString();

        try {
            srv.updateFriendship(oldHandle1, oldHandle2, newHandle1, newHandle2);
            System.out.println("Friendship updated successfully.");
        } catch (NotFoundException | ValidationException e) {
            System.out.println("Couldn't update the friendship. " + e.getMessage());
        }
    }

    /**
     * UI for updating a user
     */
    public void updateUser() {
        printUsers();
        System.out.println();

        System.out.print("User's handle: ");
        String oldHandle = readString();
        System.out.print("User's new name: ");
        String newName = readString();
        System.out.print("User's new handle: ");
        String newHandle = readString();

        try {
            srv.updateUser(oldHandle, newName, newHandle);
            System.out.println("User updated successfully.");
        } catch (NotFoundException | ValidationException e) {
            System.out.println("Couldn't update the user. " + e.getMessage());
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
                System.out.println(user1 + " is friends with " + user2 + " [since " + fr.getFriendsFrom().format(DATE_TIME_FORMATTER) + "]");
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
                case 3 -> deleteUser();
                case 4 -> removeFriendship();
                case 5 -> updateUser();
                case 6 -> updateFriendship();
                case 7 -> nrOfCommunities();
                case 8 -> socialCommunity();
                case 9 -> printUsers();
                case 10 -> printFriendships();
                default -> {
                    if(x != 0)
                        System.out.println("Invalid command, must be between 0 and 9.");
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
