package it.eng.tz.puglia.autpae.testRepository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.TemplateEmailDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.repository.base.TemplateEmailBaseRepository;
import it.eng.tz.puglia.autpae.search.TemplateEmailSearch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateRepositoryTest {
	
	@Autowired
	private TemplateEmailBaseRepository templateRepository;

	@Test
	@Rollback
	@Transactional
	public void testSelect() throws Exception {
		TemplateEmailDTO templateDTO = new TemplateEmailDTO(TipoTemplate.ESITO_VERIFICA, "oggetto test", "testo test", "descrizione test", "nome test");
		templateRepository.insert(templateDTO);
		assertEquals(1, templateRepository.select().size());
	}

	@Test
	@Rollback
	@Transactional
	public void testCount() throws Exception {
		TemplateEmailDTO templateDTO = new TemplateEmailDTO(TipoTemplate.ESITO_VERIFICA, "oggetto test", "testo test", "descrizione test", "nome test");
		templateRepository.insert(templateDTO);
		TemplateEmailSearch search = new TemplateEmailSearch();
		search.setCodice(templateDTO.getCodice());
		assertEquals(1, templateRepository.count(search));
	}

	@Test
	@Rollback
	@Transactional
	public void testFind() throws Exception {
		TemplateEmailDTO templateDTO = new TemplateEmailDTO(TipoTemplate.ESITO_VERIFICA, "oggetto test", "testo test", "descrizione test", "nome test");
		templateRepository.insert(templateDTO);
		assertEquals(templateDTO, templateRepository.find(templateDTO.getCodice()));
	}

	@Test
	@Rollback
	@Transactional
	public void testInsert() throws Exception {
		TemplateEmailDTO templateDTO = new TemplateEmailDTO(TipoTemplate.ESITO_VERIFICA, "oggetto test", "testo test", "descrizione test", "nome test");
		assertEquals(templateDTO.getCodice(), templateRepository.insert(templateDTO));
	}

	@Test
	@Rollback
	@Transactional
	public void testUpdateTemplateDTO() throws Exception {
		TemplateEmailDTO templateDTO = new TemplateEmailDTO(TipoTemplate.ESITO_VERIFICA, "oggetto test", "testo test", "descrizione test", "nome test");
		templateRepository.insert(templateDTO);
		templateDTO.setNome("nome test update");
		assertNotNull(templateRepository.update(templateDTO));
	}

	@Test
	@Rollback
	@Transactional
	public void testDelete() throws Exception {
		TemplateEmailDTO templateDTO = new TemplateEmailDTO(TipoTemplate.ESITO_VERIFICA, "oggetto test", "testo test", "descrizione test", "nome test");
		templateRepository.insert(templateDTO);
		TemplateEmailSearch search = new TemplateEmailSearch();
		search.setCodice(templateDTO.getCodice());
		assertNotNull(templateRepository.delete(search));
	}

	@Test
	@Rollback
	@Transactional
	public void testSearch() throws Exception {
		TemplateEmailDTO templateDTO = new TemplateEmailDTO(TipoTemplate.ESITO_VERIFICA, "oggetto test", "testo test", "descrizione test", "nome test");
		templateRepository.insert(templateDTO);
		TemplateEmailSearch search = new TemplateEmailSearch();
		search.setCodice(templateDTO.getCodice());
		assertEquals(1, templateRepository.search(search).getList().size());
	}

}
