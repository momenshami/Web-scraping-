import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mohammed on 05.07.2017.
 */
public class MilliyetCrawler extends Crawler {

    public void getLinks() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.milliyet.com.tr/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        org.jsoup.select.Elements links1 = doc.select(".flashbar   a");
        org.jsoup.select.Elements links2 = doc.select(".flashbar1 .top_p1 a");
        org.jsoup.select.Elements links3 = doc.select(".flashbar1 .top_p2 a");

        ArrayList<String> linksList = new ArrayList<String> ();
        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);
        System.out.println(linksList);
    }

    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                linksList.add(link1);
            }
        }
    }
}
