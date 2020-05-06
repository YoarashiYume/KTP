public class First {
    public static int remainder (int a, int b)//1-remainder of the division
    {
        return a%b;
    }
    public static double triArea (double a, double h)//2-square of triangle
    {
        return 0.5*a*h;
    }
    public static int animal (int chickens, int cows, int pigs)//3-count of animal`s legs on farm
    {
        return 2*chickens+4*(pigs+cows);
    }
    public static boolean profitableGamble (double prob, double prize, double pay)//4-condition check
    {
        return prob*prize>pay;
    }
    public static String operation (double res, double a,double b)//5-return what we should do with a and b, to get res
    {
        return (a+b)==res ? "added" : (a*b)==res ? "multiply" : (a/b)==res ? "divide" : (a-b)==res ? "subtracted" : "none";
    }
    public static int ctoa(char ch)//6-converting char to its ASCII number
    {
        return (int)ch;
    }
    public static int addUpTo(int startValue)//7-sum of all the numbers before startValue (including it)
    {
        int result=0;
        for(int i = 1; i<=startValue;i++)
            result+=i;
        return result;
    }
    public static int nextEdge(int a,int b)//8-maximum of third side of triangle
    {
        return a+b-1;
    }
    public static int sumOfCubes(int[] mass)//9-summ of cubes
    {
        int res=0;
        for (int i:mass) {
            res+=i*i*i;
        }
        return res;
    }
    public static boolean abcmath(int value, int count,int divider)//10-adds value to value in count times and checks divide it on divider
    {
        for (int i = 0; i< count;i++)
            value+=value;
        return value%divider==0;
    }
}