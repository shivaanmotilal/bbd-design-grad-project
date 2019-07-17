package za.co.bbd.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.bbd.wallet.entity.TransactionEntity;

@Repository("wallet.TransactionRepository")
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
}
