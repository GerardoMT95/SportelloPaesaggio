package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

/**
* The persistent class for the TNO_PPTR_REFERENTI_PROGETTO database table.
* 
*/

/*
* CREATE TABLE TNO_PPTR_REFERENTI_PROGETTO ( REFERENTE_PROGETTO_ID NUMBER NOT
* NULL, TIPO_REFERENTE VARCHAR2(2 BYTE) NOT NULL, COGNOME VARCHAR2(60 BYTE),
* NOME VARCHAR2(60 BYTE), CODICE_FISCALE VARCHAR2(16 BYTE), DITTA_RUOLO_RICH
* VARCHAR2(100 BYTE), DITTA_RAGIONE_SOCIALE VARCHAR2(400 BYTE),
* DITTA_PARTITA_IVA VARCHAR2(12 BYTE), DITTA_CODICE_FISCALE VARCHAR2(16 BYTE),
* COMUNE_NASCITA VARCHAR2(100 BYTE), PROV_NASCITA VARCHAR2(4 BYTE),
* STATO_NASCITA VARCHAR2(100 BYTE), DATA_NASCITA DATE, COMUNE_RESIDENZA
* VARCHAR2(100 BYTE), PROV_RESIDENZA VARCHAR2(4 BYTE), INDIRIZZO VARCHAR2(100
* BYTE), NUM_CIVICO VARCHAR2(10 BYTE), CAP VARCHAR2(5 BYTE), TEL_FISSO
* VARCHAR2(60 BYTE), TEL_CELLULARE VARCHAR2(60 BYTE), TEL_FAX VARCHAR2(60
* BYTE), INDIRIZZO_EMAIL VARCHAR2(100 BYTE), INDIRIZZO_PEC VARCHAR2(100 BYTE),
* TECNICO_COMUNE_STUDIO VARCHAR2(100 BYTE), TECNICO_PROV_STUDIO VARCHAR2(4
* BYTE), TECNICO_STATO_STUDIO VARCHAR2(100 BYTE), TECNICO_INDIRIZZO_STUDIO
* VARCHAR2(100 BYTE), TECNICO_NUM_CIV_STUDIO VARCHAR2(10 BYTE),
* TECNICO_CAP_STUDIO VARCHAR2(5 BYTE), TECNICO_ORD_COLLEGIO VARCHAR2(400 BYTE),
* TECNICO_ORD_COLLEGIO_SEDE VARCHAR2(100 BYTE), TECNICO_ORD_COLLEGIO_N_ISCR
* VARCHAR2(50 BYTE), T_PRATICA_ID NUMBER, STATO_RESIDENZA VARCHAR2(100 BYTE),
* SOGGETTO_RAPPRESENTATO VARCHAR2(400 BYTE), CODICE_PRATICA_APPPTR VARCHAR2(255
* BYTE), PROG NUMBER )
*/

public class PptrReferentiProgetto {
	
	private long referenteProgettoId;
	
	//@Column( name = "CAP" )
	private String cap;
	
	//@Column( name = "CODICE_FISCALE" )
	private String codiceFiscale;
	
	//@Column( name = "CODICE_PRATICA_APPPTR" )
	private String codicePraticaAppptr;
	
	//@Column( name = "COGNOME" )
	private String cognome;
	
	//@Column( name = "COMUNE_NASCITA" )
	private String comuneNascita;
	
	//@Column( name = "COMUNE_RESIDENZA" )
	private String comuneResidenza;
	
	//@Temporal( TemporalType.DATE )
	//@Column( name = "DATA_NASCITA" )
	private Date dataNascita;
	
	
	//@Column( name = "DITTA_CODICE_FISCALE" )
	private String dittaCodiceFiscale;
	
	//@Column( name = "DITTA_PARTITA_IVA" )
	private String dittaPartitaIva;
	
	//@Column( name = "DITTA_RAGIONE_SOCIALE" )
	private String dittaRagioneSociale;
	
