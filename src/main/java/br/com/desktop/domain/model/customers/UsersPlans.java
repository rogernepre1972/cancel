package br.com.desktop.domain.model.customers;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "planos_usuarios")
public class UsersPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Column(name = "num", columnDefinition = "INT UNSIGNED not null")
    private Long admId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plano", nullable = false)
    private Plans plano;

    @Size(max = 50)
    @Column(name = "login", length = 50)
    private String login;

    @Size(max = 30)
    @Column(name = "senha", length = 30)
    private String senha;

    @Size(max = 12)
    @Column(name = "mac", length = 12)
    private String mac;

    @Size(max = 15)
    @Column(name = "ip", length = 15)
    private String ip;

    @Size(max = 30)
    @Column(name = "dominio", length = 30)
    private String dominio;

    @Column(name = "condominio", columnDefinition = "INT UNSIGNED")
    private Long condominio;

    @Size(max = 100)
    @NotNull
    @Column(name = "observacoes", nullable = false, length = 100)
    private String observacoes;

    @Size(max = 12)
    @Column(name = "mac2", length = 12)
    private String mac2;

    @Size(max = 12)
    @Column(name = "mac3", length = 12)
    private String mac3;

    @Size(max = 6)
    @Column(name = "latitude", length = 6)
    private String latitude;

    @Size(max = 7)
    @Column(name = "longitude", length = 7)
    private String longitude;

    @Size(max = 15)
    @Column(name = "bridge", length = 15)
    private String bridge;

    @Column(name = "par", columnDefinition = "INT UNSIGNED")
    private Long par;

    @Column(name = "porta", columnDefinition = "INT UNSIGNED")
    private Long porta;

    @Size(max = 10)
    @Column(name = "telefone", length = 10)
    private String telefone;

    @Size(max = 10)
    @Column(name = "adicional", length = 10)
    private String adicional;

    @Column(name = "ativo", columnDefinition = "INT UNSIGNED")
    private Long ativo;

    @Column(name = "desconto", precision = 7, scale = 2)
    private BigDecimal desconto;

    @Size(max = 100)
    @Column(name = "motivo", length = 100)
    private String motivo;

    @Column(name = "posicao_gpon", columnDefinition = "INT UNSIGNED")
    private Long posicaoGpon;

    @Size(max = 16)
    @Column(name = "wifi_ssid", length = 16)
    private String wifiSsid;

    @Size(max = 16)
    @Column(name = "wifi_senha", length = 16)
    private String wifiSenha;

    @NotNull
    @Column(name = "status", nullable = false)
    private Character status;

    @OneToMany(mappedBy = "planoUsuario")
    private Set<UsersPlansAdditional> usersPlansAdditionals = new LinkedHashSet<>();

}
