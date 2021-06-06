
package biblioteca.modelo;

import biblioteca.entidades.Autor;
import biblioteca.entidades.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LibroData {
    private Connection con;
    
    public LibroData(Conexion c){
        con = c.getConnection();
    }
    
    public void agregarLibro(Libro libro){
        String sql = "INSERT INTO libro (id_autor,ISBN,nombre,editorial,año,tipo) VALUES(?,?,?,?,?,?)";
        
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
            ps.setInt(1, libro.getAutor().getId_autor());
            ps.setInt(2, libro.getISBN());
            ps.setString(3, libro.getNombre());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getAño());
            ps.setString(6, libro.getTipo());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()){
                libro.setId_libro(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "El libro ha sido agregado!");
            }
            else{
                JOptionPane.showMessageDialog(null, "No se pudo obtener el id de su libro");
            }
        
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar su libro: " + ex.getMessage());
        } 
    }   //FUNCIONA
    
    public void actualizarLibro (Libro libro){
        String sql = "UPDATE libro SET id_autor=?,ISBN=?,nombre=?,editorial=?,año=?,tipo=? WHERE id_libro=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, libro.getAutor().getId_autor());
            ps.setInt(2, libro.getISBN());
            ps.setString(3, libro.getNombre());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getAño());
            ps.setString(6, libro.getTipo());
            ps.setInt(7, libro.getId_libro());
            
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Su libro ha sido actualizado");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acualizar el libro: " + ex.getMessage());
        }
        
        
    }   //FUNCIONA
     
    public List<Libro> buscarLibro(String nombre_libro){
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT libro.id_libro,libro.id_autor, libro.ISBN,libro.nombre,libro.editorial,libro.año,libro.tipo,autor.nombre_autor,autor.apellido_autor,autor.dni_autor,autor.fech_nac,autor.nacionalidad FROM libro,autor WHERE libro.id_autor=autor.id_autor AND libro.nombre LIKE ?";
        String nombreDeLibro = "%"+nombre_libro+"%";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, nombreDeLibro);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Libro libro = new Libro();
                Autor autor = new Autor();
                autor.setId_autor(rs.getInt("id_autor"));
                autor.setApellidoAutor(rs.getString("apellido_autor"));
                autor.setDni(rs.getInt("dni_autor"));
                autor.setNombreAutor(rs.getString("nombre_autor"));
                autor.setFecha_nac(rs.getDate("fech_nac").toLocalDate());
                autor.setNacionalidad(rs.getString("nacionalidad"));
                libro.setAutor(autor);
                libro.setId_libro(rs.getInt("id_libro"));
                libro.setISBN(rs.getInt("ISBN"));
                libro.setNombre(rs.getString("nombre"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setAño(rs.getInt("año"));
                libro.setTipo(rs.getString("tipo"));
                libros.add(libro);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar libro: " + ex.getMessage());
        }
        
        return libros;
    }
    
    public List<Libro> obtenerLibros(){
        List<Libro> listaDeLibros = new ArrayList();
        String sql = "SELECT libro.id_libro,libro.id_autor, libro.ISBN,libro.nombre,libro.editorial,libro.año,libro.tipo,autor.nombre_autor,autor.apellido_autor,autor.dni_autor,autor.fech_nac,autor.nacionalidad FROM libro,autor WHERE libro.id_autor=autor.id_autor ";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Libro libro = new Libro();
                Autor autor=new Autor();
                autor.setId_autor(rs.getInt("id_autor"));
                autor.setApellidoAutor(rs.getString("apellido_autor"));
                autor.setDni(rs.getInt("dni_autor"));
                autor.setNombreAutor(rs.getString("nombre_autor"));
                autor.setFecha_nac(rs.getDate("fech_nac").toLocalDate());
                autor.setNacionalidad(rs.getString("nacionalidad"));
                libro.setAutor(autor);
                libro.setId_libro(rs.getInt("id_libro"));
                libro.setISBN(rs.getInt("ISBN"));
                libro.setNombre(rs.getString("nombre"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setAño(rs.getInt("año"));
                libro.setTipo(rs.getString("tipo"));
                listaDeLibros.add(libro);
            }
            ps.close();
            JOptionPane.showMessageDialog(null, "Lista de libros en base de datos");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los libros: " + ex.getMessage());
        }
        
        return listaDeLibros;
    }
}
