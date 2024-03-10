import java.util.ArrayList;
import java.util.List;

public class Laboratory1Bonus {
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

        List<List<Integer>> cycles;
        cycles = findCycles(matrix, n);

        System.out.println("Number of cycles in Wheel Graph W" + n + ": " + cycles.size());
        System.out.println("Cycles:");

        for (List<Integer> cycle : cycles){
            System.out.println(cycle);
        }

        System.out.println((n * n - 3 * n + 3) == cycles.size());
    }

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

    public static List<List<Integer>> findCycles(int[][] matrix, int n) {
        List<List<Integer>> cycles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> cycle = new ArrayList<>();
            findCyclesUtil(matrix, cycle, i, i, n, cycles);
        }
        return cycles;
    }

    private static void findCyclesUtil(int[][] matrix, List<Integer> cycle, int start, int current, int n, List<List<Integer>> cycles) {
        cycle.add(current);
        for (int i = 0; i < n; i++) {
            if (matrix[current][i] == 1) {
                if (i == start && cycle.size() >= 2) {
                    if (!cycleExists(cycles, cycle)) {
                        cycle.add(start);
                        cycles.add(new ArrayList<>(cycle));//!!!!!!!!!!!!!!!!!!!!!
                    }
                } else if (!cycle.contains(i)) {
                    findCyclesUtil(matrix, cycle, start, i, n, cycles);
                }
            }
        }
        cycle.remove(cycle.size() - 1);//bktr!!!!!!!!!!!!
    }

    private static boolean cycleExists(List<List<Integer>> cycles, List<Integer> cycle) {
        for (List<Integer> existingCycle : cycles) {
            //all integers from cycle are a part of an existing cycle and vice versa
            if (existingCycle.containsAll(cycle) && cycle.containsAll(existingCycle)) {
                return true;
            }
        }
        return false;
    }
}

