package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for table public.corrispondenza
 */
public class CorrispondenzaDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	protected Long id;
	protected String messageId;
	protected String idEmlCmis;
	protected String mittenteEmail;
	protected String mittenteDenominazione;
	protected String mittenteUsername;
	protected String mittenteEnte;
	protected String oggetto;
	protected String testo;					 // text
	protected Date dataInvio;
	protected boolean stato;				 // superfluo e ridondante ma utile
	protected boolean comunicazione;
	protected boolean bozza;

	public CorrispondenzaDTO() { }

	public CorrispondenzaDTO(Long id, String oggetto, String text, boolean comunicazione, boolean bozza) {
		this.id = id;
		this.oggetto = oggetto;
		this.testo = text;
		this.comunicazione = comunicazione;
		this.bozza = bozza; 
	}

	
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMittenteEmail() {
		return mittenteEmail;
	}

	public void setMittenteEmail(String mittenteEmail) {
		this.mittenteEmail = mittenteEmail;
	}

	public String getMittenteDenominazione() {
		return mittenteDenominazione;
	}

	public void setMittenteDenominazione(String mittenteDenominazione) {
		this.mittenteDenominazione = mittenteDenominazione;
	}

	public String getMittenteEnte() {
		return mittenteEnte;
	}

	public void setMittenteEnte(String mittenteEnte) {
		this.mittenteEnte = mittenteEnte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isComunicazione() {
		return comunicazione;
	}

	public void setComunicazione(boolean comunicazione) {
		this.comunicazione = comunicazione;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public boolean isBozza() {
		return bozza;
	}

	public void setBozza(boolean bozza) {
		this.bozza = bozza;
	}

	public String getMittenteUsername() {
		return mittenteUsername;
	}

	public void setMittenteUsername(String mittenteUsername) {
		this.mittenteUsername = mittenteUsername;
	}

	public String getIdEmlCmis() {
		return idEmlCmis;
	}

	public void setIdEmlCmis(String idEmlCmis) {
		this.idEmlCmis = idEmlCmis;
	}

	public boolean isStato() {
		return stato;
	}

	public void setStato(boolean stato) {
		this.stato = stato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bozza ? 1231 : 1237);
		result = prime * result + (comunicazione ? 1231 : 1237);
		result = prime * result + ((dataInvio == null) ? 0 : dataInvio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idEmlCmis == null) ? 0 : idEmlCmis.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((mittenteDenominazione == null) ? 0 : mittenteDenominazione.hashCode());
		result = prime * result + ((mittenteEmail == null) ? 0 : mittenteEmail.hashCode());
		result = prime * result + ((mittenteEnte == null) ? 0 : mittenteEnte.hashCode());
		result = prime * result + ((mittenteUsername == null) ? 0 : mittenteUsername.hashCode());
		result = prime * result + ((oggetto == null) ? 0 : oggetto.hashCode());
		result = prime * result + (stato ? 1231 : 1237);
		result = prime * result + ((testo == null) ? 0 : testo.hashCode());
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
		CorrispondenzaDTO other = (CorrispondenzaDTO) obj;
		if (bozza != other.bozza)
			return false;
		if (comunicazione != other.comunicazione)
			return false;
		if (dataInvio == null) {
			if (other.dataInvio != null)
				return false;
		} else if (!dataInvio.equals(other.dataInvio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idEmlCmis == null) {
			if (other.idEmlCmis != null)
				return false;
		} else if (!idEmlCmis.equals(other.idEmlCmis))
			return false;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (mittenteDenominazione == null) {
			if (other.mittenteDenominazione != null)
				return false;
		} else if (!mittenteDenominazione.equals(other.mittenteDenominazione))
			return false;
		if (mittenteEmail == null) {
			if (other.mittenteEmail != null)
				return false;
		} else if (!mittenteEmail.equals(other.mittenteEmail))
			return false;
		if (mittenteEnte == null) {
			if (other.mittenteEnte != null)
				return false;
		} else if (!mittenteEnte.equals(other.mittenteEnte))
			return false;
		if (mittenteUsername == null) {
			if (other.mittenteUsername != null)
				return false;
		} else if (!mittenteUsername.equals(other.mittenteUsername))
			return false;
		if (oggetto == null) {
			if (other.oggetto != null)
				return false;
		} else if (!oggetto.equals(other.oggetto))
			return false;
		if (stato != other.stato)
			return false;
		if (testo == null) {
			if (other.testo != null)
				return false;
		} else if (!testo.equals(other.testo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CorrispondenzaDTO [id=" + id + ", messageId=" + messageId + ", idEmlCmis=" + idEmlCmis
				+ ", mittenteEmail=" + mittenteEmail + ", mittenteDenominazione=" + mittenteDenominazione
				+ ", mittenteUsername=" + mittenteUsername + ", mittenteEnte=" + mittenteEnte + ", oggetto=" + oggetto
				+ ", testo=" + testo + ", dataInvio=" + dataInvio + ", stato=" + stato + ", comunicazione="
				+ comunicazione + ", bozza=" + bozza + "]";
	}

}
