package it.eng.tz.puglia.autpae.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;

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
	public static List<TipologicaDestinatarioDTO> eliminaDuplicati(List<TipologicaDestinatarioDTO>... lista) {

		List<TipologicaDestinatarioDTO> listaIniziale = new ArrayList<>();

		if (lista!=null) {
			for (List<TipologicaDestinatarioDTO> listaParziale : lista) { 
				if (listaParziale!=null && !listaParziale.isEmpty()) {
					listaIniziale.addAll(listaParziale); 
				}
			}
		}
		
		Set<String> setEmail = new HashSet<>();

		listaIniziale.forEach(elem->{ if(elem.getEmail() != null) setEmail.add(elem.getEmail().trim().toLowerCase()); });

		List<TipologicaDestinatarioDTO> listaFinale = new ArrayList<>(setEmail.size());

		setEmail.forEach(email->{ listaFinale.add(new TipologicaDestinatarioDTO(email, null, null)); });

		listaFinale.forEach(elemFinale->{
			listaIniziale.forEach(elemIniz->{
				if (elemIniz.getEmail() != null && elemIniz.getEmail().trim().toLowerCase().equals(elemFinale.getEmail())) {
					elemFinale.setNome(elemIniz.getNome().trim());
					elemFinale.setPec (elemIniz.isPec());
					
					TipoDestinatario tipo = null;
					for (TipologicaDestinatarioDTO destIniziale : listaIniziale) {
						if (destIniziale.getEmail() != null && destIniziale.getEmail().trim().toLowerCase().equals(elemFinale.getEmail())) {
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