package opr.capacidad.Entidades;

import java.io.Serializable;

public class Imagen implements Serializable {

    private Integer idImagen;

    private String nombreImagen;

    public Imagen(Integer idImagen, String nombreImagen) {
        this.idImagen = idImagen;
        this.nombreImagen = nombreImagen;
    }

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }
}