package myapp.data;

import java.util.Date;
import java.util.List;

/**
 * This is just a simple Java-bean
 * @author nilstes
 */
public class MyEntity {
    private String id;
    private String name;
    
    public MyEntity() {
    }

    public MyEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
