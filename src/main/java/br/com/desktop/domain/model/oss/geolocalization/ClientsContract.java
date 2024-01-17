package br.com.desktop.domain.model.oss.geolocalization;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientsContract {

    private Long idContract;
    private Long planId;
    private Long numClient;
    private LocalDateTime dataContrato;
    private Long status;
    private Long desconto;
    private Long valorInstalacao;
    private Long quantidadeParcelas;
    private Long receberNotaFiscal;
    private Long vendidoPor;
    private Long tipo;
    private Long contratoFidelidade;
    private Long atendenteId;
    private Long promocaoId;
    private Long valorPlano;
    private Long taxaAdesao;
    private Long quantidadeParcelasAdesao;
    private String observacoes;
    private Long migradoProvedor;
    private Long tipoOrigemVenda;
}
