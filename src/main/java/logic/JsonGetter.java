package logic;

import org.apache.log4j.Logger;
import start.Tunes;

public class JsonGetter {

    private static final Logger LOG = Logger.getLogger(JsonGetter.class);

    public String getJsonFromAccountPage(String htmlPage) throws StringIndexOutOfBoundsException{

        String json;

        int startPoint = htmlPage.indexOf(Tunes.startjson.getTune()) + Tunes.startjson.getTune().length();
        int endPoint = htmlPage.indexOf(Tunes.endjson.getTune(), startPoint);
        LOG.trace("startPoint:" + startPoint);
        LOG.trace("endPoint:" + endPoint);

        json = htmlPage.substring(startPoint, endPoint);

        return json;
    }
}
