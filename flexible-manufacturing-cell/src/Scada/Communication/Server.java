/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Scada.Communication;

import java.io.* ;
import java.net.* ;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier Sánchez
 */
public class Server {

    int _port;

    public Server() { }

    public void start() {
        try {
            Properties prop = new Properties();
            InputStream is = new FileInputStream("build//classes//flexiblemanufacturingcell//resources//Mailboxes.properties");
            prop.load(is);
            _port = Integer.parseInt(prop.getProperty("Scada.port"));
            ServerSocket skServidor = new ServerSocket(_port);
            System.out.println("Server listening at port " + _port);
            int numCli = 1;
            while(true) {
                Socket skCliente = skServidor.accept();
                System.out.println("Serving to client " + numCli);
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                System.out.println("Received> " + Short.parseShort((String) in.readObject()));
                
                short a = (short) numCli;
                out.writeObject(a);
                skCliente.close();
                numCli++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
