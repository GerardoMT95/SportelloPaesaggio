package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiTipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;

public class DettaglioCorrispondenzaDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private CorrispondenzaDTO corrispondenza;
	private List<DestinatarioDTO> destinatari;
	private List<AllegatiTipoContenutoDTO> allegati;
	private List<AllegatiDTO> allegatiInfo;

	public DettaglioCorrispondenzaDTO()
	{
		this.destinatari = new ArrayList<DestinatarioDTO>();
		this.allegati = new ArrayList<AllegatiTipoContenutoDTO>();
		this.allegatiInfo = new ArrayList<AllegatiDTO>();
	}

	public DettaglioCorrispondenzaDTO(CorrispondenzaDTO corrispondenza)
	{
		if(corrispondenza==null) {
			this.corrispondenza = new CorrispondenzaDTO();	
		}else {
			this.corrispondenza=corrispondenza;
		}
		
//		this.corrispondenza = new CorrispondenzaDTO();
//		this.corrispondenza.setId(corrispondenza.getId());
//		this.corrispondenza.setIdEmlCmis(corrispondenza.getIdEmlCmis());
//		this.corrispondenza.setMittenteDenominazione(corrispondenza.getMittenteDenominazione());
//		this.corrispondenza.setMittenteUsername(corrispondenza.getMittenteUsername());
//		this.corrispondenza.setMittenteEmail(corrispondenza.getMittenteEmail());
//		this.corrispondenza.setMittenteEnte(corrispondenza.getMittenteEnte());
//		this.corrispondenza.setOggetto(corrispondenza.getOggetto());
//		this.corrispondenza.setTesto(corrispondenza.getTesto());
//		this.corrispondenza.setDataInvio(corrispondenza.getDataInvio());
//		this.corrispondenza.setBozza(corrispondenza.getBozza());
//		this.corrispondenza.setStato(corrispondenza.getStato());
//		this.corrispondenza.setRiservata(corrispondenza.isRiservata());
		this.destinatari = new ArrayList<DestinatarioDTO>();
		this.allegati = new ArrayList<AllegatiTipoContenutoDTO>();
		this.allegatiInfo = new ArrayList<AllegatiDTO>();
	}

	public CorrispondenzaDTO getCorrispondenza()
	{
		return corrispondenza;
	}
	public void setCorrispondenza(CorrispondenzaDTO corrispondenza)
	{
		this.corrispondenza = corrispondenza;
	}

	public List<DestinatarioDTO> getDestinatari()
	{
		return destinatari;
	}
	public void setDestinatari(List<DestinatarioDTO> destinatari)
	{
		this.destinatari = destinatari;
	}

	public List<AllegatiTipoContenutoDTO> getAllegati()
	{
		return allegati;
	}
	public void setAllegati(List<AllegatiTipoContenutoDTO> allegati)
	{
		this.allegati = allegati;
	}

	public List<AllegatiDTO> getAllegatiInfo()
	{
		return allegatiInfo;
	}

	public void setAllegatiInfo(List<AllegatiDTO> allegatiInfo)
	{
		this.allegatiInfo = allegatiInfo;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allegati == null) ? 0 : allegati.hashCode());
		result = prime * result + ((allegatiInfo == null) ? 0 : allegatiInfo.hashCode());
		result = prime * result + ((corrispondenza == null) ? 0 : corrispondenza.hashCode());
		result = prime * result + ((destinatari == null) ? 0 : destinatari.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DettaglioCorrispondenzaDTO other = (DettaglioCorrispondenzaDTO) obj;
		if (allegati == null)
		{
			if (other.allegati != null)
				return false;
		} else if (!allegati.equals(other.allegati))
			return false;
		if (allegatiInfo == null)
		{
			if (other.allegatiInfo != null)
				return false;
		} else if (!allegatiInfo.equals(other.allegatiInfo))
			return false;
		if (corrispondenza == null)
		{
			if (other.corrispondenza != null)
				return false;
		} else if (!corrispondenza.equals(other.corrispondenza))
			return false;
		if (destinatari == null)
		{
			if (other.destinatari != null)
				return false;
		} else if (!destinatari.equals(other.destinatari))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "DettaglioCorrispondenzaDTO [corrispondenza=" + corrispondenza + ", destinatari=" + destinatari
				+ ", allegati=" + allegati + ", allegatiInfo=" + allegatiInfo + "]";
	}


}
