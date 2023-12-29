package mx.uv;

public class Usuario {
    String id;
    String nombre;
    String contraseña;
    String compras;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCompras() {
        return compras;
    }

    public void setCompras() {
        this.compras = compras;
    }

    public Usuario(String id, String nombre, String contraseña, String compras) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.compras = compras;
    }

}
