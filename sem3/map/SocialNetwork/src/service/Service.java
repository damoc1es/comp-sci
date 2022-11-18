package service;

import domain.Friendship;
import domain.User;
import repository.EntityFilterFunction;
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
     * Removes user with specified handle
     * @param handle handle of user to be removed
     * @return how many friendship the user had (that were in turn, removed)
     * @throws NotFoundException if no user with handle was found
     */
    public int removeUser(String handle) throws NotFoundException {
        User user = getUserByHandle(handle);
        if(user == null)
            throw new NotFoundException("User with this handle not found.");

        userRepo.remove(user);

        List<Friendship> toBeDeleted = new ArrayList<>();

        int k=0;
        for(Friendship fr : friendsRepo.getList()) {
            if(fr.getUserId1().equals(user.getId()) || fr.getUserId2().equals(user.getId())) {
                toBeDeleted.add(fr);
                k++;
            }
        }

        for(Friendship fr : toBeDeleted) {
            friendsRepo.remove(fr);
        }

        return k;
    }

    /**
     * Removes friendship
     * @param handle1 first user's handle
     * @param handle2 second user's handle
     * @throws NotFoundException if any user was not found
     */
    public void removeFriendship(String handle1, String handle2) throws NotFoundException {
        User user1 = getUserByHandle(handle1);
        User user2 = getUserByHandle(handle2);

        if(user1 == null)
            throw new NotFoundException("First user can't be found.");
        if(user2 == null)
            throw new NotFoundException("Second user can't be found.");

        Friendship friendship = null;
        for(Friendship fr : friendsRepo.getList()) {
            if(fr.getUserId1().equals(user1.getId()) && fr.getUserId2().equals(user2.getId()))
                friendship = fr;
        }

        if(friendship == null)
            throw new NotFoundException("Friendship not found.");

        friendsRepo.remove(friendship);
    }

    /**
     * Updates the data of an user
     * @param oldHandle old handle of user
     * @param newName new name of user
     * @param newHandle new handle of user
     * @throws NotFoundException if user wasn't found
     * @throws ValidationException if new data of user isn't valid
     */
    public void updateUser(String oldHandle, String newName, String newHandle) throws NotFoundException, ValidationException {
        User user = getUserByHandle(oldHandle);

        if(user == null)
            throw new NotFoundException("User can't be found.");

        User newUser = new User(newName, newHandle);
        newUser.setId(user.getId());

        userRepo.update(user, newUser);
    }

    /**
     *
     * @param oldHandle1 old handle of user1
     * @param oldHandle2 old handle of user2
     * @param newHandle1 new handle of user1
     * @param newHandle2 new handle of user2
     * @throws NotFoundException if any user wasn't found or friendship doesn't exist
     * @throws ValidationException if new data for friendship isn't valid
     */
    public void updateFriendship(String oldHandle1, String oldHandle2, String newHandle1, String newHandle2) throws NotFoundException, ValidationException {
        User oldUser1 = getUserByHandle(oldHandle1);
        User oldUser2 = getUserByHandle(oldHandle2);
        User newUser1 = getUserByHandle(newHandle1);
        User newUser2 = getUserByHandle(newHandle2);

        if(oldUser1 == null)
            throw new NotFoundException("First (old) user can't be found.");
        if(oldUser2 == null)
            throw new NotFoundException("Second (old) user can't be found.");

        if(newUser1 == null)
            throw new NotFoundException("First (new) user can't be found.");
        if(newUser2 == null)
            throw new NotFoundException("Second (new) user can't be found.");

        Friendship oldFriendship = null;
        for(Friendship fr : friendsRepo.getList()) {
            if(fr.getUserId1().equals(oldUser1.getId()) && fr.getUserId2().equals(oldUser2.getId()))
                oldFriendship = fr;
        }

        if(oldFriendship == null)
            throw new NotFoundException("Friendship not found.");


        Friendship newFriendship = new Friendship(newUser1.getId(), newUser2.getId(), oldFriendship.getFriendsFrom());
        newFriendship.setId(oldFriendship.getId());
        friendsRepo.update(oldFriendship, newFriendship);
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
        return userRepo.findByUUID(id);
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
