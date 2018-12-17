package com.jmlearning.randomthings;

public class MatrixGraphs {

    public static void main(String args[]) {

        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(10);
        graph.addEdge(1, 2);
        graph.addEdge(1, 5);
        graph.addEdge(2, 5);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);

        System.out.println(graph);
    }
}

class AdjacencyMatrixGraph {

    private int numberOfVertices;
    private int numberOfEdges;
    private int[][] adjacency;

    static final int EDGE_EXIST = 1;
    static final int EDGE_NONE = 0;

    public AdjacencyMatrixGraph(int givenNumberOfVertices) {

        this.setNumberOfVertices(givenNumberOfVertices);
        this.setNumberOfEdges(0);
        this.setAdjacency(new int[givenNumberOfVertices][givenNumberOfVertices]);

        for(int i = 0; i < givenNumberOfVertices; i++) {

            for(int j = 0; j < givenNumberOfVertices; j++) {

                this.adjacency()[i][j] = AdjacencyMatrixGraph.EDGE_NONE;
            }
        }
    }

    private void setNumberOfVertices(int newNumberOfVertices) {

        this.numberOfVertices = newNumberOfVertices;
    }

    public int numberOfVertices() {

        return this.numberOfVertices;
    }

    private void setNumberOfEdges(int newNumberOfEdges) {

        this.numberOfEdges = newNumberOfEdges;
    }

    public int numberOfEdges() {

        return this.numberOfEdges;
    }

    private void setAdjacency(int[][] newAdjacency) {

        this.adjacency = newAdjacency;
    }

    private int[][] adjacency() {

        return this.adjacency;
    }

    private boolean adjacencyOfEdgeDoesExist(int from, int to) {

        return (this.adjacency()[from][to] != AdjacencyMatrixGraph.EDGE_NONE);
    }

    public boolean vertexDoesExist(int aVertex) {

        if(aVertex >= 0 && aVertex < this.numberOfVertices()) {

            return true;
        }
        else {

            return false;
        }
    }

    public boolean edgeDoesExist(int from, int to) {

        if(this.vertexDoesExist(from) && this.vertexDoesExist(to)) {

            return (this.adjacencyOfEdgeDoesExist(from, to));
        }

        return false;
    }

    public boolean addEdge(int from, int to) {

        if(this.vertexDoesExist(from) && this.vertexDoesExist(to)) {

            if(!this.vertexDoesExist(from) && this.vertexDoesExist(to)) {
                this.adjacency()[from][to] = AdjacencyMatrixGraph.EDGE_EXIST;
                this.adjacency()[to][from] = AdjacencyMatrixGraph.EDGE_EXIST;
                this.setNumberOfEdges(this.numberOfEdges() + 1);
                return true;
            }
        }
        
        return false;
    }

    public boolean removeEdge(int from, int to) {

        if(!this.vertexDoesExist(from) || !this.vertexDoesExist(to)) {

            if(this.adjacencyOfEdgeDoesExist(from, to)) {

                this.adjacency()[from][to] = AdjacencyMatrixGraph.EDGE_NONE;
                this.adjacency()[to][from] = AdjacencyMatrixGraph.EDGE_NONE;
                this.setNumberOfEdges(this.numberOfEdges() - 1);
                return true;
            }
        }
        
        return false;
    }

    public String toString() {

        String s = new String();
        s = "    ";

        for(int i = 0; i < this.numberOfVertices(); i++) {

            s = s + String.valueOf(i) + " ";
        }

        s = s + " \n";

        for(int i = 0; i < this.numberOfVertices(); i++) {

            s = s + String.valueOf(i) + " : ";

            for(int j = 0; j < this.numberOfVertices(); j++) {

                s = s + String.valueOf(this.adjacency[i][j]) + " ";
            }

            s = s + "\n";
        }
        
        return s;
    }
}