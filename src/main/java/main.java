import Games.IGame;
import Games.TilePuzzle;
import algorithms.AStar;
import algorithms.EPEAStar;
import algorithms.PEAStar2;
import sun.misc.ASCIICaseInsensitiveComparator;
import java.io.FileWriter;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.Collection;

public class main {

    public static void main(String[] args) {
        long[] AstarResults=new long[200];
        long[] PEAstarResults=new long[200];
        long[] EPEAstarResults=new long[200];
        long suma=0;
        long sumb=0;
        long sumc=0;
        String csv = "results.csv";
        try {
            String [] record =new String[3];
            for(int i=0; i<200; i++) {
                CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
                System.out.println(i);
                TilePuzzle b = new TilePuzzle(15);
                AStar aStar = new AStar(b);
                long start = System.nanoTime();
                IGame goal = aStar.solve();
                long end = System.nanoTime();
    //            aStar.printSolution(goal);
                AstarResults[i] = (end - start);
                suma += (end - start);
    //            System.out.println("Run time:" + (end - start));
    //            System.out.println("---------------------------------------------------------------------------");
                PEAStar2 peaStar = new PEAStar2(b);
                start = System.nanoTime();
                IGame goal2 = peaStar.solve();
                end = System.nanoTime();
    //            peaStar.printSolution(goal2);
                PEAstarResults[i] = (end - start);
                sumb += (end - start);
    //            System.out.println("Run time:" + (end - start));
    //            System.out.println("---------------------------------------------------------------------------");
                EPEAStar epaStar = new EPEAStar(b);
                start = System.nanoTime();
                IGame goal3 = epaStar.solve();
                end = System.nanoTime();
    //            aStar.printSolution(goal3);
                sumc += (end - start);
                EPEAstarResults[i] = (end - start);
    //            System.out.println("Run time:" + (end - start));
                record[0]=""+AstarResults[i];
                record[1]=""+PEAstarResults[i];
                record[2]=""+EPEAstarResults[i];
                writer.writeNext(record);
                writer.close();
            }
            long mean = suma/200;
            System.out.println("Astar mean time: "+mean);
            mean = sumb/200;
            System.out.println("PEAstar mean time: "+mean);
            mean = sumc/200;
            System.out.println("EPEAstar mean time: "+mean);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(b);
//        Collection<IGame> boards = b.getNeighbors();
//        for(IGame board : boards){
//            System.out.println(board);
//            System.out.println(board.getHeuristic());
//        }
    }

}
