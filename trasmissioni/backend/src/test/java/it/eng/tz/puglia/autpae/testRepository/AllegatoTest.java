package it.eng.tz.puglia.autpae.testRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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

import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.ApplicazioneRepository;
import it.eng.tz.puglia.autpae.generated.orm.search.ApplicazioneSearch;
import it.eng.tz.puglia.autpae.repository.AllegatoDiogeneInfoRepository;
import it.eng.tz.puglia.autpae.repository.AllegatoFullRepository;
import it.eng.tz.puglia.autpae.repository.base.AllegatoBaseRepository;
import it.eng.tz.puglia.autpae.search.AllegatoSearch;
import it.eng.tz.puglia.autpae.utility.AllegatoFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AllegatoTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AllegatoTest.class);
	private AllegatoFactory factory = new AllegatoFactory();
	
	@Autowired
	private AllegatoBaseRepository dao;
	@Autowired
	private ApplicazioneRepository appDao;
	
	@Autowired
	private AllegatoDiogeneInfoRepository allDao;
	
	@Test
	public void testMapping()
	{
		AllegatoSearch search = new AllegatoSearch();
		search.setColonna(Allegato.id);
		search.setId(1L);
		StringBuilder sql = new StringBuilder("select * from ").append(Allegato.getTableName());
		search.getSqlWhereClause(sql);
		search.getSqlOrderByClause(sql);
		LOGGER.info(sql.toString());
	}
	
	@Test
	public void testGetDocDiogene()
	{
		allDao.getListaFileDaInviare(1,10);
	}
	

	
	@Test
	public void testCommon()
	{
		ApplicazioneSearch search=new ApplicazioneSearch();
		search.setCodice("AUTPAE");
		appDao.search(search);
		
	}
	
	@Test
	@Rollback
	@Transactional
	public void testInsert()
	{
		try
		{
			AllegatoDTO allegato = factory.creaAllegato();
			Long result = dao.insert(allegato);
			assertNotEquals((Long)0L, result);
		}
		catch(Exception e)
		{
			LOGGER.error("Errore nell'inserimento del allegato {}", e.getMessage(), e);
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
			Long id = dao.insert(factory.creaAllegato());
			AllegatoDTO result = dao.find(id);
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
			List<AllegatoDTO> result;
			AllegatoDTO f1 = factory.creaAllegato();
			AllegatoDTO f2 = factory.creaAllegato();
			AllegatoDTO f3 = factory.creaAllegato();

			f1.setId(dao.insert(f1));
			f2.setId(dao.insert(f2));
			f3.setId(dao.insert(f3));
			
			AllegatoSearch search = new AllegatoSearch();
			
			search.setNome(f1.getNome());
			result = dao.search(search).getList();
			assertNotEquals(0, result.size());
			
			search.setNome(null);
			search.setDeleted(f2.getDeleted());
			result = dao.search(search).getList();
			assertNotEquals(0, result.size());
			
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
	public void testCount()
	{
		try
		{
			AllegatoDTO f1 = factory.creaAllegato();
			AllegatoDTO f2 = factory.creaAllegato();
			AllegatoDTO f3 = factory.creaAllegato();
	
			f1.setId(dao.insert(f1));
			f2.setId(dao.insert(f2));
			f3.setId(dao.insert(f3));
			
			AllegatoSearch search = new AllegatoSearch();
			assertEquals(3, dao.count(search));
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
			AllegatoDTO f = factory.creaAllegato();
			f.setId(dao.insert(f));
			f.setNome("codiceModificato");
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
			AllegatoDTO f = factory.creaAllegato();
			f.setId(dao.insert(f));
			f.setNome("codiceModificato");
			AllegatoSearch search = new AllegatoSearch();
			search.setId(f.getId());
			assertEquals(1, dao.delete(search));
		}
		catch(Exception e)
		{
			LOGGER.info("Errore non previsto ", e.getMessage(), e);
			fail();
		}
	}
}
