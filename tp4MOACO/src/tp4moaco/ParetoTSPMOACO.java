/*
 * Funciones que contiene las funciones para manejo del conjunto Pareto
 */
package tp4moaco;

import java.util.Objects;
import java.util.Vector;
/**
 *
 * @author usuario
 */
public class ParetoTSPMOACO {    
      public int MINCONTEXTO=0; //para un contexto de minimizacion
      public int MAXCONTEXTO=1; //para un contexto de maximizacion
      public int CONTEXTOACTUAL=0; //  
//-----------------------------------------
    public ParetoTSPMOACO(int contexto){
        //---------------- INICIO INICIALIZAR CONFIGURACIONES ---
        CONTEXTOACTUAL=contexto;
        //---------------- FIN INICIALIZAR CONFIGURACIONES ---
    }//Constructor Pareto
//-----------------------------------------

    
    public int esIgual(Double[] uprima,Double[] vprima ){
        // cero para los falsos, uno para los verdaderos
        int nuprima=uprima.length;
        int nvprima=vprima.length;        
        int flagResultado=0; //asume que es falso
        int flagComparador=0; //asume que es falso
    
        if(nuprima==nvprima){
            //son iguales en tamano
            //entonces se compara cada elemento
            for(int i=0;i<nuprima;i++){
//                System.out.println(uprima[i]+" - - "+vprima[i]+" flagComparador="+flagComparador+" size="+nuprima+" "+nvprima);
//                System.out.println("Comparar="+Double.compare(uprima[i], vprima[i]));                
                //si son iguales es 0, si d1<d2=-1; d1>d2=1;
                if(Double.compare(uprima[i], vprima[i])==0){
                    //si son iguales no hace nada
                }else{
                    //prende cuando encuentra diferencias
                    flagComparador=1;                  
                }//nuprima[i]!=nvprima[i]
            }//fin for
            //CUANDO SE ROMPE EL CICLO o este TERMINA
            //algo pasó
//            System.out.println("ANTES DEL FLAG");
            if(flagComparador==0){
//            System.out.println("flagCompadaro==0");
                //todos los elementos son iguales
                return 1; //devuelve verdadero
            }else{
                //al menos un elemento es distinto
//            System.out.println("flagCompadaro==0");
                return 0; //devuelve falso            
            }//flagComparador==0                                    
        }else{
            return 0;
        }//fin nuprima==nvprima
    }//fin esIgual

    /**
     * 
     * @param uprima vector u
     * @param vprima vector v
     * @return Devuelve valor 1 si uprima <= prima
     * @return Devuelve valor 0 si no se cumple
     */
    public static int esMenorIgual(Double[] uprima,Double[] vprima ){
        // cero para los falsos, uno para los verdaderos
        int nuprima=uprima.length;
        int nvprima=vprima.length;        
        int flagResultado=0; //asume que es falso
        int flagComparador=0; //asume que es falso
    
        if(nuprima==nvprima){
            //son iguales en tamano
            //entonces se compara cada elemento
            //si fi(u)<=fi(v) termina como que es <=
            for(int i=0;i<nuprima;i++){
                if(uprima[i]>vprima[i]){
                    //prende cuando encuentra diferencias
                   flagComparador=1;
                   break; 
                }//nuprima[i]>nvprima[i]
            }//fin for
            //CUANDO SE ROMPE EL CICLO o este TERMINA
            //algo pasó
            if(flagComparador==0){
                //todos los elementos son menores iguales
                return 1; //devuelve verdadero
            }else{
                //al menos un elemento es mayor
                return 0; //devuelve falso            
            }//flagComparador==0                                    
        }else{
            return 0;//no son iguales en indice
        }//fin nuprima==nvprima
    }//fin esMenorIgual



    public static int esMayorIgual(Double[] uprima,Double[] vprima ){
        // cero para los falsos, uno para los verdaderos
        int nuprima=uprima.length;
        int nvprima=vprima.length;        
        int flagResultado=0; //asume que es falso
        int flagComparador=0; //asume que es falso
    
        if(nuprima==nvprima){
            //son iguales en tamano
            //entonces se compara cada elemento
            //si fi(u)>=fi(v) termina como que es <=
            for(int i=0;i<nuprima;i++){
                if(uprima[i]<vprima[i]){
                    //prende cuando encuentra diferencias
                   flagComparador=1;
                   break; 
                }//nuprima[i]>nvprima[i]
            }//fin for
            //CUANDO SE ROMPE EL CICLO o este TERMINA
            //algo pasó
            if(flagComparador==0){
                //todos los elementos son mayores iguales
                return 1; //devuelve verdadero
            }else{
                //al menos un elemento es menor
                return 0; //devuelve falso            
            }//flagComparador==0                                    
        }else{
            return 0;//no son iguales en indice
        }//fin nuprima==nvprima
    }//fin esMenorIgual


