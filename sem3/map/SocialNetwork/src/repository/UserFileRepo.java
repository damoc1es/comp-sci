package repository;

import domain.User;
import domain.exception.DuplicatedException;
import domain.exception.ValidationException;
import domain.validators.Validator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

public class UserFileRepo extends InMemoryRepo<User> {
    private final String filename;

    /**
     * Repository constructor
     * @param validator T validator
     */
    public UserFileRepo(Validator<User> validator, String filename) {
        super(validator);
        this.filename = filename;
        loadFromFile();
    }

    /**
     * Loads from file the data
     */
    private void loadFromFile() {
        Path path = Paths.get(filename);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> {
                String[] l = line.split(";");
                User usr = new User(l[1], l[2]);
                usr.setId(UUID.fromString(l[0]));
                try {
                    super.store(usr);
                } catch (DuplicatedException | ValidationException ignore) {}
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes to file the data
     */
    private void writeToFile() {
        Path path = Paths.get(filename);
        List<User> userList = super.getList();

        try {
            Files.write(path, "".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userList.forEach(user -> {
            String userString = String.format("%s;%s;%s\n", user.getId(), user.getName(), user.getHandle());
            try {
                Files.write(path, userString.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void store(User obj) throws ValidationException, DuplicatedException {
        super.store(obj);
        writeToFile();
    }

    @Override
    public void remove(User obj) {
        super.remove(obj);
        writeToFile();
    }

    @Override
    public void update(User oldObj, User newObj) throws ValidationException {
        super.update(oldObj, newObj);
        writeToFile();
    }
}
