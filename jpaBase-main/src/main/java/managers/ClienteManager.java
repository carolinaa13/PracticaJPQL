package managers;

import funciones.FuncionApp;
import jakarta.persistence.*;
import org.example.Cliente;
import org.example.Factura;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteManager {
    EntityManagerFactory emf = null;
    EntityManager em = null;
    private Object clienteId;

    public ClienteManager(boolean anularShowSQL) {
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

    public List<Cliente> getClientesXIds(List<Long> idsClientes){
        String jpql = "FROM Cliente WHERE id IN (:idsClientes) ORDER BY razonSocial ASC";
        Query query = em.createQuery(jpql);
        query.setParameter("idsClientes", idsClientes);
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    public List<Cliente> getClientesXRazonSocialParcialmente(String razonSocial){
        StringBuilder jpql = new StringBuilder("SELECT DISTINCT(cliente) FROM Cliente cliente WHERE 1=1 ");
        if(!FuncionApp.isEmpty(razonSocial))
            jpql.append(this.parseSearchField("cliente.razonSocial", razonSocial));
        jpql.append(" ORDER BY cliente.razonSocial ASC");
        Query query = em.createQuery(jpql.toString());
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }
    public List<Cliente> getClientexfactura(){
        String jpql = "SELECT c FROM Cliente c JOIN c.facturas f " +
                "GROUP BY c " +
                "ORDER BY COUNT(f.Id) DESC";
        // Usamos TypedQuery para obtener objetos Cliente de forma segura
        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

        // 5. Usamos .setMaxResults(1) en lugar de 'LIMIT 1'
        query.setMaxResults(1);
        try {
            // 6. Usamos .getSingleResult() porque solo queremos un cliente
            List<Cliente> clientes = Collections.singletonList(query.getSingleResult());
            return clientes;
        } catch (NoResultException e) {
            // Manejar el caso donde no hay clientes/facturas
            return null;
        }
      }
   public List<Cliente> getClienteifexists(){
       String jpql = "SELECT c FROM Cliente c " + // <--- ESPACIO AQUÍ
               "WHERE EXISTS (" +
               "SELECT f FROM Factura f " + // <--- ESPACIO AQUÍ
               "WHERE f.cliente = c" +
               ")";
       Query query = em.createQuery(jpql);
      List<Cliente> clientes = query.getResultList();
       return clientes;
   }
    public List<Cliente> getCliente(){
        //String jpql = "FROM Cliente";
        String jpql = "SELECT c FROM Cliente c";
        Query query = em.createQuery(jpql);

        List<Cliente> clientes = query.getResultList();
        return clientes;
    }
    public List<Factura> getFacturasXClientexrangofecha(Long clienteId, LocalDate fechaInicio, LocalDate fechaActual){
        String jpql ="SELECT f FROM Factura f " +
                "WHERE f.cliente.id = :idCliente " +
                "AND f.fechaAlta BETWEEN :fechaInicio AND :fechaFin " +
                "ORDER BY f.fechaAlta DESC";
        // Crear y parametrizar la consulta ---
        TypedQuery<Factura> query = em.createQuery(jpql, Factura.class);

        // Pasamos los valores calculados a los parámetros de la consulta
        query.setParameter("idCliente", clienteId);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaActual);

        // Ejecutar la consulta
        List<Factura> facturas = query.getResultList();

        return facturas;
    }
    public Double getTotalFacturadoPorCliente(Long clienteId) {
        // 1. SELECCIONAMOS la SUMA del campo 'total' de la factura.
        // 2. FILTRAMOS (WHERE) navegando desde la factura (f)
        //    hasta el ID de su cliente (f.cliente.id).
        String jpql = "SELECT SUM(f.total) " +
                "FROM Factura f " +
                "WHERE f.cliente.id = :idDelCliente";

        TypedQuery<Double> query = em.createQuery(jpql, Double.class);

        // 3. Asignamos el valor al parámetro :idDelCliente
        query.setParameter("idDelCliente", clienteId);

        Double total = query.getSingleResult();

        // 5. Manejamos el caso de que SUM() devuelva NULL
        return (total != null) ? total : 0.0;
    }

    public void cerrarEntityManager(){
        em.close();
        emf.close();
    }

    public String parseSearchField(String field, String value) {

        String[] fields = new String[1];
        fields[0] = field;

        return this.parseSearchField(fields, value);
    }

    public String parseSearchField(String field[], String value) {
        if(value != null) {
            String[] values = value.split(" ");
            StringBuffer result = new StringBuffer();

            for(int i = 0; i < values.length; i++) {

                StringBuffer resultFields = new StringBuffer();

                if(!values[i].trim().equals("")){
                    result.append(" AND ");

                    for (int j = 0; j < field.length ; j++){

                        if (j!=0)
                            resultFields.append(" OR ");

                        resultFields.append(" (LOWER(" + field[j].trim() + ") LIKE '" + values[i].trim().toLowerCase() + "%' OR LOWER(" + field[j].trim() + ") LIKE '%" + values[i].trim().toLowerCase() + "%')");
                    }
                    result.append("(" + resultFields.toString() + ")");
                }
            }

            return result.toString();
        }

        return "";
    }
}
