package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

/**
 * @Table(name = "TNO_PPTR_DEST_URB_INTERV")
 * @author acolaianni
 *
 */
public class PptrDestUrbInterv {
	//@Column(name = "T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;

	//@Column(name = "STRUM_URB_APPROVATO")
	private String strumUrbApprovato;

	//@Column(name = "STRUM_URB_APPROVATO_DATA")
	private Date strumUrbApprovatoData;

	//@Column(name = "STRUM_URB_APPROVATO_ATTO")
	private String strumUrbApprovatoAtto;

	//@Column(name = "DESTIN_AREA_STR_VIG")
	private String destinAreaStrVig;

	//@Column(name = "STRUM_APPROV_ULT_TUTELE")
	private String strumApprovUltTutele;

	//@Column(name = "STRUM_URB_ADOTTATO")
	private String strumUrbAdottato;

	//@Column(name = "STRUM_URB_ADOTTATO_DATA")
	private Date strumUrbAdottatoData;

	//@Column(name = "STRUM_URB_ADOTTATO_ATTO")
	private String strumUrbAdottatoAtto;

	//@Column(name = "DESTIN_AREA_STR_ADOTT")
	private String destinAreaStrAdott;

	//@Column(name = "STRUM_ADOTT_ULT_TUTELE")
	private String strumAdottUltTutele;

	//@Column(name = "CONFORME_DISCIPL_URB_VIG")
	private String conformeDisciplUrbVig;

	//@Column(name = "CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;
	
	//@Column( name="PROG" )
	private long prog;
	
	//@Column( name="LOCALIZ_INTERV_ID" )
	private long idLocalizzaInterv;
	
	//@Column( name="ID_STRUM_URBAN_ART100" )
	private long idStrumUrbanArt100;
	
	//@Column( name="ID_STRUM_URBAN_ART38" )
	private long idStrumUrbanArt38;
	
	//@Column( name="CHECK_PRESA_VISIONE	" )
	private String checkPresaVisione;

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public String getStrumUrbApprovato() {
		return strumUrbApprovato;
	}

	public void setStrumUrbApprovato(String strumUrbApprovato) {
		this.strumUrbApprovato = strumUrbApprovato;
	}

	public Date getStrumUrbApprovatoData() {
		return strumUrbApprovatoData;
	}

	public void setStrumUrbApprovatoData(Date strumUrbApprovatoData) {
		this.strumUrbApprovatoData = strumUrbApprovatoData;
	}

	public String getStrumUrbApprovatoAtto() {
		return strumUrbApprovatoAtto;
	}

	public void setStrumUrbApprovatoAtto(String strumUrbApprovatoAtto) {
		this.strumUrbApprovatoAtto = strumUrbApprovatoAtto;
	}

	public String getDestinAreaStrVig() {
		return destinAreaStrVig;
	}

	public void setDestinAreaStrVig(String destinAreaStrVig) {
		this.destinAreaStrVig = destinAreaStrVig;
	}

	public String getStrumApprovUltTutele() {
		return strumApprovUltTutele;
	}

	public void setStrumApprovUltTutele(String strumApprovUltTutele) {
		this.strumApprovUltTutele = strumApprovUltTutele;
	}

	public String getStrumUrbAdottato() {
		return strumUrbAdottato;
	}

	public void setStrumUrbAdottato(String strumUrbAdottato) {
		this.strumUrbAdottato = strumUrbAdottato;
	}

	public Date getStrumUrbAdottatoData() {
		return strumUrbAdottatoData;
	}

	public void setStrumUrbAdottatoData(Date strumUrbAdottatoData) {
		this.strumUrbAdottatoData = strumUrbAdottatoData;
	}

	public String getStrumUrbAdottatoAtto() {
		return strumUrbAdottatoAtto;
	}

	public void setStrumUrbAdottatoAtto(String strumUrbAdottatoAtto) {
		this.strumUrbAdottatoAtto = strumUrbAdottatoAtto;
	}

	public String getDestinAreaStrAdott() {
		return destinAreaStrAdott;
	}

	public void setDestinAreaStrAdott(String destinAreaStrAdott) {
		this.destinAreaStrAdott = destinAreaStrAdott;
	}

	public String getStrumAdottUltTutele() {
		return strumAdottUltTutele;
	}

	public void setStrumAdottUltTutele(String strumAdottUltTutele) {
		this.strumAdottUltTutele = strumAdottUltTutele;
	}

	public String getConformeDisciplUrbVig() {
		return conformeDisciplUrbVig;
	}

	public void setConformeDisciplUrbVig(String conformeDisciplUrbVig) {
		this.conformeDisciplUrbVig = conformeDisciplUrbVig;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	public long getIdLocalizzaInterv() {
		return idLocalizzaInterv;
	}

	public void setIdLocalizzaInterv(long idLocalizzaInterv) {
		this.idLocalizzaInterv = idLocalizzaInterv;
	}

	public long getIdStrumUrbanArt100() {
		return idStrumUrbanArt100;
	}

	public void setIdStrumUrbanArt100(long idStrumUrbanArt100) {
		this.idStrumUrbanArt100 = idStrumUrbanArt100;
	}

	public long getIdStrumUrbanArt38() {
		return idStrumUrbanArt38;
	}

	public void setIdStrumUrbanArt38(long idStrumUrbanArt38) {
		this.idStrumUrbanArt38 = idStrumUrbanArt38;
	}

	public String getCheckPresaVisione() {
		return checkPresaVisione;
	}

	public void setCheckPresaVisione(String checkPresaVisione) {
		this.checkPresaVisione = checkPresaVisione;
	}
	
	
}
