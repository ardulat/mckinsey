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
}
