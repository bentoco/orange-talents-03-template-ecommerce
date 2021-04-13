package br.com.zupacademy.ecommerce.purchase.payment;

public enum PagSeguroStatusResponse {
    ERRO,
    SUCESSO;

    public PaymentTransactionStatus normalize () {
        if (this.equals(SUCESSO)) {
            return PaymentTransactionStatus.success;
        }
        return PaymentTransactionStatus.error;
    }
}
