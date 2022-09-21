# SICREDI - VOTAÇÃO DE ASSOCIADOS

## CONSIDERAÇÕES
	
	- Para criar as sessões de votação, considerei que poderiam haver mais de uma sessão para a mesma pauta, então, no momento que é enviada a votação,
	deve ser enviado o id da sessão e não da pauta.
	- Escolhi colocar todas as mensagens em inglês porque facilitaria a comunicação se tivéssemos desenvolvedores trabalhando em outros países.
	- Embora a tendência seja utilizar a separação dos pacotes por funcionalidades (Package by Feature),
	decidi usar o layout por camadas (Package by Layers), porque tive que ir entendendo as funcionalidades e adaptando conforme ia desenvolvendo,
	uma vez que não tinha as histórias escritas e bem definidas.
	- Utilizei o Swagger para documentar os rest points.
	- Os testes unitários foram realizados em apenas algumas funcionalidades, por questões de tempo apenas.
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
