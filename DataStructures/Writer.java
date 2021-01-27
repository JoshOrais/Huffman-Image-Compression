package DataStructures;

import java.io.*;

public class Writer {
    private File dataFile;
    private File treeFile;

    public Writer() {
        try {
            dataFile = new File("COMPRESSED.data");
            treeFile = new File("TREE.txt");

            if (dataFile.exists()) {
                dataFile.delete();
            }
            if (treeFile.exists()) {
                treeFile.delete();
            }

            dataFile.createNewFile();
            treeFile.createNewFile();

		} catch (Exception e) {
			e.printStackTrace();
        }
    }

    public void writeBitString(int height, int width, String string) {

        //test
        // System.out.println("\nReceived BitString: \n" + string);
        //

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(dataFile, true);

            for (int i=3; i>=0; i--) {
                fileOutputStream.write((byte)(height >> (8*i)));
            }
            for (int i=3; i>=0; i--) {
                fileOutputStream.write((byte)(width >> (8*i)));
            }

            int stringLength = string.length();
            int extraLength = stringLength % 8;

            System.out.println("String length: " + stringLength);
            System.out.println("Extra length: " + extraLength);

            //test
            // System.out.print("\nString Passed | Byte Written\n");
            //

            for (int i=0; i<(stringLength-extraLength); i+=8) {
                int num = convertToByte(string.substring(i, i+8));

                //test
                // System.out.print(string.substring(i, i+8) + " | ");
                //

                fileOutputStream.write((byte)(num));

                //test
                // System.out.println(String.format("%8s", Integer.toBinaryString(((byte)(num)) & 0xFF)).replace(' ', '0'));
                //
            }

            String extraString = string.substring(stringLength-extraLength);
            for (int i=0; i<(8-extraLength); i++) {
                extraString += '0';
            }
            int num = convertToByte(extraString);
            fileOutputStream.write((byte)(num));
        }
        catch (Exception e) {
			e.printStackTrace();
        }
        finally {
            try {
                fileOutputStream.close();
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }

    private int convertToByte (String string) {
        int num = 0;

        for (int i=0; i<8; i++) {

            if (string.charAt(i) == '1') {
                num = (num << 1) | 1;
            }
            else {
                num = (num << 1);
            }
        }

        return num;
    }

    public void writeTree(Node root) {
        try {
            FileWriter writer = new FileWriter("TREE.txt", true);
            serialize(root, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serialize(Node node, FileWriter writer) throws java.io.IOException {

        if (node == null) {
            writer.write("\nMARK");
            return;
        }

        writer.write("\n" + Integer.toString(node.value));
        serialize(node.left, writer);
        serialize(node.right, writer);
    }
}