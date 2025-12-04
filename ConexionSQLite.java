import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {
	private static final String DB_URL = "jdbc:sqlite:direcciones.db";
    

	
	@SuppressWarnings("CallToPrintStackTrace")
	public static Connection conectar() {
		Connection conexion = null;
		try {
			// Cargar el driver JDBC para SQLite 
			Class.forName("org.sqlite.JDBC");

			// Obtener la conexión
			conexion = DriverManager.getConnection(DB_URL);
			System.out.println("Conexión exitosa a SQLite");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: No se encontró el driver SQLite");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error de conexión a la base de datos");
			e.printStackTrace();
		}
		return conexion;
	}

	public static void main(String[] args) {
		conectar();
	}
}
