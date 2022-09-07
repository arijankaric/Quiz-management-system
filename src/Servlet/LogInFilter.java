package Servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LogInFilter
 */
@WebFilter(
        urlPatterns = "/admin/*",
        filterName = "AdminFilter",
        description = "Filter all admin URLs"       
)
public class LogInFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LogInFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		System.out.println("doFilter");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		String url = httpRequest.getContextPath() + "/adminLogin";
		boolean loggedIn = session != null && session.getAttribute("loggedIn") != null && session.getAttribute("loggedIn").equals(true);
		boolean loginRequest = httpRequest.getRequestURI().equals(url);

		// pass the request along the filter chain
		// chain.doFilter(request, response);

		if (loggedIn || loginRequest) {
			chain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect(url);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
