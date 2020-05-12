package com.company;

import java.net.MalformedURLException;
import java.net.URL;

public class URLDepthPair {
    private URL url;
    private int depth;
    public URLDepthPair(String url,int depth)
    {
        this.depth = depth;
        try {
            this.url =new URL(url);
        } catch (MalformedURLException e) {
            this.url = null;
        }
    }
    public int getDepth()
    {
      return this.depth;
    }
    public String getHost()
    {
        return this.url.getHost();
    }
    public String getPath()
    {
        return this.url.getPath();
    }
    public String toString()
    {
        return this.url.toString();
    }
}
