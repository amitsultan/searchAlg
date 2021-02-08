package algorithms;
import Games.IGame;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStar {

    private PriorityQueue<IGame> open_list;
    HashMap<IGame, Double> open_list_hash;
    HashSet<IGame> closed_list_hash;

    public void solveBoard(IGame game){
        long start = System.currentTimeMillis();
        open_list = new PriorityQueue<>(new Comparator<IGame>() {
            @Override
            public int compare(IGame o1, IGame o2) {
                double o1_cost = o1.F();
                double o2_cost = o2.F();
                return (int) (o1_cost - o2_cost);
            }
        });

        open_list_hash = new HashMap<>();
        closed_list_hash = new HashSet<>();
        open_list.add(game);
        IGame goal = null;
        long end = 0;

        while(!open_list.isEmpty()){
            IGame current = open_list.poll();
            if(current.isGoal()){
                goal = current;
                end = System.currentTimeMillis();
                System.out.println("Goal found!");
                break;
            }
            if(closed_list_hash.contains(current)){
                System.exit(-1);
            }
            for (IGame neighbor : current.getNeighbors()) {
                double neighbor_cost = neighbor.F();
                if(closed_list_hash.contains(neighbor)){
                    continue;
                }
                if(!open_list_hash.containsKey(neighbor)){
                    open_list_hash.put(neighbor, neighbor.F());
                    open_list.add(neighbor);
                }else{
                    double old_f = open_list_hash.get(neighbor);
                    if(old_f <= neighbor_cost){
                        continue;
                    }else{
                        open_list_hash.remove(neighbor);
                        open_list.remove(neighbor);
                        open_list.add(neighbor);
                        open_list_hash.put(neighbor, neighbor.F());

                    }
                }
            }
            closed_list_hash.add(current);
        }
        int cost = 0;
        while(goal != null){
            goal = goal.getPrev();
            cost++;
        }
        float sec = (end - start) / 1000F;
        System.out.println("Solution took: "+sec + " seconds");
        System.out.println("Solution cost: "+cost);
        System.out.println("Open size: "+ open_list.size());
        System.out.println("Expended: "+ closed_list_hash.size());
    }

}
