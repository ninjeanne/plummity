package shop.plumeria.plummity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.plumeria.plummity.utils.StandardRatingType;
import shop.plumeria.plummity.utils.VeteranRatingType;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VeteranRatingDTO {
    private UserDTO user;
    private Map<String, VeteranRatingType> ratings;
}
