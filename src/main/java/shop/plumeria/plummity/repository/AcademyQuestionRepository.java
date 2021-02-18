package shop.plumeria.plummity.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.plumeria.plummity.dao.AcademyQuestionDAO;

@Repository
public interface AcademyQuestionRepository extends JpaRepository<AcademyQuestionDAO, String> {

    @Query(value = "SELECT question FROM AcademyQuestionDAO question order by function('RAND')")
    Slice<AcademyQuestionDAO> getRandomQuestions(Pageable pageable);

    AcademyQuestionDAO getAcademyQuestionDAOByUuid(String uuid);

}
