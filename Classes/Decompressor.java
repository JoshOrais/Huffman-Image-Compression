package Classes;

import DataStructures.*;

import java.io.File;
import java.awt.image.*;
import java.awt.*;

public class Decompressor {

    private BufferedImage recreatedImage;

    public Decompressor(File dataFile, File treeFile) {

        DecompressorFunctions functions = new DecompressorFunctions();

        Node huffmanRoot = functions.readTreeFile(treeFile);

        String bitString = functions.readDataFile(dataFile);

        Dimension dimensions = functions.getDimensions();

        recreatedImage = functions.recreateImage(huffmanRoot, bitString, dimensions);

        System.out.println("\nFILE HAS BEEN DECOMPRESSED!!!");
    }

    public BufferedImage getImage() {
        return recreatedImage;
    }
}