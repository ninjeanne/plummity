package shop.plumeria.plummity.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.plumeria.plummity.dto.ImageDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    public boolean saveNewImage(String useridentifier, MultipartFile file) {
        //TODO save a new image
        return false;
    }

    public List<ImageDTO> getLatestImagesForUser(String useridentifier) {
        //TODO get latests images via pagination (newest to oldest). Maybe stop loading the very old ones (> 2 Weeks)
        return new ArrayList<ImageDTO>();
    }

}
