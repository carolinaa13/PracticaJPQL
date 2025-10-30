Comenzamos a estudiar JPA y la implementación con Hibernate. (Proyecto de base )
1- Descargar el proyecto
2- Abrir en Intelligent Idea Community
3 - Este Proyecto utiliza gradle y la base de datos H2 en memoria para simplificar

Para qué sirve la claúsula EXISTS

La cláusula EXISTS en JPQL es un operador booleano que se utiliza en la cláusula WHERE (o HAVING)
para verificar si una subconsulta (subquery) devuelve al menos una fila.

Si la subconsulta encuentra una o más filas, EXISTS evalúa como TRUE.
Si la subconsulta no encuentra ninguna fila, EXISTS evalúa como FALSE.

La base de datos puede dejar de ejecutar la subconsulta tan pronto como encuentra la primera fila 
que cumple la condición, ya que no necesita contar ni recolectar todos los resultados.