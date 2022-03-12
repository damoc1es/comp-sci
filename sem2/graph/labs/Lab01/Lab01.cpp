#include <iostream>
#include <fstream>
#include <vector>

#define NMAX 100
#define MMAX NMAX
#define INF (1 << 30 - 1)

using namespace std;

ifstream fin("in.txt");

bool visited[NMAX];

int n, m; // n = number of vertices, m = number of edges
int M_inc[NMAX][NMAX];
int M_adj[NMAX][NMAX];
int M_dist[NMAX][NMAX];
vector <int> L_adj[NMAX];
vector <pair <int, int>> L_edges;

void L_edges_to_M_adj() {
    for(auto it : L_edges) {
        auto n1 = it.first, n2 = it.second;
        M_adj[n1][n2] = M_adj[n2][n1] = 1;
    }
}

void print_M_adj() {
    cout << '\n';
    for(int i=1; i<=n; i++) {
        for(int j=1; j<=n; j++)
            cout << M_adj[i][j] << " ";
        cout << '\n';
    }
    cout << '\n';
}

void L_edges_to_M_inc() {
    int i=1;
    for(auto it=L_edges.begin(); it != L_edges.end(); ++it, ++i) {
        auto n1 = (*it).first, n2 = (*it).second;
        M_inc[n1][i] = M_inc[n2][i] = 1;
    }
}

void print_M_inc() {
    cout << '\n';
    for(int i=1; i<=n; i++) {
        for(int j=1; j<=m; j++)
            cout << M_inc[i][j] << " ";
        cout << '\n';
    }
    cout << '\n';
}

void L_edges_to_L_adj() {
    for(auto it : L_edges) {
        auto n1 = it.first, n2 = it.second;
        
        bool duplicated = false;
        for(auto it2 : L_adj[n1])
            if(it2 == n2)
                duplicated = true;
            
        if(!duplicated) {
            L_adj[n1].push_back(n2);
            L_adj[n2].push_back(n1);
        }
    }
}

void print_L_adj() {
    cout << '\n';
    for(int i=1; i<=n; i++) {
        cout << i << " | ";
        for(auto it : L_adj[i]) {
            cout << it << ' ';
        }
        cout << '\n';
    }
    cout << '\n';
}

int degree(int node) {
    return L_adj[node].size();
}

void Roy_Floyd() {
    for(int k=1; k<=n; k++)
        for(int i=1; i<=n; i++)
            for(int j=1; j<=n; j++)
                if(i != j && M_adj[i][k] && M_adj[k][j] && (M_adj[i][j] > M_adj[i][k] + M_adj[k][j] || !M_adj[i][j]))
                    M_adj[i][j] = M_adj[i][k] + M_adj[k][j];
}

void DFS(int node) {
    visited[node] = true;
    for(auto it : L_adj[node])
        if(!visited[it])
            DFS(it);
}

int main() {
    fin >> n;

    pair <int, int> edge;
    while(fin >> edge.first >> edge.second)
        L_edges.push_back(edge);
    m = L_edges.size();
    
    L_edges_to_M_adj();
    cout << "Matricea de adiacenta:";
    print_M_adj();

    L_edges_to_M_inc();
    cout << "Matricea de incidenta:";
    print_M_inc();

    L_edges_to_L_adj();
    cout << "Lista de adiacenta:";
    print_L_adj();

    vector <int> isolated;
    for(int i=1; i<=n; i++)
        if(degree(i) == 0)
            isolated.push_back(i);
        
    if(isolated.size() == 0)
        cout << "Nu exista noduri izolate.";
    else {
        cout << "Noduri izolate: ";
        for(auto it : isolated)
            cout << it << " ";
    }
    cout << "\n\n";

    int k=degree(1);
    bool regulat = true;
    for(int i=2; i<=n; i++)
        if(degree(i) != k) {
            regulat = false;
            break;
        }
    if(regulat)
        cout << "Graful este " << k << "-regulat.";
    else cout << "Graful nu este regulat";
    cout << "\n\n";

    Roy_Floyd();
    cout << "Matricea distantelor:";
    print_M_adj();

    DFS(1);
    bool conex = true;
    for(int i=2; i<=n; i++)
        if(!visited[i])
            conex = false;
    if(conex)
        cout << "Graful este conex.";
    else cout << "Graful nu este conex.";
    cout << "\n\n";
    
    return 0;
}