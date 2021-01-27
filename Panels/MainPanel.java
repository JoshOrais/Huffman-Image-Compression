package Panels;

import Classes.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.*;
import java.awt.image.*;
import javax.imageio.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainPanel implements ActionListener {
    private JFrame frame;
    private JPanel compressorPanel;
    private JPanel decompressorPanel;
    private JLabel label1;
    private JLabel label2;
    private JScrollPane compressScrollPane;
    private JScrollPane decompressScrollPane;
    private JLabel fileSize;
    private JLabel compressedSize;
    private JLabel status;
    private JButton selectFile;
    private JButton compress;
    private JButton decompress;
    private JButton exit;

    private File imageFile = null;

    public MainPanel() {

        JLabel title = new JLabel("HUFFMAN IMAGE COMPRESSION", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 50));
        title.setBounds(0,0,1600,125);

        exit = new JButton("EXIT");
        exit.setBounds(1525,0,75,30);
        exit.setBackground(Color.red);
        exit.addActionListener(this);

        ImageIcon icon = new ImageIcon("Resources/sample.png");
        label1 = new JLabel(icon);
        label2 = new JLabel(icon);

        compressScrollPane = new JScrollPane(label1);
        compressScrollPane.setBounds(10,10,780,500);
        decompressScrollPane = new JScrollPane(label2);
        decompressScrollPane.setBounds(10,10,780,500);

        selectFile = new JButton("SELECT FILE");
        selectFile.setBounds(20,525,120,50);
        selectFile.addActionListener(this);

        compress = new JButton("COMPRESS");
        compress.setBounds(275,550,250,75);
        compress.addActionListener(this);

        decompress = new JButton("DECOMPRESS");
        decompress.setBounds(275,550,250,75);
        decompress.addActionListener(this);

        fileSize = new JLabel("Image File Size: ");
        fileSize.setFont(new Font("Arial", Font.PLAIN, 20));
        fileSize.setBounds(20,800,770,25);

        compressedSize = new JLabel("Compressed File Size: ");
        compressedSize.setFont(new Font("Arial", Font.PLAIN, 20));
        compressedSize.setBounds(20,830,770,25);
        
        status = new JLabel("Current Status . . . ", SwingConstants.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 30));
        status.setBounds(0,800,1600,100);

        compressorPanel = new JPanel();
        compressorPanel.setBounds(0,125,800,650);
        compressorPanel.setLayout(null);

        compressorPanel.add(compressScrollPane);
        compressorPanel.add(selectFile);
        compressorPanel.add(compress);

        decompressorPanel = new JPanel();
        decompressorPanel.setBounds(800,125,800,650);
        decompressorPanel.setLayout(null);

        decompressorPanel.add(decompressScrollPane);
        decompressorPanel.add(decompress);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setBounds(160, 90, 1600, 900);
        
        frame.add(title);
        frame.add(exit);
        frame.add(compressorPanel);
        frame.add(decompressorPanel);
        frame.add(fileSize);
        frame.add(compressedSize);
        frame.add(status);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String action = e.getActionCommand();

        if (action.equals("EXIT")) {
            System.out.println("Exit");
            frame.dispose();
        }
        if (action.equals("SELECT FILE")) {
            System.out.println("Select File");

            JFileChooser fileManager = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = fileManager.showOpenDialog(null);

            if(returnValue == JFileChooser.APPROVE_OPTION){
                imageFile = fileManager.getSelectedFile();

                Image image = null;
                try {
                    image = ImageIO.read(imageFile);
                }
                catch(IOException exception) {
                    exception.printStackTrace();
                }
                
                ImageIcon icon = new ImageIcon(image);
                label1.setIcon(icon);
                
                float imageSize = imageFile.length();
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                if (imageSize < 1000) {
                    fileSize.setText("Image File Size: " + imageSize + " B");
                }
                if (imageSize >= 1000) {
                    fileSize.setText("Image File Size: " + decimalFormat.format(imageSize/1000) + " KB");  
                }
                if (imageSize >= 1000000) {
                    fileSize.setText("Image File Size: " + decimalFormat.format(imageSize/1000000) + " MB");  
                }
                if (imageSize >= 1000000000) {
                    fileSize.setText("Image File Size: " + decimalFormat.format(imageSize/1000000000) + " GB");  
                }             

                compressedSize.setText("Compressed File Size: ");
                label2.setIcon(new ImageIcon("Resources/sample.png"));

                status.setText("File Loaded: " + imageFile.getName());
            }
        }
        if (action.equals("COMPRESS")) {
            System.out.println("Compressing");

            //if null, pop a message

            new Compressor(imageFile);
            status.setText("Compression Done");

            File compressedFile = new File("COMPRESSED.data");
            float compressedFileSize = compressedFile.length();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            if (compressedFileSize < 1000) {
                compressedSize.setText("Compressed File Size: " + compressedFileSize + " B");
            }
            if (compressedFileSize >= 1000) {
                compressedSize.setText("Compressed File Size: " + decimalFormat.format(compressedFileSize/1000) + " KB");  
            }
            if (compressedFileSize >= 1000000) {
                compressedSize.setText("Compressed File Size: " + decimalFormat.format(compressedFileSize/1000000) + " MB");  
            }
            if (compressedFileSize >= 1000000000) {
                compressedSize.setText("Compressed File Size: " + decimalFormat.format(compressedFileSize/1000000000) + " GB");  
            }
        }
        if (action.equals("DECOMPRESS")) {
            System.out.println("Decompressing");

            File compressedFile = new File("COMPRESSED.data");
            File treeFile = new File("TREE.txt");

            //if null, pop a message

            Decompressor decompressor = new Decompressor(compressedFile, treeFile);
            ImageIcon icon = new ImageIcon(decompressor.getImage());
            label2.setIcon(icon);
            status.setText("Decompression Done");
        }
    }
}