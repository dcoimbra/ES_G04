# Adventure Builder [![Build Status](https://travis-ci.com/tecnico-softeng/es18tg_4-project.svg?token=QrvBnxsJSb77gEVgCsc4&branch=develop)](https://travis-ci.com/tecnico-softeng/es18tg_4-project) [![codecov](https://codecov.io/gh/tecnico-softeng/es18tg_4-project/branch/master/graph/badge.svg?token=pf3I6YTLlc)](https://codecov.io/gh/tecnico-softeng/es18tg_4-project)



To run tests execute: mvn clean install

To see the coverage reports, go to <module name>/target/site/jacoco/index.html.


|   Number   |          Name           |                  Email                  |   GitHub Username  | Group |
| ---------- | ----------------------- | --------------------------------------- | -------------------| ----- |
|   84766    |     Samuel Santos       |  samuel.c.santos@tecnico.ulisboa.pt     |    santos-samuel   |   2   |
|   84729    | João Francisco Almeida  | joao.santos.almeida@tecnico.ulisboa.pt  |     JFMSAlmeida    |   2   |
|   84704    |    Bernardo Andrade     | bernardo.andrade@tecnico.ulisboa.pt     |       Berhart      |   2   |
|   84708    |     David Coimbra       |  david.coimbra@tecnico.ulisboa.pt       |    HiveMindize     |   1   |
|   84727    |      Ivan Zarro         |       ivan.zarro@tecnico.ulisboa.pt     |     ivancivel      |   2   |
|   84842    |      José Ferrão        |       jozeus.alves@gmail.com            |       jozeus       |   1   |
|   84750    |      Nuno Bombico       |     nuno.bombico@tecnico.ulisboa.pt     |    nunoBombico1    |   1   |

- **Group 1: Bank, Activity, Hotel, Car**
- **Group 2: Broker, Tax**

### Infrastructure

This project includes the persistent layer, as offered by the FénixFramework.
This part of the project requires to create databases in mysql as defined in `resources/fenix-framework.properties` of each module.

See the lab about the FénixFramework for further details.

#### Docker (Alternative to installing Mysql in your machine)

To use a containerized version of mysql, follow these stesp:

```
docker-compose -f local.dev.yml up -d
docker exec -it mysql sh
```

Once logged into the container, enter the mysql interactive console

```
mysql --password
```

And create the 7 databases for the project as specified in
the `resources/fenix-framework.properties`.
