package graph;


import java.util.*;

public class Graph_ {

    public static class Node{
        public int value;
        public int in; // 入度
        public int out; // 出度
        public ArrayList<Node> nexts;
        public ArrayList<Edge> edges;

        public Node(int value){
            this.value = value;
            this.in = 0;
            this.out = 0;
            this.nexts = new ArrayList<>();
            this.edges = new ArrayList<>();
        }
    }

    public static class Edge {
        public int weight;
        public Node from;
        public Node to;

        public Edge(int weight, Node from, Node to){
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }

    public static class Graph {
        public HashMap<Integer, Node> nodes;
        public HashSet<Edge> edges;

        public Graph(){
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }

    public static Graph createGraph(int[][] matrix){
        Graph graph = new Graph();
        for(int i = 0; i < matrix.length; i++){
            // 拿到每一条边 matrix[i]
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];
            if (!graph.nodes.containsKey(from)){
                graph.nodes.put(from, new Node(from));
            }
            if(!graph.nodes.containsKey(to)){
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge newEdge = new Edge(weight, fromNode , toNode);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            fromNode.edges.add(newEdge);
            graph.edges.add(newEdge);
        }
        return graph;

    }

    // 宽度优先遍历
    public static void bfs(Node start){
        if(start == null) return;
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.value);
            for(Node next : cur.nexts){
                if(!set.contains(next)){
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

    //深度优先遍历
    public static void dfs(Node start){
        if(start == null) return;
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.add(start);
        set.add(start);
        System.out.println(start.value);
        while(!stack.isEmpty()){
            Node cur = stack.pop();
            for(Node next : cur.nexts){
                if(!set.contains(next)){
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }

    // 拓扑排序
    public static List<Node> topologySort(Graph graph){
        // key: 某个节点   value: 剩余的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 只有剩余入度为0的点，才进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();
        for(Node node : graph.nodes.values()){
            inMap.put(node, node.in);
            if(node.in == 0){
                zeroInQueue.add(node);
            }
        }
        List<Node> result = new ArrayList<>();
        while(!zeroInQueue.isEmpty()){
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for(Node next : cur.nexts){
                inMap.put(next, inMap.get(next) - 1);
                if(inMap.get(next) == 0) zeroInQueue.add(next);
            }
        }
        return result;

    }
    public static void main(String[] args) {
        int[][] matrix = {{1,1,2}, {1, 2, 3}, {1, 3, 4}};
        Graph graph = createGraph(matrix);
        bfs(graph.nodes.get(1));
        dfs(graph.nodes.get(1));
        List<Node> nodes = topologySort(graph);
        for(Node node : nodes){
            System.out.println(node.value);
        }
    }
}
