/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trash;

import DbmsAPP.Dbms;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

/**
 *
 * @author pc
 */
public class RMI_IIOPtoCORBAclient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.cosnaming.CNCtxFactory");
            properties.setProperty(Context.PROVIDER_URL,"iiop://localhost:1050");
            
            Context ic = new InitialContext(properties);                        
            Object objref = ic.lookup("Hello");            
            Dbms s = (Dbms) PortableRemoteObject.narrow(objref, Dbms.class);            
            System.out.println (s.hello());
        } catch( Exception e ) {
            System.err.println( "Exception " + e);
            e.printStackTrace( );            
        }
    }    
}
