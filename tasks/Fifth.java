import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Fifth {
    public static int[] encrypt(String textLine)//1-function returns an encrypted message
    {
        int[] array = new int[textLine.length()];
        array[0]=textLine.charAt(0);//don`t touch first symbol
        for (int i = 1; i<textLine.length();i++)
            array[i]=textLine.charAt(i)-textLine.charAt(i-1);//code of another symbol: current_char_code - previous_char_code
        return array;
    }
    public static String decrypt(int[] array)//function returns an decrypted message
    {
        StringBuilder line = new StringBuilder();
        line.append((char) array[0]);//don`t touch first symbol
        for (int i = 1; i<array.length;i++)
            line.append((char) (array[i] + (int) line.charAt(i - 1)));//code of another symbol: current_char_code + previous_char_code
        return line.toString();
    }
    public static boolean canMove(String figure, String startPos, String endPos)//2-function checks if the figure can go to the goal
    {
        boolean canItMove = false;
        figure = figure.toLowerCase();
        //#####get position######
        char sPosLetter = Character.toUpperCase(startPos.charAt(0));
        char ePosLetter = Character.toUpperCase(endPos.charAt(0));
        int sPosDigit = Integer.parseInt(String.valueOf((char)startPos.charAt(1)));
        int ePosDigit = Integer.parseInt(String.valueOf((char)endPos.charAt(1)));
        //########################
        int dPos = Math.abs(ePosDigit-sPosDigit);//count distance
        int dPosL = Math.abs(ePosLetter-sPosLetter);
        //checks if a line item is available
        if (ePosDigit>0&&ePosDigit<9&&sPosDigit>0&&sPosDigit<9&&ePosLetter>64&&ePosLetter<91&&sPosLetter>64&&sPosLetter<91&&startPos.length()==2&&endPos.length()==2)
            switch (figure)
            {
                case "pawn":
                case "пешка"://checking for pawn
                    if (dPosL==0)
                        if (sPosDigit==2)
                        {
                            if ((dPos==1) || (sPosDigit + 2 == ePosDigit))
                                canItMove = true;
                        }
                        else if (sPosDigit==7)
                        {
                            if ((dPos==1) || (sPosDigit - 2 == ePosDigit))
                                canItMove = true;
                        }
                        else if (dPos==1)
                            canItMove = true;
                    break;
                case "knight":
                case "конь"://checking for knight
                    if (dPos<3&&dPosL<3&&dPos!=0&&dPosL!=0)
                        if (dPosL==1&&dPos==2)
                            canItMove=true;
                        else if (dPosL==2&&dPos==1)
                            canItMove=true;
                    break;
                case "bishop":
                case "слон"://checking for bishop
                    if (dPos==dPosL&&dPos!=0&&dPosL!=0)
                        canItMove=true;
                    break;
                case "rook":
                case "ладья"://checking for rook
                    if (dPos==0||dPosL==0)
                        if (dPos!=dPosL)
                            canItMove=true;
                    break;
                case "king":
                case "король"://checking for king
                    if (dPos<2&&dPosL<2)
                        canItMove=true;
                    break;
                case "queen":
                case "ферзь"://checking for queen
                    if (dPos==0||dPosL==0)
                        if (dPos!=dPosL)
                            canItMove=true;
                    if (dPos==dPosL&&dPos!=0&&dPosL!=0)
                        canItMove=true;
                    break;
                default:
                    break;
            }
        return canItMove;
    }
    public static boolean canComplete(String line, String endLine)//3-function checks if the first word can be completed from the second using only existing letters
    {
        String tempLine = endLine;
        boolean canBeCompleted = true;
        for(char cChar : line.toCharArray())//remove existing char
        {
            if (tempLine.indexOf(cChar) != -1)
                tempLine = tempLine.substring(0, tempLine.indexOf(cChar)) + tempLine.substring(tempLine.indexOf(cChar) + 1);
            else
                canBeCompleted=false;
        }
        String resultLine="";
        if (!line.equals("")) {
            if (canBeCompleted) {
                int sPos = 0;//start position
                int bPos = 0;

                while ((tempLine.length() != 0 && canBeCompleted) || (sPos < line.length() && bPos < endLine.length()-1 && canBeCompleted)) {//checking all symbol
                    if (sPos < line.length()&&line.charAt(sPos) == endLine.charAt(bPos))
                    {
                        resultLine += line.charAt(sPos);
                        bPos++;
                        sPos++;
                    } else if (tempLine.indexOf(endLine.charAt(bPos)) != -1) {
                        resultLine += tempLine.charAt(tempLine.indexOf(endLine.charAt(bPos)));
                        tempLine = tempLine.substring(0, tempLine.indexOf(endLine.charAt(bPos))) + tempLine.substring(tempLine.indexOf(endLine.charAt(bPos)) + 1);
                        bPos++;
                    } else
                        canBeCompleted = false;
                }
            }
        }
        return canBeCompleted;
    }
    public static int sumDigProd(int ... array)//4-The function sums all the elements, then multiplies each digit from the resulting numbers, until the value => 10
    {
        int result=0;
        for (int value : array) result += value;//sums
        while (result>=10)//multiplies
        {
            int tempResult=1;
            for (char ch:String.valueOf(result).toCharArray())
                tempResult*=Integer.parseInt(String.valueOf((char)ch));
            result=tempResult;
        }
        return array.length==0 ? -1 : result;
    }
    public static String sameVowelGroup(String[] array)//5-function searches for words where unique vowels = unique vowels in the first word
    {
        String resultLine="";
        String vowels ="";
        for (char cChar:array[0].toCharArray())
            vowels+= (cChar=='a' || cChar=='e' ||cChar=='i' || cChar=='o' || cChar=='u') ? cChar : "";//remembering all vowels
        for (String cString : array)
        {
            String tVowels="";
            boolean allVowel=true;
            for (char cChar : cString.toCharArray())
                if (cChar=='a' || cChar=='e' ||cChar=='i' || cChar=='o' || cChar=='u')//remembering all vowels in current word
                    tVowels +=cChar;
            for (char cChar:vowels.toCharArray())
                allVowel = tVowels.indexOf(cChar)!=-1 ? allVowel : false;//if all vowels are from the first word in the current word
            resultLine+= allVowel ? cString + " " : "";
        }
        return resultLine;
    }
    public static boolean validateCard(long number)//6-function checks if the number is a bank card number
    {
        String cNubmder =String.valueOf(number);//get string number
        int summ = 0;
        //##########Luhn algorithm####
        for (int i = cNubmder.length()-2;i>=0;i--) {//reverse number, without last one(check_value)
            if ((i + 1) % 2 == 1)
            {//double value on odd place
                int temp = 2 * Integer.parseInt(String.valueOf((char) cNubmder.charAt(i)));
                while (temp >= 10) {//sums every numeral, if >10
                    int tempResult = 0;
                    for (char ch : String.valueOf(temp).toCharArray())
                        tempResult += Integer.parseInt(String.valueOf((char) ch));
                    temp = tempResult;
                }
                summ += temp;
            } else
                summ += Integer.parseInt(String.valueOf((char) cNubmder.charAt(i)));
        }
        summ = 10-(summ-10*(int)(summ/10));//10-last_numeral
        return Integer.valueOf(String.valueOf(cNubmder.charAt(cNubmder.length()-1)))==summ;//checks with check_value
    }
    public static String numToEng(int value)//7-function transfer integer to string representation of this integer
    {
        String result = "";
        if (value > 0 && value < 1000) {
            String[] variables_eng = new String[]{"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
                    "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen",
                    "nineteen", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
            result += value / 100 != 0 ? variables_eng[value / 100] + " hundred " : "";
            if (value % 100 >= 20 || value % 100 < 10) {
                result += (value / 10) % 10 != 0 ? variables_eng[(value / 10) % 10 + 19] + " " : "";
                result += variables_eng[(value % 10)];
            } else
                result += (value / 10) % 10 != 0 ? variables_eng[(value % 100)] + " " : "";


        }
        return value == 0 ? "Eng = zero" : result;
    }
    public static String numToRus(int value)//function transfer integer to string representation of this integer
    {
        String result="";
        if (value>0&&value<1000) {
            String[] variables_rus = new String[]{"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять","десять",
                    "одиннадцать", "двенадцать", "ринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать",
                    "девятнадцать", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто",
                    "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот"};
            result += value / 100 != 0 ? variables_rus[value / 100 + 28] + " " : "";
            if (value%100>=20||value%100<10)
            {
                result += (value / 10) % 10 != 0 ? variables_rus[(value / 10) % 10 + 19] + " " : "";
                result += variables_rus[(value % 10)];
            }
            else
                result += (value / 10) % 10 != 0 ? variables_rus[(value % 100)]+ " ": "";
        }
        else
            result="Неверное значение";
        return value==0 ? "ноль" : result;
    }
    public static String getSha256Hash(String line) {//8-sha256  hash
        byte[] hash = new byte[]{};
        try {
            hash = MessageDigest.getInstance("SHA-256").digest(line.getBytes(StandardCharsets.UTF_8));
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("I can`t do it: " + e);
        }
        StringBuilder hexString = new StringBuilder(new BigInteger(1, hash).toString(16));
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
    public static String correctTitle(String line)//9-make "correct title", all first letter in Upcase,without "and", "the" ,"of" and "in"
    {
        String result = "";
        String pos="";
        while (line.indexOf("-")!=-1)//Hyphenated words are considered separate words.
        {
            pos+=String.valueOf(line.indexOf("-"))+" ";
            line=line.substring(0,line.indexOf("-"))+" "+line.substring(line.indexOf("-")+1);
        }
        for (String cString:line.split(" ")) {
            if(cString.length()==0)
                result+=cString.toLowerCase() + " ";//save spaces
            else
            if (cString.length()==1)
                result+=Character.isLetter(cString.charAt(0)) ? Character.toUpperCase(cString.charAt(0))+" " : cString+" ";//save word
            else
            if(cString.toLowerCase().equals("and")||cString.toLowerCase().equals("the")||cString.toLowerCase().equals("of")||cString.toLowerCase().equals("in"))
                result+=cString.toLowerCase() + " ";
            else
                result+=Character.toUpperCase(cString.charAt(0))+cString.substring(1).toLowerCase()+" ";//save symbols
        }
        if (!pos.equals(""))
            for (String cPos:pos.split(" "))
                result=result.substring(0,Integer.parseInt(cPos))+"-"+result.substring(Integer.parseInt(cPos)+1);

        return result.substring(0,result.length()-1);
    }
    public static String hexLattice(int value)//10-function build Hexagonal by CenteredHexagonalNumbers
    {
        int countOfDots = 1;
        int cCenteredHexagonalNumbers = 1;
        int neededEvil=0;
        int maxLength = 1;
        while (value>cCenteredHexagonalNumbers)
        {
            countOfDots++;//count of dots in up line
            maxLength+=2;//length of central line
            cCenteredHexagonalNumbers=1+6*(countOfDots*(countOfDots-1)/2);//near CenteredHexagonalNumbers
            neededEvil+=cCenteredHexagonalNumbers/3>0 ? 1: 0;//for correct right part
        }
        countOfDots = cCenteredHexagonalNumbers==value ? countOfDots: 0;
        String result = "";
        int disp = countOfDots %2==0 ? 2 : 1;//displays for correct spaces
        boolean check = true;
        int countOfSpace = 2 * (maxLength - countOfDots + 1);
        for (int i = 0; i < maxLength; i++) {
            if (check)
                countOfSpace -= 2;//while upper part under construction
            else
                countOfSpace += 2;//bottom part
            if (countOfSpace == 0)
                check = false;
            for (int j = 0; j < 2 * maxLength; j++) {//print Hexagonal
                if (j < countOfSpace / 2) {
                    result += " ";
                } else if (j >= 2 * maxLength - countOfDots - countOfSpace / 2 + neededEvil)
                    result += " ";
                else if ((j + i + disp) % 2 == 0) {
                    result += " ";
                } else
                    result += "o";
            }
            result += "\n";
        }
        return countOfDots!=0 ? result : "Invaid";
    }