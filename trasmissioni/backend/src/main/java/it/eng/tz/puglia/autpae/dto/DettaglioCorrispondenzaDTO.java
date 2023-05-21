package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;

public class DettaglioCorrispondenzaDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private CorrispondenzaDTO corrispondenza;
	private List<DestinatarioDTO> destinatari;
//	private List<TipologicaNumeriDTO> allegati;
	private List<AllegatoCustomDTO> allegatiInfo;
	private List<RicevutaDTO> ricevutaAccettazione;
	
	
	
	public List<RicevutaDTO> getRicevutaAccettazione() {
		return ricevutaAccettazione;
	}


	public void setRicevutaAccettazione(List<RicevutaDTO> ricevutaAccettazione) {
		this.ricevutaAccettazione = ricevutaAccettazione;
	}


	public DettaglioCorrispondenzaDTO() { 
		this.destinatari  = new ArrayList<DestinatarioDTO>();
//		this.allegati     = new ArrayList<TipologicaNumeriDTO>();
		this.allegatiInfo = new ArrayList<AllegatoCustomDTO>();
		this.ricevutaAccettazione = new ArrayList<>();
	}
	

	public DettaglioCorrispondenzaDTO(CorrispondenzaDTO corrispondenza) {
		this.corrispondenza = new CorrispondenzaDTO();
		this.corrispondenza.setId(corrispondenza.getId());
		this.corrispondenza.setIdEmlCmis(corrispondenza.getIdEmlCmis());
		this.corrispondenza.setMittenteDenominazione(corrispondenza.getMittenteDenominazione());
		this.corrispondenza.setMittenteUsername(corrispondenza.getMittenteUsername());
		this.corrispondenza.setMittenteEmail(corrispondenza.getMittenteEmail());
		this.corrispondenza.setMittenteEnte(corrispondenza.getMittenteEnte());
		this.corrispondenza.setOggetto(corrispondenza.getOggetto());
		this.corrispondenza.setTesto(corrispondenza.getTesto());
		this.corrispondenza.setDataInvio(corrispondenza.getDataInvio());
		this.corrispondenza.setBozza(corrispondenza.isBozza());
		this.corrispondenza.setStato(corrispondenza.isStato());
		this.destinatari  = new ArrayList<DestinatarioDTO>();
//		this.allegati     = new ArrayList<TipologicaNumeriDTO>();
		this.allegatiInfo = new ArrayList<AllegatoCustomDTO>();
		this.ricevutaAccettazione = new ArrayList<>();
	}
	
	
	public CorrispondenzaDTO getCorrispondenza() {
		return corrispondenza;
	}

	public void setCorrispondenza(CorrispondenzaDTO corrispondenza) {
		this.corrispondenza = corrispondenza;
	}

	public List<DestinatarioDTO> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<DestinatarioDTO> destinatari) {
		this.destinatari = destinatari;
	}

//	public List<TipologicaNumeriDTO> getAllegati() {
//		return allegati;
//	}
//
//	public void setAllegati(List<TipologicaNumeriDTO> allegati) {
//		this.allegati = allegati;
//	}
	public List<AllegatoCustomDTO> getAllegatiInfo() {
		return allegatiInfo;
	}

	public void setAllegatiInfo(List<AllegatoCustomDTO> allegatiInfo) {
		this.allegatiInfo = allegatiInfo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allegatiInfo == null) ? 0 : allegatiInfo.hashCode());
		result = prime * result + ((corrispondenza == null) ? 0 : corrispondenza.hashCode());
		result = prime * result + ((destinatari == null) ? 0 : destinatari.hashCode());
		result = prime * result + ((ricevutaAccettazione == null) ? 0 : ricevutaAccettazione.hashCode());
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
		DettaglioCorrispondenzaDTO other = (DettaglioCorrispondenzaDTO) obj;
		if (allegatiInfo == null) {
			if (other.allegatiInfo != null)
				return false;
		} else if (!allegatiInfo.equals(other.allegatiInfo))
			return false;
		if (corrispondenza == null) {
			if (other.corrispondenza != null)
				return false;
		} else if (!corrispondenza.equals(other.corrispondenza))
			return false;
		if (destinatari == null) {
			if (other.destinatari != null)
				return false;
		} else if (!destinatari.equals(other.destinatari))
			return false;
		if (ricevutaAccettazione == null) {
			if (other.ricevutaAccettazione != null)
				return false;
		} else if (!ricevutaAccettazione.equals(other.ricevutaAccettazione))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "DettaglioCorrispondenzaDTO [corrispondenza=" + corrispondenza + ", destinatari=" + destinatari
				+ ", allegatiInfo=" + allegatiInfo + ", ricevutaAccettazione=" + ricevutaAccettazione + "]";
	}

	
}
