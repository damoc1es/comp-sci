import domain.Friendship;
import domain.User;
import domain.validators.UserValidator;
import repository.InMemoryRepo;
import repository.Repository;
import service.Service;
import ui.CLI;

public class Main {
    public static void main(String[] args) {
        Repository<User> userRepo = new InMemoryRepo<>();
        Repository<Friendship> friendsRepo = new InMemoryRepo<>();

        Service service = new Service(userRepo, friendsRepo, new UserValidator());

        CLI cli = new CLI(service);
        cli.run();
    }
}