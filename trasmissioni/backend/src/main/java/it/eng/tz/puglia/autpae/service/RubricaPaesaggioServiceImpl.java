package it.eng.tz.puglia.autpae.service;

import org.springframework.stereotype.Service;

import it.eng.tz.puglia.autpae.service.interfacce.RubricaPaesaggioService;

@Service
public class RubricaPaesaggioServiceImpl implements RubricaPaesaggioService {

/*	
  	private static final Logger log = LoggerFactory.getLogger(RubricaPaesaggioServiceImpl.class);
	
	@Autowired	private CommonService commonService;
	@Autowired  private	ApplicationProperties props;
	@Autowired	private UserUtil userUtil;
*/	

	
/*	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public long insert(PaesaggioEmailDTO entity, int idEnte, int idOrganizzazione) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			entity.setEnteId(idEnte);
			entity.setOrganizzazioneId(idOrganizzazione);
			entity.setCodiceApplicazione(props.getCodiceApplicazione());
			return commonService.insertRubricaPaesaggio(entity);
		} catch (Exception e) {
			throw e;
		}
	}	*/
	
}