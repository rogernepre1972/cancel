package br.com.desktop.domain.model.customers;

import br.com.desktop.domain.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "cobrancas")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 12)
    @NotNull
    @Column(name = "boleto", nullable = false, length = 12)
    private String boleto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "num", nullable = false)
    private User num;

    @NotNull
    @Column(name = "cobrado", nullable = false, precision = 10, scale = 2)
    private BigDecimal cobrado;

    @Column(name = "pago", precision = 10, scale = 2)
    private BigDecimal pago;

    @Size(max = 8)
    @NotNull
    @Column(name = "vencimento", nullable = false, length = 8)
    private String vencimento;

    @NotNull
    @Column(name = "baixa", nullable = false)
    private Character baixa;

    @Size(max = 8)
    @Column(name = "data", length = 8)
    private String data;

    @Column(name = "forma")
    private Character forma;

    @Size(max = 20)
    @Column(name = "funcionario", length = 20)
    private String funcionario;

    @Size(max = 60)
    @Column(name = "observacoes", length = 60)
    private String observacoes;

    @Size(max = 8)
    @Column(name = "dataregistro", length = 8)
    private String dataregistro;

    @Size(max = 60)
    @Column(name = "observacoesregistro", length = 60)
    private String observacoesregistro;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private Character tipo;

    @Size(max = 3)
    @NotNull
    @Column(name = "empresa", nullable = false, length = 3)
    private String empresa;

    @Size(max = 4)
    @NotNull
    @Column(name = "banco", nullable = false, length = 4)
    private String banco;

    @NotNull
    @Column(name = "nf", nullable = false)
    private Character nf;

    @NotNull
    @Column(name = "scm", nullable = false, precision = 10, scale = 2)
    private BigDecimal scm;

    @Size(max = 3)
    @NotNull
    @Column(name = "sistema", nullable = false, length = 3)
    private String sistema;

    @NotNull
    @Column(name = "status", nullable = false)
    private Character status;

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public BigDecimal getScm() {
        return scm;
    }

    public void setScm(BigDecimal scm) {
        this.scm = scm;
    }

    public Character getNf() {
        return nf;
    }

    public void setNf(Character nf) {
        this.nf = nf;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public String getObservacoesregistro() {
        return observacoesregistro;
    }

    public void setObservacoesregistro(String observacoesregistro) {
        this.observacoesregistro = observacoesregistro;
    }

    public String getDataregistro() {
        return dataregistro;
    }

    public void setDataregistro(String dataregistro) {
        this.dataregistro = dataregistro;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public Character getForma() {
        return forma;
    }

    public void setForma(Character forma) {
        this.forma = forma;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Character getBaixa() {
        return baixa;
    }

    public void setBaixa(Character baixa) {
        this.baixa = baixa;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public BigDecimal getPago() {
        return pago;
    }

    public void setPago(BigDecimal pago) {
        this.pago = pago;
    }

    public BigDecimal getCobrado() {
        return cobrado;
    }

    public void setCobrado(BigDecimal cobrado) {
        this.cobrado = cobrado;
    }

    public User getNum() {
        return num;
    }

    public void setNum(User num) {
        this.num = num;
    }

    public String getBoleto() {
        return boleto;
    }

    public void setBoleto(String boleto) {
        this.boleto = boleto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
