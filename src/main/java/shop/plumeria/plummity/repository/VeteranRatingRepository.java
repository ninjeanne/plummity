package shop.plumeria.plummity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.plumeria.plummity.dao.VeteranRatingDAO;

public interface VeteranRatingRepository extends JpaRepository<VeteranRatingDAO, String> {
}
