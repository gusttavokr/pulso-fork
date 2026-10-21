package web.pulso.utils;

import java.util.UUID;

public class TransactionContext {

    private static final ThreadLocal<String> transactionId = new ThreadLocal<>();

    public static String getTransactionId() {

        if (transactionId.get() == null) {
            transactionId.set(UUID.randomUUID().toString());
        }

        return transactionId.get();
    }

    public static void clear() {
        transactionId.remove();
    }
}