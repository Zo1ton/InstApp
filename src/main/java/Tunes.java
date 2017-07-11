/**
 * Created by Andrey on 30.06.2017.
 */
public enum Tunes {
    startjson ("window._sharedData = "),                    // С чего начинать искать json
    endjson (";</script>"),                                 // Чем заканчивается json. Искать только с места startjson
    startFollowers ("\"followed_by\": {\"count\": "),       // С чего начинать искать кол-во подписчиков в JSON`е
    startFollowing ("\"follows\": {\"count\": "),           // С чего начинать искать кол-во подписок в JSON`е
    startPosts ("], \"count\": "),                          // С чего начинать искать кол-во постов в JSON`е
    savedir("$temp");                   // Здесь храним то, что пишем в файл. 10.07.2017 -  "d:\\Andrey\\Programming\\Java\\"

    private final String tune;

    Tunes(String tune){
        this.tune = tune;
    }

    public String getTune() {
        return tune;
    }
}
