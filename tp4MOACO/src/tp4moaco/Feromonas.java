/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp4moaco;

public class Feromonas {
    /*
       Las feromonas son matrices cuadradas, se crean en forma independiente
    pero 
    */
    public Double [][] matrizFeromonas;
    public Double [][] matrizVisibilidad;
    
    //el valor debe ser mayor que cero
    Double INIT_FEROMONAS=1.0;
    
    public Feromonas(int CANTFILAS, int CANTCOL){
        matrizFeromonas=new Double[CANTFILAS][CANTCOL];        
        matrizVisibilidad=new Double[CANTFILAS][CANTCOL];        
        
        for(int filas=0;filas<CANTFILAS;filas++){
            for(int col=0;col<CANTCOL;col++){
                matrizFeromonas[filas][col]=INIT_FEROMONAS;
            }
        }
    }//contructor
    
    public void mostrarFeromonas(){
        //se mueve utilizando el largo, asumiendo que es una matriz cuadrada
        //de feromonas
        System.out.println("MOSTRAR FEROMONAS");                
        for(int fila=0;fila<matrizFeromonas[0].length;fila++){

            System.out.printf("%2d) ",fila);

            for(int col=0;col<matrizFeromonas[0].length;col++){
                System.out.printf("%.6f ",matrizFeromonas[fila][col]);
            }
            System.out.println();
        }
    }//contructor
    
    public void actualizarFeromonas(int fila, int col, Double valorActualizar){
        //no le coloca chequeo todavia
        matrizFeromonas[fila][col]=matrizFeromonas[fila][col]+valorActualizar;        
    }//
    
    //se supone una matriz cuadrada
    public void evaporarFeromonas(Double tasaEvaporacion){
        Double nuevaCelda=0.0;        
        for(int filas=0;filas<matrizFeromonas.length;filas++){
            for(int col=0;col<matrizFeromonas[0].length;col++){

                nuevaCelda=(1-tasaEvaporacion)*matrizFeromonas[filas][col];
                //nunca puede ser menor que init que es el valor inicializado
//                if(nuevaCelda>init){
                    matrizFeromonas[filas][col]=nuevaCelda;
//                }
            }
        }
     }//fin evaporar feromonas    
    
    //asume que el recorrido esta bien formado
    //por eso no hace revisiones
    //asume que el valor de actualizacion que es la distancia viene bien
    public void actualizarFeromonasCamino(int [] recorrido,Double longitudRecorrido){
        int tope=recorrido.length-1;//el ultimo es el 
        Double suma=0.0;        
        int i=0;
        int j=i+1;
        int punto1,punto2;

        for(int contador=0;contador<tope;contador++){
            //obtiene del listado el valor del nodo
            punto1=recorrido[i];
            punto2=recorrido[j];
            //guarda la suma del ciclo
            matrizFeromonas[punto1][punto2]=matrizFeromonas[punto1][punto2]+(1/longitudRecorrido);
            //avanza lectura
            i=j;
            j=i+1;
        }//for        
    }//actualizarFeromonasCamino

    

}//clase Feromonas
