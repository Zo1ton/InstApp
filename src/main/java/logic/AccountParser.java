package logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import objects.Person;

public class AccountParser {

    public void getPersonsListFromFile(Person person) {
        String json = person.getJson();
        System.out.println("==============");
        System.out.println(json);
        System.out.println("==============");

        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(json);
        JsonObject rootObject = rootElement.getAsJsonObject().getAsJsonObject("entry_data").getAsJsonArray("ProfilePage")
                .get(0).getAsJsonObject().getAsJsonObject("graphql").getAsJsonObject("user");

        StringBuilder comments = new StringBuilder();

        for (int i = 0; i <= 11; i++) {

            JsonObject pages = rootObject.getAsJsonObject("edge_owner_to_timeline_media");
            JsonArray array = pages.getAsJsonArray("edges");
            JsonElement element = array.get(0);
            pages = element.getAsJsonObject();
            pages = pages.getAsJsonObject("node");
            pages = pages.getAsJsonObject("edge_media_to_caption");
            array = pages.getAsJsonArray("edges");
            element = array.get(0);
            pages = element.getAsJsonObject().getAsJsonObject("node");

            comments.append(pages.get("text").getAsString());
        }

        System.out.println(comments);

    }
}
