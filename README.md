# Microsservice

## Objetivo 

O objetivo deste projeto foi criar microsserviços para a realização de cadastro de um veículo e a compra via boleto do mesmo.

## :rocket: Solução do problema
A solução do foi desenvolvida realizando um raciocínio lógico simples seguindo os seguintes passos:
    
Utilizando das vantagens da arquitetura de microsserviços realizei a criação dos seguintes projetos:
    -Api-Gateway
    -Config-Server
    -Service-Register utilizando Eureka
    -Deal Service
    -Order Service

Primeiramente realizei a criação dos microsserviços deal e order. O microsserviço Deal é responsável pelo CRUD do anúncio e suas respectivas validações. 
Já o microsserviço Order é o responsável por pela "compra" do deal, neste serviço é recebido o parâmetro do dealId que é uma referência ao anúncio e, 
como parâmetro é validado via rest ''sh http://localhost:9999/deal/api/v1.0/{id}''. 

A porta do serviço de deal neste projeto é a 8084, mas como podemos ver na url anterior o rest se conecta ao serviço de porta 9999, 
esta é a porta do [Api-Gateway] e com o response podemos validar se o anúncio existe ou não, caso o deal não existe é reportado erro. Caso contrário a order de compra é criada corretamente.


## :computer: Tecnologias

  - Java 11
  - Spring boot 2.3.1
  - Spring Fox (Swagger-ui)
  - Lombok
  - JUnit para testes unitários e de integração
  
  #### **Utilitários**

- Commit Conventional: **[Commitlint][commitlint]**
- Teste de API: **[Insomnia][insomnia]**

## **:wine_glass: COMO UTILIZAR**

Primeiramente é necessário ter instalado em sua máquina o [jdk_11] da linguagem [Java] e o [maven], após realizar a instalação e configuração do jdk e do maven em sua máquina, você pode clonar o projeto utilizando um dos comandos abaixo.

Se já possui o ssh configurado no git de sua máquina Q

```sh
  git clone git@github.com:EngTaian/order.git
  git clone git@github.com:EngTaian/deal.git
  git clone git@github.com:EngTaian/api-gateway.git
  git clone git@github.com:EngTaian/config-server.git
  git clone git@github.com:EngTaian/service-registry.git
```

Caso não tenha o ssh configurado pode utilizar o https 

```sh
  git clone https://github.com/EngTaian/order.git
  git clone https://github.com/EngTaian/deal.git
  git clone https://github.com/EngTaian/config-server.git
  git clone https://github.com/EngTaian/service-registry.git
  git clone https://github.com/EngTaian/api-gateway.git
```

Com a pasta dos projetos em sua máquina, podemos acessar cada pasta e utilizar o maven para realizar o build do projeto.

```sh
  mvn install
```
para executar sem executar os teste podemos utilizar o comando.

```sh
  mvn install -DskipTests
```
Executando um dos comandos acima o build será realizado nos projetos, agora podemos acessar a pasta target que foi criada em cada projeto executar o comando como no exemplo abaixo.
````sh
  java -jar nome_do_projeto-0.0.1-SNAPSHOT.jar
````
Sendo o nome_do_projeto o nome do projeto ao qual você está acessando. Exemplo
````sh
  java -jar order-0.0.1-SNAPSHOT.jar
````

A sequência correta para executar esse projeto é:
````sh
  java -jar order-0.0.1-SNAPSHOT.jar
````
````sh
  java -jar deal-0.0.1-SNAPSHOT.jar
````
````sh
  java -jar service-registry-0.0.1-SNAPSHOT.jar
````
````sh
  java -jar config-repo-0.0.1-SNAPSHOT.jar
````
````sh
  java -jar api-gateway-0.0.1-SNAPSHOT.jar
````
Pronto o projeto foi executado e você já pode acessar o projeto.
   
> **_Nota:_**  Também é possível acessar a API utilizando o Insomnia ou Postman. 


<!-- Techs -->

[commitlint]: https://github.com/conventional-changelog/commitlint
[insomnia]: https://insomnia.rest/
[jdk_11]: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
[Java]: https://pt.wikipedia.org/wiki/Java_(linguagem_de_programa%C3%A7%C3%A3o)
[maven]: https://maven.apache.org/download.cgi
[Api-Gateway]:https://github.com/caelum/apostila-microservices-com-spring-cloud/blob/master/06-api-gateway.md