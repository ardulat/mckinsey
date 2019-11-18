import java.util.*;

public class Main {

    private static Map<Character, Map<Character, Integer>> graph;

    public static void main(String[] args) {

        graph = new HashMap<>();

        // Sample input: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
        System.out.println("Please provide the input as in sample testcase (e.g. \"AB5, BC4, CD8\"): ");
        Scanner reader = new Scanner(System.in);

        String inputString = reader.nextLine();
        List<String> input = new ArrayList<>(Arrays.asList(inputString.split(", ")));

        for (String i : input) {

            char src = i.charAt(0); // one character label
            char trg = i.charAt(1); // one character label
            int w = Character.getNumericValue(i.charAt(2)); // weights are in the range [0, 9]

            graph.putIfAbsent(src, new HashMap<>());
            graph.get(src).put(trg, w);
        }

        System.out.print("OUTPUT #1: ");
        String route1 = "A-B-C";
        getDistance(route1);

        System.out.print("OUTPUT #2: ");
        String route2 = "A-D";
        getDistance(route2);

        System.out.print("OUTPUT #3: ");
        String route3 = "A-D-C";
        getDistance(route3);

        System.out.print("OUTPUT #4: ");
        String route4 = "A-E-B-C-D";
        getDistance(route4);

        System.out.print("OUTPUT #5: ");
        String route5 = "A-E-D";
        getDistance(route5);

        System.out.println("OUTPUT #6: " +
                getTripsCountMax('C', 'C', 0, 3));

        System.out.println("OUTPUT #7: " +
                getTripsCountExact('A', 'C', 4));

        System.out.println("OUTPUT #8: " +
                getShortestPath('A', 'C'));

        System.out.println("OUTPUT #9: " +
                getShortestPath('B', 'B'));

        System.out.println("OUTPUT #10: " +
                getRoutesCountMax('C', 'C', 0, 30));

    }

    
    /**
     * Main function to get the total distance of the route.
     * The output is printed to the console as a part of the function call.
     * @param route Route represented as a String to follow in the graph
     */

    public static void getDistance(String route) {

        getDistanceUtil(route,0, 0);
    }


    /**
     * Recursive utility function that is being used by main function to get the
     * total distance of the given route. The output is printed to the console as
     * a part of the function call.
     * @param route Route represented as a String to follow in the graph
     * @param pos Position pointer indicating current graph node label to be traversed
     * @param dist Distance counter to be printed if the path exists
     */

    public static void getDistanceUtil(String route, int pos, int dist) {
        char src = route.charAt(pos++);
        pos++; // skip "-"

        if (pos > route.length()) { // out of bounds
            System.out.println(dist);
            return;
        }

        char next = route.charAt(pos);

        if (graph.containsKey(src)) {
            Map<Character, Integer> adj = graph.get(src);

            if (adj.containsKey(next)) {
                dist += adj.get(next);
                getDistanceUtil(route, pos, dist);
            } else
                System.out.println("NO SUCH ROUTE!");
        }
    }


    /**
     * Recursive function following backtracking approach to get number of trips with
     * maximum number of stops. The function stops/backtracks when the constraint is not satisfied.
     * @param source Source graph node to start the route
     * @param target Target graph node to end the route
     * @param currentStops Counter for tracking number of stops covered so far
     * @param maxStops Maximum number of stops allowed by the algorithm
     * @return Number of trips in the graph that satisfy the requirement
     */

    public static int getTripsCountMax(char source, char target, int currentStops, int maxStops) {
        // Base cases
        if (currentStops > maxStops)
            return 0;

        int count = 0;
        if (source == target && currentStops != 0)
            count++;

        if (!graph.containsKey(source))
            return 0;

        Map<Character, Integer> adjacent = graph.get(source);

        for (char adj : adjacent.keySet())
            count += getTripsCountMax(adj, target, currentStops+1, maxStops);

        return count;
    }


