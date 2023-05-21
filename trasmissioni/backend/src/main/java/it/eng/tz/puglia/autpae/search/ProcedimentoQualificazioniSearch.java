package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import it.eng.tz.puglia.autpae.dbMapping.ProcedimentoQualificazioni;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.procedimento_qualificazione
 */
public class ProcedimentoQualificazioniSearch extends WhereClauseGenerator<ProcedimentoQualificazioni> {

	private static final long serialVersionUID = 6693642738L;
	
	private String codiceTipoProcedimento;
	private Long idTipiQualificazioni;
	private Date dataRiferimento=new Date(); //per default è riferito ad oggi.
	private Boolean esclusoEti;
	
	
	public Date getDataRiferimento() {
		return dataRiferimento;
	}

	public void setDataRiferimento(Date dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}

	public Boolean getEsclusoEti() {
		return esclusoEti;
	}

	public void setEsclusoEti(Boolean esclusoEti) {
		this.esclusoEti = esclusoEti;
	}

	public ProcedimentoQualificazioniSearch() { }

	public ProcedimentoQualificazioniSearch(String codiceTipoProcedimento, Long idTipiQualificazioni) {
		this.codiceTipoProcedimento = codiceTipoProcedimento;
		this.idTipiQualificazioni = idTipiQualificazioni;
	}
	
	
	public String getCodiceTipoProcedimento() {
		return codiceTipoProcedimento;
	}
	public void setCodiceTipoProcedimento(String codiceTipoProcedimento) {
		this.codiceTipoProcedimento = codiceTipoProcedimento;
	}
	public Long getIdTipiQualificazioni() {
		return idTipiQualificazioni;
	}
	public void setIdTipiQualificazioni(Long idTipiQualificazioni) {
		this.idTipiQualificazioni = idTipiQualificazioni;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codiceTipoProcedimento == null) ? 0 : codiceTipoProcedimento.hashCode());
		result = prime * result + ((dataRiferimento == null) ? 0 : dataRiferimento.hashCode());
		result = prime * result + ((esclusoEti == null) ? 0 : esclusoEti.hashCode());
		result = prime * result + ((idTipiQualificazioni == null) ? 0 : idTipiQualificazioni.hashCode());
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
		ProcedimentoQualificazioniSearch other = (ProcedimentoQualificazioniSearch) obj;
		if (codiceTipoProcedimento == null) {
			if (other.codiceTipoProcedimento != null)
				return false;
		} else if (!codiceTipoProcedimento.equals(other.codiceTipoProcedimento))
			return false;
		if (dataRiferimento == null) {
			if (other.dataRiferimento != null)
				return false;
		} else if (!dataRiferimento.equals(other.dataRiferimento))
			return false;
		if (esclusoEti == null) {
			if (other.esclusoEti != null)
				return false;
		} else if (!esclusoEti.equals(other.esclusoEti))
			return false;
		if (idTipiQualificazioni == null) {
			if (other.idTipiQualificazioni != null)
				return false;
		} else if (!idTipiQualificazioni.equals(other.idTipiQualificazioni))
			return false;
		return true;
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(StringUtil.isNotEmpty(codiceTipoProcedimento))
		{
			sql.append(separator)
			   .append(ProcedimentoQualificazioni.codice_tipo_procedimento.getCompleteName())
			   .append(" = :")
			   .append(ProcedimentoQualificazioni.codice_tipo_procedimento.columnName());
			parameters.put(ProcedimentoQualificazioni.codice_tipo_procedimento.columnName(), codiceTipoProcedimento);
			separator = " and ";
		}
		if(idTipiQualificazioni != null)
		{
			sql.append(separator)
			   .append(ProcedimentoQualificazioni.id_tipi_qualificazioni.getCompleteName())
			   .append(" = :")
			   .append(ProcedimentoQualificazioni.id_tipi_qualificazioni.columnName());
			parameters.put(ProcedimentoQualificazioni.id_tipi_qualificazioni.columnName(), idTipiQualificazioni);
			separator = " and ";
		}
		if(dataRiferimento != null)
		{
			sql.append(separator)
			   .append(ProcedimentoQualificazioni.date_start.getCompleteName())
			   .append(" <= :")
			   .append(ProcedimentoQualificazioni.date_start.columnName());
			parameters.put(ProcedimentoQualificazioni.date_start.columnName(), 
					java.sql.Timestamp.from(this.getDataRiferimento().toInstant()));
			separator = " and ";
			sql.append(separator)
			   .append(" ( " + ProcedimentoQualificazioni.date_end.getCompleteName() +" is null OR ")
			   .append(ProcedimentoQualificazioni.date_end.getCompleteName())
			   .append(" >= :")
			   .append(ProcedimentoQualificazioni.date_end.columnName())
			   .append(" )");
			
			parameters.put(ProcedimentoQualificazioni.date_end.columnName(), 
					java.sql.Timestamp.from(this.getDataRiferimento().toInstant()));
			separator = " and ";
		}
		if(esclusoEti!=null && esclusoEti==true) {
			sql.append(separator)
			   .append(ProcedimentoQualificazioni.escluso_eti.getCompleteName())
			   .append(" != true ");
			separator = " and ";
		}
	}

}
