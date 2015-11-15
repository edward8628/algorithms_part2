import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stopwatch;
import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private int width;
    private int height;
    private double[] energy;
    private Picture picture;

    // create a seam carver object based on the given pictur
    public SeamCarver(Picture picture) {
        this.width = picture.width();
        this.height = picture.height();
        this.picture = picture;//new or not new?
        //calculate every energy

    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
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
        StdOut.println("width is " + sc.width());
        StdOut.println("height is " + sc.height());
    }
}
