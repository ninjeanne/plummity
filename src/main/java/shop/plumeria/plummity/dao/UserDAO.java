package shop.plumeria.plummity.dao;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserDAO {
    @Id
    private String uuid;
    private long points;
    private boolean isVeteran;
}
