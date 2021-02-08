package Games;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;


public final class TilePuzzle implements IGame{

    private final int[][] tilesCopy;
    private final int N;

    public void setG(int g) {
        this.g = g;
    }

    private int g = 0;
    // cache
    private int hashCode = -1;
    private int zeroRow = -1;
    private int zeroCol = -1;
    private Collection<IGame> neighbors;

    public IGame getPrev() {
        return prev;
    }

    private IGame prev = null;

    public TilePuzzle(int n) {
        //this.N = tiles.length;
        this.N = (int) Math.sqrt(n + 1);
        this.tilesCopy = new int[N][N];
        // defensive copy
        for (int i = 0; i < n; i++) {
            int row = (int) Math.floor((i) / N);
            int col = i % N;
            this.tilesCopy[row][col] = i + 1;
        }
        this.zeroRow = N - 1;
        this.zeroCol = N - 1;
        this.shuffle();
        while(!isSolvable()){
            this.shuffle();
        }
    }

    public void setPuzzle(int[][] tiles){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] >= 0 && tiles[i][j] < N*N) tilesCopy[i][j] = tiles[i][j];
                else {
                    System.out.printf("Illegal tile value at (%d, %d): "
                            + "should be between 0 and N^2 - 1.", i, j);
                    System.exit(1);
                }
            }
        }
        if(!isSolvable()){
            System.out.println("cannot be solved");
            System.exit(-1);
        }
        findZeroTile();
    }

    public TilePuzzle(int[][] tiles, int g, IGame prev) {
        this.g = g;
        this.prev = prev;
        //this.N = tiles.length;
        this.N = tiles.length;
        this.tilesCopy = new int[N][N];
        // defensive copy
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] >= 0 && tiles[i][j] < N*N) tilesCopy[i][j] = tiles[i][j];
                else {
                    System.out.printf("Illegal tile value at (%d, %d): "
                            + "should be between 0 and N^2 - 1.", i, j);
                    System.exit(1);
                }
            }
        }
        findZeroTile();
    }


    public void printZeroLocation(){
        System.out.println(zeroRow+" "+zeroCol);
    }

    private void shuffle(){
        Random random = new Random();

        for (int i = this.tilesCopy.length - 1; i > 0; i--) {
            for (int j = this.tilesCopy[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);
                if (this.tilesCopy[i][j] == 0){
                    this.zeroRow = m;
                    this.zeroCol = n;
                }else if(this.tilesCopy[m][n] == 0){
                    this.zeroRow = i;
                    this.zeroCol = j;
                }
                int temp = this.tilesCopy[i][j];
                this.tilesCopy[i][j] = this.tilesCopy[m][n];
                this.tilesCopy[m][n] = temp;
            }
        }
    }


    public int tileAt(int row, int col) {
        if (row < 0 || row > N - 1) throw new IndexOutOfBoundsException
                ("row should be between 0 and N - 1");
        if (col < 0 || col > N - 1) throw new IndexOutOfBoundsException
                ("col should be between 0 and N - 1");

        return tilesCopy[row][col];
    }

    public int size() {
        return N;
    }

    private int calculateManhattanDistance() {
        int manhattanDistanceSum = 0;
        for (int x = 0; x < N; x++) // x-dimension, traversing rows (i)
            for (int y = 0; y < N; y++) { // y-dimension, traversing cols (j)
                int value = this.tilesCopy[x][y]; // tiles array contains board elements
                if (value != 0) { // we don't compute MD for element 0
                    int targetX = (value - 1) / N; // expected x-coordinate (row)
                    int targetY = (value - 1) % N; // expected y-coordinate (col)
                    int dx = x - targetX; // x-distance to expected coordinate
                    int dy = y - targetY; // y-distance to expected coordinate
                    manhattanDistanceSum += Math.abs(dx) + Math.abs(dy);
                }
            }
        return manhattanDistanceSum;
    }



    public boolean isGoal() {

        if (tileAt(N-1, N-1) != 0) return false;        // prune
        for (int i = 0; i < (N * N) - 1; i++) {
            int row = (int) Math.floor((i) / N);
            int col = i % N;
            if(tileAt(row, col) != i + 1){
                return false;
            }
        }
        return true;
    }



    @Override
    public boolean equals(Object y) {
        if (!(y instanceof TilePuzzle)) return false;
        TilePuzzle that = (TilePuzzle) y;
        // why bother checking whole array, if last elements aren't equals
        return this.tileAt(N - 1, N - 1) == that.tileAt(N - 1, N - 1) && this.size() == that.size() && Arrays.deepEquals(this.tilesCopy, that.tilesCopy);

    }

    @Override
    public int hashCode() {
        if (this.hashCode != -1) return hashCode;
        // more optimized version(Arrays.hashCode is too slow)?
        this.hashCode = Arrays.deepHashCode(tilesCopy);
        return this.hashCode;
    }

    public Collection<IGame> getNeighbors() {
        if (neighbors != null) return neighbors;
        if (this.zeroRow == -1 && this.zeroCol == -1) findZeroTile();
        neighbors = new HashSet<>();
        if (zeroRow - 1 >= 0)
            neighbors.add(generateNeighbor(zeroRow - 1, zeroCol));
        if (zeroCol - 1 >= 0)
            neighbors.add(generateNeighbor(zeroRow, zeroCol - 1));
        if (zeroRow + 1 < this.size())
            neighbors.add(generateNeighbor(zeroRow + 1, zeroCol));
        if (zeroCol + 1 < this.size())
            neighbors.add(generateNeighbor(zeroRow, zeroCol + 1));

        return neighbors;
    }

    private IGame generateNeighbor(int row, int col) {
        int[][] array = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                array[i][j] = tilesCopy[i][j];
            }
        }
        array[zeroRow][zeroCol] = array[row][col];
        array[row][col] = 0;
        return new TilePuzzle(array, this.g + 1, this);
    }

    @Override
    public double H() {
        return this.calculateManhattanDistance();
    }

    private void findZeroTile() {
        outerloop:
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                if (tileAt(i, j) == 0) {
                    this.zeroRow = i;       // index starting from 0
                    this.zeroCol = j;
                    break outerloop;
                }
            }
        }
    }

    public int G(){
        return this.g;
    }

    @Override
    public double F() {
        return G() + H();
    }

    public boolean isSolvable() {
        int inversions = 0;

        for (int i = 0; i < this.size() * this.size(); i++) {
            int currentRow = i / this.size();
            int currentCol = i % this.size();

            if (tileAt(currentRow, currentCol) == 0) {
                this.zeroRow = currentRow;
                this.zeroCol = currentCol;
            }

            for (int j = i; j < this.size() * this.size(); j++) {
                int row = j / this.size();
                int col = j % this.size();


                if (tileAt(row, col) != 0 && tileAt(row, col) < tileAt(currentRow, currentCol)) {
                    inversions++;
                }
            }
        }

        if (tilesCopy.length % 2 != 0 && inversions % 2 != 0) return false;
        if (tilesCopy.length % 2 == 0 && (inversions + this.zeroRow) % 2 == 0) return false;

        return true;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(4 * N * N);     // optimization?
//      s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        return s.toString();
    }

}