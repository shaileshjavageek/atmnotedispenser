package com.shailesh.bank.atm;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AtmTest {

    private AtmMachine atm = null;

    @Before
    public void init(){
        /**
         * Load ATM with some notes of currency $20 and $50
         *  $20 x 2
         *  $50 x 5
         */
        AtmMachine.ATMBuilder builder = new AtmMachine.ATMBuilder();
        builder.addNotes(CurrencyNotes.$20).withQuantityOf(2);
        builder.addNotes(CurrencyNotes.$50).withQuantityOf(5);
        //preBuildOfAtmBuilder(builder);
        atm = builder.build();
    }


    @Test
    public void test_1() throws Exception {
        /**
         * After withdrawal1 $10 (failed)
         *  $20 x 2
         *  $50 x 5
         */
        CurrencyWithdrawal withdrawal = atm.withdraw(10);
        assertFalse(withdrawal.isSuccessful());
        assertEquals(withdrawal.totalWithdrawedAmount(), 10);
        assertEquals(withdrawal.outstanding(), 10);
        AtmSummary summary1 = atm.summary();
        assertEquals(summary1.notesQuantity(CurrencyNotes.$20), 2);
        assertEquals(summary1.notesQuantity(CurrencyNotes.$50), 5);
    }

    @Test
    public void test_2() throws Exception {
        /**
         * After withdrawal2 $50 (success)
         *  $20 x 2
         *  $50 x 4
         */
        CurrencyWithdrawal withdrawal2 = atm.withdraw(50);
        assertTrue(withdrawal2.isSuccessful());
        assertEquals(withdrawal2.totalWithdrawedAmount(), 50);
        assertEquals(withdrawal2.outstanding(), 0);
        AtmSummary summary2 = atm.summary();
        assertEquals(summary2.notesQuantity(CurrencyNotes.$20), 2);
        assertEquals(summary2.notesQuantity(CurrencyNotes.$50), 4);
    }

    @Test
    public void test_3() throws Exception {
        /**
         * After withdrawal3 $20 (success)
         *  $20 x 1
         *  $50 x 4
         */
        CurrencyWithdrawal withdrawal3 = atm.withdraw(20);
        assertTrue(withdrawal3.isSuccessful());
        assertEquals(withdrawal3.totalWithdrawedAmount(), 20);
        assertEquals(withdrawal3.outstanding(), 0);
        AtmSummary summary3 = atm.summary();
        assertEquals(summary3.notesQuantity(CurrencyNotes.$20), 1);
        assertEquals(summary3.notesQuantity(CurrencyNotes.$50), 5);
    }

    @Test
    public void test_4() throws Exception {
        /**
         * After withdrawal4 $60 (failed)
         *  $20 x 1
         *  $50 x 4
         */
        CurrencyWithdrawal withdrawal4 = atm.withdraw(60);
        assertFalse(withdrawal4.isSuccessful());
        assertEquals(withdrawal4.totalWithdrawedAmount(), 60);
        assertEquals(withdrawal4.outstanding(), 20);
        AtmSummary summary4 = atm.summary();
        assertEquals(summary4.notesQuantity(CurrencyNotes.$20), 2);
        assertEquals(summary4.notesQuantity(CurrencyNotes.$50), 5);
    }

    @Test
    public void test_5() throws Exception {
        /**
         * After withdrawal5 $70 (success)
         *  $20 x 0
         *  $50 x 3
         */
        CurrencyWithdrawal withdrawal5 = atm.withdraw(70);
        assertTrue(withdrawal5.isSuccessful());
        assertEquals(withdrawal5.totalWithdrawedAmount(), 70);
        assertEquals(withdrawal5.outstanding(), 0);
        AtmSummary summary5 = atm.summary();
        assertEquals(summary5.notesQuantity(CurrencyNotes.$20), 1);
        assertEquals(summary5.notesQuantity(CurrencyNotes.$50), 4);
    }

    @Test
    public void test_6() throws Exception {
        /**
         * After withdrawal6 $20 (failed)
         *   $20 x 0
         *   $50 x 3
         */
        CurrencyWithdrawal withdrawal6 = atm.withdraw(20);
        assertTrue(withdrawal6.isSuccessful());
        assertEquals(withdrawal6.totalWithdrawedAmount(), 20);
        assertEquals(withdrawal6.outstanding(), 0);
        AtmSummary summary6 = atm.summary();
        assertEquals(summary6.notesQuantity(CurrencyNotes.$20), 1);
        assertEquals(summary6.notesQuantity(CurrencyNotes.$50), 5);
    }

    @Test
    public void test_7() throws Exception {
        /**
         * After withdrawal7 $100 (success)
         *  $20 x 0
         *  $30 x 1
         */
        CurrencyWithdrawal withdrawal7 = atm.withdraw(100);
        assertTrue(withdrawal7.isSuccessful());
        assertEquals(withdrawal7.totalWithdrawedAmount(), 100);
        assertEquals(withdrawal7.outstanding(), 0);
        AtmSummary summary7 = atm.summary();
        assertEquals(summary7.notesQuantity(CurrencyNotes.$20), 2);
        assertEquals(summary7.notesQuantity(CurrencyNotes.$50), 3);
    }

    @Test
    public void test_8() throws Exception {
        /**
         * After withdrawal8 $150 (fail)
         *  $20 x 0
         *  $50 x 1
         */
        CurrencyWithdrawal withdrawal8 = atm.withdraw(150);
        assertTrue(withdrawal8.isSuccessful());
        assertEquals(withdrawal8.totalWithdrawedAmount(), 150);
        assertEquals(withdrawal8.outstanding(), 0);
        AtmSummary summary8 = atm.summary();
        assertEquals(summary8.notesQuantity(CurrencyNotes.$20), 2);
        assertEquals(summary8.notesQuantity(CurrencyNotes.$50), 2);
    }


        @Test
        public void test_9() throws Exception {
            /**
             * After withdrawal9 $50 (success)
             *   $20 x 0
             *   $50 x 0
             */
            CurrencyWithdrawal withdrawal9 = atm.withdraw(50);
            assertTrue(withdrawal9.isSuccessful());
            assertEquals(withdrawal9.totalWithdrawedAmount(), 50);
            assertEquals(withdrawal9.outstanding(), 0);
            AtmSummary summary9 = atm.summary();
            assertEquals(summary9.notesQuantity(CurrencyNotes.$20), 2);
            assertEquals(summary9.notesQuantity(CurrencyNotes.$50), 4);
        }


    @Test
    public void test_10() throws Exception{
        /**
         * After withdrawal10 $150 (fail)
         *  $20 x 0
         *  $50 x 0
         */
        CurrencyWithdrawal withdrawal10 = atm.withdraw(150);
        assertTrue(withdrawal10.isSuccessful());
        assertEquals(withdrawal10.totalWithdrawedAmount(), 150);
        assertEquals(withdrawal10.outstanding(), 0);
        AtmSummary summary10 = atm.summary();
        assertEquals(summary10.notesQuantity(CurrencyNotes.$20), 2);
        assertEquals(summary10.notesQuantity(CurrencyNotes.$50), 2);
    }
}
