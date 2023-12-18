package br.com.desktop.domain.port;

import br.com.desktop.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByAdmId(Long admId);
}