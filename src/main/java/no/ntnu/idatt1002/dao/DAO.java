package no.ntnu.idatt1002.dao;

/**
 * The classes derived from this class provides an abstraction layer
 * to an arbitrary persistent storage mechanism.
 */
public abstract class DAO {

    protected static final String ERROR_MSG = "Failed to handle data";

    /**
     * This method is called once per instantiation to prepare the
     * necessary dependencies.
     */
    public abstract void setup();
}