package Games;

import java.util.Collection;

public interface IGame {

    Collection<IGame> getNeighbors();
    double H();
    boolean isGoal();
    int G();
    double F();
    IGame getPrev();
}
