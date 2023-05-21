package it.eng.tz.puglia.servizi_esterni.jasper.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.utility.StringWrapper;

/**
 * DTO for table autpae.localizzazione_intervento
 */
public class JasperLocalizzazioneInterventoDTO implements Serializable{

    private static final long serialVersionUID = 1269184035L;
    
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
    private Date dataRiferimentoCatastale;
    //COLUMN strade
    private Boolean strade;
    
    //particelle_catastali
    private List<ParticelleCatastaliDTO> particelle;
    
    //liste parchi_paesaggi_immobili
    private List<StringWrapper> listaParchi;
    private List<StringWrapper> listaPaesaggi;
    private List<StringWrapper> listaImmobili;
    
    
    public JasperLocalizzazioneInterventoDTO() {
    	this.particelle    = new ArrayList<>();
    	this.listaParchi   = new ArrayList<>();
    	this.listaPaesaggi = new ArrayList<>();
    	this.listaImmobili = new ArrayList<>();
	}
    
    public JasperLocalizzazioneInterventoDTO(LocalizzazioneInterventoDTO localizzazione) {
        this.indirizzo = localizzazione.getIndirizzo();
        this.numCivico = localizzazione.getNumCivico();
        this.piano = localizzazione.getPiano();
        this.interno = localizzazione.getInterno();
        this.destUsoAtt = localizzazione.getDestUsoAtt();
        this.destUsoProg = localizzazione.getDestUsoProg();
        this.comune = localizzazione.getComune();
        this.siglaProvincia = localizzazione.getSiglaProvincia();
        this.dataRiferimentoCatastale = localizzazione.getDataRiferimentoCatastale();
        this.strade = localizzazione.getStrade();
        
        this.particelle = localizzazione.getParticelle();
        if (this.particelle!=null && this.particelle.isEmpty())
        	this.particelle =null;
        
    	this.listaParchi   = new ArrayList<>();
        if (localizzazione.getUlterioriInformazioni().getBpParchiEReserve()!=null && !localizzazione.getUlterioriInformazioni().getBpParchiEReserve().isEmpty()) {
        	localizzazione.getUlterioriInformazioni().getBpParchiEReserve().forEach(selezionato-> {
        		localizzazione.getUlterioriInformazioni().getBpParchiEReserveOptions().forEach(opzione->{
        			if (opzione.getValue().equalsIgnoreCase(selezionato))
        					this.listaParchi.add(new StringWrapper("• "+"Codice Vincolo: "+opzione.getValue()+", "+opzione.getDescription()));
        		});
        	});
        }
    	this.listaPaesaggi = new ArrayList<>();
        if (localizzazione.getUlterioriInformazioni().getUcpPaesaggioRurale()!=null && !localizzazione.getUlterioriInformazioni().getUcpPaesaggioRurale().isEmpty()) {
        	localizzazione.getUlterioriInformazioni().getUcpPaesaggioRurale().forEach(selezionato-> {
        		localizzazione.getUlterioriInformazioni().getUcpPaesaggioRuraleOptions().forEach(opzione->{
        			if (opzione.getValue().equalsIgnoreCase(selezionato))
        					this.listaPaesaggi.add(new StringWrapper("• "+"Codice Vincolo: "+opzione.getValue()+", "+opzione.getDescription()));
        		});
        	});
        }
        this.listaImmobili = new ArrayList<>();
        if (localizzazione.getUlterioriInformazioni().getBpImmobileAreePubblico()!=null && !localizzazione.getUlterioriInformazioni().getBpImmobileAreePubblico().isEmpty()) {
        	localizzazione.getUlterioriInformazioni().getBpImmobileAreePubblico().forEach(selezionato-> {
        		localizzazione.getUlterioriInformazioni().getBpImmobileAreePubblicoOptions().forEach(opzione->{
        			if (opzione.getValue().equalsIgnoreCase(selezionato))
        					this.listaImmobili.add(new StringWrapper("• "+"Codice Vincolo: "+opzione.getValue()+", "+opzione.getDescription()));
        		});
        	});
        }
        if (this.listaImmobili!=null && this.listaImmobili.isEmpty())
         	this.listaImmobili =null;
        if (this.listaPaesaggi!=null && this.listaPaesaggi.isEmpty())
        	this.listaPaesaggi =null;
        if (this.listaParchi  !=null && this.listaParchi  .isEmpty())
        	this.listaParchi   =null;
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
	public List<StringWrapper> getListaParchi() {
		return listaParchi;
	}
	public void setListaParchi(List<StringWrapper> listaParchi) {
		this.listaParchi = listaParchi;
	}
	public List<StringWrapper> getListaPaesaggi() {
		return listaPaesaggi;
	}
	public void setListaPaesaggi(List<StringWrapper> listaPaesaggi) {
		this.listaPaesaggi = listaPaesaggi;
	}
	public List<StringWrapper> getListaImmobili() {
		return listaImmobili;
	}
	public void setListaImmobili(List<StringWrapper> listaImmobili) {
		this.listaImmobili = listaImmobili;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comune == null) ? 0 : comune.hashCode());
		result = prime * result + ((dataRiferimentoCatastale == null) ? 0 : dataRiferimentoCatastale.hashCode());
		result = prime * result + ((destUsoAtt == null) ? 0 : destUsoAtt.hashCode());
		result = prime * result + ((destUsoProg == null) ? 0 : destUsoProg.hashCode());
		result = prime * result + ((indirizzo == null) ? 0 : indirizzo.hashCode());
		result = prime * result + ((interno == null) ? 0 : interno.hashCode());
		result = prime * result + ((listaImmobili == null) ? 0 : listaImmobili.hashCode());
		result = prime * result + ((listaPaesaggi == null) ? 0 : listaPaesaggi.hashCode());
		result = prime * result + ((listaParchi == null) ? 0 : listaParchi.hashCode());
		result = prime * result + ((numCivico == null) ? 0 : numCivico.hashCode());
		result = prime * result + ((particelle == null) ? 0 : particelle.hashCode());
		result = prime * result + ((piano == null) ? 0 : piano.hashCode());
		result = prime * result + ((siglaProvincia == null) ? 0 : siglaProvincia.hashCode());
		result = prime * result + ((strade == null) ? 0 : strade.hashCode());
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
		JasperLocalizzazioneInterventoDTO other = (JasperLocalizzazioneInterventoDTO) obj;
		if (comune == null) {
			if (other.comune != null)
				return false;
		} else if (!comune.equals(other.comune))
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
		if (listaImmobili == null) {
			if (other.listaImmobili != null)
				return false;
		} else if (!listaImmobili.equals(other.listaImmobili))
			return false;
		if (listaPaesaggi == null) {
			if (other.listaPaesaggi != null)
				return false;
		} else if (!listaPaesaggi.equals(other.listaPaesaggi))
			return false;
		if (listaParchi == null) {
			if (other.listaParchi != null)
				return false;
		} else if (!listaParchi.equals(other.listaParchi))
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
		return true;
	}
	
	@Override
	public String toString() {
		return "JasperLocalizzazioneInterventoDTO [indirizzo=" + indirizzo + ", numCivico=" + numCivico + ", piano="
				+ piano + ", interno=" + interno + ", destUsoAtt=" + destUsoAtt + ", destUsoProg=" + destUsoProg
				+ ", comune=" + comune + ", siglaProvincia=" + siglaProvincia + ", dataRiferimentoCatastale="
				+ dataRiferimentoCatastale + ", strade=" + strade + ", particelle=" + particelle + ", listaParchi="
				+ listaParchi + ", listaPaesaggi=" + listaPaesaggi + ", listaImmobili=" + listaImmobili + "]";
	}

}