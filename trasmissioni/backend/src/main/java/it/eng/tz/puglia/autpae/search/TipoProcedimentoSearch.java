package it.eng.tz.puglia.autpae.search;

import java.sql.Date;

import it.eng.tz.puglia.autpae.dbMapping.TipoProcedimento;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.tipo_procedimento
 */
public class TipoProcedimentoSearch extends WhereClauseGenerator<TipoProcedimento> {

	private static final long serialVersionUID = 6693642738L;
	
	private String codice;
	private String descrizione;
	private Boolean inviaMail;
	private Boolean sanatoria;
	//private Date inizioValidita;
	//private Date fineValidita;
	private String applicativo;
	
	public TipoProcedimentoSearch() { }
	
	public TipoProcedimentoSearch(String codice, String descrizione, Boolean inviaMail, Boolean sanatoria) {
		this.codice = codice;
		this.descrizione = descrizione;
		this.inviaMail = inviaMail;
		this.sanatoria = sanatoria;
	}

	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Boolean getInviaMail() {
		return inviaMail;
	}
	public void setInviaMail(Boolean inviaMail) {
		this.inviaMail = inviaMail;
	}
	public Boolean getSanatoria() {
		return sanatoria;
	}
	public void setSanatoria(Boolean sanatoria) {
		this.sanatoria = sanatoria;
	}

//	public Date getInizioValidita() {
//		return inizioValidita;
//	}
//
//	public void setInizioValidita(Date inizioValidita) {
//		this.inizioValidita = inizioValidita;
//	}
//
//	public Date getFineValidita() {
//		return fineValidita;
//	}
//
//	public void setFineValidita(Date fineValidita) {
//		this.fineValidita = fineValidita;
//	}

	public String getApplicativo() {
		return applicativo;
	}

	public void setApplicativo(String applicativo) {
		this.applicativo = applicativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((inviaMail == null) ? 0 : inviaMail.hashCode());
		result = prime * result + ((sanatoria == null) ? 0 : sanatoria.hashCode());
		//result = prime * result + ((inizioValidita == null) ? 0 : inizioValidita.hashCode());
		//result = prime * result + ((fineValidita == null) ? 0 : fineValidita.hashCode());
		result = prime * result + ((applicativo == null) ? 0 : applicativo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoProcedimentoSearch other = (TipoProcedimentoSearch) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (inviaMail == null) {
			if (other.inviaMail != null)
				return false;
		} else if (!inviaMail.equals(other.inviaMail))
			return false;
		if (sanatoria == null) {
			if (other.sanatoria != null)
				return false;
		} else if (!sanatoria.equals(other.sanatoria))
			return false;
		if (applicativo == null) {
			if (other.applicativo != null)
				return false;
		} else if (!applicativo.equals(other.applicativo))
			return false;
//		if (inizioValidita == null) {
//			if (other.inizioValidita != null)
//				return false;
//		} else if (!inizioValidita.equals(other.inizioValidita))
//			return false;
//		if (fineValidita == null) {
//			if (other.fineValidita != null)
//				return false;
//		} else if (!fineValidita.equals(other.fineValidita))
//			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TipoProcedimentoSearch [codice=" + codice + ", descrizione=" + descrizione + ", inviaMail=" + inviaMail
				+ ", sanatoria=" + sanatoria 
				//+ ", inizioValidita=" + inizioValidita + ", fineValidita=" + fineValidita
				+ ", applicativo=" + applicativo + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(StringUtil.isNotEmpty(codice))
		{
			sql.append(separator)
			   .append(TipoProcedimento.codice.getCompleteName())
			   .append(" = :")
			   .append(TipoProcedimento.codice.columnName());
			parameters.put(TipoProcedimento.codice.columnName(), codice);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(descrizione))
		{
			sql.append(separator)
			   .append(TipoProcedimento.descrizione.getCompleteName())
			   .append(" = :")
			   .append(TipoProcedimento.descrizione.columnName());
			parameters.put(TipoProcedimento.descrizione.columnName(), descrizione);
			separator = " and ";
		}
		if(inviaMail!=null)
		{
			sql.append(separator)
			   .append(TipoProcedimento.invia_email.getCompleteName())
			   .append(" = :")
			   .append(TipoProcedimento.invia_email.columnName());
			parameters.put(TipoProcedimento.invia_email.columnName(), inviaMail);
			separator = " and ";
		}
		if(sanatoria!=null)
		{
			sql.append(separator)
			   .append(TipoProcedimento.sanatoria.getCompleteName())
			   .append(" = :")
			   .append(TipoProcedimento.sanatoria.columnName());
			parameters.put(TipoProcedimento.sanatoria.columnName(), sanatoria);
			separator = " and ";
		}
		if(applicativo!=null)
		{
			sql.append(separator)
			.append(TipoProcedimento.applicativo.getCompleteName())
			.append(" = :")
			.append(TipoProcedimento.applicativo.columnName());
			parameters.put(TipoProcedimento.applicativo.columnName(), applicativo);
			separator = " and ";
		}
	}
}
