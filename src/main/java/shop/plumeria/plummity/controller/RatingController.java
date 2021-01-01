package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dto.UserDTO;
import shop.plumeria.plummity.service.ImageService;
import shop.plumeria.plummity.service.UserDataService;

@RestController
@RequestMapping("/user")
public class RatingController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/{useridentifier}")
    public UserDTO getLatestUserData(@PathVariable String useridentifier) {
        return userDataService.getLatestUserData(useridentifier);
    }

    @PostMapping("/{useridentifier}/image")
    public boolean saveImageDataForUser(@PathVariable String useridentifier, @RequestParam("file") MultipartFile file) {
        return imageService.saveNewImage(useridentifier, file);
    }

    //TODO controller for ratings (preferable would be batches, check how flutter can send those)
    //TODO controller for latest images. See ImageService. Only the latest ones. Maybe a link would be enough as flutter can
    //TODO use network images to show thumbnails and not save images. Needs an active internet connection!

}
