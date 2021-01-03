package shop.plumeria.plummity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.plumeria.plummity.dao.StandardRatingDAO;

public interface StandardRatingRepository extends JpaRepository<StandardRatingDAO, String> {
}
