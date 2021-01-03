package shop.plumeria.plummity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dao.StandardRatingDAO;
import shop.plumeria.plummity.dao.UserDAO;
import shop.plumeria.plummity.dao.VeteranRatingDAO;
import shop.plumeria.plummity.dto.StandardRatingDTO;
import shop.plumeria.plummity.dto.VeteranRatingDTO;
import shop.plumeria.plummity.dto.VeteranRatingEntry;
import shop.plumeria.plummity.repository.VeteranRatingRepository;
import shop.plumeria.plummity.utils.StandardRatingType;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class RatingService {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private VeteranRatingRepository veteranRatingRepository;

    public void rateImagesStandard(StandardRatingDTO standardRating) {
        UserDAO userFromDatabase = userDataService.getLatestUserDAO(standardRating.getUser().getIdentifier());
        for (Map.Entry<String, StandardRatingType> entry : standardRating.getRatings().entrySet()) {
            ImageDAO image = imageService.getImageForId(entry.getKey());
            if (image == null) {
                log.warn("Image with id {} does not exist.", entry.getKey());
                return;
            }
            StandardRatingDAO ratingForDB = StandardRatingDAO.builder().uuid(UUID.randomUUID().toString()).image(image).user(userFromDatabase)
                    .type(entry.getValue()).build();
            image.getStandardRatings().add(ratingForDB);
            imageService.update(image);
        }
    }

    public void rateImagesVeteran(VeteranRatingDTO veteranRating) {
        UserDAO userFromDatabase = userDataService.getLatestUserDAO(veteranRating.getUser().getIdentifier());
        for (VeteranRatingEntry entry : veteranRating.getEntries()) {
            ImageDAO image = imageService.getImageForId(entry.getImageId());
            if(image == null) {
                log.warn("Image with id {} does not exist.", entry.getImageId());
                return;
            }
            VeteranRatingDAO ratingFromDb = veteranRatingRepository.getByUserAndImage(userFromDatabase, image);
            if (ratingFromDb == null) {
                VeteranRatingDAO ratingForDB = VeteranRatingDAO.builder().uuid(UUID.randomUUID().toString()).image(image).user(userFromDatabase)
                        .type(entry.getType()).build();
                image.getVeteranRatings().add(ratingForDB);
            } else {
                ratingFromDb.setType(entry.getType());
            }
            imageService.update(image);
        }
    }

}
