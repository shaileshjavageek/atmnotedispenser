package com.shailesh.bank.atm;

import java.math.BigInteger;
import java.util.Map;

import static java.lang.String.format;

/**
 * Summary class used to summarize atm currency status.
 */
public class AtmSummary {

    private Map<CurrencyNotes, BigInteger> notesQuantities;

    AtmSummary(Map<CurrencyNotes, BigInteger> notesQuantities){
        this.notesQuantities = notesQuantities;
    }

    public int notesQuantity(CurrencyNotes note) {
        return notesQuantities.getOrDefault(note, BigInteger.ZERO).intValue();
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();
        msg.append("Notes currently in atm machine");
        BigInteger totalRemainingAmount = BigInteger.valueOf(0);
        for(Map.Entry<CurrencyNotes, BigInteger> e : notesQuantities.entrySet()) {
            msg.append(format("%n %s Notes in ATM machine = %s", e.getKey(), e.getValue()));
            totalRemainingAmount = totalRemainingAmount.add(BigInteger.valueOf(e.getKey().amount()).multiply(e.getValue()));
        }
        msg.append(format("%n Total remaining amount in ATM = $%s ", totalRemainingAmount));
        return msg.toString();
    }
}
