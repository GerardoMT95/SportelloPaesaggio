package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.Date;

import it.eng.tz.puglia.autpae.enumeratori.CampionamentoSuccessivo;

public class CampionamentoDTO implements Serializable {

	private static final long serialVersionUID = -8148181367837858747L;
	
	private Long id;
	private boolean attivo;
	private short intervalloCamp;
	private CampionamentoSuccessivo tipoSuccessivo;
	private short percentuale;
	private String notificaCamp;
	private String notificaVer;
	private short  intervalloVer;
	private Boolean esitoPubb;
	private boolean customized;				// TODO: credo si possa riuscire ad eliminarlo
	private Date dataInizio;
	private Date dataCampionatura;
	private Long fascicoliRegistrati;		// non fa parte della Tabella (serve per info sul campionamento)
	private String protocollo;				// non fa parte della Tabella (serve per risolvere i PlaceHolders)
	private Date dataProtocollo;				// non fa parte della Tabella (serve per risolvere i PlaceHolders)
	
	
	public Date getDataProtocollo() {
		return dataProtocollo;
	}


	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}


	public CampionamentoDTO() { }
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isAttivo() {
		return attivo;
	}
	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	public short getIntervalloCamp() {
		return intervalloCamp;
	}
	public void setIntervalloCamp(short intervalloCamp) {
		this.intervalloCamp = intervalloCamp;
	}
	public CampionamentoSuccessivo getTipoSuccessivo() {
		return tipoSuccessivo;
	}
	public void setTipoSuccessivo(CampionamentoSuccessivo tipoSuccessivo) {
		this.tipoSuccessivo = tipoSuccessivo;
	}
	public short getPercentuale() {
		return percentuale;
	}
	public void setPercentuale(short percentuale) {
		this.percentuale = percentuale;
	}
	public String getNotificaCamp() {
		return notificaCamp;
	}
	public void setNotificaCamp(String notificaCamp) {
		this.notificaCamp = notificaCamp;
	}
	public String getNotificaVer() {
		return notificaVer;
	}
	public void setNotificaVer(String notificaVer) {
		this.notificaVer = notificaVer;
	}
	public short getIntervalloVer() {
		return intervalloVer;
	}
	public void setIntervalloVer(short intervalloVer) {
		this.intervalloVer = intervalloVer;
	}
	public Boolean getEsitoPubb() {
		return esitoPubb;
	}
	public boolean isEsitoPubb() {
		return esitoPubb;
	}
	public void setEsitoPubb(Boolean esitoPubb) {
		this.esitoPubb = esitoPubb;
	}
	public boolean isCustomized() {
		return customized;
	}
	public void setCustomized(boolean customized) {
		this.customized = customized;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataCampionatura() {
		return dataCampionatura;
	}
	public void setDataCampionatura(Date dataCampionatura) {
		this.dataCampionatura = dataCampionatura;
	}
	public Long getFascicoliRegistrati() {
		return fascicoliRegistrati;
	}
	public void setFascicoliRegistrati(Long fascicoliRegistrati) {
		this.fascicoliRegistrati = fascicoliRegistrati;
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
		result = prime * result + (attivo ? 1231 : 1237);
		result = prime * result + (customized ? 1231 : 1237);
		result = prime * result + ((dataCampionatura == null) ? 0 : dataCampionatura.hashCode());
		result = prime * result + ((dataInizio == null) ? 0 : dataInizio.hashCode());
		result = prime * result + ((dataProtocollo == null) ? 0 : dataProtocollo.hashCode());
		result = prime * result + ((esitoPubb == null) ? 0 : esitoPubb.hashCode());
		result = prime * result + ((fascicoliRegistrati == null) ? 0 : fascicoliRegistrati.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + intervalloCamp;
		result = prime * result + intervalloVer;
		result = prime * result + ((notificaCamp == null) ? 0 : notificaCamp.hashCode());
		result = prime * result + ((notificaVer == null) ? 0 : notificaVer.hashCode());
		result = prime * result + percentuale;
		result = prime * result + ((protocollo == null) ? 0 : protocollo.hashCode());
		result = prime * result + ((tipoSuccessivo == null) ? 0 : tipoSuccessivo.hashCode());
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
		CampionamentoDTO other = (CampionamentoDTO) obj;
		if (attivo != other.attivo)
			return false;
		if (customized != other.customized)
			return false;
		if (dataCampionatura == null) {
			if (other.dataCampionatura != null)
				return false;
		} else if (!dataCampionatura.equals(other.dataCampionatura))
			return false;
		if (dataInizio == null) {
			if (other.dataInizio != null)
				return false;
		} else if (!dataInizio.equals(other.dataInizio))
			return false;
		if (dataProtocollo == null) {
			if (other.dataProtocollo != null)
				return false;
		} else if (!dataProtocollo.equals(other.dataProtocollo))
			return false;
		if (esitoPubb == null) {
			if (other.esitoPubb != null)
				return false;
		} else if (!esitoPubb.equals(other.esitoPubb))
			return false;
		if (fascicoliRegistrati == null) {
			if (other.fascicoliRegistrati != null)
				return false;
		} else if (!fascicoliRegistrati.equals(other.fascicoliRegistrati))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (intervalloCamp != other.intervalloCamp)
			return false;
		if (intervalloVer != other.intervalloVer)
			return false;
		if (notificaCamp == null) {
			if (other.notificaCamp != null)
				return false;
		} else if (!notificaCamp.equals(other.notificaCamp))
			return false;
		if (notificaVer == null) {
			if (other.notificaVer != null)
				return false;
		} else if (!notificaVer.equals(other.notificaVer))
			return false;
		if (percentuale != other.percentuale)
			return false;
		if (protocollo == null) {
			if (other.protocollo != null)
				return false;
		} else if (!protocollo.equals(other.protocollo))
			return false;
		if (tipoSuccessivo != other.tipoSuccessivo)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "CampionamentoDTO [id=" + id + ", attivo=" + attivo + ", intervalloCamp=" + intervalloCamp
				+ ", tipoSuccessivo=" + tipoSuccessivo + ", percentuale=" + percentuale + ", notificaCamp="
				+ notificaCamp + ", notificaVer=" + notificaVer + ", intervalloVer=" + intervalloVer + ", esitoPubb="
				+ esitoPubb + ", customized=" + customized + ", dataInizio=" + dataInizio + ", dataCampionatura="
				+ dataCampionatura + ", fascicoliRegistrati=" + fascicoliRegistrati + ", protocollo=" + protocollo
				+ ", dataProtocollo=" + dataProtocollo + "]";
	}
	
}