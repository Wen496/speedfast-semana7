# SpeedFast - Persistencia de pedidos con JDBC (Semana 7)

Actividad formativa individual de Desarrollo Orientado a Objetos II,
Semana 7: "Conectando aplicaciones Java con bases de datos mediante JDBC".

## Descripcion

Continuacion de la interfaz grafica de la Semana 6. Ahora los pedidos,
repartidores y entregas se guardan en una base de datos MySQL mediante
JDBC, por lo que la informacion se mantiene al cerrar la aplicacion.

La aplicacion permite:

- Registrar pedidos (direccion y tipo: comida, encomienda o express).
- Registrar repartidores.
- Listar los pedidos almacenados en una JTable.
- Asignar un repartidor a un pedido pendiente, lo que registra una entrega
  con fecha y hora y cambia el estado del pedido a EN_REPARTO.

## Estructura del proyecto

- sql/speedfast_db.sql: script de creacion de la base de datos y sus tablas.
- lib: conector mysql-connector-j (.jar).
- src/modelo: clases Pedido, Repartidor y Entrega.
- src/dao: ConexionBD, PedidoDAO, RepartidorDAO y EntregaDAO.
- src/vista: VentanaPrincipal, VentanaRegistroPedido, VentanaRegistroRepartidor
  y VentanaListaPedidos.
- src/main: clase Main, punto de entrada de la aplicacion.

## Base de datos

Tablas repartidor, pedido y entrega. La tabla entrega tiene dos claves
foraneas: id_pedido hacia pedido(id) e id_repartidor hacia repartidor(id).
Los ids de las tres tablas son AUTO_INCREMENT, por eso el formulario de
pedidos ya no pide un ID: lo asigna MySQL al guardar.

Valores usados:
- tipo: COMIDA, ENCOMIENDA, EXPRESS
- estado: PENDIENTE, EN_REPARTO, ENTREGADO

## Clases DAO

ConexionBD: abre la conexion con DriverManager usando la URL, usuario y
contrasena de MySQL. Incluye el metodo cerrar() que cierra ResultSet,
Statement y Connection.

PedidoDAO: guardar(Pedido) inserta un pedido con PreparedStatement.
listarTodos() devuelve los pedidos con su repartidor asignado.
actualizarEstado(int, String) cambia el estado de un pedido.

RepartidorDAO: guardar(Repartidor) inserta un repartidor.
listarTodos() devuelve una List<Repartidor> leida con ResultSet.

EntregaDAO: guardar(Entrega) registra la relacion entre un pedido y un
repartidor, con fecha y hora.

Todos los metodos usan try-catch-finally: el catch maneja la SQLException
y el finally cierra los recursos.

## Como ejecutar en IntelliJ IDEA

1. Ejecuta sql/speedfast_db.sql en MySQL Workbench.
2. Abre la carpeta del proyecto con File > Open.
3. Si la carpeta src no queda marcada como Sources Root, hazlo con clic
   derecho sobre src > Mark Directory as > Sources Root.
4. Copia mysql-connector-j-26.7.0.jar en la carpeta lib. Luego clic
   derecho sobre el .jar > Add as Library.
5. En src/dao/ConexionBD.java reemplaza el valor de PASSWORD por la
   contrasena de tu usuario root de MySQL.
6. Ejecuta la clase Main (paquete main).

## Como compilar y ejecutar por linea de comandos

Desde la raiz del proyecto (en Windows se usa ; en lugar de :):

```
javac -d out src/modelo/*.java src/dao/*.java src/vista/*.java src/main/*.java
java -cp out:lib/mysql-connector-j-26.7.0.jar main.Main
```
