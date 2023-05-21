package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.dto.ConfigurazioneCasellaPostaleDTO;
import it.eng.tz.puglia.autpae.repository.base.ConfigurazioneCasellaPostaleBaseRepository;
import it.eng.tz.puglia.autpae.search.ConfigurazioneCasellaPostaleSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneCasellaPostaleService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class ConfigurazioneCasellaPostaleServiceImpl implements ConfigurazioneCasellaPostaleService {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigurazioneCasellaPostaleServiceImpl.class);
	
	@Autowired
	private ConfigurazioneCasellaPostaleBaseRepository repository;

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<ConfigurazioneCasellaPostaleDTO> select() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.select();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public long count(ConfigurazioneCasellaPostaleSearch filter) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.count(filter);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public ConfigurazioneCasellaPostaleDTO find(String pk) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.find(pk);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public String insert(ConfigurazioneCasellaPostaleDTO entity) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.insert(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int update(ConfigurazioneCasellaPostaleDTO entity) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.update(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int delete(ConfigurazioneCasellaPostaleSearch entity) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.delete(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public PaginatedList<ConfigurazioneCasellaPostaleDTO> search(ConfigurazioneCasellaPostaleSearch bean) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.search(bean);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
	public void insertOrUpdate(ConfigurazioneCasellaPostaleDTO ccpDTO) throws Exception {
   		int res = repository.update(ccpDTO);
		if(res==0)
			repository.insert(ccpDTO);
	}
	
	@Override
   	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ConfigurazioneCasellaPostaleDTO> getCaselleDaAttivare() throws Exception {
    	return repository.getCaselleDaAttivare();
   	
	}
	
	@Override
   	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ConfigurazioneCasellaPostaleDTO> getCaselleDaDisattivare() throws Exception {
    	return repository.getCaselleDaDisattivare();
   	
	}
	
	

}