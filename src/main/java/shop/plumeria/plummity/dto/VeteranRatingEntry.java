package shop.plumeria.plummity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.plumeria.plummity.utils.VeteranRatingType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VeteranRatingEntry {
    private String imageId;
    private VeteranRatingType type;
}