        public int esMayorAlMenosUno(Double[] uprima,Double[] vprima ){        
        // cero para los falsos, uno para los verdaderos
        int nuprima=uprima.length;
        int nvprima=vprima.length;        
        int flagResultado=0; //asume que es falso
        int flagComparador=0; //asume que es falso
    
        if(nuprima==nvprima){
            //son iguales en tamano
            //entonces se compara cada elemento
            //si fi(u)<fi(v) al menos una vez
            for(int i=0;i<nuprima;i++){
                if(uprima[i]>vprima[i]){
                    //prende cuando encuentra diferencias
                   flagComparador=1;
                   break; 
                }//nuprima[i]>nvprima[i]
            }//fin for
            //CUANDO SE ROMPE EL CICLO o este TERMINA
            //algo pasó
            if(flagComparador==0){
                //todos los elementos son menores o 
                return 0; //devuelve verdadero
            }else{
                //al menos un elemento es menor
                return 1; //devuelve verdadero
            }//flagComparador==0                                    
        }else{
            return 0;//no son iguales en indice
        }//fin nuprima==nvprima                
    }//esMayorAlMenosuno

    
    
    
    public int esMenorAlMenosUno(Double[] uprima,Double[] vprima ){        
        // cero para los falsos, uno para los verdaderos
        int nuprima=uprima.length;
        int nvprima=vprima.length;        
        int flagResultado=0; //asume que es falso
        int flagComparador=0; //asume que es falso
    
        if(nuprima==nvprima){
            //son iguales en tamano
            //entonces se compara cada elemento
            //si fi(u)<fi(v) al menos una vez
            for(int i=0;i<nuprima;i++){
                if(uprima[i]<vprima[i]){
                    //prende cuando encuentra diferencias
                   flagComparador=1;
                   break; 
                }//nuprima[i]>nvprima[i]
            }//fin for
            //CUANDO SE ROMPE EL CICLO o este TERMINA
            //algo pasó
            if(flagComparador==0){
                //todos los elementos son mayores o 
                return 0; //devuelve verdadero
            }else{
                //al menos un elemento es menor
                return 1; //devuelve verdadero
            }//flagComparador==0                                    
        }else{
            return 0;//no son iguales en indice
        }//fin nuprima==nvprima                
    }//esMenorAlMenosuno

    
    
    public int esMenor(Double[] uprima,Double[] vprima ){
        int flagMenorIgual=0;
        int flagMenor=0;
        
        flagMenorIgual=esMenorIgual(uprima,vprima);
        flagMenor=esMenorAlMenosUno(uprima,vprima);

        if(flagMenorIgual==1 && flagMenor==1){
            //es menor
            return 1;
        }else{
            //no es menor, no cumple
            return 0;        
        }//flagMenorIgual==1 && flagIgual==0        
    }//fin esMenor    


    public int esMayor(Double[] uprima,Double[] vprima ){
        int flagMayorIgual=0;
        int flagIgual=0;
        
        flagMayorIgual=esMayorIgual(uprima,vprima);
        flagIgual=esIgual(uprima,vprima);

        if(flagMayorIgual==1 && flagIgual==0){
            //es menor
            return 1;
        }else{
            //no es menor, no cumple
            return 0;        
        }//flagMenorIgual==1 && flagIgual==0
        
    }//fin esMayor    


    
   //devuelve el resultado de comparar u contra v        
    public int dominanciaPareto(Double[] uprima,Double[] vprima, int CONTEXTO ){
        int resultadoDominancia=0;
        if(CONTEXTO==MINCONTEXTO){
            resultadoDominancia=esMenor(uprima,vprima);
        }
/*
        if(CONTEXTO==MAXCONTEXTO){
            resultadoDominancia=esMayor(uprima,vprima);;
        }//fin CONTEXTO==MINCONTEXTO
*/        
        return resultadoDominancia;
    }//fin dominanciaPareto
    
    
    
    /*
    De un listado de puntos, se obtienen los no dominados, PUEDE DEVOLVER VACIO
    */
    public SolucionTSP [] getConjuntoPareto(SolucionTSP [] listadoObjetivos){
        //----------------------------------------------------------------------
        Vector <SolucionTSP> conjuntoPareto=new Vector<SolucionTSP>();
        
        SolucionTSP u=new SolucionTSP();
        SolucionTSP v=new SolucionTSP();
        
//        System.out.println("Se compara dominancia de u contra el conjunto");
        
        for(int fila=0;fila<listadoObjetivos.length;fila++){
            //------------------------------------------------------------------
            //copiar del conjunto a v;
            u=listadoObjetivos[fila];
//            System.out.print("--->>>Seleccionado u=");
  //          u.mostrarSoluciones();
                        
            for(int i=0;i<listadoObjetivos.length;i++){
                v=new SolucionTSP();

                //copiar del conjunto a v;
                v=listadoObjetivos[i];

                if((dominanciaPareto(u.vectorSoluciones, v.vectorSoluciones, CONTEXTOACTUAL)==0) && (dominanciaPareto(v.vectorSoluciones, u.vectorSoluciones, CONTEXTOACTUAL)==0) && (esIgual(u.vectorSoluciones, v.vectorSoluciones)==0)){
                }else{
//                    System.out.println(" u comparable ->");
//                    u.mostrarSoluciones();
                    //------------------------------
                    if(esIgual(u.vectorSoluciones, v.vectorSoluciones)==1){
//                        System.out.println(" IGUALES, NO HACE NADA");
                    }else{
                    //------no son iguales
                       if(dominanciaPareto(u.vectorSoluciones, v.vectorSoluciones, CONTEXTOACTUAL)==1){
  //                          System.out.println("u domina ->");                        
                            listadoObjetivos[i].dominado=1;//marca dominado
  //                          listadoObjetivos[i].mostrarSoluciones();
                        }else{
    //                    System.out.println("u NO DOMINA a v en MINIMIZACION ->"+"v="+v[0]+","+v[1]);
                        }//fin comparar               
                //----- no son iguales
                    }//son iguales                     
                //------------------------------
                }//no comparables            
            }//fin for
        }//for

        //copia los NO DOMINADOS dominados
        for(int fila=0;fila<listadoObjetivos.length;fila++){
            if(listadoObjetivos[fila].dominado==0){                
                conjuntoPareto.add(listadoObjetivos[fila]);
            }//fin dominado
        }//for        
                
        //retorna convertido a vector
        return conjuntoPareto.toArray(new SolucionTSP[conjuntoPareto.size()]);
        //----------------------------------------------------------------------
    }//getConjuntoPareto




//-----------------------------------------    
}//Fin clase Pareto
