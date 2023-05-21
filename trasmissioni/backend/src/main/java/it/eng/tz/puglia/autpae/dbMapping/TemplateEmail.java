package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum TemplateEmail implements _Interfaccia_dbMapping
{
	codice,
	oggetto,
	testo,
	descrizione,
	nome;
	
	@Override
	public String tableName()
	{
		return "template_email";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TemplateEmail> ids()
	{
		return Collections.singletonList(TemplateEmail.codice);
	}
	
	public static String getTableName()
	{
		return "template_email";
	}
}
