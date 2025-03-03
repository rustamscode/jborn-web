package rustamscode.dao;

import rustamscode.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public interface BaseDao<T, K> {
  Logger logger = Logger.getLogger("logger");

  List<T> findAll() throws DaoException;

  Optional<T> findEntityById(K id) throws DaoException;

  void delete(K id) throws DaoException;

  K create(T t) throws DaoException;

  T update(T t) throws DaoException;

  default void close(Statement statement) {
    try {
      if (statement != null) {
        statement.close();
      }
    } catch (SQLException e) {
      logger.info(String.format("Problem closing statement %s", e.getMessage()));
    }
  }

  default void close(Connection connection) {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      logger.info(String.format("Problem closing connection %s", e.getMessage()));
    }
  }

}
