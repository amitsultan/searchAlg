import Games.IGame;
import Games.TilePuzzle;

import java.util.Collection;

public class main {

    public static void main(String[] args) {
        TilePuzzle b = new TilePuzzle(8);
        System.out.println(b);
        Collection<IGame> boards = b.getNeighbors();
        for(IGame board : boards){
            System.out.println(board);
            System.out.println(board.getHeuristic());
        }
    }

}
