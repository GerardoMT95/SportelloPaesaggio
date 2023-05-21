package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;

public class UlterioreDocumentazioneMultiDTO implements Serializable
{
    private static final long serialVersionUID = 7910083882633972788L;
    private UUID idFascicolo;
    private List<DestinatarioDTO> notifica; // destinatarioNotifica
    private boolean direzione; //false corrisponderebbe ad "U"scita(Inviato) ? per adesso supponiamo false=>Uscita (Inviato) true=>ricevuto
    private List<String> enti; // visibileA
    private List<AllegatiDTO> allegati;
    
    public UUID getIdFascicolo()
    {
        return idFascicolo;
    }
    public void setIdFascicolo(UUID idFascicolo)
    {
        this.idFascicolo = idFascicolo;
    }
    
    public List<DestinatarioDTO> getNotifica()
    {
        return notifica;
    }
    public void setNotifica(List<DestinatarioDTO> notifica)
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
    
    public List<AllegatiDTO> getAllegati()
    {
        return allegati;
    }
    public void setAllegati(List<AllegatiDTO> allegati)
    {
        this.allegati = allegati;
    }
    
    
    
}
