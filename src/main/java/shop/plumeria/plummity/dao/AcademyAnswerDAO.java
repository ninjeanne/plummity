package shop.plumeria.plummity.dao;

import lombok.*;
import shop.plumeria.plummity.utils.AcademyAnswerType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AcademyAnswerDAO {
    @Id
    private String uuid;
    private String answer_text;
    private AcademyAnswerType solution;
}
