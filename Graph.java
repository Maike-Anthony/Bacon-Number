import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class to represent a directed graph.
 * @author Maike Anthony dos Santos Silva
 */
public class Graph {

    /**A hashmap that contains the vertices according to their names*/
    private HashMap<String, Vertex> vertices;

    /**A hashmap that contains the adjacency list. */
    private HashMap<Vertex, LinkedList<Edge>> edges;

    /**
     * Class constructor.
     */
    public Graph() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
    }

    /**
     * Method to add a vertex to the graph.
     * @param vertex
     */
    public void addVertex(Vertex vertex) {
        if (this.getVertex(vertex.name) != null) {
            return;
        }
        this.vertices.put(vertex.name, vertex);
        LinkedList<Edge> list = new LinkedList<>();
        this.edges.put(vertex, list);
    }

    /**
     * Method to add an edge to the graph.
     * @param source the vertex that is the source of the edge.
     * @param destination the vertex that is the destination of the edge.
     * @param label the label of the edge.
     */
    public void addEdge(Vertex source, Vertex destination, String label) {
        if (getVertex(source.name) == null) {
            addVertex(source);
        }
        if (getVertex(destination.name) == null) {
            addVertex(destination);
        }
        Edge edge = new Edge(source, destination, label);
        LinkedList<Edge> sourceEdges = this.edges.get(source);
        sourceEdges.add(edge);
    }

    /**
     * A method to find the vertex given its name.
     * @param name the name of the vertex.
     */
    public Vertex getVertex(String name) {
        if (this.vertices.containsKey(name)) {
            return this.vertices.get(name);
        } else {
            return null;
        }
    }

    /**
     * Method to get the list of edges of a vertex.
     * @param vertex the vertex whose edges we are looking for.
     * @return the list of edges of the vertex.
     */
    public LinkedList<Edge> getEdges(Vertex vertex) {
        if (this.edges.containsKey(vertex)) {
            return this.edges.get(vertex);
        } else {
            return null;
        }
    }

    /**
     * Method to get a linked list of neighboring vertices of a given vertex.
     * @param vertex the vertex whose neighbors are being looked for.
     * @return the linked list of neighbors.
     */
    public LinkedList<Vertex> getNeighbors(Vertex vertex) {
        if (this.edges.containsKey(vertex)) {
            LinkedList<Vertex> neighbors = new LinkedList<>();
            for (Edge edge : this.getEdges(vertex)) {
                neighbors.add(edge.destination);
            }
            return neighbors;
        } else {
            return null;
        }
    }
}