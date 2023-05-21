//package it.eng.tz.puglia.autpae.testRepository;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
//import it.eng.tz.puglia.autpae.repository.base.ConfigurazioneCampionamentoBaseRepository;
//import it.eng.tz.puglia.autpae.search.ConfigurazioneCampionamentoSearch;
//import it.eng.tz.puglia.autpae.utility.DtoFactory;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ConfigurazioneTest
//{
//	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurazioneTest.class);
//	private DtoFactory factory = new DtoFactory();
//	
//	@Autowired
//	private ConfigurazioneCampionamentoBaseRepository dao;
//	
//	@Test
//	@Rollback
//	@Transactional
//	public void testInsert()
//	{
//		try
//		{
//			ConfigurazioneCampionamentoDTO conf = factory.creaConfigurazione();
//			Long result = dao.insert(conf);
//			assertNotNull(result);
//		}
//		catch(Exception e)
//		{
//			LOGGER.error("Errore nell'inserimento del fascicolo {}", e.getMessage(), e);
//			fail();
//		}
//	}
//	
//	@Test
//	@Rollback
//	@Transactional
//	public void testFindById()
//	{
//		try
//		{
//			Long id = dao.insert(factory.creaConfigurazione());
//			ConfigurazioneCampionamentoDTO result = dao.find(id);
//			assertNotNull(result);
//		}
//		catch(Exception e)
//		{
//			LOGGER.error("Errore ", e.getMessage(), e);
//			fail();
//		}
//	}
//	
//	@Test
//	@Rollback
//	@Transactional
//	public void testSearch()
//	{
//		try
//		{
//			List<ConfigurazioneCampionamentoDTO> result;
//			ConfigurazioneCampionamentoDTO c1 = factory.creaConfigurazione();
//			ConfigurazioneCampionamentoDTO c2 = factory.creaConfigurazione();
//			ConfigurazioneCampionamentoDTO c3 = factory.creaConfigurazione();
//			
//			Boolean attivo = c1.getCampionamentoAttivo();
//			c2.setCampionamentoAttivo(attivo);
//			c3.setCampionamentoAttivo(!attivo);
//			
//
//			c1.setId(dao.insert(c1));
//			c2.setId(dao.insert(c2));
//			c3.setId(dao.insert(c3));
//			
//			ConfigurazioneCampionamentoSearch search = new ConfigurazioneCampionamentoSearch();
//			
//			search.setCampionamentoAttivo(attivo);
//			result = dao.search(search).getList();
//			boolean existC1 = result.parallelStream().map(ConfigurazioneCampionamentoDTO::getId).anyMatch(p -> p.equals(c1.getId()));
//			boolean existC2 = result.parallelStream().map(ConfigurazioneCampionamentoDTO::getId).anyMatch(p -> p.equals(c2.getId()));
//			
//			assertTrue(existC1);
//			assertTrue(existC2);
//		}
//		catch(Exception e)
//		{
//			LOGGER.error("Errore ", e.getMessage(), e);
//			fail();
//		}
//	}
//	
//	@Test
//	@Rollback
//	@Transactional
//	public void testCount()
//	{
//		try
//		{
//			ConfigurazioneCampionamentoSearch search = new ConfigurazioneCampionamentoSearch();
//			long precount = dao.count(search);
//			
//			ConfigurazioneCampionamentoDTO f1 = factory.creaConfigurazione();
//			ConfigurazioneCampionamentoDTO f2 = factory.creaConfigurazione();
//			ConfigurazioneCampionamentoDTO f3 = factory.creaConfigurazione();
//	
//			f1.setId(dao.insert(f1));
//			f2.setId(dao.insert(f2));
//			f3.setId(dao.insert(f3));
//			
//			assertEquals(precount + 3, dao.count(search));
//		}
//		catch(Exception e)
//		{
//			LOGGER.info("Errore non previsto {}", e.getMessage(), e);
//			fail();
//		}
//	}
//	
//	@Test
//	@Rollback
//	@Transactional
//	public void testUpdate()
//	{
//		try
//		{
//			ConfigurazioneCampionamentoDTO f = factory.creaConfigurazione();
//			f.setId(dao.insert(f));
//			f.setCampionamentoAttivo(!f.getCampionamentoAttivo());
//			assertEquals(1, dao.update(f));
//		}
//		catch(Exception e)
//		{
//			LOGGER.info("Errore non previsto ", e.getMessage(), e);
//			fail();
//		}
//	}
//	
//	@Test
//	@Rollback
//	@Transactional
//	public void testDelete()
//	{
//		try
//		{
//			ConfigurazioneCampionamentoSearch search = new ConfigurazioneCampionamentoSearch();
//			long precount = dao.count(search);
//			ConfigurazioneCampionamentoDTO f = factory.creaConfigurazione();
//			f.setId(dao.insert(f));
//			assertEquals(precount + 1, dao.count(search));
//			search.setId(f.getId());
//			assertEquals(1, dao.delete(search));
//			search.setId(null);
//			assertEquals(precount, dao.count(search));
//
//		}
//		catch(Exception e)
//		{
//			LOGGER.info("Errore non previsto ", e.getMessage(), e);
//			fail();
//		}
//	}
//}
