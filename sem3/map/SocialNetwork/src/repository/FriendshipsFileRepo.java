package repository;

import domain.Friendship;
import domain.exception.DuplicatedException;
import domain.exception.ValidationException;
import domain.validators.Validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FriendshipsFileRepo extends InMemoryRepo<Friendship>{
    private final String filename;

    /**
     * Repository constructor
     * @param validator T validator
     */
    public FriendshipsFileRepo(Validator<Friendship> validator, String filename) {
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
                Friendship fr = new Friendship(UUID.fromString(l[1]), UUID.fromString(l[2]), LocalDateTime.parse(l[3]));
                fr.setId(UUID.fromString(l[0]));
                try {
                    super.store(fr);
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
        List<Friendship> userList = super.getList();

        try {
            Files.write(path, "".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userList.forEach(fr -> {
            String frString = String.format("%s;%s;%s;%s\n", fr.getId(), fr.getUserId1(), fr.getUserId2(), fr.getFriendsFrom());
            try {
                Files.write(path, frString.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void store(Friendship obj) throws ValidationException, DuplicatedException {
        super.store(obj);
        writeToFile();
    }

    @Override
    public void remove(Friendship obj) {
        super.remove(obj);
        writeToFile();
    }

    @Override
    public void update(Friendship oldObj, Friendship newObj) {
        super.update(oldObj, newObj);
        writeToFile();
    }
}
