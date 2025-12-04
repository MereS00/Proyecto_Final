import javax.swing.*;
import java.awt.*;

public class FrmConfirmarAsesoria extends JFrame {

    private String estudiante;
    private String materia;
    private String profesor;
    private String fecha;
    private String hora;
    private String modalidad;

    public FrmConfirmarAsesoria(String estudiante, String materia, String profesor,
                                String fecha, String hora, String modalidad) {
        this.estudiante = estudiante;
        this.materia = materia;
        this.profesor = profesor;
        this.fecha = fecha;
        this.hora = hora;
        this.modalidad = modalidad;

        setTitle("Confirmar Asesoría");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1, 5, 5));

        add(new JLabel("Resumen de la asesoría:", SwingConstants.CENTER));
        add(new JLabel("Profesor: " + profesor));
        add(new JLabel("Materia: " + materia));
        add(new JLabel("Fecha: " + fecha));
        add(new JLabel("Hora: " + hora));
        add(new JLabel("Modalidad: " + modalidad));

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);
        add(panelBotones);

        btnConfirmar.addActionListener(e -> confirmar());
        btnCancelar.addActionListener(e -> {
            new MenuEstudiante(estudiante).setVisible(true);
            dispose();
        });
    }

    private void confirmar() {
        Asesoria a = new Asesoria(
                estudiante,
                profesor,
                materia,
                fecha,
                hora,
                modalidad,
                "Confirmada"
        );

        BaseDatos.insertarAsesoria(a);

        JOptionPane.showMessageDialog(this,
                "Tu asesoría se registró exitosamente",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE);

        new MenuEstudiante(estudiante).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        new FrmConfirmarAsesoria("Estudiante", "Cálculo II",
                "Prof. Ana García", "25/11/2025", "10:00", "Presencial").setVisible(true);
    }
}
