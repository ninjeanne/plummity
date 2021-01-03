package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dto.ErrorDTO;
import shop.plumeria.plummity.dto.UserDTO;
import shop.plumeria.plummity.dto.VeteranRatingEntry;
import shop.plumeria.plummity.service.ImageService;
import shop.plumeria.plummity.service.UserDataService;

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

    @PostMapping("/{useridentifier}/image")
    public ResponseEntity<ErrorDTO> saveStandardImageForUser(@PathVariable String useridentifier, @RequestParam("file") MultipartFile file) {
        ErrorDTO error = imageService.saveNewImage(useridentifier, file);
        if (error == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(error);
    }

    @GetMapping(value = "/{useridentifier}/standard/images")
    public ResponseEntity<Slice<String>> getNewestStandardImageIds(@PageableDefault(sort = { "created" }, direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable("useridentifier") String useridentifier) {
        Slice<String> imagesForUser = imageService.getLatestStandardImagesForUser(pageable, useridentifier);
        return ResponseEntity.ok().body(imagesForUser);
    }

    @GetMapping(value = "/{useridentifier}/veteran/images")
    public ResponseEntity<Slice<VeteranRatingEntry>> getVeteranImageIds(Pageable pageable, @PathVariable("useridentifier") String useridentifier) {
        Slice<VeteranRatingEntry> imagesForUser = imageService.getVeteranImagesForUser(pageable, useridentifier);
        return ResponseEntity.ok().body(imagesForUser);
    }

}
