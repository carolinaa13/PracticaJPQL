package managers;

import funciones.FuncionApp;
import org.example.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainConsultasJPQL {

    static boolean anularShowSQL = false;

    public static void main(String[] args) {

       // buscarFacturas();
        //buscarFacturasActivas();
        //buscarFacturasXNroComprobante();
        //buscarFacturasXRangoFechas();
        //buscarFacturasultimomes();
        //buscarFacturaXPtoVentaXNroComprobante();
        //buscarFacturasXCliente();
        //buscarClientemaxFacturas();
        //buscarFacturasXCuitCliente();
        //buscarFacturasXArticulo();
       //mostrarMaximoNroFactura();
       //buscarClientesXIds();
       //buscarClientesXRazonSocialParcial();
        //buscarclienteconExists();
        //buscarClientes();
        //buscarArtmasvendido();
        //buscarFacturasXClientexplazo();
        //totalFacturadoxCliente();
        //mostrarArticulosporFactura();
        //mostrarArticulomascaroenFactura();
        //buscarTotalFacturas();
        //buscarFacturasxmontoMayor();
       // buscarFacturasxNombreArt();
        //listarArticulosxNombreParcial();
        //buscarArticulosxPromedioPrecio();
    }


    public static void buscarFacturas(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            List<Factura> facturas = mFactura.getFacturas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasActivas(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            List<Factura> facturas = mFactura.getFacturasActivas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXNroComprobante(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            List<Factura> facturas = mFactura.getFacturasXNroComprobante(796910l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

   public static void buscarFacturasXRangoFechas(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaInicio = FuncionApp.restarSeisMeses(fechaActual);
            List<Factura> facturas = mFactura.buscarFacturasXRangoFechas(fechaInicio, fechaActual);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }
  public static void buscarFacturasultimomes(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaInicio = FuncionApp.restarUnMes(fechaActual);
            List<Factura> facturas = mFactura.buscarFacturasXRangoFechas(fechaInicio, fechaActual);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }
    public static void buscarFacturaXPtoVentaXNroComprobante(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            Factura factura = mFactura.getFacturaXPtoVentaXNroComprobante(2024, 796910l);
            mostrarFactura(factura);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXCliente(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            List<Factura> facturas = mFactura.getFacturasXCliente(7l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXCuitCliente(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            List<Factura> facturas = mFactura.getFacturasXCuitCliente("27236068981");
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXArticulo(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            List<Factura> facturas = mFactura.getFacturasXArticulo(6l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void mostrarMaximoNroFactura(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try {
            Long nroCompMax = mFactura.getMaxNroComprobanteFactura();
            System.out.println("N° " + nroCompMax);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }
public static void buscarClientemaxFacturas(){
    ClienteManager mCliente = new ClienteManager(anularShowSQL);
    try {
        List<Cliente> clientes = mCliente.getClientexfactura();
        for(Cliente cli : clientes){
            System.out.println("Id: " + cli.getId());
            System.out.println("CUIT: " + cli.getCuit());
            System.out.println("Razon Social: " + cli.getRazonSocial());
            System.out.println("-----------------");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }finally {
        mCliente.cerrarEntityManager();
    }
}
    public static void buscarClientesXIds(){
        ClienteManager mCliente = new ClienteManager(anularShowSQL);
        try {
            List<Long> idsClientes = new ArrayList<>();
            idsClientes.add(1l);
            idsClientes.add(2l);
            List<Cliente> clientes = mCliente.getClientesXIds(idsClientes);
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }

    public static void buscarClientesXRazonSocialParcial(){
        ClienteManager mCliente = new ClienteManager(anularShowSQL);
        try {
            List<Cliente> clientes = mCliente.getClientesXRazonSocialParcialmente("Lui");
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }
    public static void buscarClientes(){
        ClienteManager mCliente = new ClienteManager(anularShowSQL);
        try {
            List<Cliente> clientes = mCliente.getCliente();
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }
    /*Para qué sirve la claúsula EXISTS

La cláusula EXISTS en JPQL es un operador booleano que se utiliza en la cláusula WHERE (o HAVING)
para verificar si una subconsulta (subquery) devuelve al menos una fila.

Si la subconsulta encuentra una o más filas, EXISTS evalúa como TRUE.
Si la subconsulta no encuentra ninguna fila, EXISTS evalúa como FALSE.

La base de datos puede dejar de ejecutar la subconsulta tan pronto como encuentra la primera fila
que cumple la condición, ya que no necesita contar ni recolectar todos los resultados.*/

   public static void buscarclienteconExists(){
        ClienteManager mCliente = new ClienteManager(anularShowSQL);
        try{
            List<Cliente> clientes = mCliente.getClienteifexists();
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }
   public static void buscarArtmasvendido() {
       ArticuloManager mArticulo = new ArticuloManager(anularShowSQL);
       List<Articulo> articulos = mArticulo.getartxCantvta();
       for (Articulo art : articulos) {
           System.out.println("Codigo: " + art.getCodigo());
           System.out.println("CUIT: " + art.getDenominacion());
           System.out.println("-----------------");
       }
   }
   public static void buscarFacturasXClientexplazo(){
        ClienteManager mCliente = new ClienteManager(anularShowSQL);

        try {
            Long IdCLiente = 1L;
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaInicio = FuncionApp.restarTresMeses(fechaActual);
            List<Factura> facturas = mCliente.getFacturasXClientexrangofecha(IdCLiente,fechaInicio, fechaActual);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }
    public static void totalFacturadoxCliente(){
        ClienteManager mCliente =  new ClienteManager(anularShowSQL);
        try {
            List<Long> idsClientes = new ArrayList<>();
            idsClientes.add(1l);
            List<Cliente> clientes = mCliente.getClientesXIds(idsClientes);
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }
            double Total = mCliente.getTotalFacturadoPorCliente(1L);
            System.out.println("Total Facturado: $" + Total);


        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }
    public static void mostrarArticulosporFactura(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        try{
            List<Articulo> articulos= mFactura.getArticulosPorFactura(1L);
            for(Articulo art : articulos){
                System.out.println("Artículo ID: "+ art.getId());
                System.out.println("Nombre: "+ art.getDenominacion());
                System.out.println("Precio: $"+art.getPrecioVenta());
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }
    public static void mostrarArticulomascaroenFactura(){
        ArticuloManager mArticulo = new ArticuloManager(anularShowSQL);
        Articulo articulo = mArticulo.getArticuloMasCaroEnFactura(1L);
        System.out.println("El articulo mas caro de la factura es: "+ articulo.getDenominacion());
    }
public static void buscarTotalFacturas(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        Long totalFact = mFactura.getCantidadTotalFacturas();
    System.out.println("El total de Facturas generadas en el sistema es de: "+ totalFact);
}
public static void buscarFacturasxmontoMayor(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        List<Factura> facturas  = mFactura.getFacturasMayoresA(300d);
        for(Factura fac :facturas){
            System.out.println("Id Factura: "+ fac.getId());
            System.out.println("Nro. Comprobante: "+ fac.getNroComprobante());
            System.out.println("Fecha Comprobante: "+ fac.getFechaAlta());
        }
}
public static void buscarFacturasxNombreArt(){
        FacturaManager mFactura = new FacturaManager(anularShowSQL);
        List<Factura>facturas = mFactura.getFacturasPorNombreArticulo("Pera");
        for(Factura fac :facturas){
            System.out.println("Factura ID: "+ fac.getId());
            System.out.println("Nro. Comprobante: "+ fac.getNroComprobante());
        }
}
public static void listarArticulosxNombreParcial(){
        ArticuloManager mArticulo = new ArticuloManager(anularShowSQL);
        List<Articulo>articulos = mArticulo.getArticulosPorCodigoParcial("17");
        for(Articulo art :articulos){
            System.out.println("Articulo ID: "+ art.getId());
            System.out.println("Codigo de Articulo: "+ art.getCodigo());
            System.out.println("Denominacion: "+ art.getDenominacion());
            System.out.println("Precio de Venta: "+ art.getPrecioVenta());
        }
}
public static void buscarArticulosxPromedioPrecio(){
        ArticuloManager mArticulo = new ArticuloManager(anularShowSQL);
        List<Articulo>articulos = mArticulo.getArticulosSobrePrecioPromedio();
        for(Articulo art :articulos){
            System.out.println("Articulo ID: "+ art.getId());
            System.out.println("Codigo de Articulo: "+ art.getCodigo());
            System.out.println("Denominacion: "+ art.getDenominacion());
            System.out.println("Precio de Venta: "+ art.getPrecioVenta());
        }
}
    public static void mostrarFactura(Factura factura){
        List<Factura> facturas = new ArrayList<>();
        facturas.add(factura);
        mostrarFacturas(facturas);
    }

    public static void  mostrarFacturas(List<Factura> facturas){
        for(Factura fact : facturas){
            System.out.println("N° Comp: " + fact.getStrProVentaNroComprobante());
            System.out.println("Fecha: " + FuncionApp.formatLocalDateToString(fact.getFechaComprobante()));
            System.out.println("CUIT Cliente: " + FuncionApp.formatCuitConGuiones(fact.getCliente().getCuit()));
            System.out.println("Cliente: " + fact.getCliente().getRazonSocial() + " ("+fact.getCliente().getId() + ")");
            System.out.println("------Articulos------");
            for(FacturaDetalle detalle : fact.getDetallesFactura()){
                System.out.println(detalle.getArticulo().getDenominacion() + ", " + detalle.getCantidad() + " unidades, $" + FuncionApp.getFormatMilDecimal(detalle.getSubTotal(), 2));
            }
            System.out.println("Total: $" + FuncionApp.getFormatMilDecimal(fact.getTotal(),2));
            System.out.println("*************************");
        }
    }

}
