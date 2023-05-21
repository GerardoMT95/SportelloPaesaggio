package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;

public class DocumentazioneAmministrativaDto extends Documentazione implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8708398990296722909L;

	private List<AllegatiDTO>   grigliaPagamentoAllegati;

	public List<AllegatiDTO> getGrigliaPagamentoAllegati() {
		return grigliaPagamentoAllegati;
	}

	public void setGrigliaPagamentoAllegati(List<AllegatiDTO> grigliaPagamentoAllegati) {
		this.grigliaPagamentoAllegati = grigliaPagamentoAllegati;
	}
	

}
