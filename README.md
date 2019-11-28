# Digital Lab

This is a repository dedicated to the McKinsey Digital Lab programming task.

## Getting started

In this problem, I decided to go as simple as I can. Right from the beginning, it was obvious that this is a graph problem. The graph in the problem is a directed weighted graph that can have cycles.

Although I am more familiar with coding in Python and C++ (especially solving algorithmic problems like this one), I decided to code in Java because I have previously coded in it in university. Feel free to clone the repo and import it to your preferred IDE. Or you can access it for now by pulling the image from Docker Hub that I published using the command:

```
docker run -i ardulat/mckinsey
```

## Graph representation

Initially, I wanted to represent the graph in the OOP-like structure using separate classes for `Node` and `Graph` with all their methods. But because of the time constraint, I decided to go simple and describe the structure of the graph using simple hashmap. In Java code it looks as follows:
```
Map<Character, Map<Character, Integer>> graph
```
I map head labels represented as a character (e.g., "A") to adjacent tails also represented as characters mapped to their weights. Therefore, a node "A" will be mapped to "B" with weight 5 from the sample test case as `{A: {B: 5}}`.

I also assumed that node labels will be represented as characters (26 uppercase) and weights are in the range `[0, 9]` because only one character from the input is dedicated to it (though there might be problems with edges of weight 0).

## Tasks 1-5
The tasks can be solved using simple DFS traversing/parsing through the path provided by the input. If there are no adjacent nodes at some point in the path, we will simply output `NO SUCH ROUTE!`. Parsing the input path is also made easy using the `pos` pointer indicating the position of the character label of a node in the input string. At the end of the path (if such exists), we will simply output the distance of the path in total length/weight using the counter for the total distance.

**Time complexity**: `O(V+E)` where V - number of vertices, E - number of edges

**Space complexity**: `O(V+E)` dedicated to recursion tree

# Tasks 6-7, 10
The tasks use the same approach and thus, I decided to describe it in this order. The approach is based on backtracking. We are trying all the different trips/routes being provided by source and target nodes (assuming nodes there can't be pointing to themselves like in some common graph problems). So far, we will try different combinations of trips and assess whether our constraint is satisfied. Constraints are the maximum or an exact number of edges or total distance. Thus, we have to track the edges/distance covered so far. If one of our constraints fail, we backtrack.

Again, I decided to go as simple as I can. But the answer to the well-known question "Could we do better?" is yes, we can by using Dynamic Programming (DP). DP will be used here to memoize different routes that we already tried and in case we come there again, we will simply use the previously computed answer (yes, there is such path or no, there is not). DP will ensure that we do not duplicate our computations that can be well represented in a recursion tree.

**Time complexity**: `O(N^k)` where `k` - number of maximum/exact stops or maximum distance/minimum of all weights in the worst case

**Space complexity**: `O(N^k)`

# Tasks 8-9
There is a number of different algorithms for solving the shortest path problem (mostly using the Greedy approach). But once again, I decided to go as simple as I can and implemented Dijkstra's shortest path algorithm that computes the shortest path distance from the source to all nodes. It is important to note that Dijkstra's algorithm will work only on the positive weights of edges in the graph (otherwise, we would implement Bellman-Ford or Floyd-Warshall algorithms). So, given the source vertex and the target vertex, we will find the shortest path distance between them.

One modification needed for Dijkstra's shortest path algorithm for this problem is the starting setup for initiating the algorithms. Classically, Dijkstra's algorithm starts with initiating the shortest distance to the source itself as zero and starts the loop to compute all distances (initially set to +INF or `MAX_INT` in C++ syntax). As we see from the 9-th task, we can't do that (because it will output the wrong result) and thus, I decided to modify the initial setting as computing the distance to adjacent nodes to the source node (simply taking their weights) and computing the rest of distances.

**Time complexity**: `O(V^2)` because it is the naive implementation of Dijkstra's algorithms without any usage of heap/priority queue and the inner loop will find the shortest distance in `O(N)` time

**Space complexity**: `O(V)` dedicated to `distances` and `visited` hashmaps (which is in fact `O(26)` because we can have a maximum of 26 uppercase letters indicating the labels of the graph nodes)

## Bonus
It was a great challenge to configure AWS EKS using Terraform. Starting with zero knowledge of what Kubernetes is, I have dedicated my time to learn it and further dived deep into Terraform configurations. I wish I had more time to understand how infrastructure as code works but for this getting started task I decided to follow the tutorial published by Tensult: [Guide To Setup Kubernetes In AWS EKS Using Terraform And Deploy Sample Applications](https://medium.com/tensult/guide-to-setup-kubernetes-in-aws-eks-using-terraform-and-deploy-sample-applications-ee8c45e425ca).

## Final thoughts

I found the problem very interesting and hilarious at some point. The tasks with input `source = target` where we had to find a cycled path to itself were a bit challenging because they required some modifications to the algorithms. Writing algorithmic problems in Java was also challenging enough because I am very used to solving Leetcode problems in C++ and comfortable use STL. Other than that, it was a great experience solving the problem and I learned a lot during Bonus problem implementation. I was familiar only with Docker and have learned a lot about Kubernetes during this challenge. Thank you a lot for taking your time to consider my application and feel free to pin me in case you have any questions regarding the code or anything else through this email: <anuar.maratkhan@nu.edu.kz>
