package le.ac.uk.co3102.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import le.ac.uk.co3102.restapi.domain.Question;



public interface QuestionRepository extends CrudRepository<Question, Integer>{
	
	Optional<Question> findById(Integer id);
	Question findByQuestionID(Integer id);
	public List<Question> findAll();
	

}
