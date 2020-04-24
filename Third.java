
import java.util.Arrays;
public class Еhird {

    public static int solutions (int a, int b, int c)//1-this function returns the number of solutions of the quadratic equation
    {
        int D = b*b-4*a*c;
        return  D < 0 ? 0 : (D > 0 ? 2 : 1);
    }
    public static int findZip(String line)//2-this function returns position of second enters of "zip"
    {
        return !line.contains("zip") ? -1 : line.indexOf("zip",line.indexOf("zip")+1);
    }
    public static boolean checkPerfect(int value)//2-this function checks is value Perfect number or not
    {
        int cValue=value==0? -1 : 0;
        for (int i = 1; i<value;i++)
            cValue+= value%i==0 ? i : 0;
        return cValue==value;
    }
    public static String flipEndChars(String line)
    {
        /*
        * 3-this function swap first and last char of sentence
        * if this chars similar it will return "Two's a pair."
        * if length of this sentence < 2 it will return  "Incompatible."
        * */
        return line.length()<=2 ? "Incompatible." : line.charAt(0)==line.charAt(line.length()-1) ? "Two's a pair." : line.charAt(line.length()-1)+line.substring(1,line.length()-1)+line.charAt(0);
    }
    public static boolean isValidHexCode(String hexCode)//4-this function checks is "hexCode" is Hexadecimal code, where first char - "#" and chars can be in lower and upper case
    {
        boolean isHexCode=true;
        if ((hexCode.charAt(0)!='#')||(hexCode.length()!=7))
            isHexCode=false;
        for(int i = 1;i<hexCode.length()&&isHexCode;i++)
            if (!Character.isDigit(hexCode.charAt(i)))
                if ((Character.toUpperCase(hexCode.charAt(i)) < 65) || (Character.toUpperCase(hexCode.charAt(i)) > 70))
                    isHexCode = false;
        return isHexCode;
    }
    public static boolean same(int[] arr1, int[] arr2)//6-this function compare unique numbers of two arrays
    {
        int uniqueValue=0;
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        int maxLength = arr1.length-1>arr2.length-1 ? arr1.length-1 : arr2.length-1;
        for (int i = 0; i<maxLength;i++)//it checks every elements without last in every array
        {
            if (i<arr1.length-1)
                if (Arrays.binarySearch(arr1,i+1,arr1.length-1,arr1[i])<0)
                    uniqueValue++;
            if (i<arr2.length-1)
                if (Arrays.binarySearch(arr2,i+1,arr2.length-1,arr2[i])<0)
                    uniqueValue--;
        }
        uniqueValue += arr1.length==1 ? 1 : arr1[arr1.length-2]!=arr1[arr1.length-1] ? 1:0;//checks last element
        uniqueValue -= arr2.length==1 ? 1 : arr2[arr2.length-2]!=arr2[arr2.length-1] ? 1:0;
        return uniqueValue==0;
    }
    public static boolean isKaprekar(int value)//7-this function checks if "value" is Kaprekar number
    {
        int dValue = value*value;
        String s1 = Integer.toString(dValue).substring(0, Integer.toString(dValue).length() / 2).length() == 0 ? "0" : Integer.toString(dValue).substring(0, Integer.toString(dValue).length() / 2);
        String s2=Integer.toString(dValue).substring(Integer.toString(dValue).length() / 2);
        return Integer.parseInt(s1)+Integer.parseInt(s2)==value;
    }
    public static String longestZero(String line)//8-this function searches for the longest string of zeros
    {
        StringBuilder result=new StringBuilder();
        while (line.indexOf(result+"0")!=-1)
            result.append("0");
        return result.toString();
    }
    public static int nextPrime(int value) {//9-this function search next prime number,if "value" isn`t Prime
        boolean isPrime = true;
        int nValue=value;
        for (int i = 2; i<value&&isPrime;i++)//checks if the "value" is the Prime value
            isPrime = value % i != 0;
        while (!isPrime)//search next Prime value
        {
            nValue++;
            isPrime = true;
            for (int i = 2; i<nValue&&isPrime;i++)
                isPrime = nValue % i != 0;
        }
        return nValue;
    }
    public static boolean rightTriangle(int x, int y, int z)//10-this function checks if the "a","b","c" can be the sides of a right triangle
    {
        int max = Math.max(x,Math.max(y,z));//looking for the biggest side
        int m1 = max != x ? x : max != y ? y : z ;//and other sides
        int m2 = x+y+z-max-m1;
        return m2*m2+m1*m1==max*max;
    }

}