

public class Main {

    public static void main(String[] args) throws Exception {
        NewsSitesObjects creatObjets = new NewsSitesObjects();
        Databases dbs = new Databases();
        creatObjets.createEightObjcet();
        dbs.showWhatInsideDatabases();

    }


}


