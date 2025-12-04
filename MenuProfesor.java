import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuProfesor extends JFrame {

    private final String profesorId;

    public MenuProfesor(String profesorId) {
        this.profesorId = profesorId;
        initUI();
    }

    private void initUI() {
        setTitle("Menú Profesor - ID: " + profesorId);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JLabel lblBienvenida = new JLabel("Bienvenido, Profesor " + profesorId, SwingConstants.CENTER);
        lblBienvenida.setFont(lblBienvenida.getFont().deriveFont(16f));
        add(lblBienvenida, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton btnMisAsesorias = new JButton("Mis Asesorías");
        JButton btnCerrar = new JButton("Cerrar Sesión");
        panelCentro.add(btnMisAsesorias);
        panelCentro.add(btnCerrar);
        add(panelCentro, BorderLayout.CENTER);

        btnMisAsesorias.addActionListener(this::abrirMisAsesorias);
        btnCerrar.addActionListener(this::cerrarSesion);
    }

    private void abrirMisAsesorias(ActionEvent e) {
        // Abrir la ventana MisAsesoriasProfesor
        new MisAsesoriasProfesor(profesorId).setVisible(true);
        dispose();
    }

    private void cerrarSesion(ActionEvent e) {
        new LoginFrame().setVisible(true);
        dispose();
    }
}
