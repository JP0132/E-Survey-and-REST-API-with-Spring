package le.ac.uk.co3102.cw2.repo;

import org.springframework.data.repository.CrudRepository;

import le.ac.uk.co3102.cw2.model.Sni;

public interface SniRepository extends CrudRepository<Sni, String> {
	Sni findBySniNumber(String sniNumber);

}
