import java.util.*;

public class Main {

    private static Map<Character, Map<Character, Integer>> graph;

    public static void main(String[] args) {

        graph = new HashMap<>();

        List<String> input = new ArrayList<>(Arrays.asList("AB5", "BC4",
                "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"));

        for (String i : input) {
            char src = i.charAt(0);
            char trg = i.charAt(1);
            int w = Character.getNumericValue(i.charAt(2));

            graph.putIfAbsent(src, new HashMap<>());
            graph.get(src).put(trg, w);
        }

        System.out.println(graph.toString());

        String route1 = "A-B-C";
        System.out.print("The distance of the route " + route1 + ": ");
        getDistance(route1);

        String route2 = "A-D";
        System.out.print("The distance of the route " + route2 + ": ");
        getDistance(route2);

        String route3 = "A-D-C";
        System.out.print("The distance of the route " + route3 + ": ");
        getDistance(route3);

        String route4 = "A-E-B-C-D";
        System.out.print("The distance of the route " + route4 + ": ");
        getDistance(route4);

        String route5 = "A-E-D";
        System.out.print("The distance of the route " + route5 + ": ");
        getDistance(route5);

        System.out.println("The number of trips starting at C and ending at C with a maximum of 3 stops: " +
                getTripsCountMax('C', 'C', 0, 3));

        System.out.println("The number of trips starting at A and ending at C with exactly 4 stops: " +
                getTripsCountExact('A', 'C', 4));

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
            } else {
                System.out.println("NO SUCH ROUTE!");
                return;
            }
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
}
