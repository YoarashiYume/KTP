import java.util.Arrays;

public class Sixth {
    public static int bell(int arrayCount)//1-find bell number
    {
        int[][] array = new int[arrayCount+2][arrayCount+2];
        for (int i = 0;i<arrayCount;i++)
            for (int j = 0;j<arrayCount;j++)
                array[i][j]=-1;
        array[0][0]=1;
        int currentLast=0;
        for (int i = 1;i<arrayCount;i++)//building a pier triangle
        {
            array[i][0]=array[i-1][currentLast];
            currentLast=0;
            for (int j = 1;j<arrayCount;j++)
                if (array[i-1][j-1]!=-1)
                {
                    array[i][j] = array[i][j - 1] + array[i - 1][j - 1];
                    currentLast++;
                }
        }
        return array[arrayCount-1][currentLast];
    }
    public static String translateWord(String word)//2-translate word to "swine latin"
    {
        String result = "Not word";
        String cWord="";
        boolean isLetter = false;
        if(word.length()!=0) {
            for (char cChar : word.toCharArray())
                if (Character.isLetter(cChar)) {
                    cWord += Character.toLowerCase(cChar);
                    isLetter = true;
                } else
                    cWord += cChar;
            isLetter = Character.isLetter(cWord.charAt(0)) ? isLetter : false;
        }
        if (!word.contains(" ")&&(word.length()>1||isLetter))
        {
            if (cWord.charAt(0)=='a'||cWord.charAt(0)=='e'||cWord.charAt(0)=='o'||cWord.charAt(0)=='i'||cWord.charAt(0)=='u')
                result=word+"yay";//if first is vowel, then add "yay" to end
            else
            {//otherwise, insert at the end all the letters before the first vowel, adding "ay"
                int vowelPos = 0;
                while (vowelPos<cWord.length()&&cWord.charAt(vowelPos)!='a'&&cWord.charAt(vowelPos)!='e'&&cWord.charAt(vowelPos)!='o'&&cWord.charAt(vowelPos)!='i'&&cWord.charAt(vowelPos)!='u')
                    vowelPos++;
                result = word.substring(vowelPos) + word.substring(0,vowelPos) + "ay";
            }
        }
        return isLetter ? result : word;
    }
    public static String translateSentence(String line)//translate sentence to "swine latin"
    {
        StringBuilder result = new StringBuilder();
        if ((line.charAt(line.length()-1)=='.'&&line.indexOf(".")==line.length()-1)||(line.charAt(line.length()-1)=='!'&&line.indexOf("!")==line.length()-1)||(line.charAt(line.length()-1)=='?'&&line.indexOf("?")==line.length()-1))
        {//checks if there is only 1 "." and it stands at the end
            line=line.substring(0,line.length()-1);
            for (String cString : line.split(" "))
            {
                char ch = 0;
                if (cString.length()!=0)
                    if (cString.charAt(cString.length()-1)==',')
                    {
                        cString = cString.substring(0, cString.length() - 1);
                        ch=',';//remember ","
                    }
                if (!translateWord(cString).equals("Not word"))
                    result.append( ch == 0 ? translateWord(cString)+" " : translateWord(cString)+ch+" ");
            }
            result.replace(0,result.length(), result.substring(0,result.length()-1));
        }
        else
            result.replace(0,result.length(),"Incorrect line");
        return result+".";
    }
    public static boolean validColor(String rgbCode)//3-checks the correctness of the entered rgb / rgba code
    {
        while (rgbCode.indexOf(' ')!=-1)
            rgbCode=rgbCode.substring(0,rgbCode.indexOf(' '))+rgbCode.substring(rgbCode.indexOf(' '));
        boolean isColor=false;
        if ((rgbCode.indexOf('(')==3||rgbCode.indexOf('(')==4)&&rgbCode.indexOf(')')==rgbCode.length()-1)//checks branch
        {
            String code = rgbCode.substring(0,rgbCode.indexOf('('));
            isColor = code.equals("rgb") ? true : code.equals("rgba");
            rgbCode=rgbCode.substring(rgbCode.indexOf('(')+1,rgbCode.length()-1);
            String[] value =rgbCode.split(",");//take parameters
            isColor = isColor ? code.length()==value.length : false;
            for (int i = 0; i < value.length&&isColor;i++)//checks parameters
            {
                for (char c : value[i].toCharArray())
                    if (!Character.isDigit(c)&&c!='.') return false;
                try{
                    double cValue = Double.parseDouble(value[i]);
                    if (i!=3)
                        isColor= cValue>=0&&cValue<=255&&(cValue-(int)cValue==0);
                    else
                        isColor= cValue>=0&&cValue<=1;
                }
                catch (Exception e)
                {
                    isColor=false;
                }
            }
        }
        return isColor;
    }
    public static String stripUrlParams(String url, String ... parameters)//4-accepts a URL (string), removes duplicate query parameters and parameters specified in the second argument
    {
        String parametersLine ="";
        if (url.contains("?") &&url.indexOf("?")!=url.length())//remember parameters
        {
            parametersLine = url.substring(url.indexOf("?") + 1) + "&";
            url = url.substring(0, url.indexOf("?"));
        }
        String resultP ="?";
        int i = 0;
        String tempP="";
        while (i<parametersLine.length())
        {
            if (parametersLine.charAt(i)!='&')
                tempP+=parametersLine.charAt(i);
            else if (parametersLine.indexOf(tempP.split("=")[0],i+1)==-1)//try to find repetitions
            {
                if (parameters.length!=0) {
                    if (!Arrays.asList(parameters).contains(tempP.split("=")[0]))
                        resultP += tempP + "&";
                }
                else
                    resultP += tempP + "&";
                tempP="";
            }
            else
                tempP="";
            i++;
        }
        resultP=resultP.substring(0,resultP.length()-1);
        return url+resultP;
    }

