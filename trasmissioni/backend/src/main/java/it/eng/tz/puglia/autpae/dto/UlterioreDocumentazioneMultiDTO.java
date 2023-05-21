package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.AllegatoDTO;

public class UlterioreDocumentazioneMultiDTO implements Serializable
{
    private static final long serialVersionUID = 7910083882633972788L;
    private long idFascicolo;
	
    private List<TipologicaDestinatarioDTO> notifica; // destinatarioNotifica
    private boolean direzione; //false corrisponderebbe ad "U"scita(Inviato) ? per adesso supponiamo false=>Uscita (Inviato) true=>ricevuto
    private List<String> enti; // visibileA
    private List<AllegatoDTO> allegati;
    
    public long getIdFascicolo()
    {
        return idFascicolo;
    }
    public void setIdFascicolo(long idFascicolo)
    {
        this.idFascicolo = idFascicolo;
    }
    
    public List<TipologicaDestinatarioDTO> getNotifica()
    {
        return notifica;
    }
    public void setNotifica(List<TipologicaDestinatarioDTO> notifica)
    {
        this.notifica = notifica;
    }
    
    public boolean isDirezione()
    {
        return direzione;
    }
    public void setDirezione(boolean direzione)
    {
        this.direzione = direzione;
    }
    
    public List<String> getEnti()
    {
        return enti;
    }
    public void setEnti(List<String> enti)
    {
        this.enti = enti;
    }
    
    public List<AllegatoDTO> getAllegati()
    {
        return allegati;
    }
    public void setAllegati(List<AllegatoDTO> allegati)
    {
        this.allegati = allegati;
    }
    
    
    
}
