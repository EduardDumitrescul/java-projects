package GraphVisual;

public class Edge {
    private int src, dest, cap, flow;

    public Edge(int src, int dest, int cap, int flow) {
        this.src = src;
        this.dest = dest;
        this.cap = cap;
        this.flow = flow;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public int getDest() {
        return dest;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getSrc() {
        return src;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }
}
