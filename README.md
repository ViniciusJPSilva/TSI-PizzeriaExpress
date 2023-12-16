# TSI-PizzeriaExpress

Sistema de pedidos de uma pizzaria.

- Java versão 17

- Apache Tomcat versão 9.0

- Dynamic Web Module versão 4.0

<hr>

1) O arquivo de backup do banco de dados (pizzaria_backup.sql) esta no diretório "DataBase".
- Restaurar o backup para a gerar das tabelas é opcional, o sistema está programado para gera-lás ao iniciar, bastando criar o banco de dados e alterar o nome no algoritmo, como informado nas instruções abaixo.


2) Lembre-se de alterar os dados necessários para a execução da aplicação.
   
   O arquivo de configuração é: src/main/java/br/vjps/tsi/crms/system/SystemSettings.java

   O que deverá/poderá ser alterado:

	- O nome do banco, usuário e senha (caso necessário).
	
	- O modo de envio de e-mails:
		- Enviar para um e-mail de testes. (habilitado)
		- Enviar para os e-mails dos pacientes cadastrados.
		- Não enviar nenhum e-mail.
	
	- E-mail (gmail) capaz de integrar com aplicações web, utilizado para efetuar o envio dos e-mails.
		- Altere também a sua respectiva senha.

Obs.: Todas as informações necessárias estão comentadas no arquivo SystemSettings.java.
	
	
3) O login do cliente no sistema se dá por meio do e-mail e do código de acesso.
 - Não há clientes pré cadastrados, mas os mesmos podem ser cadastrados durante o uso da aplicação.


4) O login do funcionário (administrador ou cozinheiro) no sistema se dá por meio do login e da senha.
 - Há dois funcionários cadastrados, os mesmos são inseridos automaticamente no BD durante a inicialização do sistema.

```
	+-------------+------------------+
	|    LOGIN    |      SENHA       |
	+-------------+------------------+
	|    admin    |      admin       |
	+-------------+------------------+
	|     chef    |       chef       |
	+-------------+------------------+
```
