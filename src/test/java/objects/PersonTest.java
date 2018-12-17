package objects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonTest {

    @Test
    public void testGetId() {

        String login = "ngageman61";
        Person person = new Person(login);

        assertEquals(person.getId(), 1418547643L);
        assertEquals(person.getUserName(), login);
        assertTrue(person.isExist());
    }
}
