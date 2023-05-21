package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum TemplateDestinatario implements _Interfaccia_dbMapping
{
	id,
	codice_template,
	email,
	pec,
	denominazione,
	tipo;
	
	
	@Override
	public String tableName()
	{
		return "template_destinatario";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TemplateDestinatario> ids()
	{
		return Collections.singletonList(TemplateDestinatario.id);
	}
	
	public static String getTableName()
	{
		return "template_destinatario";
	}
}
