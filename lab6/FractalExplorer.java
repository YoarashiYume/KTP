package com.company;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class FractalExplorer {
    private int dispSize;
    private JImageDisplay img;
    private FractalGenerator gen;
    private Rectangle2D.Double range;
    private int rowsRemaning;
    JComboBox<FractalGenerator> box;
    JButton resetButton;
    JButton saveButton;

    public FractalExplorer(int dispSize) {
        this.dispSize = dispSize;
        this.gen = new Mandelbrot();
        this.range = new Rectangle2D.Double(0, 0, 0, 0);
        gen.getInitialRange(this.range);
    }
    public void createAndShowGUI()
    {
        JFrame frame = new JFrame("Fractal Explorer");
        img = new JImageDisplay(dispSize, dispSize);
        resetButton = new JButton("Reset");
        resetButton.setActionCommand("Reset");
        saveButton = new JButton("Save Image");
        saveButton.setActionCommand("Save");
        box = new JComboBox<FractalGenerator> ();
        box.addItem(new Mandelbrot());
        box.addItem(new Tricorn());
        box.addItem(new BurningShip());
        JPanel pan1 = new JPanel();
        JPanel pan2 = new JPanel();
        JLabel lbl = new JLabel("Fractal:");

        ActionHandler aHandler = new ActionHandler();
        MouseHandler mHandler = new MouseHandler();
        resetButton.addActionListener(aHandler);
        saveButton.addActionListener(aHandler);
        img.addMouseListener(mHandler);
        pan1.add(lbl, java.awt.BorderLayout.CENTER);
        pan1.add(box, java.awt.BorderLayout.CENTER);
        pan2.add(resetButton, java.awt.BorderLayout.CENTER);
        pan2.add(saveButton, java.awt.BorderLayout.CENTER);

        box.addActionListener(aHandler);

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(img, java.awt.BorderLayout.CENTER);
        frame.add(pan1, BorderLayout.NORTH);
        frame.add(pan2, BorderLayout.SOUTH);
        //frame.add(resetButton, java.awt.BorderLayout.SOUTH);
        //frame.add(saveButton, java.awt.BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Reset")) {
                gen.getInitialRange(range);
                drawFractal();
            } else if (e.getActionCommand().equals("Save")) {
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                int t = chooser.showSaveDialog(img);
                if (t == JFileChooser.APPROVE_OPTION) {
                    try {
                        javax.imageio.ImageIO.write(img.getBufferedImage(), "png", chooser.getSelectedFile());
                    } catch (NullPointerException | IOException e1) {
                        javax.swing.JOptionPane.showMessageDialog(img, e1.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                gen = (FractalGenerator) box.getSelectedItem();
                range = new Rectangle2D.Double(0, 0, 0, 0);
                gen.getInitialRange(range);
                drawFractal();
            }
        }
    }


    public class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (rowsRemaning==0) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, dispSize, e.getX());
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, dispSize, e.getY());
                gen.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
                drawFractal();
            }
        }
    }
    /*public void drawFractal1() - old version
    {
        for (int x = 0; x < dispSize; x++) {
            for (int y = 0; y < dispSize; y++) {
                int nIter = gen.numIterations(FractalGenerator.getCoord(range.x, range.x + range.width, dispSize, x), FractalGenerator.getCoord(range.y, range.y + range.width, dispSize, y));
                if (nIter == -1)
                    img.drawPixel(x, y, 0);
                else
                    {
                    double hue = 0.7f + (float) nIter / 200f;
                    int rgbColor = Color.HSBtoRGB((float) hue, 0.8f, 1f);
                    img.drawPixel(x, y, rgbColor);
                }
            }
        }
        img.repaint();
    }*/

    public void drawFractal()
    {
        enableUI(false);
        rowsRemaning = dispSize;
        for (int i = 0; i < dispSize; i++)
        {
            FractalWorker rowDrawer = new FractalWorker(i);
            rowDrawer.execute();
        }
    }
    private class FractalWorker extends SwingWorker<Object,Object>
    {
        private int y;
        private int[] rgb;
        public FractalWorker(int y)
        {
            this.y = y;
        }
        public Object doInBackground()
        {
            rgb = new int[dispSize];
                for (int i = 0; i < dispSize; i++) {
                    int nIter = gen.numIterations(FractalGenerator.getCoord(range.x, range.x + range.width, dispSize, i), FractalGenerator.getCoord(range.y, range.y + range.width, dispSize, y));
                    if (nIter == -1)
                        rgb[i]=0;
                    else
                    {
                        double hue = 0.7f + (float) nIter / 200f;
                        int rgbColor = Color.HSBtoRGB((float) hue, 0.8f, 1f);
                        rgb[i]= rgbColor;
                    }
                }
            return 0;
        }
        public void done()
        {
            for (int i = 0; i < dispSize; i++)
                img.drawPixel(i,y,rgb[i]);
            img.repaint(0, 0, y, dispSize, 1);
            rowsRemaning--;
            if (rowsRemaning==0)
                enableUI(true);
        }
    }
    public void enableUI(boolean var)
    {
       saveButton.setEnabled(var);
       resetButton.setEnabled(var);
       box.setEnabled(var);
    }
    public static void main(String[] args) {
        FractalExplorer fracExp = new FractalExplorer(600);
        fracExp.createAndShowGUI();
        fracExp.drawFractal();
    }
}
