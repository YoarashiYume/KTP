package com.company;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;

public class Main{
    public static final String URL_PREFIX = "<a href=\"http";
    static LinkedList<URLDepthPair> findLink = new LinkedList<URLDepthPair>();
    static LinkedList<String> visitedLink = new LinkedList<String>();
    static LinkedList<URLDepthPair> resultLink = new LinkedList<URLDepthPair>();
    public static void showResult(LinkedList<URLDepthPair> resultLink)
    {
        for (URLDepthPair c : resultLink)
            System.out.println(c.toString());
    }
    public static void request(PrintWriter out,URLDepthPair pair)
    {
        out.println("GET " + pair.getPath() + " HTTP/1.1");
        out.println("Host: " + pair.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }
    public static void searchURLs(String urlString, int maxDepth)
    {
        URLDepthPair urlPair = new URLDepthPair(urlString, 0);
        findLink.add(urlPair);
        visitedLink.add(urlString);
        while (!findLink.isEmpty()) {
            URLDepthPair currentPair = findLink.removeFirst();
            int depth = currentPair.getDepth();
            try {
                Socket s = new Socket(currentPair.getHost(), 80);
                s.setSoTimeout(1000);
                PrintWriter out =  new PrintWriter(s.getOutputStream(), true);
                BufferedReader in =  new BufferedReader(new InputStreamReader(s.getInputStream()));
                request(out,currentPair);
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.indexOf(URL_PREFIX) > 0) {
                        boolean isLinkFound = false;
                        StringBuilder currentLink = new StringBuilder();
                        char c = line.charAt(line.indexOf(URL_PREFIX) + 9);
                        currentLink.append(c);
                        for(int i = line.indexOf(URL_PREFIX) + 10;c != '"' && i < line.length() - 1;i++)
                        {
                            c = line.charAt(i);
                            if (c == '"')
                                isLinkFound = true;
                            else
                                currentLink.append(c);
                        }
                        if (isLinkFound && depth < maxDepth)
                                if(!visitedLink.contains(currentLink.toString()))
                                {
                                    URLDepthPair newPair = new URLDepthPair(currentLink.toString(), depth + 1);
                                    findLink.add(newPair);
                                    visitedLink.add(currentLink.toString());
                                }
                    }
                }
                s.close();
                resultLink.add(currentPair);
            }
            catch (IOException e) {
            }
        }
        showResult(resultLink);
    }
   public static void main(String[] args)
   {
       //String[] arg = new String[]{"http://government.ru/","1"};
       //if (arg.length>2)
       if (args.length>2)
           System.out.println("usage: java Crawler <URL><depth>");
       else
       {
           boolean isDigit = true;
          // for (int i = 0; i< arg[1].length()&&isDigit;i++)
           for (int i = 0; i< args[1].length()&&isDigit;i++)
               isDigit = Character.isDigit(args[1].charAt(i));
          // searchURLs(arg[0],Integer.parseInt(arg[1]));
           if (isDigit) searchURLs(args[0],Integer.parseInt(args[1]));
       }
   }
}
