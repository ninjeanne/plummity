package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dto.ImageDTO;
import shop.plumeria.plummity.dto.UserDTO;
import shop.plumeria.plummity.service.ImageService;
import shop.plumeria.plummity.service.UserDataService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

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

    @GetMapping("/{useridentifier}/image")
    public List<ImageDTO> getLatestImagesForUser(@PathVariable String useridentifier) {
        return imageService.getLatestImagesForUser(useridentifier);
    }

}
