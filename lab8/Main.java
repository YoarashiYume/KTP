package com.company;

import java.util.LinkedList;

public class Main {
    public static void showResult(LinkedList<URLDepthPair> resultLink)
    {
        for (URLDepthPair c : resultLink)
            System.out.println("Depth :" + c.getDepth()+"\tLink :"+c.toString());
    }
    public static boolean checkDigit(String line)
    {
        boolean isDigit = true;
        for (int i = 0; i < line.length() && isDigit; i++)
            isDigit = Character.isDigit(line.charAt(i));
        return isDigit;
    }
    public static void main(String[] args)
    {
        //args = new String[]{"http://government.ru/", "2", "3"};
        if (args.length == 3&&checkDigit(args[1])&&checkDigit(args[2]))
        {
            String lineUrl = args[0];
            int numThreads = Integer.parseInt(args[2]);
            URLPool pool = new URLPool(Integer.parseInt(args[1]));
            pool.addPair(new URLDepthPair(lineUrl, 0));
            for (int i = 0; i < numThreads; i++) {
                CrawlerTask c = new CrawlerTask(pool);
                Thread t = new Thread(c);
                t.start();
            }

            while (pool.getWait() != numThreads) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Ignoring  InterruptedException");
                }
            }
            showResult(pool.getResult());;
            System.exit(0);
        }
        else
        {
            System.out.println("usage: java Crawler <URL> <maximum_depth> <num_threads>");
        }
    }

}
