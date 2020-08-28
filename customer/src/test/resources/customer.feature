#language: pt
Funcionalidade: Manter cadastro de clientes

  Cenario: Criar um registro de cliente
    Dado Usuário deseja criar um cliente com os seguintes atributos
      | name       | email                  | cellPhone            | birth      | addressLine | addressLine2 | zipCode   | district | state | country
      | Fulano Silva | joao.silva@email.com | +55 (98) 99999-99999 | 1986-03-12 | Rua A       | Quadra 1     | 65058-000 | Calhau   | MA    | Brasil
    Quando Envio uma requesição HTTP
    Então Recebo uma resposta válida com codigo 201