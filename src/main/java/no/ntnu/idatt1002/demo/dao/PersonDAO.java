package no.ntnu.idatt1002.demo.dao;

import no.ntnu.idatt1002.demo.data.person.Person;

import java.util.Collection;
import java.util.List;

public interface PersonDAO {

    boolean add(Person person);

    Person find(int personId);

    List<Person> find(Collection<Integer> personIds);
}