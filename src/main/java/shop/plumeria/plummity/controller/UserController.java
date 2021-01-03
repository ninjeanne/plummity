package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dto.UserDTO;
import shop.plumeria.plummity.service.ImageService;
import shop.plumeria.plummity.service.UserDataService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/{useridentifier}")
    public UserDTO getLatestUserData(@PathVariable String useridentifier) {
        System.out.println(UUID.randomUUID());
        userDataService.syncUser(useridentifier);
        return userDataService.getLatestUserDTO(useridentifier);
    }

    @PostMapping("/{useridentifier}/image")
    public boolean saveImageDataForUser(@PathVariable String useridentifier, @RequestParam("file") MultipartFile file) {
        return imageService.saveNewImage(useridentifier, file);
    }

    @GetMapping(value = "/{useridentifier}/images")
    public ResponseEntity<List<String>> getNewestImageIds(@PathVariable("useridentifier") String useridentifier) {
        List<String> imagesForUser = imageService.getLatestStandardImagesForUser(useridentifier);
        return ResponseEntity.ok().body(imagesForUser);
    }

}
