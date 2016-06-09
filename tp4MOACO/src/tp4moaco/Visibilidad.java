package tp4moaco;

/**
 * Matriz de visibilidad, y sus funciones 
 * @author usuario
 */
public class Visibilidad {
    public Double [][] matrizVisibilidad;
    
    //arranca a partir de una matriz previamente creada
    public Visibilidad(Double [][] matrizDistancias){        
        matrizVisibilidad=new Double [matrizDistancias.length][matrizDistancias[0].length];        
        for(int fila=0;fila<matrizDistancias.length;fila++){
            for(int col=0;col<matrizDistancias[0].length;col++){
                if(matrizDistancias[fila][col]!=0){
                    matrizVisibilidad[fila][col]=1/matrizDistancias[fila][col];
                }else{
                    matrizVisibilidad[fila][col]=0.0;
                }//if
            }//for
        }//for
    }//fin constructor
        
    public void mostrarVisibilidad(){
        //se mueve utilizando el largo, asumiendo que es una matriz cuadrada
        //de feromonas
        System.out.println("MOSTRAR VISIBILIDAD");                
        for(int fila=0;fila<matrizVisibilidad.length;fila++){
            System.out.printf("%2d) ",fila);
            for(int col=0;col<matrizVisibilidad[0].length;col++){
                System.out.printf("%.6f ",matrizVisibilidad[fila][col]);
            }
            System.out.println();
        }
    }//fin Mostrar                    
}//fin clase Visibilidad
