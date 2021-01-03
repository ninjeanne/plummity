package shop.plumeria.plummity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.plumeria.plummity.dao.UserDAO;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, String> {

}
