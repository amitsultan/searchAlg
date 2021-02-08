import Games.IGame;
import Games.TilePuzzle;
import algorithms.AStar;
import algorithms.PEAStar;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class main {

    public static void main(String[] args) {
        TilePuzzle b = new TilePuzzle(15);
//        int[][] small = {{1, 2, 6}, {4, 8, 0}, {7, 5, 3}};
//        int[][] small = {{5, 2, 3}, {4, 1, 6}, {8,7, 0}};
        int[][] array = {{9, 5, 1, 7},{3, 8, 14, 6}, {2, 0, 12, 10}, {13, 15, 4, 11}};
        b.setPuzzle(array);
        System.out.println(b);
        System.out.println(b.H());
        AStar star = new AStar();
        PEAStar PEAstar = new PEAStar();
//        star.solveBoard(b);
        PEAstar.solveBoard( b);
    }

}
