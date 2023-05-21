package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.AllegatoDTO;

public class UlterioreDocumentazioneDTO extends AllegatoDTO implements Serializable // in teoria potrei anche fargli estendere solo AllegatoCustomDTO
{
	private static final long serialVersionUID = 7545873978918968471L;

	private long idFascicolo;
	
	private List<TipologicaDestinatarioDTO> notifica; 	 // destinatarioNotifica
	private boolean direzione; //false corrisponderebbe ad "U"scita(Inviato) ? per adesso supponiamo false=>Uscita (Inviato) true=>ricevuto
	private List<String> enti;       // visibileA

	public UlterioreDocumentazioneDTO() { }

	public UlterioreDocumentazioneDTO(AllegatoDTO allegatoDTO) {
		this.setId(allegatoDTO.getId());
		this.setNome(allegatoDTO.getNome());
		this.setTitolo(allegatoDTO.getTitolo());
		this.setDescrizione(allegatoDTO.getDescrizione());
		this.setDataCaricamento(allegatoDTO.getDataCaricamento());
		this.setDimensione(allegatoDTO.getDimensione());				   // potrei cancellarlo perchè non serve al FE
		this.setMimeType(allegatoDTO.getMimeType());					   // potrei cancellarlo perchè non serve al FE
		this.setUtenteInserimento(allegatoDTO.getUtenteInserimento());
		this.setUsernameInserimento(allegatoDTO.getUsernameInserimento());
		this.setNumeroProtocolloIn(allegatoDTO.getNumeroProtocolloIn());
		this.setNumeroProtocolloOut(allegatoDTO.getNumeroProtocolloOut());
		this.setDataProtocolloIn(allegatoDTO.getDataProtocolloIn());
		this.setDataProtocolloOut(allegatoDTO.getDataProtocolloOut());
		this.setChecksum(allegatoDTO.getChecksum());
		this.setNotifica(new ArrayList<TipologicaDestinatarioDTO>());
		this.setEnti(new ArrayList<String>());
		this.setDirezione(false);
	}

	public long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public List<TipologicaDestinatarioDTO> getNotifica() {
		return notifica;
	}

	public void setNotifica(List<TipologicaDestinatarioDTO> notifica) {
		this.notifica = notifica;
	}

	public boolean isDirezione() {
		return direzione;
	}

	public void setDirezione(boolean direzione) {
		this.direzione = direzione;
	}

	public List<String> getEnti() {
		return enti;
	}

	public void setEnti(List<String> enti) {
		this.enti = enti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (direzione ? 1231 : 1237);
		result = prime * result + ((enti == null) ? 0 : enti.hashCode());
		result = prime * result + (int) (idFascicolo ^ (idFascicolo >>> 32));
		result = prime * result + ((notifica == null) ? 0 : notifica.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UlterioreDocumentazioneDTO other = (UlterioreDocumentazioneDTO) obj;
		if (direzione != other.direzione)
			return false;
		if (enti == null) {
			if (other.enti != null)
				return false;
		} else if (!enti.equals(other.enti))
			return false;
		if (idFascicolo != other.idFascicolo)
			return false;
		if (notifica == null) {
			if (other.notifica != null)
				return false;
		} else if (!notifica.equals(other.notifica))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UlterioreDocumentazioneDTO [idFascicolo=" + idFascicolo + 
			   ", notifica=" + notifica + ", direzione=" + direzione + ", enti=" + enti + "]";
	}

}
