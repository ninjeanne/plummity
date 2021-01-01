package shop.plumeria.plummity.service;

import org.springframework.stereotype.Service;
import shop.plumeria.plummity.dto.UserDTO;

@Service
public class UserDataService {
    public UserDTO getLatestUserData(String useridentifier){
        //TODO implement me
        return UserDTO.builder().build();
    }

    public boolean checkPromotionForUser(String useridentifier){
        //TODO implement the promotion of a user to become a veteran
        return false;
    }
}
