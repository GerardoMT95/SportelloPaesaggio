package it.eng.tz.puglia.servizi_esterni.remote.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.repository.EnteRepository;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.RemoteSchemaService;

@Service
public class RemoteSchemaServiceImpl implements RemoteSchemaService {

	@Autowired
	private EnteRepository enteRepository;

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteDTO> findAllAvailableEnte() {
		return enteRepository.selectAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public EnteDTO findEnteByCode(String code) {
		Optional<EnteDTO> result = enteRepository.find(code);
		if (result.isPresent()) {
			return result.get();
		}
		return new EnteDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteDTO> findAllEnteByCodes(List<String> codes) {
		return enteRepository.findAll(codes);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteDTO> findComuniEProvince() throws Exception
	{
		return enteRepository.findComuniEProvince();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_common", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<EnteDTO> getFromCodici(List<String>codici) throws Exception
	{
		return enteRepository.getFromCodici(codici);
	}

}