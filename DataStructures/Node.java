package DataStructures;

public class Node{
    public Node left = null;
    public Node right = null;

    public int value = 0;
    public int frequency = 0;

    public String path = "";

    public Node(int value){
        this.value = value;
        frequency = 1;
    }
    public Node(){
    }
}