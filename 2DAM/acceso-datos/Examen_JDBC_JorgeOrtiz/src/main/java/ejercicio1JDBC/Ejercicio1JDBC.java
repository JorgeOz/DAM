package ejercicio1JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Ejercicio1JDBC {
    public static void main(String[] args) {
    	
        String url = "jdbc:mysql://localhost:3306/telepizza";
        String usuario = "root";
        String password = "mysql";

        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Identificador del pedido: ");
        int idPedido = scanner.nextInt();
        
        System.out.print("Identificador del producto: ");
        int idProducto = scanner.nextInt();
        
        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();

        try (Connection con = DriverManager.getConnection(url, usuario, password)) {

        	// PRIMERA PARTE - COMPROBACIONES 
        	
            // a) EL PEDIDO DEBE EXISTIR Y EN ESTADO PENDIENTE
            PreparedStatement ps = con.prepareStatement("SELECT estado FROM pedidos WHERE id_pedido = ?");
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.out.println("Error. El pedido no existe.");
                return;
            }
            
            if (!rs.getString("estado").equals("PENDIENTE")) {
                System.out.println("Error. Solo se pueden modificar pedidos en estado PENDIENTE.");
                return;
            }

            // b) El PRODUCTO DEBE EXISTIR Y TENER STOCK SUFICIENTE
            ps = con.prepareStatement("SELECT precio, stock FROM productos WHERE id_producto = ?");
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.out.println("Error. No se puede realizar un pedido para este producto.");
                return;
            }
            
            double precio = rs.getDouble("precio");
            int stock = rs.getInt("stock");
            
            if (cantidad > stock) {
                System.out.println("Error. No se puede realizar un pedido para este producto.");
                return;
            }

            // c) LA CANTIDAD DEBE SER MAYOR QUE CERO
            if (cantidad <= 0) {
                System.out.println("Error. La cantidad introducida es incorrecta.");
                return;
            }

            // Anadir el producto al pedido
            ps = con.prepareStatement("INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario) " + "VALUES (?, ?, ?, ?)");
            ps.setInt(1, idPedido);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precio);
            ps.executeUpdate();

            System.out.println("Producto anadido al pedido correctamente.");

        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        }
    }
}
