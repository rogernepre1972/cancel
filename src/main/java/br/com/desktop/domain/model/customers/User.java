package br.com.desktop.domain.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Long admId;

    @Column(name = "id")
    private String id;

    @Column(name = "senha")
    private String password;

    @Column(name = "nome")
    private String userName;

    @Column(name = "endereco")
    private String userAddress;

    @Column(name = "numero")
    private String number;

    @Column(name = "complemento")
    private String complement;

    @Column(name = "bairro")
    private String district;

    @Column(name = "cep")
    private String zipCode;

    @Column(name = "cidade")
    private String city;

    @Column(name = "estado")
    private String state;

    @Column(name = "tipofone")
    private String phoneType;

    @Column(name = "fone")
    private String phoneNumber;

    @Column(name = "tipoalternativo")
    private String alternativeType;

    @Column(name = "alternativo")
    private String alternative;

    @Column(name = "tipodocumento")
    private String documentType;

    @Column(name = "documento")
    private String document;

    @Column(name = "datacadastro")
    private String registrationDate;

    @Column(name = "datacontrato")
    private String contractDate;

    @Column(name = "observacoes")
    private String observations;

    @Column(name = "ativo")
    private String active;

    @Column(name = "vencimento")
    private String billingDay;

    @Column(name = "nf")
    private String invoice;

    @Column(name = "cancelamento")
    private String cancellation;

    @Column(name = "motivocancelamento")
    private String cancellationReason;

    @Column(name = "desconto")
    private String discount;

    @Column(name = "motivodesconto")
    private String discountReason;

    @Column(name = "cobranca")
    private String billing;

    @Column(name = "enderecocobranca")
    private String billingAddress;

    @Column(name = "numerocobranca")
    private String billingNumber;

    @Column(name = "complementocobranca")
    private String billingComplement;

    @Column(name = "bairrocobranca")
    private String billingDistrict;

    @Column(name = "cepcobranca")
    private String billingZipCode;

    @Column(name = "cidadecobranca")
    private String billingCity;

    @Column(name = "estadocobranca")
    private String billingState;

    @Column(name = "ie")
    private String stateRegistration;

    @Column(name = "empresa")
    private String company;

    @Column(name = "tipocobranca")
    private String billingType;

    @Column(name = "email")
    private String email;

    @Column(name = "observacoesnf")
    private String observationInvoice;

    @Column(name = "prospects")
    private Long prospectsNumber;

    @Column(name = "emissao")
    private String emission;

}