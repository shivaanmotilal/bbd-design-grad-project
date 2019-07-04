package za.co.bbd.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.bbd.wallet.entity.TransactionAccount;

@Repository
public interface TransactionAccountRepository extends JpaRepository<TransactionAccount, String> {
}
