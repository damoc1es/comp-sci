import domain.Friendship;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import domain.validators.Validator;
import repository.Repository;
import repository.db.FriendshipDbRepo;
import repository.db.UserDbRepo;
import repository.file.FriendshipFileRepo;
import repository.file.UserFileRepo;
import service.Service;
import ui.CLI;

public class Main {
    public static void main(String[] args) {
        Validator<User> userValidator = new UserValidator();
        Validator<Friendship> friendshipValidator = new FriendshipValidator();

        // Repository<User> userRepo = new InMemoryRepo<>(userValidator);
        // Repository<Friendship> friendsRepo = new InMemoryRepo<>(friendshipValidator);

        // Repository<User> userRepo = new UserFileRepo(userValidator, "users.csv");
        // Repository<Friendship> friendsRepo = new FriendshipFileRepo(friendshipValidator, "friends.csv");

        String url = "jdbc:postgresql://localhost:5432/SocialNetwork";
        String username = "postgres";

        Repository<User> userRepo = new UserDbRepo(url, username, "superman", userValidator);
        Repository<Friendship> friendsRepo = new FriendshipDbRepo(url, username, "superman", friendshipValidator);

        Service service = new Service(userRepo, friendsRepo);

        CLI cli = new CLI(service);
        cli.run();
    }
}