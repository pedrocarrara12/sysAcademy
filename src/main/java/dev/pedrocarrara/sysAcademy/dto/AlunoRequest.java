package dev.pedrocarrara.sysAcademy.dto;

import dev.pedrocarrara.sysAcademy.entity.Aluno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlunoRequest(
        @NotBlank(message = "Nome e obrigatorio")
        @Size(max = 150, message = "Nome deve ter no maximo 150 caracteres")
        String nome,

        @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
        String cpf,
        @Past(message = "data de nascimento deve estar no passado")
        LocalDate dataNascimento,

        @Size(max = 1, message = "Sexo deve ter no maximo 1 caractere")
        String sexo,

        @Size(max = 15, message = "Telefone deve ter no maximo 15 caracteres")
        String telefone,

        @Email(message = "Email invalido")
        @Size(max = 150, message = "Email deve ter no maximo 150 caracteres")
        String email,

        @Size(max = 150, message = "Endereco deve ter no maximo 150 caracteres")
        String endereco,

        @Size(max = 20, message = "Numero deve ter no maximo 20 caracteres")
        String numero,

        @Size(max = 100, message = "Complemento deve ter no maximo 100 caracteres")
        String complemento,

        @Size(max = 100, message = "Cidade deve ter no maximo 100 caracteres")
        String cidade,

        @Size(max = 2, message = "Estado deve ter no maximo 2 caracteres")
        String estado,

        @Size(max = 20, message = "CEP deve ter no maximo 20 caracteres")
        String cep
) {
    public Aluno toEntity() {
        Aluno aluno = new Aluno();
        applyToEntity(aluno);
        return aluno;
    }

    public void applyToEntity(Aluno aluno) {
        aluno.setNome(nome);
        aluno.setCpf(cpf);
        aluno.setDataNascimento(dataNascimento);
        aluno.setSexo(sexo);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
        aluno.setEndereco(endereco);
        aluno.setNumero(numero);
        aluno.setComplemento(complemento);
        aluno.setCidade(cidade);
        aluno.setEstado(estado);
        aluno.setCep(cep);
    }
}
