package shop.plumeria.plummity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dao.UserDAO;
import shop.plumeria.plummity.dto.ImageDTO;
import shop.plumeria.plummity.repository.ImageRepository;
import shop.plumeria.plummity.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean saveNewImage(String useridentifier, MultipartFile file) {
        if (useridentifier == null) {
            log.warn("Couldn't save image for missing userid {}", useridentifier);
            return false;
        }
        if (file == null) {
            log.warn("Couldn't save image for userid {} with empty file data", useridentifier);
            return false;
        }

        UserDAO owner = userRepository.getOne(useridentifier);

        try {
            ImageDAO imageToSave = ImageDAO.builder()
                    .size(file.getSize()).owner(owner).uuid(UUID.randomUUID().toString()).created(new Date()).data(file.getBytes())
                    .mediaType(file.getContentType()).build();
            imageRepository.save(imageToSave);
        } catch (IOException e) {
            log.warn("Couldn't save image for userid {}", useridentifier);
            return false;
        }
        return true;
    }

    public void update(ImageDAO image){
        if (image == null) {
            log.warn("Couldn't save empty image");
            return;
        }
        imageRepository.save(image);
    }

    public ImageDAO getImageForId(String imageId) {
       return imageRepository.getOne(imageId);
    }

    public List<ImageDTO> getLatestImagesForUser(String useridentifier) {
        //TODO get latests images via pagination (newest to oldest). Maybe stop loading the very old ones (> 2 Weeks)
        return new ArrayList<ImageDTO>();
    }

}
