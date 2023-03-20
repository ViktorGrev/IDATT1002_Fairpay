package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.dao.sqlite.SQLiteUserDAO;
import no.ntnu.idatt1002.dao.sqlite.SQLiteGroupDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class Database {

    public static final Logger logger = Logger.getLogger(Database.class.getSimpleName());
    private static final Map<Class<?>, DAO> daoMap = new HashMap<>();

    private static void setup() {
        for(DAO dao : daoMap.values()) {
            logger.info("Loading " + dao.getClass().getSimpleName());
            dao.setup();
        }
    }

    public static UserDAO getUserDAO() {
        return getDAO(UserDAO.class);
    }

    public static GroupDAO getGroupDAO() {
        return getDAO(GroupDAO.class);
    }

    private static void registerDAO(Class<?> daoClass, DAO dao) {
        logger.info("Registering implementation " + dao.getClass().getSimpleName() + " for " + daoClass.getSimpleName());
        daoMap.put(daoClass, dao);
    }

    private static <T> T getDAO(Class<T> daoClass) {
        if(!daoMap.containsKey(daoClass))
            throw new IllegalArgumentException(daoClass.getSimpleName() + " is not a registered DAO");
        return daoClass.cast(daoMap.get(daoClass));
    }

    static {
        registerDAO(UserDAO.class, new SQLiteUserDAO());
        registerDAO(GroupDAO.class, new SQLiteGroupDAO());
        setup();
    }
}