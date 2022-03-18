
import GraphVisual.*;

import java.util.*;

public class GraphModel {
    private int vertexCount;
    private int edgeCount;
    private int source, sink;
    private ArrayList<Integer>[] adj;
    private ArrayList <Edge> edgeList;
    private int[] level;

    public GraphModel() {
        setVertexCount(6);
        initialize();
        /*addEdge(0, 1, 16);
        addEdge(0, 2, 13);
        addEdge(2, 1, 4);
        addEdge(1, 3, 12);
        addEdge(3, 2, 9);
        addEdge(2, 4, 14);
        addEdge(4, 3, 7);
        addEdge(3, 5, 20);
        addEdge(4, 5, 4);*/
        randomGraph(10);
    }

    public void setVertexCount(int value) {
        vertexCount = value;
        initialize();
    }

    private void initialize() {
        edgeCount = 0;
        source  = 0;
        sink = vertexCount - 1;
        edgeList = new ArrayList<>();
        adj = new ArrayList[vertexCount];
        for(int i = 0; i < vertexCount; i ++)
            adj[i] = new ArrayList<Integer>();
        level = new int[vertexCount];
    }

    private boolean checkGraph() {
        int minDist = vertexCount / 3;
        boolean multipleEdges = false;

        computeLayers();
        if(level[sink] - level[source] < minDist)
            return false;
        for(int i = 0; i < vertexCount; i ++)
            if(level[i] == -1)
                    return false;
        return true;
    }

    public void randomGraph(int vertexCount) {
        setVertexCount(vertexCount);
        int[][] a = new int[vertexCount][vertexCount];
        int pathCount = 10, minLenght = vertexCount / 3;

        for(int p = 0; p < pathCount; p ++) {
            int cap = (int)(Math.random() * 5);
            boolean[] visited = new boolean[vertexCount];
            int lenght = 0, node = source;

            while(node != sink) {
                visited[node] = true;
                lenght ++;

                int next;
                do {
                    next = (int)(Math.random() * vertexCount);
                }while(node == next || visited[next] == true || (next == sink && lenght < minLenght));

                a[node][next] += cap + (Math.random() * 3);
                node = next;
            }
        }

        for(int i = 0; i < vertexCount; i ++) {
            for(int j = i + 1; j < vertexCount; j ++) {
                if(a[i][j] > a[j][i])
                    addEdge(i, j, a[i][j] - a[j][i]);
                else if(a[j][i] > a[i][j])
                    addEdge(j, i, a[j][i] - a[i][j]);
            }
        }
    }

    public void addEdge(int src, int dest, int cap) {
        edgeList.add(new Edge(src, dest, cap));
        edgeList.add(new Edge(dest, src, 0));
        adj[src].add(edgeCount);
        adj[dest].add(edgeCount + 1);
        edgeCount += 2;
    }

    public boolean computeLayers(){
        Arrays.fill(level, -1);
        level[source] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);

        while(queue.isEmpty() == false) {
            int node = queue.poll();

            for(int i = 0; i < adj[node].size(); i ++) {
                int next = edgeList.get(adj[node].get(i)).next;
                int potential = edgeList.get(adj[node].get(i)).cap - edgeList.get(adj[node].get(i)).flow;
                if(level[next] == -1 && potential > 0) {
                    level[next] = level[node] + 1;
                    queue.add(next);
                }
            }
        }

        return (level[sink] != -1);
    }

    private int dfs(ArrayList<GraphVisual.Edge> path,  int node, int flow) {
        if(node == sink)
            return flow;
        if(flow == 0)
            return 0;

        for(int i = 0; i < adj[node].size(); i ++) {
            int next = edgeList.get(adj[node].get(i)).next;
            int potential = edgeList.get(adj[node].get(i)).cap - edgeList.get(adj[node].get(i)).flow;

            if(level[next] != level[node] + 1 || potential <= 0)
                continue;

            int nextFlow = dfs(path, next, Math.min(flow, potential));
            if(nextFlow == 0)
                continue;

            if(edgeList.get(adj[node].get(i)).cap > 0)
                path.add(convertEdge(edgeList.get(adj[node].get(i))));
            else
                path.add(convertEdge(edgeList.get(adj[node].get(i) ^ 1)));

            edgeList.get(adj[node].get(i)).flow += nextFlow;
            edgeList.get(adj[node].get(i) ^ 1).flow -= nextFlow;
            return nextFlow;
        }
        return 0;
    }

    private GraphVisual.Edge convertEdge(Edge edge) {
        return new GraphVisual.Edge(edge.node, edge.next, edge.cap, edge.flow);
    }

    public int findNewFlow(ArrayList<GraphVisual.Edge> path) {
        return dfs(path, source, 1000000);
    }

    public Vertex[] getVertexList() {
        Vertex[] list = new Vertex[vertexCount];
        for(int i = 0; i < vertexCount; i ++) {
            list[i] = new Vertex();
            list[i].setLayer(level[i]);
            if(i == 0)
                list[i].setLabel("Source");
            else if(i + 1 == vertexCount)
                list[i].setLabel("Sink");
            else
                list[i].setLabel(Integer.toString(i));
        }

        return list;
    }

    public ArrayList<GraphVisual.Edge> getEdgeList() {
        ArrayList<GraphVisual.Edge> list = new ArrayList<>();
        for(int i = 0; i < vertexCount; i ++) {
            for(int j = 0; j < adj[i].size(); j ++) {
                Edge edge = edgeList.get(adj[i].get(j));
                if(edge.cap > 0) {
                    list.add(new GraphVisual.Edge(edge.node, edge.next, edge.cap, edge.flow));
                }
            }
        }
        return list;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public static void main(String[] args) {
        GraphModel graph = new GraphModel();
        graph.setVertexCount(6);
        graph.addEdge(0, 1, 16);
        graph.addEdge(0, 2, 13);
        graph.addEdge(2, 1, 4);
        graph.addEdge(1, 3, 12);
        graph.addEdge(3, 2, 9);
        graph.addEdge(2, 4, 14);
        graph.addEdge(4, 3, 7);
        graph.addEdge(3, 5, 20);
        graph.addEdge(4, 5, 4);

    }


    private class Edge {
        public int node, next;
        public int cap, flow = 0;


        public Edge(int node, int next, int cap) {
            this.node = node;
            this.next = next;
            this.cap = cap;
        }
    }
}
