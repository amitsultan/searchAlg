package algorithms;
import Games.IGame;
import java.util.*;

public class PEAStar2 {
    private PriorityQueue<IGame> open_list;
    HashMap<Integer,IGame> open_list_hash;
    HashSet<Integer> closed_list_hash;


    public PEAStar2(IGame root){
        this.open_list = new PriorityQueue<>((o1, o2) -> o1.F() == o2.F() ? (int) (o1.getHeuristic()-o2.getHeuristic()) : (int) (o1.F()-o2.F()));
        this.open_list_hash = new HashMap<>();
        this.closed_list_hash = new HashSet<>();
        open_list_hash.put(root.hashCode(),root);
        open_list.add(root);
    }

    public IGame solve(){
        while (this.open_list.size() > 0){
            IGame current = open_list.poll();
            if (current.isGoal()){
                return current;
            }
            HashSet<IGame> neighbors=(HashSet) current.getNeighbors();
            double min=current.F();
            for (IGame neighbor : neighbors) {
                if(neighbor.F()==current.F()) {
                    current.addDevelopedNeighbore();
                    if (!open_list_hash.containsKey(neighbor.hashCode()) && !closed_list_hash.contains(neighbor.hashCode())) {
                        open_list_hash.put(neighbor.hashCode(), neighbor);
                        open_list.add(neighbor);
                    }else if(!closed_list_hash.contains(neighbor.hashCode())) {
                        if(current.getG()+1<open_list_hash.get(neighbor.hashCode()).getG()){
                            open_list_hash.get(neighbor.hashCode()).setG(current.getG()+1);
                            open_list_hash.get(neighbor.hashCode()).setPrevious(current);
                        }
                    }
                } else if((neighbor.F()>current.F() && neighbor.F()<min) || min == current.F()){
                    min=neighbor.F();
                }
            }
            if(current.getDevelopedNeighbores()==neighbors.size()){
                open_list_hash.remove(current.hashCode());
                closed_list_hash.add(current.hashCode());
            }else {
                current.setF(min);
                open_list.add(current);
            }
        }
        return null;
    }

    public void printSolution(IGame goal){
        IGame current=goal;
        List<IGame> l= new ArrayList<>();
        l.add(0,current);
        while(current.getPrevious()!=null){
            current=current.getPrevious();
            l.add(0,current);
        }
        for(IGame board : l){
            System.out.println(board);
        }
        System.out.println("Number of steps: "+l.size());
    }
}
