
package biblioteca.modelo;

import biblioteca.entidades.Multa;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MultaData {
    private Connection con;
    
    public MultaData(Conexion c){
        con = c.getConnection();
    }
    
    public void agregarMulta (Multa multa){
        String sql = "INSERT INTO multa (fecha_inicio, id_prestamo) VALUES (?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
            ps.setDate(1, Date.valueOf(multa.getFecha_inicio()));
            ps.setInt(2, multa.getPrestamo().getIdPrestamo());
     
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()){
                multa.setId_multa(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "La multa se ha registrado con exito!");
            }
            else{
                JOptionPane.showMessageDialog(null, "No se pudo obtener el id de la multa");
            }
            
            ps.close();
        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al agregar multa: " + ex.getMessage());
        }   
    }   //Agrega solo la fecha de inicio de la multa, la fecha fin se agrega en modificarPrestamo (PrestamoData)

   public boolean prestamoXFecha (int id_lector){
       String sql = "SELECT fecha_fin FROM multa,prestamos WHERE multa.id_prestamo = prestamos.id_prestamo AND prestamos.id_lector = ?";
       ArrayList <Date> aux = new ArrayList <> ();
       boolean multaSaldada = true;
       try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, id_lector);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                aux.add(rs.getDate(1));
            }
                
            for (int f = 0; f<aux.size();f++){
            
            if (aux.get(f).toLocalDate().isAfter(LocalDate.now())){
                multaSaldada = false;
            }    
            
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al comprobar las multas saldadas: " + ex.getMessage());
        }
       return multaSaldada;
   }  //Chequea las multas saldadas para poder agregar un prestamo
   
   public void anularMulta (Multa multa){
       String sql = "UPDATE multa SET fecha_fin=? id_prestamo=? WHERE multa.id_multa=?";   
       
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, multa.getId_multa());
            ps.setInt(3, multa.getPrestamo().getIdPrestamo());
            
            ps.executeQuery();
            ps.close();
            JOptionPane.showMessageDialog(null, "Se ha anulado su multa!");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al anular una multa: " + ex.getMessage());
        }
       
       
   }   //Anula una multa poniendole a la multa la fecha de hoy mismo
   
   public void fechaFinal (int id_prestamo, LocalDate fecha_fin){
       String sql = "UPDATE multa SET fecha_fin =? WHERE id_prestamo=?";
       
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setDate(1, Date.valueOf(fecha_fin));
            ps.setInt(2, id_prestamo);
            
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al darle fecha final a la multa: " + ex.getMessage());
        }
   }  //Metodo para agregar la fecha_fin de multa en modificarPrestamo (PrestamoData)
}
