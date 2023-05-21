/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.util.Date;


/**
 * CIVILIA_CS.TNO_PPTR_TIPO_INTERVENTO
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrTipoIntervento {
	
	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;
	
	//@Column(name="INTERVENTI_NO_EDIL")
	private String interventiNoEdil;
	
	//@Column(name="MANUTENZIONE")
	private String manutenzione;
	
	//@Column(name="NUOVA_COSTRUZIONE")
	private String nuovaCostruzione;
	               
	//@Column(name="RIST_EDILIZIA")
	private String ristEdilizia;
	            
	//@Column(name="RIST_URBANISTICA")
	private String ristUrbanistica;
	
	//@Column(name="CONFORM_INTERV_ARTT_REG_EDIL")
	private String conformIntervArttRegEdil;
	
/*	@Column(name="CONFORM_DATA_APPROV_REG_EDIL")
	private String conformDataApprovRegEdil;
	*/
	//@Temporal(TemporalType.DATE)
	//@Column(name="CONFORM_DATA_APPROV_REG_EDIL")
	private Date conformDataApprovRegEdil;
	
	//@Column(name="PROG")
	private long prog;
	
	//@Column(name="CONFORM_ATTO_APPROV_REG_EDIL")
	private String conformAttoApprovRegEdil;
	
	
	public String getConformAttoApprovRegEdil() {
		return conformAttoApprovRegEdil;
	}

	public void setConformAttoApprovRegEdil(String conformAttoApprovRegEdil) {
		this.conformAttoApprovRegEdil = conformAttoApprovRegEdil;
	}

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public String getInterventiNoEdil() {
		return interventiNoEdil;
	}

	public void setInterventiNoEdil(String interventiNoEdil) {
		this.interventiNoEdil = interventiNoEdil;
	}

	public String getManutenzione() {
		return manutenzione;
	}

	public void setManutenzione(String manutenzione) {
		this.manutenzione = manutenzione;
	}

	public String getNuovaCostruzione() {
		return nuovaCostruzione;
	}

	public void setNuovaCostruzione(String nuovaCostruzione) {
		this.nuovaCostruzione = nuovaCostruzione;
	}

	public String getRistEdilizia() {
		return ristEdilizia;
	}

	public void setRistEdilizia(String ristEdilizia) {
		this.ristEdilizia = ristEdilizia;
	}

	public String getRistUrbanistica() {
		return ristUrbanistica;
	}

	public void setRistUrbanistica(String ristUrbanistica) {
		this.ristUrbanistica = ristUrbanistica;
	}

	public String getConformIntervArttRegEdil() {
		return conformIntervArttRegEdil;
	}

	public void setConformIntervArttRegEdil(String conformIntervArttRegEdil) {
		this.conformIntervArttRegEdil = conformIntervArttRegEdil;
	}

	public Date getConformDataApprovRegEdil() {
		return conformDataApprovRegEdil;
	}

	public void setConformDataApprovRegEdil(Date conformDataApprovRegEdil) {
		this.conformDataApprovRegEdil = conformDataApprovRegEdil;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	@Override
	public String toString() {
		return "PptrTipoIntervento [tPraticaApptrId=" + tPraticaApptrId + ", interventiNoEdil=" + interventiNoEdil
				+ ", manutenzione=" + manutenzione + ", nuovaCostruzione=" + nuovaCostruzione + ", ristEdilizia="
				+ ristEdilizia + ", ristUrbanistica=" + ristUrbanistica + ", conformIntervArttRegEdil="
				+ conformIntervArttRegEdil + ", conformDataApprovRegEdil=" + conformDataApprovRegEdil + ", prog=" + prog
				+ "]";
	}
	
	

}
