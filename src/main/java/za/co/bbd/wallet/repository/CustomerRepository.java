package za.co.bbd.wallet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.bbd.wallet.entity.CustomerEntity;

@Repository("wallet.CustomerRepository")
public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}
