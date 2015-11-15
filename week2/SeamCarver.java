import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stopwatch;
import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private int width;
    private int height;
    private double[][] energy;
    private Picture picture;

    // create a seam carver object based on the given pictur
    public SeamCarver(Picture picture) {
        this.width = picture.width();
        this.height = picture.height();
        this.picture = picture;
        //this.picture = new Picture(picture); //new or not new?
        this.energy = new double[width][height];
        calculateEnergy();         //calculate every energy

    }

    private void calculateEnergy() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width-1 || j == height-1) { //right?
                    energy[i][j] = 1000;
                    continue;
                }
                //dont forget to cast from int to double
                //I can simplify the process
                // x
                Color colorX1 = picture.get(i, j-1);
                Color colorX2 = picture.get(i, j+1);
                int r1 = colorX1.getRed();
                int b1 = colorX1.getBlue();
                int g1 = colorX1.getGreen();
                int r2 = colorX2.getRed();
                int b2 = colorX2.getBlue();
                int g2 = colorX2.getGreen();
                double x = (r1-r2)*(r1-r2) + (g1-g2)*(g1-g2) + (b1-b2)*(b1-b2);

                //y
                Color colorY1 = picture.get(i-1, j);
                Color colorY2 = picture.get(i+1, j);
                r1 = colorY1.getRed();
                b1 = colorY1.getBlue();
                g1 = colorY1.getGreen();
                r2 = colorY2.getRed();
                b2 = colorY2.getBlue();
                g2 = colorY2.getGreen();

                double y = (r1-r2)*(r1-r2) + (g1-g2)*(g1-g2) + (b1-b2)*(b1-b2);
                energy[i][j] = Math.sqrt(x+y);
            }
        }
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return this.width;
    }

    // height of current picture
    public int height() {
        return this.height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > this.width) throw new java.lang.IndexOutOfBoundsException();
        if (y < 0 || y > this.height) throw new java.lang.IndexOutOfBoundsException();

        return 0;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
    }

    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(pic);
        //sc.picture().show();
        StdOut.println("width is " + sc.width());
        StdOut.println("height is " + sc.height());
    }
}
