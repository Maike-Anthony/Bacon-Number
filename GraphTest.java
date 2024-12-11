import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class GraphTest {
    @Test
    void testAddEdge() {
        Vertex first = new Vertex("First");
        Vertex second = new Vertex("Second");
        Graph graph = new Graph();
        graph.addVertex(first);
        graph.addVertex(second);
        graph.addEdge(first, second, "FirstEdge");
        LinkedList<Edge> edges = graph.getEdges(first);
        Vertex destination = edges.get(0).destination;
        assertTrue(destination.equals(second));
    }

    @Test
    void testGetEdges() {
        Vertex first = new Vertex("First");
        Vertex second = new Vertex("Second");
        Graph graph = new Graph();
        graph.addVertex(first);
        graph.addVertex(second);
        graph.addEdge(first, second, "FirstEdge");
        LinkedList<Edge> edges = graph.getEdges(first);
        Vertex destination = edges.get(0).destination;
        assertTrue(destination.equals(second));
    }

    @Test
    void testAddVertex() {
        Vertex first = new Vertex("First");
        Graph graph = new Graph();
        graph.addVertex(first);
        Vertex found = graph.getVertex("First");
        assertTrue(found.equals(first));
    }

    @Test
    void testGetVertex() {
        Vertex first = new Vertex("First");
        Graph graph = new Graph();
        graph.addVertex(first);
        Vertex found = graph.getVertex("First");
        assertTrue(found.equals(first));
    }

    @Test
    void testGetNeighbors() {
        Vertex first = new Vertex("First");
        Vertex second = new Vertex("Second");
        Vertex third = new Vertex("Third");
        Graph graph = new Graph();
        graph.addVertex(first);
        graph.addVertex(second);
        graph.addVertex(third);
        graph.addEdge(first, second, "FirstEdge");
        graph.addEdge(first, third, "SecondEdge");
        LinkedList<Vertex> neighbors = graph.getNeighbors(first);
        assertEquals(second, neighbors.get(0));
        assertEquals(third, neighbors.get(1));
    }
}
