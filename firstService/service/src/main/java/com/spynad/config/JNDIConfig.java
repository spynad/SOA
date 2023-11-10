package com.spynad.config;

import com.spynad.service.TicketService;
import jakarta.ws.rs.NotFoundException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import com.spynad.service.PersonService;

public class JNDIConfig {
    public static PersonService personService(){
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        //jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        //jndiProps.put("jboss.naming.client.ejb.context", true);
        jndiProps.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
        //jndiProps.put(Context.PROVIDER_URL, "http://localhost:8080/wildfly-services");
        try {
            final Context context = new InitialContext(jndiProps);
            return  (PersonService) context.lookup("ejb:/ejb-1.0-SNAPSHOT/PersonServiceImpl!com.spynad.service.PersonService");
        } catch (NamingException e){
            System.out.println("Unable to find bean");
            e.printStackTrace();
            throw new NotFoundException();
        }
    }

    public static TicketService ticketService(){
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        //jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        //jndiProps.put("jboss.naming.client.ejb.context", true);
        jndiProps.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
        //jndiProps.put(Context.PROVIDER_URL, "http://localhost:8080/wildfly-services");
        try {
            final Context context = new InitialContext(jndiProps);
            return  (TicketService) context.lookup("ejb:/ejb-1.0-SNAPSHOT/TicketServiceImpl!com.spynad.service.TicketService");
        } catch (NamingException e){
            System.out.println("Unable to find bean");
            e.printStackTrace();
            throw new NotFoundException();
        }
    }
}
