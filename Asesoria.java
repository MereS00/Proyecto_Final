public class Asesoria {
    
    private int id;
    private String estudiante;
    private String profesor;
    private String materia;
    private String fecha;
    private String hora;
    private String modalidad;
    private String estado;


public void setId(int id) { this.id = id; }
public int getId() { return id; }

    public Asesoria(String estudiante, String profesor, String materia,
                    String fecha, String hora, String modalidad, String estado) {
        this.estudiante = estudiante;
        this.profesor = profesor;
        this.materia = materia;
        this.fecha = fecha;
        this.hora = hora;
        this.modalidad = modalidad;
        this.estado = estado;
    }

    public String getEstudiante() { return estudiante; }
    public String getProfesor() { return profesor; }
    public String getMateria() { return materia; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getModalidad() { return modalidad; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
