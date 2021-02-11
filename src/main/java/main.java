import Games.IGame;
import Games.TilePuzzle;
import algorithms.AStar;
import algorithms.EPEAStar;
import algorithms.PEAStar2;
import sun.misc.ASCIICaseInsensitiveComparator;

import java.util.Collection;

public class main {

    public static void main(String[] args) {
//        TilePuzzle b = new TilePuzzle(15);
//        AStar aStar=new AStar(b);
//        long start= System.currentTimeMillis();
//        IGame goal=aStar.solve();
//        long end= System.currentTimeMillis();
//        aStar.printSolution(goal);
//        System.out.println("Run time:"+ (end-start));
//        System.out.println("---------------------------------------------------------------------------");
//        PEAStar2 peaStar=new PEAStar2(b);
//        start= System.currentTimeMillis();
//        IGame goal2=peaStar.solve();
//        end= System.currentTimeMillis();
//        peaStar.printSolution(goal2);
//        System.out.println("Run time:"+ (end-start));
//        System.out.println("---------------------------------------------------------------------------");
        TilePuzzle b = new TilePuzzle(15);
        EPEAStar aStar=new EPEAStar(b);
        long start= System.currentTimeMillis();
        IGame goal=aStar.solve();
        long end= System.currentTimeMillis();
        aStar.printSolution(goal);
        System.out.println("Run time:"+ (end-start));
//        System.out.println(b);
//        Collection<IGame> boards = b.getNeighbors();
//        for(IGame board : boards){
//            System.out.println(board);
//            System.out.println(board.getHeuristic());
//        }
    }

}
