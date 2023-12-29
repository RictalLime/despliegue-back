package mx.uv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DAO {
    private static Conexion c = new Conexion();

    public static List<Usuario> dameUsuarios() {
        Statement stm = null;
        ResultSet rs = null;
        Connection conn = null;
        List<Usuario> resultado = new ArrayList<>();

        conn = Conexion.getConnection();

        try {
            String sql = "SELECT * from usuarios";
            stm = (Statement) conn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                Usuario u = new Usuario(rs.getString("id"), rs.getString("nombre"), rs.getString("contraseña"), rs.getString("compras"));
                resultado.add(u);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            rs = null;
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                stm = null;
            }
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return resultado;
    }

    public static String crearUsuario(Usuario u) {
        PreparedStatement stm = null;
        Connection conn = null;
        String msj = "";

        conn = c.getConnection();
        try {
            String sql = "INSERT INTO usuarios (id, nombre, contraseña, compras) values (?,?,?,?)";
            stm = (PreparedStatement) conn.prepareStatement(sql);
            stm.setString(1, u.getId());
            stm.setString(2, u.getNombre());
            stm.setString(3, u.getContraseña());
            stm.setString(4, u.getCompras());
            if (stm.executeUpdate() > 0)
                msj = "usuario agregado";
            else
                msj = "usuario no agregado";

        } catch (Exception e) {
            System.out.println("Error al agregar los datos en la base de datos"+e.getMessage());
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                stm = null;
            }
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return msj;
    }

    public static Usuario obtenerUsuarioPorCredenciales(String nombre, String contraseña) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection conn = null;
        Usuario usuarioEncontrado = null;

        conn = Conexion.getConnection();

        try {
            String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contraseña = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, nombre);
            stm.setString(2, contraseña);
            rs = stm.executeQuery();

            if (rs.next()) {
                usuarioEncontrado = new Usuario(rs.getString("id"), rs.getString("nombre"),
                        rs.getString("contraseña"), rs.getString("compras"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeResources(conn, stm, rs);
        }

        return usuarioEncontrado;
    }

    public static void actualizarUsuario(Usuario usuario) {
        PreparedStatement stm = null;
        Connection conn = null;

        conn = Conexion.getConnection();

        try {
            String sql = "UPDATE usuarios SET nombre=?, contraseña=?, compras=? WHERE id=?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, usuario.getNombre());
            stm.setString(2, usuario.getContraseña());
            stm.setString(3, usuario.getCompras());
            stm.setString(4, usuario.getId());

            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeResources(conn, stm, null);
        }
    }

    private static void closeResources(Connection conn, Statement stm, ResultSet rs) {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        if (stm != null) {
            try {
                stm.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
