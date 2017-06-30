Partida Dados 0.9
================================

Aplicación desarrollada en Java que simula una partida de dados interactiva entre 
servidor y clientes. El programa nos permite lanzar tiradas de dados y contabilizar
los puntos obtenidos a la vez que competimos contra la máquina (o servidor), a su vez,
se registran los posibles nuevos records y se almacenan de forma persistente.

El programa hace uso de varias interfaces gráficas y diferentes elementos multimedia, 
desde los cuales podremos lanzar tiradas, ver el total de puntos, un resumen de la partida
actual, etc..

Tanto servidor como cliente se lanzan en procesos diferentes para permitir un uso simultáneo
de los mismos, posibilitando así, una ejecución multi proceso con diferentes clientes conectados 
a un mismo servidor.

Para facilitar la puesta en marcha de la aplicación se proporcionan varios ejecutables .jar con
el proyecto construido y listo para ser ejecutado de manera gráfica.

## Requisitos
- [Java] 7 o superior (para ejecutar la Aplicación)

## Entorno de desarrollo
La aplicación ha sido desarrollada utilizando el IDE [NetBeans] pero también es posible su 
importanción en [Eclipe] y demás IDE's.

## Licencia
Esta aplicación se ofrece bajo licencia [GPL versión 3].

[GPL versión 3]: https://www.gnu.org/licenses/gpl-3.0.en.html
[NetBeans]: https://netbeans.org/
[Eclipe]: https://eclipse.org/
[Java]: https://www.java.com/
