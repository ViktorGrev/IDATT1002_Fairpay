package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.dao.sqlite.SqlUserDAO;
import no.ntnu.idatt1002.dao.sqlite.SqlGroupDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class Database {

    public static final Logger logger = Logger.getLogger(Database.class.getSimpleName());
    private static final Map<Class<?>, DAO<?, ?>> daoMap = new HashMap<>();

    /**
     * Prepares the registered DAO classes.
     */
    private static void init() {
        for(DAO<?, ?> dao : daoMap.values()) {
            logger.info("Loading " + dao.getClass().getSimpleName());
            dao.init();
        }
    }

    /**
     * Registers a new DAO class.
     * @param   daoClass the type of the DAO class
     * @param   dao the DAO class
     */
    private static void registerDAO(Class<?> daoClass, DAO<?, ?> dao) {
        logger.info("Registering implementation " + dao.getClass().getSimpleName() + " for " + daoClass.getSimpleName());
        daoMap.put(daoClass, dao);
    }

    /**
     * Returns a DAO implementation.
     * @param   daoClass the DAO class
     * @return  a DAO implementation class
     * @param   <T> the type of the DAO
     * @throws  ClassCastException if the registered DAO is not of type T
     */
    public static <T> T getDAO(Class<T> daoClass) {
        if(!daoMap.containsKey(daoClass))
            throw new IllegalArgumentException(daoClass.getSimpleName() + " is not a registered DAO");
        return daoClass.cast(daoMap.get(daoClass));
    }

    static {
        // Registering default DAO classes and initializing dependencies
        registerDAO(UserDAO.class, new SqlUserDAO());
        registerDAO(GroupDAO.class, new SqlGroupDAO());
        init();
    }
}