package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.interfacce.UlterioreDocumentazioneAttribute;

public class UlterioreDocumentazioneDTO implements Serializable,UlterioreDocumentazioneAttribute{


	private static final long serialVersionUID = -2868558404425463002L;
	
	private UUID idFascicolo;
	private String titoloDocumento;
	private String descrizioneContenuto;
	private List<DestinatarioDTO> notificaA;
	private String[] visibileA;
	
	public UUID getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(UUID idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	public String getTitoloDocumento() {
		return titoloDocumento;
	}
	public void setTitoloDocumento(String titoloDocumento) {
		this.titoloDocumento = titoloDocumento;
	}
	public String getDescrizioneContenuto() {
		return descrizioneContenuto;
	}
	public void setDescrizioneContenuto(String descrizioneContenuto) {
		this.descrizioneContenuto = descrizioneContenuto;
	}
	
	public String[] getVisibileA() {
		return visibileA;
	}
	public void setVisibileA(String[] visibileA) {
		this.visibileA = visibileA;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<DestinatarioDTO> getNotificaA() {
		return notificaA;
	}
	public void setNotificaA(List<DestinatarioDTO> notificaA) {
		this.notificaA = notificaA;
	}
	@Override
	public String titoloAttribute() {
		return this.titoloDocumento;
	}
	@Override
	public String descrizioneAttibute() {
		return this.descrizioneContenuto;
	}
	
	
}
