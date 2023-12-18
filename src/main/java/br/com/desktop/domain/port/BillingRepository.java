package br.com.desktop.domain.port;

import br.com.desktop.domain.model.customers.Billing;
import org.springframework.data.repository.CrudRepository;


public interface BillingRepository extends CrudRepository<Billing, Long> {

    Billing findByBoleto(String boleto);
}