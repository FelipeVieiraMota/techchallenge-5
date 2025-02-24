# 📅 Sistema de Agendamento de Consultas Médicas Eletivas

## 📌 Visão Geral
O sistema de agendamento de consultas médicas visa facilitar o processo de marcação e gerenciamento de consultas para pacientes, médicos e recepcionistas. A plataforma deve ser intuitiva, segura e eficiente, garantindo uma boa experiência para todos os usuários.

---

## 🎯 Funcionalidades Principais

### **Para Pacientes**
- 📌 Cadastro e login seguro
- 🔍 Pesquisa por médicos, especialidades e planos de saúde
- 📅 Escolha de datas e horários disponíveis
- ✅ Confirmação e cancelamento de consultas
- 📜 Histórico de consultas
- 🔔 Notificações (e-mail, SMS ou WhatsApp) de lembrete

### **Para Médicos**
- 🕒 Gerenciamento de disponibilidade
- ✅ Aceitação ou recusa de consultas
- 📂 Acesso ao histórico do paciente
- 🔄 Controle de cancelamentos e reagendamentos

### **Para Recepção/Administração**
- 📊 Painel de controle para gerenciamento de agendamentos
- 📜 Relatórios de consultas realizadas, canceladas e pendentes
- 👥 Gestão de médicos e horários disponíveis
- 🏥 Suporte a múltiplas clínicas e unidades

---

## 🔄 Regras e Lógica do Agendamento
- **Disponibilidade**: Bloqueio automático de horários já ocupados.
- **Tempo de consulta**: Definir tempos padrão (ex.: 30 min, 1h) por especialidade.
- **Confirmação obrigatória**: Pacientes devem confirmar a consulta dentro de um período pré-definido.
- **Cancelamento**: Estabelecer prazos para cancelamento sem multas.
- **Fila de espera**: Notificação automática para pacientes na lista de espera quando houver desistências.

---

## 🛠 Tecnologias Sugeridas
- **Frontend**: React, Vue.js ou Angular
- **Backend**: Node.js, Django ou Laravel
- **Banco de Dados**: PostgreSQL ou MongoDB
- **Autenticação**: OAuth, JWT
- **Notificações**: Twilio (SMS/WhatsApp), Firebase (Push), SMTP (E-mail)
- **Integrações**: APIs de planos de saúde, telemedicina, pagamento online

---

## 🚀 Diferenciais
- 📱 Aplicativo móvel (Android/iOS)
- 💬 Chat para comunicação entre médicos e pacientes
- 🏥 Integração com prontuário eletrônico
- 🤖 Agendamento via WhatsApp (chatbot)
- 🔄 Suporte a múltiplas unidades de saúde e redes de clínicas

---

## 📂 Estrutura do Banco de Dados (Exemplo Simplificado)
```sql
CREATE TABLE pacientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    senha_hash TEXT NOT NULL
);

CREATE TABLE medicos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(100) NOT NULL,
    crm VARCHAR(20) UNIQUE NOT NULL,
    disponibilidade JSONB NOT NULL
);

CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,
    paciente_id INT REFERENCES pacientes(id),
    medico_id INT REFERENCES medicos(id),
    data_hora TIMESTAMP NOT NULL,
    status VARCHAR(20) CHECK (status IN ('agendada', 'cancelada', 'realizada'))
);
```

---

## 📥 Exemplos de Inserção de Dados

### **Inserindo Dados na Tabela `pacientes`**
```sql
INSERT INTO pacientes (nome, email, telefone, senha_hash)
VALUES 
    ('João da Silva', 'joao.silva@email.com', '(11) 99999-1234', 'senha_hash_joao'),
    ('Maria Oliveira', 'maria.oliveira@email.com', '(21) 98888-5678', 'senha_hash_maria'),
    ('Carlos Souza', 'carlos.souza@email.com', '(31) 97777-4321', 'senha_hash_carlos');
```

### **Inserindo Dados na Tabela `medicos`**
```sql
INSERT INTO medicos (nome, especialidade, crm, disponibilidade)
VALUES 
    ('Dra. Fernanda Mendes', 'Cardiologia', 'CRM12345', '{"segunda-feira": ["08:00-12:00", "14:00-18:00"], "quarta-feira": ["08:00-12:00"]}'),
    ('Dr. Ricardo Lima', 'Ortopedia', 'CRM67890', '{"terça-feira": ["09:00-12:00"], "quinta-feira": ["13:00-17:00"]}'),
    ('Dra. Ana Beatriz', 'Dermatologia', 'CRM54321', '{"segunda-feira": ["10:00-14:00"], "sexta-feira": ["08:00-12:00"]}');
```

### **Inserindo Dados na Tabela `consultas`**
```sql
INSERT INTO consultas (paciente_id, medico_id, data_hora, status)
VALUES 
    (1, 1, '2025-03-10 08:30:00', 'agendada'),
    (2, 2, '2025-03-12 09:00:00', 'agendada'),
    (3, 3, '2025-03-14 10:30:00', 'realizada'),
    (1, 2, '2025-03-15 13:00:00', 'cancelada');
```

---

## 🔍 Consultas SQL para Ver os Dados

### **Visualizar todos os pacientes cadastrados**
```sql
SELECT * FROM pacientes;
```

### **Visualizar médicos e suas especialidades**
```sql
SELECT nome, especialidade, crm FROM medicos;
```

### **Visualizar consultas agendadas com nome do paciente e médico**
```sql
SELECT c.id AS consulta_id, p.nome AS paciente, m.nome AS medico, m.especialidade, c.data_hora, c.status
FROM consultas c
JOIN pacientes p ON c.paciente_id = p.id
JOIN medicos m ON c.medico_id = m.id
ORDER BY c.data_hora;
```

### **Contar consultas por status**
```sql
SELECT status, COUNT(*) AS total FROM consultas GROUP BY status;
```

### **Verificar horários disponíveis do médico de ID 1**
```sql
SELECT nome, disponibilidade FROM medicos WHERE id = 1;
```

---

## 📞 Contato e Suporte
Caso tenha dúvidas ou sugestões, entre em contato pelo e-mail **suporte@agendamedica.com** ou pelo WhatsApp **(XX) XXXX-XXXX**.

🚀 **Vamos construir um sistema de agendamento eficiente e prático!**