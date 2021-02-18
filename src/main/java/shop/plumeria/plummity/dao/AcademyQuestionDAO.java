package shop.plumeria.plummity.dao;

import lombok.*;
import shop.plumeria.plummity.dto.AcademyAnswerDTO;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AcademyQuestionDAO {
    @Id
    private String uuid;
    private String questiontext;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = AcademyAnswerDAO.class)
    private List<AcademyAnswerDAO> answers;
    private String information;
}
