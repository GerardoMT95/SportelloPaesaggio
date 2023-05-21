package it.eng.tz.puglia.autpae.utility;


import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.CampionamentoSuccessivo;
import it.eng.tz.puglia.autpae.enumeratori.EsitoProvvedimento;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;

@Component
public class DtoFactory
{
	private final Logger LOGGER = LoggerFactory.getLogger(DtoFactory.class);
	
	static TipoProcedimento getRandomTipoProcedimento()
	{
		TipoProcedimento[] tipologie = {TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004,
									   	TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010,
									   	TipoProcedimento.ACCERT_COMPAT_PAES_DPR_31_2017,
									   	TipoProcedimento.AUT_PAES_ORD,
									   	TipoProcedimento.AUT_PAES_SEMPL_DPR_139_2010,
									   	TipoProcedimento.AUT_PAES_SEMPL_DPR_31_2017};
		Random random = new Random();
		return tipologie[random.nextInt(tipologie.length)];
	}
	static StatoFascicolo getRandomStatus()
	{
		StatoFascicolo[] status = {StatoFascicolo.CANCELLED,
								   StatoFascicolo.FINISHED,
								   StatoFascicolo.ON_MODIFY,
								   StatoFascicolo.SELECTED,
								   StatoFascicolo.TRANSMITTED,
								   StatoFascicolo.WORKING};
		Random random = new Random();
		return status[random.nextInt(status.length)];
	}
	static EsitoProvvedimento getRandomEsito()
	{
		EsitoProvvedimento[] esito = {EsitoProvvedimento.AUT_CON_PRESCRIZ,
				   					   EsitoProvvedimento.AUTORIZZATO,
				   					   EsitoProvvedimento.NON_AUTORIZZATO};
		Random random = new Random();
		return esito[random.nextInt(esito.length)];
	}
//	static QualificaDPCM_2005 getRandomQualificaDPCM_2005()
//	{
//		QualificaDPCM_2005[] qualDPCM_2005 = {QualificaDPCM_2005.QualificaDPCM_2005_01,
//											  QualificaDPCM_2005.QualificaDPCM_2005_02,
//											  QualificaDPCM_2005.QualificaDPCM_2005_03};
//		Random random = new Random();
//		return qualDPCM_2005[random.nextInt(qualDPCM_2005.length)];
//	}
//	static TipoIntervento getRandomTipoIntervento()
//	{
//		TipoIntervento[] tipo = {TipoIntervento.INTERVENTI_NON_EDILIZI,
//								 TipoIntervento.MANUTENZIONE_RESTAURO,
//								 TipoIntervento.NUOVA_COSTRUZIONE,
//								 TipoIntervento.RISTR_EDILIZIA,
//								 TipoIntervento.RISTR_URBANISTICA};
//		Random random = new Random();
//		return tipo[random.nextInt(tipo.length)];
//	}
	static CampionamentoSuccessivo getRandomCampionamentoSuccessivo()
	{
		CampionamentoSuccessivo[] successivi = {CampionamentoSuccessivo.PREDEFINITO, CampionamentoSuccessivo.TRASMISSIONE};
		Random random = new Random();
		return successivi[random.nextInt(successivi.length)];
	}
	
	//Metodi per la crazione del fascicolo
	public FascicoloDTO creaFascicolo()
	{
		LOGGER.info("popolo il dto fasciolo per i test");
		Random random = new Random();
		FascicoloDTO fascicolo = new FascicoloDTO();
		fascicolo.setCodice(StringGenerator.randomAlphaNumeric(255));
	//	fascicolo.setUfficio(StringGenerator.randomAlphaNumeric(10));
		fascicolo.setTipoProcedimento(getRandomTipoProcedimento());
		fascicolo.setCodiceInternoEnte(StringGenerator.randomAlphaNumeric(255));
		fascicolo.setNumeroInternoEnte(StringGenerator.randomAlphaNumeric(255));
		fascicolo.setDataProtocollo(new Date());
		fascicolo.setDataRilascioAutorizzazione(new Date());
		fascicolo.setStato(getRandomStatus());
		if(fascicolo.getStato() == StatoFascicolo.TRANSMITTED || fascicolo.getStato() == StatoFascicolo.FINISHED)
		{
			fascicolo.setDataCampionamento(new Date());
			fascicolo.setDataTrasmissione(new Date());
		}
		if(fascicolo.getStato() == StatoFascicolo.FINISHED)
		{
			fascicolo.setDataVerifica(new Date());
		}
		fascicolo.setEsito(getRandomEsito());
		fascicolo.setNote(StringGenerator.randomAlphaNumeric(1000));
		fascicolo.setNumeroProvvedimento(StringGenerator.randomAlphaNumeric(100));
		fascicolo.setOggettoIntervento(StringGenerator.randomAlphaNumeric(255));
		fascicolo.setProtocollo(StringGenerator.randomAlphaNumeric(255));
//		fascicolo.setQualDPCM_2005(getRandomQualificaDPCM_2005());
//		fascicolo.setQualificazioneDpr_139_2010(StringGenerator.randomAlphaNumeric(1, "SN"));
//		fascicolo.setQualificazioneDpr_31_2017(StringGenerator.randomAlphaNumeric(1, "SN"));
		fascicolo.setRup(StringGenerator.randomAlphaNumeric(255));
		fascicolo.setSanatoria(random.nextBoolean());
//		fascicolo.setTipoInterventoTempo(StringGenerator.randomAlphaNumeric(1, "TP"));
//		fascicolo.setTipologiaIntervento(getRandomTipoIntervento());
		fascicolo.setUsernameUtenteCreazione(StringGenerator.randomAlphaNumeric(255));
		
		return fascicolo;
	}
	
	public ConfigurazioneCampionamentoDTO creaConfigurazione()
	{
		Random random = new Random();
		ConfigurazioneCampionamentoDTO configurazione = new ConfigurazioneCampionamentoDTO();
		configurazione.setCampionamentoAttivo(random.nextBoolean());
		configurazione.setEsitoPubblico(random.nextBoolean());
		configurazione.setTipoCampionamentoSuccessivo(getRandomCampionamentoSuccessivo());
		configurazione.setIntervalloCampionamento((short)(30*(Math.random())));
		return configurazione;
	}
	
	public ConfigurazionePermessiDTO creaConfigurazionePermesso()
	{
		Random random = new Random();
		ConfigurazionePermessiDTO confperm = new ConfigurazionePermessiDTO();
		confperm.setCodiceEnte(StringGenerator.randomAlphaNumeric(255));
		confperm.setPermessoComunicazione(random.nextBoolean());
		confperm.setPermessoDocumentazione(random.nextBoolean());
		confperm.setPermessoOsservazione(random.nextBoolean());
		return confperm;
	}
	
}
