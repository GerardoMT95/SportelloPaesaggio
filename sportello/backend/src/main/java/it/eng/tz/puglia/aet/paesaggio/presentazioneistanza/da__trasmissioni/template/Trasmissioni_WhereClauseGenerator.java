package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

abstract public class Trasmissioni_WhereClauseGenerator<M extends Trasmissioni_Interfaccia_dbMapping> extends Trasmissioni_OrderByGenerator<M>
{
	private static final long serialVersionUID = -6493259923274863681L;
	
	protected StringBuilder sql;
	protected Map<String, Object> parameters;
	protected abstract void generateWhereCriteria();
	
	public StringBuilder getSqlWhereClause(StringBuilder baseSql)
	{
		sql = new StringBuilder();
		parameters = new HashMap<String, Object>();
		generateWhereCriteria();
		return baseSql.append(sql);
	}
	
	public Map<String, Object> getSqlParameters()
	{
		return parameters;
	}
	
	public MapSqlParameterSource getMapSqlParamiters()
	{
	   return new MapSqlParameterSource(parameters);
	}
	
}