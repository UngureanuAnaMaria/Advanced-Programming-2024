import java.util.ArrayList;
import java.util.List;

public class Laborator1Bonus {
    public static void main(String[] args){
        int n = 5;
        int[][] matrix = createAdjacencyMatrix(n);

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(matrix[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        /* 
        List<List<Integer>> cycles = findCycles(matrix, n);

        System.out.println("Number of cycles in Wheel Graph W" + n + ": " + cycles.size());
        System.out.println("Cycles:");

        for (List<Intrger> cycle : cycles){
            System.out.println(cycle);
        }
        */
    }
/* 
    public static List<List<Integer>> findCycles(int[][] matrix, int n){
        List<List<Integer>> cycles = new ArrayList();
        //???more cycles from one vertex???
        for(int i= 0; i < n; i++){
            List<Integer> cycle = new ArrayList();
            //  dfs/bfs
        }
    }
*/
    public static int[][] createAdjacencyMatrix(int n){
        int[][] matrix = new int[n][n];

        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++){
                if(i == j) matrix[i][j] = 0;
                else if(i == 0 || j==0) matrix[i][j] = 1;//hub=0 and ij spoke
                else if(j == i - 1 || i == j - 1) matrix[i][j] = 1;
                else if((j == n - 1 && i == 1) || (i == n - 1 && j == 1)) matrix[i][j] = 1;
                else matrix[i][j] = 0;
            }

        return matrix;
    }
}
