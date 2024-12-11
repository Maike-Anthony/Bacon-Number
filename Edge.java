/**
 * A class to represent the edges of a graph.
 * @author Maike Anthony dos Santos Silva
 */
public class Edge {
    /**The source vertex of the edge*/
    Vertex source;

    /**The destination vertex of the edge */
    Vertex destination;

    /**The label of the edge */
    String label;

    /**
     * Class constructor.
     * @param source the source vertex of the edge.
     * @param destination the destination vertex of the edge.
     * @param label the name of the edge.
     */
    public Edge(Vertex source, Vertex destination, String label) {
        this.source = source;
        this.destination = destination;
        this.label = label;
    }
}