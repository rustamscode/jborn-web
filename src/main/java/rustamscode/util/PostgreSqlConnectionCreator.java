package rustamscode.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSqlConnectionCreator {

  private static final Properties properties = new Properties();

  private static final String DB_URL;

  private static final String DB_PASS;

  private static final String DB_USER;

  private final static String appConfigPath = "src/main/resources/app.properties";

  static {
    try {
      properties.load(new FileReader(appConfigPath));

      DB_URL = properties.getProperty("POSTGRES.DB.URL");
      DB_PASS = properties.getProperty("POSTGRES.DB.PASS");
      DB_USER = properties.getProperty("POSTGRES.DB.USER");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
  }
}
