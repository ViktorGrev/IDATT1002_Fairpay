package no.ntnu.idatt1002.demo.repo;

import no.ntnu.idatt1002.demo.data.MyEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyEntityRepoTest {

    @Test
    public void testThatWeCanReadMyEntityFromDatabase() {
        MyEntity e = new MyEntityRepo().getMyEntity("id");
        assertEquals(e.getName(), "name");
    }

}