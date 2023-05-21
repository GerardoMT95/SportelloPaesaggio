package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.enumeratori.TipoAssegnamento;

/**
 * DTO for table valori_assegnamento
 */
public class ValoriAssegnamentoDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private int idOrganizzazione;
	private String fase;
	private String idComuneTipoProcedimento;		// idComune = campo "id" della tabella ENTE per i "CO"
	private String denominazioneComuneProcedimento; // non c'è sul DB
	private TipoAssegnamento tipoAssegnamento;		// non c'è sul DB
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	
	
	public ValoriAssegnamentoDTO() { }
	
	public ValoriAssegnamentoDTO(TipoAssegnamento tipoAssegnamento, String idComuneTipoProcedimento, String denominazioneComuneProcedimento) { 
		this.tipoAssegnamento = tipoAssegnamento;
		this.idComuneTipoProcedimento = idComuneTipoProcedimento;
		this.denominazioneComuneProcedimento = denominazioneComuneProcedimento;
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
	public String getIdComuneTipoProcedimento() {
		return idComuneTipoProcedimento;
	}
	public void setIdComuneTipoProcedimento(String idComuneTipoProcedimento) {
		this.idComuneTipoProcedimento = idComuneTipoProcedimento;
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
		result = prime * result + ((denominazioneFunzionario == null) ? 0 : denominazioneFunzionario.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((idComuneTipoProcedimento == null) ? 0 : idComuneTipoProcedimento.hashCode());
		result = prime * result + idOrganizzazione;
		result = prime * result + ((tipoAssegnamento == null) ? 0 : tipoAssegnamento.hashCode());
		result = prime * result + ((usernameFunzionario == null) ? 0 : usernameFunzionario.hashCode());
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
		ValoriAssegnamentoDTO other = (ValoriAssegnamentoDTO) obj;
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
		if (fase == null) {
			if (other.fase != null)
				return false;
		} else if (!fase.equals(other.fase))
			return false;
		if (idComuneTipoProcedimento == null) {
			if (other.idComuneTipoProcedimento != null)
				return false;
		} else if (!idComuneTipoProcedimento.equals(other.idComuneTipoProcedimento))
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
		return true;
	}
	
	@Override
	public String toString() {
		return "ValoriAssegnamentoDTO [idOrganizzazione=" + idOrganizzazione + ", fase=" + fase
				+ ", idComuneTipoProcedimento=" + idComuneTipoProcedimento + ", denominazioneComuneProcedimento="
				+ denominazioneComuneProcedimento + ", tipoAssegnamento=" + tipoAssegnamento + ", usernameFunzionario="
				+ usernameFunzionario + ", denominazioneFunzionario=" + denominazioneFunzionario + "]";
	}
	
}