package bookmyshow.service;

import bookmyshow.model.booking.Booking;
import bookmyshow.model.exceptions.TransactionFailedException;
import bookmyshow.model.transaction.Transaction;
import bookmyshow.model.transaction.TransactionStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransactionService {
    private final double TRANSACTION_FEE = 1.0;
    private final PaymentService paymentService;
    private final Map<String, Transaction> transactions;

    public TransactionService(PaymentService paymentService) {
        this.paymentService = paymentService;
        transactions = new HashMap<>();
    }

    public Transaction initiateTransaction(Booking booking) {
        double amount = booking.getTotalPrice() + TRANSACTION_FEE;
        String transactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(transactionId, booking, TransactionStatus.PENDING, amount);
        transactions.put(transaction.getId(), transaction);

        try {
            paymentService.pay();
            transaction.setStatus(TransactionStatus.SUCCESSFUL);
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new TransactionFailedException("Transaction failed.");
        }

        return transactions.get(transaction.getId());
    }
}
