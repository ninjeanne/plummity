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
import shop.plumeria.plummity.utils.StandardRatingType;
import shop.plumeria.plummity.utils.VeteranRatingType;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class RatingService {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private ImageService imageService;

    public void rateImagesStandard(StandardRatingDTO standardRating) {
        UserDAO userFromDatabase = userDataService.getLatestUserDAO(standardRating.getUser().getIdentifier());
        for (Map.Entry<String, StandardRatingType> entry : standardRating.getRatings().entrySet()) {
            ImageDAO image = imageService.getImageForId(entry.getKey());
            StandardRatingDAO ratingForDB = StandardRatingDAO.builder().uuid(UUID.randomUUID().toString()).image(image).user(userFromDatabase)
                    .type(entry.getValue()).build();
            image.getStandardRatings().add(ratingForDB);
            imageService.update(image);
        }
    }

    public void rateImagesVeteran(VeteranRatingDTO veteranRating) {
        UserDAO userFromDatabase = userDataService.getLatestUserDAO(veteranRating.getUser().getIdentifier());
        for (Map.Entry<String, VeteranRatingType> entry : veteranRating.getRatings().entrySet()) {
            ImageDAO image = imageService.getImageForId(entry.getKey());
            VeteranRatingDAO ratingForDB = VeteranRatingDAO.builder().uuid(UUID.randomUUID().toString()).image(image).user(userFromDatabase)
                    .type(entry.getValue()).build();
            image.getVeteranRatings().add(ratingForDB);
            imageService.update(image);
        }
    }

}
