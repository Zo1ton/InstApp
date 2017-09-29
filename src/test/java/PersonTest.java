import objects.Person;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    @Test
    public void testGetId() {

        String login = "ngageman61";
        Person person = new Person(login, false);

        assertEquals(person.getId(), 1418547643L);
        assertEquals(person.getUserName(), login);
        assertEquals(person.isExist(), true);
    }
}
