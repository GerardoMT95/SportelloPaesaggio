package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum ParticelleCatastali implements _Interfaccia_dbMapping
{
	pratica_id,
	comune_id,
	id,
	livello,
	sezione,
	foglio,
	particella,
	sub,
	cod_cat,
	oid,
	note,
	descr_sezione;
	
	
	@Override
	public String tableName()
	{
		return "particelle_catastali";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ParticelleCatastali> ids()
	{
		List<ParticelleCatastali> pk = new ArrayList<>();
		pk.add(ParticelleCatastali.comune_id);
		pk.add(ParticelleCatastali.pratica_id);
		pk.add(ParticelleCatastali.id);
		return pk;
	}
	
	public static String getTableName()
	{
		return "particelle_catastali";
	}
	
}