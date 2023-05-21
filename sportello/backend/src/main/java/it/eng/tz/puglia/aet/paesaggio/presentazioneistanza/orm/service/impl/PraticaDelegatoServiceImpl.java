package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaDelegatoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaDelegatoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.PraticaDelegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.security.util.SecurityUtil;

@Service
public class PraticaDelegatoServiceImpl implements PraticaDelegatoService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PraticaDelegatoRepository dao;

	@Autowired
	PraticaRepository praticaDao;

	@Autowired
	private AllegatoService allegatoSvc;

	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE, timeout = 60000)
	public PraticaDelegatoDTO addDelegato(PraticaDelegatoDTO delegato, MultipartFile delega, PraticaDTO pratica)
			throws Exception {
		dao.setDelegatoCorrenteFalse(delegato.getId());
		delegato.setIndice(dao.nextIndex(delegato.getId()));
		dao.insert(delegato);
		allegatoSvc.uploadDelegaAllegato(pratica, delegato.getIndice(), SezioneAllegati.ALLEGATO_DELEGA.name(), delega);
		return delegato;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ, timeout = 60000)
	public PraticaDelegatoDTO getLastDelegato() throws Exception {
		PraticaDelegatoDTO delegato = null;
		PraticaDelegatoSearch search = new PraticaDelegatoSearch();
		search.setCodiceFiscale(SecurityUtil.getUserDetail().getFiscalCode());
		search.setLimit(1);
		search.setPage(1);
		PaginatedList<PraticaDelegatoDTO> list = dao.search(search);
		if (list.getList() == null || list.getList().isEmpty()) {
			delegato = new PraticaDelegatoDTO();
			final UserDetail userDetail = SecurityUtil.getUserDetail();
			delegato.setNome(userDetail.getFirstName());
			delegato.setCognome(userDetail.getLastName());
			delegato.setCodiceFiscale(userDetail.getFiscalCode());
		} else {
			delegato = list.getList().get(0);
		}
		return delegato;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ, timeout = 60000)
	public List<PraticaDelegatoDTO> findDelegati(UUID idPratica) throws Exception {
		PraticaDelegatoSearch search = new PraticaDelegatoSearch();
		search.setId(idPratica.toString());
		search.setLimit(100);
		search.setPage(1);
		return dao.search(search).getList();
	}

	@Override
	public void validaDelegato(PraticaDelegatoDTO delegato, MultipartFile delega)
			throws CustomOperationToAdviceException {
		if (delegato == null) {
			logger.error("Errore, delegato assente");
			throw new CustomOperationToAdviceException("Errore, delegato assente");
		}
		if (delega == null) {
			logger.error("Errore, allegato delegato assente");
			throw new CustomOperationToAdviceException("Errore, allegato delegato assente");
		}
		Set<ConstraintViolation<PraticaDelegatoDTO>> violations = validator.validate(delegato);
		if (violations != null && !violations.isEmpty()) {
			String errors = violations.stream().map(m -> m.getMessage()).collect(Collectors.joining("</li><li>"));
			throw new CustomOperationToAdviceException("<ul><li>" + errors + "</li></ul>");
		}
	}

}
