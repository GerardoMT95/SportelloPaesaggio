package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import it.eng.tz.puglia.autpae.dbMapping.Richiedente;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.autpae.utility.DateUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.richiedente
 */
public class RichiedenteSearch extends WhereClauseGenerator<Richiedente> {

	private static final long serialVersionUID = -7421420464211427221L;
	
	private Long id;
	private Long idFascicolo;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String sesso;
	private Date dataNascita;
	private Date dataNascitaDa;
	private Date dataNascitaA;
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
	private String dittaSocieta;
	private String dittaInQualitaDi;
	private String dittaQualitaAltro;
	private String dittaCodiceFiscale;
	private String dittaPartitaIva;
	

	public RichiedenteSearch() { }
	
	
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

	public Date getDataNascitaDa() {
		return dataNascitaDa;
	}

	public void setDataNascitaDa(Date dataNascitaDa) {
		this.dataNascitaDa = dataNascitaDa;
	}

	public Date getDataNascitaA() {
		return dataNascitaA;
	}

	public void setDataNascitaA(Date dataNascitaA) {
		this.dataNascitaA = dataNascitaA;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cap == null) ? 0 : cap.hashCode());
		result = prime * result + ((codiceFiscale == null) ? 0 : codiceFiscale.hashCode());
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((comuneNascita == null) ? 0 : comuneNascita.hashCode());
		result = prime * result + ((comuneResidenza == null) ? 0 : comuneResidenza.hashCode());
		result = prime * result + ((dataNascita == null) ? 0 : dataNascita.hashCode());
		result = prime * result + ((dataNascitaA == null) ? 0 : dataNascitaA.hashCode());
		result = prime * result + ((dataNascitaDa == null) ? 0 : dataNascitaDa.hashCode());
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
		result = prime * result + ((sesso == null) ? 0 : sesso.hashCode());
		result = prime * result + ((statoNascita == null) ? 0 : statoNascita.hashCode());
		result = prime * result + ((statoResidenza == null) ? 0 : statoResidenza.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((viaResidenza == null) ? 0 : viaResidenza.hashCode());
		return result;
	}


	@Override
	public String toString() {
		return "RichiedenteSearch [id=" + id + ", idFascicolo=" + idFascicolo + ", nome=" + nome + ", cognome="
				+ cognome + ", codiceFiscale=" + codiceFiscale + ", sesso=" + sesso + ", dataNascita=" + dataNascita
				+ ", dataNascitaDa=" + dataNascitaDa + ", dataNascitaA=" + dataNascitaA + ", statoNascita="
				+ statoNascita + ", provinciaNascita=" + provinciaNascita + ", comuneNascita=" + comuneNascita
				+ ", statoResidenza=" + statoResidenza + ", provinciaResidenza=" + provinciaResidenza
				+ ", comuneResidenza=" + comuneResidenza + ", viaResidenza=" + viaResidenza + ", numeroResidenza="
				+ numeroResidenza + ", cap=" + cap + ", pec=" + pec + ", email=" + email + ", telefono=" + telefono
				+ ", dittaSocieta=" + dittaSocieta + ", dittaInQualitaDi=" + dittaInQualitaDi + ", dittaCodiceFiscale="
				+ dittaCodiceFiscale + ", dittaPartitaIva=" + dittaPartitaIva + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RichiedenteSearch other = (RichiedenteSearch) obj;
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
		if (dataNascitaA == null) {
			if (other.dataNascitaA != null)
				return false;
		} else if (!dataNascitaA.equals(other.dataNascitaA))
			return false;
		if (dataNascitaDa == null) {
			if (other.dataNascitaDa != null)
				return false;
		} else if (!dataNascitaDa.equals(other.dataNascitaDa))
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
	protected void generateWhereCriteria() {
		String separatore = " where ";
		if(id != null)
		{
			sql.append(separatore)
			   .append(Richiedente.id.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.id.columnName());
			parameters.put(Richiedente.id.columnName(), id);
			separatore = " and ";
		}
		if(idFascicolo != null)
		{
			sql.append(separatore)
			   .append(Richiedente.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.id_fascicolo.columnName());
			parameters.put(Richiedente.id_fascicolo.columnName(), idFascicolo);
			separatore = " and ";
		}
		if(nome != null)
		{
			sql.append(separatore)
			   .append("lower(")
			   .append(Richiedente.nome.getCompleteName())
			   .append("::text) like :")
			   .append(Richiedente.nome.columnName());
			parameters.put(Richiedente.nome.columnName(), StringUtil.convertRightLike(nome.toLowerCase()));
			separatore = " and ";
		}
		if(cognome != null)
		{
			sql.append(separatore)
			   .append("lower(")
			   .append(Richiedente.cognome.getCompleteName())
			   .append("::text) like :")
			   .append(Richiedente.cognome.columnName());
			parameters.put(Richiedente.cognome.columnName(), StringUtil.convertRightLike(cognome.toLowerCase()));
			separatore = " and ";
		}
		if(codiceFiscale != null)
		{
			sql.append(separatore)
			   .append(Richiedente.codice_fiscale.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.codice_fiscale.columnName());
			parameters.put(Richiedente.codice_fiscale.columnName(), codiceFiscale);
			separatore = " and ";
		}
		if(sesso != null)
		{
			sql.append(separatore)
			   .append(Richiedente.sesso.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.sesso.columnName());
			parameters.put(Richiedente.sesso.columnName(), sesso);
			separatore = " and ";
		}
		if(dataNascita != null)
		{
			sql.append(separatore)
			   .append(Richiedente.data_nascita.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.data_nascita.columnName());
			parameters.put(Richiedente.data_nascita.columnName(), dataNascita);
			separatore = " and ";
		}
		if(dataNascitaDa != null && dataNascitaA != null)	
		{
			sql.append(separatore)
				.append(Richiedente.data_nascita.getCompleteName())
				.append(" BETWEEN :")
				.append(Richiedente.data_nascita.columnName())
				.append("_DA")
				.append(" AND :")
				.append(Richiedente.data_nascita.columnName())
				.append("_A");
			parameters.put(Richiedente.data_nascita.columnName() + "_DA", dataNascitaDa);
			parameters.put(Richiedente.data_nascita.columnName() + "_A" , DateUtil.endOfDay(dataNascitaA));
			separatore = " and ";
		}
		else if(dataNascitaDa != null)	
		{
			sql.append(separatore)
				.append(Richiedente.data_nascita.getCompleteName())
				.append(" >= :")
				.append(Richiedente.data_nascita.columnName());
			parameters.put(Richiedente.data_nascita.columnName(), dataNascitaDa);
			separatore = " and ";
		}
		else if(dataNascitaA != null)
		{
			sql.append(separatore)
				.append(Richiedente.data_nascita.getCompleteName())
				.append(" <= :")
				.append(Richiedente.data_nascita.columnName());
			parameters.put(Richiedente.data_nascita.columnName(), DateUtil.endOfDay(dataNascitaA));
			separatore = " and ";
		}
		if(statoNascita != null)
		{
			sql.append(separatore)
			   .append(Richiedente.stato_nascita.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.stato_nascita.columnName());
			parameters.put(Richiedente.stato_nascita.columnName(), statoNascita);
			separatore = " and ";
		}
		if(provinciaNascita != null)
		{
			sql.append(separatore)
			   .append(Richiedente.provincia_nascita.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.provincia_nascita.columnName());
			parameters.put(Richiedente.provincia_nascita.columnName(), provinciaNascita);
			separatore = " and ";
		}
		if(comuneNascita != null)
		{
			sql.append(separatore)
			   .append(Richiedente.comune_nascita.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.comune_nascita.columnName());
			parameters.put(Richiedente.comune_nascita.columnName(), comuneNascita);
			separatore = " and ";
		}
		if(statoResidenza != null)
		{
			sql.append(separatore)
			   .append(Richiedente.stato_residenza.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.stato_residenza.columnName());
			parameters.put(Richiedente.stato_residenza.columnName(), statoResidenza);
			separatore = " and ";
		}
		if(provinciaResidenza != null)
		{
			sql.append(separatore)
			   .append(Richiedente.provincia_residenza.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.provincia_residenza.columnName());
			parameters.put(Richiedente.provincia_residenza.columnName(), provinciaResidenza);
			separatore = " and ";
		}
		if(comuneResidenza != null)
		{
			sql.append(separatore)
			   .append(Richiedente.comune_residenza.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.comune_residenza.columnName());
			parameters.put(Richiedente.comune_residenza.columnName(), comuneResidenza);
			separatore = " and ";
		}
		if(viaResidenza != null)
		{
			sql.append(separatore)
			   .append("lower(")
			   .append(Richiedente.via_residenza.getCompleteName())
			   .append("::text) like :")
			   .append(Richiedente.via_residenza.columnName());
			parameters.put(Richiedente.via_residenza.columnName(), StringUtil.convertRightLike(viaResidenza.toLowerCase()));
			separatore = " and ";
		}
		if(numeroResidenza != null)
		{
			sql.append(separatore)
			   .append(Richiedente.numero_residenza.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.numero_residenza.columnName());
			parameters.put(Richiedente.numero_residenza.columnName(), numeroResidenza);
			separatore = " and ";
		}
		if(cap != null)
		{
			sql.append(separatore)
			   .append(Richiedente.cap.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.cap.columnName());
			parameters.put(Richiedente.cap.columnName(), cap);
			separatore = " and ";
		}
		if(pec != null)
		{
			sql.append(separatore)
			   .append(Richiedente.pec.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.pec.columnName());
			parameters.put(Richiedente.pec.columnName(), pec);
			separatore = " and ";
		}
		if(email != null)
		{
			sql.append(separatore)
			   .append(Richiedente.email.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.email.columnName());
			parameters.put(Richiedente.email.columnName(), email);
			separatore = " and ";
		}
		if(telefono != null)
		{
			sql.append(separatore)
			   .append(Richiedente.telefono.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.telefono.columnName());
			parameters.put(Richiedente.telefono.columnName(), telefono);
			separatore = " and ";
		}
		if(dittaSocieta != null)
		{
			sql.append(separatore)
			   .append("lower(")
			   .append(Richiedente.ditta_societa.getCompleteName())
			   .append("::text) like :")
			   .append(Richiedente.ditta_societa.columnName());
			parameters.put(Richiedente.ditta_societa.columnName(), StringUtil.convertRightLike(dittaSocieta.toLowerCase()));
			separatore = " and ";
		}
		if(dittaInQualitaDi != null)
		{
			sql.append(separatore)
			   .append("lower(")
			   .append(Richiedente.ditta_in_qualita_di.getCompleteName())
			   .append("::text) like :")
			   .append(Richiedente.ditta_in_qualita_di.columnName());
			parameters.put(Richiedente.ditta_in_qualita_di.columnName(), StringUtil.convertRightLike(dittaInQualitaDi.toLowerCase()));
			separatore = " and ";
		}
		if(dittaCodiceFiscale != null)
		{
			sql.append(separatore)
			   .append(Richiedente.ditta_codice_fiscale.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.ditta_codice_fiscale.columnName());
			parameters.put(Richiedente.ditta_codice_fiscale.columnName(), dittaCodiceFiscale);
			separatore = " and ";
		}
		if(dittaPartitaIva != null)
		{
			sql.append(separatore)
			   .append(Richiedente.ditta_partita_iva.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.ditta_partita_iva.columnName());
			parameters.put(Richiedente.ditta_partita_iva.columnName(), dittaPartitaIva);
			separatore = " and ";
		}
		if(dittaQualitaAltro != null)
		{
			sql.append(separatore)
			   .append(Richiedente.ditta_qualita_altro.getCompleteName())
			   .append(" = :")
			   .append(Richiedente.ditta_qualita_altro.columnName());
			parameters.put(Richiedente.ditta_qualita_altro.columnName(), dittaQualitaAltro);
			separatore = " and ";
		}
	}

}