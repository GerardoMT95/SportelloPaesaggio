//package it.eng.tz.puglia.autpae.testRepository;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
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
//import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;
//import it.eng.tz.puglia.autpae.repository.ConfigurazionePermessiFullRepository;
//import it.eng.tz.puglia.autpae.search.ConfigurazionePermessiSearch;
//import it.eng.tz.puglia.autpae.utility.DtoFactory;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ConfigurazionePermessiTest
//{
//	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurazionePermessiTest.class);
//	private DtoFactory factory = new DtoFactory();
//	
//	@Autowired
//	private ConfigurazionePermessiFullRepository dao;
//	
//	@Test
//	@Rollback
//	@Transactional
//	public void testInsert()
//	{
//		try
//		{
//			ConfigurazionePermessiDTO conperm = factory.creaConfigurazionePermessi();
//			String result = dao.insert(conperm);
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
//			String id = dao.insert(factory.creaConfigurazionePermessi());
//			ConfigurazionePermessiDTO result = dao.find(id);
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
//			List<ConfigurazionePermessiDTO> result;
//			ConfigurazionePermessiDTO f1 = factory.creaConfigurazionePermessi();
//			ConfigurazionePermessiDTO f2 = factory.creaConfigurazionePermessi();
//			ConfigurazionePermessiDTO f3 = factory.creaConfigurazionePermessi();
//			
//			Boolean permesso = f1.getPermessoComunicazione();
//			f2.setPermessoComunicazione(permesso);
//			f3.setPermessoComunicazione(!permesso);
//
//			dao.insert(f1);
//			dao.insert(f2);
//			dao.insert(f3);
//			
//			ConfigurazionePermessiSearch search = new ConfigurazionePermessiSearch();
//			
//			search.setPermessoComunicazione(permesso);
//			result = dao.search(search).getList();
//			boolean existF1 = result.parallelStream().map(ConfigurazionePermessiDTO::getCodice).anyMatch(p -> p.equals(f1.getCodice()));
//			boolean existF2 = result.parallelStream().map(ConfigurazionePermessiDTO::getCodice).anyMatch(p -> p.equals(f2.getCodice()));
//			boolean existF3 = result.parallelStream().map(ConfigurazionePermessiDTO::getCodice).anyMatch(p -> p.equals(f3.getCodice()));
//
//			
//			assertTrue(existF1);
//			assertTrue(existF2);
//			assertFalse(existF3);
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
//			ConfigurazionePermessiSearch search = new ConfigurazionePermessiSearch();
//			long precount = dao.count(search);
//			
//			ConfigurazionePermessiDTO f1 = factory.creaConfigurazionePermessi();
//			ConfigurazionePermessiDTO f2 = factory.creaConfigurazionePermessi();
//			ConfigurazionePermessiDTO f3 = factory.creaConfigurazionePermessi();
//			
//			dao.insert(f1);
//			dao.insert(f2);
//			dao.insert(f3);
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
//			ConfigurazionePermessiDTO f = factory.creaConfigurazionePermessi();
//			dao.insert(f);
//			f.setPermessoComunicazione(!f.getPermessoComunicazione());
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
//			ConfigurazionePermessiSearch search = new ConfigurazionePermessiSearch();
//			long precount = dao.count(search);
//			ConfigurazionePermessiDTO f = factory.creaConfigurazionePermessi();
//			dao.insert(f);
//			assertEquals(precount + 1, dao.count(search));
//			search.setCodiceEnte(f.getCodiceEnte());
//			assertEquals(1, dao.delete(search));
//			search.setCodiceEnte(null);
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
