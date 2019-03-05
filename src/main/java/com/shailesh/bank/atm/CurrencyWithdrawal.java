package com.shailesh.bank.atm;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * Cash withdrawal class
 */
public class CurrencyWithdrawal {

    private Map<CurrencyNotes, BigInteger> withdraw;

    private int totalAmount, outstandingAmount;

    private boolean status;

    CurrencyWithdrawal(Map<CurrencyNotes, BigInteger> withdraw, int total, int outstanding, boolean success) {
        this.withdraw = new HashMap<>(withdraw);
        this.totalAmount = total;
        this.status = success;
        this.outstandingAmount = outstanding;
    }

    public boolean isSuccessful() {
        return status;
    }

    public int totalWithdrawedAmount() {
        return totalAmount;
    }

    public int outstanding() {
        return outstandingAmount;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("Attempting withdraw %s \n Withdrawal Operation is %s");
        msg.append("\n Outstanding Amount %s");

        if (!withdraw.isEmpty()) {
            msg.append("\n Notes in this transaction :");
            for (Map.Entry<CurrencyNotes, BigInteger> n : withdraw.entrySet()) {
                msg.append("\n ").append(n.getKey()).append(" notes ").append(" = ").append(n.getValue().intValue());
            }
        } else {
            msg.append("\n  ATM is out of cash.. SORRY for inconvenience ! ");
        }
        return format(msg.toString(), totalAmount, status ? "Success" : "Failed",outstandingAmount);
    }


    static class WithdrawBuilder {

        private Map<CurrencyNotes, BigInteger> withdrawal = new HashMap<CurrencyNotes, BigInteger>();

        private boolean isSuccess = true;

        private int totalAmount = 0;

        private int outstandingAmount = 0;

        WithdrawBuilder add(CurrencyNotes note) {
            withdrawal.compute(note, (n,bi)->bi == null ? BigInteger.ONE : bi.add(BigInteger.ONE));
            return this;
        }

        WithdrawBuilder setSum(int total) {
            this.totalAmount = total;
            return this;
        }

        WithdrawBuilder setOutstanding(int outstanding) {
            this.outstandingAmount = outstanding;
            return this;
        }

        WithdrawBuilder markAsFailed() {
            isSuccess = false;
            return this;
        }

        CurrencyWithdrawal build() {
            return new CurrencyWithdrawal(withdrawal, totalAmount, outstandingAmount, isSuccess);
        }
    }

}
