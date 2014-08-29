package org.ferris.ejbrace.web.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.ferris.ejbrace.ejb.AccountService;
import org.ferris.ejbrace.model.Account;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@WebServlet("/ServletCallToEjb")
public class ServletCallToEjb extends HttpServlet 
{
    private final String redLookup = "java:comp/env/ejb/Red";
    
    // Remember, this is a 
    //   
    //  !! LOCAL JNDI LOOKUP NAME !!
    //
    // and this lookup name is only used by this WAR.  How this 
    // local lookup name maps to a real EJB instance is configured
    // in web.xml and glassfish-web.xml
    @EJB(lookup = redLookup)
    AccountService accountService;
    
    class ByEjb extends ServiceTemplateMethod 
    {
        @Override
        public Account getAccount() {
            return accountService.getAccount();
        }

        @Override
        public String getName() {
            return ServletCallToEjb.class.getName();
        }
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException 
    {
        ByEjb byEjb = new ByEjb();
        byEjb.service(response);
    }
}
