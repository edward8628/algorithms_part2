import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given pictur
    public SeamCarver(Picture picture) {
        if (picture == null) throw new java.lang.NullPointerException();
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
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
        if (x < 0 || x >= width()) throw new java.lang.IndexOutOfBoundsException();
        if (y < 0 || y >= height()) throw new java.lang.IndexOutOfBoundsException();
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

        return Math.sqrt(x+y);
    }

    // sequence of indices for horizontal seam O(2HW)
    public int[] findHorizontalSeam() {
        int[][] edgeTo = new int[width()][height()];
        double[][] distTo = new double[width()][height()];
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                distTo[i][j] = Double.MAX_VALUE;
            }
        }

        //process of relaxation each pixel O(HW)
        for (int i = 0; i < width()-1; i++) {
            for (int j = 0; j < height(); j++) {//skip last row
                //relax current pixel for horizontal seam
                if (i == 0) distTo[i][j] = energy(i, j);
                //right up
                if (j != 0 && distTo[i+1][j-1] > distTo[i][j]+energy(i+1, j-1)) {
                    distTo[i+1][j-1] = distTo[i][j]+energy(i+1, j-1);
                    edgeTo[i+1][j-1] = j; 
                }
                //right
                if (distTo[i+1][j] > distTo[i][j]+energy(i+1, j)) {
                    distTo[i+1][j] = distTo[i][j]+energy(i+1, j);
                    edgeTo[i+1][j] = j;
                }
                //right down
                if (j != height()-1 && distTo[i+1][j+1] > distTo[i][j]+energy(i+1, j+1)) {
                    distTo[i+1][j+1] = distTo[i][j]+energy(i+1, j+1);
                    edgeTo[i+1][j+1] = j;
                }
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

    // sequence of indices for vertical seam O(2HW)
    public int[] findVerticalSeam() {
        int[][] edgeTo = new int[width()][height()];
        double[][] distTo = new double[width()][height()];
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                distTo[i][j] = Double.MAX_VALUE;
            }
        }

        //process of relaxation each pixel O(HW)
        for (int j = 0; j < height()-1; j++) {
            for (int i = 0; i < width(); i++) {//skip last row
                //relax current pixel for vertical seam
                if (j == 0) distTo[i][j] = energy(i, j);
                //down left
                if (i != 0 && distTo[i-1][j+1] > distTo[i][j] + energy(i-1, j+1)) {
                    distTo[i-1][j+1] = distTo[i][j] + energy(i-1, j+1);
                    edgeTo[i-1][j+1] = i; 
                }
                //down
                if (distTo[i][j+1] > distTo[i][j] + energy(i, j+1)) {
                    distTo[i][j+1] = distTo[i][j] + energy(i, j+1);
                    edgeTo[i][j+1] = i;
                }
                //down right
                if (i != width()-1 && distTo[i+1][j+1] > distTo[i][j] + energy(i+1, j+1)) {
                    distTo[i+1][j+1] = distTo[i][j] + energy(i+1, j+1);
                    edgeTo[i+1][j+1] = i;
                }
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

    //print distTo with seam for debug
    private void printDistTo(int[] seam, double[][] distTo) {
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++)
                if (seam[j] == i) StdOut.printf("%9.0f *", distTo[i][j]);
                else StdOut.printf("%9.0f ", distTo[i][j]);
            StdOut.println();
        }
        StdOut.println();
    }

    //print energy with seam for debug
    private void printEnergy(int[] seam) {
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++)
                if (seam[j] == i) StdOut.printf("%9.0f *", this.energy(i, j));
                else StdOut.printf("%9.0f ", this.energy(i, j));
            StdOut.println();
        }
        StdOut.println();
    }

    // remove horizontal seam from current picture O(HW)
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new java.lang.NullPointerException();
        if (seam.length != width()) throw new java.lang.IllegalArgumentException();
        for (int i = 0; i < seam.length; i++) {// 2 adj differ by more than 1 
            if (seam[i] < 0 || seam[i] >= height())
                throw new java.lang.IllegalArgumentException();
            if (i != 0 && Math.abs(seam[i-1]-seam[i]) > 1) 
                throw new java.lang.IllegalArgumentException();
        }
        if (height() <= 1) throw new java.lang.IllegalArgumentException();

        Picture pic = new Picture(width(), height()-1);
        for (int i = 0; i < width(); i++) {
            for (int j = 0, k = 0; j < height(); j++) {
                if (j != seam[i]) {
                    pic.set(i, k, picture.get(i, j));
                    k++;
                }
            }
        }
        this.picture = pic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new java.lang.NullPointerException();
        if (seam.length != height()) throw new java.lang.IllegalArgumentException();
        for (int i = 0; i < seam.length; i++) {// 2 adj differ by more than 1 
            if (seam[i] < 0 || seam[i] >= width())
                throw new java.lang.IllegalArgumentException();
            if (i != 0 && Math.abs(seam[i-1]-seam[i]) > 1) 
                throw new java.lang.IllegalArgumentException();
        }
        if (width() <= 1) throw new java.lang.IllegalArgumentException();

        Picture pic = new Picture(width() - 1, height());
        for (int i = 0; i < height(); i++) {
            for (int j = 0, k = 0; j < width(); j++) {
                if (j != seam[i]) {
                    pic.set(k, i, picture.get(j, i));
                    k++;
                }
            }
        }
        this.picture = pic;
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
