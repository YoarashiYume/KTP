import java.util.Scanner;
public class Primes {


    public static int isPrimes(int input) {
        boolean isSimple = true;
        for(int i = 2; i < input&&isSimple; i++)
                isSimple = input % i >= 1;
        return isSimple ? input : -1;
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        for (int i = 2; i < x; i++)
            if (isPrimes(i)!=-1)
            System.out.print(i+"\t");
        in.close();
    }
}
