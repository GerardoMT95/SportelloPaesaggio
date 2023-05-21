package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoProcedimentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.VIpaUORepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.IpaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.DominioService;


@Service
public class DominioServiceImpl implements DominioService {

	@Autowired
	TipoProcedimentoRepository tipoProcDao;
	
	@Autowired 
	TipoContenutoRepository tipoContDao;
	
	@Autowired
	private VIpaUORepository vIpaRepository;
	
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public List<PlainNumberValueLabel> getTipiProcedimento(Boolean ancheInattivi) {
		return tipoProcDao.select().stream()
		.filter(el->(ancheInattivi!=null && ancheInattivi) || el.getAbilitatoPresentazione())
		.map(el->{
			return new PlainNumberValueLabel((long)el.getId(),el.getDescrizione(),el.getSanatoria()?"isInSanatoria":null);
		})
		.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public List<TipoContenutoDTO> getTipiContenuto(Integer tipoProcedimento) {
		List<TipoContenutoDTO> ret = tipoContDao.selectByTipoProc(tipoProcedimento);
		return ret;
		
	}
	
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true , rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<IpaEnteDTO> autocompleteIpaEnte(final String query) throws Exception
	{
	    return vIpaRepository.autocompleteIpaEnte(query);	
	}
	
		
	
}
