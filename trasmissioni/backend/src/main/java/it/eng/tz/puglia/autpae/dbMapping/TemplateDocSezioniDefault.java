package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.List;

public enum TemplateDocSezioniDefault implements _Interfaccia_dbMapping
{
	valore,
	id_allegato,
	tipo_sezione,
	template_doc_nome,
	nome;
	
	@Override
	public String tableName()
	{
		return "template_doc_sezioni_default";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TemplateDocSezioniDefault> ids()
	{
		return List.of(TemplateDocSezioniDefault.template_doc_nome,TemplateDocSezioniDefault.nome);
	}
	
	public static String getTableName()
	{
		return "template_doc_sezioni_default";
	}
}
