import javax.swing.*;
import java.awt.*;

public class MenuEstudiante extends JFrame {

    private JButton btnAgendar, btnMisAsesorias, btnCerrar;
    private String nombreUsuario;

    public MenuEstudiante(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;

        setTitle("Menú Principal - Estudiante");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel lblBienvenida = new JLabel("Bienvenido, " + nombreUsuario, SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        btnAgendar = new JButton("Agendar Asesoría");
        btnMisAsesorias = new JButton("Mis Asesorías");
        btnCerrar = new JButton("Cerrar Sesión");

        add(lblBienvenida);
        add(btnAgendar);
        add(btnMisAsesorias);
        add(btnCerrar);

        btnAgendar.addActionListener(e -> {
            new FrmSeleccionMateria(nombreUsuario).setVisible(true);
            dispose();
        });

        btnMisAsesorias.addActionListener(e -> {
            new MisAsesoriasFrame(nombreUsuario).setVisible(true);
            dispose();
        });

        btnCerrar.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        new MenuEstudiante("Estudiante").setVisible(true);
    }
}
