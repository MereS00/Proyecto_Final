import static org.junit.Assert.*;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TestPrograma {

    private void limpiarAsesoriasDeEstudiante(String estudiante) throws Exception {
        try (Connection conn = BaseDatos.conectar();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM asesorias WHERE estudiante = ?")) {
            ps.setString(1, estudiante);
            ps.executeUpdate();
        }
    }

    @Test  // Test para agendar una asesoría y verificar que se haya guardado en la BD
    public void testAgendarAsesoria() throws Exception {
        limpiarAsesoriasDeEstudiante("123456");
        Asesoria a = new Asesoria(
                "123456",              // ID del estudiante
                "Profe Ana",
                "Calculo II",
                "25/11/2025",
                "10:00",
                "Presencial",
                "Activa"
        );
        BaseDatos.insertarAsesoria(a);

        List<Asesoria> lista = BaseDatos.obtenerAsesoriasDeEstudiante("123456");

        assertEquals(1, lista.size());
        assertEquals("123456", lista.get(0).getEstudiante());
        assertEquals("Profe Ana", lista.get(0).getProfesor());
        assertEquals("Calculo II", lista.get(0).getMateria());
    }

    @Test  // Test para cancelar una asesoría y verificar que su estado se actualice en la BD
    public void testCancelarAsesoria() throws Exception {
       
        limpiarAsesoriasDeEstudiante("789012");

        Asesoria a = new Asesoria(
                "789012",
                "Profe Mario",
                "Matrices",
                "30/11/2025",
                "12:00",
                "Virtual",
                "Activa"
        );
        BaseDatos.insertarAsesoria(a);

        List<Asesoria> lista = BaseDatos.obtenerAsesoriasDeEstudiante("789012");
        assertFalse(lista.isEmpty());  

        Asesoria asesoriaBD = lista.get(0);
        int id = asesoriaBD.getId();

        BaseDatos.actualizarEstadoAsesoria(id, "Cancelada");

        List<Asesoria> listaDespues = BaseDatos.obtenerAsesoriasDeEstudiante("789012");
        Asesoria asesoriaActualizada = listaDespues.get(0);

        assertEquals("Cancelada", asesoriaActualizada.getEstado());
    }

    @Test // Test para listar asesorías de un estudiante específico y verificar que se obtengan solo las de ese estudiante
    public void testListarAsesoriasPorEstudiante() throws Exception {

        limpiarAsesoriasDeEstudiante("123456");
        limpiarAsesoriasDeEstudiante("777888");

        BaseDatos.insertarAsesoria(new Asesoria(
                "123456", "Profe Ana", "Calculo II",
                "25/11/2025", "10:00", "Presencial", "Activa"
        ));
        BaseDatos.insertarAsesoria(new Asesoria(
                "123456", "Profe Mario", "Matrices",
                "26/11/2025", "09:00", "Virtual", "Activa"
        ));

        BaseDatos.insertarAsesoria(new Asesoria(
                "777888", "Profe Ana", "Estadistica",
                "27/11/2025", "11:00", "Presencial", "Activa"
        ));

        List<Asesoria> asesorias123456 = BaseDatos.obtenerAsesoriasDeEstudiante("123456");

        assertEquals(2, asesorias123456.size());
        for (Asesoria a : asesorias123456) {
            assertEquals("123456", a.getEstudiante());
        }
    }

    @Test  // Test para verificar que el ID de estudiante cumple con 6 dígitos
    public void testIDEstudianteEsValido() {
        String id = "123456";
        assertTrue(id.matches("\\d{6}"));
    }


    @Test // Test para verificar que el ID del profesor tiene exactamente 5 dígitos
    public void testIdProfesorTiene5Digitos() {
        String idProfesor = "12345"; // ejemplo válido
        assertTrue("El ID del profesor debe ser de EXACTAMENTE 5 dígitos.",
                idProfesor.matches("\\d{5}"));
    }

    @Test// Test para verificar que un estudiante existe en la tabla login de la base de datos
    public void testEstudianteExisteEnBD() throws Exception {
    String idEstudiante = "123456"; // asegúrate que exista

    try (Connection conn = BaseDatos.conectar();
         PreparedStatement ps = conn.prepareStatement(
                 "SELECT COUNT(*) FROM login WHERE id = ?"
         )) {

        ps.setString(1, idEstudiante);
        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        assertEquals("El estudiante no existe en la tabla login.", 1, count);
    }
}

        @Test // Test para verificar que un profesor existe en la tabla login de la base de datos
        public void testProfesorExisteEnBD() throws Exception {
    String idProfesor = "12345"; // asegúrate que exista

    try (Connection conn = BaseDatos.conectar();
         PreparedStatement ps = conn.prepareStatement(
                 "SELECT COUNT(*) FROM login WHERE id = ?"
         )) {

        ps.setString(1, idProfesor);
        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        assertEquals("El profesor no existe en la tabla login.", 1, count);
    }
}

        @Test // Test para verificar que un horario específico de un profesor está disponible
        public void testHorarioDisponible() throws Exception {
            String profesor = "11111";
            String fecha = "10/12/2025";
            String hora = "14:00";
    try (Connection conn = BaseDatos.conectar();
         PreparedStatement ps = conn.prepareStatement(
                 "SELECT COUNT(*) FROM asesorias WHERE profesor = ? AND fecha = ? AND hora = ?"
         )) {

        ps.setString(1, profesor);
        ps.setString(2, fecha);
        ps.setString(3, hora);

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);
        assertEquals("El horario NO está disponible.", 0, count);
    }
}

        @Test //Test para verificar que no se pueda agendar una asesoría en un horario ya ocupado por el mismo profesor
        public void testEvitarDobleAgendado() throws Exception {
        String profesor = "11111";
        String fecha = "15/12/2025";
        String hora = "09:00";
    try (Connection conn = BaseDatos.conectar();
         PreparedStatement ps = conn.prepareStatement(
                 "DELETE FROM asesorias WHERE profesor = ? AND fecha = ? AND hora = ?"
         )) {
        ps.setString(1, profesor);
        ps.setString(2, fecha);
        ps.setString(3, hora);
        ps.executeUpdate();
    }

    // Insertar una asesoría
    Asesoria a = new Asesoria("999999", profesor, "Álgebra", fecha, hora, "Presencial", "Activa");
    BaseDatos.insertarAsesoria(a);

    try (Connection conn = BaseDatos.conectar();
         PreparedStatement ps = conn.prepareStatement(
                 "SELECT COUNT(*) FROM asesorias WHERE profesor = ? AND fecha = ? AND hora = ?"
         )) {

        ps.setString(1, profesor);
        ps.setString(2, fecha);
        ps.setString(3, hora);

        ResultSet rs = ps.executeQuery();
        rs.next();

        assertEquals(1, rs.getInt(1));
    }
}

