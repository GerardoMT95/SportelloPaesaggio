package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PM_Ruoli implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String nomeRuolo;
	private String descrizioneRuolo;
	private Object ruoloPadre;
	private String applicazione;
	private String codiceRuolo;
	private Object gruppi;
	private Object permessi;
	private Object nomePadre;
	private String nomeApplicazione;
	private Integer numeroGruppi;
	private Integer numeroPermessi;
	private Object gruppiEntity;
	private List<Permesso> permessiEntity = null;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	
	public PM_Ruoli() {
	}

	public PM_Ruoli(String id, String nomeRuolo, String descrizioneRuolo, Object ruoloPadre, String applicazione,
			String codiceRuolo, Object gruppi, Object permessi, Object nomePadre, String nomeApplicazione,
			Integer numeroGruppi, Integer numeroPermessi, Object gruppiEntity, List<Permesso> permessiEntity,
			Map<String, Object> additionalProperties) {
		super();
		this.id = id;
		this.nomeRuolo = nomeRuolo;
		this.descrizioneRuolo = descrizioneRuolo;
		this.ruoloPadre = ruoloPadre;
		this.applicazione = applicazione;
		this.codiceRuolo = codiceRuolo;
		this.gruppi = gruppi;
		this.permessi = permessi;
		this.nomePadre = nomePadre;
		this.nomeApplicazione = nomeApplicazione;
		this.numeroGruppi = numeroGruppi;
		this.numeroPermessi = numeroPermessi;
		this.gruppiEntity = gruppiEntity;
		this.permessiEntity = permessiEntity;
		this.additionalProperties = additionalProperties;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeRuolo() {
		return nomeRuolo;
	}

	public void setNomeRuolo(String nomeRuolo) {
		this.nomeRuolo = nomeRuolo;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}

	public Object getRuoloPadre() {
		return ruoloPadre;
	}

	public void setRuoloPadre(Object ruoloPadre) {
		this.ruoloPadre = ruoloPadre;
	}

	public String getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}

	public Object getGruppi() {
		return gruppi;
	}

	public void setGruppi(Object gruppi) {
		this.gruppi = gruppi;
	}

	public Object getPermessi() {
		return permessi;
	}

	public void setPermessi(Object permessi) {
		this.permessi = permessi;
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

	public Integer getNumeroGruppi() {
		return numeroGruppi;
	}

	public void setNumeroGruppi(Integer numeroGruppi) {
		this.numeroGruppi = numeroGruppi;
	}

	public Integer getNumeroPermessi() {
		return numeroPermessi;
	}

	public void setNumeroPermessi(Integer numeroPermessi) {
		this.numeroPermessi = numeroPermessi;
	}

	public Object getGruppiEntity() {
		return gruppiEntity;
	}

	public void setGruppiEntity(Object gruppiEntity) {
		this.gruppiEntity = gruppiEntity;
	}

	public List<Permesso> getPermessiEntity() {
		return permessiEntity;
	}

	public void setPermessiEntity(List<Permesso> permessiEntity) {
		this.permessiEntity = permessiEntity;
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
		result = prime * result + ((applicazione == null) ? 0 : applicazione.hashCode());
		result = prime * result + ((codiceRuolo == null) ? 0 : codiceRuolo.hashCode());
		result = prime * result + ((descrizioneRuolo == null) ? 0 : descrizioneRuolo.hashCode());
		result = prime * result + ((gruppi == null) ? 0 : gruppi.hashCode());
		result = prime * result + ((gruppiEntity == null) ? 0 : gruppiEntity.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeApplicazione == null) ? 0 : nomeApplicazione.hashCode());
		result = prime * result + ((nomePadre == null) ? 0 : nomePadre.hashCode());
		result = prime * result + ((nomeRuolo == null) ? 0 : nomeRuolo.hashCode());
		result = prime * result + ((numeroGruppi == null) ? 0 : numeroGruppi.hashCode());
		result = prime * result + ((numeroPermessi == null) ? 0 : numeroPermessi.hashCode());
		result = prime * result + ((permessi == null) ? 0 : permessi.hashCode());
		result = prime * result + ((permessiEntity == null) ? 0 : permessiEntity.hashCode());
		result = prime * result + ((ruoloPadre == null) ? 0 : ruoloPadre.hashCode());
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
		PM_Ruoli other = (PM_Ruoli) obj;
		if (additionalProperties == null) {
			if (other.additionalProperties != null)
				return false;
		} else if (!additionalProperties.equals(other.additionalProperties))
			return false;
		if (applicazione == null) {
			if (other.applicazione != null)
				return false;
		} else if (!applicazione.equals(other.applicazione))
			return false;
		if (codiceRuolo == null) {
			if (other.codiceRuolo != null)
				return false;
		} else if (!codiceRuolo.equals(other.codiceRuolo))
			return false;
		if (descrizioneRuolo == null) {
			if (other.descrizioneRuolo != null)
				return false;
		} else if (!descrizioneRuolo.equals(other.descrizioneRuolo))
			return false;
		if (gruppi == null) {
			if (other.gruppi != null)
				return false;
		} else if (!gruppi.equals(other.gruppi))
			return false;
		if (gruppiEntity == null) {
			if (other.gruppiEntity != null)
				return false;
		} else if (!gruppiEntity.equals(other.gruppiEntity))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeApplicazione == null) {
			if (other.nomeApplicazione != null)
				return false;
		} else if (!nomeApplicazione.equals(other.nomeApplicazione))
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
		if (numeroGruppi == null) {
			if (other.numeroGruppi != null)
				return false;
		} else if (!numeroGruppi.equals(other.numeroGruppi))
			return false;
		if (numeroPermessi == null) {
			if (other.numeroPermessi != null)
				return false;
		} else if (!numeroPermessi.equals(other.numeroPermessi))
			return false;
		if (permessi == null) {
			if (other.permessi != null)
				return false;
		} else if (!permessi.equals(other.permessi))
			return false;
		if (permessiEntity == null) {
			if (other.permessiEntity != null)
				return false;
		} else if (!permessiEntity.equals(other.permessiEntity))
			return false;
		if (ruoloPadre == null) {
			if (other.ruoloPadre != null)
				return false;
		} else if (!ruoloPadre.equals(other.ruoloPadre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ruoli [id=" + id + ", nomeRuolo=" + nomeRuolo + ", descrizioneRuolo=" + descrizioneRuolo
				+ ", ruoloPadre=" + ruoloPadre + ", applicazione=" + applicazione + ", codiceRuolo=" + codiceRuolo
				+ ", gruppi=" + gruppi + ", permessi=" + permessi + ", nomePadre=" + nomePadre + ", nomeApplicazione="
				+ nomeApplicazione + ", numeroGruppi=" + numeroGruppi + ", numeroPermessi=" + numeroPermessi
				+ ", gruppiEntity=" + gruppiEntity + ", permessiEntity=" + permessiEntity + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
