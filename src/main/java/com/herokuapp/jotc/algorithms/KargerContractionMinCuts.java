package main.java.com.herokuapp.jotc.algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Joe on 05/11/15 at 20:20.
 * <p/>
 * Requirements: ${REQUIREMENTS}
 */
public class KargerContractionMinCuts {

    private static class Graph {
        private UnionFind vertices;
        private final List<Edge> edges = new ArrayList<Edge>();

        public Graph(int[][] array) {
            vertices = new UnionFind(array.length);
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (i < array[i][j]) {
                        edges.add(new Edge(i, array[i][j]));
                    }
                }
            }
        }
    }

    private static class Edge {
        private int end1;
        private int end2;

        public Edge(int end1, int end2) {
            this.end1 = end1;
            this.end2 = end2;
        }
    }

    public static int minCut(Graph gr) {
        Random rnd = new Random();

        while(gr.vertices.count() > 2) {
            Edge edge = gr.edges.remove(rnd.nextInt(gr.edges.size()));
            gr.vertices.union(edge.end1, edge.end2);
        }
        for (int i = 0; i < gr.edges.size(); i++) {
            Edge e = gr.edges.get(i);
            if (gr.vertices.connected(e.end1, e.end2)) {
                gr.edges.remove(i);
                i--;
            }
        }
        return gr.edges.size();
    }

    public static int[][] getArray(String relPath) throws IOException {
        Map<Integer, List<Integer>> vertices = new LinkedHashMap<Integer, List<Integer>>();
        BufferedReader br = new BufferedReader(new FileReader(relPath));
        String line;
        while((line = br.readLine()) != null) {
            String[] split = line.trim().split("(\\s)+");
            List<Integer> adjList = new ArrayList<Integer>();
            for(int i = 1; i < split.length; i++) {
                adjList.add(Integer.parseInt(split[i]) - 1);
            }
            vertices.put(Integer.parseInt(split[0]) - 1, adjList);
        }
        int[][] array = new int[vertices.size()][];
        for (Map.Entry<Integer, List<Integer>> entry : vertices.entrySet() ) {
            List<Integer> adjList = entry.getValue();
            int[] adj = new int[adjList.size()];
            for( int i = 0; i < adj.length; i++ ) {
                adj[i] = adjList.get( i );
            }
            array[entry.getKey()] = adj;
        }
        return array;
    }

    /**
     * @param args
     */
    public static void main( String[] args ) throws IOException {
        int[][] arr = getArray("./src/main/resources/kargerMinCut.txt");

        int min = arr.length;
        int iter = arr.length * arr.length;

        for(int i = 0; i < iter; i++) {
            min = Math.min(min, minCut(new Graph(arr)));
        }
        System.out.println("Min: " + min);
    }
}