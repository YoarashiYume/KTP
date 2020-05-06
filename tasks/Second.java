public class Second {
    public static String repeat(String line,int amount)//1-multiplies each character in a line amount times
    {
        String result="";
        for (char cChar:line.toCharArray())
            result+=Character.toString(cChar).repeat(amount);
        return result;
    }
    public static int differenceMaxMin(int[] mass)//2-calculates the difference between the maximum and minimum elements of the array
    {
        int min = mass[0];
        int max = min;
        for (int cValue:mass)
        {
            max = cValue>max ? cValue : max;
            min = cValue<min ? cValue:min;
        }
        return max-min;
    }
    public static boolean isAvgWhole(int[] mass)//3-is the average value of the array integer?
    {
        int summ=0;
        for (int cValue:mass)
            summ+=cValue;
        return summ%mass.length == 0;
    }
    public static int[] cumulativeSum(int[] mass)//4-convert an array to a view when each element is the sum of the previous elements
    {
        int summ=0;
        for (int i = 0; i< mass.length;i++)
        {
            summ+=mass[i];
            mass[i]=summ;
        }
        return mass;
    }
    public static int getDecimalPlaces(String word)//5-this function return decimal part of value(in string view)
    {
        return word.indexOf(".")!=-1 ? word.length()-word.indexOf(".")-1 : 0;
    }
    public static int getFibonacci(int number)//6-this function return digit of fibonacci at 'number' place
    {
        return (int)((Math.pow((1+(Math.sqrt(5)))/2,number+1)-Math.pow((1-(Math.sqrt(5)))/2,number+1))/(Math.sqrt(5)));
    }
    public static boolean isValid(String index)//7-this function checks if an index is entered
    {
        boolean error = index.length()==5;//index has length = 5
        for (int i = 0; i<index.length()&&error;i++)
            error = Character.isDigit(index.charAt(i));
        return error;
    }
    public static boolean isStrangePair(String fWord, String sWord)//8-this function checks is fWord and sWord strange pair
    {
        return ((fWord.length()==0)||(sWord.length()==0)) ? fWord.equals(sWord) : (fWord.charAt(0)==sWord.charAt(sWord.length()-1))&&(sWord.charAt(0)==fWord.charAt(fWord.length()-1));
        //if first char of fWord = last char of sWord and first char of sWord = last char of fWord, then function return true
    }
    public static boolean isPrefix(String word, String prefix)//9-this function checks if the prefix is the prefix of the word
    {
        return word.indexOf(prefix.substring(0,prefix.length()-1))==0;
    }
    public static boolean isSuffix(String word, String suffix)//9-this function checks if the suffix is the suffix of the word
    {
        return word.lastIndexOf(suffix.substring(1))+suffix.substring(1).length()==word.length();
    }
    public static int boxSeq(int steps)
    {
        //return steps == 0 ? 0 : ((steps%2 == 1 ? 3*((steps+1)/2) : 3*((steps/2))) - steps/2); - previous variant
        /*10 - this function return amount cells, on 'steps' step by next algorithm
         * 0 step - start with 0
         * 1 step - add 3
         * 2 step - subtract 1
         * repeat 1 and 2 steps */
        return  steps%2==0 ? steps : steps+2;
    }
}