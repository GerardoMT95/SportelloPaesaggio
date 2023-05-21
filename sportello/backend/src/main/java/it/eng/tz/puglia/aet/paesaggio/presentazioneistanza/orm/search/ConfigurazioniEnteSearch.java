package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.configurazioni_ente
 */
public class ConfigurazioniEnteSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 4669182729L;
    //COLUMN id_organizzazione
    private String idOrganizzazione;
    //COLUMN sistema_protocollazione
    private String sistemaProtocollazione;
    //COLUMN protocollazione_endpoint
    private String protocollazioneEndpoint;
    //COLUMN protocollazione_administration
    private String protocollazioneAdministration;
    //COLUMN protocollazione_aoo
    private String protocollazioneAoo;
    //COLUMN protocollazione_register
    private String protocollazioneRegister;
    //COLUMN protocollazione_user
    private String protocollazioneUser;
    //COLUMN protocollazione_password
    private String protocollazionePassword;
    //COLUMN protocollazione_hash_algorithm
    private String protocollazioneHashAlgorithm;
    //COLUMN sistema_pagamento
    private String sistemaPagamento;
    //COLUMN pagamento_intestatario
    private String pagamentoIntestatario;
    //COLUMN pagamento_iban
    private String pagamentoIban;
    //COLUMN pagamento_causale
    private String pagamentoCausale;
    //COLUMN sistema_pec
    private String sistemaPec;
    //COLUMN pec_indirizzo
    private String pecIndirizzo;
    //COLUMN pec_nome
    private String pecNome;
    //COLUMN pec_username
    private String pecUsername;
    //COLUMN pec_password
    private String pecPassword;
    //COLUMN pec_host_in
    private String pecHostIn;
    //COLUMN pec_host_out
    private String pecHostOut;
    //COLUMN pec_protocollo_in
    private String pecProtocolloIn;
    //COLUMN pec_protocollo_out
    private String pecProtocolloOut;
    //COLUMN pec_tipo_protocollo_in
    private String pecTipoProtocolloIn;
    //COLUMN pec_tipo_protocollo_out
    private String pecTipoProtocolloOut;
    //COLUMN pec_ssl_in
    private String pecSslIn;
    //COLUMN pec_ssl_out
    private String pecSslOut;
    //COLUMN pec_tls_in
    private String pecTlsIn;
    //COLUMN pec_tls_out
    private String pecTlsOut;
    //COLUMN pec_porta_ssl_in
    private String pecPortaSslIn;
    //COLUMN pec_porta_ssl_out
    private String pecPortaSslOut;
    //COLUMN pec_porta_tls_in
    private String pecPortaTlsIn;
    //COLUMN pec_porta_tls_out
    private String pecPortaTlsOut;
    //COLUMN pec_start_tls_in
    private String pecStartTlsIn;
    //COLUMN pec_start_tls_out
    private String pecStartTlsOut;
    //COLUMN pec_autenticazione_in
    private String pecAutenticazioneIn;
    //COLUMN pec_autenticazione_out
    private String pecAutenticazioneOut;
    //COLUMN pubblicazione_stato_pratica
    private String pubblicazioneStatoPratica;

    public String getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(String idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getSistemaProtocollazione(){
        return this.sistemaProtocollazione;
    }

    public void setSistemaProtocollazione(String sistemaProtocollazione){
        this.sistemaProtocollazione = sistemaProtocollazione;
    }

    public String getProtocollazioneEndpoint(){
        return this.protocollazioneEndpoint;
    }

    public void setProtocollazioneEndpoint(String protocollazioneEndpoint){
        this.protocollazioneEndpoint = protocollazioneEndpoint;
    }

    public String getProtocollazioneAdministration(){
        return this.protocollazioneAdministration;
    }

    public void setProtocollazioneAdministration(String protocollazioneAdministration){
        this.protocollazioneAdministration = protocollazioneAdministration;
    }

    public String getProtocollazioneAoo(){
        return this.protocollazioneAoo;
    }

    public void setProtocollazioneAoo(String protocollazioneAoo){
        this.protocollazioneAoo = protocollazioneAoo;
    }

    public String getProtocollazioneRegister(){
        return this.protocollazioneRegister;
    }

    public void setProtocollazioneRegister(String protocollazioneRegister){
        this.protocollazioneRegister = protocollazioneRegister;
    }

    public String getProtocollazioneUser(){
        return this.protocollazioneUser;
    }

    public void setProtocollazioneUser(String protocollazioneUser){
        this.protocollazioneUser = protocollazioneUser;
    }

    public String getProtocollazionePassword(){
        return this.protocollazionePassword;
    }

    public void setProtocollazionePassword(String protocollazionePassword){
        this.protocollazionePassword = protocollazionePassword;
    }

    public String getProtocollazioneHashAlgorithm(){
        return this.protocollazioneHashAlgorithm;
    }

    public void setProtocollazioneHashAlgorithm(String protocollazioneHashAlgorithm){
        this.protocollazioneHashAlgorithm = protocollazioneHashAlgorithm;
    }

    public String getSistemaPagamento(){
        return this.sistemaPagamento;
    }

    public void setSistemaPagamento(String sistemaPagamento){
        this.sistemaPagamento = sistemaPagamento;
    }

    public String getPagamentoIntestatario(){
        return this.pagamentoIntestatario;
    }

    public void setPagamentoIntestatario(String pagamentoIntestatario){
        this.pagamentoIntestatario = pagamentoIntestatario;
    }

    public String getPagamentoIban(){
        return this.pagamentoIban;
    }

    public void setPagamentoIban(String pagamentoIban){
        this.pagamentoIban = pagamentoIban;
    }

    public String getPagamentoCausale(){
        return this.pagamentoCausale;
    }

    public void setPagamentoCausale(String pagamentoCausale){
        this.pagamentoCausale = pagamentoCausale;
    }

    public String getSistemaPec(){
        return this.sistemaPec;
    }

    public void setSistemaPec(String sistemaPec){
        this.sistemaPec = sistemaPec;
    }

    public String getPecIndirizzo(){
        return this.pecIndirizzo;
    }

    public void setPecIndirizzo(String pecIndirizzo){
        this.pecIndirizzo = pecIndirizzo;
    }

    public String getPecNome(){
        return this.pecNome;
    }

    public void setPecNome(String pecNome){
        this.pecNome = pecNome;
    }

    public String getPecUsername(){
        return this.pecUsername;
    }

    public void setPecUsername(String pecUsername){
        this.pecUsername = pecUsername;
    }

    public String getPecPassword(){
        return this.pecPassword;
    }

    public void setPecPassword(String pecPassword){
        this.pecPassword = pecPassword;
    }

    public String getPecHostIn(){
        return this.pecHostIn;
    }

    public void setPecHostIn(String pecHostIn){
        this.pecHostIn = pecHostIn;
    }

    public String getPecHostOut(){
        return this.pecHostOut;
    }

    public void setPecHostOut(String pecHostOut){
        this.pecHostOut = pecHostOut;
    }

    public String getPecProtocolloIn(){
        return this.pecProtocolloIn;
    }

    public void setPecProtocolloIn(String pecProtocolloIn){
        this.pecProtocolloIn = pecProtocolloIn;
    }

    public String getPecProtocolloOut(){
        return this.pecProtocolloOut;
    }

    public void setPecProtocolloOut(String pecProtocolloOut){
        this.pecProtocolloOut = pecProtocolloOut;
    }

    public String getPecTipoProtocolloIn(){
        return this.pecTipoProtocolloIn;
    }

    public void setPecTipoProtocolloIn(String pecTipoProtocolloIn){
        this.pecTipoProtocolloIn = pecTipoProtocolloIn;
    }

    public String getPecTipoProtocolloOut(){
        return this.pecTipoProtocolloOut;
    }

    public void setPecTipoProtocolloOut(String pecTipoProtocolloOut){
        this.pecTipoProtocolloOut = pecTipoProtocolloOut;
    }

    public String getPecSslIn(){
        return this.pecSslIn;
    }

    public void setPecSslIn(String pecSslIn){
        this.pecSslIn = pecSslIn;
    }

    public String getPecSslOut(){
        return this.pecSslOut;
    }

    public void setPecSslOut(String pecSslOut){
        this.pecSslOut = pecSslOut;
    }

    public String getPecTlsIn(){
        return this.pecTlsIn;
    }

    public void setPecTlsIn(String pecTlsIn){
        this.pecTlsIn = pecTlsIn;
    }

    public String getPecTlsOut(){
        return this.pecTlsOut;
    }

    public void setPecTlsOut(String pecTlsOut){
        this.pecTlsOut = pecTlsOut;
    }

    public String getPecPortaSslIn(){
        return this.pecPortaSslIn;
    }

    public void setPecPortaSslIn(String pecPortaSslIn){
        this.pecPortaSslIn = pecPortaSslIn;
    }

    public String getPecPortaSslOut(){
        return this.pecPortaSslOut;
    }

    public void setPecPortaSslOut(String pecPortaSslOut){
        this.pecPortaSslOut = pecPortaSslOut;
    }

    public String getPecPortaTlsIn(){
        return this.pecPortaTlsIn;
    }

    public void setPecPortaTlsIn(String pecPortaTlsIn){
        this.pecPortaTlsIn = pecPortaTlsIn;
    }

    public String getPecPortaTlsOut(){
        return this.pecPortaTlsOut;
    }

    public void setPecPortaTlsOut(String pecPortaTlsOut){
        this.pecPortaTlsOut = pecPortaTlsOut;
    }

    public String getPecStartTlsIn(){
        return this.pecStartTlsIn;
    }

    public void setPecStartTlsIn(String pecStartTlsIn){
        this.pecStartTlsIn = pecStartTlsIn;
    }

    public String getPecStartTlsOut(){
        return this.pecStartTlsOut;
    }

    public void setPecStartTlsOut(String pecStartTlsOut){
        this.pecStartTlsOut = pecStartTlsOut;
    }

    public String getPecAutenticazioneIn(){
        return this.pecAutenticazioneIn;
    }

    public void setPecAutenticazioneIn(String pecAutenticazioneIn){
        this.pecAutenticazioneIn = pecAutenticazioneIn;
    }

    public String getPecAutenticazioneOut(){
        return this.pecAutenticazioneOut;
    }

    public void setPecAutenticazioneOut(String pecAutenticazioneOut){
        this.pecAutenticazioneOut = pecAutenticazioneOut;
    }

    public String getPubblicazioneStatoPratica(){
        return this.pubblicazioneStatoPratica;
    }

    public void setPubblicazioneStatoPratica(String pubblicazioneStatoPratica){
        this.pubblicazioneStatoPratica = pubblicazioneStatoPratica;
    }


}
