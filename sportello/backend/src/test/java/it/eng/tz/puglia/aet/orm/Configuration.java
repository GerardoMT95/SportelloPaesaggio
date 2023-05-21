package it.eng.tz.puglia.aet.orm;


import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.PresentazioneIstanzaApplication;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.config.SportelloConfigBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.DestinatarioService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentazioneIstanzaApplication.class)
public class Configuration {
	@Autowired 
	IConfigurazioneService confSvc;
	@Autowired
	DestinatarioService destinatariSvc;
	
	@Test
	public void fetchAndUpdate() throws Exception {
		
		SportelloConfigBean dto =(SportelloConfigBean)confSvc.findConfigurazioneCorrente(new Date(),SportelloConfigBean.class);
		System.out.println(dto);
		final String nuovoValore="NUOVO VALORE";
		dto.setEsoneroOneriLabel(nuovoValore);
		confSvc.upsertConfigurazione(dto, SportelloConfigBean.class,"io");
		SportelloConfigBean nuovo =(SportelloConfigBean)confSvc.findConfigurazioneCorrente(new Date(),SportelloConfigBean.class);
		assert nuovo.getEsoneroOneriLabel().equals(nuovoValore);
	}
	
	
	@Test
	public void findDestinatari() throws Exception {
		
		List<DestinatarioDTO> ret = destinatariSvc.findDestinatariForEnteDelegato(8, List.of(11), TipoDestinatario.TO);
		ret.addAll(destinatariSvc.findDestinatariForRegione(List.of(11), TipoDestinatario.TO));
		ret.addAll(destinatariSvc.findDestinatariForSoprintendenze(List.of(11), TipoDestinatario.TO));
		ret.addAll(destinatariSvc.findDestinatariForComuni(List.of(11), TipoDestinatario.TO));
		ret.addAll(destinatariSvc.findDestinatariForProvince(List.of(11), TipoDestinatario.TO));
		System.out.println(ret);
		
	}

}
