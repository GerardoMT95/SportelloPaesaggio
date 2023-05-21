package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DTO for table presentazione_istanza.configurazioni_ente
 */
public class ConfigurazioniEnteDTO implements Serializable {
	private static final long serialVersionUID = 6131712219L;

	private int idOrganizzazione;
	private boolean sistemaProtocollazione; // MANUALE: false - AUTOMATICO: true - (default false)
	private String protocollazioneEndpoint;
	private String protocollazioneAdministration;
	private String protocollazioneAoo;
	private String protocollazioneRegister;
	private String protocollazioneUser;
	private String protocollazionePassword;
	private String protocollazioneHashAlgorithm;
	private String protocollazioneIndirizzoPostale;
	private String protocollazioneIndirizzoTelematico;
	private String protocollazioneTipoIndirizzoTelematico;
	private String protocollazioneAooDenominazione;
	private String protocollazioneDenominazione;
	private String protocollazioneNumeroRegistrazione;
	private Date protocollazioneDataRegistrazione;

	private boolean sistemaPagamento; // INTEGRATO: true - NON INTEGRATO: false - (default false)
	private String pagamentoTipoRiscossione; // corrisponde a tipoRiscossione  ex-intestatarioPagamento
	private String pagamentoCodEnte; // corrisponde a codEnte (codice ipa ente) ex-pagamentoIban
	private String pagamentoTipologia; // corrisponde a tipologia MyPay  ex-pagamentoCausale
	private String pagamentoPassword; // su DB criptata 
	private String pagamentoEndPoint; // endpoint mypay
	private double pagamentoCommissione; // endpoint mypay
	private String pagamentoRegexIud; // regex per generazione iud 
	private boolean sistemaPec; // INTERNO: true - ESTERNO: false - (default false)
	private String pecIndirizzo;
	private String pecNome;
	private String pecUsername;
	private String pecPassword;
	private String pecHostIn;
	private String pecHostOut;
	private Boolean pecProtocolloIn;
	private Boolean pecProtocolloOut; // non viene usato
	private String pecTipoProtocolloIn;
	private String pecTipoProtocolloOut;
	private Boolean pecSslIn;
	private Boolean pecSslOut;
	private Boolean pecTlsIn; // non viene usato
	private Boolean pecTlsOut;
	private Integer pecPortaSslIn;
	private Integer pecPortaSslOut;
	private Integer pecPortaTlsIn; // non viene usato
	private Integer pecPortaTlsOut;
	private Boolean pecStartTlsIn;
	private Boolean pecStartTlsOut;
	private Boolean pecAutenticazioneIn;
	private Boolean pecAutenticazioneOut;
	private Boolean pubblicazioneStatoPratica; // SI: true - NO: false - DEFAULT: null
	private Boolean canCreateConferenza;
	private String indirizziMailDefault;
	private String bilancio;
	// COLUM tipo_pratica_seduta

	private List<Integer> tipoPraticaSeduta;

	public double getPagamentoCommissione() {
		return pagamentoCommissione;
	}

	public void setPagamentoCommissione(double pagamentoCommissione) {
		this.pagamentoCommissione = pagamentoCommissione;
	}

	public String getPagamentoRegexIud() {
		return pagamentoRegexIud;
	}

	public void setPagamentoRegexIud(String pagamentoRegexIud) {
		this.pagamentoRegexIud = pagamentoRegexIud;
	}

	public ConfigurazioniEnteDTO() {
		tipoPraticaSeduta = new ArrayList<Integer>();
		tipoPraticaSeduta.add(1);
		tipoPraticaSeduta.add(2);
	}

	public String getPagamentoPassword() {
		return pagamentoPassword;
	}

	public void setPagamentoPassword(String pagamentoPassword) {
		this.pagamentoPassword = pagamentoPassword;
	}

	public String getPagamentoEndPoint() {
		return pagamentoEndPoint;
	}

	public void setPagamentoEndPoint(String pagamentoEndPoint) {
		this.pagamentoEndPoint = pagamentoEndPoint;
	}

	public int getIdOrganizzazione() {
		return idOrganizzazione;
	}

	public void setIdOrganizzazione(int idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}

	public boolean isSistemaProtocollazione() {
		return sistemaProtocollazione;
	}

	public void setSistemaProtocollazione(boolean sistemaProtocollazione) {
		this.sistemaProtocollazione = sistemaProtocollazione;
	}

	public String getProtocollazioneEndpoint() {
		return protocollazioneEndpoint;
	}

	public void setProtocollazioneEndpoint(String protocollazioneEndpoint) {
		this.protocollazioneEndpoint = protocollazioneEndpoint;
	}

	public String getProtocollazioneAdministration() {
		return protocollazioneAdministration;
	}

