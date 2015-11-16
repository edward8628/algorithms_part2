import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stopwatch;
import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.MinPQ;

public class SeamCarver {
    private int width;
    private int height;
    private double[][] energy;
    private Picture picture; // save color or picture?

    // create a seam carver object based on the given pictur
    public SeamCarver(Picture picture) {
        this.width = picture.width();
        this.height = picture.height();
        this.picture = picture;
        //this.picture = new Picture(picture); //new or not new?
        this.energy = new double[width][height]; //once for all or calculate each time
        //calculateEnergy(); //calculate every energy
        //read about optimaze 

    }

    private double singleEnergy(int i, int j) {
        //dont forget to cast from int to double
        //I can simplify the process
        if (i == 0 || j == 0 || i == width-1 || j == height-1) {
            return 1000.0; //right?
        }
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
        return Math.sqrt(x+y);
    }

    private void entireEnergy() {
        this.energy = new double[width][height]; //energy or no energy?
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energy[i][j] = singleEnergy(i, j);;
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

        return singleEnergy(x, y);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] result = new int[this.width];
        //perform

        //transpose
        for (int i = 0; i < width; i++) {
            for (int j = i+1; j < height; j++) {
                Color temp = picture.get(i, j);
                picture.set(i, j, picture.get(j, i));
                picture.set(j, i, temp);
            }
        }

        return result;
    }

    private double findSmall (double a, double b, double c) {
        return Math.min(Math.min(a, b), c);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] edgeTo = new int[this.picture.height()];
        double distTo[][] = new double[this.picture.width()][this.picture.height()];

        //what if out of bound?
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                relax();//parent pix and current pix as args
            }
        }

        return seam;
    }

    //update distTo and edgeTo
    private void relax() {
        //might not be right
        if (distTo[][] == 0) {
            distTo[][]=energy();//nothign yet and save
            edgeTo[]=; //save parent pix
            return;
        }

        if (distTo[][] > distTo[][]+energy()) {//current > parent energy + current energy
            distTo[][]=distTo[][]+energy();//current = parent energy + current energy
            edgeTo[]=; //save parent pix
        }
    }

    //update array of energy every time after removal
    private void updateEnergy() {

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
        //i want to test with all images

        //         //print all energy
        //         for (int i = 0; i < sc.width(); i++) {
        //             for (int j = 0; j < sc.height(); j++) {
        //                 StdOut.println(sc.energy(i, j));
        //             }
        //         }
        for (int i : sc.findVerticalSeam()) {
            StdOut.println(i);
        }

    }
}
