package com.shailesh.bank.atm;

import java.io.IOException;
import java.util.Arrays;

import static java.lang.String.format;

/**
 * Class for running this application.
 *
 */
public class AtmRunner {

    public static void main(String[] args) throws IOException {

        AtmUtility.ConsoleWriter console = new AtmUtility.ConsoleWriter();
        console.writeline("Creating ATM-----");

        AtmMachine.ATMBuilder atmBuilder = new AtmMachine.ATMBuilder();

        for(CurrencyNotes note : CurrencyNotes.values()) {
            int q = console.readAsInt(format("Enter the quantities of %s notes : ", note));
            atmBuilder.addNotes(note).withQuantityOf(q);
        }

        AtmMachine atm = atmBuilder.build();
        console.writeline("ATM created successfully.");

        boolean exit = false;
        do {
            console.writeline("----------------------------------------------------------------------------------");
            char option = console.readAsChar("s for atm summary, w for withdrawal, e to exit followed by enter: ", Arrays.asList('s', 'w', 'e'));
            switch (option) {
                case 's':
                    AtmSummary summary = atm.summary();
                    console.write(summary.toString());
                    console.writeline("");
                    break;
                case 'w':
                    int sum = console.readAsInt("Amount to withdraw (an integer followed by enter)? ");
                    CurrencyWithdrawal withdrawal = atm.withdraw(sum);
                    console.write(withdrawal.toString());
                    console.writeline("");
                    break;
                case 'e':
                    console.writeline("Exit..!");
                    exit = true;
            }
        } while (!exit);
    }



}
