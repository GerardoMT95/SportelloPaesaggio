package it.eng.tz.puglia.autpae.testRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.eng.tz.puglia.autpae.dto.LineaTemporaleDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;
import it.eng.tz.puglia.autpae.repository.ConfigurazionePermessiFullRepository;
import it.eng.tz.puglia.autpae.repository.FascicoloFullRepository;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.servizi_esterni.remote.repository.EnteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchemaAccessTest {

	@Autowired
	FascicoloFullRepository dao;
	@Autowired 
	ConfigurazionePermessiFullRepository confPermessiDao;
	@Autowired EnteRepository enteDao;
	
	@Autowired 
	FascicoloService fascicoloService;
	
	@Test
	public void lineaTemporale() throws Exception {
		
		fascicoloService.getCountForAccelerator(null);		// mettere l'apposito search 
		ConfigurazionePermessiDTO ret = confPermessiDao.find("PUBLIC");
		System.out.println(ret);
		FascicoloSearch fs=new FascicoloSearch();
		fs.setLimit(100);
		fascicoloService.searchFascicolo(fs);
		fascicoloService.getCountForAccelerator(null);		// mettere l'apposito search 
		LineaTemporaleDTO ret2 = dao.lineaTemporale(1L);
		enteDao.findComuniEProvince();
		fascicoloService.getCountForAccelerator(null);		// mettere l'apposito search 
		confPermessiDao.find("PUBLIC");
		
	}
}
