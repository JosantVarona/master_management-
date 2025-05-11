package dam.josantvarona.tfgbakend.Excepcions;

public class RecordNotFoundException extends RuntimeException {
    private String excepcion;
    private Object fieldValue;

    public RecordNotFoundException(String excepcion, Object fieldValue) {
        super(excepcion + " - " + fieldValue);
        this.excepcion = excepcion;
        this.fieldValue = fieldValue;
    }

    public String getExcepcion() {
        return excepcion;
    }

    public void setExcepcion(String excepcion) {
        this.excepcion = excepcion;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }
}
