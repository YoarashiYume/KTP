package com.company;

import java.util.LinkedList;

public class URLPool {
    private LinkedList<URLDepthPair> unScanUrl;
    private LinkedList<URLDepthPair> allUnScanUrl;
    private LinkedList<String> allUrl;
    private int depth;
    private int maxDepth;
    private int tWait;

    public URLPool(int maxDepth) {
        this.maxDepth = maxDepth;
        unScanUrl = new LinkedList<URLDepthPair>();
        allUnScanUrl = new LinkedList<URLDepthPair>();
        tWait = 0;
        allUrl = new LinkedList<String>();
    }

    public synchronized void addPair(URLDepthPair currentPair) {
        if (allUrl.indexOf(currentPair.toString()) != -1) {
            allUnScanUrl.add(currentPair);
            if (currentPair.getDepth() < maxDepth) {
                unScanUrl.add(currentPair);
                notify();
            }
        }
    }
    public LinkedList<URLDepthPair> getUrls()
    {
        return allUnScanUrl;
    }
    public int getTWait()
    {
        return tWait;
    }
    public synchronized URLDepthPair getNextPair() {
        while (unScanUrl.size() == 0) {
            try {
                tWait++;
                wait();
                tWait--;
            } catch (InterruptedException e) {
                System.out.println("Caught unexpected " +
                        "InterruptedException, ignoring...");
            }
        }
        URLDepthPair nextPair = unScanUrl.getFirst();
        unScanUrl.removeFirst();
        return nextPair;
    }

}
