package com.douviz.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RestrictionFilter implements Filter {
	public static final String ACCES_CONNEXION = "/connexion";
	public static final String ATT_SESSION_USER = "sessionUtilisateur";

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		/* Non-filtrage des ressources statiques */
        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        System.out.println(request.getRequestURI());
        System.out.println("chemin: " + chemin);
        if ( chemin.startsWith( "/inc" ) ) {
            chain.doFilter( request, response );
            return;
        }

		HttpSession session = request.getSession();

		if (session.getAttribute(ATT_SESSION_USER) == null) {
			// response.sendRedirect(request.getContextPath() + ACCES_PUBLIC);
			request.getRequestDispatcher(ACCES_CONNEXION).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}

}
