package it.eng.tz.puglia.geo.service;

import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;

public interface IParticelleCatastaliResolve {

	/**
	 * Elabora il record particella
	 * @author Alessio Bottalico
	 * @date 3 ott 2022
	 * @param location
	 * @throws Exception
	 */
	public void elaboraParticelle(ParticelleCatastaliDTO location) throws Exception;
}
