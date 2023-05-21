package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.enumeratori.TipoAssegnamento;

/**
 * DTO for table configurazione_assegnamento
 */
public class ConfigurazioneAssegnamentoDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private int idOrganizzazione;
	private String fase;
	private TipoAssegnamento criterioAssegnamento;
	private List<ValoriAssegnamentoDTO> valoriAssegnati;	// non c'è sul DB
	
	
	public ConfigurazioneAssegnamentoDTO() {
		this.valoriAssegnati = new ArrayList<>();
	}
	
	
	public int getIdOrganizzazione() {
		return idOrganizzazione;
	}
	public void setIdOrganizzazione(int idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public TipoAssegnamento getCriterioAssegnamento() {
		return criterioAssegnamento;
	}
	public void setCriterioAssegnamento(TipoAssegnamento criterioAssegnamento) {
		this.criterioAssegnamento = criterioAssegnamento;
	}
	public List<ValoriAssegnamentoDTO> getValoriAssegnati() {
		return valoriAssegnati;
	}
	public void setValoriAssegnati(List<ValoriAssegnamentoDTO> valoriAssegnati) {
		this.valoriAssegnati = valoriAssegnati;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((criterioAssegnamento == null) ? 0 : criterioAssegnamento.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + idOrganizzazione;
		result = prime * result + ((valoriAssegnati == null) ? 0 : valoriAssegnati.hashCode());
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
		ConfigurazioneAssegnamentoDTO other = (ConfigurazioneAssegnamentoDTO) obj;
		if (criterioAssegnamento != other.criterioAssegnamento)
			return false;
		if (fase == null) {
			if (other.fase != null)
				return false;
		} else if (!fase.equals(other.fase))
			return false;
		if (idOrganizzazione != other.idOrganizzazione)
			return false;
		if (valoriAssegnati == null) {
			if (other.valoriAssegnati != null)
				return false;
		} else if (!valoriAssegnati.equals(other.valoriAssegnati))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ConfigurazioneAssegnamentoDTO [idOrganizzazione=" + idOrganizzazione + ", fase=" + fase
				+ ", criterioAssegnamento=" + criterioAssegnamento + ", valoriAssegnati=" + valoriAssegnati + "]";
	}
}