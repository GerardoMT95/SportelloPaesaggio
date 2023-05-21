package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;

public interface _Interfaccia_dbMapping
{
	
	/**
	 * Restituisce il nome della tabella + "." + il valore assegnato a nomeColonna nell'enumeratore
	 * Se il nome tabella ha un . viene sostituito con  \".\"
	 *
	 * @return Stringa contenente il valore assegnato
	 */
	default String getCompleteName()
	{
		return "\"" + tableName().replace(".","\".\"") + "\".\"" + columnName() + "\"";
	}

	/**
	 * Restituisce il valore assegnato all'enumeratore con anteposto il valore "nometabella_enumeratore"
	 *
	 * @return Stringa contenente il valore assegnato
	 */
	default String getAlias()
	{
		return "\"" + tableName() + "_" + columnName() + "\"";
	}

	/**
	 * Restituisce il valore assegnato all'enumeratore con anteposto il valore nometabella_enumeratore, da usare quando si hanno le viste
	 *
	 * @return Stringa contenente il valore assegnato
	 */
	default String getAliasName()
	{
		return tableName() + "_" + columnName();
	}
	
	/**
	 * Restituisce le colonne (nel caso siano più di uno) contrassegnate come chiavi dell'entità
	 *
	 * @return Collezioni dei nomi delle colonne che formano la chiave primaria
	 */
	<T extends _Interfaccia_dbMapping> Collection<T> ids();
	
	/**
	 * Restituisce il nome della tabella di riferimento
	 * 
	 * @return Stringa contenente il nome della tabella
	 * 
	 */
	String tableName();
	
	/**
	 * Restituisce il valore assegnato a nomeColonna nell'enumeratore
	 *
	 * @return Stringa contenente il valore assegnato
	 */
	String columnName();
	
//	default String schemaName() {		return "";	};
//	default String fullTableName() {return schemaName().isEmpty()?tableName():schemaName()+"."+tableName();};
	
}
