package service;

import domain.Friendship;
import domain.User;
import repository.Repository;
import utils.Config;
import domain.exception.DuplicatedException;
import domain.exception.NotFoundException;
import domain.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Service {
    Repository<User> userRepo;
    Repository<Friendship> friendsRepo;

    /**
     * Service constructor
     * @param userRepo User repository
     * @param friendsRepo Friendship repository
     */
    public Service(Repository<User> userRepo, Repository<Friendship> friendsRepo) {
        this.userRepo = userRepo;
        this.friendsRepo = friendsRepo;
    }

    /**
     * Adds user with given name and handle
     * @param name user's name
     * @param handle user's handle
     * @throws DuplicatedException if handle already exists
     * @throws ValidationException if user isn't valid
     */
    public void addUser(String name, String handle) throws DuplicatedException, ValidationException {
        User added = new User(name, handle);

        if(getUserByHandle(handle) == null)
            userRepo.store(added);
        else throw new DuplicatedException("User with this handle already exists.");
    }

    /**
     * Adds friendship with given user's ids
     * @param handle1 first user's handle
     * @param handle2 second user's handle
     * @throws NotFoundException if users with given handles don't exist
     * @throws DuplicatedException if the users are already friends
     */
    public void addFriendship(String handle1, String handle2) throws NotFoundException, DuplicatedException, ValidationException {
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
            friendsRepo.store(new Friendship(usr1, usr2, LocalDateTime.now()));
            if(!Config.ONE_SIDED_FRIENDSHIP)
                friendsRepo.store(new Friendship(usr2, usr1, LocalDateTime.now()));
        } catch(DuplicatedException exception) {
            throw new DuplicatedException("This friendship already exists.");
        }
    }

    /**
     * @return list of all users
     */
    public List<User> getUsers() {
        return userRepo.getList();
    }

    /**
     * Finds user by handle
     * @param handle handle of user to be found
     * @return the user or null if user wasn't found
     */
    public User getUserByHandle(String handle) {
        for(User user : userRepo.getList()) {
            if(Objects.equals(user.getHandle(), handle))
                return user;
        }
        return null;
    }

    /**
     * Finds user by UUID
     * @param id uuid of user to be found
     * @return the user or null if user wasn't found
     */
    public User getUserByUUID(UUID id) {
        for(User user : userRepo.getList()) {
            if(user.getId() == id)
                return user;
        }
        return null;
    }

    /**
     * @return number of existent communities
     */
    public int nrOfCommunities() {
        Network network = new Network(friendsRepo.getList(), userRepo.getList());
        return network.getCommunities().size();
    }

    /**
     * @return list of users of most social community
     */
    public List<User> mostSocialCommunity() {
        Network network = new Network(friendsRepo.getList(), userRepo.getList());
        List<User> userList = new ArrayList<>();
        for(UUID id : network.getMostSocialCommunity())
            userList.add(getUserByUUID(id));
        return userList;
    }

    /**
     * @return list of all friendships
     */
    public List<Friendship> getFriendships() {
        return friendsRepo.getList();
    }
}
