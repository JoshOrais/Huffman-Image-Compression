package DataStructures;

import java.util.ArrayList;

public class Heap {
    private Node[] heapNodes;
    private int heapSize=0;

    public Heap(Node[] nodes){
        heapNodes = new Node[nodes.length+1];
        insert(nodes);
    }

    public Node popTop(){
        Node popped = heapNodes[1];
        heapNodes[1] = null;

        Node lastNode = heapNodes[heapSize];
        // heapNodes[heapSize] = null;
        heapSize--;

        percolateDown(1, lastNode);

        return popped;
    }
    
    public void insert(Node node) {
        heapSize++;
        percolateUp(heapSize, node);
    }

    public int getSize() {
        return heapSize;
    }

    public Node[] traverse() {
        return heapNodes;
    }

    public Node[] getPatterns() {
        return null;
    }

    //----------Private Functions----------

    private void insert(Node[] nodeArray) {
        for (int i=0; i<nodeArray.length; i++) {
            heapSize++;
            percolateUp(i+1, nodeArray[i]);
        }
    }

    private void percolateDown(int i, Node node) {
        int j;

        if (2*i > heapSize) {
            heapNodes[i] = node;
        }

        else if (2*i == heapSize) {
            if (heapNodes[2*i].frequency < node.frequency) {
                heapNodes[i] = heapNodes[2*i];
                heapNodes[2*i] = node;
            }
            else {
                heapNodes[i] = node;
            }
        }

        else if (2*i < heapSize) {
            if (heapNodes[2*i].frequency < heapNodes[(2*i)+1].frequency) {
                j = 2*i;
            }
            else {
                j = (2*i) + 1;
            }

            if (heapNodes[j].frequency < node.frequency) {
                heapNodes[i] = heapNodes[j];
                percolateDown(j, node);
            }
            else {
                heapNodes[i] = node;
            }
        }
    }

    private void percolateUp(int i, Node node) {
        if (i == 1) {
            heapNodes[1] = node;
        }
        
        else {
            if (heapNodes[i/2].frequency < node.frequency) {
                heapNodes[i] = node;
            }
            else {
                heapNodes[i] = heapNodes[i/2];
                percolateUp(i/2, node);
            }
        } 
    }
}