package za.co.bbd.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.bbd.wallet.entity.PaymentEntity;

@Repository("wallet.PaymentRepository")
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
}
