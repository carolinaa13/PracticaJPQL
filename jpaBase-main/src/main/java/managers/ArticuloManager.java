package managers;

import org.example.Articulo;
import org.example.Cliente;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticuloManager {
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public ArticuloManager(boolean anularShowSQL) {
        Map<String, Object> properties = new HashMap<>();
        if(anularShowSQL){
            // Desactivar el show_sql (si está activado en el persistence.xml o configuración por defecto)
            properties.put("hibernate.show_sql", "false");
        }else{
            properties.put("hibernate.show_sql", "true");
        }
        emf = Persistence.createEntityManagerFactory("example-unit", properties);
        em = emf.createEntityManager();

    }

    public List<Articulo> getArticulosXDenominacion(String denominacion){
        String jpql = "FROM Articulo WHERE denominacion LIKE :denominacion ORDER BY denominacion";
        Query query = em.createQuery(jpql);
        query.setParameter("denominacion",'%'+denominacion+'%');
        List<Articulo> articulos = query.getResultList();
        return articulos;
    }
   public List<Articulo> getartxCantvta(){
       String jpql = "SELECT a " +
               "FROM FacturaDetalle fd JOIN fd.articulo a " +
               "GROUP BY a.id, a.descripcion " + // Agrupar por los campos de 'a'
               "ORDER BY SUM(fd.cantidad) DESC";

       // Usamos TypedQuery porque sabemos que devolverá objetos Articulo
       TypedQuery<Articulo> query = em.createQuery(jpql, Articulo.class);

       List<Articulo> articulos = query.getResultList();

       return articulos;
   }
    public Articulo getArticuloMasCaroEnFactura(Long facturaId) {

        // 1. SELECCIONAMOS la entidad Artículo (fd.articulo).
        // 2. COMENZAMOS en FacturaDetalle (fd) porque tiene el precio de venta.
        // 3. FILTRAMOS por la factura específica (fd.factura.id = :idFactura).
        // 4. ORDENAMOS por el precio de venta (fd.precioVenta) de mayor a menor.
        String jpql = "SELECT a " +
                "FROM FacturaDetalle fd JOIN fd.articulo a " +
                "WHERE fd.factura.id = :idFactura " +
                "ORDER BY a.precioVenta DESC";

        TypedQuery<Articulo> query = em.createQuery(jpql, Articulo.class);
        query.setParameter("idFactura", facturaId);

        query.setMaxResults(1);

        try {
            // 6. Usamos getSingleResult() para obtener ese único artículo.
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Ocurre si la factura no tiene ningún detalle (ningún artículo)
            return null;
        }
    }
    public List<Articulo> getArticulosPorCodigoParcial(String codigoParcial) {

        // 1. SELECCIONAMOS la entidad Articulo (a).
        // 2. FILTRAMOS (WHERE) usando LIKE para una coincidencia parcial.
        String jpql = "SELECT a " +
                "FROM Articulo a " +
                "WHERE a.codigo LIKE :patron";

        TypedQuery<Articulo> query = em.createQuery(jpql, Articulo.class);

        // 3. CONSTRUIMOS EL PATRÓN:
        //    Añadimos '%' antes y después de la cadena de búsqueda.
        //    Si codigoParcial = "123", el patrón será "%123%".
        //    Esto buscará "123" en cualquier parte del campo 'codigo'.
        String patronDeBusqueda = "%" + codigoParcial + "%";

        // 4. Asignamos el patrón construido al parámetro :patron
        query.setParameter("patron", patronDeBusqueda);

        // 5. Obtenemos la lista de resultados
        return query.getResultList();
    }
    public List<Articulo> getArticulosSobrePrecioPromedio() {

        // 1. CONSULTA PRINCIPAL: Selecciona el artículo 'a'.
        // 2. FILTRO (WHERE): Compara el precio de 'a' (a.precio)
        //    con el resultado de la subconsulta.
        //
        // 3. SUBCONSULTA: (SELECT AVG(a2.precio) FROM Articulo a2)
        //    Calcula el promedio de precio de TODOS los artículos.
        //    Es crucial usar un alias diferente (a2) dentro de la subconsulta.
        String jpql = "SELECT a " +
                "FROM Articulo a " +
                "WHERE a.precioVenta > (SELECT AVG(a2.precioVenta) FROM Articulo a2)";

        // Creamos una TypedQuery, ya que devolverá objetos Articulo
        TypedQuery<Articulo> query = em.createQuery(jpql, Articulo.class);

        // Esta consulta no necesita parámetros

        // 5. Obtenemos la lista de resultados
        return query.getResultList();
    }
}
