package shop.plumeria.plummity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import shop.plumeria.plummity.dao.AcademyAnswerDAO;
import shop.plumeria.plummity.dao.AcademyQuestionDAO;
import shop.plumeria.plummity.dto.AcademyAnswerDTO;
import shop.plumeria.plummity.dto.AcademyQuestionDTO;
import shop.plumeria.plummity.dto.ErrorDTO;
import shop.plumeria.plummity.repository.AcademyQuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AcademyService {

    @Autowired
    private AcademyQuestionRepository academyQuestionRepository;

    public List<AcademyQuestionDTO> getRandomDailyQuestions() {
        Pageable pageable = PageRequest.of(0, 5);
        Slice<AcademyQuestionDAO> questions = academyQuestionRepository.getRandomQuestions(pageable);
        return convert(questions);
    }

    private List<AcademyQuestionDTO> convert(Slice<AcademyQuestionDAO> sliceFromDB) {
        List<AcademyQuestionDAO> questionsFromDB = sliceFromDB.getContent();
        List<AcademyQuestionDTO> questions = new ArrayList<>();
        questionsFromDB.forEach((questionFromDB) -> {
            List<AcademyAnswerDTO> answers = new ArrayList<>();
            questionFromDB.getAnswers().forEach((answerFromDB) -> {
                AcademyAnswerDTO answerDTO = AcademyAnswerDTO.builder().answer_text(answerFromDB.getAnswer_text()).solution(answerFromDB.getSolution())
                        .id(answerFromDB.getUuid()).build();
                answers.add(answerDTO);
            });
            AcademyQuestionDTO questionDTO = AcademyQuestionDTO.builder().questiontext(questionFromDB.getQuestiontext())
                    .information(questionFromDB.getInformation()).id(questionFromDB.getUuid()).answers(answers).build();
            questions.add(questionDTO);
        });
        return questions;
    }

    public ErrorDTO saveNewQuestion(AcademyQuestionDTO newQuestion) {
        if (newQuestion == null) {
            log.warn("Couldn't save empty question.");
            return ErrorDTO.builder().message("Couldn't save empty question.").build();
        }

        AcademyQuestionDAO questionForDB = convert(newQuestion);
        academyQuestionRepository.save(questionForDB);
        return null;
    }

    private AcademyQuestionDAO convert(AcademyQuestionDTO newQuestion) {
        List<AcademyAnswerDAO> answersForDB = new ArrayList<>();
        newQuestion.getAnswers().forEach(newAnswer -> {
            AcademyAnswerDAO answerForDB = AcademyAnswerDAO.builder().answer_text(newAnswer.getAnswer_text()).solution(newAnswer.getSolution())
                    .uuid(UUID.randomUUID().toString()).build();
            answersForDB.add(answerForDB);
        });
        return AcademyQuestionDAO.builder().uuid(UUID.randomUUID().toString()).questiontext(newQuestion.getQuestiontext())
                .information(newQuestion.getInformation()).answers(answersForDB).build();
    }

}
