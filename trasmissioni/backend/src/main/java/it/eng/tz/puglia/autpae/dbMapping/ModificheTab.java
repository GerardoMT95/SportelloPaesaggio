package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum ModificheTab implements _Interfaccia_dbMapping {
	
	hashcode,
	tab,
	info;

	
	@Override
	public String tableName()
	{
		return "modifiche_tab";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ModificheTab> ids()
	{
		List<ModificheTab> pk = new ArrayList<ModificheTab>();
		pk.add(ModificheTab.hashcode);
		pk.add(ModificheTab.tab);
		return pk;
	}
	
	public static String getTableName()
	{
		return "modifiche_tab";
	}

}