package it.eng.tz.puglia.autpae.scheduler.executor.bean;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;

public class TrasmissioneBean implements Serializable {

	private static final long serialVersionUID = 3801866306757127263L;
	private List<TipologicaDestinatarioDTO> destinatari;
	private Long idAnteprima;
	private String protocollo;
	private InformazioniDTO infoDTO;
	
	public List<TipologicaDestinatarioDTO> getDestinatari(){
		return this.destinatari;
	}
	public void setDestinatari(final List<TipologicaDestinatarioDTO> destinatari) {
		this.destinatari = destinatari;
	}
	public Long getIdAnteprima() {
		return idAnteprima;
	}
	public void setIdAnteprima(final Long anteprimaId) {
		this.idAnteprima = anteprimaId;
	}
	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(final String protocollo) {
		this.protocollo = protocollo;
	}
	public InformazioniDTO getInfoDTO() {
		return infoDTO;
	}
	public void setInfoDTO(final InformazioniDTO infoDTO) {
		this.infoDTO = infoDTO;
	}
}
