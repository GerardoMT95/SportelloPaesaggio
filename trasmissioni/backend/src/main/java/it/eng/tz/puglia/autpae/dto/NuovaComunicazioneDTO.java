package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;

import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.utility.validator.EmailValidator;
import it.eng.tz.regione.puglia.webmail.be.dto.InvioMailDto;

public class NuovaComunicazioneDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private Long id;				   // idCorrispondenza
	private List<Long> idFascicoli;
	private List<TipologicaDestinatarioDTO> destinatari;
	private String oggetto;
	private String testo;
	private List<AllegatoInfoDTO> allegati;
	private List<Long> idAllegati;	   // da usare se vogliamo che gli allegati, per le bozze, siano caricati e cancellati al "salva". Attualmente chiamiamo servizi appositi
	private boolean bozza;
	private boolean comunicazione;
	private TipoTemplate tipoTemplate;
	private InfoPlaceHoldersDTO infoPlaceHolders;
	
	
	public NuovaComunicazioneDTO() {
		this.idFascicoli = new ArrayList<Long>();
		this.destinatari = new ArrayList<TipologicaDestinatarioDTO>();
		this.allegati    = new ArrayList<AllegatoInfoDTO>();
		this.idAllegati  = new ArrayList<Long>();
		this.infoPlaceHolders = new InfoPlaceHoldersDTO();
	}

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Long> getIdFascicoli() {
		return idFascicoli;
	}
	public void setIdFascicoli(List<Long> idFascicoli) {
		this.idFascicoli = idFascicoli;
	}
	public List<TipologicaDestinatarioDTO> getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(List<TipologicaDestinatarioDTO> destinatari) {
		this.destinatari = destinatari;
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
	public List<AllegatoInfoDTO> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<AllegatoInfoDTO> allegati) {
		this.allegati = allegati;
	}
	public boolean isBozza() {
		return bozza;
	}
	public void setBozza(boolean bozza) {
		this.bozza = bozza;
	}
	public List<Long> getIdAllegati() {
		return idAllegati;
	}
	public void setIdAllegati(List<Long> idAllegati) {
		this.idAllegati = idAllegati;
	}
	public TipoTemplate getTipoTemplate() {
		return tipoTemplate;
	}
	public void setTipoTemplate(TipoTemplate tipoTemplate) {
		this.tipoTemplate = tipoTemplate;
	}
	public boolean isComunicazione() {
		return comunicazione;
	}
	public void setComunicazione(boolean comunicazione) {
		this.comunicazione = comunicazione;
	}
	public InfoPlaceHoldersDTO getInfoPlaceHolders() {
		return infoPlaceHolders;
	}
	public void setInfoPlaceHolders(InfoPlaceHoldersDTO infoPlaceHolders) {
		this.infoPlaceHolders = infoPlaceHolders;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allegati == null) ? 0 : allegati.hashCode());
		result = prime * result + (bozza ? 1231 : 1237);
		result = prime * result + (comunicazione ? 1231 : 1237);
		result = prime * result + ((destinatari == null) ? 0 : destinatari.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idAllegati == null) ? 0 : idAllegati.hashCode());
		result = prime * result + ((idFascicoli == null) ? 0 : idFascicoli.hashCode());
		result = prime * result + ((infoPlaceHolders == null) ? 0 : infoPlaceHolders.hashCode());
		result = prime * result + ((oggetto == null) ? 0 : oggetto.hashCode());
		result = prime * result + ((testo == null) ? 0 : testo.hashCode());
		result = prime * result + ((tipoTemplate == null) ? 0 : tipoTemplate.hashCode());
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
		NuovaComunicazioneDTO other = (NuovaComunicazioneDTO) obj;
		if (allegati == null) {
			if (other.allegati != null)
				return false;
		} else if (!allegati.equals(other.allegati))
			return false;
		if (bozza != other.bozza)
			return false;
		if (comunicazione != other.comunicazione)
			return false;
		if (destinatari == null) {
			if (other.destinatari != null)
				return false;
		} else if (!destinatari.equals(other.destinatari))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idAllegati == null) {
			if (other.idAllegati != null)
				return false;
		} else if (!idAllegati.equals(other.idAllegati))
			return false;
		if (idFascicoli == null) {
			if (other.idFascicoli != null)
				return false;
		} else if (!idFascicoli.equals(other.idFascicoli))
			return false;
		if (infoPlaceHolders == null) {
			if (other.infoPlaceHolders != null)
				return false;
		} else if (!infoPlaceHolders.equals(other.infoPlaceHolders))
			return false;
		if (oggetto == null) {
			if (other.oggetto != null)
				return false;
		} else if (!oggetto.equals(other.oggetto))
			return false;
		if (testo == null) {
			if (other.testo != null)
				return false;
		} else if (!testo.equals(other.testo))
			return false;
		if (tipoTemplate != other.tipoTemplate)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NuovaComunicazioneDTO [id=" + id + ", idFascicoli=" + idFascicoli + ", destinatari=" + destinatari
				+ ", oggetto=" + oggetto + ", testo=" + testo + ", allegati=" + allegati + ", idAllegati=" + idAllegati
				+ ", bozza=" + bozza + ", comunicazione=" + comunicazione + ", tipoTemplate=" + tipoTemplate
				+ ", infoPlaceHolders=" + infoPlaceHolders + "]";
	}
	
	public InvioMailDto mapToInvioMailDto(String fromAddress) {
		InvioMailDto outObj=new InvioMailDto();
		outObj.setFrom(fromAddress);
		outObj.setCorpoMail(this.getTesto());
		outObj.setOggettoMail(this.getOggetto());
		Map<TipoDestinatario, List<TipologicaDestinatarioDTO>> destinatari=
				this.getDestinatari()
				.stream()
				.map(dest->{//per adesso i non intestati finiscono nel TO
					if(dest.getTipo()==null) {
						dest.setTipo(TipoDestinatario.TO);
					}return dest;
							})
				.filter(dest->dest.getTipo()!=null && List.of(TipoDestinatario.values()).contains(dest.getTipo()))
				.filter(dest->EmailValidator.getInstance().isValid(dest.getEmail()))
				.collect(Collectors.groupingBy(dest->dest.getTipo()));
		outObj.setTo(new String[0]);//evitiamo di passare il null....
		if(destinatari.get(TipoDestinatario.TO)!=null && destinatari.get(TipoDestinatario.TO).size()>0) {
			Set<String> toSet=
					destinatari.get(TipoDestinatario.TO).stream().map(tipologicaDest->tipologicaDest.getEmail()).collect(Collectors.toSet());
			String[] to=new String[toSet.size()];
			System.arraycopy(toSet.toArray(), 0, to, 0, toSet.size());
			outObj.setTo(to);
		}
		if(destinatari.get(TipoDestinatario.CC)!=null && destinatari.get(TipoDestinatario.CC).size()>0) {
			Set<String> ccSet=
					destinatari.get(TipoDestinatario.CC).stream().map(tipologicaDest->tipologicaDest.getEmail()).collect(Collectors.toSet());
			if(ccSet.size()>0) {
				String[] bcc=new String[ccSet.size()];
				System.arraycopy(ccSet.toArray(), 0, bcc, 0, ccSet.size());
				outObj.setBcc(bcc);
				 
			}
		}
		return outObj;
	}
	
	public Resource[] mapAllegatiToResource() {
		List<Resource> ret=new ArrayList<>();
		this.allegati.forEach(allegato-> {
			if(allegato.getIsUrl()==true) {
				//non lo aggiungo....
			}else {
				ret.add(allegato.getFile().getResource());	
			}
			
		});
		return ret.toArray(new Resource[0]);
	}
	
}
