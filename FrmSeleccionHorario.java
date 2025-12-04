import javax.swing.*;
import java.awt.*;

public class FrmSeleccionHorario extends JFrame {

    private String estudiante;
    private String materia;
    private String profesor;

    public FrmSeleccionHorario(String estudiante, String materia, String profesor) {
        this.estudiante = estudiante;
        this.materia = materia;
        this.profesor = profesor;

        setTitle("Horarios disponibles");
        setSize(320, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel lblTitulo = new JLabel("Horarios de " + profesor, SwingConstants.CENTER);

        // Ejemplo de horarios
        JButton btnH1 = new JButton("04/12/2025 - 10:00");
        JButton btnH2 = new JButton("05/12/2025 - 12:00");
        JButton btnH3 = new JButton("08/12/2025 - 13:00");
        JButton btnH4 = new JButton("09/12/2025 - 09:00");
        JButton btnVolver = new JButton("Volver");


        add(lblTitulo);
        add(btnH1);
        add(btnH2);
        add(btnH3);
        add(btnH4);
        add(btnVolver);

        btnH1.addActionListener(e -> irAConfirmar("04/12/2025", "10:00"));
        btnH2.addActionListener(e -> irAConfirmar("05/12/2025", "12:00"));
        btnH3.addActionListener(e -> irAConfirmar("08/12/2025", "13:00"));
        btnH4.addActionListener(e -> irAConfirmar("09/12/2025", "09:00"));
        btnVolver.addActionListener(e -> {
            new FrmSeleccionProfesor(estudiante, materia).setVisible(true);
            dispose();
        });
    }

    private void irAConfirmar(String fecha, String hora) {
        // Mostrar diálogo para elegir modalidad
        FrmElegirModalidad.Modalidad modal = FrmElegirModalidad.showDialog(this);
        if (modal == FrmElegirModalidad.Modalidad.CANCELADO) {
            // Usuario canceló: no continuar
            return;
        }
        String modalidadStr = (modal == FrmElegirModalidad.Modalidad.PRESENCIAL) ? "Presencial" : "En línea";
        new FrmConfirmarAsesoria(estudiante, materia, profesor, fecha, hora, modalidadStr).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        new FrmSeleccionHorario("Estudiante", "Cálculo II", "Prof. Ana García").setVisible(true);
    }
}
