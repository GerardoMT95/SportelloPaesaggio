package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.Date;

public class LineaTemporaleDTO implements Serializable
{
	private static final long serialVersionUID = 6985990040232694483L;
	
	private Date dataCreazione;
	private Date dataTrasmissione;
	private Date dataCampionamento;
	private Date dataVerifica;
	private String richiedenteNome;
	private String richiedenteCognome;
	private String ente;
	private String tipoProcedimento;
	private String protocollo;
	
	
	public LineaTemporaleDTO() { }

	
	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataTrasmissione() {
		return dataTrasmissione;
	}

	public void setDataTrasmissione(Date dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}

	public Date getDataCampionamento() {
		return dataCampionamento;
	}

	public void setDataCampionamento(Date dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}

	public Date getDataVerifica() {
		return dataVerifica;
	}

	public void setDataVerifica(Date dataVerifica) {
		this.dataVerifica = dataVerifica;
	}

	public String getRichiedenteNome() {
		return richiedenteNome;
	}

	public void setRichiedenteNome(String richiedenteNome) {
		this.richiedenteNome = richiedenteNome;
	}

	public String getRichiedenteCognome() {
		return richiedenteCognome;
	}

	public void setRichiedenteCognome(String richiedenteCognome) {
		this.richiedenteCognome = richiedenteCognome;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getTipoProcedimento() {
		return tipoProcedimento;
	}

	public void setTipoProcedimento(String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCampionamento == null) ? 0 : dataCampionamento.hashCode());
		result = prime * result + ((dataCreazione == null) ? 0 : dataCreazione.hashCode());
		result = prime * result + ((dataTrasmissione == null) ? 0 : dataTrasmissione.hashCode());
		result = prime * result + ((dataVerifica == null) ? 0 : dataVerifica.hashCode());
		result = prime * result + ((ente == null) ? 0 : ente.hashCode());
		result = prime * result + ((protocollo == null) ? 0 : protocollo.hashCode());
		result = prime * result + ((richiedenteCognome == null) ? 0 : richiedenteCognome.hashCode());
		result = prime * result + ((richiedenteNome == null) ? 0 : richiedenteNome.hashCode());
		result = prime * result + ((tipoProcedimento == null) ? 0 : tipoProcedimento.hashCode());
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
		LineaTemporaleDTO other = (LineaTemporaleDTO) obj;
		if (dataCampionamento == null) {
			if (other.dataCampionamento != null)
				return false;
		} else if (!dataCampionamento.equals(other.dataCampionamento))
			return false;
		if (dataCreazione == null) {
			if (other.dataCreazione != null)
				return false;
		} else if (!dataCreazione.equals(other.dataCreazione))
			return false;
		if (dataTrasmissione == null) {
			if (other.dataTrasmissione != null)
				return false;
		} else if (!dataTrasmissione.equals(other.dataTrasmissione))
			return false;
		if (dataVerifica == null) {
			if (other.dataVerifica != null)
				return false;
		} else if (!dataVerifica.equals(other.dataVerifica))
			return false;
		if (ente == null) {
			if (other.ente != null)
				return false;
		} else if (!ente.equals(other.ente))
			return false;
		if (protocollo == null) {
			if (other.protocollo != null)
				return false;
		} else if (!protocollo.equals(other.protocollo))
			return false;
		if (richiedenteCognome == null) {
			if (other.richiedenteCognome != null)
				return false;
		} else if (!richiedenteCognome.equals(other.richiedenteCognome))
			return false;
		if (richiedenteNome == null) {
			if (other.richiedenteNome != null)
				return false;
		} else if (!richiedenteNome.equals(other.richiedenteNome))
			return false;
		if (tipoProcedimento == null) {
			if (other.tipoProcedimento != null)
				return false;
		} else if (!tipoProcedimento.equals(other.tipoProcedimento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LineaTemporaleDTO [dataCreazione=" + dataCreazione + ", dataTrasmissione=" + dataTrasmissione
				+ ", dataCampionamento=" + dataCampionamento + ", dataVerifica=" + dataVerifica + ", richiedenteNome="
				+ richiedenteNome + ", richiedenteCognome=" + richiedenteCognome + ", ente=" + ente
				+ ", tipoProcedimento=" + tipoProcedimento + ", protocollo=" + protocollo + "]";
	}

}
