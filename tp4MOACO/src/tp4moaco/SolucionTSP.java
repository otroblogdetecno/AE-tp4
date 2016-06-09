package tp4moaco;

import java.util.Arrays;

/*
* Guarda la solucion que incluye un tour hamiltoniano y dos distancias
* tomadas de dos matrices diferentes.
*
*/

public class SolucionTSP {
    //configuracion de los objetivos, esta es una clase generica, pero para 
    //bi objetivo se configura con dos    
    //NUMVARIABLES, este debe ser del mismo tamano que el camino o ciclo hamiltoniano
    public int NUMVARIABLES=5;
    public int NUMOBJETIVOS=2;
    
    //se guardan los nodos del ciclo hamiltoniando, los nombres de las ciudades son enteros
    //los cuales se leen de los archivos KROA100.tsp y etc...
    public int [] vectorRecorrido=new int[NUMVARIABLES];
    
    //vector de soluciones en este caso funcion 1 y funcion 2
    public Double [] vectorSoluciones=new Double[NUMOBJETIVOS];

    public int index=0;
    public int dominado=0; //indica si es dominado o no para la busqueda del pareto

    public SolucionTSP(){
        vectorRecorrido=new int[NUMVARIABLES];
        vectorSoluciones=new Double[NUMOBJETIVOS];  
    }//fin contructor
    
    public SolucionTSP(int cantVariables, int cantSoluciones){
        vectorRecorrido=new int [cantVariables];
        vectorSoluciones=new Double [cantSoluciones];
    }//fin contructor
  
    
    public Double [] getRecorrido(){
        Double [] vectorResultado;
        vectorResultado=new Double[vectorRecorrido.length];
        //devuelve el vector
        System.arraycopy(vectorRecorrido, 0, vectorResultado, 0, vectorRecorrido.length);
        return vectorResultado;
    }//getDeciciones

    public Double [] getSoluciones(){
        Double [] vectorResultado;
        vectorResultado=new Double[vectorSoluciones.length];
        //devuelve el vector
        System.arraycopy(vectorSoluciones, 0, vectorResultado, 0, vectorSoluciones.length);
        return vectorResultado;
    }//getDeciciones

    public void mostrarRecorrido(){
        for(int fila=0;fila<vectorRecorrido.length;fila++){
            System.out.print(";"+vectorRecorrido[fila]);
        }        
            System.out.print(";");
    }//mostrarDeciciones
           
    public void mostrarSoluciones(){
        for(int fila=0;fila<vectorSoluciones.length;fila++){
            System.out.print(";"+vectorSoluciones[fila]);
        }        
            System.out.print(";");
    }//mostrarDeciciones

}//fin SolucionTSP
