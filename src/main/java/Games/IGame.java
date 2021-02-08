package Games;

import java.util.Collection;

public interface IGame {

    Collection<IGame> getNeighbors();
    double getHeuristic();
    double F();
    boolean isGoal();
    int hashCode();
    void setG(int g);
    int getG();
    IGame getPrevious();
    int[][] getBoard();
}
