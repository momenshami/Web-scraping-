import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

import java.io.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public abstract class Crawler {


    public abstract void  getLinks() throws Exception;

    public void addLinksToFile(ArrayList<String> linksList, String LinkFile){
        try{
            for (int i = 0; i <linksList.size() ; i++) {


            String LinksFile= LinkFile;
            FileWriter fw; //the true will append the new data

            fw = new FileWriter(LinksFile,true);
            fw.write(linksList.get(i));
            fw.write("\n\n");

            fw.close();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }
    public void usingBoilerPipe(ArrayList<String> linksList, String fileName)throws Exception{

       try {
           for (int i =  0; i <linksList.size() ; i++) {
                System.out.println(""+linksList.get(i));


                URL url = new URL(linksList.get(i));
               String link =linksList.get(i);//"" )


               final HTMLDocument htmlDoc = HTMLFetcher.fetch(url);
               final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
               String content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
               writeToFile(content,link,fileName);
               System.out.println(content);
           }
       }
       catch (IOException e){
           e.printStackTrace();
       }

    }

    public void writeToFile(String content, String Link ,String FileName) throws IOException  {
         try{
            String filename= FileName;
            FileWriter fw; //the true will append the new data

            fw = new FileWriter(filename,true);
            fw.write(Link);
            fw.write(content+"\n\n");//appends the string to the file

            fw.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}