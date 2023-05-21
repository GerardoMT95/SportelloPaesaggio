package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table autpae.comuni_competenza
 */
public class ComuniCompetenzaDTO implements Serializable{

    private static final long serialVersionUID = 1269184035L;
    
    //COLUMN pratica_id
    private long praticaId;
    //COLUMN ente_id
    private long enteId;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN cod_cat
    private String codCat;
    //COLUMN cod_istat
    private String codIstat;
    //COLUMN data_inserimento
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataInserimento;
    //COLUMN creazione
    private boolean creazione;
    //COLUMN effettivo
    private boolean effettivo;
    
    
	public long getPraticaId() {
		return praticaId;
	}
	public void setPraticaId(long praticaId) {
		this.praticaId = praticaId;
	}
	public long getEnteId() {
		return enteId;
	}
	public void setEnteId(long enteId) {
		this.enteId = enteId;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCodCat() {
		return codCat;
	}
	public void setCodCat(String codCat) {
		this.codCat = codCat;
	}
	public String getCodIstat() {
		return codIstat;
	}
	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public boolean isCreazione() {
		return creazione;
	}
	public void setCreazione(boolean creazione) {
		this.creazione = creazione;
	}
	public boolean isEffettivo() {
		return effettivo;
	}
	public void setEffettivo(boolean effettivo) {
		this.effettivo = effettivo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codCat == null) ? 0 : codCat.hashCode());
		result = prime * result + ((codIstat == null) ? 0 : codIstat.hashCode());
		result = prime * result + (creazione ? 1231 : 1237);
		result = prime * result + ((dataInserimento == null) ? 0 : dataInserimento.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + (effettivo ? 1231 : 1237);
		result = prime * result + (int) (enteId ^ (enteId >>> 32));
		result = prime * result + (int) (praticaId ^ (praticaId >>> 32));
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
		ComuniCompetenzaDTO other = (ComuniCompetenzaDTO) obj;
		if (codCat == null) {
			if (other.codCat != null)
				return false;
		} else if (!codCat.equals(other.codCat))
			return false;
		if (codIstat == null) {
			if (other.codIstat != null)
				return false;
		} else if (!codIstat.equals(other.codIstat))
			return false;
		if (creazione != other.creazione)
			return false;
		if (dataInserimento == null) {
			if (other.dataInserimento != null)
				return false;
		} else if (!dataInserimento.equals(other.dataInserimento))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (effettivo != other.effettivo)
			return false;
		if (enteId != other.enteId)
			return false;
		if (praticaId != other.praticaId)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ComuniCompetenzaDTO [praticaId=" + praticaId + ", enteId=" + enteId + ", descrizione=" + descrizione
				+ ", codCat=" + codCat + ", codIstat=" + codIstat + ", dataInserimento=" + dataInserimento
				+ ", creazione=" + creazione + ", effettivo=" + effettivo + "]";
	}

}