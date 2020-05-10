package com.company;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int dispSize;
    private JImageDisplay img;
    private FractalGenerator gen;
    private Rectangle2D.Double range;

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
        JButton resetButton = new JButton("Reset");

        ActionHandler aHandler = new ActionHandler();
        MouseHandler mHandler = new MouseHandler();
        resetButton.addActionListener(aHandler);
        img.addMouseListener(mHandler);

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(img, java.awt.BorderLayout.CENTER);
        frame.add(resetButton, java.awt.BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gen.getInitialRange(range);
            drawFractal();
        }
    }

    /** Simple handler to zoom in on the clicked pixel. **/
    public class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            double xCoord = FractalGenerator.getCoord(range.x,
                    range.x + range.width, dispSize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y,
                    range.y + range.width, dispSize, e.getY());
            gen.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }
    private void drawFractal()
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
    }
    public static void main(String[] args) {
        FractalExplorer fracExp = new FractalExplorer(600);
        fracExp.createAndShowGUI();
        fracExp.drawFractal();
    }
}
