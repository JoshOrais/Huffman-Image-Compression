package DataStructures;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.lang.Math; 

public class DecompressorFunctions {

    private Dimension dimensions;

    public Node readTreeFile(File file) {
        System.out.println("\nReading Tree File ...");
        System.out.println("File is: " + file.getName());

        Reader reader = new Reader();
        Node huffmanRoot = reader.readTreeFile(file);

        System.out.println("Root is : " + huffmanRoot.value);

        return huffmanRoot;
    }

    public String readDataFile(File file) {
        System.out.println("\nReading Data File ...");
        System.out.println("File is: " + file.getName()); 
        System.out.println("File length: " + (int)(file.length()));

        Reader reader = new Reader();
        String bitString = reader.readDataFile(file);

        String dimensionString = bitString.substring(0,64);
        String dataString = bitString.substring(64);

        setDimensions(dimensionString);

        //test
        // System.out.println("\nResult: \n" + dimensionString + "\n" + dataString);
        //
        
        System.out.println("Bit String Length : " + dimensionString.length() + " | " + dataString.length());

        return dataString;
    }

    private void setDimensions(String string) {
        String heightString = string.substring(0,32);
        String widthString = string.substring(32);

        int height = 0;
        int width = 0;

        for (int i=0; i<32; i++) {
            if (heightString.charAt(i) == '1') {
                height += Math.pow(2, (31-i));
            }
            if (widthString.charAt(i) == '1') {
                width += Math.pow(2, (31-i));
            }
        }

        dimensions = new Dimension(width, height);
    }

    public Dimension getDimensions() {
        return dimensions;
    }

    public BufferedImage recreateImage(Node huffmanRoot, String data, Dimension dimensions) {

        System.out.println("\nRecreating Image ... ");

        int height = (int)dimensions.getHeight();
        int width = (int)dimensions.getWidth();

        System.out.println("Image Dimension: " + height + " x " + width);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int charPointer = 0;
        Node node = huffmanRoot;

        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {

                while (node.left != null && node.right != null) {
                    if (data.charAt(charPointer) == '0') {
                        node = node.left;
                    }
                    else if (data.charAt(charPointer) == '1') {
                        node = node.right;
                    }
                    charPointer++;
                }

                image.setRGB(j, i, node.value);
                node = huffmanRoot;
            }
        }

        return image;
    }
}