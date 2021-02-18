package shop.plumeria.plummity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class AcademyQuestionDTO {
    private String id;
    private String questiontext;
    private List<AcademyAnswerDTO> answers;
    private String information;
}
