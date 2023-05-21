package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.io.Serializable;
import java.util.Date;

import it.eng.tz.puglia.util.string.StringUtil;


public class RichiedenteDTO implements Serializable {

	private static final long serialVersionUID = 7162211932348119352L;
	
	private Long id;
	private Long idFascicolo;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String sesso;
	private Date dataNascita;
	private String statoNascita;
	private String provinciaNascita;
	private String comuneNascita;
	private String statoResidenza;
	private String provinciaResidenza;
	private String comuneResidenza;
	private String viaResidenza;
	private String numeroResidenza;
	private String cap;
	private String pec;
	private String email;
	private String telefono;
	private boolean ditta = false;
	private String dittaSocieta;
	private String dittaInQualitaDi;
	private String dittaQualitaAltro;
	private String dittaCodiceFiscale;
	private String dittaPartitaIva;
	private ResponsabileDTO responsabile;

	
	public RichiedenteDTO() { }
	
	public boolean isAditta() {
		return ((StringUtil.isNotEmpty(getDittaSocieta	  	())) ||
				(StringUtil.isNotEmpty(getDittaInQualitaDi  ())) ||
				(StringUtil.isNotEmpty(getDittaCodiceFiscale())) ||
				(StringUtil.isNotEmpty(getDittaPartitaIva	())) );
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getStatoNascita() {
		return statoNascita;
	}

	public void setStatoNascita(String statoNascita) {
		this.statoNascita = statoNascita;
	}

	public String getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getStatoResidenza() {
		return statoResidenza;
	}

	public void setStatoResidenza(String statoResidenza) {
		this.statoResidenza = statoResidenza;
	}

	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}

	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}

	public String getNumeroResidenza() {
		return numeroResidenza;
	}

	public void setNumeroResidenza(String numeroResidenza) {
		this.numeroResidenza = numeroResidenza;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isDitta() {
		return ditta;
	}

	public void setDitta(boolean ditta) {
		this.ditta = ditta;
	}

	public String getDittaSocieta() {
		return dittaSocieta;
	}

	public void setDittaSocieta(String dittaSocieta) {
		this.dittaSocieta = dittaSocieta;
	}

	public String getDittaInQualitaDi() {
		return dittaInQualitaDi;
	}

	public void setDittaInQualitaDi(String dittaInQualitaDi) {
		this.dittaInQualitaDi = dittaInQualitaDi;
	}

	public String getDittaCodiceFiscale() {
		return dittaCodiceFiscale;
	}

	public void setDittaCodiceFiscale(String dittaCodiceFiscale) {
		this.dittaCodiceFiscale = dittaCodiceFiscale;
	}

	public String getDittaPartitaIva() {
		return dittaPartitaIva;
	}

	public void setDittaPartitaIva(String dittaPartitaIva) {
		this.dittaPartitaIva = dittaPartitaIva;
	}

	public String getDittaQualitaAltro() {
		return dittaQualitaAltro;
	}

	public void setDittaQualitaAltro(String dittaQualitaAltro) {
		this.dittaQualitaAltro = dittaQualitaAltro;
	}

	public ResponsabileDTO getResponsabile() {
		return responsabile;
	}

	public void setResponsabile(ResponsabileDTO responsabile) {
		this.responsabile = responsabile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cap == null) ? 0 : cap.hashCode());
		result = prime * result + ((codiceFiscale == null) ? 0 : codiceFiscale.hashCode());
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((comuneNascita == null) ? 0 : comuneNascita.hashCode());
		result = prime * result + ((comuneResidenza == null) ? 0 : comuneResidenza.hashCode());
		result = prime * result + ((dataNascita == null) ? 0 : dataNascita.hashCode());
		result = prime * result + (ditta ? 1231 : 1237);
		result = prime * result + ((dittaCodiceFiscale == null) ? 0 : dittaCodiceFiscale.hashCode());
		result = prime * result + ((dittaInQualitaDi == null) ? 0 : dittaInQualitaDi.hashCode());
		result = prime * result + ((dittaPartitaIva == null) ? 0 : dittaPartitaIva.hashCode());
		result = prime * result + ((dittaQualitaAltro == null) ? 0 : dittaQualitaAltro.hashCode());
		result = prime * result + ((dittaSocieta == null) ? 0 : dittaSocieta.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((numeroResidenza == null) ? 0 : numeroResidenza.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
		result = prime * result + ((provinciaNascita == null) ? 0 : provinciaNascita.hashCode());
		result = prime * result + ((provinciaResidenza == null) ? 0 : provinciaResidenza.hashCode());
		result = prime * result + ((responsabile == null) ? 0 : responsabile.hashCode());
		result = prime * result + ((sesso == null) ? 0 : sesso.hashCode());
		result = prime * result + ((statoNascita == null) ? 0 : statoNascita.hashCode());
		result = prime * result + ((statoResidenza == null) ? 0 : statoResidenza.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((viaResidenza == null) ? 0 : viaResidenza.hashCode());
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
		RichiedenteDTO other = (RichiedenteDTO) obj;
		if (cap == null) {
			if (other.cap != null)
				return false;
		} else if (!cap.equals(other.cap))
			return false;
		if (codiceFiscale == null) {
			if (other.codiceFiscale != null)
				return false;
		} else if (!codiceFiscale.equals(other.codiceFiscale))
			return false;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (comuneNascita == null) {
			if (other.comuneNascita != null)
				return false;
		} else if (!comuneNascita.equals(other.comuneNascita))
			return false;
		if (comuneResidenza == null) {
			if (other.comuneResidenza != null)
				return false;
		} else if (!comuneResidenza.equals(other.comuneResidenza))
			return false;
		if (dataNascita == null) {
			if (other.dataNascita != null)
				return false;
		} else if (!dataNascita.equals(other.dataNascita))
			return false;
		if (ditta != other.ditta)
			return false;
		if (dittaCodiceFiscale == null) {
			if (other.dittaCodiceFiscale != null)
				return false;
		} else if (!dittaCodiceFiscale.equals(other.dittaCodiceFiscale))
			return false;
		if (dittaInQualitaDi == null) {
			if (other.dittaInQualitaDi != null)
				return false;
		} else if (!dittaInQualitaDi.equals(other.dittaInQualitaDi))
			return false;
		if (dittaPartitaIva == null) {
			if (other.dittaPartitaIva != null)
				return false;
		} else if (!dittaPartitaIva.equals(other.dittaPartitaIva))
			return false;
		if (dittaQualitaAltro == null) {
			if (other.dittaQualitaAltro != null)
				return false;
		} else if (!dittaQualitaAltro.equals(other.dittaQualitaAltro))
			return false;
		if (dittaSocieta == null) {
			if (other.dittaSocieta != null)
				return false;
		} else if (!dittaSocieta.equals(other.dittaSocieta))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (numeroResidenza == null) {
			if (other.numeroResidenza != null)
				return false;
		} else if (!numeroResidenza.equals(other.numeroResidenza))
			return false;
		if (pec == null) {
			if (other.pec != null)
				return false;
		} else if (!pec.equals(other.pec))
			return false;
		if (provinciaNascita == null) {
			if (other.provinciaNascita != null)
				return false;
		} else if (!provinciaNascita.equals(other.provinciaNascita))
			return false;
		if (provinciaResidenza == null) {
			if (other.provinciaResidenza != null)
				return false;
		} else if (!provinciaResidenza.equals(other.provinciaResidenza))
			return false;
		if (responsabile == null) {
			if (other.responsabile != null)
				return false;
		} else if (!responsabile.equals(other.responsabile))
			return false;
		if (sesso == null) {
			if (other.sesso != null)
				return false;
		} else if (!sesso.equals(other.sesso))
			return false;
		if (statoNascita == null) {
			if (other.statoNascita != null)
				return false;
		} else if (!statoNascita.equals(other.statoNascita))
			return false;
		if (statoResidenza == null) {
			if (other.statoResidenza != null)
				return false;
		} else if (!statoResidenza.equals(other.statoResidenza))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (viaResidenza == null) {
			if (other.viaResidenza != null)
				return false;
		} else if (!viaResidenza.equals(other.viaResidenza))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RichiedenteDTO [id=" + id + ", idFascicolo=" + idFascicolo + ", nome=" + nome + ", cognome=" + cognome
				+ ", codiceFiscale=" + codiceFiscale + ", sesso=" + sesso + ", dataNascita=" + dataNascita
				+ ", statoNascita=" + statoNascita + ", provinciaNascita=" + provinciaNascita + ", comuneNascita="
				+ comuneNascita + ", statoResidenza=" + statoResidenza + ", provinciaResidenza=" + provinciaResidenza
				+ ", comuneResidenza=" + comuneResidenza + ", viaResidenza=" + viaResidenza + ", numeroResidenza="
				+ numeroResidenza + ", cap=" + cap + ", pec=" + pec + ", email=" + email + ", telefono=" + telefono
				+ ", ditta=" + ditta + ", dittaSocieta=" + dittaSocieta + ", dittaInQualitaDi=" + dittaInQualitaDi
				+ ", dittaQualitaAltro=" + dittaQualitaAltro + ", dittaCodiceFiscale=" + dittaCodiceFiscale
				+ ", dittaPartitaIva=" + dittaPartitaIva + ", responsabile=" + responsabile + "]";
	}

}