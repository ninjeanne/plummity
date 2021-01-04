package shop.plumeria.plummity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dao.StandardRatingDAO;
import shop.plumeria.plummity.dao.UserDAO;
import shop.plumeria.plummity.dto.ErrorDTO;
import shop.plumeria.plummity.dto.VeteranRatingEntry;
import shop.plumeria.plummity.repository.ImageRepository;
import shop.plumeria.plummity.repository.VeteranRatingRepository;
import shop.plumeria.plummity.utils.VeteranRatingType;

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
    private UserDataService userDataService;

    @Autowired
    private VeteranRatingRepository veteranRatingRepository;

    public ErrorDTO saveNewImage(String useridentifier, MultipartFile file) {
        return saveNewImageWithId(useridentifier, UUID.randomUUID().toString(), file);
    }

    public ErrorDTO saveNewImageWithId(String useridentifier, String imageId, MultipartFile file) {
        if (useridentifier == null) {
            log.warn("Couldn't save image for missing userid {}", useridentifier);
            return ErrorDTO.builder().message("Couldn't save image for missing userid").data(useridentifier).build();
        }
        if (imageId == null) {
            log.warn("Couldn't save image for missing imageId {}", imageId);
            return ErrorDTO.builder().message("Couldn't save image for missing imageId").data(imageId).build();
        }
        if (file == null) {
            log.warn("Couldn't save image for userid {} with empty file data", useridentifier);
            return ErrorDTO.builder().message("Couldn't save image for missing file data").data(file).build();
        }

        UserDAO owner = userDataService.getLatestUserDAO(useridentifier);

        try {
            ImageDAO imageToSave = ImageDAO.builder().size(file.getSize()).owner(owner).uuid(imageId).created(new Date()).data(file.getBytes())
                    .mediaType(file.getContentType()).veteranRatings(new ArrayList<>()).standardRatings(new ArrayList<>()).build();
            imageRepository.save(imageToSave);
        } catch (IOException e) {
            log.warn("Couldn't save image for userid {}", useridentifier);
            return ErrorDTO.builder().message("Couldn't save image for userid").data(useridentifier).build();
        }
        return null;
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

    public byte[] displayImage(String imageId) {
        ImageDAO imageDAO = getImageForId(imageId);
        if (imageDAO == null) {
            log.warn("Image with id {} couldn't be found.", imageId);
            return null;
        }
        return imageDAO.getData();
    }

    public static Date get10DaysAgo() {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        return new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
    }

    public Slice<String> getLatestStandardImagesForUser(Pageable pageable, String useridentifier) {
        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "created"));
        UserDAO userFromDatabase = userDataService.getLatestUserDAO(useridentifier);
        Slice<ImageDAO> images = imageRepository.getLatest(get10DaysAgo(), pageable);
        List<String> correctImagesIDs = new ArrayList<>();

        for (ImageDAO image : images) {
            boolean isImageRated = false;
            for (StandardRatingDAO standardRating : image.getStandardRatings()) {
                if (standardRating.getUser().getUseridentifier().equals(userFromDatabase.getUseridentifier())) {
                    isImageRated = true;
                    break;
                }
            }
            if (!isImageRated) {
                correctImagesIDs.add(image.getUuid());
            }
        }
        return new SliceImpl<>(correctImagesIDs, pageable, images.hasNext());
    }

    public Slice<VeteranRatingEntry> getVeteranImagesForUser(Pageable pageable, String useridentifier) {
        Slice<ImageDAO> allVeteranImages = imageRepository.getAllImagesForVeteran(pageable);
        List<VeteranRatingEntry> completeTypes = new ArrayList<>();

        for (ImageDAO image : allVeteranImages) {
            VeteranRatingType type = veteranRatingRepository.getTypeIfExists(useridentifier, image.getUuid());
            if (type != null) {
                completeTypes.add(new VeteranRatingEntry(image.getUuid(), type));
            } else {
                completeTypes.add(new VeteranRatingEntry(image.getUuid(), VeteranRatingType.zero));
            }
        }

        return new SliceImpl<>(completeTypes, pageable, allVeteranImages.hasNext());
    }

}
