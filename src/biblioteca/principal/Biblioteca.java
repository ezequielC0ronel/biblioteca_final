/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.principal;
import biblioteca.modelo.*;
import biblioteca.entidades.*;
import java.time.LocalDate;
import java.time.Month;
/**
 *
 * @author Ezequiel Coronel
 */
public class Biblioteca {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexion con = new Conexion();
        AutorData ad = new AutorData(con);
        LectorData ld = new LectorData(con);
        LibroData libd = new LibroData(con);
        EjemplarData ed = new EjemplarData(con);
        
        
        
    }
    
}
