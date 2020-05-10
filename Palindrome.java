public class Palindrome {
    private static boolean isPal(String input) {
        int leng = input.length() - 1;
        int midle = (leng / 2) + (leng % 2);
        for (int i = 0; i < midle; i++) 
            if (input.charAt(i) != input.charAt(leng - i)) { return false; }        
        return true;
    }

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (isPal(args[i]))
                System.out.print(args[i] + " is Palindrome\n");
            else
                System.out.print(args[i] + "Not Palindrome\n");
        }
    }
}