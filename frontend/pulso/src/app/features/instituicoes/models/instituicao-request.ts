export interface InstituicaoRequest {
  nome: string;

  // Lista de e-mails de contato
  emails?: string[];

  telefone: string;
  fotoPerfil: string;
  endereco: string;

  cnpj: string;

  exigeVinculo: boolean;
}