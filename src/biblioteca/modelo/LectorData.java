
package biblioteca.modelo;

import biblioteca.entidades.Lector;
import biblioteca.entidades.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JOptionPane;

public class LectorData {
    private Connection con;
    
    public LectorData(Conexion c){
        con = c.getConnection();
    }
    
    public void agregarLector (Lector lector){
        String sql = "INSERT INTO lector (nombre_lector,apellido_lector,dni_lector,dire_lector,estado_lector) VALUES (?,?,?,?,?)";   
    
        try {
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                  
            ps.setString(1, lector.getNombreLector());
            ps.setString(2, lector.getApellidoLector());
            ps.setInt(3, lector.getDniLector());
            ps.setString(4, lector.getDireLector());
            ps.setBoolean(5, lector.getEstado_lector());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()){//Añadido el setteo de id al lector - *Añadido. Se pedian las keys de la bd pero no se usaban, ahora se le asigna el id al lector que pasa por parametro.
                lector.setId_lector(rs.getInt(1));
                JOptionPane.showMessageDialog(null,"Se ha agregado su lector!");
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo obtener el id del lector");
            }
            ps.close();
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar lector: " + ex.getMessage());
        } 
    }  //FUNCIONA
    
    public void actualizarLector (Lector lector){
        String sql = "UPDATE lector SET nombre_lector=?,apellido_lector=?,dni_lector=?,dire_lector=?,estado_lector=? WHERE id_lector=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);//Ps sin devolver las keys - *Modificado. Antes devolvia las keys generadas.
                  
            ps.setString(1, lector.getNombreLector());
            ps.setString(2, lector.getApellidoLector());
            ps.setInt(3, lector.getDniLector());
            ps.setString(4, lector.getDireLector());
            ps.setBoolean(5, lector.getEstado_lector());
            ps.setInt(6, lector.getId_lector());
            
            ps.executeUpdate();
            ps.close();
            
            JOptionPane.showMessageDialog(null, "Se han actualizado los datos del Lector");
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar lector: " + ex.getMessage());
        } 
    }   //FUNCIONA
    
    public Lector buscarLector(int dni_lector){//parametro del metodo - *Modificado. Ahora el metodo toma como parametro el dni del Lector para poder buscarlo, antes usaba el id del lector.
        String query="SELECT * FROM lector WHERE lector.dni_lector=?";
        Lector lector=null;
        try{
            PreparedStatement ps=con.prepareStatement(query);
            
            ps.setInt(1,dni_lector);
            
            ResultSet rs=ps.executeQuery();
            
            if(rs.next()){//Ahora se usa if en vez de while - *Modificado. Si buscabamos como antes (por id) no saldrian varios lectores, solo un unico lector. 
                lector = new Lector();
                lector.setId_lector(rs.getInt(1));
                lector.setNombreLector(rs.getString(2));
                lector.setApellidoLector(rs.getString(3));
                lector.setDniLector(rs.getInt(4));
                lector.setDireLector(rs.getString(5));
                lector.setEstado_lector(rs.getBoolean(6));
            }else{
                JOptionPane.showMessageDialog(null, "No se ha encontrado dicho lector");//Añadido un mensaje si no existe un lector con ese dni.
            }
            
            ps.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al buscar lector: " + ex.getMessage());
        }
        return lector;
    }//funciona
    
    public void cambiarEstadoLector(int id_lector, boolean estado){
        String query="UPDATE lector SET estado_lector=? WHERE id_lector=?";
        try{
            PreparedStatement ps=con.prepareStatement(query);
            ps.setBoolean(1, estado);
            ps.setInt(2, id_lector);
            
            ps.executeUpdate();
            ps.close();
            
            if(estado){//Segun el cambio de estado del lector se muestra uno de los dos mensajes - *Añadido.
                JOptionPane.showMessageDialog(null, "El lector ha sido activado");
            }else{
                JOptionPane.showMessageDialog(null, "El Lector a sido desactivado");
            }
            
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al cambiar el estado del lector: " + ex.getMessage());
        }
        
    }//funciona 
    
    //Si contiene una multa con mas de 90 dias de retraso, se cambia el estado del lector a inactivo.
    public void chequeoEstadoLector(){
        String sql = "SELECT multa.id_prestamo, fecha_inicio FROM multa, prestamos WHERE multa.id_prestamo = prestamo.id_prestamo";
        TreeMap <Integer,LocalDate> aux = new TreeMap<>();
        Set <Integer> aux2 = aux.keySet();
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                aux.put(rs.getInt(1), rs.getDate(2).toLocalDate());
            }
            
            for(Integer it : aux2){
                long esp = ChronoUnit.DAYS.between(aux.get(it), LocalDate.now());
                System.out.println(esp);
                if(esp > 90){
                    Conexion conex = new Conexion();
                    PrestamoData pd = new PrestamoData(conex);
                    Prestamo preAux = pd.buscarPrestamo(it);
                    cambiarEstadoLector(preAux.getLector().getId_lector(), false);
                }
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al chequear el estado del lector:" + ex.getMessage());
        }
        
    }
}
