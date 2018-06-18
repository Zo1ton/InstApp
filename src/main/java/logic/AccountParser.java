package logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import objects.Person;
import org.apache.log4j.Logger;

import java.util.List;

public class AccountParser {

    private static final Logger LOG = Logger.getLogger(AccountParser.class);
    static int count = 0;

    public void findUsersWithTagsInComments(List<String> personsList, List<String> tags) {
        if (!personsList.isEmpty()) {
            for (String name : personsList) {

                Person person = new Person(name);

                try {
                    String comments = getFirst12PersonComments(person);

                    for (String tag: tags) {
                        if (comments.toUpperCase().contains(tag)) {
//                            System.out.println(comments.toUpperCase().indexOf(tag));
                            System.out.print(person.getUserName());
                            System.out.println(" - есть коменты с хэштегом " + tag);
                        } else {
                            System.out.println("У пользователя " + person.getUserName() + " нет коментов с хэштегом " + tag);
                        }

                    }
                }   catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    System.out.println("StringIndexOutOfBoundsException у юзера - " + person.getUserName());
                }
            }
        }
    }

    private String getFirst12PersonComments(Person person) {

        String json = person.getJson();

        JsonParser parser = new JsonParser();
        JsonElement rootElement;
        JsonArray array = new JsonArray();

        try {
            rootElement = parser.parse(json);
            array = rootElement.getAsJsonObject().getAsJsonObject("entry_data").getAsJsonArray("ProfilePage")
                    .get(0).getAsJsonObject().getAsJsonObject("graphql").getAsJsonObject("user")
                    .getAsJsonObject("edge_owner_to_timeline_media").getAsJsonArray("edges");
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            LOG.error("NPE у юзера - " + person.getUserName());
        }

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

        LOG.trace("User " + person.getUserName() + " comments: " + comments);
        System.out.println(count++);

        return comments.toString();
    }
}
