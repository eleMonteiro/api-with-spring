## Tecnologias Usadas no Projeto
* Java 11
* Gradle
* Spring Boot
* Spring Data (Hibernate/JPA)
* PostgreSQL
* FlyWay

## Testando a Aplicação
* Departamentos
    * `POST` Departamento 
        - ROTA: `localhost:8090/departamentos/`
        - Adicione o seguinte JSON ao Body ou Corpo da Requisição
    ```json
        {
            "dnome": "ADM"
        }
    ```

    * `PUT` Departamento 
        - ROTA: `localhost:8090/departamentos/1`
        - Adicione o seguinte JSON ao Body ou Corpo da Requisição
    ```json
        {
            "dnumero": 1,
            "dnome": "ADM",
            "gerente": {
                "cpf": "77766655511",
                "enome": "RAFAEL"
            }
        }
    ```

    * `DELETE` Departamento 
        - ROTA: `localhost:8090/departamentos/1`

    * `GET` Departamentos 
        - ROTA: `localhost:8090/departamentos/?pagina=0&tamanho=3`

* Empregados
    * `POST` Empregado
        - ROTA: `localhost:8090/empregados/`
        - Adicione o seguinte JSON ao Body ou Corpo da Requisição
    ```json
        {
            "cpf": "77766655511",
            "enome": "ELENILSON",
            "salario": 2000.0,
        }
    ```

    * `PUT` Empregado 
        - ROTA: `localhost:8090/empregados/77766655511`
        - Adicione o seguinte JSON ao Body ou Corpo da Requisição
    ```json
        {
            "cpf": "77766655511",
            "enome": "ELENILSON",
            "salario": 2000.0,
            "supervisor": {
                "cpf": "77766655522"
            },
            "departamento": {
                "dnumero": 1
            }
        }
    ```

    * `DELETE` Empregado 
        - ROTA: `localhost:8090/empregados/1`

    * `GET` Empregados 
        - ROTA: `localhost:8090/empregados/?pagina=0&tamanho=3`
        
* Projetos
    * `POST` Projeto 
        - ROTA: `localhost:8090/projetos/`
        - Adicione o seguinte JSON ao Body ou Corpo da Requisição
    ```json
        {
            "pnome": "TESTE 2",
            "departamento": {
                "dnumero": 1
            }
        }
    ```

    * `PUT` Projeto 
        - ROTA: `localhost:8090/projetos/1`
        - Adicione o seguinte JSON ao Body ou Corpo da Requisição
    ```json
        {
            "pnumero": 1,
            "pnome": "BIA - BOLSA DE INICIAÇÃO ACADÊMICA",
            "departamento": {
                "dnumero": 1
            }
        }
    ```

    * `DELETE` Projeto
        - ROTA: `localhost:8090/projetos/1`

    * `GET` Projetos 
        - ROTA: `localhost:8090/projetos/?pagina=0&tamanho=3`

* Trabalhos
    * `POST` Trabalho 
        - ROTA: `localhost:8090/trabalhos/`
        - Adicione o seguinte JSON ao Body ou Corpo da Requisição
    ```json
        {
            "empregado": {
                "cpf": "77766655522"
            },
            "projeto": {
                "pnumero": 1
            },
            "inicio": "2021-04-05",
            "termino": "2022-04-05"
        }
    ```

    * `PUT` Trabalho 
        - ROTA: `localhost:8090/trabalhos/1`
        - Adicione o seguinte JSON ao Body ou Corpo da Requisição
    ```json
        {
            "empregado": {
                "cpf": "77766655511"
            },
            "projeto": {
                "pnumero": 1
            },
            "inicio": "2021-04-05",
            "termino": "2022-04-05"
        }
    ```

    * `DELETE` Trabalho
        - ROTA: `localhost:8090/trabalhos/1`

    * `GET` Trabalhos 
        - ROTA: `localhost:8090/trabalhos/?pagina=0&tamanho=3`