package shop.plumeria.plummity.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dao.UserDAO;

import java.util.Date;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageDAO, String> {

    List<ImageDAO> getAllByOwner(UserDAO owner);
    ImageDAO getImageDAOByUuid(String uuid);

    @Query("select images from ImageDAO images where images.owner.uuid not like :useridentifier and images.created  >= :tenDaysAgo")
    List<ImageDAO> getLatest(String useridentifier, Date tenDaysAgo, Sort sort);
}