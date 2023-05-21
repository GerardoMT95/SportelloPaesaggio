package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.ProvvedimentoTabDTO;
import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoObbligatorioPK;
import it.eng.tz.puglia.autpae.enumeratori.ErroriValidazioneBE;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.search.AllegatoFascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoFascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoObbligatorioService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.ProvvedimentoService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class ProvvedimentoServiceImpl implements ProvvedimentoService {

	private static final Logger log = LoggerFactory.getLogger(ProvvedimentoServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private FascicoloService fascicoloService;
	
	@Autowired	private AllegatoService allegatoService;
	
	@Autowired	private AllegatoFascicoloService allegatoFascicoloService;
	
	@Autowired	private AllegatoObbligatorioService allegatoObbligatorioService;
	
	@Autowired private MessageSource messageSource;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public ProvvedimentoTabDTO datiProvvedimento(Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		ProvvedimentoTabDTO provvedimentoTab = new ProvvedimentoTabDTO();

		AllegatoFascicoloSearch searchProvvedimento = new AllegatoFascicoloSearch();
		searchProvvedimento.setIdFascicolo(idFascicolo);
		searchProvvedimento.setType(TipoAllegato.PROVVEDIMENTO_FINALE);
		List<AllegatoFascicoloDTO> listaProvv = allegatoFascicoloService.search(searchProvvedimento).getList();
		AllegatoDTO provvedimentoFinale = new AllegatoDTO();
		if (!listaProvv.isEmpty()) {
			provvedimentoFinale = allegatoService.find(listaProvv.get(0).getIdAllegato());	// TODO: sostituire tutto con il metodo generico per gli allegati
		}
		AllegatoCustomDTO provvedimento = new AllegatoCustomDTO(provvedimentoFinale);
		provvedimento.setObbligatorio(true);
		provvedimento.setMultiplo(false);
		provvedimentoTab.setProvvedimento(provvedimento);
		
		
		AllegatoFascicoloSearch searchProvvedimentoPrivato = new AllegatoFascicoloSearch();
		searchProvvedimentoPrivato.setIdFascicolo(idFascicolo);
		searchProvvedimentoPrivato.setType(TipoAllegato.PROVVEDIMENTO_FINALE_PRIVATO);
		List<AllegatoFascicoloDTO> listaProvvPrivato = allegatoFascicoloService.search(searchProvvedimentoPrivato).getList();
		AllegatoDTO provvedimentoFinalePrivato = new AllegatoDTO();
		if (!listaProvvPrivato.isEmpty()) {
			provvedimentoFinalePrivato = allegatoService.find(listaProvvPrivato.get(0).getIdAllegato());  // TODO: sostituire tutto con il metodo generico per gli allegati
		}
		AllegatoCustomDTO provvedimentoPrivato = new AllegatoCustomDTO(provvedimentoFinalePrivato);
		provvedimentoPrivato.setObbligatorio(false);
		provvedimentoPrivato.setMultiplo(false);
		provvedimentoTab.setProvvedimentoPrivato(provvedimentoPrivato);
		
		
		AllegatoCustomDTO parere 		= null;
		AllegatoCustomDTO parerePrivato = null;
		TipoProcedimento tipoProc = fascicoloService.find(idFascicolo).getTipoProcedimento();
		try {
			allegatoObbligatorioService.find(new AllegatoObbligatorioPK(TipoAllegato.PARERE_MIBAC, tipoProc.name()));
			log.info("L'allegato "+TipoAllegato.PARERE_MIBAC.name()+" è previsto");

			AllegatoFascicoloSearch searchParere = new AllegatoFascicoloSearch();
			searchParere.setIdFascicolo(idFascicolo);
			searchParere.setType(TipoAllegato.PARERE_MIBAC);
			List<AllegatoFascicoloDTO> listaParere = allegatoFascicoloService.search(searchParere).getList();

			if (!listaParere.isEmpty()) {
				AllegatoDTO parereMibac = allegatoService.find(listaParere.get(0).getIdAllegato());	// TODO: sostituire tutto con il metodo generico per gli allegati
				parere = new AllegatoCustomDTO(parereMibac);
			}
			else {
				parere = new AllegatoCustomDTO();
			}
			parere.setObbligatorio(true);
			parere.setMultiplo(false);
			
			
			AllegatoFascicoloSearch searchParerePrivato = new AllegatoFascicoloSearch();
			searchParerePrivato.setIdFascicolo(idFascicolo);
			searchParerePrivato.setType(TipoAllegato.PARERE_MIBAC_PRIVATO);
			List<AllegatoFascicoloDTO> listaParerePrivato = allegatoFascicoloService.search(searchParerePrivato).getList();

			if (!listaParerePrivato.isEmpty()) {
				AllegatoDTO parereMibacPrivato = allegatoService.find(listaParerePrivato.get(0).getIdAllegato());	// TODO: sostituire tutto con il metodo generico per gli allegati
				parerePrivato = new AllegatoCustomDTO(parereMibacPrivato);
			}
			else {
				parerePrivato = new AllegatoCustomDTO();
			}
			parerePrivato.setObbligatorio(false);
			parerePrivato.setMultiplo(false);
		} catch (DataAccessException e) {
			log.info("L'allegato "+TipoAllegato.PARERE_MIBAC.name()+" non è previsto");
		} finally {
			provvedimentoTab.setParere		 (parere);
			provvedimentoTab.setParerePrivato(parerePrivato);
		}
		return provvedimentoTab;
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public AllegatoCustomDTO inserisciAllegato(Long idFascicolo, TipoAllegato tipoAllegato, MultipartFile file) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		// controllo che non esista già un allegato inserito nel DB per quel TYPE (devono essere tutti singoli)

		AllegatoFascicoloSearch search = new AllegatoFascicoloSearch();
		search.setIdFascicolo(idFascicolo);
		search.setType(tipoAllegato);
		if (allegatoFascicoloService.count(search)!=0) {
		// scelgo di sovrascrivere, quindi cancello l'allegato già presente
			Long idAllegato = allegatoFascicoloService.search(search).getList().get(0).getIdAllegato();
			allegatoService.eliminaAllegato(idAllegato);
		// scelgo di sollevare errore
			//	log.error("E' già presente un allegato di tipo "+tipoAllegato+" per questo fascicolo");
			//	throw new Exception("E' già presente un allegato di tipo "+tipoAllegato+" per questo fascicolo");
		}
		return allegatoService.inserisciAllegato(Collections.singletonList(idFascicolo), tipoAllegato, file, null);
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<SelectOptionDto> validazione(FascicoloDTO fascicolo,Long idFascicolo, TipoProcedimento tipoProcedimento) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<SelectOptionDto> errori=new ArrayList<SelectOptionDto>();
		AllegatoFascicoloSearch search = new AllegatoFascicoloSearch();
		search.setIdFascicolo(idFascicolo);
		 if(StringUtil.isEmpty(fascicolo.getNumeroProvvedimento())){
			 errori.add(new SelectOptionDto(ErroriValidazioneBE.PROVVEDIMENTO_NUMEROPROVV_MANCANTE.name(),
					 messageSource.getMessage("validazioneFascicolo."+ErroriValidazioneBE.PROVVEDIMENTO_NUMEROPROVV_MANCANTE.name(), null, Locale.getDefault())));
		 }
		 if(fascicolo.getDataRilascioAutorizzazione()==null){
			 errori.add(new SelectOptionDto(ErroriValidazioneBE.PROVVEDIMENTO_DATA_AUTORIZZAZIONE_MANCANTE.name(),
					 messageSource.getMessage("validazioneFascicolo."+ErroriValidazioneBE.PROVVEDIMENTO_DATA_AUTORIZZAZIONE_MANCANTE.name(), null, Locale.getDefault())));
		 }
		 if(fascicolo.getDataRilascioAutorizzazione()!=null && fascicolo.getDataRilascioAutorizzazione().after(new Date())){
			 errori.add(new SelectOptionDto(ErroriValidazioneBE.PROVVEDIMENTO_DATA_AUTORIZZAZIONE_FUTURA.name(),
					 messageSource.getMessage("validazioneFascicolo."+ErroriValidazioneBE.PROVVEDIMENTO_DATA_AUTORIZZAZIONE_FUTURA.name(), null, Locale.getDefault())));
		 }
		 if(fascicolo.getEsito()==null){
			 errori.add(new SelectOptionDto(ErroriValidazioneBE.PROVVEDIMENTO_ESITO_OBBLIGATORIO.name(),
					 messageSource.getMessage("validazioneFascicolo."+ErroriValidazioneBE.PROVVEDIMENTO_ESITO_OBBLIGATORIO.name(), null, Locale.getDefault())));			 
		 }  
		 if(StringUtil.isEmpty(fascicolo.getRup())){
			 errori.add(new SelectOptionDto(ErroriValidazioneBE.PROVVEDIMENTO_RUP_OBBLIGATORIO.name(),
					 messageSource.getMessage("validazioneFascicolo."+ErroriValidazioneBE.PROVVEDIMENTO_RUP_OBBLIGATORIO.name(), null, Locale.getDefault())));
		 }  
		try {
			allegatoObbligatorioService.find(new AllegatoObbligatorioPK(TipoAllegato.PROVVEDIMENTO_FINALE, tipoProcedimento.name()));
			search.setType(TipoAllegato.PROVVEDIMENTO_FINALE);
			if (allegatoFascicoloService.count(search)!=1)
				{
				errori.add(new SelectOptionDto(ErroriValidazioneBE.ALLEGATO_OBBLIGATORIO_MANCANTE.name(),
						messageSource.getMessage("validazioneFascicolo."+ErroriValidazioneBE.ALLEGATO_OBBLIGATORIO_MANCANTE.name(), 
								new Object[] {TipoAllegato.PROVVEDIMENTO_FINALE.getTextValue()} 
								, Locale.getDefault())));
				//return false;
				}
		} catch (DataAccessException e) {
			log.info("L'allegato "+TipoAllegato.PROVVEDIMENTO_FINALE.name()+" non è obbligatorio");
		}
		try {
			allegatoObbligatorioService.find(new AllegatoObbligatorioPK(TipoAllegato.PARERE_MIBAC, tipoProcedimento.name()));
			search.setType(TipoAllegato.PARERE_MIBAC);
			if (allegatoFascicoloService.count(search)!=1)
				{
				errori.add(new SelectOptionDto(ErroriValidazioneBE.ALLEGATO_OBBLIGATORIO_MANCANTE.name(),
						messageSource.getMessage("validazioneFascicolo."+ErroriValidazioneBE.ALLEGATO_OBBLIGATORIO_MANCANTE.name(), 
								new Object[] {TipoAllegato.PARERE_MIBAC.getTextValue()} 
								, Locale.getDefault())));
				//return false;
				}
		} catch (DataAccessException e) {
			log.info("L'allegato "+TipoAllegato.PARERE_MIBAC.name()+" non è obbligatorio");
		}
		return errori;
		//return true;
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public Boolean cambiaTipo(long idFascicolo, long idAllegato, TipoAllegato nuovoTipo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			TipoAllegato tipoPrecedente = null;
			
				 if (nuovoTipo== TipoAllegato.PARERE_MIBAC)
				tipoPrecedente = TipoAllegato.PARERE_MIBAC_PRIVATO;
			else if (nuovoTipo== TipoAllegato.PARERE_MIBAC_PRIVATO)
				tipoPrecedente = TipoAllegato.PARERE_MIBAC;
			else if (nuovoTipo== TipoAllegato.PROVVEDIMENTO_FINALE)
				tipoPrecedente = TipoAllegato.PROVVEDIMENTO_FINALE_PRIVATO;
			else if (nuovoTipo== TipoAllegato.PROVVEDIMENTO_FINALE_PRIVATO)
				tipoPrecedente = TipoAllegato.PROVVEDIMENTO_FINALE;
			else
				throw new Exception("Errore. Il Tipo allegato non è tra quelli previsti!");
			
			AllegatoFascicoloSearch searchAF = new AllegatoFascicoloSearch();
			searchAF.setIdFascicolo(idFascicolo);
			searchAF.setType(nuovoTipo);
			if (allegatoFascicoloService.count(searchAF)!=0) {
			// scelgo di sovrascrivere, quindi cancello l'allegato già presente
				Long idAllegatoGiaPresente = allegatoFascicoloService.search(searchAF).getList().get(0).getIdAllegato();
				allegatoService.eliminaAllegato(idAllegatoGiaPresente);
			}
			return (allegatoFascicoloService.cambiaTipo(idFascicolo, idAllegato, tipoPrecedente, nuovoTipo)==1);
		} catch (Exception e) {
			throw e;
		}
	}
	
}