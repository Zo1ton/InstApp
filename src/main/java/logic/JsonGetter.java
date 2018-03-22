package logic;

import org.apache.log4j.Logger;
import start.Tunes;

public class JsonGetter {

    private static final Logger LOG = Logger.getLogger(JsonGetter.class);

    public String getJsonFromAccountPage(String htmlPage) throws StringIndexOutOfBoundsException{

        String json;

        int x = htmlPage.indexOf(Tunes.startjson.getTune()) + Tunes.startjson.getTune().length();
        int y = htmlPage.indexOf(Tunes.endjson.getTune(), x);

            json = htmlPage.substring(x, y);

        return json;
    }
}
