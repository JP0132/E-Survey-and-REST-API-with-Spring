package le.ac.uk.co3102.restapi.repository;

import org.springframework.data.repository.CrudRepository;

import le.ac.uk.co3102.restapi.domain.Option;
public interface OptionRepository extends CrudRepository<Option, Integer> {

}
