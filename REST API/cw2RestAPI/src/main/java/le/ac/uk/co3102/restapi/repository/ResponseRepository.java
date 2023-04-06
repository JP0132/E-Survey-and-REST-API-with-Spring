package le.ac.uk.co3102.restapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import le.ac.uk.co3102.restapi.domain.Response;



public interface ResponseRepository extends CrudRepository<Response, Integer>{
	Response findByResponseID(Integer responseID);
	
	public List<Response> findAllByQuestionID(Integer questionID);
	
	List<Response> findByUserID(String userID);

}
