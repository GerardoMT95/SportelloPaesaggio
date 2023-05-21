package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum FascicoloIntervento implements _Interfaccia_dbMapping {
	
	id_fascicolo,
	id_tipi_qualificazioni;

	@Override
	public String tableName()
	{
		return "fascicolo_intervento";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<FascicoloIntervento> ids()
	{
		List<FascicoloIntervento> pk = new ArrayList<FascicoloIntervento>();
		pk.add(FascicoloIntervento.id_fascicolo);
		pk.add(FascicoloIntervento.id_tipi_qualificazioni);
		return pk;
	}
	
	public static String getTableName()
	{
		return "fascicolo_intervento";
	}
	
}
