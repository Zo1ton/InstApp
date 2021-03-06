package logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import objects.Person;
import org.apache.log4j.Logger;

import java.util.List;

import static com.google.gson.JsonParser.parseString;

public class AccountParser {

    private static final Logger LOG = Logger.getLogger(AccountParser.class);

    /**
     * Ищет аккаунты у которых в публикациях присутствуют посты с указанными тэгами
     *
     * @param personsList - Список названий аккаунтов
     * @param tags        - Список тэгов
     */
    public void findUsersWithTagsInComments(List<String> personsList, List<String> tags) {
        if (!personsList.isEmpty()) {
            personsList.forEach((name) -> {
                Person person = new Person(name);
                LOG.trace("Обрабатываем пользователя: " + name);
                try {
                    String comments = getFirst12PersonComments(person);
                    tags.forEach((tag) -> {
                        if (comments.toUpperCase().contains(tag)) {
                            LOG.info("https://www.instagram.com/" + name + " - есть коменты с хэштегом " + tag);
                        } else {
                            LOG.trace("У пользователя " + name + " нет коментов с хэштегом " + tag);
                        }
                    });
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    LOG.error("StringIndexOutOfBoundsException у юзера - " + person.getUserName());
                }
            });
        }
    }


    private String getFirst12PersonComments(Person person) {

        String json = person.getJson();
        JsonArray array = new JsonArray();
        try {
            array = parseString(json).getAsJsonObject().getAsJsonObject("entry_data").getAsJsonArray("ProfilePage")
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

        return comments.toString();
    }
}
