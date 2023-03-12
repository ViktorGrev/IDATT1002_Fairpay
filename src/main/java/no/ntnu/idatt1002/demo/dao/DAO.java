package no.ntnu.idatt1002.demo.dao;

/**
 * The classes derived from this class provides an abstraction layer
 * to an arbitrary persistent storage mechanism.
 */
public abstract class DAO {

    /**
     * This method is called once per instantiation to prepare the
     * necessary dependencies.
     */
    public abstract void setup();
}