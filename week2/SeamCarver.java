import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private int[][] edgeTo; // this way save memory?
    private double[][] distTo;
    //System.arraycopy(); //remove seam?

    // create a seam carver object based on the given pictur
    public SeamCarver(Picture picture) {
        this.picture = picture;
        // save color or picture?
        //read about optimaze 

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
        if (x < 0 || x > width()) throw new java.lang.IndexOutOfBoundsException();
        if (y < 0 || y > height()) throw new java.lang.IndexOutOfBoundsException();
        return singleEnergy(x, y);
    }

    //calculate energy when calling
    private double singleEnergy(int i, int j) {
        if (i == 0 || j == 0 || i == this.picture.width()-1 || j == this.picture.height()-1) {
            return 1000.0;
        }

        //have to check for optimal
        // x
        Color colorX1 = picture.get(i, j-1);
        Color colorX2 = picture.get(i, j+1);
        int x = (colorX1.getRed()-colorX2.getRed())*(colorX1.getRed()-colorX2.getRed()) + 
            (colorX1.getGreen()-colorX2.getGreen())*(colorX1.getGreen()-colorX2.getGreen()) + 
            (colorX1.getBlue()-colorX2.getBlue())*(colorX1.getBlue()-colorX2.getBlue());

        //y
        Color colorY1 = picture.get(i-1, j);
        Color colorY2 = picture.get(i+1, j);
        int y = (colorY1.getRed()-colorY2.getRed())*(colorY1.getRed()-colorY2.getRed()) + 
            (colorY1.getGreen()-colorY2.getGreen())*(colorY1.getGreen()-colorY2.getGreen()) + 
            (colorY1.getBlue()-colorY2.getBlue())*(colorY1.getBlue()-colorY2.getBlue());

        return Math.sqrt(x+y); //sqrt is expensive, remove?
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        //this way save memory?
        edgeTo = new int[width()][height()];
        distTo = new double[width()][height()];
        //init with MAX OR ZERO? O(HW)
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                distTo[i][j] = Double.MAX_VALUE;
            }
        }

        //process of relaxation each pixel O(HW)
        for (int i = 0; i < width()-1; i++) {
            for (int j = 0; j < height(); j++) {//skip last row
                relaxHorizontal(i, j);//relax current pixel
            }
        }

        //search last row to find smallest energy O(H+W)
        double smallest = Double.MAX_VALUE;
        int[] seam = new int[width()];
        for (int j = 0; j < height(); j++) {
            if (distTo[width()-1][j] < smallest) {
                smallest = distTo[width()-1][j];
                seam[width()-1] = j;
            }
        }

        //trace up from smallest in last row
        for (int i = width()-2; i >= 0; i--) {
            seam[i] = edgeTo[i+1][seam[i+1]];
        }

        return seam;
    }

    //checking all current's adj update distTo and edgeTo
    private void relaxHorizontal(int i, int j) {
        if (i == 0) distTo[i][j]=energy(i, j);
        //right up
        if (j != 0 && distTo[i+1][j-1] > distTo[i][j]+energy(i+1, j-1)) {
            distTo[i+1][j-1]=distTo[i][j]+energy(i+1, j-1);
            edgeTo[i+1][j-1]=j; 
        }
        //right
        if (distTo[i+1][j] > distTo[i][j]+energy(i+1, j)) {
            distTo[i+1][j]=distTo[i][j]+energy(i+1, j);
            edgeTo[i+1][j]=j;
        }
        //right down
        if (j != height()-1 && distTo[i+1][j+1] > distTo[i][j]+energy(i+1, j+1)) {
            distTo[i+1][j+1]=distTo[i][j]+energy(i+1, j+1);
            edgeTo[i+1][j+1]=j;
        }
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        //this way save memory?
        edgeTo = new int[width()][height()];
        distTo = new double[width()][height()];
        //init with MAX OR ZERO? O(HW)
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                distTo[i][j] = Double.MAX_VALUE;
            }
        }

        //process of relaxation each pixel O(HW)
        for (int j = 0; j < height()-1; j++) {
            for (int i = 0; i < width(); i++) {//skip last row
                relaxVertical(i, j);//relax current pixel
            }
        }

        //search last row to find smallest energy O(H+W)
        double smallest = Double.MAX_VALUE;
        int[] seam = new int[height()];
        for (int i = 0; i < width(); i++) {
            if (distTo[i][height()-1] < smallest) {
                smallest = distTo[i][height()-1];
                seam[height()-1] = i;
            }
        }

        //trace up from smallest in last row
        for (int j = height()-2; j >= 0; j--) {
            seam[j] = edgeTo[seam[j+1]][j+1];
        }

        return seam;
    }

    //checking all current's adj update distTo and edgeTo
    private void relaxVertical(int i, int j) {
        if (j == 0) distTo[i][j]=energy(i, j);
        //down left
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
        if (i != width()-1 && distTo[i+1][j+1] > distTo[i][j]+energy(i+1, j+1)) {
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
        String[] inputs = {"seamCarving/6x5.png", 
                "seamCarving/7x3.png",
                "seamCarving/7x10.png",
                "seamCarving/10x10.png",
                "seamCarving/10x12.png",
                "seamCarving/12x10.png"};

        for (String input : inputs) {
            Picture pic = new Picture(input);
            StdOut.println(input);
            SeamCarver sc = new SeamCarver(pic);
            //sc.picture().show();
            StdOut.print("width is " + sc.width());
            StdOut.println(" height is " + sc.height());

            StdOut.print("vertical seam: ");
            for (int i : sc.findVerticalSeam()) {
                StdOut.print(i+" ");
            }
            StdOut.println();
            StdOut.print("horizontal seam: ");
            for (int i : sc.findHorizontalSeam()) {
                StdOut.print(i+" ");
            }
            StdOut.println();
        }
    }
}
