/*
 * Codigo, que implementa las funciones de la hormiga
 */
package tp4moaco;
import java.util.Arrays;
import java.util.Random;


/**
 *
 * @author usuario
 */
public class HormigaBIOBJ {
    //para el objetivo uno
    public CircuitoBIOBJ circuito1;
    public Feromonas feromonas1;
    public Visibilidad visibilidad1;

    //datos para el objetivo dos
    public CircuitoBIOBJ circuito2;
    public Feromonas feromonas2;
    public Visibilidad visibilidad2;
    
    //valores especificos para cálculo a la probabilidad
    public int kHormiga=1;
    public int mHormiga=1;
    Double alfa=1.0;
    Double beta=1.0;
    Double lambdaT=1.0;
    
    //recibe previamente inicializadas las matrices
    public HormigaBIOBJ(Double alfaP,Double betaP,int kHormigaP,int mHormigaP,CircuitoBIOBJ circuitoP1,Feromonas feromonasP1, Visibilidad visibilidadP1,CircuitoBIOBJ circuitoP2,Feromonas feromonasP2, Visibilidad visibilidadP2){
        circuito1=circuitoP1;
        feromonas1=feromonasP1; 
        visibilidad1=visibilidadP1;


        circuito2=circuitoP2;
        feromonas2=feromonasP2; 
        visibilidad2=visibilidadP2;

        //se utiliza para calcular el lambdaT de la probabilidad.
        kHormiga=kHormigaP;
        mHormiga=mHormigaP;
        alfa=alfaP;
        beta=betaP;
        lambdaT=(kHormigaP-1.0)/(mHormigaP-1.0);

//        lambdaT=0.1;
        
//        System.out.println("CONSTRUCTOR HORMIGA --> :)");
        //circuito1.mostrarMatrizDistancias();
        //feromonas.mostrarFeromonas();
        //visibilidad.mostrarVisibilidad();
        
    }//constructor
    
    //recibe un conjunto de ciudades aun NO VISITDAS y calcula
    //la probabilidad de elección de cada una de las ciudades
    //la ciudad actual viene en el parametro
    //a proxima ciudad a visitar es j
    //por cada 
    public Double calcularProbabilidadBIOBJ(int i,int j, int [] ciudadesNoVisitadasP){
        Double probabilidad=0.0;
        Double suma=1.0;
        int tamano=ciudadesNoVisitadasP.length;
        int index=0;
        
        //esto es para la suma que divide para obtener a probabilidad
        for(int k=0;k<tamano;k++){
            index=ciudadesNoVisitadasP[k];//obtiene del conjunto no visitados el valor
//            System.out.println(" index->"+index+" k="+k+"   feromonas="+feromonas.matrizFeromonas[i][index]+"  visibilidad="+visibilidad.matrizVisibilidad[i][index]);
            suma=suma+(
                    Math.pow(feromonas1.matrizFeromonas[i][index], (lambdaT*alfa))*
                    Math.pow(feromonas2.matrizFeromonas[i][index], ((1.0-lambdaT)*alfa))*                    
                    Math.pow(visibilidad1.matrizVisibilidad[i][index], (lambdaT*beta))*
                    Math.pow(visibilidad2.matrizVisibilidad[i][index], ((1.0-lambdaT)*beta)));
        }//for

        probabilidad=(
                Math.pow(feromonas1.matrizFeromonas[i][j], (lambdaT*alfa))*
                Math.pow(feromonas2.matrizFeromonas[i][j], ((1.0-lambdaT)*alfa))*                
                Math.pow(visibilidad1.matrizVisibilidad[i][j], (lambdaT*beta))*
                Math.pow(visibilidad2.matrizVisibilidad[i][j], ((1.0-lambdaT)*beta)))/suma;

//        System.out.println("Probabilidad="+probabilidad+" Suma="+suma);        
        
        return probabilidad; //retorna la probabilidad mas alta
    }//calcularProbabilidad
    
