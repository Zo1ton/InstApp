package logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import objects.Person;

import java.util.List;

public class AccountParser {

    public void personsParser(List<String> personsList) {
        if (!personsList.isEmpty()) {
            for (String name : personsList) {
//                System.out.println(name);
                Person person = new Person(name);
                /*System.out.print(person.getUserName());
                System.out.print(" - id-");
                System.out.print(person.getId());*/

                getFirst12PersonComments(person);
            }
        }
    }

    public void getFirst12PersonComments(Person person) {

        String json = person.getJson();
/*        System.out.println("==============");
        System.out.println(json);
        System.out.println("==============");*/

        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(json);
        JsonArray array = rootElement.getAsJsonObject().getAsJsonObject("entry_data").getAsJsonArray("ProfilePage")
                .get(0).getAsJsonObject().getAsJsonObject("graphql").getAsJsonObject("user")
                .getAsJsonObject("edge_owner_to_timeline_media").getAsJsonArray("edges");

        StringBuilder comments = new StringBuilder();
        int arraySize = array.size() >= 12 ? 11 : array.size() - 1;

        if (array.size() != 0) {
            for (int i = 0; i <= arraySize; i++) {

                JsonElement element = array.get(i);
                JsonObject pages = element.getAsJsonObject();
                pages = pages.getAsJsonObject("node");
                pages = pages.getAsJsonObject("edge_media_to_caption");
                JsonArray comment = pages.getAsJsonArray("edges");

                if (comment.size() > 0) {
                    element = comment.get(0);
                    pages = element.getAsJsonObject().getAsJsonObject("node");

                    comments.append(pages.get("text").getAsString());
                }
            }
        }
//        System.out.println(comments);
        if (comments.toString().contains("#IPA")) {
            System.out.println(comments.indexOf("#IPA"));
            System.out.println(person.getUserName());
        } else {
            System.out.println(comments.indexOf("#IPA"));
            System.out.println("---------------");
        }
    }
}
