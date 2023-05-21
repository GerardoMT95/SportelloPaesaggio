/**
 * 
 */
package it.eng.tz.puglia.servizi_esterni.webmail.internal_event;

/**
 * @author Adriano Colaianni
 * @date 3 dic 2021
 */
public class KafkaConsumerEvent {
	enum TipoEvento{
		Riavvio
	}
 
	TipoEvento tipo=TipoEvento.Riavvio;
}
