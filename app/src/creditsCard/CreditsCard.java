package creditsCard;

import java.util.ArrayList;
import java.util.Calendar;

import mysql.Query;
import randomNum.RandomNum;

public class CreditsCard {
    private int idClient;
    private int idBank;

    public CreditsCard(int idClient) {
        this.idClient = idClient;
    }

    public CreditsCard(int idClient, int idBank) {
        this.idClient = idClient;
        this.idBank = idBank;
    }

    public void createCreditCard() {

        int rand;
        int lastDigit = new RandomNum(100, 999).getRandom();
        int useVisa = new RandomNum(0, 1).getRandom();
        ArrayList<String> creditCardNumber = new ArrayList<String>();
        
        for (int i = 0; i < 4; i++) {
            rand = new RandomNum(1001, 9999).getRandom();
            creditCardNumber.add(Integer.toString(rand));     
        }
        
        int expireMonth = new RandomNum(1, 12).getRandom();
        
        Calendar calendar = Calendar.getInstance();
        int year;
        String number = creditCardNumber.get(0) + " " + creditCardNumber.get(1) + " " + creditCardNumber.get(2) + " " + creditCardNumber.get(3);
        
        year = calendar.get(Calendar.YEAR); 
        year = year + new RandomNum(2, 5).getRandom();

        String expire = String.format("%s/%s", (expireMonth < 10) ? ("0" + expireMonth) : expireMonth, year);

        new Query("INSERT INTO creditscard (creditCardNumber, lastDigit, idClient, idBank, useVisa, expire) VALUES (?, ?, ?, ?, ?, ?)")
        .setQuery(
            number,
            lastDigit,
            this.idClient,
            this.idBank,
            useVisa,
            expire
        ); // Okkk

        System.out.println("TARJETA VINCULADA A TU CUENTA BANCARIA");
        System.out.printf("\n\n* %s (%d)\n* Tipo de tarjeta: %s\n* Expira: %s", number, lastDigit, (useVisa == 1) ? "Visa" : "Mastercard", expire);       
    }

    public void deleteCreditCard() {
        new Query("DELETE FROM creditscard WHERE idClient = ?").setQuery(this.idClient);

        System.out.println("TU TARJETA HA SIDO DESVINCULADA CON ÉXITO");
    }
}
