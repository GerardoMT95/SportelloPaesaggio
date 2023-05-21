package it.eng.tz.puglia.aet.orm;


import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.PresentazioneIstanzaApplication;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConferenzaDeiServiziRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConferenzaDeiServiziRepository.StatoConferenza;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentazioneIstanzaApplication.class)
public class Cds {
	@Autowired
	private ConferenzaDeiServiziRepository dao;
	
	@Test
	public void findIdsDaAggiornare() throws Exception {
		List<StatoConferenza> statiAmmessi = List.of(StatoConferenza.Bozza,StatoConferenza.Compilazione,StatoConferenza.Valutazione);
		List<Long> lista = this.dao.idCdsByPraticaEStati("86b09553-4213-4f93-9802-5d012a2c5aac", statiAmmessi);
		System.out.println(lista);
	}
	
	@Test
	public void hasAllegatiInviati() throws Exception {
		boolean ret = this.dao.hasCdsAllegatiInviati(UUID.fromString("86b09553-4213-4f93-9802-5d012a2c5aac"), 
				UUID.fromString("86b09553-4213-4f93-9802-5d012a2c5aac"),218L);
		System.out.println(ret);
	}

}
