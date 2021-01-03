package shop.plumeria.plummity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dao.StandardRatingDAO;
import shop.plumeria.plummity.dao.UserDAO;
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

    @Autowired
    private UserDataService userDataService;

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
            ImageDAO imageToSave = ImageDAO.builder().size(file.getSize()).owner(owner).uuid(UUID.randomUUID().toString()).created(new Date())
                    .data(file.getBytes()).mediaType(file.getContentType()).veteranRatings(new ArrayList<>()).standardRatings(new ArrayList<>()).build();
            imageRepository.save(imageToSave);
        } catch (IOException e) {
            log.warn("Couldn't save image for userid {}", useridentifier);
            return false;
        }
        return true;
    }

    public void update(ImageDAO image) {
        if (image == null) {
            log.warn("Couldn't save empty image");
            return;
        }
        imageRepository.save(image);
    }

    public ImageDAO getImageForId(String imageId) {
        return imageRepository.getImageDAOByUuid(imageId);
    }

    public Date get10DaysAgo(){
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        return new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
    }

    public List<String> getLatestStandardImagesForUser(String useridentifier) {
        UserDAO userFromDatabase = userDataService.getLatestUserDAO(useridentifier);
        List<ImageDAO> images = imageRepository.getLatest(useridentifier, get10DaysAgo(), Sort.by(Sort.Direction.ASC, "created"));
        List<String> correctImagesIDs = new ArrayList<>();

        for (ImageDAO image : images) {
            boolean isImageRated = false;
            for (StandardRatingDAO standardRating : image.getStandardRatings()) {
                if (standardRating.getUser().getUuid().equals(userFromDatabase.getUuid())) {
                    isImageRated = true;
                    break;
                }
            }
            if (!isImageRated) {
                correctImagesIDs.add(image.getUuid());
            }
        }
        return correctImagesIDs;
    }

    public Page<ImageDAO> findAllProducts(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

}
