import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrmElegirModalidad extends JDialog {
    public enum Modalidad { PRESENCIAL, EN_LINEA, CANCELADO }

    private Modalidad seleccion = Modalidad.CANCELADO;

    private final JRadioButton rbPresencial = new JRadioButton("Presencial");
    private final JRadioButton rbEnLinea = new JRadioButton("En línea");
    private final JButton btnOk = new JButton("Aceptar");
    private final JButton btnCancelar = new JButton("Cancelar");


    private String estudiante;
    private String materia;
    private String profesor;
    private String fecha;
    private String hora;

    public FrmElegirModalidad(Window parent) {
        super(parent, "Elegir modalidad", ModalityType.APPLICATION_MODAL);
        initComponents();
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public FrmElegirModalidad(Window parent, String estudiante, String materia, String profesor, String fecha, String hora) {
        super(parent, "Elegir modalidad", ModalityType.APPLICATION_MODAL);
        this.estudiante = estudiante;
        this.materia = materia;
        this.profesor = profesor;
        this.fecha = fecha;
        this.hora = hora;
        initComponents();
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbPresencial);
        grupo.add(rbEnLinea);
        rbPresencial.setSelected(true);

        JPanel opciones = new JPanel(new GridLayout(2, 1, 6, 6));
        opciones.setBorder(BorderFactory.createTitledBorder("Modalidad"));
        opciones.add(rbPresencial);
        opciones.add(rbEnLinea);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(btnOk);
        botones.add(btnCancelar);

        btnOk.addActionListener(e -> {
            seleccion = rbPresencial.isSelected() ? Modalidad.PRESENCIAL : Modalidad.EN_LINEA;
           
            if (estudiante != null && materia != null && profesor != null && fecha != null && hora != null) {
                String modalidadStr = (seleccion == Modalidad.PRESENCIAL) ? "Presencial" : "En línea";
                new FrmConfirmarAsesoria(estudiante, materia, profesor, fecha, hora, modalidadStr).setVisible(true);
            }
            dispose();
        });

        btnCancelar.addActionListener(e -> {
            seleccion = Modalidad.CANCELADO;
            dispose();
        });

      
        getRootPane().registerKeyboardAction(e -> btnCancelar.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

  
        getRootPane().setDefaultButton(btnOk);

        Container c = getContentPane();
        c.setLayout(new BorderLayout(8, 8));
        c.add(opciones, BorderLayout.CENTER);
        c.add(botones, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(300, 150));
    }

    public Modalidad getSeleccion() {
        return seleccion;
    }

    /**
     * Muestra el diálogo de forma modal y retorna la modalidad seleccionada.
     * Devuelve Modalidad.CANCELADO si el usuario cancela o cierra el diálogo.
     */
    public static Modalidad showDialog(Window parent) {
        FrmElegirModalidad dlg = new FrmElegirModalidad(parent);
        dlg.setVisible(true);
        return dlg.getSeleccion();
    }

    //Muestra el diálogo con contexto y, si se acepta, abre FrmConfirmarAsesoria.
    public static Modalidad showDialog(Window parent, String estudiante, String materia, String profesor, String fecha, String hora) {
        FrmElegirModalidad dlg = new FrmElegirModalidad(parent, estudiante, materia, profesor, fecha, hora);
        dlg.setVisible(true);
        return dlg.getSeleccion();
    }

    
}
