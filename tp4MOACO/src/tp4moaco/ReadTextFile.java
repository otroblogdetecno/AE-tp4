/*
 * Maneja los archivos de texto
 */
package tp4moaco;

 import java.io.File;
 import java.io.FileNotFoundException;
 import java.lang.IllegalStateException;
 import java.util.Locale;
 import java.util.NoSuchElementException;
 import java.util.Scanner;

import java.util.Vector;


public class ReadTextFile {

    private Scanner input;
    private int LINEASCABECERA=7;
    private int TAMANO=0;
    
    //un vector de registros
    Vector<NodoCiudad> vector = new Vector<NodoCiudad>();
    NodoCiudad [] vectorP;
        
     public void openFile(String archivo,int lineasNum)
     {
        try
        {
           LINEASCABECERA=lineasNum; 
           String directorioActual = System.getProperty("user.dir");  
           input = new Scanner( new File( directorioActual+"/"+archivo ) );           
           //TAMANO=contarRecords();
        } // end try
        catch ( FileNotFoundException fileNotFoundException )
        {
            String directorioActual = System.getProperty("user.dir"); 
            System.out.println("--- DIRECTORIO=>"+directorioActual+"/"+archivo);
            System.err.println( "Error opening file." );
            System.err.println( "Coloque el archivo en :"+directorioActual );
           System.exit( 1 );
        } // end catch
     } // end method openFile     
     

    //cerrar archivo 
    public void closeFile()
    {
       if ( input != null )
            input.close(); // close file
    } // end method closeFile



     // read record from file
     public NodoCiudad [] readRecords()
     {
        int contadorFilas=0;
        String linea=" ";    
        
        NodoCiudad registro=new NodoCiudad();
        NodoCiudad [] arrayListado;
        
        int tamano=TAMANO;

                        
        try // read records from file using Scanner object
        {
           while ( input.hasNext() )
           {
               if(contadorFilas>LINEASCABECERA){
                   //lectura del registro
                   try{
                       registro=new NodoCiudad();
                       int index=Integer.valueOf(input.next());//
                       registro.coordenadaX=Double.valueOf(input.next());
                       registro.coordenadaY=Double.valueOf(input.next());                                          
                       //va agregando, facil y bonito
                       vector.add(registro);
                       //-------------------------------------
//                       System.out.println("lindo y bonito="+registro.coordenadaX+"-"+registro.coordenadaY);
                       //-------------------------------------                       
                   }catch(Exception e){
                   }//catch
               }//fin
               contadorFilas++;//avanza filas
           } // end while
           
        } // end try
        catch ( NoSuchElementException elementException )
        {
           System.err.println( "File improperly formed." );
           input.close();
           System.exit( 1 );
        } // end catch
        catch ( IllegalStateException stateException )
        {
           System.err.println( "Error reading from file." );
           System.exit( 1 );
        } // end catch
        //-------------        
        vectorP=vector.toArray(new NodoCiudad[vector.size()]);
        //-------------
        return vectorP;
        //-------------        
     } // end method readRecords
        
}//fin leer
