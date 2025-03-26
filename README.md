# Ryanair api challage 

in order to run the project

Pre-condition
Install Docker
Import app image to Docker like this: docker load -i api_testing_service_latest.tar.xz
Start demo app like this: docker run -d -p 8900:8900 --name apiservice api_testing_service

clone the project 
in the command line type "mvn test" 

final report will be available inside the reports folder as html file
