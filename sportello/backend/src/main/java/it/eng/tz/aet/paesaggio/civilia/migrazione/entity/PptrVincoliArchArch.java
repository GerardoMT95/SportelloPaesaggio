package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;
/**
 * @Table( name="TNO_PPTR_VINC_ARCH_ARCH" )
 * @author acolaianni
 *
 */
public class PptrVincoliArchArch {
	//@Column( name="VINC_ARCH_ARCH_ID" )
	private long id;
	
	//@Column( name="T_PRATICA_APPTR_ID" )
	private long tPraticaApptrId;
	
	//@Column( name="CODICE_PRATICA_APPPTR" )
	private String codicePraticaAppptr;
	
	//@Column( name="VINC_ARC_NO_TUTELA" )
	private String vincArcNoTutuela;
	
	//@Column( name="VINC_ARC_MONUM_DIRETTO" )
	private String vincArcMonumDiretto;
	
	//@Column( name="VINC_ARC_MONUM_INDIRETTO" )
	private String vincArcMonumIndirett;
	
	//@Column( name="VINC_ARC_ARCHEO_DIRETTO" )
	private String vincArcArcheoDiretto;
	
	//@Column( name="VINC_ARC_ARCHEO_INDIRETTO" )
	private String vincArcArcheoIndirett;
	
	//@Column( name="ALTRI_VINCOLI" )
	private String altriVincoli;
	
	//@Column( name="PROG" )
	private long prog;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getVincArcNoTutuela() {
		return vincArcNoTutuela;
	}

	public void setVincArcNoTutuela(String vincArcNoTutuela) {
		this.vincArcNoTutuela = vincArcNoTutuela;
	}

	public String getVincArcMonumDiretto() {
		return vincArcMonumDiretto;
	}

	public void setVincArcMonumDiretto(String vincArcMonumDiretto) {
		this.vincArcMonumDiretto = vincArcMonumDiretto;
	}

	public String getVincArcMonumIndirett() {
		return vincArcMonumIndirett;
	}

	public void setVincArcMonumIndirett(String vincArcMonumIndirett) {
		this.vincArcMonumIndirett = vincArcMonumIndirett;
	}

	public String getVincArcArcheoDiretto() {
		return vincArcArcheoDiretto;
	}

	public void setVincArcArcheoDiretto(String vincArcArcheoDiretto) {
		this.vincArcArcheoDiretto = vincArcArcheoDiretto;
	}

	public String getVincArcArcheoIndirett() {
		return vincArcArcheoIndirett;
	}

	public void setVincArcArcheoIndirett(String vincArcArcheoIndirett) {
		this.vincArcArcheoIndirett = vincArcArcheoIndirett;
	}

	public String getAltriVincoli() {
		return altriVincoli;
	}

	public void setAltriVincoli(String altriVincoli) {
		this.altriVincoli = altriVincoli;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}
	
	

}
