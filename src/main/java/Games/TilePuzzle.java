package Games;

import java.util.*;


public final class TilePuzzle implements IGame{

    private int[][] tilesCopy;
    private final int N;

    // cache
    private int hashCode = -1;
    private int zeroRow = -1;
    private int zeroCol = -1;
    private Collection<IGame> neighbors;
    private int G;
    private IGame previous;
    /*
     * Rep Invariant
     *      tilesCopy.length > 0
     * Abstraction Function
     *      represent single board of 8 puzzle game
     * Safety Exposure
     *      all fields are private and final (except cache variables). In the constructor,
     * defensive copy of tiles[][] (array that is received from the client)
     * is done.
     */
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
        this.G=0;
        this.previous=null;
    }

    public TilePuzzle(int[][] tiles, int G, IGame previous) {
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
        checkRep();
        this.G=G+1;
        this.previous=previous;
    }




//    private void shuffle(){
//        Random random = new Random();
//
//        for (int i = this.tilesCopy.length - 1; i > 0; i--) {
//            for (int j = this.tilesCopy[i].length - 1; j > 0; j--) {
//                int m = random.nextInt(i + 1);
//                int n = random.nextInt(j + 1);
//                if (this.tilesCopy[i][j] == 0){
//                    this.zeroRow = m;
//                    this.zeroCol = n;
//                }else if(this.tilesCopy[m][n] == 0){
//                    this.zeroRow = i;
//                    this.zeroCol = j;
//                }
//                int temp = this.tilesCopy[i][j];
//                this.tilesCopy[i][j] = this.tilesCopy[m][n];
//                this.tilesCopy[m][n] = temp;
//            }
//        }
//    }

    private void shuffle(){
        IGame current = this;
        Random random = new Random();
        for(int i=0; i<100; i++){
            HashSet<IGame> neighbores =(HashSet<IGame>) current.getNeighbors();
            List<IGame> neighboresList= new ArrayList<>(neighbores);
            int n = neighbores.size();
            int j = random.nextInt(n);
            current = neighboresList.get(j);
            this.tilesCopy=current.getBoard();
//            System.out.println(this);
        }
        this.tilesCopy=current.getBoard();
        findZeroTile();
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


    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;

        int expectedRow = 0, expectedCol = 0;
        for (int row = 0; row < this.size(); row++) {
            for (int col = 0; col < this.size(); col++) {
                if (tileAt(row, col) != 0 && tileAt(row, col) != (row*N + col + 1)) {
                    expectedRow = (tileAt(row, col) - 1) / N;
                    expectedCol = (tileAt(row, col) - 1) % N;
                    manhattan += Math.abs(expectedRow - row) + Math.abs(expectedCol - col);
                }
            }
        }
        return manhattan;
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
        if (this.zeroRow == -1 && this.zeroCol == -1) findZeroTile();
        neighbors = new HashSet<>();

        if (zeroRow - 1 >= 0)           generateNeighbor(zeroRow - 1, true);
        if (zeroCol - 1 >= 0)           generateNeighbor(zeroCol - 1, false);
        if (zeroRow + 1 < this.size())  generateNeighbor(zeroRow + 1, true);
        if (zeroCol + 1 < this.size())  generateNeighbor(zeroCol + 1, false);

        return neighbors;
    }

    @Override
    public double getHeuristic() {
        return this.manhattan();
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
    private void generateNeighbor(int toPosition, boolean isRow) {
        TilePuzzle board = new TilePuzzle(this.tilesCopy, this.G, this);
        if (isRow)  swapEntries(board.tilesCopy, zeroRow, zeroCol, toPosition, zeroCol);
        else        swapEntries(board.tilesCopy, zeroRow, zeroCol, zeroRow, toPosition);

        neighbors.add(board);
    }


    private void swapEntries(int[][] array, int fromRow, int fromCol, int toRow, int toCol) {
        int i = array[fromRow][fromCol];
        array[fromRow][fromCol] = array[toRow][toCol];
        array[toRow][toCol] = i;
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

    private void checkRep() {
        assert tilesCopy.length > 0;
    }

    public double F(){
        return this.getHeuristic()+this.G;
    }

    public int getG(){
        return G;
    }

    public void setG(int g) {
        this.G=g;
    }

    public IGame getPrevious(){
        return this.previous;
    }

    public int[][] getBoard(){
        return this.tilesCopy;
    }
}