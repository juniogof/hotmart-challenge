#Introdução
Aplicação criada para o [desafio desenvolvedor back-end](https://github.com/Hotmart-Org/hotmart-challenge/tree/master/back-end) da [Hotmart](http://www.hotmart.com).

#Sobre
A aplicação foi desenvolvida usando [Spring Boot](https://spring.io/projects/spring-boot) na versão 2.3 junto a linguagem [Java](https://java.com/) em sua versão 1.8. O banco de dados usado foi [MySQL](https://www.mysql.com/).

#Bibliotecas usadas
- [Spring boot](https://spring.io/projects/spring-boot) - facilitador de configuração e publicação
- [Spring data](https://spring.io/projects/spring-data) - facilitador para banco de dados
- [Spring boot dev tools](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html) - facilitador de desenvolvimento
- [Spring boot cache](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-caching.html) - para realização de cache das buscas
- [Hystrix](https://spring.io/guides/gs/circuit-breaker/) - para tolerância a falha na chamada da biblioteca externa
- [JUnit4](https://junit.org/junit4/) - para criação dos testes unitários
- [Mockito](https://site.mockito.org/) - para mock dos testes unitários
- [FlyAway](https://flywaydb.org/) - para versionamento dos scripts de carga
- [Lombok](https://projectlombok.org/) - facilitador e clean code
- [Hibernate validator](https://hibernate.org/validator/documentation/getting-started/) - para validação dos dados enviados para API
- [Swagger](https://swagger.io/) - para documentação da API

#Considerações de ambiente
- Todas as configurações, como banco de dados e URL da News API, se encontram no arquivo `application.properties`.
- O *FlyAway* precisa do banco de dados previamente criado para executar os *scripts* de versionamento `create database hotmart_challenge;`. 
- Na pasta raiz do projeto você pode usar os seguintes comandos:  
1) `mvnw clean install`: para compilar.  
2) `mvnw spring-boot::run`: para disponibilzar a aplicação.  
3) `mvnw test`: para executar os testes unitários.  
4) `mvnw spring-boot:build-image`: para gerar a imagem [Docker](https://www.docker.com/) o [Spring Boot 2.3 oferece um facilitador](https://spring.io/guides/gs/spring-boot-docker/)  
- Para ter acesso a documentação da API, após disponibilizar a aplicação, você deve acessar o endereço [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Caso deseje abrir o projeto em alguma IDE você irá precisar instalar o [plugin do Lombok](https://projectlombok.org/setup/eclipse).
- O diagrama do banco de dados encontra-se na pasta raiz do projeto `diagram.png`.
- Uma coleção [Postman](https://www.postman.com/) com chamadas a API também se encontra na pasta raiz do projeto `hotmart-challenge.postman_collection.json`.

#Considerações de negócio
- `Score = X + Y + X`: considerei que houve um erro de digitação e o construi composto por X + Y + **Z**.
- `O output do serviço deve conter as informações dataAtual e termoPesquisado, bem como a lista de produtos que atendem à pesquisa. Os atributos de cada produto retornado são { identificador, nome, descrição, data de criação, score}`: além desses atributos retornei também o *name* da tabela *product_category* pois achei relevante já que a busca é também feita sobre nesse campo.
- `Todos os serviços devem ser auditados`: considerei que seria auditoria nos *endpoints* então criei uma classe *Aspect* para capturar e criar *log* toda vez que é o serviço é chamado.
- `Considere consumir o endpoint https://newsapi.org/docs/endpoints/top-headlines 4 vezes ao dia`: como esse *endpoint* trazia tópicos de notícias que não eram relacionados com as minhas categorias eu preferi consultar o endpoint *https://newsapi.org/v2/everything* para cada categoria que esteja no meu banco de dados.

#O que não foi feito
- `Testes automatizados além do unitário`
- `Colocar segurança nos endpoints que persistem dados. (Pode usar usuário/senha em memória)`

#Sobre o Autor
Estou à disposição,  

[Júnio Gonçalves Fernandes](https://www.linkedin.com/in/juniogof/)  
[juniogof@gmail.com](mailto:juniogof@gmail.com)