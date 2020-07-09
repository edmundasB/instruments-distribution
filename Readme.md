### Build and install docker images:

1) Install backend application by running command: `docker build -t homework:instrument-distribution-back .`
2) Install frontend application by running command: `docker build -t homework:instrument-distribution-front .`

### Start services:
1) Start backend service on 8081 port: `docker run -p 8081:8081 homework:instrument-distribution-back`
2) Start frontend service on 3000 port: `docker run -it -p 3000:3000 homework:instrument-distribution-front`

Application REST API descriptions can be found: http://localhost:8081/swagger-ui.html

Frontend application can be found: http://localhost:3000/

You can use file **test-data.csv** to load data.

