package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.plumeria.plummity.dto.StandardRatingDTO;
import shop.plumeria.plummity.dto.UserDTO;
import shop.plumeria.plummity.dto.VeteranRatingDTO;
import shop.plumeria.plummity.service.RatingService;
import shop.plumeria.plummity.utils.StandardRatingType;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/standard")
    public void standardRateImage(@RequestBody StandardRatingDTO standardRating) {
        ratingService.rateImagesStandard(standardRating);
    }

    @PostMapping("/veteran")
    public void veteranRateImage(@RequestBody VeteranRatingDTO veteranRatingDTO) {
        ratingService.rateImagesVeteran(veteranRatingDTO);
    }

    //TODO controller for latest images. See ImageService. Only the latest ones. Maybe a link would be enough as flutter can
    //TODO use network images to show thumbnails and not save images. Needs an active internet connection!

    //TODO set up gitlab ci and the server

}