    public static String[] getHashTags(String line)//5-function extracts the three longest words from a string and converts them to hashtags
    {
        StringBuilder cLine= new StringBuilder();
        for (char ch : line.toCharArray())//remember only letter
            if (ch!=0)
                if (Character.isLetter(ch))
                    cLine.append(Character.toLowerCase(ch));
                else if (ch==' ')
                    cLine.append(ch);
        int size = cLine.toString().split(" ").length>2 ? 3 : cLine.toString().split(" ").length;
        String[] result = new String[size];
        for (int i = 0;i<size;i++ )
            result[i]="";
        for (String cString : cLine.toString().split(" "))
            for (int i = 0; i<size; i++)//checks every word
                if(result[i].length()<cString.length())
                {
                    for (int j = size-1; j >= 0; j--)//transfer to correct place
                    {
                        if (j != i)
                            result[j] = result[j - 1];
                        else {
                            result[j] = cString;
                            break;
                        }
                    }
                    break;
                }
        for (int i = 0;i<size;i++ )
            result[i]="#"+result[i];
        return result;
    }

    public static int ulam(int num)//6-function takes the number n and returns the nth number in the Ulam sequence
    {
        int[] ulamList = new int[num>1 ? num : 2];
        ulamList[0]=1;
        ulamList[1]=2;
        for (int i = 2;i<ulamList.length;i++)
            ulamList[i]=-1;
        int curPos = 1;
        String forbiddenValue = " ";
        while (curPos<num-1)
        {
            int minSum = Integer.MAX_VALUE;
            int i = 0;
            while (ulamList[i]!=-1)
            {
                int j=i+1;
                while (ulamList[j]!=-1)
                {
                    if (i!=j)
                        if(ulamList[i]+ulamList[j]<minSum&&(ulamList[i]+ulamList[j])>ulamList[curPos]&& !forbiddenValue.contains(" " + String.valueOf(ulamList[i]+ulamList[j]) + " "))
                            /*find minimum sum, that not forbidden*/
                            minSum = ulamList[i] + ulamList[j];
                        else if (minSum==ulamList[i]+ulamList[j]) {
                            if (!forbiddenValue.contains(" " + String.valueOf(ulamList[i] + ulamList[j]) + " "))
                                forbiddenValue += minSum + " ";//adds to forbidden sum
                            j=0;
                            i=0;
                            minSum = Integer.MAX_VALUE;
                        }
                    j++;

                }
                i++;
            }
            curPos++;
            ulamList[curPos]=minSum;
        }
        return num>0 ? ulamList[num-1] : -1;
    }
    public static String convertToRoman(int value)//8-convert value to Roman value
    {
        StringBuilder result = new StringBuilder();
        if (value>0&&value<4000)
        {
            String[] array = new String[]{"I", "X", "C", "M", "V", "L", "D"};
            String v = String.valueOf(value);
            int pos = 0;
            for (int j = v.length() - 1; j > -1; j--) {//builds from end
                int cValue = Integer.parseInt(String.valueOf((char) v.charAt(j)));
                //some spelling rules
                if (cValue < 4)
                    result.insert(0, array[pos].repeat(cValue));
                else if (cValue == 4)
                    result.insert(0, array[pos] + array[pos + 4]);
                else if (cValue == 5)
                    result.insert(0, array[pos + 4]);
                else if (cValue == 9)
                    result.insert(0, array[pos] + array[pos + 1]);
                else
                {
                    StringBuilder tr = new StringBuilder();
                    tr.append(array[pos].repeat(cValue - 5));
                    result.insert(0, array[pos + 4] + tr);
                }
                pos++;
            }
        }
        return result.toString();
    }
    public static String mathResult(String v1,String v2,char move)//math operations for strings
    {
        int fValue = Integer.parseInt(v1);
        int result=-1;
        if (!v2.equals("")) {
            int sValue = Integer.parseInt(v2);
            switch (move) {
                case '+':
                    result = fValue + sValue;
                    break;
                case '-':
                    result = fValue - sValue;
                    break;
                case '*':
                    result = fValue * sValue;
                    break;
                case '/':
                    result = fValue / sValue;
                    break;
            }
        }
        else
            result=fValue;
        return String.valueOf(result);
    }
    public static boolean formula(String line)//9-function checks if the formula is correct or not.
    {
        boolean isCorrect = true;
        while (line.contains(" "))
            line=line.substring(0,line.indexOf(" "))+line.substring(line.indexOf(" ")+1);
        int pValue=0;
        int cValue=0;
        int i =0;
        for (String cPart : line.split("="))//split string to parts
        {
            pValue=cValue;
            if (isCorrect)
            {
                StringBuilder v1 = new StringBuilder();
                StringBuilder v2 = new StringBuilder();
                char move = 0;//to take different parts and for operations
                for (char c : cPart.toCharArray())
                {
                    if (Character.isDigit(c))
                    {//remembers value in formulas
                        if (move == 0)
                            v1.append(c);
                        else
                            v2.append(c);
                    }
                    else if (c=='+'||c=='*'||c=='/'||c=='-')
                    {
                        if (move==0)
                            move = c;//switch to part
                        else
                        {
                            v1.replace(0,v1.length(),mathResult(v1.toString(),v2.toString(),move));
                            v2.delete(0,v2.length());
                            move=c;
                        }
                    }
                    else
                        isCorrect=false;
                }

                v1.replace(0,v1.length(),mathResult(v1.toString(),v2.toString(),move));//
                v2.delete(0,v2.length());
                cValue=Integer.parseInt(v1.toString());//remember result
            }
            else
                break;
            if (i!=0)
                isCorrect = pValue==cValue;//compare result
            else
                i++;
        }
        return isCorrect;
    }
    public static String reverseString(String word)//to reverse string
    {
        StringBuilder result= new StringBuilder();
        for(char c : word.toCharArray())
            result.insert(0, c);
        return result.toString();
    }
    public static boolean palindromedescendant(int value)//10-function returns true if the number itself is a palindrome or any of its descendants
    {
        boolean isPalindrome = false;
        String line = String.valueOf(value);
        while (line.length()>1&&!isPalindrome)
        {
            isPalindrome=reverseString(line).equals(line);
            if (!isPalindrome)
            {
                StringBuilder tLine = new StringBuilder();
                for (int i = 0;i<line.length()-1;i=i+2)// building a descendant
                {
                    int v1 = Integer.parseInt(String.valueOf(line.charAt(i)));
                    int v2 = Integer.parseInt(String.valueOf(line.charAt(i+1)));
                    tLine.append((v1 + v2));
                }
                line= tLine.toString();
            }
        }
        return isPalindrome;
    }
    public static String longestNonrepeatingSubstring(String line)//7-function returns the longest non-repeating substring for string input
    {
        String cLine="";
        String pLine="";
        cLine+=line.charAt(0);
        for (int i = 1 ; i<line.length();i++)
        {
            if (cLine.indexOf(line.charAt(i))!=-1)
            {//checks recurring char
                pLine=pLine.length()<cLine.length()? cLine : pLine;//find longest - current or previous
                if (cLine.indexOf(line.charAt(i))>cLine.length()-1)
                    cLine=cLine.substring(cLine.indexOf(line.charAt(i)+1));//take new line
                else
                    cLine="";
            }
            cLine+=line.charAt(i);
        }
        return pLine.length()<cLine.length()? cLine : pLine;
    }