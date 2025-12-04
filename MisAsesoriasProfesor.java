import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MisAsesoriasProfesor extends JFrame {

    private final String profesorId;
    private final DefaultTableModel model;
    private final JTable table;

    public MisAsesoriasProfesor(String profesorId) {
        this.profesorId = profesorId;
        setTitle("Mis Asesorías - Profesor " + profesorId);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JLabel lblTitulo = new JLabel("Mis Asesorías", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(18f));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabla de asesorías 
        String[] cols = {"ID", "Fecha", "Hora", "Materia"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        btnEditar.addActionListener(this::accionEditar);
        btnEliminar.addActionListener(this::accionEliminar);
        btnVolver.addActionListener(e -> {
            new MenuProfesor(profesorId).setVisible(true);
            dispose();
        });

    }

    private void accionEditar(ActionEvent e) {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una asesoría para editar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String id = (String) model.getValueAt(fila, 0);
        JTextField idField = new JTextField(id);
        JTextField fechaField = new JTextField((String) model.getValueAt(fila, 1));
        JTextField horaField = new JTextField((String) model.getValueAt(fila, 2));
        JTextField materiaField = new JTextField((String) model.getValueAt(fila, 3));

        JPanel p = new JPanel(new GridLayout(4, 2, 6, 6));
        p.add(new JLabel("ID:")); p.add(idField);
        p.add(new JLabel("Fecha (YYYY-MM-DD):")); p.add(fechaField);
        p.add(new JLabel("Hora (HH:MM):")); p.add(horaField);
        p.add(new JLabel("Materia:")); p.add(materiaField);

        int opc = JOptionPane.showConfirmDialog(this, p, "Editar Asesoría", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opc == JOptionPane.OK_OPTION) {
            model.setValueAt(idField.getText().trim(), fila, 0);
            model.setValueAt(fechaField.getText().trim(), fila, 1);
            model.setValueAt(horaField.getText().trim(), fila, 2);
            model.setValueAt(materiaField.getText().trim(), fila, 3);
        }
    }

    private void accionEliminar(ActionEvent e) {
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una asesoría para eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar la asesoría seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(fila);
        }
    }

}