package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.TipologicaIntegerBooleanDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConfigurazioniEnteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ConfigurazioniEnteSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioniEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.webmail.DynamicKafkaConsumer;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.webmail.service.WebmailService;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.service.http.bean.Capitolo;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.CasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigMailInDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigMailOutDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.enumerations.MailInEnum;
import it.eng.tz.regione.puglia.webmail.be.enumerations.MailOutEnum;

/**
 * Service CRUD for table presentazione_istanza.ConfigurazioniEnte
 */
@Service
public class ConfigurazioniEnteService
		extends GenericCrudService<ConfigurazioniEnteDTO, ConfigurazioniEnteSearch, ConfigurazioniEnteRepository>
		implements IConfigurazioniEnteService {

	@Autowired
	UserUtil userUtil;

	/**
	 * dao
	 */
	@Autowired
	private ConfigurazioniEnteRepository dao;

	@Autowired
	private WebmailService webmailService;

	@Autowired
	private DynamicKafkaConsumer dk;

	/**
	 * get dao
	 */
	@Override
	protected ConfigurazioniEnteRepository getDao() {
		return this.dao;
	}

	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_WRITE, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public ConfigurazioniEnteDTO inserisciOaggiorna(ConfigurazioniEnteDTO dto) throws Exception {
		dto.setIdOrganizzazione(userUtil.getIntegerId());
		this.valida(dto);
		this.ripulisci(dto);
		try {
			dto.setProtocollazionePassword(CryptUtil.encrypt(dto.getProtocollazionePassword()));
			dto.setPecPassword(CryptUtil.encrypt(dto.getPecPassword()));
			dto.setPagamentoPassword(CryptUtil.encrypt(dto.getPagamentoPassword()));
			//verifico che il campo indirizzi mail di default abbia al suo interno delle mail corrette
		    if(dto.getIndirizziMailDefault()!= null && !dto.getIndirizziMailDefault().isEmpty()) {
		    	//Verifico che non ci siano spazi
		    	if(dto.getIndirizziMailDefault().contains(" "))
		    		throw new Exception("Formattazione non corretta! La stringa contiene spazi");
		    	//Controllo la validità delle singole mail
		    	String[] verificaMail = dto.getIndirizziMailDefault().split(",");
		    	for (String mail : verificaMail) {
					if(!StringUtil.isEmail(mail))
						throw new Exception("Inserire delle mail valide!");
				}
		    	//aggiorno la stringa corretta
		    	String newMailDefault="";
		    	for (int i=0; i<verificaMail.length; i++) {
					newMailDefault = newMailDefault.concat(verificaMail[i]);
					if(i<verificaMail.length-1)
						newMailDefault = newMailDefault.concat(",");
				}
		    	dto.setIndirizziMailDefault(newMailDefault);
		    }
		    
			dao.find(dto);
			this.aggiorna(dto);
			dto=dao.find(dto);
			this.inviaConfigurazioneEmail(dto);
			this.dk.restart();
		} catch (DataAccessException e) {
			logger.info("Configurazione non ancora esistente per l'Ente");
			this.inserisci(dto);
		} catch (Exception e1) {
			throw e1;
		}
		return dto;
	}

	private void inviaConfigurazioneEmail(ConfigurazioniEnteDTO dto) throws CustomOperationToAdviceException {
		String oldAuth = LogUtil.getAuthorization();
		//tolgo l'autorizzazione perchè per l'utilizzo del webmail non deve esistere, altrimenti mi da 403
		LogUtil.addAuthorization(null);
		try {
			
			// controllo se per caso non esiste già una configurazione attiva, nel caso
			// invoco il webmail per disattivarla.
			try {
				ConfigurazioniEnteDTO oldConfig = dao.find(dto);
				if (oldConfig.isSistemaPec()) {
					ConfigurazioneCasellaPostaleDto ccpdOld = new ConfigurazioneCasellaPostaleDto();
					buildCasellaConfig(oldConfig, ccpdOld);
					ccpdOld.getCasellaPostale().setCasellaAttiva(false);
					this.webmailService.configuraEAttivaCasellaPostale(ccpdOld);
				}
			} catch (EmptyResultDataAccessException e) {}
			if (!dto.isSistemaPec()) // nessuna nuova configurazione
				return;
			ConfigurazioneCasellaPostaleDto ccpd = new ConfigurazioneCasellaPostaleDto();
			buildCasellaConfig(dto, ccpd);
			ccpd.getCasellaPostale().setCasellaAttiva(false);
			this.webmailService.configuraEAttivaCasellaPostale(ccpd);
		} catch (CustomOperationToAdviceException e) {
			throw e;
		}
		catch (Exception e) {
			logger.error("Errore in inviaConfigurazioneEmail",e);
			throw new CustomOperationToAdviceException("Errore durante l'attivazione dell'indirizzo PEC. Verificare che i dati inseriti siano corretti");
		}
		finally {
			LogUtil.addAuthorization(oldAuth);
		}
	}

	/**
	 * builda la configurazione casella di posta a pratire da ConfigurazioniEnteDTO
	 * 
	 * @author acolaianni
	 *
	 * @param dto
	 * @param ccpd
	 * @throws Exception
	 */
	private void buildCasellaConfig(ConfigurazioniEnteDTO dto, ConfigurazioneCasellaPostaleDto ccpd) throws Exception {
		CasellaPostaleDto casellaPostale = new CasellaPostaleDto();
		casellaPostale.setIndirizzoMail(dto.getPecIndirizzo());
		casellaPostale.setUsername(dto.getPecUsername());
		casellaPostale.setPassword(dto.getPecPassword());
		// settare a ture
		casellaPostale.setPec(true);
		casellaPostale.setNomeVisualizzato(dto.getPecNome());
		casellaPostale.setCasellaAttiva(true);
		ccpd.setCasellaPostale(casellaPostale);

		ConfigMailInDto configurazioneMailIngresso = new ConfigMailInDto();
		configurazioneMailIngresso.setHost(dto.getPecHostIn());
		configurazioneMailIngresso.setImap(dto.getPecTipoProtocolloIn().contains("IMAP"));
		if (dto.getPecPortaTlsIn() != null) {
			configurazioneMailIngresso.setPorta(dto.getPecPortaTlsIn());
		} else {
			configurazioneMailIngresso.setPorta(dto.getPecPortaSslIn());
		}
		configurazioneMailIngresso.setRichiedeSsl(dto.getPecSslIn());
		configurazioneMailIngresso.setRichiedeStarttsl(dto.getPecStartTlsIn());
		configurazioneMailIngresso.setProtocollo(MailInEnum.valueOf(dto.getPecTipoProtocolloIn()));
		configurazioneMailIngresso.setRichiedeAutenticazione(dto.getPecAutenticazioneIn());
		ccpd.setConfigurazioneMailIngresso(configurazioneMailIngresso);

		ConfigMailOutDto configurazioneMailUscita = new ConfigMailOutDto();
		configurazioneMailUscita.setHost(dto.getPecHostOut());
		configurazioneMailUscita.setPortaTls(dto.getPecPortaTlsOut());
		configurazioneMailUscita.setPortaSsl(dto.getPecPortaSslOut());
		configurazioneMailUscita.setProtocollo(MailOutEnum.valueOf(dto.getPecTipoProtocolloOut()));
		configurazioneMailUscita.setRichiedeSsl(dto.getPecSslOut());
		configurazioneMailUscita.setRichiedeStarttsl(dto.getPecTlsOut());
		configurazioneMailUscita.setRichiedeAutenticazione(dto.getPecAutenticazioneOut());
		ccpd.setConfigurazioneMailUscita(configurazioneMailUscita);
	}

	// @Transactional(transactionManager=DatabaseConfiguration.TX_WRITE,
	// propagation=Propagation.MANDATORY, rollbackFor={Exception.class},
	// timeout=60000, readOnly=false)
	private ConfigurazioniEnteDTO inserisci(ConfigurazioniEnteDTO dto) throws Exception {
		this.valida(dto);
		this.ripulisci(dto);
		try {
			long res = dao.insert(dto);
			if (res != 1)
				throw new Exception();
		} catch (Exception e) {
			throw new Exception("Errore nell'inserimento delle Configurazioni per l'Ente", e);
		}
		return dto;
	}

	// @Transactional(transactionManager=DatabaseConfiguration.TX_WRITE,
	// propagation=Propagation.MANDATORY, rollbackFor={Exception.class},
	// timeout=60000, readOnly=false)
	private ConfigurazioniEnteDTO aggiorna(ConfigurazioniEnteDTO dto) throws Exception {
		this.valida(dto);
		this.ripulisci(dto);
		try {
			long res = dao.update(dto);
			if (res != 1)
				throw new Exception();
		} catch (Exception e) {
			throw new Exception("Errore nell'aggiornamento delle Configurazioni per l'Ente", e);
		}
		return dto;
	}

	/*
	 * @Transactional(propagation=Propagation.REQUIRED,
	 * rollbackFor={Exception.class}, timeout=60000, readOnly=false) public void
	 * elimina(int idOrganizzazione) throws Exception { ConfigurazioniEnteDTO dto =
	 * new ConfigurazioniEnteDTO(); dto.setIdOrganizzazione(idOrganizzazione); try {
	 * int res = dao.delete(dto); if (res!=1) throw new Exception(); } catch
	 * (Exception e) { throw e; } }
	 */
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, noRollbackFor = DataAccessException.class, timeout = 60000, readOnly = true)
	public ConfigurazioniEnteDTO find(Integer idOrganizzazione) throws Exception {
		return doFindByIdOrganizzazione(idOrganizzazione);
	}

	private ConfigurazioniEnteDTO doFindByIdOrganizzazione(Integer idOrganizzazione) throws Exception {
		ConfigurazioniEnteDTO dto = new ConfigurazioniEnteDTO();
		dto.setIdOrganizzazione(idOrganizzazione);
		try {
			return this.ripulisciPerFE(dao.find(dto));
		} catch (DataAccessException e) {
			return dto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = DataAccessException.class, timeout = 60000, readOnly = true)
	public Boolean showVerbaleCommissionelocale(Long enteDelegato, Integer tipoProcedimento) throws Exception
	{
		List<Integer> defaults = Arrays.asList(1,2);
		boolean b = defaults.contains(tipoProcedimento);
		try
		{
			List<Integer> tpv = dao.getTPConVerbale(enteDelegato);
			if(tpv != null)
				b = tpv.contains(tipoProcedimento);
		}catch(DataAccessException e)
		{
			//nulla
		}
		return b;
	}
	
	@Override
	@Transactional(transactionManager = DatabaseConfiguration.TX_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = DataAccessException.class, timeout = 60000, readOnly = true)
	public List<Integer> tipologieParereCommissioneLocale(long idOrganizzazione) throws Exception
	{
		return dao.getTPConVerbale(idOrganizzazione);
	}
	

	private ConfigurazioniEnteDTO ripulisciPerFE(ConfigurazioniEnteDTO dto) {
		if (dto.getPecProtocolloIn() == null)
			dto.setPecProtocolloIn(false);
		if (dto.getPecSslIn() == null)
			dto.setPecSslIn(false);
		if (dto.getPecSslOut() == null)
			dto.setPecSslOut(false);
		if (dto.getPecTlsOut() == null)
			dto.setPecTlsOut(false);
		if (dto.getPecStartTlsIn() == null)
			dto.setPecStartTlsIn(false);
		if (dto.getPecStartTlsOut() == null)
			dto.setPecStartTlsOut(false);
		if (dto.getPecAutenticazioneIn() == null)
			dto.setPecAutenticazioneIn(false);
		if (dto.getPecAutenticazioneOut() == null)
			dto.setPecAutenticazioneOut(false);
		return dto;
	}
	
	@Override
	public List<TipologicaIntegerBooleanDto> visibilitaStatoAvanzamento(Set<Integer> idOrganizzazioni) throws Exception {
		try {
			return dao.visibilitaStatoAvanzamento(new HashSet<Integer>(idOrganizzazioni));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Deprecated
	protected void validateInsertDTO(ConfigurazioniEnteDTO entity) throws CustomValidationException {
		logger.error("Questo metodo non dovrebbe essere mai richiamato!");
	}

	@Override
	@Deprecated
	protected void validateUpdateDTO(ConfigurazioniEnteDTO entity) throws CustomValidationException {
		logger.error("Questo metodo non dovrebbe essere mai richiamato!");
	}

	private void valida(ConfigurazioniEnteDTO dto) throws Exception {
		if (dto.getIdOrganizzazione() <= 0)
			throw new Exception("Errore nella validazione della Configurazione dell'Ente (A1)");
		if (dto.isSistemaProtocollazione() == true) {
			if (StringUtil.isEmpty(dto.getProtocollazioneAdministration()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (B1)");
			if (StringUtil.isEmpty(dto.getProtocollazioneAoo()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (B2)");
			if (StringUtil.isEmpty(dto.getProtocollazioneEndpoint()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (B3)");
			if (StringUtil.isEmpty(dto.getProtocollazioneHashAlgorithm()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (B4)");
			if (StringUtil.isEmpty(dto.getProtocollazionePassword()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (B5)");
			if (StringUtil.isEmpty(dto.getProtocollazioneRegister()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (B6)");
			if (StringUtil.isEmpty(dto.getProtocollazioneUser()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (B7)");
		}
		if (dto.isSistemaPagamento() == true) {
			if (StringUtil.isEmpty(dto.getPagamentoTipologia()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (C1)");
			if (StringUtil.isEmpty(dto.getPagamentoCodEnte()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (C2)");
			if (StringUtil.isEmpty(dto.getPagamentoTipoRiscossione()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (C3)");
			if (StringUtil.isEmpty(dto.getPagamentoPassword()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (C4)");
			if (StringUtil.isEmpty(dto.getPagamentoEndPoint()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (C5)");
			if (StringUtil.isEmpty(dto.getPagamentoRegexIud()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (C6)");
			try {
				if (!StringUtil.isEmpty(dto.getBilancio()))
					JsonUtil.toBean(dto.getBilancio(), Capitolo[].class);
			} catch (Exception e) {
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (C7)");
			}
		}
		if (dto.isSistemaPec() == true) {
			if (StringUtil.isEmpty(dto.getPecHostIn()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D1)");
			if (StringUtil.isEmpty(dto.getPecHostOut()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D2)");
			if (StringUtil.isEmpty(dto.getPecIndirizzo()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D3)");
			if (StringUtil.isEmpty(dto.getPecNome()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D4)");
			if (StringUtil.isEmpty(dto.getPecPassword()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D5)");
			if (StringUtil.isEmpty(dto.getPecTipoProtocolloIn()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D6)");
			if (StringUtil.isEmpty(dto.getPecTipoProtocolloOut()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D7)");
			if (StringUtil.isEmpty(dto.getPecUsername()))
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D8)");
			if (dto.getPecAutenticazioneIn() == null)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D9)");
			if (dto.getPecAutenticazioneOut() == null)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D10)");
			if (dto.getPecProtocolloIn() == null)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D11)");
			// if (dto.getPecProtocolloOut()==null)
			// throw new Exception("Errore nella validazione della Configurazione dell'Ente
			// (D12)");
			if (dto.getPecSslIn() == null)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D13)");
			if (dto.getPecSslOut() == null)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D14)");
			if (dto.getPecStartTlsIn() == null)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D15)");
			if (dto.getPecStartTlsOut() == null)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D16)");
			// if (dto.getPecTlsIn()==null)
			// throw new Exception("Errore nella validazione della Configurazione dell'Ente
			// (D17)");
			if (dto.getPecTlsOut() == null)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D18)");
			if (dto.getPecPortaSslIn() == null || dto.getPecPortaSslIn() <= 0)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D19)");
			if (dto.getPecPortaSslOut() == null || dto.getPecPortaSslOut() <= 0)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D20)");
			// if (dto.getPecPortaTlsIn()==null || dto.getPecPortaTlsIn()<=0)
			// throw new Exception("Errore nella validazione della Configurazione dell'Ente
			// (D21)");
			if (dto.getPecPortaTlsOut() == null || dto.getPecPortaTlsOut() <= 0)
				throw new Exception("Errore nella validazione della Configurazione dell'Ente (D22)");
		}
	}

	private void ripulisci(ConfigurazioniEnteDTO dto) throws Exception {
		if (dto.isSistemaProtocollazione() == false) {
			dto.setProtocollazioneAdministration(null);
			dto.setProtocollazioneAoo(null);
			dto.setProtocollazioneEndpoint(null);
			dto.setProtocollazioneHashAlgorithm(null);
			dto.setProtocollazionePassword(null);
			dto.setProtocollazioneRegister(null);
			dto.setProtocollazioneUser(null);
		}
		if (dto.isSistemaPagamento() == false) {
			dto.setPagamentoTipologia(null);
			dto.setPagamentoCodEnte(null);
			dto.setPagamentoTipoRiscossione(null);
			dto.setPagamentoPassword(null);
			dto.setPagamentoEndPoint(null);
			dto.setPagamentoRegexIud(null);
			dto.setPagamentoCommissione(0);
		}
		if (dto.isSistemaPec() == false) {
			dto.setPecHostIn(null);
			dto.setPecHostOut(null);
			dto.setPecIndirizzo(null);
			dto.setPecNome(null);
			dto.setPecPassword(null);
			dto.setPecTipoProtocolloIn(null);
			dto.setPecTipoProtocolloOut(null);
			dto.setPecUsername(null);
			dto.setPecAutenticazioneIn(null);
			dto.setPecAutenticazioneOut(null);
			dto.setPecProtocolloIn(null);
			dto.setPecProtocolloOut(null);
			dto.setPecSslIn(null);
			dto.setPecSslOut(null);
			dto.setPecStartTlsIn(null);
			dto.setPecStartTlsOut(null);
			dto.setPecTlsIn(null);
			dto.setPecTlsOut(null);
			dto.setPecPortaSslIn(null);
			dto.setPecPortaSslOut(null);
			dto.setPecPortaTlsIn(null);
			dto.setPecPortaTlsOut(null);
		}
	}

}