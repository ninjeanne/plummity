package shop.plumeria.plummity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import shop.plumeria.plummity.dto.AcademyQuestionDTO;
import shop.plumeria.plummity.dto.ErrorDTO;
import shop.plumeria.plummity.service.AcademyService;

import java.util.List;

@RestController
@RequestMapping("/academy")
public class AcademyController {

    @Autowired
    private AcademyService academyService;

    @RequestMapping(value = "/dailyquestions", method = RequestMethod.GET)
    public ResponseEntity<List<AcademyQuestionDTO>> getDailyQuestion() {
        List<AcademyQuestionDTO> data = academyService.getRandomDailyQuestions();
        return ResponseEntity.ok().body(data);
    }

    @RequestMapping(value = "/dailyquestions", method = RequestMethod.POST)
    public ResponseEntity<ErrorDTO> addNewQuestion(@RequestBody AcademyQuestionDTO academyQuestionDTO) {
        ErrorDTO error = academyService.saveNewQuestion(academyQuestionDTO);
        if (error == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(error);
    }
}
