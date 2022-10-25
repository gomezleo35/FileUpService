# API FileUpService

## Introduccion

En este proyecto vamos a utilizar la siguiente imagen de docker postgres para el poder instalar la base de datos.

## Archivos necesarios

- Docker
  - [Descargar Docker](https://www.docker.com/get-started)
  - [Instalar Docker](https://docs.docker.com/install/linux/docker-ce/ubuntu/)
  - [Instalar Docker Compose](https://docs.docker.com/compose/install/)

## Instalacion Base de datos

- Para poder instalar la base de datos, se debe ingresar al directorio donde se encuentra el archivo de configuracion de docker-compose osea en la carpeta dataBase/docker-compose.yml.

- Una vez ingresado en ese directorio, se debe ejecutar el comando docker-compose up -d para iniciar el servidor de base de datos.

- Para verificar si realmente se levanto correctamente el servidor de base de datos, se debe ejecutar el comando docker-compose ps para ver es estado del contenedor. Nos deberia mostrar el estado Up si se levanto correctamente.
