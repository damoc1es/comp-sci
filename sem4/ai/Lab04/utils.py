import matplotlib.pyplot as plt
import networkx as nx

def read_graph(filename):
    with open(filename, 'r') as f:
        n = int(f.readline())
        matrix = []
        
        for _ in range(n):
            line = f.readline()
            matrix.append([int(elem) for elem in line.split(',')])
        
        for i in range(n):
            for j in range(n):
                if matrix[i][j] == 0:
                    matrix[i][j] = float('inf')

        graph = {
            'noNodes': n,
            'matrix': matrix,
            'noEdges': n*(n-1)//2,
            'source': int(f.readline()),
            'destination': int(f.readline())
        }

        return graph

def read_tsp_file(filename):
    import tsplib95
    with open(filename) as f:
        return tsplib95.read(f).get_graph()


def nx_to_alt_format(network: nx.Graph):
    mat = nx.to_numpy_array(network).tolist()
    n = network.number_of_nodes()
    for i in range(n):
        for j in range(n):
            if mat[i][j] == 0:
                mat[i][j] = float('inf')
    
    graph = {
        'noNodes': n,
        'matrix': mat,
        'noEdges': network.number_of_edges(),
        'source': 0,
        'destination': n-1,
    }

    return graph

def read_tsp(filename):
    return nx_to_alt_format(read_tsp_file(filename))

def plot_stats(gens, bests, avgs, worsts):
    plt.plot(gens, bests, label="Best")
    plt.plot(gens, avgs, label="Average")
    plt.plot(gens, worsts, label="Worst")
    plt.legend()
    plt.show()
