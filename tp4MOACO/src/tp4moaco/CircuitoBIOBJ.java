/*
 * Implementa las funciones de manejo del grafo hamiltoniano
 */
package tp4moaco;


public class CircuitoBIOBJ {
    
    //burma14
    public Double [][] lecturaDesdeArchivo;
        
    public int CANTIDADCIUDADES;//=lecturaDesdeArchivo.length; //se asume que representa la cantidad de ciudades
    //listado con los nodos para las ciudades
    public NodoCiudad [] listadoCiudades;//=new NodoCiudad[CANTIDADCIUDADES];
    
    // se utiliza para el calculo de las distancias entre ciudades
    public Double [][] matrizDistanciasCiudades;//=new Double[CANTIDADCIUDADES][CANTIDADCIUDADES];
    
    //archivo
    public ReadTextFile archivo = new ReadTextFile();
    
    
    public CircuitoBIOBJ(String archivoC){
        //----------------------------------               
        //archivo.openFile("burma14.tsp",7); //0..7
        //archivo.openFile("berlin52.tsp",5);
        //archivo.openFile("kroA100.tsp",5);        
        archivo.openFile(archivoC,5);                
        listadoCiudades=archivo.readRecords();
        archivo.closeFile();          
        //----------------------------------                
        //configurando las variables globales en el constructor
        CANTIDADCIUDADES=listadoCiudades.length;        
        matrizDistanciasCiudades=new Double[CANTIDADCIUDADES][CANTIDADCIUDADES];

        //cargaCiudadesLista();
        calcularMatrizDistanciasCiudades();
    }//CircuitoBIOBJ

    //Falta un constructor para que lea del archivo.
    
    public void cargaCiudadesLista(){
        for(int i=0;i<CANTIDADCIUDADES;i++){
            listadoCiudades[i]=new NodoCiudad(" ",lecturaDesdeArchivo[i][0],lecturaDesdeArchivo[i][1]);
        }//fin for          
    }
    
    public void mostrarCiudadesLista(){
        System.out.println("LISTA DE CIUDADES");
        for(int fila=0;fila<CANTIDADCIUDADES;fila++){
            System.out.printf("%2d) %s \n",fila,listadoCiudades[fila].toString());
        }//fin for              
    }
    
    //calcula distancia entre dos puntos de acuerdo a sus coordenadas
    public Double calcularDistanciaEuclideana(Double x1, Double y1,Double x2, Double y2){
            Double distancia=0.0;
            distancia=Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
            return distancia;
    }
    
    //crea una matriz de distancias entre los puntos
    public void calcularMatrizDistanciasCiudades(){
        NodoCiudad ciudad1;
        NodoCiudad ciudad2;        
        for(int fila=0;fila<CANTIDADCIUDADES;fila++){
            //elije una ciudad indice
            ciudad1=new NodoCiudad();
            ciudad1=listadoCiudades[fila];
            //recorre
            for(int col=0;col<CANTIDADCIUDADES;col++){
               ciudad2=new NodoCiudad();
               ciudad2=listadoCiudades[col];
               //en la diagonal principal es cero     
               if(fila==col){
                   matrizDistanciasCiudades[fila][col]=0.0;
               }else{
                   matrizDistanciasCiudades[fila][col]=calcularDistanciaEuclideana(ciudad1.coordenadaX,ciudad1.coordenadaY,ciudad2.coordenadaX, ciudad2.coordenadaY);
               }//fin if fila==col
            }//fin for columnas
        }//fin for filas
    }
    
    //recorre y muestra la matriz de distancias
    public void mostrarMatrizDistancias(){
        System.out.println("MATRIZ DE DISTANCIAS");
        for(int fila=0;fila<CANTIDADCIUDADES;fila++){
            System.out.printf("%2d) ",fila);
            for(int col=0;col<CANTIDADCIUDADES;col++){
                System.out.printf("%.3f ",matrizDistanciasCiudades[fila][col]);
            }//fin for columnas
            System.out.println();
        }//fin for filas
    }
    
    //datos del grafo
    public int getNumeroNodos(){
        return CANTIDADCIUDADES+1;    
    }

    public int getNumeroArcos(){
        int aristas=(CANTIDADCIUDADES*(CANTIDADCIUDADES-1))/2;
        return aristas;
    }
    
    //Desde el punto 1 al punto 2 calcula el reocrrido    
    //asume un recorrido bien formado, eso implica que tiene que venir
    //con el tamano adecuado CANTIDAD DE NODOS + 1 por ser hamiltoniano
    public Double getLongitudRecorrido(int [] recorrido){
        int tope=recorrido.length-1;
        Double suma=0.0;
        
        int i=0;
        int j=i+1;
        int punto1,punto2;
        
        for(int contador=0;contador<tope;contador++){
            //obtiene del listado el valor del nodo
            punto1=recorrido[i];
            punto2=recorrido[j];
            //guarda la suma del ciclo
            suma=suma+matrizDistanciasCiudades[punto1][punto2];
            //avanza lectura
            i=j;
            j=i+1;
        }//for
        
        return suma;
    }//getLongitudRecorrido
    
    //la distancia entre dos puntos usando matriz de distancias
    public Double getLongitudDosPuntos(int punto1,int punto2){
        Double longitud=0.0;        
        if(punto1 < CANTIDADCIUDADES && punto2 < CANTIDADCIUDADES){
            longitud=matrizDistanciasCiudades[punto1][punto2];
        }        
        return longitud;
    }//getLongitudDosPuntos
    
    public NodoCiudad [] getVectorCiudades(){
        
        return listadoCiudades;
    }    
    
    //se usa para indices de matrices de distancias
    //joder tio, que tecnologia
    public int [] getVectorCiudadesEnteros(){
        int [] listadoCiudadesEnteros=new int [listadoCiudades.length];
        
        for(int i=0;i<listadoCiudades.length;i++){
            listadoCiudadesEnteros[i]=i;
        }
        return listadoCiudadesEnteros;
    }        
    
}//fin CircuitoBIOBJ
