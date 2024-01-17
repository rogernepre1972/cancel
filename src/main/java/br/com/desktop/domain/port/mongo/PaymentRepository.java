package br.com.desktop.domain.port.mongo;

import br.com.desktop.domain.model.mongo.PaymentCancellation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentCancellation, Long> {
    @Query("{'puId': ?0 }")
    Optional<PaymentCancellation> findByPuId(Long puId);
}
