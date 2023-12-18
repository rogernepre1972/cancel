package br.com.desktop.domain.model.customers;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "planos")
public class Plans {
    @Id
    @Size(max = 30)
    @Column(name = "plano", nullable = false, length = 30)
    private String id;

    //TODO [JPA Buddy] generate columns from DB
}