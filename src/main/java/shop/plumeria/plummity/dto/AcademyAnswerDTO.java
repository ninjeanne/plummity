package shop.plumeria.plummity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.plumeria.plummity.utils.AcademyAnswerType;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class AcademyAnswerDTO {
    private String id;
    private String answer_text;
    private AcademyAnswerType solution;
}
