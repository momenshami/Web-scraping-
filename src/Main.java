/*
 *
  * This project about web scraping for eight Turkish websites
  *
  *
  * */
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;


public class Main {
    final static Level LOG_LEVEL = Level.ERROR;

    public static void main(String[] args) throws Exception {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(LOG_LEVEL);
        NewsSitesObjects creatObjets = new NewsSitesObjects(); // creating objects for each website
        creatObjets.createEightObjcet();

//       Databases dbs = new Databases(); // if we wannna see what is inside the Databases
//       dbs.showWhatInsideDatabases();

    }

}


