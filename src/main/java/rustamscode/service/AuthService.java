package rustamscode.service;

import org.apache.commons.codec.digest.DigestUtils;
import rustamscode.dao.UserDao;
import rustamscode.model.UserCredentials;
import rustamscode.model.UserEntity;

import java.util.Optional;

public class AuthService {

  private final CredentialReader credentialReader;

  private final UserDao userDao;

  public AuthService(CredentialReader credentialReader, UserDao userDao) {
    this.credentialReader = credentialReader;
    this.userDao = userDao;
  }

  public UserEntity login() {
    while (true) {
      UserCredentials userCredentials = credentialReader.readCredentials();

      Optional<UserEntity> userOptional = userDao.getUserByEmailAndPassword(
          DigestUtils.md5Hex(userCredentials.getEmail()).toUpperCase(),
          DigestUtils.md5Hex(userCredentials.getPassword()).toUpperCase());

      if (userOptional.isPresent()) {
        return userOptional.get();
      }

      System.out.println("Email or password is incorrect.\nPlease try again\n________________________________________");
    }
  }

  public UserEntity register() {
    UserCredentials userCredentials = credentialReader.readCredentials();

    String emailMd5 = DigestUtils.md5Hex(userCredentials.getEmail()).toUpperCase();
    String passwordMd5 = DigestUtils.md5Hex(userCredentials.getPassword()).toUpperCase();

    if (userDao.getUserByEmail(emailMd5).isPresent()) {
      throw new IllegalArgumentException("User with this email already exists.");
    }

    UserEntity newUser = new UserEntity();
    newUser.setEmail(emailMd5);
    newUser.setPassword(passwordMd5);

    Long newUserId = userDao.create(newUser);

    System.out.println("Registration is successful!");
    newUser.setId(newUserId);

    return newUser;
  }
}
