import java.util.*;

public class Main {

    private static Map<Character, Map<Character, Integer>> graph;

    public static void main(String[] args) {

        graph = new HashMap<>();

        List<String> input = new ArrayList<>(Arrays.asList("AB5", "BC4",
                "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

        for (String i : input) {
            char src = i.charAt(0); // one character label
            char trg = i.charAt(1); // one character label
            int w = Character.getNumericValue(i.charAt(2)); // weights are in the range [0, 9]

            graph.putIfAbsent(src, new HashMap<>());
            graph.get(src).put(trg, w);
        }

        System.out.println(graph.toString());

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

    public static void getDistance(String route) {

        getDistanceUtil(route,0, 0);
    }

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

    // Time complexity: O(N^maxStops) in the worst case
    // Note: We can reduce it by using Dynamic Programming
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

    // Time complexity: O(N^exactStops) in the worst case
    // Note: We can reduce it by using Dynamic Programming
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

    public static char minDistance(Map<Character, Boolean> visited, Map<Character, Integer> distances) {
        char res = 'A'; // random value that will be updated according to Dijkstra
        int dist = Integer.MAX_VALUE;

        for (char node : graph.keySet()) {
            if (visited.get(node) == false && distances.get(node) < dist)
                res = node;
        }

        return res;
    }

    // Dijkstra's algorithm for computing shortest path from source to all vertices
    public static int getShortestPath(char source, char target) {
        Map<Character, Boolean> visited = new HashMap<>();
        Map<Character, Integer> distances = new HashMap<>();

        // fill hashmaps with initial values
        for (char node : graph.keySet()) {
            visited.put(node, false);
            distances.put(node, Integer.MAX_VALUE);
        }

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

            // no need to look further
            if (w == target)
                break;
        }

        return distances.get(target);
    }

    // Time complexity: O(N^(maxDistance/minOfAllWeights)) in the worst case
    // Note: We can reduce it by using Dynamic Programming
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
