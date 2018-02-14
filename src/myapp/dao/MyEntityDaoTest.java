package myapp.dao;


import myapp.data.MyEntity;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyEntityDaoTest {

    @Test
    public void testThatWeCanReadMyEntityFromDatabase() {
        MyEntity e = new MyEntityDao().getMyEntity("id");

        assertEquals(e.getName(), "name");
    }

}