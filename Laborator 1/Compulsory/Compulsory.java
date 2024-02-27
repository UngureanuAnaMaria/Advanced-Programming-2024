public class Laborator1Compulsory {
    public static void main(String args[]){
         System.out.println("Hello world!");
         String Languages[] = {"C", "C++", "C#", "Phyton", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
         int n = (int) (Math.random() * 1_000_000);
         n*=3;
         n+=0b10101;//Integer.parseInt("10101", 2);
         n+=0xFF;//Integer.parseInt("FF", 16);
         n*=6;
         int result = n;
        while(result > 9) {
            result = 0;
            while(n != 0) {
                result = result + n % 10;
                n = n / 10;
            }
            n = result;
        }
         
        System.out.println(result);
        System.out.println("Willy-nilly, this semester I will learn " + Languages[result]);  
    }
}
