/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp4moaco;

/**
 * Se utiliza para el traspaso de datos desde archivos de texto
 */
public class NodoCiudad {
    //------------------------------------
    public String nombre="";
    public Double coordenadaX=0.0;
    public Double coordenadaY=0.0;
    //------------------------------------
    
    public NodoCiudad(){
            this.nombre="";
            this.coordenadaX=0.0;
            this.coordenadaY=0.0;
    }//constructor NodoCiudad

    public NodoCiudad(String nombreC, Double coordenadaXC, Double coordenadaYC){
            this.nombre=nombreC;
            this.coordenadaX=coordenadaXC;
            this.coordenadaY=coordenadaYC;
    }//constructor NodoCiudad

    @Override
    public String toString() {
        return "NodoCiudad{" + "nombre=" + nombre + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + '}';
    }                
}//fin NodoCiudad
