package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.io.Serializable;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoAssegnamento;

/**
 * DTO for table valori_assegnamento
 */
public class ValoriAssegnamentoOldDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private int idOrganizzazione;
	private String fase;
	private int idComuneTipoProcedimento;			// idComune = campo "id" della tabella ENTE per i "CO"
	private String denominazioneComuneProcedimento; // non c'è sul DB
	private TipoAssegnamento tipoAssegnamento;		// non c'è sul DB
	private boolean rup;
	private String usernameUtente;
	private String denominazioneUtente;
	
	
	public ValoriAssegnamentoOldDTO() { }
	
	public ValoriAssegnamentoOldDTO(TipoAssegnamento tipoAssegnamento, int idComuneTipoProcedimento, String denominazioneComuneProcedimento) { 
		this.tipoAssegnamento = tipoAssegnamento;
		this.idComuneTipoProcedimento = idComuneTipoProcedimento;
		this.denominazioneComuneProcedimento = denominazioneComuneProcedimento;
	}
	
	
	public ValoriAssegnamentoOldDTO(ValoriAssegnamentoNewDTO valoriNewDTO, boolean rup) {
		this.idOrganizzazione = valoriNewDTO.getIdOrganizzazione();
		this.fase = valoriNewDTO.getFase();
		this.idComuneTipoProcedimento = valoriNewDTO.getIdComuneTipoProcedimento();
		this.denominazioneComuneProcedimento = valoriNewDTO.getDenominazioneComuneProcedimento();
		this.tipoAssegnamento = valoriNewDTO.getTipoAssegnamento();
		this.rup = rup;
		if (rup==true) {
			this.usernameUtente = valoriNewDTO.getUsernameRup();
			this.denominazioneUtente = valoriNewDTO.getDenominazioneRup();
		}
		else {
			this.usernameUtente = valoriNewDTO.getUsernameFunzionario();
			this.denominazioneUtente = valoriNewDTO.getDenominazioneFunzionario();
		}
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
	public int getIdComuneTipoProcedimento() {
		return idComuneTipoProcedimento;
	}
	public void setIdComuneTipoProcedimento(int idComuneTipoProcedimento) {
		this.idComuneTipoProcedimento = idComuneTipoProcedimento;
	}
	public boolean isRup() {
		return rup;
	}
	public void setRup(boolean rup) {
		this.rup = rup;
	}
	public String getUsernameUtente() {
		return usernameUtente;
	}
	public void setUsernameUtente(String usernameUtente) {
		this.usernameUtente = usernameUtente;
	}
	public String getDenominazioneUtente() {
		return denominazioneUtente;
	}
	public void setDenominazioneUtente(String denominazioneUtente) {
		this.denominazioneUtente = denominazioneUtente;
	}
	public String getDenominazioneComuneProcedimento() {
		return denominazioneComuneProcedimento;
	}
	public void setDenominazioneComuneProcedimento(String denominazioneComuneProcedimento) {
		this.denominazioneComuneProcedimento = denominazioneComuneProcedimento;
	}
	public TipoAssegnamento getTipoAssegnamento() {
		return tipoAssegnamento;
	}
	public void setTipoAssegnamento(TipoAssegnamento tipoAssegnamento) {
		this.tipoAssegnamento = tipoAssegnamento;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((denominazioneComuneProcedimento == null) ? 0 : denominazioneComuneProcedimento.hashCode());
		result = prime * result + ((denominazioneUtente == null) ? 0 : denominazioneUtente.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + idComuneTipoProcedimento;
		result = prime * result + idOrganizzazione;
		result = prime * result + (rup ? 1231 : 1237);
		result = prime * result + ((tipoAssegnamento == null) ? 0 : tipoAssegnamento.hashCode());
		result = prime * result + ((usernameUtente == null) ? 0 : usernameUtente.hashCode());
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
		ValoriAssegnamentoOldDTO other = (ValoriAssegnamentoOldDTO) obj;
		if (denominazioneComuneProcedimento == null) {
			if (other.denominazioneComuneProcedimento != null)
				return false;
		} else if (!denominazioneComuneProcedimento.equals(other.denominazioneComuneProcedimento))
			return false;
		if (denominazioneUtente == null) {
			if (other.denominazioneUtente != null)
				return false;
		} else if (!denominazioneUtente.equals(other.denominazioneUtente))
			return false;
		if (fase == null) {
			if (other.fase != null)
				return false;
		} else if (!fase.equals(other.fase))
			return false;
		if (idComuneTipoProcedimento != other.idComuneTipoProcedimento)
			return false;
		if (idOrganizzazione != other.idOrganizzazione)
			return false;
		if (rup != other.rup)
			return false;
		if (tipoAssegnamento != other.tipoAssegnamento)
			return false;
		if (usernameUtente == null) {
			if (other.usernameUtente != null)
				return false;
		} else if (!usernameUtente.equals(other.usernameUtente))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ValoriAssegnamentoDTO [idOrganizzazione=" + idOrganizzazione + ", fase=" + fase
				+ ", idComuneTipoProcedimento=" + idComuneTipoProcedimento + ", denominazioneComuneProcedimento="
				+ denominazioneComuneProcedimento + ", tipoAssegnamento=" + tipoAssegnamento + ", rup=" + rup
				+ ", usernameUtente=" + usernameUtente + ", denominazioneUtente=" + denominazioneUtente + "]";
	}
	
}