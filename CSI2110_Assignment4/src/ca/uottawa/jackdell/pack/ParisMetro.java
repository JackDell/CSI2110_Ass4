package ca.uottawa.jackdell.pack;

import java.util.List;

public class ParisMetro {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

    public ParisMetro(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