    /**
     * Recursive function following backtracking approach to get number of trips with
     * exact number of stops. The function halts when the constraint is not satisfied.
     * @param source Source graph node to start the route
     * @param target Target graph node to end the route
     * @param exactStops Exact number of stops being targeted by the algorithm
     * @return Number of trips that satisfy the requirement
     */

    public static int getTripsCountExact(char source, char target, int exactStops) {
        // Base cases
        if (exactStops == 0 && source == target)
            return 1;
        if (exactStops <= 0) // when exactStops = 0, it means source != target
            return 0;

        if (!graph.containsKey(source))
            return 0;

        Map<Character, Integer> adjacent = graph.get(source); // assuming that source exists in input

        int count = 0;

        for (char adj : adjacent.keySet())
            count += getTripsCountExact(adj, target, exactStops-1);

        return count;
    }


    /**
     * Simple utility function used by Dijkstra's shortest path algorithm to find
     * next node to visited based on the shortest unvisited distance node in linear time.
     * Can be optimized to find the next node in logarithmic time using heap data structure.
     * @param visited Hashmap of visited/unvisited nodes to track the nodes the algorithm
     *                covered so far
     * @param distances Hashmap of distances to the nodes from the source (initially set to +INF)
     * @return Graph node label to visit next in Dijkstra's algorithm
     */

    public static char minDistance(Map<Character, Boolean> visited, Map<Character, Integer> distances) {
        char res = 'A'; // random value that will be updated according to Dijkstra
        int dist = Integer.MAX_VALUE;

        for (char node : graph.keySet()) {
            if (visited.get(node) == false && distances.get(node) < dist)
                res = node;
        }

        return res;
    }


    /**
     * Dijkstra's algorithm for finding shortest path distance from source node to target node
     * of the graph. The algorithm finds all distances from source node to the rest nodes of
     * the graph (initially set to +INF).
     * @param source Source node from where to start shortest path
     * @param target Target node to which find the shortest path distance
     * @return Shortest path distance from source to target node
     */

    public static int getShortestPath(char source, char target) {
        Map<Character, Boolean> visited = new HashMap<>();
        Map<Character, Integer> distances = new HashMap<>();

        // fill hashmaps with initial values
        for (char node : graph.keySet()) {
            visited.put(node, false);
            distances.put(node, Integer.MAX_VALUE);
        }

        // initial setting for dijkstra's shortest path algorithm
        Map<Character, Integer> sourceAdjacent = graph.get(source);
        for (char node : sourceAdjacent.keySet()) {
            distances.put(node, sourceAdjacent.get(node));
        }

        for (int i = 0; i < graph.size(); i++) { // while all are not visited
            char w = minDistance(visited, distances);

            visited.put(w, true); // update

            Map<Character, Integer> adjacent = graph.get(w);

            for (char adj : adjacent.keySet()) {
                // for sums that are greater than MAX_INT, integer overflows (results in negative value)
                long distW = distances.get(w);
                long distAdj = adjacent.get(adj);

                // Dijkstra's greedy criterion
                if (distW + distAdj < distances.get(adj))
                    distances.put(adj, distances.get(w) + adjacent.get(adj));
            }
        }

        return distances.get(target);
    }


    /**
     * Recursive function following backtracking approach to get number of trips with
     * maximum distance along the path. The function halts when the constraint is not satisfied.
     * @param source Source graph node to start the route
     * @param target Target graph node to end the route
     * @param currentDistance Counter for tracking the distance covered so far
     * @param maxDistance Maximum distance allowed by the algorithm
     * @return Number of trips in the graph that satisfy the requirement
     */

    public static int getRoutesCountMax(char source, char target, int currentDistance, int maxDistance) {
        // Base cases
        if (currentDistance >= maxDistance) // strictly less than (by definition)
            return 0;

        int count = 0;
        if (source == target && currentDistance != 0)
            count++;

        if (!graph.containsKey(source))
            return 0;

        Map<Character, Integer> adjacent = graph.get(source);

        for (char adj : adjacent.keySet())
            count += getRoutesCountMax(adj, target, currentDistance + adjacent.get(adj), maxDistance);

        return count;
    }
}
