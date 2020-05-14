import java.util.Scanner;
public class Palindrome {
    private static boolean isPal(String input) {
        int leng = input.length() - 1;
        int midle = (leng / 2) + (leng % 2);
        for (int i = 0; i < midle; i++)
            if (input.charAt(i) != input.charAt(leng - i)) { return false; }
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String word = in.nextLine();
        if (isPal(word))
            System.out.print(word + " is Palindrome\n");
        else
            System.out.print(word + "Not Palindrome\n");
    }
}
