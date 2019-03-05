package com.shailesh.bank.atm;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CurrencyNotesTest {

    @Test
    public void testCurrencyPermutations() {
        List<Iterator<CurrencyNotes>> cpList = CurrencyNotes.currencyPermutations();

        assertEquals(cpList.size(), 2);
        assertEquals(iteratorAsString(cpList.get(0)), "$50$20");
        assertEquals(iteratorAsString(cpList.get(1)), "$20$50");
    }


    private <E> String iteratorAsString(Iterator<E> e) {
        StringBuilder b = new StringBuilder();
        while(e.hasNext()) {
            b.append(e.next().toString());
        }
        return b.toString();
    }
}
