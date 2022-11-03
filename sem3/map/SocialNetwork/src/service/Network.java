package service;

import domain.Friendship;
import domain.User;

import java.util.*;

public class Network {
    Map<UUID, List<UUID>> network;

    /**
     * @param friendshipList list of all friendships
     * @param usersList list of all users
     */
    public Network(List<Friendship> friendshipList, List<User> usersList) {
        network = new HashMap<>();

        for(User u : usersList) {
            network.put(u.getId(), new ArrayList<>());
        }

        for(Friendship fr : friendshipList) {
            UUID u1 = fr.getUserId1();
            UUID u2 = fr.getUserId2();

            if(!network.get(u1).contains(u2)) {
                network.get(u1).add(u2);
            }

            if(!network.get(u2).contains(u1)) {
                network.get(u2).add(u1);
            }
        }
    }

    /**
     * Visits every node from starting node and sets the value from map for every node
     * @param visited map of every node and 0 if visited
     * @param node starting node
     */
    private void BFS(HashMap<UUID, Integer> visited, UUID node) {
        Queue<UUID> queue = new LinkedList<>();
        queue.add(node);

        while(queue.size() != 0) {
            UUID x = queue.poll();

            for(UUID i : network.get(x)) {
                if(visited.get(i) == 0) {
                    visited.put(i, visited.get(node));
                    queue.add(i);
                }
            }
        }
    }

    /**
     * Figures out every connected component that represents a community
     * @return list of all communities (list of IDs)
     */
    public List<List<UUID>> getCommunities() {
        HashMap<UUID, Integer> visited = new HashMap<>();
        for(UUID id : network.keySet()) {
            visited.put(id, 0);
        }

        int k=1;
        List<List<UUID>> communities = new ArrayList<>();
        for(UUID id : network.keySet()) {
            if(visited.get(id) == 0) {
                visited.put(id, k);
                BFS(visited, id);

                List<UUID> list = new ArrayList<>();
                for(UUID id2 : network.keySet()) {
                    if(visited.get(id2) == k)
                        list.add(id2);
                }
                communities.add(list);

                k++;
            }
        }
        return communities;
    }

    /**
     * Most social community = community with most friendships
     * @return most social community (list of IDs)
     */
    public List<UUID> getMostSocialCommunity() {
        List<List<UUID>> communities = getCommunities();

        int max = -1;
        List<UUID> maxList = new ArrayList<>();
        for(List<UUID> list : communities) {
            int k = 0;
            for(UUID id : list) {
                k += network.get(id).size();
            }
            if(k > max) {
                max = k;
                maxList = list;
            }
        }

        return maxList;
    }
}
