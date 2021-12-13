/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author Equipo 6
 */
public class Cliente_NoMain {
    
    private Socket cliente;
    private ObjectOutputStream out;
    private OutputStreamWriter out2;
        
    public void servidorReigstrar(String nombre, String email,
            String contrasena, String celular, String sexo, Date fechaNac,
            int edad) {
        
        try {

            cliente = new Socket("localhost", 9999);
            
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        
            out = new ObjectOutputStream(cliente.getOutputStream());            
            
            out.writeObject(nombre);  // objeto a transferir
            out.close();
            cliente.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
