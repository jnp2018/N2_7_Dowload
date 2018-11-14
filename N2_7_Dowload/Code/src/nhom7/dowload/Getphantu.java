/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhom7.dowload;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Getphantu {
    
    public ArrayList<String> getX(String url) {
       ArrayList<String> list = new ArrayList<>();
        Document doc;
        try {
            doc=Jsoup.connect(url).get();
            Elements img =doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            for(Element im:img)
            {
                String l=im.attr("src");
                if(l.length()>0)
                {
                    if(l.length()<4)
                        l=doc.baseUri()+l.substring(1);
                    else if(!l.substring(0,4).equals("http"))
                        l=doc.baseUri()+l.substring(1);
                }
              list.add(l);                          
            }
        } catch (IOException ex) {
           
        }
        return list;
    }
}
