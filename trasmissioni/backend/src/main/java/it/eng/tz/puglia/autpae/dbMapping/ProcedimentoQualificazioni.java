package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum ProcedimentoQualificazioni implements _Interfaccia_dbMapping {
	
	codice_tipo_procedimento,
	id_tipi_qualificazioni,
	date_start,
	date_end,
	escluso_eti;

	@Override
	public String tableName()
	{
		return "procedimento_qualificazioni";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ProcedimentoQualificazioni> ids()
	{
		List<ProcedimentoQualificazioni> pk = new ArrayList<ProcedimentoQualificazioni>();
		pk.add(ProcedimentoQualificazioni.codice_tipo_procedimento);
		pk.add(ProcedimentoQualificazioni.id_tipi_qualificazioni);
		pk.add(ProcedimentoQualificazioni.date_start);
		return pk;
	}
	
	public static String getTableName()
	{
		return "procedimento_qualificazioni";
	}
	
}
