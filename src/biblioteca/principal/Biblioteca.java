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
        //AutorData ad = new AutorData(con);
        //LibroData lbd = new LibroData(con);
        LectorData ld = new LectorData(con);
        
        
        //AutorData test
        
        //agregarAutor(Autor autor) - Funcionando.
        //Primero necesitamos un Objeto tipo Autor para poder agregarlo luego en la tabla Autor.
        //Autor a1 = new Autor(38203765, "Roberto", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //Luego llamamos al metodo agregarAutor de AutorData para poder añadir el autor a la tabla Autor.
        //ad.agregarAutor(a1);
        
        //actualizarAutor(Autor autor) - Funcionando.
        //Necesitamos un Objeto tipo Autor para poder actualizarlo, esta vez, usando el constructor que permite un id_autor.
        //El id que colocamos es el que la base le asigno a este Autor.
        //Autor a1 = new Autor(8,38203765, "Roberto", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //a1.setNombreAutor("Roberto Alfredo");
        //ad.actualizarAutor(a1);
        
        //buscarAutor(String nombre, String apellido) - Funcionando.
        //System.out.println(ad.buscarAutor("Alejandra", "Pizarnik"));
        //System.out.println(ad.buscarAutor("Roberto", "Martin"));//Un autor que no existe.
        
        //obtenerAutores() - Funcionando.
        //System.out.println(ad.obtenerAutores());
        
        
        //LibroData test
        
        //agregarLibro(Libro libro) - Funcionando.
        //Autor a1 = new Autor(8,38203765, "Roberto Alfredo", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //Libro l1 = new Libro(a1, 734651, 1997, "Cuentos de fútbol argentino", "Alfaguara", "Ficcion");
        //lbd.agregarLibro(l1);
        
        //actualizarLibro(Libro libro) - Funcionando.
        //Autor a1 = new Autor(8,38203765, "Roberto Alfredo", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //Libro l1 = new Libro(4,a1, 734651, 1997, "Cuentos de fútbol argentino", "Alfaguara", "Ficcion");
        //l1.setISBN(534251);
        //lbd.actualizarLibro(l1);
        
        //buscarLibro(String nombre_libro) - Funcionando.
        /*for(Libro l :lbd.buscarLibro("Gaucho")){
            System.out.println(l);
        }
        */
        
        //obtenerLibros() - Funcionando.
        /*for(Libro l : lbd.obtenerLibros()){
            System.out.println(l);
            System.out.println("-----------");
        }
       ¨*/
        
        //EjemplarData test
        
    }
    
}
