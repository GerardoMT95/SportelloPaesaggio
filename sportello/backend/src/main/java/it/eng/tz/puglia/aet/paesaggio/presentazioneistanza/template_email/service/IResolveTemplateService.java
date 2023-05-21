package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service;

import java.util.UUID;
import java.util.function.Function;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;

public interface IResolveTemplateService {

	String risolviTesto(String placeholder, String testo, UUID idPratica);
	String risolviTesto(String placeholder, String testo, PraticaDTO pratica);
	/**
	 * se alcuni tag li risolvo fuori con logica esterna...
	 * @author acolaianni
	 *
	 * @param placeholder
	 * @param testo
	 * @param pratica
	 * @param customResolving
	 * @return
	 */
	String risolviTesto(String placeholder, String testo, PraticaDTO pratica, Function<String, String> customResolving);
}
