package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dto.UserDTO;
import shop.plumeria.plummity.dto.VeteranRatingEntry;
import shop.plumeria.plummity.service.ImageService;
import shop.plumeria.plummity.service.UserDataService;
import shop.plumeria.plummity.utils.VeteranRatingType;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/{useridentifier}")
    public UserDTO getLatestUserData(@PathVariable String useridentifier) {
        userDataService.syncUser(useridentifier);
        return userDataService.getLatestUserDTO(useridentifier);
    }

    @PostMapping("/{useridentifier}/standard/image")
    public boolean saveStandardImageForUser(@PathVariable String useridentifier, @RequestParam("file") MultipartFile file) {
        return imageService.saveNewImage(useridentifier, file);
    }

    @GetMapping(value = "/{useridentifier}/standard/images")
    public ResponseEntity<List<String>> getNewestStandardImageIds(@PathVariable("useridentifier") String useridentifier) {
        List<String> imagesForUser = imageService.getLatestStandardImagesForUser(useridentifier);
        return ResponseEntity.ok().body(imagesForUser);
    }

    @GetMapping(value = "/{useridentifier}/veteran/images")
    public ResponseEntity<Slice<VeteranRatingEntry>> getVeteranImageIds(Pageable pageable, @PathVariable("useridentifier") String useridentifier) {
        Slice<VeteranRatingEntry> imagesForUser = imageService.getVeteranImagesForUser(pageable, useridentifier);
        return ResponseEntity.ok().body(imagesForUser);
    }

}
