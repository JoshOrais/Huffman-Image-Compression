package DataStructures;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class CompressFunctions {

    private Node huffmanRoot;
    
    public int[][] readImage(File file) {

        System.out.println("\nReading Image ...");

        BufferedImage image = null;
        
        try{
            image = ImageIO.read(file);
        }
        catch(IOException e){
            System.out.println(e);
        }

        int height = image.getHeight();
        int width = image.getWidth();
        int[][] pixelArray = new int[height][width]; 

        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                pixelArray[i][j] = image.getRGB(j,i);
            }
        }

        System.out.println("Image size: " + height + " x " + width);

        return pixelArray;
    }

    public Node[] getUnique(int[][] pixelArray) {

        System.out.println("\nReading Unique Pixels ...");

        BinarySearchTree tree = new BinarySearchTree();

        for (int i=0; i<pixelArray.length; i++) {
            for (int j=0; j<pixelArray[0].length; j++) {
                Node newNode = new Node(pixelArray[i][j]);
                tree.insert(newNode);
            }
        }

        Node[] uniquePixels = tree.traverse();

        for (int i=0; i<uniquePixels.length; i++) {
            uniquePixels[i].left = null;
            uniquePixels[i].right = null;
        }

        System.out.println("Unique pixels: " + uniquePixels.length);

        //test
        // System.out.println("Unique Nodes: ");
        // display(uniquePixels);
        //end

        return uniquePixels;
    }

    public Node[] generateHuffman(Node[] uniquePixels) {

        System.out.println("\nGenerating Huffman Tree ...");

        Heap heap = new Heap(uniquePixels);

        while (heap.getSize() > 1) {
            Node nodeA = heap.popTop();
            Node nodeB = heap.popTop();
            
            Node newNode = new Node();
            newNode.frequency = nodeA.frequency + nodeB.frequency;
            newNode.left = nodeA;
            newNode.right = nodeB;
            heap.insert(newNode);
        }

        Node finalNode = heap.popTop();

        Tree tree = new Tree(finalNode);

        //test
        // Node[] nodeArray = tree.traverse();
        // System.out.println("Tree Size: " + nodeArray.length);
        // System.out.println("Huffman Tree: ");
        // display(nodeArray);
        //end

        huffmanRoot = finalNode;

        Node[] tempLeaves = tree.traverseLeaves();

        Node[] leaves = new Node[tempLeaves.length];
        for (int i=0; i<tempLeaves.length; i++) {
            Node tempNode = new Node();
            tempNode.value = tempLeaves[i].value;
            tempNode.frequency = tempLeaves[i].frequency;
            tempNode.path = tempLeaves[i].path;
            leaves[i] = tempNode;
        }

        System.out.println("Root Frequency: " + finalNode.frequency);
        System.out.println("Leaves Size: " + leaves.length);

        //test
        // System.out.println("Leaves:");
        // display(leaves);
        //end
        
        return leaves;
    }

    public Node getHuffmanRoot() {
        return huffmanRoot;
    }
    
    public void writeToFile(int[][] pixelArray, Node[] leaves, Node root) {
        System.out.println("\nPutting leaves into BST ... ");

        BinarySearchTree tree = new BinarySearchTree();

        for (int i=0; i<leaves.length; i++) {
            leaves[i].left = null;
            leaves[i].left = null;
            tree.insert(leaves[i]);
        }

        System.out.println("BST Done");

        //test
        // System.out.println("Putting Unique Pixels in Binary Search Tree");
        // System.out.println("Traversing Binary Search Tree");
        // display(tree.traverse());
        //end

        StringBuilder bitString = new StringBuilder();

        for (int i=0; i<pixelArray.length; i++) {
            for (int j=0; j<pixelArray[0].length; j++) {
                Node node = tree.locate(pixelArray[i][j]);
                bitString.append(node.path);
            }
        }

        System.out.println("Generating bitstring done");
        System.out.println("Bit String Length: " + bitString.length());

        //test
        // System.out.println("\nCompressed Bitstring: \n" + bitString.toString());
        //end

        System.out.println("\nWriting File ...");
        Writer writer = new Writer();
        writer.writeBitString(pixelArray.length, pixelArray[0].length, bitString.toString());

        System.out.println("\nWriting Tree ...");
        writer.writeTree(root);
        System.out.println("Done Writing Tree");
    }

    //----------Private----------

    private void display(Node[] nodeArray) {
        for (int i=0; i< nodeArray.length; i++) {
            System.out.println("          " + nodeArray[i].value + " | " + nodeArray[i].frequency + " | " + nodeArray[i].path);
        }
    }
}