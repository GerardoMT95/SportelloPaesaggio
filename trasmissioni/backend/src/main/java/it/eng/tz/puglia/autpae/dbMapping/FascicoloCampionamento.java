package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum FascicoloCampionamento implements _Interfaccia_dbMapping {
	
	id_campionamento,
	id_fascicolo;

	@Override
	public String tableName()
	{
		return "fascicolo_campionamento";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<FascicoloCampionamento> ids()
	{
		List<FascicoloCampionamento> pk = new ArrayList<FascicoloCampionamento>();
		pk.add(FascicoloCampionamento.id_fascicolo);
		pk.add(FascicoloCampionamento.id_campionamento);
		return pk;
	}
	
	public static String getTableName()
	{
		return "fascicolo_campionamento";
	}

}
