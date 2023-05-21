package it.eng.tz.puglia.autpae.utility;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.puglia.autpae.entity.AllegatoDTO;

public class AllegatoFactory
{
	private final Logger LOGGER = LoggerFactory.getLogger(AllegatoFactory.class);
	

	//Metodi per la crazione dell'allegato
	public AllegatoDTO creaAllegato()
	{
		LOGGER.info("popolo il dto allegato per i test");
		AllegatoDTO allegato = new AllegatoDTO();
		allegato.setNome(StringGenerator.randomAlphaNumeric(255));
		allegato.setDescrizione(StringGenerator.randomAlphaNumeric(10));
		allegato.setMimeType(StringGenerator.randomAlphaNumeric(10));
		allegato.setDataCaricamento(new Date());
		allegato.setContenuto(StringGenerator.randomAlphaNumeric(255));
		allegato.setChecksum(StringGenerator.randomAlphaNumeric(255));
		allegato.setDeleted(Math.random() < 0.5);
		allegato.setDimensione((int) (Math.random()*1000000));

		return allegato;
	}
}
