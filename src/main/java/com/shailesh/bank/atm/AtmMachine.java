package com.shailesh.bank.atm;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


/**
 * Master class for ATM application
 */
public class AtmMachine {

    private final AtmMasterOps atmMaster;

    private AtmMachine(AtmMasterOps atmMasterOps) {
        this.atmMaster = atmMasterOps;
    }

    public CurrencyWithdrawal withdraw(int sum) {
        return atmMaster.withdraw(sum);
    }

    public AtmSummary summary() {
        return atmMaster.summary();
    }

    public static class ATMBuilder {
        private Map<CurrencyNotes, BigInteger> notesQuantities = new HashMap<>();

        public static class NotesBuilder {
            private ATMBuilder parentBuilder;
            private CurrencyNotes note;

            private NotesBuilder(ATMBuilder parentBuilder, CurrencyNotes note) {
                this.parentBuilder = parentBuilder;
                this.note = note;
            }

            public ATMBuilder withQuantityOf(int quantity) {
                parentBuilder.notesQuantities.compute(note,
                        (n, bi) ->  bi == null ? BigInteger.valueOf(quantity) : bi.add(BigInteger.valueOf(quantity)));
                return parentBuilder;
            }
        }

        public NotesBuilder addNotes(CurrencyNotes note) {
            AtmUtility.checkNullArgs(note);
            notesQuantities.putIfAbsent(note, BigInteger.valueOf(0));
            return new NotesBuilder(this, note);
        }

        public AtmMachine build() {
            return new AtmMachine(AtmMasterOps.performOperation(notesQuantities));
        }
    }




}
