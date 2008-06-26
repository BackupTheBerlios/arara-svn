package net.indrix.arara.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.vo.User;

public class MaintenanceFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        boolean maintenance = false;
        
        if (request instanceof HttpServletRequest) {
            HttpSession session = ((HttpServletRequest) request).getSession(true);

            User user = (User) session.getAttribute(ServletConstants.USER_KEY);
            String ip = request.getRemoteAddr();
            
            if ((user != null && user.getLogin().equals("jefferson")) || "201.82.133.188".equals(ip)){
                chain.doFilter(request, response);
                return;
            } else {
                String nextResourceToExecute = ServletUtil.getResource((HttpServletRequest)request);
                if (nextResourceToExecute != null && nextResourceToExecute.contains("doMaintenance.jsp")){
                    chain.doFilter(request, response);
                    return;                    
                } else {                    
                    ServletContext context = session.getServletContext();
                    if (context != null){
                        maintenance = (Boolean) context.getAttribute(FilterConstants.MAINTENANCE);

                        if (!maintenance) {
                            chain.doFilter(request, response);
                            return;
                        } else {
                            context.getRequestDispatcher(FilterConstants.MAINTENANCE_PAGE).forward(request, response);
                            return;
                        }                                        
                    } else {
                        chain.doFilter(request, response);
                        return;
                    }
                }
                              
            }
            
        }

        throw new ServletException("Software Error");

    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}
