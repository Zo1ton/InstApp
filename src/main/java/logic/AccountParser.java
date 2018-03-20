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
        JsonArray array = rootElement.getAsJsonObject().getAsJsonObject("entry_data").getAsJsonArray("ProfilePage")
                .get(0).getAsJsonObject().getAsJsonObject("graphql").getAsJsonObject("user")
                .getAsJsonObject("edge_owner_to_timeline_media").getAsJsonArray("edges");

        StringBuilder comments = new StringBuilder();
        int arraySize = array.size() >= 12 ? 11 : array.size() - 1;

        if (array.size() != 0) {
            System.out.println("arraySize:" + arraySize);
            for (int i = 0; i <= arraySize; i++) {
                System.out.println("i"+i);
                JsonElement element = array.get(i);
                JsonObject pages = element.getAsJsonObject();
                pages = pages.getAsJsonObject("node");
                pages = pages.getAsJsonObject("edge_media_to_caption");
                array = pages.getAsJsonArray("edges");

                if (array.size() > 0) {
                    element = array.get(0);
                    pages = element.getAsJsonObject().getAsJsonObject("node");

                    comments.append(pages.get("text").getAsString());
                }
            }
        }

        System.out.println(comments);

    }
}
