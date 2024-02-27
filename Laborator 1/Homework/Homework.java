public class Laborator1Tema {
    public static void main(String args[]){
        //validate???
         if(args.length < 3){
            System.out.println("Not enough arguments!");
            System.exit(-1);
        }  
        
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        
        //int a=10, b=80, k=1;

        if(a >= b){
            System.out.println("Invalid format: a must be less than b.");
        }
        
        long startTime = System.nanoTime();

        StringBuilder reductibleNumbers = new StringBuilder();
        for(int i = a; i <= b; i++)
            if(isKreductible(i, k))  reductibleNumbers.append(i).append("  ");
        System.out.println("K-reductible numbers in the interval [" + a + ", " + b + "] for k = " + k + " : " + reductibleNumbers);
 
        long endTime = System.nanoTime();
        long nanoDuration = endTime - startTime;
        long milliDuration = (endTime - startTime) / 1_000_000; // Convert nanoseconds to milliseconds, currentTimeMillis()

        System.out.println("Running time: " + nanoDuration + " nanoseconds.");
        System.out.println("Running time: " + milliDuration + " milliseconds.");
    }

    public static boolean isKreductible(int n, int k){
        int sum = 0;
        while(n > 9){
            sum += (n%10)*(n%10);
            n/=10;
            if(n == 0 && sum > 9){
                 n = sum;
                 sum = 0;
             }
        }

        return n == k; 
    }
}
