package za.co.bbd.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.bbd.wallet.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
