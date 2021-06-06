package biblioteca.modelo;

import biblioteca.entidades.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AutorData {

    private Connection con;

    public AutorData(Conexion c) {
        con = c.getConnection();
    }

    public void agregarAutor(Autor autor) {
        String sql = "INSERT INTO autor (nombre_autor, apellido_autor, dni_autor, fech_nac, nacionalidad) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, autor.getNombreAutor());
            ps.setString(2, autor.getApellidoAutor());
            ps.setInt(3, autor.getDni());
            ps.setDate(4, Date.valueOf(autor.getFecha_nac()));
            ps.setString(5, autor.getNacionalidad());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                autor.setId_autor(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "El autor ha sido agregado");//Mensaje de agregado - *Se cambio la posicion en la que estaba.
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el id del autor");
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar un autor: " + ex.getMessage());
        }
    }

    public void actualizarAutor(Autor autor) {
        //Nombre del metodo - *modificado. Antes era modificarAutor.
        String sql = "UPDATE autor SET nombre_autor = ?, apellido_autor = ?, dni_autor = ?, fech_nac = ?, nacionalidad = ? WHERE id_autor = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, autor.getNombreAutor());
            ps.setString(2, autor.getApellidoAutor());
            ps.setInt(3, autor.getDni());
            ps.setDate(4, Date.valueOf(autor.getFecha_nac()));
            ps.setString(5, autor.getNacionalidad());
            ps.setInt(6, autor.getId_autor());

            ps.executeUpdate();
            ps.close();

            JOptionPane.showMessageDialog(null, "Se han actualizado los datos del Autor");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar un autor: " + ex.getMessage());
        }

    }

    public Autor buscarAutor(String nombre, String apellido) {//Nombre del metodo - *modificado. Antes era buscarLector.
        String query = "SELECT * FROM autor WHERE nombre_autor LIKE ? AND apellido_autor LIKE ?";
        Autor autor = null;
        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, nombre);
            ps.setString(2, apellido);

            ResultSet rs = ps.executeQuery();

            //Utilizo if porque si el autor con ese nombre y apellido existe seria solo un autor.
            //Pero puede que el while funcione mejor ya que pueden ver autores del mismo apellido y tendria que mostrarlos.
            if (rs.next()) {
                autor = new Autor();
                autor.setId_autor(rs.getInt(1));
                autor.setNombreAutor(rs.getString(2));
                autor.setApellidoAutor(rs.getString(3));
                autor.setDni(rs.getInt(4));
                autor.setFecha_nac(rs.getDate(5).toLocalDate());//dato en el parametro del setter - *modificado. Antes se usaba parse del LocalDate.
                autor.setNacionalidad(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado dicho Autor");
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar un autor: " + ex.getMessage());
        }

        return autor;
    }
    

    public List<Autor> obtenerAutores() {//Nombre del metodo - *Modificado. Antes era getAll().
        List<Autor> listaAutores = new ArrayList();
        String query = "SELECT * FROM autor";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Autor autor = new Autor();
                autor.setId_autor(rs.getInt(1));
                autor.setNombreAutor(rs.getString(2));
                autor.setApellidoAutor(rs.getString(3));
                autor.setDni(rs.getInt(4));
                autor.setFecha_nac(rs.getDate(5).toLocalDate());
                autor.setNacionalidad(rs.getString(6));
                listaAutores.add(autor);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar todos los autores: " + ex.getMessage());
        }
        
        return listaAutores;
    }
}
