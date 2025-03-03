package rustamscode.dao;

import rustamscode.model.AccountEntity;

import java.util.List;

public interface AccountDao extends BaseDao<AccountEntity, Long> {

  List<AccountEntity> getAccountsByUserId(Long id);
}
