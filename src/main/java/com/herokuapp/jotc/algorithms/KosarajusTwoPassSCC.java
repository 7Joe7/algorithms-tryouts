package main.java.com.herokuapp.jotc.algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Stack;

/**
 * Created by Joe on 08/11/15 at 01:46.
 * <p/>
 * Requirements: ${REQUIREMENTS}
 */
public class KosarajusTwoPassSCC {
    private static final Stack<Vertex> stack = new Stack<Vertex>();
    private static final int NUMBER_OF_VERTICES = 875714;
    private static final SortedList<Vertex> graph = new SortedList<Vertex>(new Comparator<Vertex>() {
        @Override
        public int compare(Vertex o1, Vertex o2) {
            return o1.order < o2.order ? +1 : o1.order > o2.order ? -1 : 0;
        }
    });

    private static void printTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
    }


    public static void main(String[] args) throws IOException {
        printTime();
        for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
            graph.add(new Vertex(i));
        }
        BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/SCC.txt"));
        String line;
        while((line = br.readLine()) != null) {
            String[] split = line.trim().split("(\\s)+");
            graph.get(Integer.parseInt(split[0]) - 1).edges.add(Integer.parseInt(split[1]) - 1);
            graph.get(Integer.parseInt(split[1]) - 1).reverseEdges.add(Integer.parseInt(split[0]) - 1);
        }
        for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
            depthFirstSearch(graph, stack.push(graph.get(0)), true);
        }
        printTime();
    }

    private static class Vertex {
        private boolean explored = false;
        private final ArrayList<Integer> edges = new ArrayList<Integer>();
        private final ArrayList<Integer> reverseEdges = new ArrayList<Integer>();
        private int order;

        public Vertex(int order) {
            this.order = order;
        }
    }

    public static SortedList<Vertex> depthFirstSearch(SortedList<Vertex> graph, Stack<Vertex> stack, boolean reverse) {
        if (reverse) {
            while (!stack.empty()) {
                for (int i : stack.pop().reverseEdges) {
                    Vertex next = graph.get(i);
                    if (!next.explored) {
                        stack.push(next);
                    }
                    next.explored = true;
                }

            }
        } else {
            for (int i : stack.pop().edges) {

            }
        }
        return graph;
    }
}
