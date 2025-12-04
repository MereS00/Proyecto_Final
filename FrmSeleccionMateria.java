import javax.swing.*;
import java.awt.*;

public class FrmSeleccionMateria extends JFrame {

    private String estudiante;

    public FrmSeleccionMateria(String estudiante) {
        this.estudiante = estudiante;

        setTitle("Seleccionar Materia");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel lblTitulo = new JLabel("Agendar asesoría: Tu materia", SwingConstants.CENTER);

        JButton btnMat1 = new JButton("Cálculo II");
        JButton btnMat2 = new JButton("Programación OO");
        JButton btnMat3 = new JButton("Matrices");
        JButton btnMat4 = new JButton("Estadística");

        //Boton volver
        JButton btnVolver = new JButton("Volver");

        add(lblTitulo);
        add(btnMat1);
        add(btnMat2);
        add(btnMat3);
        add(btnMat4);
       
        add(btnVolver);
        

        btnMat1.addActionListener(e -> irAProfesor("Cálculo II"));
        btnMat2.addActionListener(e -> irAProfesor("Programación OO"));
        btnMat3.addActionListener(e -> irAProfesor("Matrices"));
        btnMat4.addActionListener(e -> irAProfesor("Estadística"));

        btnVolver.addActionListener(e -> {
            new MenuEstudiante(estudiante).setVisible(true);
            dispose();
        });
    }

    private void irAProfesor(String materia) {
        new FrmSeleccionProfesor(estudiante, materia).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        new FrmSeleccionMateria("Estudiante ").setVisible(true);
    }
}
