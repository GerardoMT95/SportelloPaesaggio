package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.IndirizzoCompletoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.InformazionePersonaleDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.RichiedenteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.TecnicoIncaricatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.UlterioriInformazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.RuoloPraticaEnum;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.RuoloReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperAbstractDomandaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDelegatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperIndirizzoCompletoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperInformazioniPersonaliDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperLocalizzazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperParticellaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperReferenteDocumentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperRichiedenteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperTecnicoIncaricatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperUlterioreInformazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParticelleCatastaliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDocumentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.RuoloPraticaRepository;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

public abstract class AbstractJasperDichiarazioneAdapter<T extends JasperAbstractDomandaDto> implements IJasperDichiarazioneAdapter<T>
{
	
	protected abstract String getDescDittaInQualitaDi(int inQualitaDi,String DescrAltroDitta);
	
	protected final void adaptBaseData(final T entity, final FascicoloDto fascicolo)
	{
		entity.setCodiceFascicolo(fascicolo.getCodicePraticaAppptr());
		entity.setTipoProcedimento(fascicolo.getTipoProcedimento());
		entity.setOggettoIntervento(fascicolo.getOggetto());
		entity.setRichiedente(Collections.singletonList(this.adaptRichiedente(fascicolo)));
		entity.setTecnicoIncaricato(Collections.singletonList(this.adaptTecnicoIncaricato(fascicolo)));
		entity.setLocalizzazione(this.adaptlocalizzazione(fascicolo));
		entity.setTestoPrivacyHtml(fascicolo.getPrivacyText());
		PraticaDelegatoDTO delegatoCorrente = fascicolo.getDelegatoCorrente();
		if(delegatoCorrente!=null) {
			entity.setDelegato(Collections.singletonList(this.adaptDelegato(fascicolo,delegatoCorrente)));
		}
		entity.setTestoPrivacyHtml(fascicolo.getPrivacyText());
	}
	
	/**
	 * Metodo base che viene utilizzato per convertire le informazioni basilari che nascono da "informazione personale"
	 * @param base oggetto Jasper in cui devono essere settati i dati
	 * @param info oggetto da cui vanno prelevati i dati
	 */
	protected final <B extends JasperInformazioniPersonaliDto, I extends InformazionePersonaleDto> void setBaseInfo(final B base, final I info)
	{
		if(info != null)
		{
			base.setCodiceFiscale(info.getCodiceFiscale());
			base.setCognome(info.getCognome());
			base.setNome(info.getNome());
			base.setNatoIl(info.getNatoIl());
			if (info.getNatoStato().getValue().equals(1L))
			{
				base.setNatoStato("Italia");
				base.setNatoProvincia(info.getNatoProvincia().getDescription());
				base.setNatoComune(info.getNatoComune().getDescription());
			} 
			else
			{
				base.setNatoStato(info.getNatoStato().getDescription());
				base.setNatoCitta(info.getNatoCitta());
			}
			base.setSesso(info.getSesso());
			base.setDocumento(Collections.singletonList(this.adaptDocumentoReferente(info.getDocumento())));
		}
	}
	
	protected final JasperIndirizzoCompletoDto adaptIndirizzoCompleto(final IndirizzoCompletoDto indirizzo)
	{
		final JasperIndirizzoCompletoDto entity = new JasperIndirizzoCompletoDto();
		if(indirizzo != null)
		{
			entity.setCap(indirizzo.getCap());
			entity.setVia(indirizzo.getVia());
			entity.setN(indirizzo.getN());
			if(indirizzo.getStato() != null)
			{
				if (indirizzo.getStato().getValue().equals(1L))
				{
					entity.setStato("Italia");
					entity.setProvincia(indirizzo.getProvincia().getDescription());
					entity.setComune(indirizzo.getComune().getDescription());
				} else
				{
					entity.setStato(indirizzo.getStato().getDescription());
					entity.setCitta(indirizzo.getCitta());
				}
			}
		}
		return entity;
	}
	
