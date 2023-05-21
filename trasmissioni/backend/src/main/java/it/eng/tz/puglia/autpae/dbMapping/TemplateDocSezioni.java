package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.List;

public enum TemplateDocSezioni implements _Interfaccia_dbMapping
{
	valore,
	id_allegato,
	tipo_sezione,
	template_doc_nome,
	nome;
	
	@Override
	public String tableName()
	{
		return "template_doc_sezioni";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TemplateDocSezioni> ids()
	{
		return List.of(TemplateDocSezioni.template_doc_nome,TemplateDocSezioni.nome);
	}
	
	public static String getTableName()
	{
		return "template_doc_sezioni";
	}
}
