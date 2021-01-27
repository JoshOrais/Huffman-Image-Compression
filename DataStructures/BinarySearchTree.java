package DataStructures;

import java.util.ArrayList;

public class BinarySearchTree {
    public Node root = null;

    public void insert(Node node) {
        if (root == null) {
            root = node;
        }
        else {
            insert(root, node);
        }
    }

    private void insert(Node parent, Node newNode) {
        if (newNode.value == parent.value) {
            parent.frequency++;
        }
        else if (newNode.value < parent.value) {
            if (parent.left == null) {
                parent.left = newNode;
            }
            else {
                insert(parent.left, newNode);
            }
        }
        else if (newNode.value > parent.value) {
            if (parent.right == null) {
                parent.right = newNode;
            }
            else {
                insert(parent.right, newNode);
            }
        }
    }

    public Node[] traverse() {
        ArrayList<Node> nodesList = new ArrayList<Node>();

        nodesList = traverse(nodesList, root);

        Node[] nodes = nodesList.toArray(new Node[nodesList.size()]);
        return nodes;
    }

    public ArrayList<Node> traverse(ArrayList<Node> nodesList, Node node) {
        if (node.left != null) {
            nodesList = traverse(nodesList, node.left);
        }

        nodesList.add(node);

        if (node.right != null) {
            nodesList = traverse(nodesList, node.right);
        }

        return nodesList;
    }

    public Node locate (int value) {
        return locate(value, root);
    }

    private Node locate (int value, Node reference) {
        if ( value == reference.value) {
            return reference;
        }
        else if (value < reference.value) {
            return locate(value, reference.left);
        }
        else {
            return locate(value, reference.right);
        }
    }
}