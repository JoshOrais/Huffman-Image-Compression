package Classes;

import DataStructures.*;

import java.io.File;

public class Compressor {

    public Node huffmanRoot;

    public Compressor(File file) {

        CompressFunctions functions = new CompressFunctions();

        int[][] pixelArray = functions.readImage(file);

        Node[] uniquePixels = functions.getUnique(pixelArray);

        Node[] leavesWithPath = functions.generateHuffman(uniquePixels);
        huffmanRoot = functions.getHuffmanRoot();

        functions.writeToFile(pixelArray, leavesWithPath, huffmanRoot);

        System.out.println("\nFILE HAS BEEN COMPRESSED!!!");
    }
}