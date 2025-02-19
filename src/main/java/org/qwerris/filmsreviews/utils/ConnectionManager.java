package org.qwerris.filmsreviews.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@UtilityClass
public class ConnectionManager {
    private static final BlockingQueue<Connection> POOL = new LinkedBlockingQueue<>();
    private static final String URL = "db.url";
    private static final String USERNAME = "db.user";
    private static final String PASSWORD = "db.password";

    static {
        loadDriver();
        initPool();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initPool() {
        for (int i = 0; i < 10; i++) {
            Connection connection = openConnection();

            POOL.add((Connection) Proxy.newProxyInstance(
                    ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) ->
                            method.getName().equals("close") ?
                                    POOL.add((Connection) proxy) : method.invoke(connection, args)
            ));
        }
    }

    public static Connection getConnection() {
        try {
            return POOL.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection openConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtils.getProperty(URL),
                    PropertiesUtils.getProperty(USERNAME),
                    PropertiesUtils.getProperty(PASSWORD)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