	public void setProtocollazioneAdministration(String protocollazioneAdministration) {
		this.protocollazioneAdministration = protocollazioneAdministration;
	}

	public String getProtocollazioneAoo() {
		return protocollazioneAoo;
	}

	public void setProtocollazioneAoo(String protocollazioneAoo) {
		this.protocollazioneAoo = protocollazioneAoo;
	}

	public String getProtocollazioneRegister() {
		return protocollazioneRegister;
	}

	public void setProtocollazioneRegister(String protocollazioneRegister) {
		this.protocollazioneRegister = protocollazioneRegister;
	}

	public String getProtocollazioneUser() {
		return protocollazioneUser;
	}

	public void setProtocollazioneUser(String protocollazioneUser) {
		this.protocollazioneUser = protocollazioneUser;
	}

	public String getProtocollazionePassword() {
		return protocollazionePassword;
	}

	public void setProtocollazionePassword(String protocollazionePassword) {
		this.protocollazionePassword = protocollazionePassword;
	}

	public String getProtocollazioneHashAlgorithm() {
		return protocollazioneHashAlgorithm;
	}

	public void setProtocollazioneHashAlgorithm(String protocollazioneHashAlgorithm) {
		this.protocollazioneHashAlgorithm = protocollazioneHashAlgorithm;
	}

	public boolean isSistemaPagamento() {
		return sistemaPagamento;
	}

	public void setSistemaPagamento(boolean sistemaPagamento) {
		this.sistemaPagamento = sistemaPagamento;
	}

	public String getPagamentoTipoRiscossione() {
		return pagamentoTipoRiscossione;
	}

	public void setPagamentoTipoRiscossione(String tipoRiscossione) {
		this.pagamentoTipoRiscossione = tipoRiscossione;
	}

	public String getPagamentoCodEnte() {
		return pagamentoCodEnte;
	}

	public void setPagamentoCodEnte(String codEnte) {
		this.pagamentoCodEnte = codEnte;
	}

	public String getPagamentoTipologia() {
		return pagamentoTipologia;
	}

	public void setPagamentoTipologia(String tipologia) {
		this.pagamentoTipologia = tipologia;
	}

	public boolean isSistemaPec() {
		return sistemaPec;
	}

	public void setSistemaPec(boolean sistemaPec) {
		this.sistemaPec = sistemaPec;
	}

	public String getPecIndirizzo() {
		return pecIndirizzo;
	}

	public void setPecIndirizzo(String pecIndirizzo) {
		this.pecIndirizzo = pecIndirizzo;
	}

	public String getPecNome() {
		return pecNome;
	}

	public void setPecNome(String pecNome) {
		this.pecNome = pecNome;
	}

	public String getPecUsername() {
		return pecUsername;
	}

	public void setPecUsername(String pecUsername) {
		this.pecUsername = pecUsername;
	}

	public String getPecPassword() {
		return pecPassword;
	}

	public void setPecPassword(String pecPassword) {
		this.pecPassword = pecPassword;
	}

	public String getPecHostIn() {
		return pecHostIn;
	}

	public void setPecHostIn(String pecHostIn) {
		this.pecHostIn = pecHostIn;
	}

	public String getPecHostOut() {
		return pecHostOut;
	}

	public void setPecHostOut(String pecHostOut) {
		this.pecHostOut = pecHostOut;
	}

	public Boolean getPecProtocolloIn() {
		return pecProtocolloIn;
	}

	public void setPecProtocolloIn(Boolean pecProtocolloIn) {
		this.pecProtocolloIn = pecProtocolloIn;
	}

	public Boolean getPecProtocolloOut() {
		return pecProtocolloOut;
	}

	public void setPecProtocolloOut(Boolean pecProtocolloOut) {
		this.pecProtocolloOut = pecProtocolloOut;
	}

	public String getPecTipoProtocolloIn() {
		return pecTipoProtocolloIn;
	}

	public void setPecTipoProtocolloIn(String pecTipoProtocolloIn) {
		this.pecTipoProtocolloIn = pecTipoProtocolloIn;
	}

	public String getPecTipoProtocolloOut() {
		return pecTipoProtocolloOut;
	}

	public void setPecTipoProtocolloOut(String pecTipoProtocolloOut) {
		this.pecTipoProtocolloOut = pecTipoProtocolloOut;
	}

	public Boolean getPecSslIn() {
		return pecSslIn;
	}

	public void setPecSslIn(Boolean pecSslIn) {
		this.pecSslIn = pecSslIn;
	}

	public Boolean getPecSslOut() {
		return pecSslOut;
	}

	public void setPecSslOut(Boolean pecSslOut) {
		this.pecSslOut = pecSslOut;
	}

