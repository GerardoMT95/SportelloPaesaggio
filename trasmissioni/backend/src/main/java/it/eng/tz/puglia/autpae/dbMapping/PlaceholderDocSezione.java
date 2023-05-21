package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.List;

public enum PlaceholderDocSezione implements _Interfaccia_dbMapping
{
	placeholder_codice,
	template_doc_sezione_nome,
	template_doc_nome;
	
	
	@Override
	public String tableName()
	{
		return "placeholder_doc_sezione";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<PlaceholderDocSezione> ids()
	{
		return List.of(PlaceholderDocSezione.placeholder_codice,PlaceholderDocSezione.template_doc_nome,PlaceholderDocSezione.template_doc_sezione_nome);
	}
	
	public static String getTableName()
	{
		return "placeholder_doc_sezione";
	}
}
