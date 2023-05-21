package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.AvviaPagamentoRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PagamentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.config.SportelloConfigBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaPagamentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TariffaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConfigurazioniEnteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaPagamentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TariffaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaPagamentiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.AnagraficaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IPagamentiService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.service.http.IMyPayWSClientService;
import it.eng.tz.puglia.service.http.bean.Accertamento;
import it.eng.tz.puglia.service.http.bean.AnnullaImportaDovutoRequestBean;
import it.eng.tz.puglia.service.http.bean.Capitolo;
import it.eng.tz.puglia.service.http.bean.ChiediPagatiConRicevutaRequestBean;
import it.eng.tz.puglia.service.http.bean.ChiediPagatiConRicevutaResponseBean;
import it.eng.tz.puglia.service.http.bean.ImportaDovutoRequestBean;
import it.eng.tz.puglia.service.http.bean.ImportaDovutoResponseBean;
import it.eng.tz.puglia.service.http.bean.VerificaAvvisoRequestBean;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.date.DateUtil;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class PagamentiService extends PraticaDataAwareService implements IPagamentiService {

	private final static Logger LOGGER = LoggerFactory.getLogger(PagamentiService.class);

	@Autowired
	PraticaRepository praticaDao;

	@Autowired
	TariffaRepository tariffaDao;

	@Autowired
	PraticaPagamentiRepository praticaPagDao;

	@Autowired
	ReferentiRepository refDao;

	@Autowired
	AnagraficaRepository anagraficaDao;

	@Autowired
	ConfigurazioniEnteRepository confEntiDao;

	@Autowired
	IConfigurazioneService confSvc;
	
	@Autowired
	private IMyPayWSClientService clientMyPay;
	
	@Autowired
	PagamentiService pagSvc; //me stesso per attivare transazione su metodo di aggiornamento pagamento
	
	/**
	 * Url di redirect per mypay
	 */
	@Value("${urlRedirect.mypay}")
	private String urlRedirectMyPay;

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_WRITE)
	public PagamentoDto avviaPagamento(String idPratica, AvviaPagamentoRequestBean requestBean) throws Exception {
		final StopWatch sw = LogUtil.startLog("avviaPagamento ", idPratica);
		LOGGER.info("Start avviaPagamento {}", idPratica);
		try {
			
			PraticaDTO pratica = this.praticaDao.find(UUID.fromString(idPratica));
			//Controllo che la data di scandeza sia oggi + giorni configurati nell'apposita tabella
			this.checkDataScadenza(requestBean.getDataScadenza(), pratica);
			this.checkStatoForUpdate(pratica);
			this.checkDiMiaCompetenza(pratica);
			final PagamentoDto dto = this.getPagamento(pratica);
			if (dto != null)
				throw new CustomOperationToAdviceException("Pagamento esistente");

			if (requestBean.getDataScadenza() == null)
				throw new CustomValidationException("Data Scadenza è obbligatorio");
			if (requestBean.getImportoDiProgetto() < 0)
				throw new CustomValidationException("Importo di progetto è obbligatorio");
			LOGGER.info("Start Creazione Importo");
			ImportoETariffa impETariffa=this.calcolaOneriETariffa(Integer.parseInt(pratica.getEnteDelegato()),
					pratica.getTipoProcedimento(), requestBean.getImportoDiProgetto());
			LOGGER.info("Start Call mypay");
			final ImportaDovutoRequestBean request = new ImportaDovutoRequestBean();
			request.setDataEsecuzionePagamento(requestBean.getDataScadenza());
			request.setImporto(impETariffa.getImporto());
			this.setImportaDovuti(request, pratica);
			final ImportaDovutoResponseBean response = this.clientMyPay.creadDovuto(request);
			LOGGER.info("Start Insert on db");
			final PraticaPagamentiDTO dtoNew = new PraticaPagamentiDTO();
			dtoNew.setDataScadenza(requestBean.getDataScadenza());
			dtoNew.setImportoPagamento(request.getImporto());
			dtoNew.setImportoProgetto(requestBean.getImportoDiProgetto());
			dtoNew.setUrlPdf(response.getUrlDownload());
			dtoNew.setIud(request.getIud());
			dtoNew.setIuv(response.getIuv());
			dtoNew.setCausale(request.getCausale());
			dtoNew.setIdPratica(pratica.getId());
			dtoNew.setIdTariffa(impETariffa.getTariffa().getId());
			dtoNew.setDeleted(false);
			this.praticaPagDao.insert(dtoNew);
			return PagamentoDto.buildFromPraticaPagamento(dtoNew);
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	private PagamentoDto getPagamento(PraticaDTO pratica) {
		PagamentoDto pagDto = null;
		PraticaPagamentiDTO pagPrat = this.getPagamentoPratica(pratica);
		if(pagPrat!=null) {
			pagDto=PagamentoDto.buildFromPraticaPagamento(pagPrat);
		}
		return pagDto;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_READ)
	public PraticaPagamentiDTO getPagamentoPraticaWithoutCheck(PraticaDTO pratica) {
		return this.getPagamentoPratica(pratica);
	}
	
	private PraticaPagamentiDTO getPagamentoPratica(PraticaDTO pratica) {
		PraticaPagamentiDTO pagDto = null;
		PraticaPagamentiSearch pps = new PraticaPagamentiSearch();
		pps.setIdPratica(pratica.getId().toString());
		pps.setDeleted("false");
		PaginatedList<PraticaPagamentiDTO> pratPags = this.praticaPagDao.search(pps);
		if (ListUtil.isNotEmpty(pratPags.getList())) {
			pagDto = pratPags.getList().get(0);
		}
		return pagDto;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_READ)
	public PagamentoDto getPagamento(String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("getPagamento ", idPratica);
		LOGGER.info("Start getPagamento {}", idPratica);
		try {
			PraticaDTO pratica = this.praticaDao.find(UUID.fromString(idPratica));
			//Accesso in lettura per dare visualizzazione anche ad altri titolari (non owner)
			this.checkDiMiaCompetenzaInLettura(pratica);
			final  PraticaPagamentiDTO pagamento = this.getPagamentoPratica(pratica);
			if (pagamento != null && pagamento.getPagato() == false)
				this.verificaPagamento(pagamento,Integer.parseInt(pratica.getEnteDelegato()));
			return PagamentoDto.buildFromPraticaPagamento(pagamento);
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_READ)
	public PagamentoDto getPagamentoByCodPraticaAppptr(String codicePraticaAppptr) throws Exception {
		final StopWatch sw = LogUtil.startLog("getPagamentoByCodPraticaAppptr ", codicePraticaAppptr);
		LOGGER.info("Start getPagamentoByCodPraticaAppptr {}", codicePraticaAppptr);
		try {
			PraticaDTO pratica = this.praticaDao.findByCodice(codicePraticaAppptr);
			return this.getPagamento(pratica.getId().toString());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_READ)
	public boolean verificaPagamento(String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("verificaPagamento ", idPratica);
		LOGGER.info("Start verificaPagamento {}", idPratica);
		try {
			PraticaDTO pratica = this.praticaDao.find(UUID.fromString(idPratica));
			this.checkStatoForUpdate(pratica);
			this.checkDiMiaCompetenzaInLettura(pratica);
			PraticaPagamentiDTO pagamento = this.getPagamentoPratica(pratica);
			if (pagamento == null)
				throw new CustomOperationToAdviceException("Pagamento non esistente");
			if(!pagamento.getPagato()==true) {
				final ChiediPagatiConRicevutaResponseBean result = this.verificaPagamento(pagamento,Integer.parseInt(pratica.getEnteDelegato()));
				return result.isEsito();
			}else {
				return true;
			}
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void annullaPagamento(String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("annullaPagamento ", idPratica);
		LOGGER.info("Start annullaPagamento {}", idPratica);
		try {
			PraticaDTO pratica = this.praticaDao.find(UUID.fromString(idPratica));
			this.checkStatoForUpdate(pratica);
			this.checkDiMiaCompetenza(pratica);
			final PagamentoDto dto = this.getPagamento(pratica);
			if(dto == null) 
				throw new CustomOperationToAdviceException("Pagamento non esistente");
			LOGGER.info("Start Call mypay");
			final AnnullaImportaDovutoRequestBean request = new AnnullaImportaDovutoRequestBean();
			request.setDataEsecuzionePagamento(dto.getDataScadenza());
			request.setImporto(dto.getImportoPagamento());
			this.setImportaDovuti(request, pratica);
			request.setIuv(dto.getIuv());
			request.setIud(dto.getIud());
			this.clientMyPay.annullaDovuto(request);
			LOGGER.info("Start Update db");
			this.praticaPagDao.annullaPagamento(dto.getId());
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}

	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_WRITE)
	public String generaUrlMyPay(String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("generaUrlMyPay ", idPratica);
		LOGGER.info("Start generaUrlMyPay {}", idPratica);
		try {
			PraticaDTO pratica = this.praticaDao.find(UUID.fromString(idPratica));
			this.checkStatoForUpdate(pratica);
			this.checkDiMiaCompetenzaInLettura(pratica);
			final PagamentoDto dto = this.getPagamento(idPratica);
			if(dto == null) 
				throw new CustomOperationToAdviceException("Pagamento non esistente");
			if(dto.isPagato())
				throw new CustomOperationToAdviceException("Pagamento già effettuato");
			if(StringUtil.isNotEmpty(dto.getUrlMyPay()))
				return dto.getUrlMyPay();
			LOGGER.info("Start Call mypay");
			final VerificaAvvisoRequestBean request = new VerificaAvvisoRequestBean();
			request.setIuv(dto.getIuv());
			request.setUrlRedirect(this.urlRedirectMyPay.replace("{pratica}", pratica.getCodicePraticaAppptr()));
			final ConfigurazioniEnteDTO configurazionePagamenti = this
					.getConfigurazioneEnte(Integer.parseInt(pratica.getEnteDelegato()));
			request.setPassword(CryptUtil.encrypt(configurazionePagamenti.getPagamentoPassword()));
			String urlMyPay =null;
			try {
				urlMyPay =  this.clientMyPay.getUrlMyPay(request);
			}catch(Exception e) {
				LOGGER.error("Errore nella getUrlMypay",e);
				throw new CustomOperationToAdviceException("Errore durante la comunicazione con il servizio MyPay per prelevare il link di pagamento. Riprovare più tardi.");
			}
			LOGGER.info("Start Update db");
			this.praticaPagDao.salvaUrlMyPay(dto.getId(), urlMyPay);
			return urlMyPay;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_READ)
	public void downloadRicevuta(String idPratica, HttpServletResponse response) throws Exception {
		final StopWatch sw = LogUtil.startLog("downloadRicevuta ", idPratica);
		LOGGER.info("Start downloadRicevuta {}", idPratica);
		try {
			final PagamentoDto dto = this.getPagamento(idPratica);
			if(dto == null) 
				throw new CustomOperationToAdviceException("Pagamento non esistente");
			response.setContentType("application/xml");
			response.setHeader("Content-disposition", StringUtil.concateneString("attachment; filename=\"Ricevuta Pagamento.xml\""));
			try(PrintWriter pw = response.getWriter()){
				pw.write(dto.getRicevuta());
			}
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}

	}

	private ConfigurazioniEnteDTO getConfigurazioneEnte(Integer idOrganizzazione)
			throws CustomOperationToAdviceException {
		ConfigurazioniEnteDTO confEnte = new ConfigurazioniEnteDTO();
		confEnte.setIdOrganizzazione(idOrganizzazione);
		try {
			confEnte = this.confEntiDao.find(confEnte);
		} catch (Exception e) {
			LOGGER.error("Errore nella find della configurazione dell'ente con id: " + confEnte.getIdOrganizzazione(),
					e);
			throw new CustomOperationToAdviceException(
					"L'ente delegato intestatrio della pratica non ha l'integrazione con mypay!!!!");
		}
		if (!confEnte.isSistemaPagamento() || StringUtil.isBlank(confEnte.getPagamentoCodEnte())
				|| StringUtil.isBlank(confEnte.getPagamentoPassword())) {
			LOGGER.error("Errore nella configurazione pagamenti dell'ente con id: " + confEnte.getIdOrganizzazione());
			throw new CustomOperationToAdviceException(
					"L'ente delegato intestatrio della pratica non ha l'integrazione con mypay!!!!");
		}
		return confEnte;
	}

	private void setImportaDovuti(final ImportaDovutoRequestBean bean, final PraticaDTO pratica) throws Exception {
		final String data = new SimpleDateFormat("yyyyMMdd_HHmmss").format(DateUtil.currentTimestamp());
		final String codice = pratica.getCodicePraticaAppptr();
		ReferentiDTO proponente = refDao.selectRichiedente(pratica.getId(), "SD");
		if(!pratica.getValidazioneIstanza())
			throw new CustomOperationToAdviceException("Validare l'istanza prime di generare il pagamento, i dati del proponente verranno utilizzati come mittente pagatore!");

		final ConfigurazioniEnteDTO configurazionePagamenti = this
				.getConfigurazioneEnte(Integer.parseInt(pratica.getEnteDelegato()));
		bean.setCfPagatore(proponente.getCodiceFiscale());
		bean.setNominativoPagatore(
				new StringBuilder(proponente.getNome())
				.append(" ").append(proponente.getCognome()).toString());
		bean.setEmailPagatore(proponente.getMail());
		bean.setIndirizzoPagatore(proponente.getIndirizzoResidenza());
		bean.setCivicoPagatore(proponente.getCivicoResidenza());
		bean.setCapPagatore(proponente.getCapResidenza());
		bean.setLocalitaPagatore(
				StringUtil.isNotBlank(proponente.getComuneResidenzaName()) ? proponente.getComuneResidenzaName()
						: proponente.getComuneResidenzaEstero());
		if (StringUtil.isNotBlank(proponente.getProvinciaResidenzaName())) {
			bean.setProvinciaPagatore(this.anagraficaDao.findSiglaProvinciaById(proponente.getProvinciaResidenza()));
		}
		bean.setNazionePagatore(this.anagraficaDao.findSiglaNazioneById(proponente.getNazionalitaNascita()));
		if(StringUtil.isEmpty(configurazionePagamenti.getPagamentoRegexIud())){
			throw new CustomOperationToAdviceException("Errore nella configurazione del sistema di pagamento, impossibile procedere!");
		}
		bean.setIud(configurazionePagamenti.getPagamentoRegexIud().replace("{DATA}", data).replace("{CODICE}", codice));
		bean.setCommissioneCaricoPa(configurazionePagamenti.getPagamentoCommissione());
		bean.setIdentificativoTipoDovuto(configurazionePagamenti.getPagamentoTipologia());
		bean.setDatiSpecificiRiscossione(configurazionePagamenti.getPagamentoTipoRiscossione());// Di default si imposta
																								// a 9/COD TIPO DOVUTO
		
		bean.setPassword(CryptUtil.encrypt(configurazionePagamenti.getPagamentoPassword()));
		bean.setCausale("Oneri Istruttori pratica paes. "+codice);
		if(configurazionePagamenti.getBilancio() != null) {
			bean.setCapitoli(Arrays.asList((JsonUtil.toBean(configurazionePagamenti.getBilancio(), Capitolo[].class))));
			for(Capitolo capitolo : bean.getCapitoli()) {
				capitolo.setAccertamento(new ArrayList<Accertamento>());
				Accertamento acc = new Accertamento();
				acc.setImporto(BigDecimal.valueOf(bean.getImporto()));
				capitolo.getAccertamento().add(acc);
			}
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void aggiornaPagato(Long idPagamento,String ricevuta) {
		this.praticaPagDao.aggiornaPagamentoATrue(idPagamento,ricevuta);
	}
	
	private ChiediPagatiConRicevutaResponseBean verificaPagamento(PraticaPagamentiDTO dto,Integer idOrganizzazione) throws Exception {
		LOGGER.info("verificaPagamento con mypay");
		final ChiediPagatiConRicevutaRequestBean request = new ChiediPagatiConRicevutaRequestBean();
		request.setIuv(dto.getIuv());
		final ConfigurazioniEnteDTO configurazionePagamenti = this
				.getConfigurazioneEnte(idOrganizzazione);
		request.setPassword(CryptUtil.encrypt(configurazionePagamenti.getPagamentoPassword()));
		final ChiediPagatiConRicevutaResponseBean result = this.clientMyPay.verificaPagamento(request);
		LOGGER.info("Result {}", result);
		if(result.isEsito())
		{
			pagSvc.aggiornaPagato(dto.getId(), result.getRicevuta());
		}
		return result;
	}
	
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, transactionManager = DatabaseConfiguration.TX_READ)
	public double calcolaOneri(Integer idEnteDelegato,Integer tipoProcedimento,double importoProgetto) {
		ImportoETariffa impEtariffa = calcolaOneriETariffa(idEnteDelegato, tipoProcedimento, importoProgetto);
		return impEtariffa.getImporto();
	}

	private ImportoETariffa calcolaOneriETariffa(Integer idEnteDelegato, Integer tipoProcedimento, double importoProgetto) {
		ImportoETariffa importoETariffa=new ImportoETariffa();
		TariffaDTO tariffa = tariffaDao.getTariffaByImportoTipoProcOrganizzazione(
				idEnteDelegato, tipoProcedimento,
				importoProgetto, new Date());
		importoETariffa.setTariffa(tariffa);
		double importo = importoProgetto;
		// 1) Si verifica se eliminare eccedente
		if (tariffa.getDeleteEccedente())
			importo = importo - tariffa.getSogliaMinima();
		// 2) Si applica percentuale
		importo = importo * (tariffa.getPercentuale() / 100);
		// 3) Si aggiunge cifra minima
		importo = importo + tariffa.getCifraDaAggiungere();
		importo = importo * (tariffa.getPercentualeFinale() / 100);
		importoETariffa.setImporto(importo);
		return importoETariffa;
	}
	
	/**
	 * Metodo che verifica che la data di scadenza ricevuta in input rispetti i giorni configurati a partire da oggi
	 * @author Giuseppe Canciello
	 * @date 16 giu 2022
	 * @param dataScadenza
	 * @param pratica
	 * @throws CustomValidationException
	 */
	private void checkDataScadenza(Date dataScadenza, PraticaDTO pratica) throws CustomValidationException {
		LocalDate dataGiusta= LocalDate.now();
		LocalDate dataRicevuta=new java.sql.Date(dataScadenza.getTime()).toLocalDate();
		try {
			SportelloConfigBean configBean=confSvc.findConfigurazioneCorrente(pratica.getDataCreazione(), SportelloConfigBean.class);
			dataGiusta=dataGiusta.plusDays(configBean.getGiorniScadenzaPagamento());
		} catch (Exception e) {
			throw new CustomValidationException("Problemi durante recupero configurazione scadenza");
		}
		
		if (!dataRicevuta.equals(dataGiusta)) {
			throw new CustomValidationException("La data di scadenza non è valida");
		}
	}
	

	private class ImportoETariffa{
		TariffaDTO tariffa;
		double importo;
		/**
		 * @return the tariffa
		 */
		public TariffaDTO getTariffa() {
			return tariffa;
		}
		/**
		 * @param tariffa the tariffa to set
		 */
		public void setTariffa(TariffaDTO tariffa) {
			this.tariffa = tariffa;
		}
		/**
		 * @return the importo
		 */
		public double getImporto() {
			return importo;
		}
		/**
		 * @param importo the importo to set
		 */
		public void setImporto(double importo) {
			this.importo = importo;
		}
		
	}
	
}
