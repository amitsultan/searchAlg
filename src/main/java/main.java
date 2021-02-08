import Games.IGame;
import Games.TilePuzzle;
import algorithms.AStar;
import sun.misc.ASCIICaseInsensitiveComparator;

import java.util.Collection;

public class main {

    public static void main(String[] args) {
        TilePuzzle b = new TilePuzzle(15);
        AStar aStar=new AStar(b);
        IGame goal=aStar.solve();
        aStar.printSolution(goal);
//        System.out.println(b);
//        Collection<IGame> boards = b.getNeighbors();
//        for(IGame board : boards){
//            System.out.println(board);
//            System.out.println(board.getHeuristic());
//        }
    }

}
