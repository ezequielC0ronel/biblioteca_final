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
        //LectorData ld = new LectorData(con);
        //EjemplarData ed = new EjemplarData(con);     
        PrestamoData pd = new PrestamoData(con);
        
        //AutorData test
        
        //agregarAutor(Autor autor) - Funcionando.
        //Primero necesitamos un Objeto tipo Autor para poder agregarlo luego en la tabla Autor.
        //Autor a1 = new Autor(38203765, "Roberto", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //Luego llamamos al metodo agregarAutor de AutorData para poder añadir el autor a la tabla Autor.
        //ad.agregarAutor(a1);
        
        //actualizarAutor(Autor autor) - Funcionando.
        //Necesitamos un Objeto tipo Autor para poder actualizarlo, esta vez, usando el constructor que permite un id_autor.
        //El id que colocamos es el que la base le asigno a este Autor.
        //Autor a1 = new Autor(4,38203765, "Roberto", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //a1.setNombreAutor("Roberto Alfredo");
        //ad.actualizarAutor(a1);
        
        //buscarAutor(String nombre, String apellido) - Funcionando.
        //System.out.println(ad.buscarAutor("Roberto Alfredo", "Fontanarrosa"));
        //System.out.println(ad.buscarAutor("Roberto", "Martin"));//Un autor que no existe.
        
        //obtenerAutores() - Funcionando.
        //System.out.println(ad.obtenerAutores());
        
        
        //LibroData test
        
        //agregarLibro(Libro libro) - Funcionando.
        //Autor a1 = new Autor(4,38203765, "Roberto Alfredo", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //Libro l1 = new Libro(a1, 734651, 1997, "Cuentos de fútbol argentino", "Alfaguara", "Ficcion");
        //lbd.agregarLibro(l1);
        
        //actualizarLibro(Libro libro) - Funcionando.
        //Autor a1 = new Autor(4,38203765, "Roberto Alfredo", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //Libro l1 = new Libro(3,a1, 734651, 1997, "Cuentos de fútbol argentino", "Alfaguara", "Ficcion");
        //l1.setISBN(534251);
        //lbd.actualizarLibro(l1);
        
        //buscarLibro(String nombre_libro) - Funcionando.
        /*for(Libro l :lbd.buscarLibro("Cuentos")){
            System.out.println(l);
        }*/
        
        //obtenerLibros() - Funcionando.
        /*for(Libro l : lbd.obtenerLibros()){
            System.out.println(l);
            System.out.println("-----------");
        }
        */
        
        
        
        //EjemplarData test
        
        //agregarEjemplar(Ejemplar ejemplar) - Funcionando.
        //Autor a1 = new Autor(4,38203765, "Roberto Alfredo", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //Libro l1 = new Libro(3, a1, 534251, 1997, "Cuentos de fútbol argentino", "Alfaguara", "Ficcion");
        //Ejemplar e1 = new Ejemplar(l1, "Disponible");
        //ed.agregarEjemplares(e1, 5);
        
        //actualizarEstado(Ejemplar ejemplar, String estado) - Funcionando.
        //Ejemplar e1 = new Ejemplar(20);
        //ed.actualizarEstado(e1, "Prestado");
        
        //ejemplarDisponible(int id_ejemplar) - Funcionando.
        //System.out.println(ed.ejemplarDisponible(15));//Ejemplar disponible;
        //System.out.println(ed.ejemplarDisponible(20));//Ejemplar no disponible;
        
        //estadoEjemplar(int id_ejemplar) - Funcionando.
        //System.out.println(ed.estadoEjemplar(14));//Ejemplar con estado "Prestado"
        
        
        
        
        //PrestamoData test
        
        //registrarPrestamo(Prestamo prestamo) - Funcionando.
        /*Lector lec1 = new Lector(8,42205836, "Ezequiel", "Coronel", "Tapas 321", true);
        Autor a1 = new Autor(3,38203765, "Roberto Alfredo", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        Libro l1 = new Libro(3, a1, 534251, 1997, "Cuentos de fútbol argentino", "Alfaguara", "Ficcion");
        Ejemplar e1 = new Ejemplar(23, l1);
        
        Prestamo p1 = new Prestamo(lec1, e1, true, LocalDate.now());
        
        pd.registrarPrestamo(p1);
        */
        
        //entregarPrestamo(Prestamo prestamo), modificarPrestamo(Prestamo prestamo) - Funcionando.
        /*Lector lec1 = new Lector(8,42205836, "Ezequiel", "Coronel", "Tapas 321", true);
        Autor a1 = new Autor(3,38203765, "Roberto Alfredo", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        Libro l1 = new Libro(3, a1, 534251, 1997, "Cuentos de fútbol argentino", "Alfaguara", "Ficcion");
        Ejemplar e1 = new Ejemplar(23, l1);
        
        Prestamo p1 = new Prestamo(20, lec1, e1, true, LocalDate.of(2021, Month.JUNE, 8));
        pd.entregarPrestamo(p1);
        */
        
        //anularPrestamo(Prestamo prestamo) - Funcionando.
        //Primero creamos un nuevo prestamo y luego lo registramos.
        //Lector lec1 = new Lector(8,42205836, "Ezequiel", "Coronel", "Tapas 321", true);
        //Autor a1 = new Autor(3,38203765, "Roberto Alfredo", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //Libro l1 = new Libro(3, a1, 534251, 1997, "Cuentos de fútbol argentino", "Alfaguara", "Ficcion");
        //Ejemplar e1 = new Ejemplar(21);
        
        //Prestamo p1 = new Prestamo(lec1, e1, true, LocalDate.now());
        
        //pd.registrarPrestamo(p1);
        
        //Se presenta un problema a la hora de generar el prestamo, procedemos a anular el prestamo.
        //Lector lec1 = new Lector(8,42205836, "Ezequiel", "Coronel", "Tapas 321", true);
        //Autor a1 = new Autor(3,38203765, "Roberto Alfredo", "Fontanarrosa", "Argentina", LocalDate.of(1994, Month.NOVEMBER, 26));
        //Libro l1 = new Libro(3, a1, 534251, 1997, "Cuentos de fútbol argentino", "Alfaguara", "Ficcion");
        //Ejemplar e1 = new Ejemplar(21);
        
        //Prestamo p1 = new Prestamo(22,lec1, e1, true, LocalDate.now()); //Esta vez usando el constructor que nos permite colocar el id_prestamo generado en la BD.
        //pd.anularPrestamo(p1);
        
        
    }
    
}
