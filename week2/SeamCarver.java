import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stopwatch;
import java.awt.Color;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.MinPQ;

public class SeamCarver {
    private Picture picture; // save color or picture?
    private int[][] edgeTo; // this way save memory?
    private double[][] distTo;
    //System.arraycopy(); //remove seam?

    // create a seam carver object based on the given pictur
    public SeamCarver(Picture picture) {
        this.picture = picture;
        //this.picture = new Picture(picture); //new or not new?
        //read about optimaze 

    }

    private double singleEnergy(int i, int j) {
        //dont forget to cast from int to double
        //I can simplify the process
        if (i == 0 || j == 0 || i == this.picture.width()-1 || j == this.picture.height()-1) {
            return 1000.0; //right?
        }
        // x
        // i have to check for optimal
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

        return Math.sqrt(x+y); //sqrt is expensive, remove?
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }

    // height of current picture
    public int height() {
        return this.picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > this.picture.width()) throw new java.lang.IndexOutOfBoundsException();
        if (y < 0 || y > this.picture.height()) throw new java.lang.IndexOutOfBoundsException();
        return singleEnergy(x, y);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] edgeTo = new int[this.picture.height()];

        //transpose
        for (int i = 0; i < this.picture.width(); i++) {
            for (int j = i+1; j < this.picture.height(); j++) {
                Color temp = picture.get(i, j);
                picture.set(i, j, picture.get(j, i));
                picture.set(j, i, temp);
            }
        }
        // treat as find vertical like document said
        //transpose back

        return edgeTo;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        //this way save memory?
        edgeTo = new int[this.picture.width()][this.picture.height()];
        distTo = new double[this.picture.width()][this.picture.height()];
        //init with MAX OR ZERO? O(HW)
        for (int i = 0; i < this.picture.width(); i++) {
            for (int j = 0; j < this.picture.height(); j++) {
                distTo[i][j] = Double.MAX_VALUE;
            }
        }

        //process of relaxation each pixel O(HW)
        for (int i = 0; i < this.picture.width(); i++) {
            for (int j = 0; j < this.picture.height()-1; j++) {//skip last row
                relax(i, j);//relax current pixel
            }
        }

        //search last row to find smallest energy O(H+W)
        double smallest = Double.MAX_VALUE;
        int[] seam = new int[this.picture.height()];
        for (int i = 0; i < this.picture.width(); i++) {
            if (distTo[i][this.picture.height()-1] < smallest) {
                smallest = distTo[i][this.picture.height()-1];
                seam[this.picture.height()-1] = i;
            }
        }

        //trace up from smallest in last row
        for (int j = this.picture.height()-2; j >= 0; j--) {
            seam[j] = edgeTo[seam[j+1]][j+1];
        }

        printEnergy(seam);
        printDistTo(seam);

        return seam;
    }

    //checking all current's adj update distTo and edgeTo
    private void relax(int i, int j) {
        //might not be right
        //down left
        if (j == 0) distTo[i][j]=energy(i, j);
        if (i != 0 && distTo[i-1][j+1] > distTo[i][j]+energy(i-1, j+1)) {
            distTo[i-1][j+1]=distTo[i][j]+energy(i-1, j+1);//down left = par energy + curr energy
            edgeTo[i-1][j+1]=i; 
        }
        //down
        if (distTo[i][j+1] > distTo[i][j]+energy(i, j+1)) {//curr > par energy + curr energy
            distTo[i][j+1]=distTo[i][j]+energy(i, j+1);//down = par energy + curr energy
            edgeTo[i][j+1]=i;
        }
        //down right
        if (i != this.picture.width()-1 && distTo[i+1][j+1] > distTo[i][j]+energy(i+1, j+1)) {
            distTo[i+1][j+1]=distTo[i][j]+energy(i+1, j+1);//down right = par energy + curr energy
            edgeTo[i+1][j+1]=i;
        }
    }

    //print distTo with seam for debug
    private void printDistTo(int[] seam) {
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++)
                if (seam[j]==i) StdOut.printf("%9.0f *", this.distTo[i][j]);
                else StdOut.printf("%9.0f ", this.distTo[i][j]);
            StdOut.println();
        }
        StdOut.println();
    }

    //print energy with seam for debug
    private void printEnergy(int[] seam) {
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++)
                if (seam[j]==i) StdOut.printf("%9.0f *", this.energy(i, j));
                else StdOut.printf("%9.0f ", this.energy(i, j));
            StdOut.println();
        }
        StdOut.println();
    }

    //update array of energy every time after removal
    private void updateEnergy() {
        //not to calcuatle unchanged energy
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
