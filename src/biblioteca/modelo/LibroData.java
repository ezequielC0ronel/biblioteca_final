
package biblioteca.modelo;

import biblioteca.entidades.Autor;
import biblioteca.entidades.Libro;
import java.sql.*;
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
     
    public Libro buscarLibro(String nombre_libro){
       String query = "SELECT * FROM libro WHERE nombre LIKE ?";
       Libro libro = null;
        
        try {
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setString(1, nombre_libro + "%");
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){//Se busca UN libro.
                libro = new Libro();
                libro.setId_libro(rs.getInt(1));
                Autor autor = new Autor();
                libro.setAutor(autor);
                libro.getAutor().setId_autor(rs.getInt(2));
                libro.setISBN(rs.getInt(3));
                libro.setNombre(rs.getString(4));
                libro.setEditorial(rs.getString(5));
                libro.setAño(rs.getInt(6));
                libro.setTipo(rs.getString(7));
            }else{
                JOptionPane.showMessageDialog(null, "No se ha encontrado ese libro");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el libro: " + ex.getMessage());
        }
        
        return libro;
    }
}