    public int seleccionarCiudad(int ciudadActual,int [] ciudadesNoVisitadasP){
        //por cada uno de los elementos del conjunto de no visitados
        //obtiene una probabilidad
        //la ciudad j será la proxima ciudad a visitar, 
        Double mejorProbabilidad=0.0;
        Double p=0.0;
        int ciudadElegida=0;
        
//        System.out.println("SELECCIONAR CIUDAD->");                
        for(int j=0;j<ciudadesNoVisitadasP.length;j++){
            p=calcularProbabilidadBIOBJ(ciudadActual, j, ciudadesNoVisitadasP);

//            System.out.println("CIUDAD actual->"+ciudadActual+" A  ciudadesNoVisitadas[j]="+ciudadesNoVisitadasP[j]+" p="+p);

            //si son iguales es 0, si d1<d2=-1; d1>d2=1;            
            if(Double.compare(p, mejorProbabilidad)==1){
                    //p>=mejorProbabilidad){
                mejorProbabilidad=p; //guardar la mejor probabilidad
                ciudadElegida=ciudadesNoVisitadasP[j]; //guarda la ciudad elegida
            }//mejorProbabilidad
        }//for        
        //devuelve la ciudad elegida que debe ser utilizada para acceder a la 
        //matriz, es el valor de la ciudad

//        System.out.println("SALTAR DESDE ciudad= "+ciudadActual+"  ciudadElegida= "+ciudadElegida);
        return ciudadElegida; //
        
    }//seleccionarCiudad
    
    
    public int [] construirSolucion(){       

        //-----------------------------------------------------------        
        //--- uso random para inicializar la variable aleatoria
        Random rnd=new Random();        
        rnd.setSeed(System.currentTimeMillis());
        //-----------------------------------------------------------

        //variable indice para elegir la ciudad a visitar
        int ciudadElegidaIndex;                

//        int [] ciudadesNoVisitadas={0,1,2,3,4,5,6,7,8,9,10};
        //se inicializa con el listados de ciudades no visitadas
        int [] ciudadesNoVisitadas=circuito1.getVectorCiudadesEnteros();
        //si o si recorre todas las ciudades, porque el grafo es simetrico
        int TAMANOLISTA=ciudadesNoVisitadas.length;
        //-------- vectores para encontrar la solucion del tour ---
        int [] ciudadesVisitadas=new int[TAMANOLISTA+1];//es una posicion mas grande para guardar el primer nodo
        int [] ciudadesTemporal=new int[TAMANOLISTA];
        int ultimaCiudad=0;//se utiliza para asignar el final del tour

//        System.out.println("HORMIGA CONSTRUIR SOLUCION -->");        
        //como son grafos simetricos, todos los nodos se conectan con todos, si o si se recorre
        //completo el vector lista de ciudades no visitadas
        int ciudadActual=0;
        int valor=0;

//        for(int contador=0;contador<4;contador++){        
        for(int contador=0;contador<TAMANOLISTA;contador++){
//            System.out.println("inicio iteracion");
            //Elige al azar una ciudad la primera vez
            if(contador==0){
//              System.out.print("PRIMERA CIUDAD AL AZAR->");
              ciudadElegidaIndex=rnd.nextInt(ciudadesNoVisitadas.length);
//              ciudadElegidaIndex=(int)(rnd.nextDouble()*(ciudadesNoVisitadas.length)+0);
//              ciudadElegidaIndex=(int)(Math.random()*(ciudadesNoVisitadas.length)+0);
              valor=ciudadesNoVisitadas[ciudadElegidaIndex];

//              System.out.println(ciudadElegidaIndex);
            }else{
//----------------------------------------
            //quiere decir que ya existe una ciudad visitada
            //desde la ciudad actual me trae la el indice de ciudad con mejor probabilidad
            //ciudadActual=ciudadesVisitadas[contador];//el valor de la ciudad en ciudades visitadas
            
            //traer el indice de la ciudad no visitada en arreglo no visitadas
            valor=seleccionarCiudad(ciudadActual,ciudadesNoVisitadas);
//            System.out.println("valor="+valor);
            //busca el valor en las ciudadesNoVisitadas para devolver su index
            ciudadElegidaIndex=0;            
            for(ciudadElegidaIndex=0;ciudadElegidaIndex<ciudadesNoVisitadas.length;ciudadElegidaIndex++){
                if(ciudadesNoVisitadas[ciudadElegidaIndex]==valor){
                    break;
                }
            }//for
//----------------------------------------                
            }//si no es la primera vez
//            System.out.println("De la ciudad="+ciudadActual+" a la ciudad="+valor);
                       

            ciudadesVisitadas[contador]=valor;


            //cuando es la primera vez, se procede a cargar una variable para asignar a la ultima
            //posicion del tour hamiltoniano
            //inicializa a cero el contador de no visitadas. Indica cuantas ciudades faltan
            int indexNoVisita=0;
            for(int fila=0;fila<ciudadesNoVisitadas.length;fila++){
                    //----------------------------------
                    //evalua para saltar la ciudad elegida previamente
                    if(fila!=ciudadElegidaIndex){
                        //traslada a un vector temporal que representa al nuevo conjunto
                        //no visitado
                        ciudadesTemporal[indexNoVisita]=ciudadesNoVisitadas[fila];
                        indexNoVisita++;//avanza contador
                    }//if
                    //----------------------------------
            }//for

            //------------------------------------------------
            //reinicializa para que tome el tamano
            ciudadesNoVisitadas=new int[indexNoVisita];
//            System.out.println("Largo ciudadesNoVisitadas->"+indexNoVisita);
            //copia los nuevos nodos sin visitar del temporal a su ubicacion final
            System.arraycopy(ciudadesTemporal, 0,ciudadesNoVisitadas,0, indexNoVisita );            
            //reinicializa ciudades temporal
            ciudadesTemporal=new int [indexNoVisita];
            
            //guarda el valor de la ciudad visitada, no el indice
            ciudadActual=ciudadesVisitadas[contador];
            //------------------------------------------------
/*            
            System.out.println("CIUDADES NO VISITADAS");            
            for(int i=0;i<ciudadesNoVisitadas.length;i++){
                System.out.print(ciudadesNoVisitadas[i]+" ");                
            }
            System.out.println();

            System.out.println("CIUDADES VISITADAS");            
            for(int i=0;i<ciudadesVisitadas.length;i++){
                System.out.print(ciudadesVisitadas[i]+" ");                
            }
            System.out.println();
            System.out.println("fin iteracion");            
*/
        }//fin contador                .

        //----------------------------------------------------------------------
        //almacena el ultimo valor obtenido de las ciudades no visitadas
        ciudadesVisitadas[TAMANOLISTA]=valor;
        //cierra el tour hamiltoniano, colocando el nodo de vuelta
        ciudadesVisitadas[TAMANOLISTA]=ciudadesVisitadas[0];        
        //----------------------------------------------------------------------                
    /*
        System.out.println("CIUDADES VISITADAS FINAL");            
            for(int i=0;i<ciudadesVisitadas.length;i++){
                System.out.print(ciudadesVisitadas[i]+" ");                
            }
            System.out.println();
    */    
            //se devuelven las ciudades visitadas
            return ciudadesVisitadas;
    }//fin contruir solucion
}//fin clase hormiga
