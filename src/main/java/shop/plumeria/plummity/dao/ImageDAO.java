package shop.plumeria.plummity.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ImageDAO {

    @Id
    private String uuid;

    @Lob
    @Column(length=100000)
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] data;

    private long size;

    private String mediaType;

    @JsonIgnore
    @OneToOne(cascade = { CascadeType.ALL })
    private UserDAO owner;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = StandardRatingDAO.class)
    private List<StandardRatingDAO> standardRatings;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = VeteranRatingDAO.class)
    private List<VeteranRatingDAO> veteranRatings;

    @CreatedDate
    private Date created;
}
