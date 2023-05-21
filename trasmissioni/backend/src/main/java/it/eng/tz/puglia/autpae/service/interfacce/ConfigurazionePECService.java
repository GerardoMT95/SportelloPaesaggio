package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.dto.ConfigurazionePECBean;

public interface ConfigurazionePECService
{
//	/**
//	 * Popola la tabella "configurazione_pec", se questa è vuota, usando la configurazione di 
//	 * default presente nel progetto
//	 * 
//	 * @return true se inserisce il default, altrimenti false
//	 * @throws Exception
//	 */
//	boolean populateIfNotExist() throws Exception;
	
	/**
	 * Metodo che verifica l'esistenza di una configurazione custom
	 * 
	 * @return true se esiste almeno un record nella tabella delle configurazione PEC
	 * @throws Exception
	 */
	boolean configurationExist() throws Exception;
	
	/**
	 * 
	 * @param bean {@link ConfigurazionePECBean} contente le informazioni con cui aggiornare 
	 * la configurazione presente su db
	 * 
	 * @throws Exception
	 */
	void saveOrUpdate(ConfigurazionePECBean bean) throws Exception;
	
	/**
	 * Ripristina la configurazione di default presente nel progetto
	 * 
	 * @return Configurazione di default in un oggetto del tipo {@link ConfigurazionePECBean}
	 * @throws Exception
	 */
	ConfigurazionePECBean resetDefault() throws Exception;
	
	/**
	 * Elimina la configurazione presente su db
	 * 
	 * @throws Exception
	 */
	void delete() throws Exception;
	
	/**
	 * Restituisce la configurazione presente su db
	 * @return Configurazione salvata su db in un oggetto di tipo {@link ConfigurazionePECBean}
	 * @throws Exception
	 */
	ConfigurazionePECBean getConfiguration() throws Exception;

	/**
	 * carica il default di configurazione nella tabella configurazione_pec
	 * @autor Adriano Colaianni
	 * @date 4 mag 2021
	 * @return
	 * @throws Exception
	 */
	void caricaDefault() throws Exception;

	/**
	 * aggiorna il bean casellaMittenteApplicazione con il valore della casella a DB
	 * @autor Adriano Colaianni
	 * @date 26 mag 2022
	 * @throws Exception
	 */
	void initBeanCasellaMittenteApplicazione() throws Exception;
}
