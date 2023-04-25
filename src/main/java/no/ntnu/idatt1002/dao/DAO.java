package no.ntnu.idatt1002.dao;

import java.util.Collection;
import java.util.List;

/**
 * The classes derived from this class provides an abstraction layer
 * to an arbitrary persistent storage mechanism.
 *
 * @param   <T> the type of the result object
 * @param   <S> the type of the identifier
 */
public interface DAO<T, S> {

  /**
   * Finds the first object in this table whose identifier matches the specified.
   *
   * @param   filter the specified identifier
   * @return  an object
   */
  T find(S filter);

  /**
   * Finds all objects in this table whose identifier is present
   * in the specified filter.
   *
   * @param   filter the filter of object identifiers
   * @return  a list of objects
   */
  List<T> find(Collection<S> filter);

  /**
   * This method is called once per instantiation to prepare the
   * necessary dependencies.
   */
  void init();
}
