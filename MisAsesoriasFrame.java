import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MisAsesoriasFrame extends JFrame {

    private JTable tabla;
    private JButton btnCancelar, btnActualizar, btnVolver;
    private DefaultTableModel modelo;
    private String estudiante;
    private List<Integer> indicesBase; // índices reales en BaseDatos

    public MisAsesoriasFrame(String estudiante) {
        this.estudiante = estudiante;
        this.indicesBase = new ArrayList<>();

        setTitle("Mis Asesorías - " + estudiante);
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(
                new Object[]{"Profesor", "Materia", "Fecha", "Hora", "Estado", "Modalidad"},
                0
        );

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new FlowLayout());

        btnCancelar = new JButton("Cancelar Asesoría");
        btnActualizar = new JButton("Actualizar");
        btnVolver = new JButton("Volver");

        panelBotones.add(btnCancelar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarAsesoriasReales();

        btnActualizar.addActionListener(e -> cargarAsesoriasReales());

        btnVolver.addActionListener(e -> {
            new MenuEstudiante(estudiante).setVisible(true);
            dispose();
        });

        btnCancelar.addActionListener(e -> cancelarAsesoria());
    }

    private void cargarAsesoriasReales() {

        modelo.setRowCount(0);
        indicesBase.clear();

        for (Asesoria a : BaseDatos.obtenerAsesoriasDeEstudiante(estudiante)) {

            modelo.addRow(new Object[]{
                    a.getProfesor(),
                    a.getMateria(),
                    a.getFecha(),
                    a.getHora(),
                    a.getEstado(),
                    a.getModalidad()
            });

            indicesBase.add(a.getId()); // guardas id real en DB
        }
    }

    private void cancelarAsesoria() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una asesoría para cancelar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que desea cancelar esta asesoría?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            int indiceReal = indicesBase.get(fila);
            int idReal = indicesBase.get(fila);
            BaseDatos.actualizarEstadoAsesoria(idReal, "Cancelada");
            modelo.setValueAt("Cancelada", fila, 4);


            modelo.setValueAt("Cancelada", fila, 4);

            JOptionPane.showMessageDialog(this, "Asesoría cancelada");
        }
    }

    public static void main(String[] args) {
        new MisAsesoriasFrame("Estudiante").setVisible(true);
    }
}