	public Boolean getPecTlsIn() {
		return pecTlsIn;
	}

	public void setPecTlsIn(Boolean pecTlsIn) {
		this.pecTlsIn = pecTlsIn;
	}

	public Boolean getPecTlsOut() {
		return pecTlsOut;
	}

	public void setPecTlsOut(Boolean pecTlsOut) {
		this.pecTlsOut = pecTlsOut;
	}

	public Integer getPecPortaSslIn() {
		return pecPortaSslIn;
	}

	public void setPecPortaSslIn(Integer pecPortaSslIn) {
		this.pecPortaSslIn = pecPortaSslIn;
	}

	public Integer getPecPortaSslOut() {
		return pecPortaSslOut;
	}

	public void setPecPortaSslOut(Integer pecPortaSslOut) {
		this.pecPortaSslOut = pecPortaSslOut;
	}

	public Integer getPecPortaTlsIn() {
		return pecPortaTlsIn;
	}

	public void setPecPortaTlsIn(Integer pecPortaTlsIn) {
		this.pecPortaTlsIn = pecPortaTlsIn;
	}

	public Integer getPecPortaTlsOut() {
		return pecPortaTlsOut;
	}

	public void setPecPortaTlsOut(Integer pecPortaTlsOut) {
		this.pecPortaTlsOut = pecPortaTlsOut;
	}

	public Boolean getPecStartTlsIn() {
		return pecStartTlsIn;
	}

	public void setPecStartTlsIn(Boolean pecStartTlsIn) {
		this.pecStartTlsIn = pecStartTlsIn;
	}

	public Boolean getPecStartTlsOut() {
		return pecStartTlsOut;
	}

	public void setPecStartTlsOut(Boolean pecStartTlsOut) {
		this.pecStartTlsOut = pecStartTlsOut;
	}

	public Boolean getPecAutenticazioneIn() {
		return pecAutenticazioneIn;
	}

	public void setPecAutenticazioneIn(Boolean pecAutenticazioneIn) {
		this.pecAutenticazioneIn = pecAutenticazioneIn;
	}

	public Boolean getPecAutenticazioneOut() {
		return pecAutenticazioneOut;
	}

	public void setPecAutenticazioneOut(Boolean pecAutenticazioneOut) {
		this.pecAutenticazioneOut = pecAutenticazioneOut;
	}

	public Boolean getPubblicazioneStatoPratica() {
		return pubblicazioneStatoPratica;
	}

	public void setPubblicazioneStatoPratica(Boolean pubblicazioneStatoPratica) {
		this.pubblicazioneStatoPratica = pubblicazioneStatoPratica;
	}

	/**
	 * @return the tipoPraticaSeduta
	 */
	public List<Integer> getTipoPraticaSeduta() {
		return tipoPraticaSeduta;
	}

	/**
	 * @param tipoPraticaSeduta the tipoPraticaSeduta to set
	 */
	public void setTipoPraticaSeduta(List<Integer> tipoPraticaSeduta) {
		this.tipoPraticaSeduta = tipoPraticaSeduta;
	}

	public String getProtocollazioneIndirizzoPostale() {
		return protocollazioneIndirizzoPostale;
	}

	public void setProtocollazioneIndirizzoPostale(String protocollazioneIndirizzoPostale) {
		this.protocollazioneIndirizzoPostale = protocollazioneIndirizzoPostale;
	}

	public String getProtocollazioneIndirizzoTelematico() {
		return protocollazioneIndirizzoTelematico;
	}

	public void setProtocollazioneIndirizzoTelematico(String protocollazioneIndirizzoTelematico) {
		this.protocollazioneIndirizzoTelematico = protocollazioneIndirizzoTelematico;
	}

	public String getProtocollazioneAooDenominazione() {
		return protocollazioneAooDenominazione;
	}

	public void setProtocollazioneAooDenominazione(String protocollazioneAooDenominazione) {
		this.protocollazioneAooDenominazione = protocollazioneAooDenominazione;
	}

	public String getProtocollazioneDenominazione() {
		return protocollazioneDenominazione;
	}

	public void setProtocollazioneDenominazione(String protocollazioneDenominazione) {
		this.protocollazioneDenominazione = protocollazioneDenominazione;
	}

	public String getProtocollazioneTipoIndirizzoTelematico() {
		return protocollazioneTipoIndirizzoTelematico;
	}

	public void setProtocollazioneTipoIndirizzoTelematico(String protocollazioneTipoIndirizzoTelematico) {
		this.protocollazioneTipoIndirizzoTelematico = protocollazioneTipoIndirizzoTelematico;
	}

	public String getProtocollazioneNumeroRegistrazione() {
		return protocollazioneNumeroRegistrazione;
	}

