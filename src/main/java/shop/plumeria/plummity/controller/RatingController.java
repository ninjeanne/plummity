package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.plumeria.plummity.dto.ErrorDTO;
import shop.plumeria.plummity.dto.StandardRatingDTO;
import shop.plumeria.plummity.dto.UserDTO;
import shop.plumeria.plummity.dto.VeteranRatingDTO;
import shop.plumeria.plummity.service.RatingService;
import shop.plumeria.plummity.utils.StandardRatingType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/standard")
    public ResponseEntity<List<ErrorDTO>> standardRateImage(@RequestBody StandardRatingDTO standardRating) {
        List<ErrorDTO> failedRatings = ratingService.rateImagesStandard(standardRating);
        if(!failedRatings.isEmpty()){
            return ResponseEntity.badRequest().body(failedRatings);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/veteran")
    public ResponseEntity<List<ErrorDTO>> veteranRateImage(@RequestBody VeteranRatingDTO veteranRatingDTO) {
        List<ErrorDTO> failedRatings = ratingService.rateImagesVeteran(veteranRatingDTO);
        if(!failedRatings.isEmpty()){
            return ResponseEntity.badRequest().body(failedRatings);
        }
        return ResponseEntity.ok().build();
    }
}
