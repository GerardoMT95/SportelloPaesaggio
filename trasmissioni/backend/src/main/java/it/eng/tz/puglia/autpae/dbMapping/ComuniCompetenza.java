package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum ComuniCompetenza implements _Interfaccia_dbMapping
{
	pratica_id,
	ente_id,
	data_inserimento,
	descrizione,
	cod_cat,
	cod_istat,
	creazione,
	effettivo;
	
	
	@Override
	public String tableName()
	{
		return "comuni_competenza";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ComuniCompetenza> ids()
	{
		List<ComuniCompetenza> pk = new ArrayList<>();
		pk.add(ComuniCompetenza.pratica_id);
		pk.add(ComuniCompetenza.ente_id);
		return pk;
	}
	
	public static String getTableName()
	{
		return "comuni_competenza";
	}
	
}