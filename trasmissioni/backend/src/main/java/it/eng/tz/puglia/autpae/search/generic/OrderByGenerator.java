package it.eng.tz.puglia.autpae.search.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;
import it.eng.tz.puglia.util.string.StringUtil;

abstract class OrderByGenerator<M extends _Interfaccia_dbMapping> extends Paging
{
	private static final long serialVersionUID = -9165730955177129412L;
	protected M colonna;
	protected Direction direzione;
	protected StringBuilder orderBySql;

	public OrderByGenerator()
	{
		super();
		direzione = Direction.ASC;
		colonna = null;
	}
	public OrderByGenerator(Direction direction, M column)
	{
		super();
		this.direzione = direction;
		this.colonna = column;
	}
	public OrderByGenerator(String direction, M column)
	{
		this(Direction.fromString(direction), column);
	}
	
	public M getColonna()
	{
		return colonna;
	}
	public void setColonna(M colonna)
	{
		this.colonna = colonna;
	}

	public Direction getDirezione()
	{
		return direzione;
	}
	public void setDirezione(Direction direzione)
	{
		this.direzione = direzione;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((colonna == null) ? 0 : colonna.hashCode());
		result = prime * result + ((direzione == null) ? 0 : direzione.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		OrderByGenerator<M> other = (OrderByGenerator<M>) obj;
		if (colonna == null)
		{
			if (other.colonna != null)
				return false;
		} else if (!colonna.equals(other.colonna))
			return false;
		if (direzione != other.direzione)
			return false;
		return true;
	}

	public List<String> getOrderByFields() {
		List<String> ret=new ArrayList<>();
		if(this.colonna != null)
		{
			ret.add(colonna.getCompleteName());
			if(this.colonna.ids()!=null && this.colonna.ids().size()>0) {
				this.colonna.ids().stream()
				.map(_Interfaccia_dbMapping::getCompleteName)
				.filter(col->!this.colonna.getCompleteName().equals(col))
				.forEach(ret::add);	
			}
		}	
		return ret;
	}
	
	public StringBuilder getSqlOrderByClause(StringBuilder baseSql)
	{
		orderBySql = new StringBuilder();
		if(this.colonna != null)
		{
			orderBySql.append(" ORDER BY ")
			  		  .append(colonna.getCompleteName())
			  		  .append(" ")
			  		  .append(direzione.name());
			@SuppressWarnings("unchecked")
			List<M> collectionId = (List<M>) colonna.ids().parallelStream().filter(p -> !p.columnName().equals(this.colonna.columnName())).collect(Collectors.toList());
			if(collectionId != null && !collectionId.isEmpty())
			{
				String ids = colonna.ids().parallelStream().map(_Interfaccia_dbMapping::getCompleteName).collect(Collectors.joining(","));
				if(StringUtil.isNotEmpty(ids))
				{
					orderBySql.append(",")
							  .append(ids);
				}
			}
		}
		return baseSql.append(orderBySql);
	}
	
	public enum Direction
	{
		ASC,
		DESC;

		public static Direction fromString(String s)
		{
			if(s.equalsIgnoreCase("desc"))
				return DESC;
			return ASC;
		}
	}
	
}
