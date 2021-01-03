package shop.plumeria.plummity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.plumeria.plummity.utils.StandardRatingType;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StandardRatingDTO {
    private String useridentifier;
    private Map<String, StandardRatingType> ratings;
}