	//@Column( name = "DITTA_RUOLO_RICH" )
	private String dittaRuoloRich;
	
	//@Column( name = "INDIRIZZO" )
	private String indirizzo;
	
	//@Column( name = "INDIRIZZO_EMAIL" )
	private String indirizzoEmail;
	
	//@Column( name = "INDIRIZZO_PEC" )
	private String indirizzoPec;
	
	//@Column( name = "NOME" )
	private String nome;
	
	//@Column( name = "NUM_CIVICO" )
	private String numCivico;
	
	//@Column( name = "PROV_NASCITA" )
	private String provNascita;
	
	//@Column( name = "PROV_RESIDENZA" )
	private String provResidenza;
	
	//@Column( name = "STATO_NASCITA" )
	private String statoNascita;
	
	//@Column( name = "STATO_RESIDENZA" )
	private String statoResidenza;
	
	//@Column( name = "T_PRATICA_APPTR_ID" )
	private long tPraticaId;
	
	//@Column( name = "TECNICO_CAP_STUDIO" )
	private String tecnicoCapStudio;
	
	//@Column( name = "TECNICO_COMUNE_STUDIO" )
	private String tecnicoComuneStudio;
	
	//@Column( name = "TECNICO_INDIRIZZO_STUDIO" )
	private String tecnicoIndirizzoStudio;
	
	//@Column( name = "TECNICO_NUM_CIV_STUDIO" )
	private String tecnicoNumCivStudio;
	
	//@Column( name = "TECNICO_ORD_COLLEGIO" )
	private String tecnicoOrdCollegio;
	
	//@Column( name = "TECNICO_ORD_COLLEGIO_N_ISCR" )
	private String tecnicoOrdCollegioNIscr;
	
	//@Column( name = "TECNICO_ORD_COLLEGIO_SEDE" )
	private String tecnicoOrdCollegioSede;
	
	//@Column( name = "TECNICO_PROV_STUDIO" )
	private String tecnicoProvStudio;
	
	//@Column( name = "TECNICO_STATO_STUDIO" )
	private String tecnicoStatoStudio;
	
	//@Column( name = "TEL_CELLULARE" )
	private String telCellulare;
	
	//@Column( name = "TEL_FAX" )
	private String telFax;
	
	//@Column( name = "TEL_FISSO" )
	private String telFisso;
	
	//@Column( name = "TIPO_REFERENTE" ) // enum
	private String tipoReferente;
	
	//@Column( name = "SOGGETTO_RAPPRESENTATO" )
	private String soggettoRappresentato;
	
	//@Column( name = "PROG" )
	private long prog;

	public long getReferenteProgettoId() {
		return referenteProgettoId;
	}

	public void setReferenteProgettoId(long referenteProgettoId) {
		this.referenteProgettoId = referenteProgettoId;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDittaCodiceFiscale() {
		return dittaCodiceFiscale;
	}

	public void setDittaCodiceFiscale(String dittaCodiceFiscale) {
		this.dittaCodiceFiscale = dittaCodiceFiscale;
	}

	public String getDittaPartitaIva() {
		return dittaPartitaIva;
	}

	public void setDittaPartitaIva(String dittaPartitaIva) {
		this.dittaPartitaIva = dittaPartitaIva;
	}

	public String getDittaRagioneSociale() {
		return dittaRagioneSociale;
	}

	public void setDittaRagioneSociale(String dittaRagioneSociale) {
		this.dittaRagioneSociale = dittaRagioneSociale;
	}

	public String getDittaRuoloRich() {
		return dittaRuoloRich;
	}

	public void setDittaRuoloRich(String dittaRuoloRich) {
		this.dittaRuoloRich = dittaRuoloRich;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}

	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}

	public String getIndirizzoPec() {
		return indirizzoPec;
	}

	public void setIndirizzoPec(String indirizzoPec) {
		this.indirizzoPec = indirizzoPec;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumCivico() {
		return numCivico;
	}

	public void setNumCivico(String numCivico) {
		this.numCivico = numCivico;
	}

	public String getProvNascita() {
		return provNascita;
	}

	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}

