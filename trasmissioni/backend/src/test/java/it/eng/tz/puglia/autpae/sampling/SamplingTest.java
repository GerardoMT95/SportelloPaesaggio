package it.eng.tz.puglia.autpae.sampling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.service.interfacce.CampionamentoService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SamplingTest {

	@Autowired CampionamentoService campionamentoSvc;
	
	@Test
	@Transactional  //rollback al termine del test
	public void inviaMail() throws Exception {
//		campionamentoSvc.inviaPresamplingNotification(1);
//		campionamentoSvc.inviaNotificaVerifica(1);
		CampionamentoDTO entity = campionamentoSvc.getActiveSampling();
		campionamentoSvc.effettuaCampionamentoEInviaNotifica(entity);
	}
	
	@Test
	@Transactional  //rollback al termine del test
	public void inviaMailNotificaPreVerifica() throws Exception {
		CampionamentoDTO entity = campionamentoSvc.getActiveSampling();
		campionamentoSvc.inviaNotificaVerifica(entity.getId());
	}
	
	
	
}
