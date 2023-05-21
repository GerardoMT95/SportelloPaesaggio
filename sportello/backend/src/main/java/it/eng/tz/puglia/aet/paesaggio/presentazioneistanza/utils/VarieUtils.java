package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TipologicaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.util.string.StringUtil;

public class VarieUtils {

	@SafeVarargs
	public static List<TipologicaDTO> eliminaDuplicati2(List<TipologicaDTO>... lista) {

		List<TipologicaDTO> list_1 = new ArrayList<>();

		for (List<TipologicaDTO> listaParziale : lista) { 
			if (listaParziale!=null) 
				list_1.addAll(listaParziale); 
		}

		Set<String> set_1 = new HashSet<>();

		list_1.forEach(elem->{ set_1.add(elem.getCodice().trim().toLowerCase()); });

		List<TipologicaDTO> listaFinale = new ArrayList<>(set_1.size());

		set_1.forEach(elem->{ listaFinale.add(new TipologicaDTO(elem, null)); });

		listaFinale.forEach(elemFinale->{
			 list_1.forEach(elemIn->{
				 if (elemIn.getCodice().trim().toLowerCase().equals(elemFinale.getCodice())) {
					 elemFinale.setLabel(elemIn.getLabel().trim());
				 }
			});
		});

		return listaFinale;
	}
	
	@SafeVarargs
	public static List<DestinatarioDTO> eliminaDuplicati(List<DestinatarioDTO>... lista) throws Exception {

		List<DestinatarioDTO> listaIniziale = new ArrayList<>();

		if (lista!=null) {
			for (List<DestinatarioDTO> listaParziale : lista) { 
				if (listaParziale!=null && !listaParziale.isEmpty()) {
					listaIniziale.addAll(listaParziale); 
				}
			}
		}
		
		Set<String> setEmail = new HashSet<>();

		listaIniziale.forEach(elem->
		{ 
			if(StringUtil.isEmail(elem.getEmail()))
				setEmail.add(elem.getEmail().trim().toLowerCase()); 
		});

		List<DestinatarioDTO> listaFinale = new ArrayList<>(setEmail.size());
		//costruisco la lista finale a partire dal set
		for(String email: setEmail)
		{
			DestinatarioDTO tmp = new DestinatarioDTO();
			tmp.setEmail(email);
			tmp.setNome(null);
			tmp.setPec(false);
			tmp.setTipo(TipoDestinatario.TO);
			listaFinale.add(tmp);
		}

		listaFinale.forEach(elemFinale->{
			listaIniziale.forEach(elemIniz->{
				if (StringUtil.isEmail(elemIniz.getEmail()) && elemIniz.getEmail().trim().toLowerCase().equals(elemFinale.getEmail())) {
					elemFinale.setNome(elemIniz.getNome().trim());
					elemFinale.setPec (elemIniz.getPec());
					
					TipoDestinatario tipo = null;
					for (DestinatarioDTO destIniziale : listaIniziale) {
						if (StringUtil.isEmail(destIniziale.getEmail()) && destIniziale.getEmail().trim().toLowerCase().equals(elemFinale.getEmail())) {
							if (destIniziale.getTipo()!=null && !TipoDestinatario.TO.equals(tipo)) {
								tipo = destIniziale.getTipo();
							}
						}
					}
					elemFinale.setTipo(tipo);
				}
			});
		});

		return listaFinale;
	}
	
}