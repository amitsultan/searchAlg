package Games;

import java.util.Collection;

public interface IGame {

    Collection<IGame> getNeighbors();
    double getHeuristic();
}
