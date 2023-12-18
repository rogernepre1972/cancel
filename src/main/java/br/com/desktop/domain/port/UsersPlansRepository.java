package br.com.desktop.domain.port;

import br.com.desktop.domain.model.customers.UsersPlans;
import org.springframework.data.repository.CrudRepository;


public interface UsersPlansRepository extends CrudRepository<UsersPlans, Long> {
}