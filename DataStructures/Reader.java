package DataStructures;

import java.io.*;
import java.awt.*; 
import java.awt.image.*;

public class Reader {

    public Node readTreeFile(File treeFile) {

        Node huffmanRoot = null;

        try {
            FileReader fileReader = new FileReader(treeFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            bufferedReader.readLine();
            huffmanRoot = deserialize(bufferedReader);

            fileReader.close();  
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return huffmanRoot;
    }

    private Node deserialize(BufferedReader bufferedReader) throws java.io.IOException {
        String line = bufferedReader.readLine();

        if (line == null) {
            return null;
        }

        //test
        // System.out.println(line);
        //

        if (line.equals("MARK")) {
            return null;
        }

        Node node = new Node(Integer.parseInt(line));
        node.left = deserialize(bufferedReader);
        node.right = deserialize(bufferedReader);
        
        return node;
    }

    public String readDataFile(File file) {

        FileInputStream fileInputStream = null;
        StringBuilder bitString = new StringBuilder();

        try{
            fileInputStream = new FileInputStream(file);

            int fileLength = (int)(file.length());
            byte[] byteArray = new byte[fileLength];
            fileInputStream.read(byteArray);

            for (int i=0; i<fileLength; i++) {
                bitString.append(String.format("%8s", Integer.toBinaryString(byteArray[i] & 0xFF)).replace(' ', '0'));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileInputStream.close();
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }

        return bitString.toString();
    }
}
