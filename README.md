# 🧩 TechChallenge-5

Este projeto é composto por múltiplos microserviços, cada um responsável por um domínio específico da aplicação. A estrutura é modularizada para facilitar a manutenção, escalabilidade e reutilização de componentes comuns.

---

## 📌 Microserviços e Documentação Swagger

Cada microserviço possui sua própria interface Swagger para facilitar a visualização e teste dos endpoints disponíveis:

- 🔐 **Autenticação e Usuários**  
  [http://localhost:8080/autorizacao-usuarios/swagger-ui/index.html](http://localhost:8080/autorizacao-usuarios/swagger-ui/index.html)

- 📅 **Agendamentos e Horários Disponíveis**  
  [http://localhost:8080/agendamentos/swagger-ui/index.html](http://localhost:8080/agendamentos/swagger-ui/index.html)

- 🩺 **Especialidades**  
  [http://localhost:8080/especialidades/swagger-ui/index.html](http://localhost:8080/especialidades/swagger-ui/index.html)

---

## 📢 Notificações com RabbitMQ + Twilio

O serviço de **notificações** é responsável por enviar mensagens (como SMS) para os usuários utilizando o [Twilio](https://www.twilio.com/) e mensageria via **RabbitMQ**.

### 🔧 Para executar corretamente este serviço, é necessário:

1. **Configurar uma porta livre** no `application.yml` do microserviço de notificações.
   > ❗ Verifique se a porta não está em uso. Use o comando `netstat -ano | findstr :PORTA` no terminal para verificar.

2. **Cadastrar e configurar um número no Twilio**, incluindo:
  - `conta_sid`
  - `auth_token`
  - `número de envio` (ex: `+19497827123`)

3. Garantir que o serviço do **RabbitMQ** esteja em execução, já que ele é utilizado para enviar e receber eventos de notificação.

---

## 📊 Monitoramento e Analytics

A aplicação conta com um painel de monitoramento baseado no **Spring Boot Admin**, permitindo visualizar o estado e informações detalhadas dos microserviços em tempo real:

- 🌐 **Dashboard de Monitoramento:**  
  [http://localhost:20000/applications](http://localhost:20000/applications)

---

## 🧰 CommonLibrary

A pasta `commonlibrary` contém uma **biblioteca compartilhada** com componentes reutilizáveis por múltiplos microserviços. Isso inclui utilitários, DTOs, validadores e outras funcionalidades comuns.

---

## ⚙️ Configuração e Execução

Cada microserviço possui seus próprios arquivos de configuração, localizados nas pastas 'configuration' e 'filter'.

### 🔄 Subindo o sistema:

1. Certifique-se de que o banco de dados e outras dependências externas estejam disponíveis (se aplicável).
2. Inicie os microserviços individualmente executando a respectiva classe `Application` de cada um.
3. Por fim, execute o `GatewayApplication`, que é responsável por orquestrar e rotear as requisições para os serviços corretos.

> 💡 Os logs da aplicação podem ser consultados no arquivo `app.log` localizado na raiz do projeto ou nos diretórios dos microserviços.

---