	private JasperReferenteDocumentoDto adaptDocumentoReferente(final ReferentiDocumentoDTO documento)
	{
		final JasperReferenteDocumentoDto doc = new JasperReferenteDocumentoDto();
		if(documento != null)
		{
			doc.setDataRilascio(documento.getDataRilascio());
			doc.setDataScadenza(documento.getDataScadenza());
			doc.setEnteRilascio(documento.getEnteRilascio());
			doc.setNumero(documento.getNumero());
			doc.setTipoDocumento(documento.getIdTipo());
		}
		return doc;
	}
	
	private JasperDelegatoDto adaptDelegato(final FascicoloDto fascicolo,PraticaDelegatoDTO delegato) {
		final JasperDelegatoDto entity = new JasperDelegatoDto();
		entity.setNome(delegato.getNome());
		entity.setCognome(delegato.getCognome());
		entity.setSesso(delegato.getSesso());
		entity.setCodiceFiscale(delegato.getCodiceFiscale());
		entity.setNatoIl(delegato.getDataNascita());
		
		if (delegato.getIdNazioneNascita().equals(1))
		{
			entity.setNatoStato(delegato.getNazioneNascita());
			entity.setNatoProvincia(delegato.getProvinciaNascita());
			entity.setNatoComune(delegato.getComuneNascita());
		} 
		else
		{
			entity.setNatoStato(delegato.getNazioneNascita());
			entity.setNatoCitta(delegato.getComuneNascitaEstero());
		}
		JasperIndirizzoCompletoDto indirizzoCompleto = new JasperIndirizzoCompletoDto();
		entity.setResidenteIn(List.of(indirizzoCompleto));
		indirizzoCompleto.setCap(delegato.getCapResidenza());
		indirizzoCompleto.setVia(delegato.getIndirizzoResidenza());
		indirizzoCompleto.setN(delegato.getCivicoResidenza());
		if(delegato.getNazioneResidenza()!= null)
		{
			if (delegato.getIdNazioneResidenza().equals(1))
			{
				indirizzoCompleto.setStato(delegato.getNazioneResidenza());
				indirizzoCompleto.setProvincia(delegato.getProvinciaResidenza());
				indirizzoCompleto.setComune(delegato.getComuneResidenza());
			} else
			{
				indirizzoCompleto.setStato(delegato.getNazioneResidenza());
				indirizzoCompleto.setComune(delegato.getComuneResidenzaEstero());
			}
		}
		entity.setIndirizzoEmail(delegato.getMail());
		entity.setPec(delegato.getPec());
		return entity;
	}
	
	private JasperRichiedenteDto adaptRichiedente(final FascicoloDto fascicolo)
	{
		final JasperRichiedenteDto entity = new JasperRichiedenteDto();
		if(fascicolo.getIstanza() != null && fascicolo.getIstanza().getRichiedente() != null)
		{
			final RichiedenteDto richiedente = fascicolo.getIstanza().getRichiedente();
			this.setBaseInfo(entity, richiedente);
			entity.setIndirizzoEmail(richiedente.getIndirizzoEmail());
			entity.setPec(richiedente.getPec());
			entity.setRecapitoTelefonico(richiedente.getRecapitoTelefonico());
			entity.setNelCaso(richiedente.isNelCaso());
			entity.setResidenteIn(Collections.singletonList(this.adaptIndirizzoCompleto(richiedente.getResidenteIn())));
			if (entity.getNelCaso() != null && entity.getNelCaso())
			{
				entity.setDittaInQualitaDiDescr(this.getDescDittaInQualitaDi(richiedente.getInQualitaDi(),richiedente.getDescAltroDitta()));
				entity.setInQualitaDi(richiedente.getInQualitaDi());
				entity.setPartitaIva(richiedente.getPartitaIva());
				entity.setSocieta(richiedente.getSocieta());
				entity.setSocietaCodiceFiscale(richiedente.getSocietaCodiceFiscale());
				entity.setDescAltroDitta(richiedente.getDescAltroDitta());
				entity.setCodiceIpa(richiedente.getCodiceIpa());
				entity.setTipoAzienda(richiedente.getTipoAzienda());
			}
		}
		return entity;
	}
	
