package shop.plumeria.plummity.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.plumeria.plummity.utils.StandardRatingType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardRatingDAO {
    @Id
    private String uuid;

    @OneToOne(cascade = { CascadeType.ALL })
    private ImageDAO image;

    @OneToOne(cascade = { CascadeType.ALL })
    private UserDAO user;

    private StandardRatingType type;
}
