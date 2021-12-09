/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import com.google.gson.Gson;
import dominio.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Equipo 6
 */
public class Cliente {
    
    private Gson gson = new Gson();
    private String json;
    
    private Socket socket ;
    private ObjectOutputStream objectOutputStream;
    // FLUJO DE ENTRADA DE DATOS (LA INFORMACIÓN LLEGARÁ POR AQUÍ, SOLO HAY QUE CONVERTIRLA)
    private ObjectInputStream flujoEntradaDatos;

    
    public Cliente() {
        try {
            this.socket = new Socket("localhost", 7777);
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntradaDatos = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public String registrar(String nombre, String email, String contrasena, 
            String celular, String sexo, Date fechaNac, int edad) {

        Usuario usuario = new Usuario(nombre, email, contrasena, celular, sexo, fechaNac, edad);
        json = gson.toJson(usuario);

        json = "reg" + json;
        
        String mensajeRespuesta = "";
        
        try {
            objectOutputStream.writeObject(json);
            objectOutputStream.flush();
            
            mensajeRespuesta = (String) flujoEntradaDatos.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }

        
        return mensajeRespuesta;   
    }
    
    public String registrar(String nombre, String email, String contrasena, 
            String sexo, Date fechaNac, int edad) {
        
        Usuario usuario = new Usuario(nombre, email, contrasena, sexo, fechaNac, edad);
        json = gson.toJson(usuario);
        
        json = "reg"+json;
        
        String mensajeRespuesta = "";
        try {
            objectOutputStream.writeObject(json);
            objectOutputStream.flush();
        
        mensajeRespuesta = (String) flujoEntradaDatos.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        
        return mensajeRespuesta;
    }
    
    public Usuario iniciarSesionEmail(String email, String contrasena) {
        
        Usuario usuario = new Usuario();
        
        usuario.setEmail(email);
        usuario.setContrasena(contrasena);
        
        json = gson.toJson(usuario);
        json = "ini" + json;
        
        try {
            objectOutputStream.writeObject(json);
            objectOutputStream.flush();
            String mensajeRespuesta = (String) flujoEntradaDatos.readObject();

            Usuario usuarioRes = gson.fromJson(mensajeRespuesta, Usuario.class);
            return usuarioRes;
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
            return null;
        }
    }
    
    public Usuario iniciarSesionCel(String celular, String contrasena) {
        
        Usuario usuario = new Usuario();
        
        usuario.setCelular(celular);
        usuario.setContrasena(contrasena);
        
        json = gson.toJson(usuario);
        json = "ini" + json;
        
        try {
            objectOutputStream.writeObject(json);
            objectOutputStream.flush();
        
        String mensajeRespuesta = (String) flujoEntradaDatos.readObject();
        
        Usuario usuarioRes = gson.fromJson(mensajeRespuesta, Usuario.class);
        return usuarioRes;
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
            return null;
        }        
    }
    
    public String actualizarUsuario(String nombre, String email, String contrasena, String sexo, Date fechaNac, int edad ) {
        Usuario usuario = new Usuario(nombre, email, contrasena, sexo, fechaNac, edad);  
        
        json = gson.toJson(usuario);
        json = "act" + json;
        
        String mensajeRespuesta = "";
        try {
            objectOutputStream.writeObject(json);
            objectOutputStream.flush();

            mensajeRespuesta = (String) flujoEntradaDatos.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        
        return mensajeRespuesta;
        
    }
    
    public void cerrar() {
        try {
            System.out.println("Closing socket from client side");
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void terminar() {
        try {
            objectOutputStream.writeObject("terminar");
            objectOutputStream.flush();
            this.cerrar();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
}
