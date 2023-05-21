package it.eng.tz.puglia.autpae.testRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.repository.FascicoloFullRepository;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.utility.DtoFactory;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FascicoloTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FascicoloTest.class);
	private DtoFactory factory = new DtoFactory();
	
	@Autowired
	private FascicoloFullRepository dao;
	
	@Test
	@Rollback
	@Transactional
	public void testInsert()
	{
		try
		{
			FascicoloDTO fascicolo = factory.creaFascicolo();
			Long result = dao.insert(fascicolo);
			assertNotNull(result);
		}
		catch(Exception e)
		{
			LOGGER.error("Errore nell'inserimento del fascicolo {}", e.getMessage(), e);
			fail();
		}
	}
	
	@Test
	@Rollback
	@Transactional
	public void testFindById()
	{
		try
		{
			Long id = dao.insert(factory.creaFascicolo());
			FascicoloDTO result = dao.find(id);
			assertNotNull(result);
		}
		catch(Exception e)
		{
			LOGGER.error("Errore ", e.getMessage(), e);
			fail();
		}
	}
	
	@Test
	@Rollback
	@Transactional
	public void testSearch()
	{
		try
		{
			List<FascicoloDTO> result;
			FascicoloDTO f1 = factory.creaFascicolo();
			FascicoloDTO f2 = factory.creaFascicolo();
			FascicoloDTO f3 = factory.creaFascicolo();
			
			String codice = f1.getCodice();
			f2.setCodice(codice);
			f3.setCodice("test");
			

			f1.setId(dao.insert(f1));
			f2.setId(dao.insert(f2));
			f3.setId(dao.insert(f3));
			
			FascicoloSearch search = new FascicoloSearch();
			
			search.setCodice(codice);
			result = dao.search(search).getList();
			boolean existF1 = result.parallelStream().map(FascicoloDTO::getId).anyMatch(p -> p.equals(f1.getId()));
			boolean existF2 = result.parallelStream().map(FascicoloDTO::getId).anyMatch(p -> p.equals(f2.getId()));
			
			assertTrue(existF1);
			assertTrue(existF2);
		}
		catch(Exception e)
		{
			LOGGER.error("Errore ", e.getMessage(), e);
			fail();
		}
	}
	
	@Test
	@Rollback
	@Transactional
	public void testCount()
	{
		try
		{
			FascicoloSearch search = new FascicoloSearch();
			long precount = dao.count(search);
			
			FascicoloDTO f1 = factory.creaFascicolo();
			FascicoloDTO f2 = factory.creaFascicolo();
			FascicoloDTO f3 = factory.creaFascicolo();
	
			f1.setId(dao.insert(f1));
			f2.setId(dao.insert(f2));
			f3.setId(dao.insert(f3));
			
			assertEquals(precount + 3, dao.count(search));
		}
		catch(Exception e)
		{
			LOGGER.info("Errore non previsto {}", e.getMessage(), e);
			fail();
		}
	}
	
	@Test
	@Rollback
	@Transactional
	public void testUpdate()
	{
		try
		{
			FascicoloDTO f = factory.creaFascicolo();
			f.setId(dao.insert(f));
			f.setCodice("codiceModificato");
			assertEquals(1, dao.update(f));
		}
		catch(Exception e)
		{
			LOGGER.info("Errore non previsto ", e.getMessage(), e);
			fail();
		}
	}
	
	@Test
	@Rollback
	@Transactional
	public void testDelete()
	{
		try
		{
			FascicoloSearch search = new FascicoloSearch();
			long precount = dao.count(search);
			FascicoloDTO f = factory.creaFascicolo();
			f.setId(dao.insert(f));
			assertEquals(precount + 1, dao.count(search));
			search.setId(f.getId());
			assertEquals(1, dao.delete(search));
			search.setId(null);
			assertEquals(precount, dao.count(search));

		}
		catch(Exception e)
		{
			LOGGER.info("Errore non previsto ", e.getMessage(), e);
			fail();
		}
	}
	
	
}

