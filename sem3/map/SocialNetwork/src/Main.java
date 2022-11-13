import domain.Friendship;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import repository.FriendshipsFileRepo;
import repository.InMemoryRepo;
import repository.Repository;
import repository.UserFileRepo;
import service.Service;
import ui.CLI;

public class Main {
    public static void main(String[] args) {
        // Repository<User> userRepo = new InMemoryRepo<>(new UserValidator());
        Repository<User> userRepo = new UserFileRepo(new UserValidator(), "users.txt");
        // Repository<Friendship> friendsRepo = new InMemoryRepo<>(new FriendshipValidator());
        Repository<Friendship> friendsRepo = new FriendshipsFileRepo(new FriendshipValidator(), "friends.txt");

        Service service = new Service(userRepo, friendsRepo);

        CLI cli = new CLI(service);
        cli.run();
    }
}