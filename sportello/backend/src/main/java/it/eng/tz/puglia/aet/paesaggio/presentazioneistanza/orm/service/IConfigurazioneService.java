package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.Date;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioneDTO;

public interface IConfigurazioneService {

	/**
	 * cerca la configurazione corrente per il bean di tipo
	 * @author acolaianni
	 *
	 * @param data
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	<T> T findConfigurazioneCorrente(Date data, Class<T> tipo) throws Exception;

	/**
	 * aggiorna la configurazione corrente settando end_date alla corrente e creando una nuova
	 * @author acolaianni
	 *
	 * @param <T>
	 * @param objConf
	 * @param tipo
	 * @param userUpdated
	 * @return
	 * @throws Exception
	 */
	<T> ConfigurazioneDTO upsertConfigurazione(T objConf, Class<T> tipo, String userUpdated) throws Exception;
}