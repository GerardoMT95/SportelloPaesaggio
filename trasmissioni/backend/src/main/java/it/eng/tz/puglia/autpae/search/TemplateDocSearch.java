package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.TemplateDoc;
import it.eng.tz.puglia.autpae.dbMapping.TemplateEmail;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.template_email
 */
public class TemplateDocSearch extends WhereClauseGenerator<TemplateDoc> {

	private static final long serialVersionUID = 6693642738L;
	
	private String descrizione;
	private String nome;

	
	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public TemplateDocSearch() { }


		@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(StringUtil.isNotEmpty(nome))
		{
			sql.append(separator)
			   .append(TemplateDoc.nome.getCompleteName())
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
	}
	
}
