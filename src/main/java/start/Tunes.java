package start;

/**
 * Created by Andrey on 30.06.2017.
 */
public enum Tunes {
    startjson("window._sharedData = "),// С чего начинать искать json
    endjson(";</script>"),             // Чем заканчивается json. Искать только с места startjson
    savedir("%TEMP%"),                 // Здесь храним то, что пишем в файл. 10.07.2017 -  "d:\\Andrey\\Programming\\Java\\"
    dbFile("temp.out");                // Файл куда серюализуем данные коллекции

    private final String tune;

    Tunes(String tune) {
        this.tune = tune;
    }

    public String getTune() {
        return tune;
    }
}