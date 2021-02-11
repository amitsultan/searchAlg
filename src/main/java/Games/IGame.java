package Games;

import java.util.Collection;

public interface IGame {

    Collection<IGame> getNeighbors();
    double getHeuristic();
    double F();
    void setF(double f);
    boolean isGoal();
    int hashCode();
    void setG(int g);
    int getG();
    IGame getPrevious();
    void setPrevious(IGame pre);
    int[][] getBoard();
    void addDevelopedNeighbore();
    int getDevelopedNeighbores();
    Collection<IGame> OSF();
}
