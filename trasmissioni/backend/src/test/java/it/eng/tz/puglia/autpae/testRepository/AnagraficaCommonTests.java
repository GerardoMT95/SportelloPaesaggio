package it.eng.tz.puglia.autpae.testRepository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.repository.AnagraficaFullRepository;
import it.eng.tz.puglia.servizi_esterni.remote.repository.EnteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnagraficaCommonTests
{
	private Logger LOGGER = LoggerFactory.getLogger(AnagraficaCommonTests.class);
	@Autowired
	private AnagraficaFullRepository anagraficaRepository;	
	@Autowired
	private EnteRepository enteRepository;
	
	@Test
	public void test_1() throws Exception
	{
		List<TipologicaDTO> list = anagraficaRepository.selectAllUcpPaesaggioRurale();
		LOGGER.info("anagrafica dto: {}", list);
	}
	
	@Test
	public void test_2()
	{
		List<EnteDTO> list = enteRepository.selectAll();
		LOGGER.info("anagrafica dto: {}", list);
	}
	
	@Test
	public void mailParchi() throws Exception
	{
		List<TipologicaDTO> list = anagraficaRepository.emailParchi(List.of("PR0020"));
		LOGGER.info("email parco dto: {}", list);
	}
}