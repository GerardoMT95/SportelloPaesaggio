package it.eng.tz.puglia.autpae.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.filter.ICustomLogFilter;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PM_GruppiRuoli;

@Component
public class VerifyGroupFilter extends OncePerRequestFilter implements ICustomLogFilter
{
	public static final String GROUP_KEY = "GRUPPO-UTENTE";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
			throws ServletException, IOException
	{
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && request.getHeader(GROUP_KEY) != null)
		{
			final String actualGroup = new String(Base64.decodeBase64(request.getHeader(GROUP_KEY).getBytes()));
			if(authentication.getPrincipal()==null || !(authentication.getPrincipal() instanceof UserDetail))
			{
				throw new BadCredentialsException("Attenzione: nessuna informazione per l'account utente, impossibile effettuare check di appartenenza al gruppo con codice " + actualGroup);
			}
			final UserDetail loggedUser = (UserDetail)authentication.getPrincipal();
			final List<PM_GruppiRuoli> groups = (List<PM_GruppiRuoli>) loggedUser.getOtherFields().get("GruppoRuoli");
			if(groups.stream().map(PM_GruppiRuoli::getCodiceGruppo).noneMatch(p -> p.equals(actualGroup)))
				throw new BadCredentialsException("Attenzione: l'utente " + loggedUser.getUsername() + " non appartiene al gruppo con codice " + actualGroup);
			SecurityUtil.getUserDetail().getOtherFields().put("codiceGruppo", actualGroup);
			groups.forEach(g->{
				if (g.getCodiceGruppo().equals(actualGroup))
					SecurityUtil.getUserDetail().getOtherFields().put("nomeGruppo", g.getNomeGruppo());
			});
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public int getOrder() { return 0; }
}
