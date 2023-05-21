package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PM_GruppiRuoli implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String nomeGruppo;
	private String codiceGruppo;
	private String descrizioneGruppo;
	private String idApplicazione;
	private Object idGruppoPadre;
	private Object idTipo;
	private Object nomePadre;
	private String nomeApplicazione;
	private String codiceApplicazione;
	private Object nomeRuolo;
	private Object nomeTipo;
	private List<PM_Ruoli> ruoli = null;
	private List<Object> idUtentiResponsabili = null;
	private Integer numeroRuoliAssociati;
	private Integer numeroResponsabili;
	//aggiungo anche in questa mappa "denominazioneEnte" che corrisponde alla denominazione su tabella common.ente
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	
	public PM_GruppiRuoli() {
	}

	public PM_GruppiRuoli(String id, String nomeGruppo, String codiceGruppo, String descrizioneGruppo,
			String idApplicazione, Object idGruppoPadre, Object idTipo, Object nomePadre, String nomeApplicazione,
			String codiceApplicazione, Object nomeRuolo, Object nomeTipo, List<PM_Ruoli> ruoli,
			List<Object> idUtentiResponsabili, Integer numeroRuoliAssociati, Integer numeroResponsabili,
			Map<String, Object> additionalProperties) {
		super();
		this.id = id;
		this.nomeGruppo = nomeGruppo;
		this.codiceGruppo = codiceGruppo;
		this.descrizioneGruppo = descrizioneGruppo;
		this.idApplicazione = idApplicazione;
		this.idGruppoPadre = idGruppoPadre;
		this.idTipo = idTipo;
		this.nomePadre = nomePadre;
		this.nomeApplicazione = nomeApplicazione;
		this.codiceApplicazione = codiceApplicazione;
		this.nomeRuolo = nomeRuolo;
		this.nomeTipo = nomeTipo;
		this.ruoli = ruoli;
		this.idUtentiResponsabili = idUtentiResponsabili;
		this.numeroRuoliAssociati = numeroRuoliAssociati;
		this.numeroResponsabili = numeroResponsabili;
		this.additionalProperties = additionalProperties;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeGruppo() {
		return nomeGruppo;
	}

	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}

	public String getCodiceGruppo() {
		return codiceGruppo;
	}

	public void setCodiceGruppo(String codiceGruppo) {
		this.codiceGruppo = codiceGruppo;
	}

	public String getDescrizioneGruppo() {
		return descrizioneGruppo;
	}

	public void setDescrizioneGruppo(String descrizioneGruppo) {
		this.descrizioneGruppo = descrizioneGruppo;
	}

	public String getIdApplicazione() {
		return idApplicazione;
	}

	public void setIdApplicazione(String idApplicazione) {
		this.idApplicazione = idApplicazione;
	}

	public Object getIdGruppoPadre() {
		return idGruppoPadre;
	}

	public void setIdGruppoPadre(Object idGruppoPadre) {
		this.idGruppoPadre = idGruppoPadre;
	}

	public Object getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Object idTipo) {
		this.idTipo = idTipo;
	}

	public Object getNomePadre() {
		return nomePadre;
	}

	public void setNomePadre(Object nomePadre) {
		this.nomePadre = nomePadre;
	}

	public String getNomeApplicazione() {
		return nomeApplicazione;
	}

	public void setNomeApplicazione(String nomeApplicazione) {
		this.nomeApplicazione = nomeApplicazione;
	}

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	public Object getNomeRuolo() {
		return nomeRuolo;
	}

	public void setNomeRuolo(Object nomeRuolo) {
		this.nomeRuolo = nomeRuolo;
	}

	public Object getNomeTipo() {
		return nomeTipo;
	}

	public void setNomeTipo(Object nomeTipo) {
		this.nomeTipo = nomeTipo;
	}

	public List<PM_Ruoli> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<PM_Ruoli> ruoli) {
		this.ruoli = ruoli;
	}

	public List<Object> getIdUtentiResponsabili() {
		return idUtentiResponsabili;
	}

	public void setIdUtentiResponsabili(List<Object> idUtentiResponsabili) {
		this.idUtentiResponsabili = idUtentiResponsabili;
	}

	public Integer getNumeroRuoliAssociati() {
		return numeroRuoliAssociati;
	}

	public void setNumeroRuoliAssociati(Integer numeroRuoliAssociati) {
		this.numeroRuoliAssociati = numeroRuoliAssociati;
	}

	public Integer getNumeroResponsabili() {
		return numeroResponsabili;
	}

	public void setNumeroResponsabili(Integer numeroResponsabili) {
		this.numeroResponsabili = numeroResponsabili;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalProperties == null) ? 0 : additionalProperties.hashCode());
		result = prime * result + ((codiceApplicazione == null) ? 0 : codiceApplicazione.hashCode());
		result = prime * result + ((codiceGruppo == null) ? 0 : codiceGruppo.hashCode());
		result = prime * result + ((descrizioneGruppo == null) ? 0 : descrizioneGruppo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idApplicazione == null) ? 0 : idApplicazione.hashCode());
		result = prime * result + ((idGruppoPadre == null) ? 0 : idGruppoPadre.hashCode());
		result = prime * result + ((idTipo == null) ? 0 : idTipo.hashCode());
		result = prime * result + ((idUtentiResponsabili == null) ? 0 : idUtentiResponsabili.hashCode());
		result = prime * result + ((nomeApplicazione == null) ? 0 : nomeApplicazione.hashCode());
		result = prime * result + ((nomeGruppo == null) ? 0 : nomeGruppo.hashCode());
		result = prime * result + ((nomePadre == null) ? 0 : nomePadre.hashCode());
		result = prime * result + ((nomeRuolo == null) ? 0 : nomeRuolo.hashCode());
		result = prime * result + ((nomeTipo == null) ? 0 : nomeTipo.hashCode());
		result = prime * result + ((numeroResponsabili == null) ? 0 : numeroResponsabili.hashCode());
		result = prime * result + ((numeroRuoliAssociati == null) ? 0 : numeroRuoliAssociati.hashCode());
		result = prime * result + ((ruoli == null) ? 0 : ruoli.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PM_GruppiRuoli other = (PM_GruppiRuoli) obj;
		if (additionalProperties == null) {
			if (other.additionalProperties != null)
				return false;
		} else if (!additionalProperties.equals(other.additionalProperties))
			return false;
		if (codiceApplicazione == null) {
			if (other.codiceApplicazione != null)
				return false;
		} else if (!codiceApplicazione.equals(other.codiceApplicazione))
			return false;
		if (codiceGruppo == null) {
			if (other.codiceGruppo != null)
				return false;
		} else if (!codiceGruppo.equals(other.codiceGruppo))
			return false;
		if (descrizioneGruppo == null) {
			if (other.descrizioneGruppo != null)
				return false;
		} else if (!descrizioneGruppo.equals(other.descrizioneGruppo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idApplicazione == null) {
			if (other.idApplicazione != null)
				return false;
		} else if (!idApplicazione.equals(other.idApplicazione))
			return false;
		if (idGruppoPadre == null) {
			if (other.idGruppoPadre != null)
				return false;
		} else if (!idGruppoPadre.equals(other.idGruppoPadre))
			return false;
		if (idTipo == null) {
			if (other.idTipo != null)
				return false;
		} else if (!idTipo.equals(other.idTipo))
			return false;
		if (idUtentiResponsabili == null) {
			if (other.idUtentiResponsabili != null)
				return false;
		} else if (!idUtentiResponsabili.equals(other.idUtentiResponsabili))
			return false;
		if (nomeApplicazione == null) {
			if (other.nomeApplicazione != null)
				return false;
		} else if (!nomeApplicazione.equals(other.nomeApplicazione))
			return false;
		if (nomeGruppo == null) {
			if (other.nomeGruppo != null)
				return false;
		} else if (!nomeGruppo.equals(other.nomeGruppo))
			return false;
		if (nomePadre == null) {
			if (other.nomePadre != null)
				return false;
		} else if (!nomePadre.equals(other.nomePadre))
			return false;
		if (nomeRuolo == null) {
			if (other.nomeRuolo != null)
				return false;
		} else if (!nomeRuolo.equals(other.nomeRuolo))
			return false;
		if (nomeTipo == null) {
			if (other.nomeTipo != null)
				return false;
		} else if (!nomeTipo.equals(other.nomeTipo))
			return false;
		if (numeroResponsabili == null) {
			if (other.numeroResponsabili != null)
				return false;
		} else if (!numeroResponsabili.equals(other.numeroResponsabili))
			return false;
		if (numeroRuoliAssociati == null) {
			if (other.numeroRuoliAssociati != null)
				return false;
		} else if (!numeroRuoliAssociati.equals(other.numeroRuoliAssociati))
			return false;
		if (ruoli == null) {
			if (other.ruoli != null)
				return false;
		} else if (!ruoli.equals(other.ruoli))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GruppiRuoli [id=" + id + ", nomeGruppo=" + nomeGruppo + ", codiceGruppo=" + codiceGruppo
				+ ", descrizioneGruppo=" + descrizioneGruppo + ", idApplicazione=" + idApplicazione + ", idGruppoPadre="
				+ idGruppoPadre + ", idTipo=" + idTipo + ", nomePadre=" + nomePadre + ", nomeApplicazione="
				+ nomeApplicazione + ", codiceApplicazione=" + codiceApplicazione + ", nomeRuolo=" + nomeRuolo
				+ ", nomeTipo=" + nomeTipo + ", ruoli=" + ruoli + ", idUtentiResponsabili=" + idUtentiResponsabili
				+ ", numeroRuoliAssociati=" + numeroRuoliAssociati + ", numeroResponsabili=" + numeroResponsabili
				+ ", additionalProperties=" + additionalProperties + "]";
	}
	
}