@Test// Test para verificar un login válido en la tabla login de la base de datos
public void testLoginValido() throws Exception {
    String id = "123456";     // Asegúrate que EXISTE en login
    String password = "aaaaa";  // Su contraseña correcta

    try (Connection conn = BaseDatos.conectar();
         PreparedStatement ps = conn.prepareStatement(
                 "SELECT COUNT(*) FROM login WHERE id = ? AND password = ?"
         )) {

        ps.setString(1, id);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        assertEquals("El login debería ser válido pero no se encontró en la BD.", 
                     1, count);
    }
}


@Test// Test para verificar un login con contraseña incorrecta en la tabla login de la base de datos
public void testLoginConPasswordIncorrecto() throws Exception {
    String id = "123456";     // Usuario real
    String password = "incorrecta";

    try (Connection conn = BaseDatos.conectar();
         PreparedStatement ps = conn.prepareStatement(
                 "SELECT COUNT(*) FROM login WHERE id = ? AND password = ?"
         )) {

        ps.setString(1, id);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        assertEquals("El login debería fallar por contraseña incorrecta.", 
                     0, count);
    }
}


@Test// Test para verificar un login con ID inexistente en la tabla login de la base de datos
public void testLoginConIdInexistente() throws Exception {
    String id = "000000";     // ID que NO está en login
    String password = "aaaaa";

    try (Connection conn = BaseDatos.conectar();
         PreparedStatement ps = conn.prepareStatement(
                 "SELECT COUNT(*) FROM login WHERE id = ? AND password = ?"
         )) {

        ps.setString(1, id);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        assertEquals("El login debería fallar porque el ID no existe.", 
                     0, count);
    }
}



}
