# SICREDI - VOTAÇÃO DE ASSOCIADOS

## CONSIDERAÇÕES

	- Escolhi colocar todas as mensagens em inglês porque facilitaria a comunicação se tivéssemos desenvolvedores trabalhando em outros países.
	- Utilizei o Swagger para documentar os rest points.
	- Estou usando um banco de dados de teste, o H2, apenas para poder persistir os dados e não precisar realizar nenhuma configuração local
	- Quanto ao versionamento, acredito que deva ser seguido o padrão semântico. https://semver.org/


## CONFIGURAÇÃO
	
		A primeira vez que executar no ambiente de teste, alterar no arquivo "application-tst.properties", localizado na pasta "resource", 
	a propriedade "spring.jpa.hibernate.ddl-auto" mudando de "create", para "none", pois na primeira vez será criada a base de dados.


## INFORMAÇÕES DE ACESSO

### H2
	<localhost>/h2-console
	Exemplo: http://localhost:8080/h2-console
	JDBC URL: jdbc:h2:./sicredi
	User Name: sa
	Password:
	
	OBS: a senha é em branco mesmo
	
### Swagger
	<localhost>/swagger-ui.html#/
	Exemplo: http://localhost:8080/swagger-ui.html#/
