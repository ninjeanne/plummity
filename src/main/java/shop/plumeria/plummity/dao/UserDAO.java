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
    private String useridentifier;
    private long farmpoints;
    private boolean isVeteran;
}
