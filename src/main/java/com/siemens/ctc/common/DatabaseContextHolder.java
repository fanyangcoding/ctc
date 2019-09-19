package com.siemens.ctc.common;

public class DatabaseContextHolder {
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType databaseType) {
        contextHolder.set(databaseType);
    }

    public static DatabaseType getDatabaseType() {
        return contextHolder.get();
    }

    public static void clearDatabaseType() {
        contextHolder.remove();
    }
}
