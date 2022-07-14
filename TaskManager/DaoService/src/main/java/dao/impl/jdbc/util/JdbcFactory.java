package dao.impl.jdbc.util;

import dao.impl.helper.PropertiesHelper;
import dao.impl.jdbc.JdbcDAOFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcFactory {
    private static PropertiesHelper propertiesHelper = new PropertiesHelper("hibernate.properties");

    private static JdbcFactory connectionFactory = null;

    public JdbcFactory() {
        try {
            Class.forName(propertiesHelper.getProperties().getProperty("Driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connecting to database
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        String url = propertiesHelper.getProperties().getProperty("URL");
        String user = propertiesHelper.getProperties().getProperty("USER");
        String password = propertiesHelper.getProperties().getProperty("PASSWORD");

        Connection conn = null;
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static JdbcFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new JdbcFactory();
        }
        return connectionFactory;
    }
}
