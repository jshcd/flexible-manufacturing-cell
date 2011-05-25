/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Scada.Communication;

import java.io.* ;
import java.net.* ;

/**
 *
 * @author Javier
 */
public class Server {
    static final int PORT = 5000;

    public Server() {

    }

    public void start() {
        try {
            ServerSocket skServidor = new ServerSocket(PORT);
            System.out.println("Server listening at port " + PORT);
            int numCli = 1;
            while(true) {
                Socket skCliente = skServidor.accept(); // Crea objeto
                System.out.println("Serving to client " + numCli);
                ObjectOutputStream out = new ObjectOutputStream(skCliente.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(skCliente.getInputStream());
                System.out.println("Received> " + Short.parseShort((String) in.readObject()));
                
                short a = 15;
                out.writeObject(a);
                skCliente.close();
                numCli++;
            }
        } catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }
}
