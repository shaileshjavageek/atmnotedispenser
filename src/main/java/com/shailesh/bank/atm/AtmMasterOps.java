package com.shailesh.bank.atm;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Abstract class used to perform internal operation of ATM
 */
abstract class AtmMasterOps {

    abstract void set(NotesKeeper notesKeeper);
    abstract NotesKeeper get();
    abstract CurrencyWithdrawal withdraw(int sum);
    abstract AtmSummary summary();

    AtmSummary internalSummary() {
        return new AtmSummary(get().copyMyself().notesQuantities);
    }

    private static class NotesKeeper {

        private Map<CurrencyNotes, BigInteger> notesQuantities;
        private NotesKeeper(){}

        static NotesKeeper newCopy(Map<CurrencyNotes, BigInteger> notesQuantities) {
            AtmUtility.checkNullArgs(notesQuantities);
            NotesKeeper notesKeeper = new NotesKeeper();
            notesKeeper.notesQuantities = new HashMap<>(notesQuantities);
            return notesKeeper;
        }

        NotesKeeper copyMyself() {
            return NotesKeeper.newCopy(this.notesQuantities);
        }
    }

    CurrencyWithdrawal internalWithdraw(int sum) {
        if (sum <= 0) {
            throw new IllegalArgumentException("Sum must be greater than zero!");
        }

        CurrencyWithdrawal.WithdrawBuilder builder = null;
        List<Iterator<CurrencyNotes>> notesPermutationsList =  CurrencyNotes.currencyPermutations();
        for(Iterator<CurrencyNotes> currentNotesPermutation : notesPermutationsList) {
            int efSum = sum;
            NotesKeeper copyOfNotesKeeper = get().copyMyself();
            builder = new CurrencyWithdrawal.WithdrawBuilder();
            builder.setSum(sum);

            NEXT_NOTE:
            while (currentNotesPermutation.hasNext()) {
                CurrencyNotes currentNote = currentNotesPermutation.next();
                while (efSum > 0) {
                    BigInteger quantities = copyOfNotesKeeper.notesQuantities.get(currentNote);
                    if (quantities == null) {
                        continue NEXT_NOTE;
                    }
                    while (true) {
                        if (efSum >= currentNote.amount() && quantities.compareTo(BigInteger.ZERO) == 1) {
                            efSum = efSum - currentNote.amount();
                            quantities = quantities.subtract(BigInteger.ONE);
                            copyOfNotesKeeper.notesQuantities.put(currentNote, quantities);
                            builder.add(currentNote);
                        } else {
                            continue NEXT_NOTE;
                        }
                    }
                }
            }
            builder.setOutstanding(efSum);
            if (efSum == 0) {
                set(copyOfNotesKeeper);
                return builder.build();
            }
            else {
                builder.markAsFailed();
            }
        }
        return builder.build();
    }


    static AtmMasterOps performOperation(final Map<CurrencyNotes, BigInteger> n) {
        return new AtmMasterOps(){
            private NotesKeeper notesKeeper = NotesKeeper.newCopy(n);

            @Override
            void set(NotesKeeper notesKeeper) {
                this.notesKeeper = notesKeeper;
            }

            @Override
            NotesKeeper get() {
                return notesKeeper;
            }

            @Override
            CurrencyWithdrawal withdraw(int sum) {
                return internalWithdraw(sum);
            }

            @Override
            AtmSummary summary() {
                return internalSummary();
            }
        };
    }





}
