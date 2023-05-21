/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
public interface IDataSourceCiviliaConstants {

	/************* CIV **************************/
	/**
	 * sPRING DATASOURCE BEAN FOR oracle civ
	 */
	String ORACLE_CIV_DS_SPRING_BEAN_NAME = "CIVDATSOURCE";
	
	/**
	 * Name in spring context for jdbc template linked to oracle civ
	 */
	String ORACLE_CIV_TEMPLATE_SPRING_BEAN_NAME = "oracleCivTemplate";
	
	/**
	 * Name in spring context for transaction linked to oracle civ
	 */
	String ORACLE_CIV_TRANSACTION_SPRING_BEAN_NAME = "oracleCivTransaction";
	
	/**
	 * Name in spring context for named jdbc template
	 */
	String ORACLE_CIV_NAMEDPARAMETERJDBCTEMPLATE_SPRING_BEAN_NAME = "namedCIVTEMPLATE";
	
}
