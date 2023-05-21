package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.io.Serializable;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoAssegnamento;

/**
 * DTO for table valori_assegnamento
 */
public class ValoriAssegnamentoNewDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private int idOrganizzazione;
	private String fase;
	private int idComuneTipoProcedimento;			// idComune = campo "id" della tabella ENTE per i "CO"
	private String denominazioneComuneProcedimento; // non c'è sul DB
	private TipoAssegnamento tipoAssegnamento;		// non c'è sul DB
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	private String usernameRup;
	private String denominazioneRup;
	
	
	public ValoriAssegnamentoNewDTO() { }
	
	public ValoriAssegnamentoNewDTO(TipoAssegnamento tipoAssegnamento, int idComuneTipoProcedimento, String denominazioneComuneProcedimento) { 
		this.tipoAssegnamento = tipoAssegnamento;
		this.idComuneTipoProcedimento = idComuneTipoProcedimento;
		this.denominazioneComuneProcedimento = denominazioneComuneProcedimento;
	}
	
	public ValoriAssegnamentoNewDTO(ValoriAssegnamentoOldDTO old_1) {
		this.idOrganizzazione = old_1.getIdOrganizzazione();
		this.fase = old_1.getFase();
		this.idComuneTipoProcedimento = old_1.getIdComuneTipoProcedimento();
		this.denominazioneComuneProcedimento = old_1.getDenominazioneComuneProcedimento();
		this.tipoAssegnamento = old_1.getTipoAssegnamento();
		this.usernameFunzionario = 		old_1.getUsernameUtente();
		this.denominazioneFunzionario = old_1.getDenominazioneUtente();
	}

	public ValoriAssegnamentoNewDTO(ValoriAssegnamentoOldDTO old_1, ValoriAssegnamentoOldDTO old_2) {
		this.idOrganizzazione = old_1.getIdOrganizzazione();
		this.fase = old_1.getFase();
		this.idComuneTipoProcedimento = old_1.getIdComuneTipoProcedimento();
		this.denominazioneComuneProcedimento = old_1.getDenominazioneComuneProcedimento();
		this.tipoAssegnamento = old_1.getTipoAssegnamento();
		this.usernameFunzionario = 		old_1.isRup()==false ? old_1.getUsernameUtente() 	  : old_2.getUsernameUtente();
		this.denominazioneFunzionario = old_1.isRup()==false ? old_1.getDenominazioneUtente() : old_2.getDenominazioneUtente();
		this.usernameRup = 				old_1.isRup()==true  ? old_1.getUsernameUtente() 	  : old_2.getUsernameUtente();
		this.denominazioneRup = 		old_1.isRup()==true  ? old_1.getDenominazioneUtente() : old_2.getDenominazioneUtente();
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
	public String getUsernameFunzionario() {
		return usernameFunzionario;
	}
	public void setUsernameFunzionario(String usernameFunzionario) {
		this.usernameFunzionario = usernameFunzionario;
	}
	public String getDenominazioneFunzionario() {
		return denominazioneFunzionario;
	}
	public void setDenominazioneFunzionario(String denominazioneFunzionario) {
		this.denominazioneFunzionario = denominazioneFunzionario;
	}
	public String getUsernameRup() {
		return usernameRup;
	}
	public void setUsernameRup(String usernameRup) {
		this.usernameRup = usernameRup;
	}
	public String getDenominazioneRup() {
		return denominazioneRup;
	}
	public void setDenominazioneRup(String denominazioneRup) {
		this.denominazioneRup = denominazioneRup;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((denominazioneComuneProcedimento == null) ? 0 : denominazioneComuneProcedimento.hashCode());
		result = prime * result + ((denominazioneFunzionario == null) ? 0 : denominazioneFunzionario.hashCode());
		result = prime * result + ((denominazioneRup == null) ? 0 : denominazioneRup.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + idComuneTipoProcedimento;
		result = prime * result + idOrganizzazione;
		result = prime * result + ((tipoAssegnamento == null) ? 0 : tipoAssegnamento.hashCode());
		result = prime * result + ((usernameFunzionario == null) ? 0 : usernameFunzionario.hashCode());
		result = prime * result + ((usernameRup == null) ? 0 : usernameRup.hashCode());
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
		ValoriAssegnamentoNewDTO other = (ValoriAssegnamentoNewDTO) obj;
		if (denominazioneComuneProcedimento == null) {
			if (other.denominazioneComuneProcedimento != null)
				return false;
		} else if (!denominazioneComuneProcedimento.equals(other.denominazioneComuneProcedimento))
			return false;
		if (denominazioneFunzionario == null) {
			if (other.denominazioneFunzionario != null)
				return false;
		} else if (!denominazioneFunzionario.equals(other.denominazioneFunzionario))
			return false;
		if (denominazioneRup == null) {
			if (other.denominazioneRup != null)
				return false;
		} else if (!denominazioneRup.equals(other.denominazioneRup))
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
		if (tipoAssegnamento != other.tipoAssegnamento)
			return false;
		if (usernameFunzionario == null) {
			if (other.usernameFunzionario != null)
				return false;
		} else if (!usernameFunzionario.equals(other.usernameFunzionario))
			return false;
		if (usernameRup == null) {
			if (other.usernameRup != null)
				return false;
		} else if (!usernameRup.equals(other.usernameRup))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ValoriAssegnamentoNewDTO [idOrganizzazione=" + idOrganizzazione + ", fase=" + fase
				+ ", idComuneTipoProcedimento=" + idComuneTipoProcedimento + ", denominazioneComuneProcedimento="
				+ denominazioneComuneProcedimento + ", tipoAssegnamento=" + tipoAssegnamento + ", usernameFunzionario="
				+ usernameFunzionario + ", denominazioneFunzionario=" + denominazioneFunzionario + ", usernameRup="
				+ usernameRup + ", denominazioneRup=" + denominazioneRup + "]";
	}
	
}