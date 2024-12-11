import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to create a graph for bacon number.
 */
public class BaconNumber {
    /**The graph that will contain all actors and titles. */
    private Graph graph;
    /**The set of actors. */
    Set<String> actors;
    /**The set of titles. */
    Set<String> titles;
    /**The map that stores a list of titles per actor. */
    Map<String, List<String>> titlesOfActor;
    /**The map that stores a list of actors per title. */
    Map<String, List<String>> ActorsOfTitle;

    /**
     * Class constructor
     * @param file the file to be read.
     */
    public BaconNumber(String filename) {
        this.graph = new Graph();
        this.actors = new HashSet<String>();
        this.titles = new HashSet<>();
        this.titlesOfActor = new HashMap<>();
        this.ActorsOfTitle = new HashMap<>();
        this.createMaps(filename);
        this.createGraph();
    }

    /**
     * The class that reads the file and puts the information in the instance variables.
     * @param file the file to be read.
     */
    private void createMaps(String filename) {
        Scanner scanner = null;
        try {
            File file = new File(filename);
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(-1);
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split("\t");
            String name = split[0];
            String title = split[1];
            this.actors.add(name);
            this.titles.add(title);
            this.addActorsOfTitle(name, title);
            this.addTitlesOfActor(name, title);
        }
        scanner.close();
    }

    /**
     * Helper method to add an actor to the list of actors of a given title.
     * @param name the name of the actor.
     * @param title the name of the title.
     */
    private void addActorsOfTitle(String name, String title) {
        if (!this.ActorsOfTitle.containsKey(title)) {
            List<String> actorsList = new ArrayList<>();
            actorsList.add(name);
            this.ActorsOfTitle.put(title, actorsList);
        } else if (!this.ActorsOfTitle.get(title).contains(name)) {
            this.ActorsOfTitle.get(title).add(name);
        }
    }

    /**
     * Helper method to add a title to the list of titles of a given actor.
     * @param name the name of the actor.
     * @param title the name of the title.
     */
    private void addTitlesOfActor(String name, String title) {
        if (!this.titlesOfActor.containsKey(name)) {
            List<String> titlesList = new ArrayList<>();
            titlesList.add(title);
            this.titlesOfActor.put(name, titlesList);
        } else if (!this.titlesOfActor.get(name).contains(title)) {
            this.titlesOfActor.get(name).add(title);
        }
    }

    /**
     * Private method to create the directed graph.
     */
    private void createGraph() {
        for (String actor : this.actors) {
            Vertex actorVertex = new Vertex(actor);
            this.graph.addVertex(actorVertex);
        }
        for (String actor : this.actors) {
            Vertex actorVertex = this.graph.getVertex(actor);
            for (String title : this.titlesOfActor.get(actor)) {
                for (String coActor : this.ActorsOfTitle.get(title)) {
                    if (!coActor.equals(actor)) {
                        Vertex coActorVertex = this.graph.getVertex(coActor);
                        this.graph.addEdge(actorVertex, coActorVertex, title);
                    }
                }
            }
        }
    }

    /**
     * Method to find shortest path betwen center and goal vertices.
     * @param center the initial vertex.
     * @param goal the goal vertex.
     * @return the shortest path from the initial vertex to the goal vertex.
     */
    public List<String> findPath(Vertex center, Vertex goal) {
        Queue<Vertex> frontier = new LinkedList<Vertex>();
        Set<Vertex> frontierSet = new HashSet<Vertex>();
        Set<Vertex> explored = new HashSet<Vertex>();
        HashMap<Vertex, Edge> pathMap = new HashMap<>();
        frontier.add(center);
        frontierSet.add(center);
        while (!frontier.isEmpty()) {
            Vertex v = frontier.poll();
            frontierSet.remove(v);
            explored.add(v);
            for (Edge edge: this.graph.getEdges(v)) {
                if (!pathMap.containsKey(edge.destination)) {
                    Vertex neighbor = edge.destination;
                    pathMap.put(neighbor, edge);
                    if (neighbor.equals(goal)) {
                        return createPath(center, goal, pathMap);
                    } else if (!(explored.contains(neighbor) || frontierSet.contains(neighbor))) {
                        frontier.add(neighbor);
                        frontierSet.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Helper method to create a path.
     * @param center the center vertex.
     * @param goal the goal vertex.
     * @param pathMap the map of vertex and edges.
     * @return the path from the goal vertex to the center vertex.
     */
    private LinkedList<String> createPath(Vertex center, Vertex goal, HashMap<Vertex, Edge> pathMap) {
        LinkedList<String> reversePath = new LinkedList<>();
        Vertex cur = goal;
        reversePath.add(goal.name);
        while (!cur.equals(center)) {
            Edge edge = pathMap.get(cur);
            String edgeLabel = edge.label;
            reversePath.add(edgeLabel);
            cur = edge.source;
            reversePath.add(cur.name);
        }
        return reversePath;
    }

    /**
     * Method to print a path.
     * @param path the path from the goal vertex to the center vertex.
     */
    public void printPath(List<String> path) {
        if (path == null) {
            System.out.println("No path found.");
            return;
        }
        String printee = "";
        for (int i = 0; i < path.size() - 1; i++) {
            printee = printee + path.get(i) + " -> ";
        }
        printee = printee + path.get(path.size() - 1);
        System.out.println(printee);
    }

    /**
     * Method to search and print the path given two actors.
     * @param centerActor the center actor's name.
     * @param goalActor the goal actor's name.
     */
    public void searchGraph(String centerActor, String goalActor) {
        Vertex centerActorVertex = this.graph.getVertex(centerActor);
        if (centerActorVertex == null) {
            System.out.println("Center actor was not found.");
            return;
        }
        Vertex goalActorVertex = this.graph.getVertex(goalActor);
        if (goalActorVertex == null) {
            System.out.println("Goal actor was not found.");
            return;
        }
        List<String> path = this.findPath(centerActorVertex, goalActorVertex);
        this.printPath(path);
    }

    /**
     * Main part of the program that creates and searches the graph.
     * @param args the command line arguments are the filename, the goal actor's name, and the center actor's name (which is Kevin Bacon by default).
     */
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            System.out.println("There must be two or three arguments after the program name.");
            System.out.println("Use: program filename goalActor centerActor");
            System.exit(-1);
        }
        BaconNumber baconNumber = new BaconNumber(args[0]);
        if (args.length == 2) {
            baconNumber.searchGraph("Kevin Bacon", args[1]);
        } else {
            baconNumber.searchGraph(args[2], args[1]);
        }
    }

    /*public static void main(String[] args) {
        BaconNumber baconNumber = new BaconNumber("imdb_small.txt");
        HashSet<Vertex> vertices = new HashSet<>();
        HashSet<Edge> edges = new HashSet<>();
        for (String actor : baconNumber.actors) {
            Vertex actorVertex = baconNumber.graph.getVertex(actor);
            vertices.add(actorVertex);
            for (Edge edge : baconNumber.graph.getEdges(actorVertex)) {
                edges.add(edge);
            }
        }
        System.out.println("The number of vertices is: " + vertices.size());
        System.out.println("The number of edges is: " + edges.size());
    }
    */
}