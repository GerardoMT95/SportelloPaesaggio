package it.eng.tz.puglia.autpae.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.dbMapping.AllegatoEnte;
import it.eng.tz.puglia.autpae.dbMapping.AllegatoFascicolo;
import it.eng.tz.puglia.autpae.dbMapping.Destinatario;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.autpae.utility.DateUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for Tab 'Ulteriore Documentazione'
 */
public class UlterioreDocumentazioneSearch extends WhereClauseGenerator<Allegato> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long 	idFascicolo;
	private String  titolo;
	private String  descrizione;
	private String  inseritoDa;
	private Date 	dataCondivisioneDa;
	private Date 	dataCondivisioneA;
	private String  destinatario;	   					  // d_ notifica
	private List<String>  visibileA = new ArrayList<>();  // enti
	
	
	public UlterioreDocumentazioneSearch() { }

	
	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getInseritoDa() {
		return inseritoDa;
	}

	public void setInseritoDa(String inseritoDa) {
		this.inseritoDa = inseritoDa;
	}

	public Date getDataCondivisioneDa() {
		return dataCondivisioneDa;
	}

	public void setDataCondivisioneDa(Date dataCondivisioneDa) {
		this.dataCondivisioneDa = dataCondivisioneDa;
	}

	public Date getDataCondivisioneA() {
		return dataCondivisioneA;
	}

	public void setDataCondivisioneA(Date dataCondivisioneA) {
		this.dataCondivisioneA = dataCondivisioneA;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public List<String> getVisibileA() {
		return visibileA;
	}

	public void setVisibileA(List<String> visibileA) {
		this.visibileA = visibileA;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dataCondivisioneA == null) ? 0 : dataCondivisioneA.hashCode());
		result = prime * result + ((dataCondivisioneDa == null) ? 0 : dataCondivisioneDa.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((destinatario == null) ? 0 : destinatario.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((inseritoDa == null) ? 0 : inseritoDa.hashCode());
		result = prime * result + ((titolo == null) ? 0 : titolo.hashCode());
		result = prime * result + ((visibileA == null) ? 0 : visibileA.hashCode());
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
		UlterioreDocumentazioneSearch other = (UlterioreDocumentazioneSearch) obj;
		if (dataCondivisioneA == null) {
			if (other.dataCondivisioneA != null)
				return false;
		} else if (!dataCondivisioneA.equals(other.dataCondivisioneA))
			return false;
		if (dataCondivisioneDa == null) {
			if (other.dataCondivisioneDa != null)
				return false;
		} else if (!dataCondivisioneDa.equals(other.dataCondivisioneDa))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (inseritoDa == null) {
			if (other.inseritoDa != null)
				return false;
		} else if (!inseritoDa.equals(other.inseritoDa))
			return false;
		if (titolo == null) {
			if (other.titolo != null)
				return false;
		} else if (!titolo.equals(other.titolo))
			return false;
		if (visibileA == null) {
			if (other.visibileA != null)
				return false;
		} else if (!visibileA.equals(other.visibileA))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UlterioreDocumentazioneSearch [idFascicolo=" + idFascicolo + ", titolo=" + titolo + ", descrizione="
				+ descrizione + ", inseritoDa=" + inseritoDa + ", dataCondivisioneDa=" + dataCondivisioneDa
				+ ", dataCondivisioneA=" + dataCondivisioneA + ", destinatario=" + destinatario + ", visibileA="
				+ visibileA + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " and ";
		
		if(idFascicolo!=null)
		{
			sql.append(separator)
			   .append(AllegatoFascicolo.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(AllegatoFascicolo.id_fascicolo.columnName());
			parameters.put(AllegatoFascicolo.id_fascicolo.columnName(), idFascicolo);
		}
		if(StringUtil.isNotEmpty(titolo))
		{
			sql.append(separator)
			   .append(Allegato.titolo.getCompleteName())
			   .append(" ILIKE :")
			   .append(Allegato.titolo.columnName());
			parameters.put(Allegato.titolo.columnName(), StringUtil.convertLike(titolo));
		}
		if(StringUtil.isNotEmpty(descrizione))
		{
			sql.append(separator)
			   .append(Allegato.descrizione.getCompleteName())
			   .append(" ILIKE :")
			   .append(Allegato.descrizione.columnName());
			parameters.put(Allegato.descrizione.columnName(), StringUtil.convertLike(descrizione));
		}
		if(StringUtil.isNotEmpty(inseritoDa))
		{
			sql.append(separator)
			   .append(Allegato.utente_inserimento.getCompleteName())
			   .append(" ILIKE :")
			   .append(Allegato.utente_inserimento.columnName());
			parameters.put(Allegato.utente_inserimento.columnName(), StringUtil.convertLike(inseritoDa));
		}
		if(dataCondivisioneDa != null && dataCondivisioneA != null)	
		{
			sql.append(separator)
				.append(Allegato.data_caricamento.getCompleteName())
				.append(" BETWEEN :")
				.append(Allegato.data_caricamento.columnName())
				.append("_DA")
				.append(" AND :")
				.append(Allegato.data_caricamento.columnName())
				.append("_A");
			parameters.put(Allegato.data_caricamento.columnName() + "_DA", dataCondivisioneDa);
			parameters.put(Allegato.data_caricamento.columnName() + "_A" , DateUtil.endOfDay(dataCondivisioneA));
		}
		else if(dataCondivisioneDa != null)	
		{
			sql.append(separator)
				.append(Allegato.data_caricamento.getCompleteName())
				.append(" >= :")
				.append(Allegato.data_caricamento.columnName());
			parameters.put(Allegato.data_caricamento.columnName(), dataCondivisioneDa);
		}
		else if(dataCondivisioneA != null)
		{
			sql.append(separator)
				.append(Allegato.data_caricamento.getCompleteName())
				.append(" <= :")
				.append(Allegato.data_caricamento.columnName());
			parameters.put(Allegato.data_caricamento.columnName(), DateUtil.endOfDay(dataCondivisioneA));
		}
		
		if(StringUtil.isNotEmpty(destinatario))
		{
			sql.append(separator)
			   .append(Destinatario.email.getCompleteName())
			   .append(" ILIKE :")
			   .append(Destinatario.email.columnName());
			parameters.put(Destinatario.email.columnName(), StringUtil.convertLike(destinatario));
		}
		
		if(visibileA!=null && !visibileA.isEmpty())
		{
			sql.append(separator)
			   .append(AllegatoEnte.codice.getCompleteName())
			   .append(" IN (:")
			   .append(AllegatoEnte.codice.columnName())
			   .append(" ) ");
			parameters.put(AllegatoEnte.codice.columnName(), visibileA);
		}
		
	}
	
}
