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
        return convertToDTO(getLatestUserDAO(useridentifier));
    }

    public void updatePointsForUser(UserDAO user){
        long points = calculatePointsForUser(user);
        user.setPoints(points);
        userRepository.save(user);
        log.warn("Updated user {} with points {}", user.getUuid(), points);
        promoteUser(user);
    }

    public void syncUser(String useridentifier){
        UserDAO userFromDatabase = getLatestUserDAO(useridentifier);
        updatePointsForUser(userFromDatabase);
    }

    UserDAO getLatestUserDAO(String useridentifier) {
        if (!userRepository.existsById(useridentifier)) {
            createUser(useridentifier);
        }
        return userRepository.findByUuid(useridentifier);
    }

    private UserDTO convertToDTO(UserDAO dao) {
        return UserDTO.builder().identifier(dao.getUuid()).points(dao.getPoints()).isVeteran(dao.isVeteran()).build();
    }

    private long calculatePointsForUser(UserDAO user) {
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
        UserDAO userDAO = UserDAO.builder().uuid(useridentifier).isVeteran(false).points(0).build();
        userRepository.save(userDAO);
        log.warn("User with userid {} has been created", useridentifier);
        return true;
    }

    private boolean checkVeteranPromotion(long points) {
        return points >= 20000;
    }

    private void promoteUser(UserDAO user) {
        if (!user.isVeteran()) {
            boolean becomesVeteran = checkVeteranPromotion(user.getPoints());
            if (becomesVeteran) {
                user.setVeteran(true);
                userRepository.save(user);
                log.warn("User with id {} is now veteran", user.getUuid());
            }
        }
    }
}
