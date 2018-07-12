package opr.capacidad.Utilidades;

public class Utilidades {

    public static final  String TABLA_USUARIO = "usuario";
    public static final  String TABLA_IMAGEN = "imagen";
    public static final  String CAMPO_ID = "id";
    public static final  String CAMPO_ID_IMAGEN = "id";
    public static final  String CAMPO_NOMBRE = "nombre";
    public static final  String CAMPO_ROL = "rol";
    public static final  String CAMPO_NOMBRE_IMAGEN = "nombre";


    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + " ("+CAMPO_ID+" INTEGER, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_ROL+" TEXT)";

    public static final String CREAR_TABLA_IMAGENES = "CREATE TABLE " + TABLA_IMAGEN + " ("+CAMPO_ID_IMAGEN+" INTEGER INTEGER AUTOINCREMENT, "+CAMPO_NOMBRE_IMAGEN+" TEXT)";

}
