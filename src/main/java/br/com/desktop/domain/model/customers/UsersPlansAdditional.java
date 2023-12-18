package br.com.desktop.domain.model.customers;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "planos_usuarios_adicionais")
public class UsersPlansAdditional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_usuario")
    private UsersPlans planoUsuario;

    //TODO [JPA Buddy] generate columns from DB
}