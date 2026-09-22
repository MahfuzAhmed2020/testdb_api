## TestDB API Project
Target: JENKINS INTEGRATION
the project performs CRUD operations and display the data on UI

it has two tables in the Docker Desktop

To run this project: 
PS C:\Users\mahfu\Desktop\testdb_api> docker-compose up -d   
then follow: http://localhost:8080/

Docker_Database: 
docker exec -it testdb_mysql mysql -u root -p 

docker exec -it testdb_mysql mysql -u root -proot testdb

http://localhost:8080/

## ================jenkins=======================

http://localhost:8081/job/testdb_api/
admin root

## ===================swagger========================


http://localhost:8080/swagger-ui.html 


http://localhost:8080/swagger-ui/index.html



## ===================================================

## TO BUILD DOCKER IMAGE
docker stop $(docker ps -q) 
mvn clean package     

docker build -t mahfuzdocker20/tesrdb_api:latest .

docker-compose up -d 
docker ps     
   

