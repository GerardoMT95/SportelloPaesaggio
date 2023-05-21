//package it.eng.tz.puglia.autpae.testRepository;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import it.eng.tz.puglia.autpae.entity.TipiEQualificazioniDTO;
//import it.eng.tz.puglia.autpae.repository.base.TipiEQualificazioniBaseRepository;
//import it.eng.tz.puglia.autpae.search.TipiEQualificazioniSearch;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TipiEQualificazioniRepositoryTest {
//	
//	@Autowired
//	private TipiEQualificazioniBaseRepository tipiEQualificazioniRepository;
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testListByTipo() {
//		assertEquals(3, tipiEQualificazioniRepository.listByTipo("qualDPCM_2005").size());
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testCount() {
//		TipiEQualificazioniSearch search = new TipiEQualificazioniSearch("qualDPCM_2005", null, null);
//		assertEquals(3, tipiEQualificazioniRepository.count(search));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testFind() {
//		TipiQualificazioniPK pk = new TipiQualificazioniPK("qualDPCM_2005", "DPCM_2005_01");
//		TipiEQualificazioniDTO tipiEQualificazioniDTO = new TipiEQualificazioniDTO("qualDPCM_2005", "DPCM_2005_01", "ordinari");
//		assertEquals(tipiEQualificazioniDTO, tipiEQualificazioniRepository.find(pk));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testSelect() throws Exception {
//		assertEquals(107, tipiEQualificazioniRepository.select().size());
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testInsert() {
//		TipiEQualificazioniDTO tipiEQualificazioniDTO = new TipiEQualificazioniDTO("test", "test", "test");
//		assertNull(tipiEQualificazioniRepository.insert(tipiEQualificazioniDTO));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testUpdateTipiEQualificazioniDTO() {
//		TipiEQualificazioniDTO tipiEQualificazioniDTO = new TipiEQualificazioniDTO("qualDPCM_2005", "DPCM_2005_01", "ordinari test");
//		assertNotNull(tipiEQualificazioniRepository.update(tipiEQualificazioniDTO));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testDelete() {
//		TipiEQualificazioniSearch search = new TipiEQualificazioniSearch("qualDPCM_2005", null, null);
//		assertEquals(3, tipiEQualificazioniRepository.delete(search));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testSearch() {
//		TipiEQualificazioniSearch search = new TipiEQualificazioniSearch("qualDPCM_2005", null, null);
//		assertEquals(3, tipiEQualificazioniRepository.search(search).getList().size());	
//	}
//
//}
