import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
public class bbcCrwler extends Crawler {

    public void getLinks() throws Exception {
        Document doc = Jsoup.connect("http://www.bbc.com/turkce").get();
        ArrayList<String> linksList = new ArrayList<String> ();

        Elements links1 = doc.select("#comp-top-story-1 > div > div a");
        Elements links2 = doc.select("#comp-top-story-2 > div a");
        Elements links3 = doc.select("#comp-top-story-3 > div > div a");

        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);


        ArrayList<String> linksListNonDuplicates = new ArrayList<String> ();
        String fileName= "bbcNew.txt";

        for (int i = 0; i <linksList.size() ; i++) {
            if(! linksList.get(i).contains("http"))
                linksListNonDuplicates.add(  "http://www.bbc.com/"+linksList.get(i)); // to add the main link for the sub_link

        }

        addLinksToFile( linksListNonDuplicates);
        usingBoilerPipe(linksListNonDuplicates, fileName);
    }

    private void addLinksToFile(ArrayList<String> linksList) {
        super.addLinksToFile(linksList,"bbclinksList.txt");
    }

    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {
            String link1 = element.attr("href");
                if (!linksList.contains(link1)) {
                        linksList.add(""+link1);
            }
        }
    }
    public void usingBoilerPipe(ArrayList<String> linksList, String fileName)throws Exception {
        super.usingBoilerPipe(linksList, fileName);
     }


    }





