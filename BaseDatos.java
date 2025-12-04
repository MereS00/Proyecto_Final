import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
    private static final String URL = "jdbc:sqlite:direcciones.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // INSERTAR ASESORÍA
    public static void insertarAsesoria(Asesoria a) {
        String sql = "INSERT INTO asesorias (estudiante, profesor, materia, fecha, hora, modalidad, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getEstudiante());
            ps.setString(2, a.getProfesor());
            ps.setString(3, a.getMateria());
            ps.setString(4, a.getFecha());
            ps.setString(5, a.getHora());
            ps.setString(6, a.getModalidad());
            ps.setString(7, a.getEstado());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // OBTENER ASESORÍAS DE UN ESTUDIANTE
    public static List<Asesoria> obtenerAsesoriasDeEstudiante(String estudiante) {
        List<Asesoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM asesorias WHERE estudiante = ?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estudiante);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Asesoria a = new Asesoria(
                        rs.getString("estudiante"),
                        rs.getString("profesor"),
                        rs.getString("materia"),
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getString("modalidad"),
                        rs.getString("estado")
                );

                a.setId(rs.getInt("id")); // Agregar este campo en tu clase Asesoria
                lista.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ACTUALIZAR ESTADO DE ASESORÍA
    public static void actualizarEstadoAsesoria(int id, String nuevoEstado) {
        String sql = "UPDATE asesorias SET estado = ? WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
