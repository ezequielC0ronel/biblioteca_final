package biblioteca.modelo;

import biblioteca.entidades.Ejemplar;
import biblioteca.entidades.Multa;
import biblioteca.entidades.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EjemplarData {

    private Connection con;
    private PrestamoData pd;
    private MultaData md;
    
    public EjemplarData(Conexion c) {
        con = c.getConnection();
        pd = new PrestamoData(c);
        md = new MultaData(c);
    }

    public void agregarEjemplares(Ejemplar ejemplar, int cant) {
        String sql = "INSERT INTO ejemplar (id_libro, estado) VALUES (?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, ejemplar.getLibro().getId_libro());
            ps.setString(2, "Disponible");

            for (int i = 0; i < cant; i++) {
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    ejemplar.setId_ejemplar(rs.getInt(1));
                }
            }
            JOptionPane.showMessageDialog(null, "Se han agregado sus ejemplares");
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar los ejemplares: " + ex.getMessage());
        }

    }

    public void actualizarEjemplar(Ejemplar ejemplar) {
        String sql = "UPDATE ejemplar SET estado = ?,id_libro = ? WHERE id_ejemplar=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, ejemplar.getEstado());
            ps.setInt(2, ejemplar.getLibro().getId_libro());
            ps.setInt(3, ejemplar.getId_ejemplar());

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "El ejemplar ha sido actualizado!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el ejemplar: " + ex.getMessage());
        }
    }

    public void actualizarEstado(Ejemplar ejemplar, String estado) {
        String sql = "UPDATE ejemplar SET estado = ? WHERE id_ejemplar = ? ";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, estado);
            ps.setInt(2, ejemplar.getId_ejemplar());

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Estado del ejemplar actualizado!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado del ejemplar: " + ex.getMessage());
        }
    }

    public boolean ejemplarDisponible(int id_ejemplar) {//Nos sirve para consultar si un ejemplar esta disponible;
        String sql = "SELECT estado From ejemplar WHERE ejemplar.id_ejemplar = ?";
        String aux;
        boolean disponible = false;

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id_ejemplar);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                aux = rs.getString(1);
                if (aux.equalsIgnoreCase("Disponible")) {//Controlar aux == null con NullPointerException.
                    disponible = true;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar la disponibilidad del ejemplar: " + ex.getMessage());
        }

        return disponible;
    }

    public void chequeoEstadoEjemplar() {
        String sql = "SELECT id_prestamo, fecha_prestamo FROM prestamos, ejemplar WHERE prestamos.id_ejemplar = ejemplar.id_ejemplar AND prestamos.estado = 1 AND ejemplar.estado = 'Prestado'";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int i = (int) ChronoUnit.DAYS.between(rs.getDate(2).toLocalDate(), LocalDate.now());
                if (i > 30) {
                    Prestamo prestamo = pd.buscarPrestamo(rs.getInt(1));
                    actualizarEstado(prestamo.getEjemplar(), "Retraso");
                    Multa multa = new Multa(prestamo, LocalDate.now());
                    md.agregarMulta(multa);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al chequear el estado del ejemplar: " + ex.getMessage());
        }
    }

    public String estadoEjemplar(int id_ejemplar) {//Retorna un string con el estado que tiene el ejemplar.
        String sql = "SELECT estado FROM ejemplar WHERE id_ejemplar = ?";
        String aux = null;

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id_ejemplar);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                aux = rs.getString(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar el estado del ejemplar: " + ex.getMessage());
        }

        return aux;

    }
}
