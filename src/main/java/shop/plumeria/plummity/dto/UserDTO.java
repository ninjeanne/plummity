package shop.plumeria.plummity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String identifier;
    private int points;
    private boolean isVeteran;
}
