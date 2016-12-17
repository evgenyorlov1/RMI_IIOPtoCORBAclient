/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_iioptocorbaclient;

import DbmsAPP.Dbms;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

/**
 *
 * @author pc
 */
public class SingletonDBMSadapter {
    
    
    private static Dbms helloRef = null;
    
    private SingletonDBMSadapter() {}
    
    
    public static Dbms getInstance() {
        if(helloRef == null) {
            try{ 
                Properties properties = new Properties();
                properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.cosnaming.CNCtxFactory");
                properties.setProperty(Context.PROVIDER_URL,"iiop://localhost:1050");
                Context ic = new InitialContext(properties);                        
                Object objref = ic.lookup("Hello");            
                helloRef = (Dbms) PortableRemoteObject.narrow(objref, Dbms.class);                            
            } catch(Exception e) {
                System.out.println("ERROR : " + e); 
                e.printStackTrace(System.out); 
            }
        }
        return helloRef;
    }  
}
