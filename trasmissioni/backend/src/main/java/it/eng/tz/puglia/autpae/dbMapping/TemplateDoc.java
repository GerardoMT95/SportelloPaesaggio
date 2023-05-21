package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum TemplateDoc implements _Interfaccia_dbMapping
{
	descrizione,
	nome;
	
	@Override
	public String tableName()
	{
		return "template_doc";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TemplateDoc> ids()
	{
		return Collections.singletonList(TemplateDoc.nome);
	}
	
	public static String getTableName()
	{
		return "template_doc";
	}
}
