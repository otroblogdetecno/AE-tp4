/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp4moaco;

// ----------------------------------
// --- para hacer los multihilos ----
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
// ----------------------------------


/**
 *
 * @author Juan Carlos Miranda Junio 2016
 * 
 * Implementación en Java del problema Biobjective TSP
 * Este trabajo práctico propone resolver el Biobjective TSP utilizando un 
 * algoritmo de Colonias de Hormigas 
 * (Multi-Objective Ant Colony Optimization - MOACO) de su preferencia. 
 * Solo se verificará la capacidad de resolver correctamente los siguientes 
 * problemas tipos publicados en http://eden.dei.uc.pt/~paquete/tsp/
1. kroAB100
2. kroAB150
 * 
 * Esta implementación utiliza dos matrices de: distancias,feromonas, visibilidad
 * Se trabajaron con versiones recortadas de los problemas propuestos.
 * 
 * 
 */
public class PrincipalTSPMOACO {
    
    //------ INICIO CONFIGURACION DEL ALGORITMO -----
    public static int CANT_GENERACIONES=10;//10
    public static int CANT_HORMIGAS=50; //50
    public static Double TASA_EVAPORACION=0.01; //0.01
    public static Double ALFA=1.0;    //1.0
    public static Double BETA=1.0;    //5.0    


    //Configuración de las variables de contexto
    //Si es un problema de minimización o maximización
    public static int MINIMIZAR=0;
    public static int MAXIMIZAR=1;
    public static int FLAG=MINIMIZAR; //por default configurado para minimizar
    
    //------ FIN CONFIGURACION DEL ALGORITMO -----    
    
    
    //--- INICIALIZACION DE FEROMONAS ---
    public static CircuitoBIOBJ circuito1; //=new CircuitoBIOBJ();
    public static Feromonas feromonas1; //=new Feromonas(circuito1.getNumeroNodos(),circuito1.getNumeroNodos());    
    public static Visibilidad visibilidad1;
    //-------------------------------


    //--- INICIALIZACION DE FEROMONAS ---
    public static CircuitoBIOBJ circuito2; //
    public static Feromonas feromonas2; //
    public static Visibilidad visibilidad2;
    

    
    public static ParetoTSPMOACO funcionesPareto=new ParetoTSPMOACO(MINIMIZAR);
    //-----------------------------------------------
    //------------ FIN CONFIGURACION DEL ALGORITMO ---------------------        

    //------se guardan las mejores soluciones 
    public static SolucionTSP [] solucionHormiga;//arreglo de soluciones obtenidas por las hormigas
    public static SolucionTSP [] listadoNoDominados;//listado procesado de no dominados
    public static SolucionTSP [] paretoSetTemp;//variable auxiliar para procesar el pareto

    public static SolucionTSP [] paretoAntes;//temporal

