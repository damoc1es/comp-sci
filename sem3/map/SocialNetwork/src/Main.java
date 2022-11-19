import domain.Friendship;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import domain.validators.Validator;
import repository.FriendshipsFileRepo;
import repository.InMemoryRepo;
import repository.Repository;
import repository.UserFileRepo;
import repository.db.FriendshipDbRepo;
import repository.db.UserDbRepo;
import service.Service;
import ui.CLI;

public class Main {
    public static void main(String[] args) {
        Validator<User> userValidator = new UserValidator();
        Validator<Friendship> friendshipValidator = new FriendshipValidator();

        // Repository<User> userRepo = new InMemoryRepo<>(userValidator);
        // Repository<Friendship> friendsRepo = new InMemoryRepo<>(friendshipValidator);

        // Repository<User> userRepo = new UserFileRepo(userValidator, "users.csv");
        // Repository<Friendship> friendsRepo = new FriendshipsFileRepo(friendshipValidator, "friends.csv");

        Repository<User> userRepo = new UserDbRepo("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres", "superman", userValidator);
        Repository<Friendship> friendsRepo = new FriendshipDbRepo("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres", "superman", friendshipValidator);

        Service service = new Service(userRepo, friendsRepo);

        CLI cli = new CLI(service);
        cli.run();
    }
}