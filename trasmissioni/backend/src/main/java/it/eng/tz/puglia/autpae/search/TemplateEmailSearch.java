package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.TemplateEmail;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.template_email
 */
public class TemplateEmailSearch extends WhereClauseGenerator<TemplateEmail> {

	private static final long serialVersionUID = 6693642738L;
	
	private TipoTemplate codice;
	private String oggetto;
	private String testo;
	private String descrizione;
	private String nome;

	public TemplateEmailSearch() { }


	public TipoTemplate getCodice() {
		return codice;
	}
	public void setCodice(TipoTemplate codice) {
		this.codice = codice;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((oggetto == null) ? 0 : oggetto.hashCode());
		result = prime * result + ((testo == null) ? 0 : testo.hashCode());
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
		TemplateEmailSearch other = (TemplateEmailSearch) obj;
		if (codice != other.codice)
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (oggetto == null) {
			if (other.oggetto != null)
				return false;
		} else if (!oggetto.equals(other.oggetto))
			return false;
		if (testo == null) {
			if (other.testo != null)
				return false;
		} else if (!testo.equals(other.testo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TemplateEmailSearch [codice=" + codice + ", oggetto=" + oggetto + ", testo=" + testo + ", descrizione="
				+ descrizione + ", nome=" + nome + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(codice!=null)
		{
			sql.append(separator)
			   .append(TemplateEmail.codice.getCompleteName())
			   .append(" = :")
			   .append(TemplateEmail.codice.columnName());
			parameters.put(TemplateEmail.codice.columnName(), codice.name());
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(nome))
		{
			sql.append(separator)
			   .append(TemplateEmail.nome.getCompleteName())
			   .append(" = :")
			   .append(TemplateEmail.nome.columnName());
			parameters.put(TemplateEmail.nome.columnName(), nome);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(descrizione))
		{
			sql.append(separator)
			   .append(TemplateEmail.descrizione.getCompleteName())
			   .append(" = :")
			   .append(TemplateEmail.descrizione.columnName());
			parameters.put(TemplateEmail.descrizione.columnName(), descrizione);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(oggetto))
		{
			sql.append(separator)
			   .append(TemplateEmail.oggetto.getCompleteName())
			   .append(" = :")
			   .append(TemplateEmail.oggetto.columnName());
			parameters.put(TemplateEmail.oggetto.columnName(), oggetto);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(testo))
		{
			sql.append(separator)
			   .append(TemplateEmail.testo.getCompleteName())
			   .append(" = :")
			   .append(TemplateEmail.testo.columnName());
			parameters.put(TemplateEmail.testo.columnName(), testo);
			separator = " and ";
		}
	}
	
}
