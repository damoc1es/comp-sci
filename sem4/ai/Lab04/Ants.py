import numpy as np

def ant_iteration(distances, pheromone, num_ants):
    ant_tours = []
    N = len(distances)
    for _ in range(num_ants): # for every ant
        # current_city = random.randint(0, N-1) # randomly choose the starting city
        current_city = 0 # starting city
        
        visited_cities = [current_city]      # visited cities
        while len(visited_cities) < N:    # repeat until all cities have been visited
            # calculate the probabilities of visiting each neighboring city
            pheromone_values = pheromone[current_city, :]   # current pheromone levels
            distances_to_neighbors = distances[current_city, :]   # current to other cities distances
            unvisited_mask = np.array([1 if i not in visited_cities else 0 for i in range(N)]) # mask to filter already visited cities
            
            probabilities = np.zeros(N)
            non_zero_mask = (distances_to_neighbors > 0) & (pheromone_values > 0)   # filter for only available cities
            probabilities[non_zero_mask] = pheromone_values[non_zero_mask] * unvisited_mask[non_zero_mask] / distances_to_neighbors[non_zero_mask]
            probabilities = probabilities / sum(probabilities)

            next_city = np.random.choice(range(N), p=probabilities) # randomly choose the next city based on the probabilities

            visited_cities.append(next_city)
            current_city = next_city
        ant_tours.append(visited_cities)
    return ant_tours
