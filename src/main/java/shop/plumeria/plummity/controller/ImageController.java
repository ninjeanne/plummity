package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import shop.plumeria.plummity.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/{imageId:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("imageId") String imageId) {
        byte[] originalData = imageService.displayImage(imageId);
        if (originalData == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] data = imageService.convertToThumbnail(originalData);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
    }

    @RequestMapping(value = "/best", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getBestImageForToday() {
        byte[] data = imageService.bestImage();
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
    }
}
