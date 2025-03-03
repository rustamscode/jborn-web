package rustamscode.dao.impl;

import rustamscode.dao.UserDao;
import rustamscode.exception.DaoException;
import rustamscode.model.UserEntity;
import rustamscode.util.PostgreSqlConnectionCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDao {

  private static final Logger logger = Logger.getLogger("logger");

  private final static String SELECT_ALL_USERS = "SELECT * FROM users";

  private final static String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

  private final static String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

  private final static String CREATE_USER = "INSERT INTO users(email, password) VALUES(?, ?)";

  private final static String UPDATE_USER = "UPDATE users SET email = ?, password = ? WHERE id = ?";

  private final static String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";

  private final static String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";

  public static final String ID_COLUMN = "id";

  public static final String EMAIL_COLUMN = "email";

  public static final String PASSWORD_COLUMN = "password";


  @Override
  public List<UserEntity> findAll() throws DaoException {
    List<UserEntity> users = new ArrayList<>();
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
         ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        users.add(mapResultSetToEntity(resultSet));
      }

      return users;
    } catch (SQLException e) {
      logger.severe("Problem finding all users");
      throw new DaoException(e);
    }
  }

  @Override
  public Optional<UserEntity> findEntityById(Long id) throws DaoException {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        return Optional.of(mapResultSetToEntity(resultSet));
      } else {
        logger.warning("User does not exist");
        return Optional.empty();
      }
    } catch (SQLException e) {
      logger.severe("Problem finding user by id");
      throw new DaoException(e);
    }
  }

  @Override
  public void delete(Long id) throws DaoException {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID)) {
      preparedStatement.setLong(1, id);

      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows == 0) {
        throw new DaoException("Updating account failed, no rows affected.");
      }

      logger.warning("User was successfully deleted");
    } catch (SQLException e) {
      logger.severe("Problem deleting user by id");
      throw new DaoException(e);
    }
  }

  @Override
  public Long create(UserEntity userEntity) throws DaoException {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, userEntity.getEmail());
      preparedStatement.setString(2, userEntity.getPassword());

      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows == 0) {
        throw new DaoException("Updating account failed, no rows affected.");
      }

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        return generatedKeys.getLong(ID_COLUMN);
      } else {
        logger.warning("Failed to retrieve generated ID");
        throw new RuntimeException();
      }
    } catch (SQLException e) {
      logger.severe("Problem creating user");
      throw new DaoException(e);
    }
  }

  @Override
  public UserEntity update(UserEntity user) throws DaoException {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
      preparedStatement.setString(1, user.getEmail());
      preparedStatement.setString(2, user.getPassword());
      preparedStatement.setLong(3, user.getId());

      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows == 0) {
        throw new DaoException("Updating account failed, no rows affected.");
      }

      return user;
    } catch (SQLException e) {
      logger.severe("Problem updating user");
      throw new DaoException(e);
    }
  }

  @Override
  public Optional<UserEntity> getUserByEmailAndPassword(String email, String password) {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        return Optional.of(mapResultSetToEntity(resultSet));
      } else {
        logger.warning("User with provided email and password is not found");
        return Optional.empty();
      }
    } catch (SQLException e) {
      logger.severe("Problem finding user by email and password");
      throw new DaoException(e);
    }
  }

  @Override
  public Optional<UserEntity> getUserByEmail(String email) {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
      preparedStatement.setString(1, email);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        return Optional.of(mapResultSetToEntity(resultSet));
      } else {
        logger.info("User with provided email is not found");
        return Optional.empty();
      }

    } catch (SQLException e) {
      logger.severe("Problem finding user by email and password");
      throw new DaoException(e);
    }
  }

  private UserEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
    UserEntity user = new UserEntity();
    user.setId(resultSet.getLong(ID_COLUMN));
    user.setEmail(resultSet.getString(EMAIL_COLUMN));
    user.setPassword(resultSet.getString(PASSWORD_COLUMN));

    return user;
  }
}
