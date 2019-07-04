package za.co.bbd.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.bbd.wallet.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
