# 📅 Sistema de Agendamento de Consultas Médicas Eletivas

## 📌 Visão Geral
O sistema de agendamento de consultas médicas visa facilitar o processo de marcação e gerenciamento de consultas para pacientes, médicos e recepcionistas. A plataforma deve ser intuitiva, segura e eficiente, garantindo uma boa experiência para todos os usuários.

---

## 🎯 Arquitetura de Múltiplas APIs
Para tornar o sistema escalável, seguro e modular, dividimos a API em **múltiplos serviços**, cada um responsável por um domínio específico.

### **📌 Estrutura das APIs**

| Serviço | Responsabilidade | Tecnologias |
|---------|-----------------|-------------|
| `auth-service` | Cadastro e autenticação | Node.js/Django, PostgreSQL, JWT |
| `agendamento-service` | Gestão de consultas | FastAPI/Express, PostgreSQL, RabbitMQ |
| `notificacaoDTO-service` | Envio de notificações | NestJS/Celery, Redis, Twilio |
| `relatorios-service` *(opcional)* | Análises e relatórios | Flask/FastAPI, ElasticSearch |

---

## **1️⃣ API de Autenticação & Usuários (`auth-service`)**
🔐 **Gerencia perfis e autenticação**  
📂 **Banco de Dados:** `usuarios_db`  

### **Endpoints**
- `POST /auth/register` → Cadastra paciente ou médico
- `POST /auth/login` → Gera token JWT
- `GET /auth/me` → Retorna perfil autenticado
- `PUT /usuarios/{id}` → Atualiza informações do usuário
- `GET /medicos` → Lista médicos disponíveis

---

## **2️⃣ API de Agendamento & Consultas (`agendamento-service`)**
📅 **Gerencia a marcação e controle das consultas**  
📂 **Banco de Dados:** `agendamentos_db`  

### **Endpoints**
- `POST /consultas` → Cria nova exame
- `GET /consultas/paciente/{id}` → Histórico do paciente
- `GET /consultas/medico/{id}` → Consultas do médico
- `PUT /consultas/{id}/cancelar` → Cancela exame
- `PUT /consultas/{id}/confirmar` → Médico confirma exame

---

## **3️⃣ API de Notificações (`notificacaoDTO-service`)**
🔔 **Responsável por envios de mensagens para pacientes e médicos**  
📂 **Banco de Dados:** `notificacoes_db`  

### **Endpoints**
- `POST /notificacoes/email` → Envia e-mail
- `POST /notificacoes/sms` → Envia SMS
- `POST /notificacoes/whatsapp` → Envia mensagem pelo WhatsApp
- `GET /notificacoes/paciente/{id}` → Lista notificações do paciente

---

## **4️⃣ API de Relatórios (`relatorios-service`)** *(Opcional)*
📊 **Gera estatísticas sobre consultas e usuários**  
📂 **Banco de Dados:** `relatorios_db`  

### **Endpoints**
- `GET /relatorios/consultas` → Estatísticas de consultas
- `GET /relatorios/medicos` → Atendimentos por médico
- `GET /relatorios/pacientes` → Histórico detalhado de um paciente

---

## **📡 Comunicação entre APIs**
Para conectar os microsserviços, usamos **mensageria (RabbitMQ, Kafka)** ou **requisições HTTP internas**. Algumas sugestões:
- `auth-service` autentica usuários e retorna tokens JWT.
- `agendamento-service` valida JWT com `auth-service`.
- `notificacaoDTO-service` escuta eventos de agendamentos para enviar mensagens.

> 🔥 **Exemplo de Comunicação:**  
1️⃣ **Paciente agenda exame** → `agendamento-service` grava no DB.  
2️⃣ **Notificação é enviada** → `notificacaoDTO-service` recebe evento via RabbitMQ e envia e-mail/SMS.  
3️⃣ **Admin exame relatório** → `relatorios-service` agrega estatísticas.  

---

## 📞 Contato e Suporte
Caso tenha dúvidas ou sugestões, entre em contato pelo e-mail **suporte@agendamedica.com** ou pelo WhatsApp **(XX) XXXX-XXXX**.

🚀 **Vamos construir um sistema de agendamento eficiente e prático!**
