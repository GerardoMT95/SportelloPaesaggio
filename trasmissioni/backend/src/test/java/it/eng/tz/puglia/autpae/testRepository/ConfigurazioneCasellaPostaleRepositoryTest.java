package it.eng.tz.puglia.autpae.testRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import it.eng.tz.puglia.autpae.dto.ConfigurazioneCasellaPostaleDTO;
import it.eng.tz.puglia.autpae.repository.base.ConfigurazioneCasellaPostaleBaseRepository;
import it.eng.tz.puglia.autpae.search.ConfigurazioneCasellaPostaleSearch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigurazioneCasellaPostaleRepositoryTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurazioneCasellaPostaleRepositoryTest.class);
	
	@Autowired
	private ConfigurazioneCasellaPostaleBaseRepository repo;

	@Test
	@Rollback
	@Transactional
	public void testSelect() {
		try {
			ConfigurazioneCasellaPostaleDTO ccpDTO = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO.setEmail("email_test@test.it");
			ccpDTO.setConfigurazione("configurazione di test 1");
			ConfigurazioneCasellaPostaleDTO ccpDTO2 = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO2.setEmail("email_test_pippo@testpippo.it");
			ccpDTO2.setConfigurazione("configurazione di test 2");
			repo.insert(ccpDTO);
			repo.insert(ccpDTO2);
			List<ConfigurazioneCasellaPostaleDTO> listCcpDTO = repo.select();
			assertNotNull(listCcpDTO);
		} catch (Exception e) {
			logger.error("Errore durante la insert");
		}
	}

	@Test
	@Rollback
	@Transactional
	public void testCount() {
		try {
			ConfigurazioneCasellaPostaleDTO ccpDTO = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO.setEmail("email_test@test.it");
			ccpDTO.setConfigurazione("configurazione di test 1");
			ConfigurazioneCasellaPostaleDTO ccpDTO2 = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO2.setEmail("email_test_pippo@testpippo.it");
			ccpDTO2.setConfigurazione("configurazione di test 2");
			repo.insert(ccpDTO);
			repo.insert(ccpDTO2);
			ConfigurazioneCasellaPostaleSearch filter = new ConfigurazioneCasellaPostaleSearch();
			filter.setEmail(ccpDTO.getEmail());
			assertEquals(1L, repo.count(filter));
		} catch (Exception e) {
			logger.error("Errore durante la insert");
		}
	}

	@Test
	@Rollback
	@Transactional
	public void testFind() {
		try {
			ConfigurazioneCasellaPostaleDTO ccpDTO = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO.setEmail("email_test@test.it");
			ccpDTO.setConfigurazione("configurazione di test 1");
			repo.insert(ccpDTO);
			assertEquals(ccpDTO, repo.find(ccpDTO.getEmail()));
		} catch (Exception e) {
			logger.error("Errore durante la insert");
		}
	}

	@Test
	@Rollback
	@Transactional
	public void testInsert() {
		try {
			ConfigurazioneCasellaPostaleDTO ccpDTO = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO.setEmail("email_test@test.it");
			ccpDTO.setConfigurazione("configurazione di test");
			assertEquals(ccpDTO.getEmail(), repo.insert(ccpDTO));
		} catch (Exception e) {
			logger.error("Errore durante la insert");
		}
	}

	@Test
	@Rollback
	@Transactional
	public void testUpdateConfigurazioneCasellaPostaleDTO() {
		try {
			ConfigurazioneCasellaPostaleDTO ccpDTO = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO.setEmail("email_test@test.it");
			ccpDTO.setConfigurazione("configurazione di test 1");
			repo.insert(ccpDTO);
			ccpDTO.setConfigurazione("configurazione di test 1 modificata");
			assertEquals(1, repo.update(ccpDTO));
		} catch (Exception e) {
			logger.error("Errore durante la insert");
		}
	}

	@Test
	@Rollback
	@Transactional
	public void testDelete() {
		try {
			ConfigurazioneCasellaPostaleDTO ccpDTO = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO.setEmail("email_test@test.it");
			ccpDTO.setConfigurazione("configurazione di test 1");
			repo.insert(ccpDTO);
			ConfigurazioneCasellaPostaleSearch entity = new ConfigurazioneCasellaPostaleSearch();
			entity.setEmail(ccpDTO.getEmail());
			assertEquals(1, repo.delete(entity));
		} catch (Exception e) {
			logger.error("Errore durante la insert");
		}
	}

	@Test
	@Rollback
	@Transactional
	public void testSearch() {
		try {
			ConfigurazioneCasellaPostaleDTO ccpDTO = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO.setEmail("email_test@test.it");
			ccpDTO.setConfigurazione("configurazione di test 1");
			ConfigurazioneCasellaPostaleDTO ccpDTO2 = new ConfigurazioneCasellaPostaleDTO();
			ccpDTO2.setEmail("email_test_pippo@testpippo.it");
			ccpDTO2.setConfigurazione("configurazione di test 2");
			repo.insert(ccpDTO);
			repo.insert(ccpDTO2);
			ConfigurazioneCasellaPostaleSearch bean = new ConfigurazioneCasellaPostaleSearch();
			bean.setEmail(ccpDTO.getEmail());
			List<ConfigurazioneCasellaPostaleDTO> listCcpDTO = repo.search(bean).getList();
			assertNotNull(listCcpDTO);
		} catch (Exception e) {
			logger.error("Errore durante la insert");
		}
	}

}
