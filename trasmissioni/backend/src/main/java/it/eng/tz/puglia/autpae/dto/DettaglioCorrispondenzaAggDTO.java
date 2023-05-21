package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.service.interfacce.RicevutaService;
import it.eng.tz.puglia.util.list.ListUtil;

public class DettaglioCorrispondenzaAggDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	@Autowired RicevutaService ricevutaService;
	
	private Long codiceComunicazione;			   			   // CorrispondenzaDTO --> id
//	private String numeroProtocollo;			   // inutile  (non protocolliamo le email)
	private String mittente;					   			   // CorrispondenzaDTO --> mittenteDenominazione (oppure mittenteUsername)
	private String mailMittente;				   			   // CorrispondenzaDTO --> mittenteEmail
	private String oggetto;						   			   // CorrispondenzaDTO --> oggetto
	private String testo;						   // aggiunto // CorrispondenzaDTO --> testo
//	private Boolean pec;						   // inutile, sempre TRUE (la corrispondenza la manda l'applicazione)
	private Date data;                                         // CorrispondenzaDTO --> dataInvio
//	private String idCms;						   // inutile  (non mandiamo mai gli idAlfresco al FE)
//	private String tipologia;					   // inutile
	private RicevutaDTO ricevutaAccettazione;		           // CorrispondenzaDTO --> stato  (se 'stato' è TRUE la vado a recuperare dal DB)
	private boolean comunicazione;				   // aggiunto // CorrispondenzaDTO --> comunicazione
	private boolean bozza;						   // aggiunto // CorrispondenzaDTO --> bozza
	private List<DestinatarioAggDTO> destinatari;
	private List<AllegatoCustomDTO> allegati;	  			   // DettaglioCorrispondenzaDTO --> allegati

	
	public DettaglioCorrispondenzaAggDTO() {
		this.destinatari = new ArrayList<DestinatarioAggDTO>();
		this.allegati    = new ArrayList<AllegatoCustomDTO>();
	}
	
	
	public DettaglioCorrispondenzaAggDTO(DettaglioCorrispondenzaDTO dettaglio) throws Exception {
		
		this.codiceComunicazione=dettaglio.getCorrispondenza().getId();
		this.mittente=dettaglio.getCorrispondenza().getMittenteDenominazione();
		this.mailMittente=dettaglio.getCorrispondenza().getMittenteEmail();
		this.oggetto=dettaglio.getCorrispondenza().getOggetto();
		this.testo=dettaglio.getCorrispondenza().getTesto();
		this.data=dettaglio.getCorrispondenza().getDataInvio();
		this.comunicazione=dettaglio.getCorrispondenza().isComunicazione();
		this.bozza=dettaglio.getCorrispondenza().isBozza();
		
//		if (dettaglio.getCorrispondenza().isStato()==true) {
//			RicevutaSearch filtro = new RicevutaSearch();
//			filtro.setIdCorrispondenza(dettaglio.getCorrispondenza().getId());
//			List<RicevutaDTO> listaRicevute = ricevutaService.getRicevuteCorrispondenza(filtro).getList();
//			if (listaRicevute.size()!=1) {
//				throw new Exception("ERRORE. Trovate più ricevute (di accettazione) associate alla stessa corrispondenza! [id="+dettaglio.getCorrispondenza().getId()+"]");
//			}
//			this.ricevutaAccettazione = listaRicevute.get(0);
//		}
		this.ricevutaAccettazione=ListUtil.isNotEmpty(dettaglio.getRicevutaAccettazione())?dettaglio.getRicevutaAccettazione().get(0):null;
		this.destinatari = new ArrayList<DestinatarioAggDTO>();
		if (dettaglio.getDestinatari()!=null)
			dettaglio.getDestinatari().forEach(destinatario->{
				this.destinatari.add(new DestinatarioAggDTO(destinatario));
			});
		this.allegati = dettaglio.getAllegatiInfo();
	}


	public Long getCodiceComunicazione() {
		return codiceComunicazione;
	}
	public void setCodiceComunicazione(Long codiceComunicazione) {
		this.codiceComunicazione = codiceComunicazione;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getMailMittente() {
		return mailMittente;
	}
	public void setMailMittente(String mailMittente) {
		this.mailMittente = mailMittente;
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public boolean isComunicazione() {
		return comunicazione;
	}
	public void setComunicazione(boolean comunicazione) {
		this.comunicazione = comunicazione;
	}
	public boolean isBozza() {
		return bozza;
	}
	public void setBozza(boolean bozza) {
		this.bozza = bozza;
	}
	public List<DestinatarioAggDTO> getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(List<DestinatarioAggDTO> destinatari) {
		this.destinatari = destinatari;
	}
	public List<AllegatoCustomDTO> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<AllegatoCustomDTO> allegati) {
		this.allegati = allegati;
	}
	public RicevutaDTO getRicevutaAccettazione() {
		return ricevutaAccettazione;
	}
	public void setRicevutaAccettazione(RicevutaDTO ricevutaAccettazione) {
		this.ricevutaAccettazione = ricevutaAccettazione;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allegati == null) ? 0 : allegati.hashCode());
		result = prime * result + (bozza ? 1231 : 1237);
		result = prime * result + ((codiceComunicazione == null) ? 0 : codiceComunicazione.hashCode());
		result = prime * result + (comunicazione ? 1231 : 1237);
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((destinatari == null) ? 0 : destinatari.hashCode());
		result = prime * result + ((mailMittente == null) ? 0 : mailMittente.hashCode());
		result = prime * result + ((mittente == null) ? 0 : mittente.hashCode());
		result = prime * result + ((oggetto == null) ? 0 : oggetto.hashCode());
		result = prime * result + ((ricevutaAccettazione == null) ? 0 : ricevutaAccettazione.hashCode());
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
		DettaglioCorrispondenzaAggDTO other = (DettaglioCorrispondenzaAggDTO) obj;
		if (allegati == null) {
			if (other.allegati != null)
				return false;
		} else if (!allegati.equals(other.allegati))
			return false;
		if (bozza != other.bozza)
			return false;
		if (codiceComunicazione == null) {
			if (other.codiceComunicazione != null)
				return false;
		} else if (!codiceComunicazione.equals(other.codiceComunicazione))
			return false;
		if (comunicazione != other.comunicazione)
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (destinatari == null) {
			if (other.destinatari != null)
				return false;
		} else if (!destinatari.equals(other.destinatari))
			return false;
		if (mailMittente == null) {
			if (other.mailMittente != null)
				return false;
		} else if (!mailMittente.equals(other.mailMittente))
			return false;
		if (mittente == null) {
			if (other.mittente != null)
				return false;
		} else if (!mittente.equals(other.mittente))
			return false;
		if (oggetto == null) {
			if (other.oggetto != null)
				return false;
		} else if (!oggetto.equals(other.oggetto))
			return false;
		if (ricevutaAccettazione == null) {
			if (other.ricevutaAccettazione != null)
				return false;
		} else if (!ricevutaAccettazione.equals(other.ricevutaAccettazione))
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
		return "DettaglioCorrispondenzaAggDTO [codiceComunicazione=" + codiceComunicazione + ", mittente=" + mittente
				+ ", mailMittente=" + mailMittente + ", oggetto=" + oggetto + ", testo=" + testo + ", data=" + data
				+ ", ricevutaAccettazione=" + ricevutaAccettazione + ", comunicazione=" + comunicazione + ", bozza="
				+ bozza + ", destinatari=" + destinatari + ", allegati=" + allegati + "]";
	}

}
