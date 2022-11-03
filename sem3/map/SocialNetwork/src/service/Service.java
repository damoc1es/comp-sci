package service;

import domain.Friendship;
import domain.User;
import domain.validators.UserValidator;
import repository.Repository;
import utils.Config;
import domain.exception.DuplicatedException;
import domain.exception.NotFoundException;
import domain.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Service {
    Repository<User> userRepo;
    Repository<Friendship> friendsRepo;

    UserValidator validator;

    public Service(Repository<User> userRepo, Repository<Friendship> friendsRepo, UserValidator validator) {
        this.userRepo = userRepo;
        this.friendsRepo = friendsRepo;
        this.validator = validator;
    }

    public void addUser(String name, String handle) throws DuplicatedException, ValidationException {
        User added = new User(name, handle);

        validator.validate(added);

        if(getUserByHandle(handle) == null)
            userRepo.store(added);
        else throw new DuplicatedException("User with this handle already exists.");
    }

    public void addFriendship(String handle1, String handle2) throws NotFoundException, DuplicatedException {
        List<User> list = userRepo.getList();
        UUID usr1=null, usr2=null;

        for(User u : list) {
            if(Objects.equals(u.getHandle(), handle1)) {
                usr1 = u.getId();
            }
        }
        if(usr1 == null)
            throw new NotFoundException("First user can't be found.");

        for(User u : list) {
            if(Objects.equals(u.getHandle(), handle2)) {
                usr2 = u.getId();
            }
        }
        if(usr2 == null)
            throw new NotFoundException("Second user can't be found.");

        try {
            friendsRepo.store(new Friendship(usr1, usr2));
            if(!Config.ONE_SIDED_FRIENDSHIP)
                friendsRepo.store(new Friendship(usr2, usr1));
        } catch(DuplicatedException exception) {
            throw new DuplicatedException("This friendship already exists.");
        }
    }

    public List<User> getUsers() {
        return userRepo.getList();
    }

    public User getUserByHandle(String handle) {
        for(User user : userRepo.getList()) {
            if(Objects.equals(user.getHandle(), handle))
                return user;
        }
        return null;
    }

    public User getUserByUUID(UUID id) {
        for(User user : userRepo.getList()) {
            if(user.getId() == id)
                return user;
        }
        return null;
    }

    public int nrOfCommunities() {
        Network network = new Network(friendsRepo.getList(), userRepo.getList());
        return network.getCommunities().size();
    }

    public List<User> mostSocialCommunity() {
        Network network = new Network(friendsRepo.getList(), userRepo.getList());
        List<User> userList = new ArrayList<>();
        for(UUID id : network.getMostSocialCommunity())
            userList.add(getUserByUUID(id));
        return userList;
    }

    public List<Friendship> getFriendships() {
        return friendsRepo.getList();
    }
}
