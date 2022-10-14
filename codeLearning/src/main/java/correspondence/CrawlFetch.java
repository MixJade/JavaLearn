package correspondence;

import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlFetch{
    public static void main(String[] args) {
        URL url ;
        Thread readURL;
        Look look = new Look();
        try { url = new URL("https://pvp.qq.com/web201605/herolist.shtml");
            look.setURL(url);
            readURL = new Thread(look);
            readURL.start();
        }
        catch(Exception exp){
            System.out.println("Have a error!");
        }
    }
}

class Look implements Runnable {
    URL url;
    public void setURL(URL url) {
        this.url=url;
    }
    public void run() {
        try {
            InputStream in = url.openStream();
            byte [] b = new byte[1024];
            int n;
            while((n=in.read(b))!=-1) {
                String str = new String(b,0,n,"gbk");
                Pattern p=Pattern.compile("(?<=alt=\".{0,5}\">).+(?=</a>)");
                Matcher m=p.matcher(str);
                int i=0;
                while (m.find()){
                    i++;
                    System.out.println(i+", "+m.group());
                }
            }
            in.close();
        }
        catch(IOException exp){
            System.out.println("Have a error in the second");
        }
    }
}