import domain.Friendship;
import domain.User;
import domain.validators.NoValidator;
import domain.validators.UserValidator;
import repository.InMemoryRepo;
import repository.Repository;
import service.Service;
import ui.CLI;

public class Main {
    public static void main(String[] args) {
        Repository<User> userRepo = new InMemoryRepo<>(new UserValidator());
        Repository<Friendship> friendsRepo = new InMemoryRepo<>(new NoValidator<>());

        Service service = new Service(userRepo, friendsRepo);

        CLI cli = new CLI(service);
        cli.run();
    }
}