	private JasperTecnicoIncaricatoDto adaptTecnicoIncaricato(final FascicoloDto fascicolo)
	{
		final JasperTecnicoIncaricatoDto entity = new JasperTecnicoIncaricatoDto();
		if(fascicolo.getIstanza() != null && fascicolo.getIstanza().getTecnicoIncaricato() != null)
		{
			final TecnicoIncaricatoDto tecnico = fascicolo.getIstanza().getTecnicoIncaricato();
			this.setBaseInfo(entity,  tecnico);
			entity.setCellulare(tecnico.getCellulare());
			entity.setDi(tecnico.getDi());
			entity.setFax(tecnico.getFax());
			entity.setPec(tecnico.getPec());
			entity.setIscritoAllOrdine(tecnico.getIscritoAllOrdine());
			entity.setN(tecnico.getN());
			if(tecnico.getConStudioIn() != null)
				entity.setConStudioIn(Collections.singletonList(this.adaptIndirizzoCompleto(tecnico.getConStudioIn())));
			if(tecnico.getResidenteIn() != null)
				entity.setResidenteIn(Collections.singletonList(this.adaptIndirizzoCompleto(tecnico.getResidenteIn())));
			entity.setRecapitoTelefonico(tecnico.getRecapitoTelefonico());
		}
		return entity;
	}
	
	private List<JasperLocalizzazioneDto> adaptlocalizzazione(final FascicoloDto fascicolo)
	{
		final List<JasperLocalizzazioneDto> entities = new ArrayList<JasperLocalizzazioneDto>();
		if(fascicolo.getIstanza() != null && fascicolo.getIstanza().getLocalizzazione() != null && fascicolo.getIstanza().getLocalizzazione().getLocalizzazioneComuni() != null)
		{
			for (final LocalizzazioneInterventoDTO l: fascicolo.getIstanza().getLocalizzazione().getLocalizzazioneComuni())
			{
				final JasperLocalizzazioneDto entity = new JasperLocalizzazioneDto();
				entity.setComune(l.getComune());
				entity.setDataRiferimentoCatastale(l.getDataRiferimentoCatastale());
				entity.setDestUsoAtt(l.getDestUsoAtt());
				entity.setDestUsoProg(l.getDestUsoProg());
				entity.setIndirizzo(l.getIndirizzo());
				entity.setInterno(l.getInterno());
				entity.setNumCivico(l.getNumCivico());
				entity.setPiano(l.getPiano());
				entity.setParticelle(this.adaptParticelle(l.getParticelle()));
				entity.setSigla(l.getSiglaProvincia());
				entity.setUlterioriInformazioni(Collections.singletonList(this.adaptUlterioriInformazioni(l.getUlterioriInformazioni())));
				entities.add(entity);
			}
		}
		return entities;
	}
	
	private JasperUlterioreInformazioniDto adaptUlterioriInformazioni(final UlterioriInformazioniDto ui)
	{
		final JasperUlterioreInformazioniDto entity = new JasperUlterioreInformazioniDto();
		//... TODO: per ora non viene mai utilizzato nei report
		return entity;
	}
	
	private List<JasperParticellaDto> adaptParticelle(final List<ParticelleCatastaliDTO> particelle)
	{
		List<JasperParticellaDto> entities = null;
		if(particelle != null && !particelle.isEmpty())
		{
			entities = new ArrayList<JasperParticellaDto>();
			for(final ParticelleCatastaliDTO particella: particelle)
			{
				final JasperParticellaDto entity = new JasperParticellaDto();
				entity.setCodCat(particella.getCodCat());
				entity.setFoglio(particella.getFoglio());
				entity.setLivello(particella.getLivello());
				entity.setParticella(particella.getParticella());
				entity.setSezione(particella.getDescrSezione()!=null?particella.getDescrSezione():particella.getSezione());
				entity.setSub(particella.getSub());
				entities.add(entity);
			}
		}
		return entities;
	}

	 
}
