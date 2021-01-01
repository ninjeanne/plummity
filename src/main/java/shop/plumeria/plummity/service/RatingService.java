package shop.plumeria.plummity.service;

import org.springframework.stereotype.Service;
import shop.plumeria.plummity.dto.StandardRatingDTO;
import shop.plumeria.plummity.dto.VeteranRatingDTO;

@Service
public class RatingService {

    public long getLatestPointsForUser(String identifier) {
        //TODO implement
        return 0;
    }

    public boolean rateImagesStandard(StandardRatingDTO standardRating){
        //TODO implement
        return false;
    }

    public boolean rateImagesVeteran(VeteranRatingDTO veteranRating){
        //TODO implement
        return false;
    }

}
