CREATE UNIQUE INDEX IF NOT EXISTS uk_matricula_ativa_por_aluno
    ON matriculas (aluno_id)
    WHERE status = 'ATIVA';
