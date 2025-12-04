import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JComboBox<String> cmbTipo;

    public LoginFrame() {
        setTitle("Inicio de Sesión");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblTipo = new JLabel("Tipo:");
        JLabel lblUsuario = new JLabel("ID:");
        JLabel lblPassword = new JLabel("Contraseña:");

        cmbTipo = new JComboBox<>(new String[]{"Estudiante", "Profesor"});
        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Iniciar Sesión");

        add(lblTipo);
        add(cmbTipo);
        add(lblUsuario);
        add(txtUsuario);
        add(lblPassword);
        add(txtPassword);
        add(new JLabel());
        add(btnLogin);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese ID y contraseña",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // validación básica del ID: debe ser 5 o 6 dígitos
        if (!usuario.matches("\\d{5}|\\d{6}")) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe contener exactamente 5 (profesor) o 6 (estudiante) números.\nEjemplo profesor: 12345, estudiante: 123456",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // SQLite
        String url = "jdbc:sqlite:direcciones.db";
        String sql = "SELECT password FROM login WHERE id = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            String test= ps.toString(); // para depuración
            System.out.print(test);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // usuario y password coinciden
                    JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");

        
                    if (usuario.length() == 6) {
                        new MenuEstudiante(usuario).setVisible(true);
                    } else if (usuario.length() == 5) {
                        new MenuProfesor(usuario).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "ID con formato inesperado",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Usuario o contraseña incorrectos",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoginFrame().setVisible(true);
    }
}
