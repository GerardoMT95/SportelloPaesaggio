package it.eng.tz.puglia.aet.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Table Entity
 * @author Antonio La Gatta
 * @date 24 ott 2018 
 */
public class Table {

	private String schema;
	private String tableName;
	private List<Column> colonne = new ArrayList<>();
	public Table(String schema, String tableName) {
		super();
		this.schema = schema;
		this.tableName = tableName;
	}
	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @return the colonne
	 */
	public List<Column> getColonne() {
		return colonne;
	}
	

}
