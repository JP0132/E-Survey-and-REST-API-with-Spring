package le.ac.uk.co3102.cw2.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import le.ac.uk.co3102.cw2.domain.Response;

public interface ResponseRepository extends CrudRepository<Response, Integer>{
	Response findByResponseID(Integer responseID);
	
	public List<Response> findAllByQuestionID(Integer questionID);
	
	List<Response> findByUserID(String userID);

}
