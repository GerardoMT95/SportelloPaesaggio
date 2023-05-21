package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration;

import java.io.IOException;
import java.util.Arrays;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.filter.ICustomLogFilter;
import it.eng.tz.puglia.security.util.SecurityUtil;

@Component
public class VerifyGroupFilter extends OncePerRequestFilter implements ICustomLogFilter
{
	private String GROUP_KEY = "GRUPPO-UTENTE";
	private final static String PUBLIC_PATH = "/public/**";
	public final static String SWAGGER_PATH = "/swagger-ui.html";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AntPathMatcher pathMatcher=new AntPathMatcher(); 
        String[] antMatchers = new String[]{PUBLIC_PATH,SWAGGER_PATH};
        String path = request.getServletPath();
        boolean isPubblico = Arrays.asList(antMatchers).stream().filter(pathToMatch->pathMatcher.match(pathToMatch, path)).findAny().isPresent();
        if(!isPubblico && authentication != null && request.getHeader(GROUP_KEY) != null)
		{
			String actualGroup = new String(Base64.decodeBase64(request.getHeader(GROUP_KEY).getBytes()));
			UserDetail loggedUser = (UserDetail)authentication.getPrincipal();
			List<PM_GruppiRuoli> groups = (List<PM_GruppiRuoli>) loggedUser.getOtherFields().get("Gruppi_Ruoli");
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
