package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.ResponsabileDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.enumeratori.Ditta;
import it.eng.tz.puglia.autpae.enumeratori.ErroriValidazioneBE;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.repository.base.RichiedenteBaseRepository;
import it.eng.tz.puglia.autpae.search.AllegatoFascicoloSearch;
import it.eng.tz.puglia.autpae.search.ResponsabileSearch;
import it.eng.tz.puglia.autpae.search.RichiedenteSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoFascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.ResponsabileService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiedenteService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class RichiedenteServiceImpl implements RichiedenteService {

	private static final Logger log = LoggerFactory.getLogger(RichiedenteServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private RichiedenteBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<RichiedenteDTO> select() 					  	throws Exception { return repository.select(); 	   	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						   count (RichiedenteSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   RichiedenteDTO  find  (Long pk) 			  		throws Exception { return repository.find  (pk); 	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Long 						   insert(RichiedenteDTO entity) 	throws Exception { return repository.insert(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						   update(RichiedenteDTO entity)	throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						   delete(RichiedenteSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<RichiedenteDTO> search(RichiedenteSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
	@Autowired  private ApplicationProperties props;
	
	@Autowired	private FascicoloService fascicoloService;
	
	@Autowired	private AllegatoService allegatoService;
	
	@Autowired	private AllegatoFascicoloService allegatoFascicoloService;
	
	@Autowired	private ResponsabileService responsabileService;
	
	@Autowired	private CorrispondenzaService corrispondenzaService;
	
	//@Autowired  private Validator validator;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, timeout=60000, readOnly=true, rollbackFor=Exception.class)
	public RichiedenteDTO datiRichiedente(Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		RichiedenteDTO richiedente = new RichiedenteDTO();
		
		RichiedenteSearch search = new RichiedenteSearch();
		search.setIdFascicolo(idFascicolo);
		List<RichiedenteDTO> listaRichiedenti = repository.search(search).getList();
		if (!listaRichiedenti.isEmpty()) {
			richiedente = listaRichiedenti.get(0);
			richiedente.setDitta(richiedente.isAditta());
		}
		if (props.isPareri()) {
			ResponsabileSearch searchR = new ResponsabileSearch();
			searchR.setIdFascicolo(idFascicolo);
			ResponsabileDTO responsabile = null;
			List<ResponsabileDTO> listaResp = responsabileService.search(searchR).getList();
			if (listaResp!=null && !listaResp.isEmpty()) {
				if (listaResp.size()>1) {
					throw new Exception("ERRORE. Più di un responsabile associato al fascicolo!");
				}
				responsabile = listaResp.get(0);
			}
			if (responsabile!=null) {
				AllegatoFascicoloSearch searchAF = new AllegatoFascicoloSearch(null, TipoAllegato.DOCUMENTO_RICONOSCIMENTO, idFascicolo);
				List<AllegatoFascicoloDTO> listaAF = allegatoFascicoloService.search(searchAF).getList();
				if (listaAF!=null && !listaAF.isEmpty()) {
					if (listaAF.size()>1) {
						throw new Exception("ERRORE. Più di un Documento di Riconoscimento del responsabile associato al fascicolo!");
					}
					responsabile.setDocumentoRiconoscimento(allegatoService.infoAllegato(listaAF.get(0).getIdAllegato()));
				}
			}
			richiedente.setResponsabile(responsabile);
		}
		return richiedente;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public Long inserisci(RichiedenteDTO richiedente) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.insert(richiedente);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public void salva(RichiedenteDTO richiedente) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		repository.update(richiedente);
	}
	
	@Override
	public List<ErroriValidazioneBE> validazione(RichiedenteDTO richiedente, long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//		Set<ConstraintViolation<RichiedenteDTO>> violazioni = validator.validate(richiedente, RichiedenteDTO.class);
//		if(violazioni!=null && violazioni.size()>0) {
//			violazioni.forEach(violazione->{
//				//TODO
//			});
//		}
		List<ErroriValidazioneBE> errori = new ArrayList<ErroriValidazioneBE>();
		
		// controllo se è una persona fisica oppure una ditta 
		short countDitta = 0;
		if (StringUtil.isNotEmpty(richiedente.getDittaSocieta())) 		countDitta++;
		if (StringUtil.isNotEmpty(richiedente.getDittaInQualitaDi())) 	countDitta++;
		if (StringUtil.isNotEmpty(richiedente.getDittaCodiceFiscale())) countDitta++;
		if (StringUtil.isNotEmpty(richiedente.getDittaPartitaIva())) 	countDitta++;
		if (countDitta!=0 && countDitta!=4)   errori.add(ErroriValidazioneBE.RICHIEDENTE_DATI_DITTA);
		
		//controllo che il valore della ditta sia idoneo se presente
		if(richiedente.isDitta() && !EnumUtils.isValidEnum(Ditta.class, richiedente.getDittaInQualitaDi())) 
			errori.add(ErroriValidazioneBE.RICHIEDENTE_DITTA_NON_CONFORME);
		
		if (richiedente.isDitta() && (richiedente.getDittaInQualitaDi()!=null) && (richiedente.getDittaInQualitaDi().toLowerCase().startsWith("altro")) && (StringUtil.isEmpty(richiedente.getDittaQualitaAltro())))	errori.add(ErroriValidazioneBE.RICHIEDENTE_DITTA_ALTRO);
		
		// verifico la consistenza dei valori inseriti
		if (StringUtil.isEmpty	 (richiedente.getNome())) 																								 	    errori.add(ErroriValidazioneBE.RICHIEDENTE_NOME);
		if (StringUtil.isEmpty	 (richiedente.getCognome())) 																							 	    errori.add(ErroriValidazioneBE.RICHIEDENTE_COGNOME);
		if (					  richiedente.getDataNascita()!=null   && this				   .isMaggiorenne     (richiedente.getDataNascita())       ==false)	errori.add(ErroriValidazioneBE.RICHIEDENTE_DATA_NASCITA);
		if (StringUtil.isEmpty	 (richiedente.getCodiceFiscale()))																								errori.add(ErroriValidazioneBE.RICHIEDENTE_CF_VUOTO);
		if (StringUtil.isNotEmpty(richiedente.getCodiceFiscale()) &&   	  fascicoloService	   .checkCodiceFiscale(richiedente.getCodiceFiscale())	   !=null ) errori.add(ErroriValidazioneBE.RICHIEDENTE_CF);
		if (StringUtil.isNotEmpty(richiedente.getDittaCodiceFiscale()) && fascicoloService	   .checkCodiceFiscale(richiedente.getDittaCodiceFiscale())!=null ) errori.add(ErroriValidazioneBE.RICHIEDENTE_DITTA_CF);
		if (StringUtil.isNotEmpty(richiedente.getDittaPartitaIva())    && fascicoloService	   .checkPartitaIVA   (richiedente.getDittaPartitaIva())   !=null ) errori.add(ErroriValidazioneBE.RICHIEDENTE_DITTA_P_IVA);
		if (StringUtil.isNotEmpty(richiedente.getCap()) 			   && fascicoloService	   .checkCAP		  (richiedente.getCap())			   !=null ) errori.add(ErroriValidazioneBE.RICHIEDENTE_CAP);
		if (StringUtil.isEmpty   (richiedente.getPec()))																									    errori.add(ErroriValidazioneBE.RICHIEDENTE_PEC);
		if (										  	 			      corrispondenzaService.checkEmail		  (richiedente.getPec())			   !=null ) errori.add(ErroriValidazioneBE.RICHIEDENTE_PEC);
		if (StringUtil.isNotEmpty(richiedente.getEmail()) 			   && corrispondenzaService.checkEmail		  (richiedente.getEmail())			   !=null ) errori.add(ErroriValidazioneBE.RICHIEDENTE_EMAIL);
		if (StringUtil.isNotEmpty(richiedente.getTelefono()) 		   && fascicoloService	   .checkTelefono	  (richiedente.getTelefono())		   !=null ) errori.add(ErroriValidazioneBE.RICHIEDENTE_TELEFONO);
		//vanno controllati i dati di nascita (se inseriti) e di residenza (se inseriti)
		if(StringUtil.isNotEmpty(richiedente.getStatoNascita()) &&
				!richiedente.getStatoNascita().equalsIgnoreCase("ITALIA") && 
				StringUtil.isNotBlank(richiedente.getProvinciaNascita())) {
			//provincia non deve essere pieno se stato<>Italia
				errori.add(ErroriValidazioneBE.PROVINCIA_NASCITA_INATTESA);
		}
		if(StringUtil.isNotEmpty(richiedente.getStatoResidenza()) &&
				!richiedente.getStatoResidenza().equalsIgnoreCase("ITALIA") && 
				StringUtil.isNotBlank(richiedente.getProvinciaResidenza())) {
			//provincia non deve essere pieno se stato<>Italia
				errori.add(ErroriValidazioneBE.PROVINCIA_RESIDENZA_INATTESA);
		}
		if (props.isPareri()) {
			if (richiedente.getResponsabile()==null)  											   errori.add(ErroriValidazioneBE.RESPONSABILE);
			if (StringUtil.isEmpty(richiedente.getResponsabile().getNome())) 					   errori.add(ErroriValidazioneBE.RESPONSABILE_NOME);
			if (StringUtil.isEmpty(richiedente.getResponsabile().getCognome())) 				   errori.add(ErroriValidazioneBE.RESPONSABILE_COGNOME);
			if (StringUtil.isEmpty(richiedente.getResponsabile().getPec())) 					   errori.add(ErroriValidazioneBE.RESPONSABILE_PEC);
			if (corrispondenzaService.checkEmail(richiedente.getResponsabile().getPec())!=null)    errori.add(ErroriValidazioneBE.RESPONSABILE_PEC);
			if (richiedente.getResponsabile().getRiconoscimentoTipo()==null) 					   errori.add(ErroriValidazioneBE.RESPONSABILE);
			if (StringUtil.isEmpty(richiedente.getResponsabile().getRiconoscimentoNumero())) 	   errori.add(ErroriValidazioneBE.RESPONSABILE);
			if (StringUtil.isEmpty(richiedente.getResponsabile().getRiconoscimentoRilasciatoDa())) errori.add(ErroriValidazioneBE.RESPONSABILE);
			if (richiedente.getResponsabile().getRiconoscimentoDataRilascio()==null) 			   errori.add(ErroriValidazioneBE.RESPONSABILE_RILASCIATO);
			if (richiedente.getResponsabile().getRiconoscimentoDataRilascio().after(new Date()))   errori.add(ErroriValidazioneBE.RESPONSABILE_RILASCIATO);
			if (richiedente.getResponsabile().getRiconoscimentoDataScadenza()==null) 			   errori.add(ErroriValidazioneBE.RESPONSABILE_SCADENZA);
			if (richiedente.getResponsabile().getRiconoscimentoDataScadenza().before(new Date()))  errori.add(ErroriValidazioneBE.RESPONSABILE_SCADENZA);
			
			AllegatoFascicoloSearch searchAF = new AllegatoFascicoloSearch(null, TipoAllegato.DOCUMENTO_RICONOSCIMENTO, idFascicolo);
			if (allegatoFascicoloService.count(searchAF)!=1)									   errori.add(ErroriValidazioneBE.RESPONSABILE_MANCA_ALLEGATO_DOCUMENTO_RICONOSCIMENTO);
		}
		else {
			if (richiedente.getResponsabile()!=null)  											   errori.add(ErroriValidazioneBE.RESPONSABILE_DATI_MANCANTI);
			
			AllegatoFascicoloSearch searchAF = new AllegatoFascicoloSearch(null, TipoAllegato.DOCUMENTO_RICONOSCIMENTO, idFascicolo);
			if (allegatoFascicoloService.count(searchAF)!=0)									   errori.add(ErroriValidazioneBE.RESPONSABILE_MANCA_ALLEGATO_DOCUMENTO_RICONOSCIMENTO);
		}
		return errori;
	}
	
	
    private boolean isMaggiorenne(Date dataNascita)
    {
    	GregorianCalendar data_nascita = new GregorianCalendar();
    	data_nascita.setTime(dataNascita);
    	
		GregorianCalendar now = new GregorianCalendar();

		int giorno = data_nascita.get(Calendar.DAY_OF_MONTH);
		int mese   = data_nascita.get(Calendar.MONTH);
		int anno   = data_nascita.get(Calendar.YEAR);

		GregorianCalendar maggioreEta = new GregorianCalendar(anno+18, mese, giorno, 0, 0, 0);
		maggioreEta.set(Calendar.MILLISECOND, 0);
		long dif = now.getTimeInMillis() - maggioreEta.getTimeInMillis();
		
		return (dif>0);
    }
    
}