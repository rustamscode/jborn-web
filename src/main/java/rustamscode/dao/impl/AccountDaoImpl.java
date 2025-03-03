package rustamscode.dao.impl;

import rustamscode.dao.AccountDao;
import rustamscode.exception.DaoException;
import rustamscode.model.AccountEntity;
import rustamscode.util.PostgreSqlConnectionCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class AccountDaoImpl implements AccountDao {

  private static final Logger logger = Logger.getLogger("logger");

  private final static String SELECT_ALL_ACCOUNTS = "SELECT * FROM accounts";

  private final static String SELECT_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE id = ?";

  private final static String DELETE_ACCOUNT_BY_ID = "DELETE FROM accounts WHERE id = ?";

  private final static String CREATE_ACCOUNT = "INSERT INTO accounts(name, account_id, balance, date) VALUES(?, ?, ?, ?)";

  private final static String UPDATE_ACCOUNT = "UPDATE accounts SET name = ?, account_id = ?, balance = ?, date = ? WHERE id = ?";

  private final static String SELECT_ACCOUNTS_BY_USER_ID = "SELECT * FROM accounts WHERE user_id = ?";

  public static final String ID_COLUMN = "id";

  public static final String NAME_COLUMN = "name";

  public static final String USER_ID_COLUMN = "user_id";

  public static final String BALANCE_COLUMN = "balance";

  public static final String DATE_COLUMN = "date";


  @Override
  public List<AccountEntity> findAll() throws DaoException {
    List<AccountEntity> accounts = new ArrayList<>();
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ACCOUNTS);
         ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        accounts.add(mapResultSetToEntity(resultSet));
      }

      return accounts;
    } catch (SQLException e) {
      logger.severe("Problem finding all accounts");
      throw new DaoException(e);
    }
  }

  @Override
  public Optional<AccountEntity> findEntityById(Long id) throws DaoException {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID)) {
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        return Optional.of(mapResultSetToEntity(resultSet));
      } else {
        logger.warning("Account does not exist");
        return Optional.empty();
      }
    } catch (SQLException e) {
      logger.severe("Problem finding account by id");
      throw new DaoException(e);
    }
  }

  @Override
  public void delete(Long id) throws DaoException {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT_BY_ID)) {
      preparedStatement.setLong(1, id);

      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows == 0) {
        throw new DaoException("Creating account failed, no rows affected.");
      }

      logger.warning("Account was successfully deleted");
    } catch (SQLException e) {
      logger.severe("Problem deleting account by id");
      throw new DaoException(e);
    }
  }

  @Override
  public Long create(AccountEntity account) throws DaoException {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, account.getName());
      preparedStatement.setLong(2, account.getUserId());
      preparedStatement.setBigDecimal(3, account.getBalance());
      preparedStatement.setObject(4, account.getDate());

      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows == 0) {
        throw new DaoException("Creating account failed, no rows affected.");
      }

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        return generatedKeys.getLong(ID_COLUMN);
      } else {
        logger.warning("Failed to retrieve generated ID");
        throw new RuntimeException();
      }
    } catch (SQLException e) {
      logger.severe("Problem creating account");
      throw new DaoException(e);
    }
  }

  @Override
  public AccountEntity update(AccountEntity account) throws DaoException {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT)) {
      preparedStatement.setString(1, account.getName());
      preparedStatement.setLong(2, account.getUserId());
      preparedStatement.setBigDecimal(3, account.getBalance());
      preparedStatement.setObject(4, account.getDate());
      preparedStatement.setLong(5, account.getId());

      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows == 0) {
        throw new DaoException("Updating account failed, no rows affected.");
      }

      return account;
    } catch (SQLException e) {
      logger.severe("Problem updating account");
      throw new DaoException(e);
    }
  }

  @Override
  public List<AccountEntity> getAccountsByUserId(Long id) throws DaoException {
    try (Connection connection = PostgreSqlConnectionCreator.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNTS_BY_USER_ID)) {
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      List<AccountEntity> accounts = new ArrayList<>();

      while (resultSet.next()) {
        accounts.add(mapResultSetToEntity(resultSet));
      }

      return accounts;
    } catch (SQLException e) {
      logger.severe("Problem getting account by user id");
      throw new DaoException(e);
    }
  }

  private AccountEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
    AccountEntity account = new AccountEntity();
    account.setId(resultSet.getLong(ID_COLUMN));
    account.setName(resultSet.getString(NAME_COLUMN));
    account.setUserId(resultSet.getLong(USER_ID_COLUMN));
    account.setBalance(resultSet.getBigDecimal(BALANCE_COLUMN));
    account.setDate(resultSet.getObject(DATE_COLUMN, LocalDate.class));
    return account;
  }
}