    //por una cuestion de facilidad, los objetos de la clase Vector se comportan mejor
    //para agregar elementos y crecer.
    public static Vector <SolucionTSP> paretoSet=new Vector<SolucionTSP>();//donde se guardan los paretos    
    public static Vector <SolucionTSP> solucionesColonia=new Vector<SolucionTSP>();
    
    
    public static SolucionTSP solucion; //variable temporal.
    
    
    public static void main(String[] args) throws IOException {

    //--- CONFIGURACION DE PARAMETROS -----------------------------------------
// tamano de los archivos a leer segun objetivos 1 y dos
    circuito1=new CircuitoBIOBJ("kroA14.tsp");
    circuito2=new CircuitoBIOBJ("kroB14.tsp");       

//descomentar segú se necesite 5 o 100 ciudades, los archivos se encuentran
//en la raiz de l proyecto
//    circuito1=new CircuitoBIOBJ("kroA5.tsp");         
//    circuito2=new CircuitoBIOBJ("kroB5.tsp");   

//    circuito1=new CircuitoBIOBJ("kroA100.tsp");
//    circuito2=new CircuitoBIOBJ("kroB100.tsp");       


    //CONFIGURACION DE OBJETIVO 1
    feromonas1=new Feromonas(circuito1.getNumeroNodos(),circuito1.getNumeroNodos());
    visibilidad1=new Visibilidad(circuito1.matrizDistanciasCiudades);   

    //CONFIGURACION DE OBJETIVO 2
    feromonas2=new Feromonas(circuito2.getNumeroNodos(),circuito2.getNumeroNodos());
    visibilidad2=new Visibilidad(circuito2.matrizDistanciasCiudades);   

    //--------------------------------------
    int [] recorrido=new int [circuito1.CANTIDADCIUDADES];
    //longitud de caminos funcion1 y funcion2
    Double longitudf1=0.0;
    Double longitudf2=0.0;   
    
    //flag que indica si una solucion encontrada es igual a un elemento del conjunto pareto    
    int flagIgual=0; 
    

    for(int generacion=0;generacion<CANT_GENERACIONES;generacion++){
            for(int hormigaK=1;hormigaK<CANT_HORMIGAS+1;hormigaK++){
                //---------- LAMADO A LA HORMIGAS
                //--- inicio trabajo hormiga ---
                HormigaBIOBJ hormiga=new HormigaBIOBJ(ALFA,BETA,hormigaK,CANT_HORMIGAS,circuito1,feromonas1,visibilidad1,circuito2,feromonas2,visibilidad2);
                //CONTRUIR SOLUCION
                //se construye el camino en una variable temporal
                recorrido=hormiga.construirSolucion();

                //obtener longitud de ambos recorridos
                longitudf1=circuito1.getLongitudRecorrido(recorrido);
                longitudf2=circuito2.getLongitudRecorrido(recorrido);
                //--- fin trabajo hormiga ---
                                                
//                System.out.printf("Generacion=%3d Hormiga=%3d R-> Longitudf1=%5.14f | Longitudf1=%5.14f-->",generacion,hormigaK,longitudf1,longitudf2);

                //----------------------------------------------------------
                //----- SOLUCION OBTENIDA -------------
                //----------------------------------------------------------
                //solucion temporal
                solucion=new SolucionTSP();
                solucion.vectorSoluciones[0]=longitudf1;
                solucion.vectorSoluciones[1]=longitudf2;
                solucion.vectorRecorrido=new int[recorrido.length];
                System.arraycopy(recorrido, 0, solucion.vectorRecorrido, 0, recorrido.length);
               
//                System.out.println("Generacion="+generacion);                                

                System.out.print("Generacion="+generacion+" hormiga="+hormigaK+" "+solucion.vectorSoluciones[0]+" - "+solucion.vectorSoluciones[1]);
/*
                //para mostrar los datos del recorrido
                for(int i=0; i<recorrido.length;i++){
                    System.out.printf(" %3d",recorrido[i]);                
                }//for recorrido
*/                
                System.out.println("");                                

                //guarda la solucion de cada hormiga en 
                solucionesColonia.add(solucion);
                                              
                //----------------------------------------------------------                
                //----------- FIN LLAMADO HORMIGAS                
            }//fin hormigasK

                //limpia el vector anter de utilizarlo para almacenar las hormigas
                solucionHormiga=new SolucionTSP[0]; //
                listadoNoDominados=new SolucionTSP[0]; //
                
                //las soluciones 
                solucionHormiga=solucionesColonia.toArray(new SolucionTSP[solucionesColonia.size()]);                
                //obtiene soluciones NO DOMINADAS
                listadoNoDominados=funcionesPareto.getConjuntoPareto(solucionHormiga);//lindo y bonito

                //importante es vacia el vector en cada vuelta
                solucionesColonia.clear();//limpiar                
                
                System.out.println("Generacion="+generacion);                
                System.out.println("solucionHormiga.length="+solucionHormiga.length);
                System.out.println("listadoNoDominados.length="+listadoNoDominados.length);                    


                //mostrar las soluciones no dominadas
                for(int fila=0;fila<listadoNoDominados.length;fila++){        
                    listadoNoDominados[fila].mostrarSoluciones();
                    System.out.println();                        
                }//for
                
                //----------------------------------------------------------
                //----- INICIO ACTUALIZAR FEROMONAS -------------
                //----------------------------------------------------------            
//                System.out.println("Actualizando feromonas generacion="+generacion);
                    
                for(int fila=0;fila<listadoNoDominados.length;fila++){
 //                   System.out.println("Actualizando feromonas "+fila+")");
                    feromonas1.actualizarFeromonasCamino(listadoNoDominados[fila].vectorRecorrido, longitudf1);
                    //EVAPORAR FEROMONAS
                    feromonas1.evaporarFeromonas(TASA_EVAPORACION);
                    
                    feromonas2.actualizarFeromonasCamino(listadoNoDominados[fila].vectorRecorrido, longitudf2);
                    //EVAPORAR FEROMONAS
                    feromonas2.evaporarFeromonas(TASA_EVAPORACION);
                }//for                        
                //----------------------------------------------------------
                //----- FIN ACTUALIZAR FEROMONAS -------------
                //----------------------------------------------------------                    
            
            

                //----------------------------------------------------------
                //----- INICIO EVALUACION DE PARETOS -------------
                //----------------------------------------------------------
                //------------aca guardo las buenas soluciones ----

                //actualizar el pareto puretoooo    
                if(generacion==0){
                //cuando es la generacion cero, se agrega para iniciar el conjunto pareto
                //------------------------------------------------------------.
                    paretoSet.add(solucion);//al pareto existente le agrega los recien llegados 
                //------------------------------------------------------------            
                }//generacion==0
//------------------------------------------------------------

                   for(int fila=0;fila<listadoNoDominados.length;fila++){
                       flagIgual=0;//inicializa la bandera
                       for(int j=0;j<paretoSet.size();j++){
                           if(funcionesPareto.esIgual(listadoNoDominados[fila].vectorSoluciones, paretoSet.elementAt(j).vectorSoluciones)==1){
                               //son iguales
                               //System.out.println("IGUALES");
                               flagIgual=1;
                               break;
                           }//if
                       }//for
                       if(flagIgual==0){
                           paretoSet.add(listadoNoDominados[fila]);//al pareto existente le agrega los recien llegados
                       }//if flagIgual==0
                   }//for
//------------------------------------------------------------
                //una vez agregados los nuevos elementos no dominados a pareto
                //se convierte a array para poder verificar los no dominados
                paretoAntes=paretoSet.toArray(new SolucionTSP[paretoSet.size()]);

                //se calcula el nuevo frente pareto
                paretoSetTemp=funcionesPareto.getConjuntoPareto(paretoAntes);
                //obtener el nuevo frente pareto

                System.out.println("paretoSet.size()="+paretoSet.size()); 
                System.out.println("paretoAntes.length()="+paretoAntes.length);                 
                
                paretoSet.clear();//antes de guardar se limpia el vector
                for(int i=0;i<paretoSetTemp.length;i++){
                    paretoSet.add(paretoSetTemp[i]);//guarda finalmente en el pareto
                }//for
        //--------------------------
                System.out.println("Ultimo paretoSet.size()="+paretoSet.size()); 
                //----------------------------------------------------------
                //----- FIN EVALUACION DE PARETOS -------------
                //----------------------------------------------------------                             
        }//fin for principal


        System.out.println("--- RECORRIDOS INICIO VECTOR PARETO FINAL---");      
        for(int i=0;i<paretoSet.size();i++){
            paretoSet.elementAt(i).mostrarRecorrido();
            System.out.println();            
        }//for
        System.out.println("--- RECORRIDOS FIN VECTOR PARETO FINAL---");

        

        System.out.println("--- SOLUCIONES INICIO VECTOR PARETO FINAL---");      
        for(int i=0;i<paretoSet.size();i++){
            paretoSet.elementAt(i).mostrarSoluciones();
            System.out.println();            
        }//for
        System.out.println("--- SOLUCIONES FIN VECTOR PARETO FINAL---");

            //---IMPRESION DE PARAMETROS----------------------------------------
            System.out.println("------------------------");    
            System.out.println("PARAMETROS DEL ALGORITMO");     
            System.out.println("------------------------");     
            System.out.println("CANT_GENERACIONES="+CANT_GENERACIONES); 
            System.out.println("CANT_HORMIGAS="+CANT_HORMIGAS);
            System.out.println("TASA_EVAPORACION="+TASA_EVAPORACION);
            System.out.println("CANT_HORMIGAS="+CANT_HORMIGAS);
            System.out.println("ALFA="+ALFA);
            System.out.println("BETA="+BETA);
            //-------------------------------------------  

    }//main
    
}//fin de clase
