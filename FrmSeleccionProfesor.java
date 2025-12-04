import javax.swing.*;
import java.awt.*;

public class FrmSeleccionProfesor extends JFrame {

    private String estudiante;
    private String materia;

    public FrmSeleccionProfesor(String estudiante, String materia) {
        this.estudiante = estudiante;
        this.materia = materia;

        setTitle("Seleccionar Profesor");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel lblTitulo = new JLabel("Profesores para: " + materia, SwingConstants.CENTER);

        JButton btnP1 = new JButton("Prof. Ana García");
        JButton btnP2 = new JButton("Prof. Luis Torres");
        JButton btnP3 = new JButton("Prof. Carlos Díaz");
        JButton btnP4 = new JButton("Prof. María López");
        JButton btnVolver = new JButton("Volver");


        add(lblTitulo);
        add(btnP1);
        add(btnP2);
        add(btnP3);
        add(btnP4);
        add(btnVolver);

        btnP1.addActionListener(e -> irAHorario("Prof. Ana García"));
        btnP2.addActionListener(e -> irAHorario("Prof. Luis Torres"));
        btnP3.addActionListener(e -> irAHorario("Prof. Carlos Díaz"));
        btnP4.addActionListener(e -> irAHorario("Prof. María López"));
        btnVolver.addActionListener(e -> {
            new FrmSeleccionMateria(estudiante).setVisible(true);
            dispose();
        });
    }

    private void irAHorario(String profesor) {
        new FrmSeleccionHorario(estudiante, materia, profesor).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        new FrmSeleccionProfesor("Estudiante", "Cálculo II").setVisible(true);
    }
}
