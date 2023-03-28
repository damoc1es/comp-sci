import networkx as nx
import numpy as np
import matplotlib.pyplot as plt
from scipy.io import mmread

def read_network_matrix(filename):
    with open(filename, "r") as f:
        net = {}
        n = int(f.readline())
        net['noNodes'] = n
        mat = []
        for i in range(n):
            mat.append([])
            line = f.readline()
            elems = line.split(" ")
            for j in range(n):
                mat[-1].append(int(elems[j]))
        net["mat"] = mat 
        degrees = []
        noEdges = 0
        for i in range(n):
            d = 0
            for j in range(n):
                if (mat[i][j] == 1):
                    d += 1
                if (j > i):
                    noEdges += mat[i][j]
            degrees.append(d)
        net["noEdges"] = noEdges
        net["degrees"] = degrees
        return net

def read_network_gml(filename):
    """Reads network from gml file

    Args:
        filename (str): file path

    Returns:
        Graph: graph
    """
    nx_graph = nx.read_gml(filename, label="id")
    
    graph = {
        "noNodes": nx_graph.number_of_nodes(),
        "mat": nx.to_numpy_array(nx_graph).astype('int32').tolist(),
        "noEdges": nx_graph.number_of_edges(),
        "degrees": [d[1] for d in nx_graph.degree()],
    }
    
    return graph

def read_network_mtx(filename):
    """Reads network from mtx file

    Args:
        filename (str): file path

    Returns:
        Graph: graph
    """
    nx_graph = nx.from_scipy_sparse_array(mmread(filename))

    graph = {
        "noNodes": nx_graph.number_of_nodes(),
        "mat": nx.to_numpy_array(nx_graph).astype('int32').tolist(),
        "noEdges": nx_graph.number_of_edges(),
        "degrees": [d[1] for d in nx_graph.degree()],
    }
    
    return graph

def read_network(filename):
    """Reads network from file (.gml, .mtx, .in)

    Args:
        filename (str): file path

    Returns:
        Graph: network
    """
    match filename.split('.'):
        case [*_, "gml"]:
            return read_network_gml(filename)
        case [*_, "mtx"]:
            return read_network_mtx(filename)
        case _:
            return read_network_matrix(filename)

def plot_stats(gens, bests, worsts, avgs):
    plt.plot(gens, bests, label="Best")
    plt.plot(gens, avgs, label="Average")
    plt.plot(gens, worsts, label="Worst")
    plt.legend()
    plt.show()
