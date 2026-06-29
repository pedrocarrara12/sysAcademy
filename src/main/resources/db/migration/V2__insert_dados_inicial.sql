INSERT INTO alunos (
    id, nome, cpf, data_nascimento, sexo, telefone, email, endereco, numero,
    complemento, cidade, estado, cep
) VALUES
    (1, 'Ana Silva', '12345678901', '1998-04-12', 'F', '(65) 99999-1001', 'ana.silva@email.com', 'Rua das Flores', '120', 'Apto 12', 'Cuiaba', 'MT', '78000000'),
    (2, 'Bruno Souza', '23456789012', '1995-08-25', 'M', '(65) 99999-1002', 'bruno.souza@email.com', 'Avenida Brasil', '450', NULL, 'Cuiaba', 'MT', '78010000'),
    (3, 'Carla Mendes', '34567890123', '2001-01-18', 'F', '(65) 99999-1003', 'carla.mendes@email.com', 'Rua Primavera', '88', 'Casa', 'Varzea Grande', 'MT', '78110000'),
    (4, 'Diego Lima', '45678901234', '1990-11-03', 'M', '(65) 99999-1004', 'diego.lima@email.com', 'Rua das Palmeiras', '300', NULL, 'Cuiaba', 'MT', '78020000');

INSERT INTO modalidades (id, nome, ativa) VALUES
    (1, 'Musculacao', TRUE),
    (2, 'Jiu-Jitsu', TRUE),
    (3, 'Muay Thai', TRUE),
    (4, 'Pilates', TRUE);

INSERT INTO graduacoes (id, modalidade_id, nome) VALUES
    (1, 2, 'Faixa Branca'),
    (2, 2, 'Faixa Azul'),
    (3, 2, 'Faixa Roxa'),
    (4, 3, 'Iniciante'),
    (5, 3, 'Intermediario'),
    (6, 4, 'Nivel 1');

INSERT INTO planos (id, modalidade_id, nome, valor_mensal, ativo) VALUES
    (1, 1, 'Musculacao Mensal', 99.90, TRUE),
    (2, 2, 'Jiu-Jitsu Mensal', 149.90, TRUE),
    (3, 3, 'Muay Thai Mensal', 139.90, TRUE),
    (4, 4, 'Pilates Mensal', 179.90, TRUE);

INSERT INTO matriculas (id, aluno_id, data_matricula, dia_vencimento, data_encerramento, status) VALUES
    (1, 1, '2026-01-10', 10, NULL, 'ATIVA'),
    (2, 2, '2026-02-05', 5, NULL, 'ATIVA'),
    (3, 3, '2026-03-15', 15, NULL, 'ATIVA'),
    (4, 4, '2026-04-01', 1, '2026-06-01', 'ENCERRADA');

INSERT INTO matriculas_modalidades (
    id, matricula_id, modalidade_id, graduacao_id, plano_id, data_inicio, data_fim
) VALUES
    (1, 1, 2, 1, 2, '2026-01-10', NULL),
    (2, 2, 3, 4, 3, '2026-02-05', NULL),
    (3, 3, 4, 6, 4, '2026-03-15', NULL),
    (4, 4, 2, 2, 2, '2026-04-01', '2026-06-01');

INSERT INTO faturas_matriculas (
    id, matricula_id, data_vencimento, valor, data_pagamento, data_cancelamento, status
) VALUES
    (1, 1, '2026-06-10', 149.90, '2026-06-09 14:30:00', NULL, 'PAGA'),
    (2, 1, '2026-07-10', 149.90, NULL, NULL, 'ABERTA'),
    (3, 2, '2026-06-05', 139.90, '2026-06-05 09:10:00', NULL, 'PAGA'),
    (4, 2, '2026-07-05', 139.90, NULL, NULL, 'ABERTA'),
    (5, 3, '2026-06-15', 179.90, NULL, NULL, 'VENCIDA'),
    (6, 4, '2026-06-01', 149.90, NULL, '2026-06-01', 'CANCELADA');

INSERT INTO assiduidade (id, matricula_id, data_entrada, data_saida) VALUES
    (1, 1, '2026-06-03 18:00:00', '2026-06-03 19:20:00'),
    (2, 1, '2026-06-05 18:10:00', '2026-06-05 19:30:00'),
    (3, 2, '2026-06-04 20:00:00', '2026-06-04 21:15:00'),
    (4, 3, '2026-06-06 07:30:00', '2026-06-06 08:30:00'),
    (5, 4, '2026-05-28 19:00:00', '2026-05-28 20:10:00');

SELECT setval(pg_get_serial_sequence('alunos', 'id'), (SELECT MAX(id) FROM alunos));
SELECT setval(pg_get_serial_sequence('modalidades', 'id'), (SELECT MAX(id) FROM modalidades));
SELECT setval(pg_get_serial_sequence('graduacoes', 'id'), (SELECT MAX(id) FROM graduacoes));
SELECT setval(pg_get_serial_sequence('planos', 'id'), (SELECT MAX(id) FROM planos));
SELECT setval(pg_get_serial_sequence('matriculas', 'id'), (SELECT MAX(id) FROM matriculas));
SELECT setval(pg_get_serial_sequence('matriculas_modalidades', 'id'), (SELECT MAX(id) FROM matriculas_modalidades));
SELECT setval(pg_get_serial_sequence('faturas_matriculas', 'id'), (SELECT MAX(id) FROM faturas_matriculas));
SELECT setval(pg_get_serial_sequence('assiduidade', 'id'), (SELECT MAX(id) FROM assiduidade));
