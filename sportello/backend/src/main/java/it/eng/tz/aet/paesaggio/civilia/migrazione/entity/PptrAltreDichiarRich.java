package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

//@Table(name="TNO_PPTR_ALTRE_DICHIAR_RICH")
public class PptrAltreDichiarRich {
	//@Column(name="ID")
	private long id;
		
	////@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApPptr;
	
	//@Column(name="PROG")
	private long prog;
	
	//@Column(name="T_PRATICA_APPPTR_ID")
	private long tPraticaApPptrId;
	
	//@Column(name="CHECK_136")
	private String check136;
	
	//@Column(name="CHECK_136A")
	private String check136A;
	
	//@Column(name="CHECK_136B")
	private String check136B;
	
	//@Column(name="CHECK_136C")
	private String check136C;
	
	//@Column(name="CHECK_136D")
	private String check136D;
	
	//@Column(name="CHECK_142")
	private String check142;
	
	//@Column(name="CHECK_142_PARCHI")
	private String check142Parchi;
	
	//@Column(name="CHECK_134")
	private String check134;
	
	//@Column(name="ENTE_RILASCIO")
	private String enteRilascio;
	
	//@Column(name="DESCR_AUT_RILASCIATA")
	private String descrAutRilasciata;
		
	//@Column(name="DATA_RILASCIO")
	private Date dataRilascio;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodicePraticaApPptr() {
		return codicePraticaApPptr;
	}

	public void setCodicePraticaApPptr(String codicePraticaApPptr) {
		this.codicePraticaApPptr = codicePraticaApPptr;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	public long gettPraticaApPptrId() {
		return tPraticaApPptrId;
	}

	public void settPraticaApPptrId(long tPraticaApPptrId) {
		this.tPraticaApPptrId = tPraticaApPptrId;
	}

	public String getCheck136() {
		return check136;
	}

	public void setCheck136(String check136) {
		this.check136 = check136;
	}

	public String getCheck136A() {
		return check136A;
	}

	public void setCheck136A(String check136a) {
		check136A = check136a;
	}

	public String getCheck136B() {
		return check136B;
	}

	public void setCheck136B(String check136b) {
		check136B = check136b;
	}

	public String getCheck136C() {
		return check136C;
	}

	public void setCheck136C(String check136c) {
		check136C = check136c;
	}

	public String getCheck136D() {
		return check136D;
	}

	public void setCheck136D(String check136d) {
		check136D = check136d;
	}

	public String getCheck142() {
		return check142;
	}

	public void setCheck142(String check142) {
		this.check142 = check142;
	}

	public String getCheck142Parchi() {
		return check142Parchi;
	}

	public void setCheck142Parchi(String check142Parchi) {
		this.check142Parchi = check142Parchi;
	}

	public String getCheck134() {
		return check134;
	}

	public void setCheck134(String check134) {
		this.check134 = check134;
	}

	public String getEnteRilascio() {
		return enteRilascio;
	}

	public void setEnteRilascio(String enteRilascio) {
		this.enteRilascio = enteRilascio;
	}

	public String getDescrAutRilasciata() {
		return descrAutRilasciata;
	}

	public void setDescrAutRilasciata(String descrAutRilasciata) {
		this.descrAutRilasciata = descrAutRilasciata;
	}

	public Date getDataRilascio() {
		return dataRilascio;
	}

	public void setDataRilascio(Date dataRilascio) {
		this.dataRilascio = dataRilascio;
	}
	
}
