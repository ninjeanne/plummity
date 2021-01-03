package shop.plumeria.plummity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dao.StandardRatingDAO;
import shop.plumeria.plummity.dao.UserDAO;
import shop.plumeria.plummity.dao.VeteranRatingDAO;
import shop.plumeria.plummity.dto.ErrorDTO;
import shop.plumeria.plummity.dto.StandardRatingDTO;
import shop.plumeria.plummity.dto.VeteranRatingDTO;
import shop.plumeria.plummity.dto.VeteranRatingEntry;
import shop.plumeria.plummity.repository.VeteranRatingRepository;
import shop.plumeria.plummity.utils.StandardRatingType;

import java.util.ArrayList;
import java.util.List;
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

    public List<ErrorDTO> rateImagesStandard(StandardRatingDTO standardRating) {
        List<ErrorDTO> failedRating = new ArrayList<>();
        if(standardRating.getUseridentifier() == null) {
            failedRating.add(ErrorDTO.builder().message("User hasn't been sent").data(standardRating).build());
            return failedRating;
        }
        UserDAO userFromDatabase = userDataService.getLatestUserDAO(standardRating.getUseridentifier());
        for (Map.Entry<String, StandardRatingType> entry : standardRating.getRatings().entrySet()) {
            ImageDAO image = imageService.getImageForId(entry.getKey());
            if (image == null) {
                log.warn("Image with id {} does not exist.", entry.getKey());
                failedRating.add(ErrorDTO.builder().message("Image doesn't exist with this id").data(entry).build());
                continue;
            }
            StandardRatingDAO ratingForDB = StandardRatingDAO.builder().uuid(UUID.randomUUID().toString()).image(image).user(userFromDatabase)
                    .type(entry.getValue()).build();
            image.getStandardRatings().add(ratingForDB);
            imageService.update(image);
        }
        return failedRating;
    }

    public List<ErrorDTO> rateImagesVeteran(VeteranRatingDTO veteranRating) {
        List<ErrorDTO> failedRating = new ArrayList<>();
        if(veteranRating.getUseridentifier() == null) {
            failedRating.add(ErrorDTO.builder().message("User hasn't been sent").data(veteranRating).build());
            return failedRating;
        }
        UserDAO userFromDatabase = userDataService.getLatestUserDAO(veteranRating.getUseridentifier());
        for (VeteranRatingEntry entry : veteranRating.getEntries()) {
            ImageDAO image = imageService.getImageForId(entry.getImageId());
            if (image == null) {
                log.warn("Image with id {} does not exist.", entry.getImageId());
                failedRating.add(ErrorDTO.builder().message("Image doesn't exist with this id").data(entry).build());
                continue;
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
        return failedRating;
    }

}
