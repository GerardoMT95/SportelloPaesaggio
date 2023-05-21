package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.autpae.dto.UlterioriInformazioniDto;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table autpae.localizzazione_intervento
 */
public class LocalizzazioneInterventoDTO implements Serializable{

    private static final long serialVersionUID = 1269184035L;
    
    //COLUMN pratica_id
    private long praticaId;
    //COLUMN comune_id
    private long comuneId;
    //COLUMN indirizzo
    private String indirizzo;
    //COLUMN num_civico
    private String numCivico;
    //COLUMN piano
    private String piano;
    //COLUMN interno
    private String interno;
    //COLUMN dest_uso_att
    private String destUsoAtt;
    //COLUMN dest_uso_prog
    private String destUsoProg;
    //COLUMN comune
    private String comune;
    //COLUMN sigla_provincia
    private String siglaProvincia;
    //COLUMN data_riferimento_catastale
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataRiferimentoCatastale;
    //COLUMN strade
    private Boolean strade;
    
    //in tabella particelle_catastali...
    private List<ParticelleCatastaliDTO> particelle;
    
    //IN altra tabella parchi_paesaggi_immobili
    private UlterioriInformazioniDto ulterioriInformazioni;

    
    
	public long getPraticaId() {
		return praticaId;
	}

	public void setPraticaId(long praticaId) {
		this.praticaId = praticaId;
	}

	public long getComuneId() {
		return comuneId;
	}

	public void setComuneId(long comuneId) {
		this.comuneId = comuneId;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumCivico() {
		return numCivico;
	}

	public void setNumCivico(String numCivico) {
		this.numCivico = numCivico;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getDestUsoAtt() {
		return destUsoAtt;
	}

	public void setDestUsoAtt(String destUsoAtt) {
		this.destUsoAtt = destUsoAtt;
	}

	public String getDestUsoProg() {
		return destUsoProg;
	}

	public void setDestUsoProg(String destUsoProg) {
		this.destUsoProg = destUsoProg;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public Date getDataRiferimentoCatastale() {
		return dataRiferimentoCatastale;
	}

	public void setDataRiferimentoCatastale(Date dataRiferimentoCatastale) {
		this.dataRiferimentoCatastale = dataRiferimentoCatastale;
	}

	public Boolean getStrade() {
		return strade;
	}

	public void setStrade(Boolean strade) {
		this.strade = strade;
	}

	public List<ParticelleCatastaliDTO> getParticelle() {
		return particelle;
	}

	public void setParticelle(List<ParticelleCatastaliDTO> particelle) {
		this.particelle = particelle;
	}

	public UlterioriInformazioniDto getUlterioriInformazioni() {
		return ulterioriInformazioni;
	}

	public void setUlterioriInformazioni(UlterioriInformazioniDto ulterioriInformazioni) {
		this.ulterioriInformazioni = ulterioriInformazioni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comune == null) ? 0 : comune.hashCode());
		result = prime * result + (int) (comuneId ^ (comuneId >>> 32));
		result = prime * result + ((dataRiferimentoCatastale == null) ? 0 : dataRiferimentoCatastale.hashCode());
		result = prime * result + ((destUsoAtt == null) ? 0 : destUsoAtt.hashCode());
		result = prime * result + ((destUsoProg == null) ? 0 : destUsoProg.hashCode());
		result = prime * result + ((indirizzo == null) ? 0 : indirizzo.hashCode());
		result = prime * result + ((interno == null) ? 0 : interno.hashCode());
		result = prime * result + ((numCivico == null) ? 0 : numCivico.hashCode());
		result = prime * result + ((particelle == null) ? 0 : particelle.hashCode());
		result = prime * result + ((piano == null) ? 0 : piano.hashCode());
		result = prime * result + (int) (praticaId ^ (praticaId >>> 32));
		result = prime * result + ((siglaProvincia == null) ? 0 : siglaProvincia.hashCode());
		result = prime * result + ((strade == null) ? 0 : strade.hashCode());
		result = prime * result + ((ulterioriInformazioni == null) ? 0 : ulterioriInformazioni.hashCode());
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
		LocalizzazioneInterventoDTO other = (LocalizzazioneInterventoDTO) obj;
		if (comune == null) {
			if (other.comune != null)
				return false;
		} else if (!comune.equals(other.comune))
			return false;
		if (comuneId != other.comuneId)
			return false;
		if (dataRiferimentoCatastale == null) {
			if (other.dataRiferimentoCatastale != null)
				return false;
		} else if (!dataRiferimentoCatastale.equals(other.dataRiferimentoCatastale))
			return false;
		if (destUsoAtt == null) {
			if (other.destUsoAtt != null)
				return false;
		} else if (!destUsoAtt.equals(other.destUsoAtt))
			return false;
		if (destUsoProg == null) {
			if (other.destUsoProg != null)
				return false;
		} else if (!destUsoProg.equals(other.destUsoProg))
			return false;
		if (indirizzo == null) {
			if (other.indirizzo != null)
				return false;
		} else if (!indirizzo.equals(other.indirizzo))
			return false;
		if (interno == null) {
			if (other.interno != null)
				return false;
		} else if (!interno.equals(other.interno))
			return false;
		if (numCivico == null) {
			if (other.numCivico != null)
				return false;
		} else if (!numCivico.equals(other.numCivico))
			return false;
		if (particelle == null) {
			if (other.particelle != null)
				return false;
		} else if (!particelle.equals(other.particelle))
			return false;
		if (piano == null) {
			if (other.piano != null)
				return false;
		} else if (!piano.equals(other.piano))
			return false;
		if (praticaId != other.praticaId)
			return false;
		if (siglaProvincia == null) {
			if (other.siglaProvincia != null)
				return false;
		} else if (!siglaProvincia.equals(other.siglaProvincia))
			return false;
		if (strade == null) {
			if (other.strade != null)
				return false;
		} else if (!strade.equals(other.strade))
			return false;
		if (ulterioriInformazioni == null) {
			if (other.ulterioriInformazioni != null)
				return false;
		} else if (!ulterioriInformazioni.equals(other.ulterioriInformazioni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocalizzazioneInterventoDTO [praticaId=" + praticaId + ", comuneId=" + comuneId + ", indirizzo="
				+ indirizzo + ", numCivico=" + numCivico + ", piano=" + piano + ", interno=" + interno + ", destUsoAtt="
				+ destUsoAtt + ", destUsoProg=" + destUsoProg + ", comune=" + comune + ", siglaProvincia="
				+ siglaProvincia + ", dataRiferimentoCatastale=" + dataRiferimentoCatastale + ", strade=" + strade
				+ ", particelle=" + particelle + ", ulterioriInformazioni=" + ulterioriInformazioni + "]";
	}

}