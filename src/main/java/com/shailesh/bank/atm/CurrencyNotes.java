package com.shailesh.bank.atm;

import java.util.*;

/**
 * Currency notes class
 */
public enum CurrencyNotes {

    $20(){
        @Override
        public int amount() {
            return 20;
        }
    },
    $50(){
        @Override
        public int amount() {
            return 50;
        }
    };

    public abstract int amount();

    public static List<Iterator<CurrencyNotes>> currencyPermutations() {
        List<Iterator<CurrencyNotes>> cpList = new ArrayList<>();
        CurrencyNotes[] notes = Arrays.copyOf(CurrencyNotes.values(), CurrencyNotes.values().length);
        for (int i=0; i< notes.length; i++) {

            CurrencyNotes[] t = new CurrencyNotes[notes.length];
            for (int k=0; k<t.length;k++) {
                t[(k+1)%t.length] = notes[k];
            }
            notes = t;
            cpList.add(new CurrencyPermutationIterator(t));
        }
        return cpList;
    }


    public static class CurrencyPermutationIterator implements Iterator<CurrencyNotes> {

        private CurrencyNotes[] notes;

        private int count = 0;

        private CurrencyPermutationIterator(CurrencyNotes[] notes) {
            this.notes = Arrays.copyOf(notes, notes.length);
        }

        @Override
        public boolean hasNext() {
            return (count < notes.length);
        }

        @Override
        public CurrencyNotes next() {
            if (hasNext()) {
                return notes[count++];
            }
            throw new NoSuchElementException("No element found.");
        }
    }



}
