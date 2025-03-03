package rustamscode.dao;

import rustamscode.model.UserEntity;

import java.util.Optional;

public interface UserDao extends BaseDao<UserEntity, Long> {
  Optional<UserEntity> getUserByEmailAndPassword(String email, String password);

  Optional<UserEntity> getUserByEmail(String email);
}
