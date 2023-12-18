package br.com.desktop.domain.port;

import br.com.desktop.domain.model.customers.Plans;
import org.springframework.data.repository.CrudRepository;


public interface PlansRepository extends CrudRepository<Plans, String> {
}