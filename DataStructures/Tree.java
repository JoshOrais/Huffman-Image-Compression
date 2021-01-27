package DataStructures;

import java.util.ArrayList;

public class Tree {
    private Node root = null;

    public Tree (Node node) {
        root = node;
    }

    public Node[] traverse() {
        ArrayList<Node> nodesList = new ArrayList<Node>();

        nodesList = traverse(nodesList, root);

        Node[] nodes = nodesList.toArray(new Node[nodesList.size()]);

        return nodes;
    }
    
    private ArrayList<Node> traverse(ArrayList<Node> nodesList, Node node) {
        if (node.left != null) {
            nodesList = traverse(nodesList, node.left);
        }

        nodesList.add(node);

        if (node.right != null) {
            nodesList = traverse(nodesList, node.right);
        }

        return nodesList;
    }

    public Node[] traverseLeaves() {
        ArrayList<Node> nodesList = new ArrayList<Node>();

        nodesList = traverseLeaves(nodesList, root, "");

        Node[] nodes = nodesList.toArray(new Node[nodesList.size()]);

        return nodes;
    }
    
    private ArrayList<Node> traverseLeaves(ArrayList<Node> nodesList, Node node, String path) {
        if (node.left != null) {
            nodesList = traverseLeaves(nodesList, node.left, path+"0");
        }

        if (node.value != 0) {
            nodesList.add(node);
            node.path = path;
        }

        if (node.right != null) {
            nodesList = traverseLeaves(nodesList, node.right, path+"1");
        }

        return nodesList;
    }
}