	public void setProtocollazioneNumeroRegistrazione(String protocollazioneNumeroRegistrazione) {
		this.protocollazioneNumeroRegistrazione = protocollazioneNumeroRegistrazione;
	}

	public Date getProtocollazioneDataRegistrazione() {
		return protocollazioneDataRegistrazione;
	}

	public void setProtocollazioneDataRegistrazione(Date protocollazioneDataRegistrazione) {
		this.protocollazioneDataRegistrazione = protocollazioneDataRegistrazione;
	}

	public Boolean getCanCreateConferenza() {
		return canCreateConferenza;
	}

	public void setCanCreateConferenza(Boolean canCreateConferenza) {
		this.canCreateConferenza = canCreateConferenza;
	}

	public String getIndirizziMailDefault() {
		return indirizziMailDefault;
	}

	public void setIndirizziMailDefault(String indirizziMailDefault) {
		this.indirizziMailDefault = indirizziMailDefault;
	}

	public String getBilancio() {
		return bilancio;
	}

	public void setBilancio(String bilancio) {
		this.bilancio = bilancio;
	}

	@Override
	public String toString() {
		return "ConfigurazioniEnteDTO [idOrganizzazione=" + idOrganizzazione + ", sistemaProtocollazione="
				+ sistemaProtocollazione + ", protocollazioneEndpoint=" + protocollazioneEndpoint
				+ ", protocollazioneAdministration=" + protocollazioneAdministration + ", protocollazioneAoo="
				+ protocollazioneAoo + ", protocollazioneRegister=" + protocollazioneRegister + ", protocollazioneUser="
				+ protocollazioneUser + ", protocollazionePassword=" + protocollazionePassword
				+ ", protocollazioneHashAlgorithm=" + protocollazioneHashAlgorithm
				+ ", protocollazioneIndirizzoPostale=" + protocollazioneIndirizzoPostale
				+ ", protocollazioneIndirizzoTelematico=" + protocollazioneIndirizzoTelematico
				+ ", protocollazioneTipoIndirizzoTelematico=" + protocollazioneTipoIndirizzoTelematico
				+ ", protocollazioneAooDenominazione=" + protocollazioneAooDenominazione
				+ ", protocollazioneDenominazione=" + protocollazioneDenominazione
				+ ", protocollazioneNumeroRegistrazione=" + protocollazioneNumeroRegistrazione
				+ ", protocollazioneDataRegistrazione=" + protocollazioneDataRegistrazione + ", sistemaPagamento="
				+ sistemaPagamento + ", pagamentoTipoRiscossione=" + pagamentoTipoRiscossione + ", pagamentoCodEnte="
				+ pagamentoCodEnte + ", pagamentoTipologia=" + pagamentoTipologia + ", pagamentoPassword="
				+ pagamentoPassword + ", pagamentoEndPoint=" + pagamentoEndPoint + ", pagamentoCommissione="
				+ pagamentoCommissione + ", pagamentoRegexIud=" + pagamentoRegexIud + ", sistemaPec=" + sistemaPec
				+ ", pecIndirizzo=" + pecIndirizzo + ", pecNome=" + pecNome + ", pecUsername=" + pecUsername
				+ ", pecPassword=" + pecPassword + ", pecHostIn=" + pecHostIn + ", pecHostOut=" + pecHostOut
				+ ", pecProtocolloIn=" + pecProtocolloIn + ", pecProtocolloOut=" + pecProtocolloOut
				+ ", pecTipoProtocolloIn=" + pecTipoProtocolloIn + ", pecTipoProtocolloOut=" + pecTipoProtocolloOut
				+ ", pecSslIn=" + pecSslIn + ", pecSslOut=" + pecSslOut + ", pecTlsIn=" + pecTlsIn + ", pecTlsOut="
				+ pecTlsOut + ", pecPortaSslIn=" + pecPortaSslIn + ", pecPortaSslOut=" + pecPortaSslOut
				+ ", pecPortaTlsIn=" + pecPortaTlsIn + ", pecPortaTlsOut=" + pecPortaTlsOut + ", pecStartTlsIn="
				+ pecStartTlsIn + ", pecStartTlsOut=" + pecStartTlsOut + ", pecAutenticazioneIn=" + pecAutenticazioneIn
				+ ", pecAutenticazioneOut=" + pecAutenticazioneOut + ", pubblicazioneStatoPratica="
				+ pubblicazioneStatoPratica + ", canCreateConferenza=" + canCreateConferenza + ", indirizziMailDefault="
				+ indirizziMailDefault + ", tipoPraticaSeduta=" + tipoPraticaSeduta + ", bilancio=" + bilancio + "]";
	}

}