	public String getProvResidenza() {
		return provResidenza;
	}

	public void setProvResidenza(String provResidenza) {
		this.provResidenza = provResidenza;
	}

	public String getStatoNascita() {
		return statoNascita;
	}

	public void setStatoNascita(String statoNascita) {
		this.statoNascita = statoNascita;
	}

	public String getStatoResidenza() {
		return statoResidenza;
	}

	public void setStatoResidenza(String statoResidenza) {
		this.statoResidenza = statoResidenza;
	}

	public long gettPraticaId() {
		return tPraticaId;
	}

	public void settPraticaId(long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}

	public String getTecnicoCapStudio() {
		return tecnicoCapStudio;
	}

	public void setTecnicoCapStudio(String tecnicoCapStudio) {
		this.tecnicoCapStudio = tecnicoCapStudio;
	}

	public String getTecnicoComuneStudio() {
		return tecnicoComuneStudio;
	}

	public void setTecnicoComuneStudio(String tecnicoComuneStudio) {
		this.tecnicoComuneStudio = tecnicoComuneStudio;
	}

	public String getTecnicoIndirizzoStudio() {
		return tecnicoIndirizzoStudio;
	}

	public void setTecnicoIndirizzoStudio(String tecnicoIndirizzoStudio) {
		this.tecnicoIndirizzoStudio = tecnicoIndirizzoStudio;
	}

	public String getTecnicoNumCivStudio() {
		return tecnicoNumCivStudio;
	}

	public void setTecnicoNumCivStudio(String tecnicoNumCivStudio) {
		this.tecnicoNumCivStudio = tecnicoNumCivStudio;
	}

	public String getTecnicoOrdCollegio() {
		return tecnicoOrdCollegio;
	}

	public void setTecnicoOrdCollegio(String tecnicoOrdCollegio) {
		this.tecnicoOrdCollegio = tecnicoOrdCollegio;
	}

	public String getTecnicoOrdCollegioNIscr() {
		return tecnicoOrdCollegioNIscr;
	}

	public void setTecnicoOrdCollegioNIscr(String tecnicoOrdCollegioNIscr) {
		this.tecnicoOrdCollegioNIscr = tecnicoOrdCollegioNIscr;
	}

	public String getTecnicoOrdCollegioSede() {
		return tecnicoOrdCollegioSede;
	}

	public void setTecnicoOrdCollegioSede(String tecnicoOrdCollegioSede) {
		this.tecnicoOrdCollegioSede = tecnicoOrdCollegioSede;
	}

	public String getTecnicoProvStudio() {
		return tecnicoProvStudio;
	}

	public void setTecnicoProvStudio(String tecnicoProvStudio) {
		this.tecnicoProvStudio = tecnicoProvStudio;
	}

	public String getTecnicoStatoStudio() {
		return tecnicoStatoStudio;
	}

	public void setTecnicoStatoStudio(String tecnicoStatoStudio) {
		this.tecnicoStatoStudio = tecnicoStatoStudio;
	}

	public String getTelCellulare() {
		return telCellulare;
	}

	public void setTelCellulare(String telCellulare) {
		this.telCellulare = telCellulare;
	}

	public String getTelFax() {
		return telFax;
	}

	public void setTelFax(String telFax) {
		this.telFax = telFax;
	}

	public String getTelFisso() {
		return telFisso;
	}

	public void setTelFisso(String telFisso) {
		this.telFisso = telFisso;
	}

	public String getTipoReferente() {
		return tipoReferente;
	}

	public void setTipoReferente(String tipoReferente) {
		this.tipoReferente = tipoReferente;
	}

	public String getSoggettoRappresentato() {
		return soggettoRappresentato;
	}

	public void setSoggettoRappresentato(String soggettoRappresentato) {
		this.soggettoRappresentato = soggettoRappresentato;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

}
