package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.Date;

import it.eng.tz.puglia.autpae.enumeratori.TipoErrore;
import it.eng.tz.puglia.autpae.enumeratori.TipoRicevuta;

/**
 * DTO for Ricvuta (ORP)
 */
public class RicevutaDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;

	private Long id;
	private Long idCorrispondenza;
	private Long idDestinatario;
	private TipoRicevuta tipoRicevuta;
	private TipoErrore errore;
	private String descrizioneErrore;
	private String idCmsEml;
	private String idCmsDatiCert;
	private String idCmsSmime;
	private Date data;
	private Long idAllegatoDatiCert;


	public RicevutaDTO() { }

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCorrispondenza() {
		return idCorrispondenza;
	}

	public void setIdCorrispondenza(Long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
	}

	public Long getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(Long idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TipoRicevuta getTipoRicevuta() {
		return tipoRicevuta;
	}

	public void setTipoRicevuta(TipoRicevuta tipoRicevuta) {
		this.tipoRicevuta = tipoRicevuta;
	}

	public TipoErrore getErrore() {
		return errore;
	}

	public void setErrore(TipoErrore errore) {
		this.errore = errore;
	}

	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}

	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	public String getIdCmsEml() {
		return idCmsEml;
	}

	public void setIdCmsEml(String idCmsEml) {
		this.idCmsEml = idCmsEml;
	}

	public String getIdCmsDatiCert() {
		return idCmsDatiCert;
	}

	public void setIdCmsDatiCert(String idCmsDatiCert) {
		this.idCmsDatiCert = idCmsDatiCert;
	}

	public String getIdCmsSmime() {
		return idCmsSmime;
	}

	public void setIdCmsSmime(String idCmsSmime) {
		this.idCmsSmime = idCmsSmime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descrizioneErrore == null) ? 0 : descrizioneErrore.hashCode());
		result = prime * result + ((errore == null) ? 0 : errore.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCmsDatiCert == null) ? 0 : idCmsDatiCert.hashCode());
		result = prime * result + ((idCmsEml == null) ? 0 : idCmsEml.hashCode());
		result = prime * result + ((idCmsSmime == null) ? 0 : idCmsSmime.hashCode());
		result = prime * result + ((idCorrispondenza == null) ? 0 : idCorrispondenza.hashCode());
		result = prime * result + ((idDestinatario == null) ? 0 : idDestinatario.hashCode());
		result = prime * result + ((tipoRicevuta == null) ? 0 : tipoRicevuta.hashCode());
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
		RicevutaDTO other = (RicevutaDTO) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descrizioneErrore == null) {
			if (other.descrizioneErrore != null)
				return false;
		} else if (!descrizioneErrore.equals(other.descrizioneErrore))
			return false;
		if (errore != other.errore)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCmsDatiCert == null) {
			if (other.idCmsDatiCert != null)
				return false;
		} else if (!idCmsDatiCert.equals(other.idCmsDatiCert))
			return false;
		if (idCmsEml == null) {
			if (other.idCmsEml != null)
				return false;
		} else if (!idCmsEml.equals(other.idCmsEml))
			return false;
		if (idCmsSmime == null) {
			if (other.idCmsSmime != null)
				return false;
		} else if (!idCmsSmime.equals(other.idCmsSmime))
			return false;
		if (idCorrispondenza == null) {
			if (other.idCorrispondenza != null)
				return false;
		} else if (!idCorrispondenza.equals(other.idCorrispondenza))
			return false;
		if (idDestinatario == null) {
			if (other.idDestinatario != null)
				return false;
		} else if (!idDestinatario.equals(other.idDestinatario))
			return false;
		if (tipoRicevuta != other.tipoRicevuta)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RicevutaDTO [id=" + id + ", idCorrispondenza=" + idCorrispondenza + ", idDestinatario=" + idDestinatario
				+ ", data=" + data + ", tipoRicevuta=" + tipoRicevuta + ", errore=" + errore + ", descrizioneErrore="
				+ descrizioneErrore + ", idCmsEml=" + idCmsEml + ", idCmsDatiCert=" + idCmsDatiCert + ", idCmsSmime="
				+ idCmsSmime + "]";
	}


	public Long getIdAllegatoDatiCert() {
		return idAllegatoDatiCert;
	}


	public void setIdAllegatoDatiCert(Long idAllegatoDatiCert) {
		this.idAllegatoDatiCert = idAllegatoDatiCert;
	}

}
