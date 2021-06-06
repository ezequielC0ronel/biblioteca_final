package biblioteca.modelo;

import biblioteca.entidades.Ejemplar;
import biblioteca.entidades.Lector;
import biblioteca.entidades.Prestamo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PrestamoData {

    private Connection con;
    private EjemplarData ed;
    private MultaData md;

    public PrestamoData(Conexion c) {
        con = c.getConnection();
        ed = new EjemplarData(c);
        md = new MultaData(c);
    }

    public void registrarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (id_lector, id_ejemplar, estado, fecha_prestamo) VALUES (?, ?, ?, ?)";
        boolean porFecha = md.prestamoXFecha(prestamo.getLector().getId_lector());
        boolean limite = limitePrestados(prestamo.getLector().getId_lector());
        boolean ejemplarDispo = ed.ejemplarDisponible(prestamo.getEjemplar().getId_ejemplar());
        boolean taActivo = lectorActivo(prestamo.getLector().getId_lector());

        try {

            if (!limite && ejemplarDispo && porFecha && taActivo) {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, prestamo.getLector().getId_lector());
                ps.setInt(2, prestamo.getEjemplar().getId_ejemplar());
                ps.setBoolean(3, prestamo.getEstado());//No deberia ser siempre true cada vez que se inicia un nuevo prestamo?
                ps.setDate(3, Date.valueOf(prestamo.getFecha_prestamo()));

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    prestamo.setIdPrestamo(rs.getInt(1));
//                Conexion conex = new Conexion();
//                EjemplarData ed = new EjemplarData(conex);
                    ed.actualizarEstadoEjemplar(prestamo.getEjemplar(), "Prestado");
                    JOptionPane.showMessageDialog(null, "El prestamo se ha registrado con exito!");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo obtener el id del prestamo.");
                }
            }else{
                if(!ejemplarDispo){
                    JOptionPane.showMessageDialog(null, "El ejemplar no se encuentra disponible!");
                }else if(limite && !porFecha){
                    JOptionPane.showMessageDialog(null, "Usted adeuda 3 libros y tiene multas activas!");
                }else if(limite){
                    JOptionPane.showMessageDialog(null, "Usted ya adeuda 3 libros!");
                }else if(!porFecha){
                    JOptionPane.showMessageDialog(null, "Usted tiene multas!");
                }else if(!taActivo){
                    JOptionPane.showMessageDialog(null, "Usted ha sido dado de baja, regularice su situacion!");
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar el prestamo: " + ex.getMessage());
        }
    }

    public void entregarPrestamo(Prestamo prestamo) {//Nombre de metodo - *Modificado.
        String sql = "UPDATE prestamos SET estado= ? WHERE id_prestamo = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setBoolean(1, false);
            ps.setInt(2, prestamo.getIdPrestamo());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se ha entregado su prestamo!");

            if ("Retraso".equals(ed.estadoEjemplar(prestamo.getEjemplar().getId_ejemplar()))) {
                //FALTA CALCULAR LA FECHA FIN DE LA MULTA.
                md.fechaFinal(prestamo.getIdPrestamo(), LocalDate.now());
            }

            ed.actualizarEstadoEjemplar(prestamo.getEjemplar(), "Disponible");

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al entregar el prestamo: " + ex.getMessage());
        }
    }

    public void anularPrestamo(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET estado = ? Where prestamos.id_prestamo = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setBoolean(1, false);
            ps.setInt(2, prestamo.getIdPrestamo());

            ps.executeUpdate();
            ps.close();

            JOptionPane.showMessageDialog(null, "Se ha anulado su prestamo!");

            ed.actualizarEstadoEjemplar(prestamo.getEjemplar(), "Disponible");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al anular un prestamo: " + ex.getMessage());
        }
    }

    public boolean limitePrestados(int id_lector) {
        String sql = "SELECT id_lector FROM prestamo WHERE prestamos.id_lector = ? AND estado = 1";
        int i = 0;
        boolean limite = false;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_lector);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                i++;
            }

            if (i >= 3) {
                limite = true;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar la cantidad de prestamos: " + ex.getMessage());
        }

        return limite;
    }

    public boolean lectorActivo(int id_lector) {
        String sql = "SELECT estado_lector FROM prestamos WHERE id_lector = ?";
        boolean lActivo = true;

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id_lector);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (!rs.getBoolean(1)) {
                    lActivo = false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al comprobar si el lector esta activo: " + ex.getMessage());
        }

        return lActivo;
    }

    public Prestamo buscarPrestamo(int id_prestamo) {
        String sql = "SELECT * FROM prestamos WHERE id_prestamo = ?";
        Prestamo prestamo = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_prestamo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                prestamo = new Prestamo(new Lector(), new Ejemplar());
                prestamo.setIdPrestamo(rs.getInt(1));
                prestamo.getLector().setId_lector(rs.getInt(2));
                prestamo.getEjemplar().setId_ejemplar(rs.getInt(3));
                prestamo.setEstado(rs.getBoolean(4));
                prestamo.setFecha_prestamo(rs.getDate(5).toLocalDate());

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el prestamo: " + ex.getMessage());
        }
        return prestamo;
    }

    public List<Prestamo> prestamosXFecha(LocalDate fecha) {
        ArrayList<Prestamo> prestamosDeLaFecha = new ArrayList<>();
        Prestamo prestamo;
        String sql = "SELECT * FROM prestamos WHERE fecha_prestamo = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(fecha));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prestamo = new Prestamo(new Lector(), new Ejemplar());
                prestamo.setIdPrestamo(rs.getInt(1));
                prestamo.getLector().setId_lector(rs.getInt(2));
                prestamo.getEjemplar().setId_ejemplar(rs.getInt(3));
                prestamo.setEstado(rs.getBoolean(4));
                prestamo.setFecha_prestamo(rs.getDate(5).toLocalDate());

                prestamosDeLaFecha.add(prestamo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los prestamos X fecha: " + ex.getMessage());
        }
        return prestamosDeLaFecha;
    }

    public List<Prestamo> prestamosVigentes() {
        ArrayList<Prestamo> pVigentes = new ArrayList<>();
        Prestamo prestamo;

        String sql = "SELECT * FROM prestamos WHERE estado = 1";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prestamo = new Prestamo(new Lector(), new Ejemplar());
                prestamo.setIdPrestamo(rs.getInt(1));
                prestamo.getLector().setId_lector(rs.getInt(2));
                prestamo.getEjemplar().setId_ejemplar(rs.getInt(3));
                prestamo.setEstado(rs.getBoolean(4));
                prestamo.setFecha_prestamo(rs.getDate(5).toLocalDate());
                pVigentes.add(prestamo);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los prestamos validos: " + ex.getMessage());
        }
        return pVigentes;
    }

    public List<Lector> prestamosVencidos() {
        ArrayList<Lector> lectoresPVencidos = new ArrayList<>();
        String sql = "SELECT lector.id_lector, lector.nombre_lector, lector.apellido_lector, lector.dni_lector, lector.dire_lector FROM lector JOIN prestamos ON lector.id_lector = prestamos.id_lector JOIN ejemplar ON ejemplar.id_ejemplar = prestamos.id_ejemplar AND ejemplar.estado = 'Retraso'";
        Lector lector;

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lector = new Lector();
                lector.setId_lector(rs.getInt(1));
                lector.setNombreLector(rs.getString(2));
                lector.setApellidoLector(rs.getString(3));
                lector.setDniLector(rs.getInt(4));
                lector.setDireLector(rs.getString(5));
                lector.setEstado_lector(rs.getBoolean(6));

                lectoresPVencidos.add(lector);
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar a los lectores con prestamos vencidos: " + ex.getMessage());
        }

        return lectoresPVencidos;
    }

    //multasActivasXMes Deberia estar en multaData.
}
