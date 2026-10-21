export enum FormaPagamento {
    PIX = 'PIX',
    A_VISTA = 'A_VISTA',
    CARTAO = 'CARTAO',
    BOLETO = 'BOLETO',
}

export const FormaPagamentoLabel: Record<FormaPagamento, string> = {
    [FormaPagamento.PIX]: 'Pix',
    [FormaPagamento.A_VISTA]: 'À vista',
    [FormaPagamento.CARTAO]: 'Cartão',
    [FormaPagamento.BOLETO]: 'Boleto',
};