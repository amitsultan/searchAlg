//package algorithms;
//import Games.IGame;
//
//import java.util.*;
//
//public class PEAStar {
//
//
//    private PriorityQueue<IGame> open_list;
//    HashMap<IGame, Double> open_list_hash;
//    HashMap<IGame, Double> closed_list_hash;
//    private int C = 0;
//
//    public void solveBoard(IGame game){
//        long start = System.currentTimeMillis();
//        open_list = new PriorityQueue<>(new Comparator<IGame>() {
//            @Override
//            public int compare(IGame o1, IGame o2) {
//                double o1_cost = o1.F();
//                double o2_cost = o2.F();
//                return (int) (o1_cost - o2_cost);
//            }
//        });
//
//        open_list_hash = new HashMap<>();
//        closed_list_hash = new HashMap<>();
//        open_list.add(game);
//        IGame goal = null;
//        long end = 0;
//
//        double look_min = game.F();
//
//        while(!open_list.isEmpty()){
//            IGame n = open_list.poll();
//            open_list_hash.remove(n);
//            if(n.isGoal()){
//                goal = n;
//                end = System.currentTimeMillis();
//                System.out.println("Goal found!");
//                break;
//            }
//
//            double F_n = n.F();
//            HashSet<IGame> smaller = new HashSet<>();
//            HashSet<IGame> greater = new HashSet<>();
//            for (IGame neighbor : n.getNeighbors()) {
//                double neighbor_cost = neighbor.F();
//                if(neighbor_cost > F_n + C){
//                    greater.add(neighbor);
//                }else{
//                    smaller.add(neighbor);
//                }
//            }
//            for (IGame neighbor : smaller) {
//                double neighbor_cost = neighbor.F();
//                if(!open_list_hash.containsKey(neighbor) && !closed_list_hash.containsKey(neighbor)){
//                    open_list_hash.put(neighbor, neighbor.F());
//                    open_list.add(neighbor);
//                }else if(open_list_hash.containsKey(neighbor) && neighbor.F() < open_list_hash.get(neighbor)){
//                    open_list_hash.remove(neighbor);
//                    open_list.remove(neighbor);
//                    open_list.add(neighbor);
//                    open_list_hash.put(neighbor, neighbor_cost);
//                }else if (closed_list_hash.containsKey(neighbor) && neighbor.F() < closed_list_hash.get(neighbor)){
//                    closed_list_hash.remove(neighbor);
//                    open_list.add(neighbor);
//                    open_list_hash.put(neighbor, neighbor_cost);
//                }
//            }
//            if (greater.size() == 0){
//                closed_list_hash.put(n, n.F());
//            }else{
//                double min = Double.MAX_VALUE;
//                LinkedList<IGame> min_node = new LinkedList<>();
//                for (IGame neighbor : greater) {
//                    if(neighbor.F() < min){
//                        min = neighbor.F();;
//                        min_node = new LinkedList<>();
//                        min_node.add(neighbor);
//                    }else if(neighbor.F() == min){
//                        min_node.add(neighbor);
//                    }
//                }
//                for (IGame node: min_node) {
//                    open_list.add(node);
//                    open_list_hash.put(node, min);
//                }
//            }
////            closed_list_hash.add(current);
//        }
//        int cost = 0;
//        while(goal != null){
//            goal = goal.getPrev();
//            cost++;
//        }
//        float sec = (end - start) / 1000F;
//        System.out.println("Solution took: "+sec + " seconds");
//        System.out.println("Solution cost: "+cost);
//        System.out.println("Open size: "+ open_list.size());
//        System.out.println("Expended: "+ closed_list_hash.size());
//    }
//
//}
