package le.ac.uk.co3102.cw2.repo;

import org.springframework.data.repository.CrudRepository;

import le.ac.uk.co3102.cw2.model.ResidentUsers;

public interface ResidentUserRepository extends CrudRepository<ResidentUsers, String>{
	ResidentUsers findByUserID(String userID);

}
