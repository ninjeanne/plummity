package shop.plumeria.plummity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dao.UserDAO;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageDAO, String> {

    List<ImageDAO> getImageDAOByOwner(UserDAO owner);
}
