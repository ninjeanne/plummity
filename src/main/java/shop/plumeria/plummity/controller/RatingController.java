package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dto.StandardRatingDTO;
import shop.plumeria.plummity.dto.UserDTO;
import shop.plumeria.plummity.dto.VeteranRatingDTO;
import shop.plumeria.plummity.service.ImageService;
import shop.plumeria.plummity.service.RatingService;
import shop.plumeria.plummity.service.UserDataService;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ImageService imageService;

    @PostMapping("/standard")
    public void standardRateImage(StandardRatingDTO standardRating) {
        ratingService.rateImagesStandard(standardRating);
    }

    @PostMapping("/veteran")
    public void veteranRateImage(VeteranRatingDTO veteranRatingDTO) {
        ratingService.rateImagesVeteran(veteranRatingDTO);
    }

    //TODO controller for ratings (preferable would be batches, check how flutter can send those)
    //TODO controller for latest images. See ImageService. Only the latest ones. Maybe a link would be enough as flutter can
    //TODO use network images to show thumbnails and not save images. Needs an active internet connection!

    //TODO set up gitlab ci and the server

}
