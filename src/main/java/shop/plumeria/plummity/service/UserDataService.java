package shop.plumeria.plummity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.plumeria.plummity.dao.ImageDAO;
import shop.plumeria.plummity.dao.StandardRatingDAO;
import shop.plumeria.plummity.dao.UserDAO;
import shop.plumeria.plummity.dao.VeteranRatingDAO;
import shop.plumeria.plummity.dto.UserDTO;
import shop.plumeria.plummity.repository.ImageRepository;
import shop.plumeria.plummity.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    public UserDTO getLatestUserDTO(String useridentifier) {
        UserDAO userFromDB = getLatestUserDAO(useridentifier);
        long plummityPoints = calculatePlummityPointsForUser(userFromDB);
        UserDTO userToSend = convertToDTO(userFromDB);
        userToSend.setPoints(userFromDB.getFarmpoints() + plummityPoints);
        return userToSend;
    }

    public void updateFarmpointsForUser(String useridentifier, long newFarmPoints){
        UserDAO userFromDB = getLatestUserDAO(useridentifier);
        userFromDB.setFarmpoints(userFromDB.getFarmpoints() + newFarmPoints);
        userRepository.save(userFromDB);
        log.warn("Updated user {} with new farmpoints {}", userFromDB.getUseridentifier(), newFarmPoints);
        promoteUser(userFromDB);
    }

    UserDAO getLatestUserDAO(String useridentifier) {
        if (!userRepository.existsById(useridentifier)) {
            createUser(useridentifier);
        }
        return userRepository.findByUseridentifier(useridentifier);
    }

    private UserDTO convertToDTO(UserDAO dao) {
        return UserDTO.builder().identifier(dao.getUseridentifier()).points(0).isVeteran(dao.isVeteran()).build();
    }

    private long calculatePlummityPointsForUser(UserDAO user) {
        List<ImageDAO> images = imageRepository.getAllByOwner(user);
        long result = 0;

        for (ImageDAO image : images) {
            List<StandardRatingDAO> standardRatings = image.getStandardRatings();
            for (StandardRatingDAO standardRating : standardRatings) {
                result += standardRating.getType().value;
            }
            List<VeteranRatingDAO> veteranRatings = image.getVeteranRatings();
            for (VeteranRatingDAO veteranRating : veteranRatings) {
                result += veteranRating.getType().value;
            }

        }
        return result;
    }

    private boolean createUser(String useridentifier) {
        if (useridentifier == null) {
            log.warn("Couldn't create user with uuid {}", useridentifier);
            return false;
        }
        UserDAO userDAO = UserDAO.builder().useridentifier(useridentifier).isVeteran(false).farmpoints(0).build();
        userRepository.save(userDAO);
        log.warn("User with userid {} has been created", useridentifier);
        return true;
    }

    private boolean checkVeteranPromotion(long points) {
        return points >= 20000;
    }

    private void promoteUser(UserDAO user) {
        if (!user.isVeteran()) {
            boolean becomesVeteran = checkVeteranPromotion(user.getFarmpoints());
            if (becomesVeteran) {
                user.setVeteran(true);
                userRepository.save(user);
                log.warn("User with id {} is now veteran", user.getUseridentifier());
            }
        }
    }
}
