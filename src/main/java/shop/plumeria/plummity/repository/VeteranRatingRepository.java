package shop.plumeria.plummity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dao.UserDAO;
import shop.plumeria.plummity.dao.VeteranRatingDAO;
import shop.plumeria.plummity.utils.VeteranRatingType;

public interface VeteranRatingRepository extends JpaRepository<VeteranRatingDAO, String> {
    @Query("select dao.type from VeteranRatingDAO dao where dao.user.uuid like :userId and dao.image.uuid like :imageId")
    VeteranRatingType getTypeIfExists(String userId, String imageId);

    VeteranRatingDAO getByUserAndImage(UserDAO user, ImageDAO image);

}
