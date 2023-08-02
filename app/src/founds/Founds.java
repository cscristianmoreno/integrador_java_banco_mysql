package founds;

import java.time.Instant;

import createNumberFormat.CreateNumberFormat;
// import investments.Investments;
import mysql.Query;
import randomNum.RandomNum;

public class Founds {
    String title;
    int idClient;

    CreateNumberFormat format = new CreateNumberFormat();

    public Founds(String title, int idClient) {
        this.title = title;
        this.idClient = idClient;
    }

    public String createInvertion(int value) {
        int rand;
        int days = new RandomNum(10, 30).getRandom();
        
        rand = new RandomNum(5, 30).getRandom(); 
        int rentPercent = rand;
        float percent;
        percent = rentPercent / 100f;
        
        int amount = (int) Math.round(value * percent);
        
        rand = new RandomNum(1, 80).getRandom();
        int chanceSuccefully = rand;

        rand = new RandomNum(50, 300).getRandom();
        int percentSuccefully = rand;
        
        percent = rand / 100f;
        int gainWithSuccefully = (int) Math.round(value + (value * percent));

        int dayMultiplier = days + new RandomNum(10, 100).getRandom();
        // rand = new RandomNum(10, 20).getRandom();
        
        int max_seconds_per_day = 86400;
        int maxDays = (max_seconds_per_day * dayMultiplier);
        long unixtime = Instant.now().getEpochSecond() + maxDays;

        new Query("INSERT INTO founds (idClient, title, invertion, amount, rent, chanceSuccefully, percentSuccefully, gainWithSuccefully, days, dateExpire) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, FROM_UNIXTIME(?))")
        .setQuery(
            this.idClient,
            this.title,
            value,
            amount,
            rentPercent,
            chanceSuccefully,
            percentSuccefully,
            gainWithSuccefully,
            days,
            unixtime
        );

        String rent = String.format(
        "Tu inversión es de $%s, tu renta fija es de un %d%%\nY deberás pagar durante %d días un monto estimado de $%s\n" +
        "El pronóstico de éxito es de un %d%%, lo cual implica que,\nsi el contrato se cumple, recibirás tu inversión ($%s +%d%%) = $%s", 
        format.setFormat(value),
        rentPercent, 
        days, 
        format.setFormat(amount),
        chanceSuccefully,
        format.setFormat(value),
        percentSuccefully,
        format.setFormat(gainWithSuccefully));

        // Investments investments = new Investments(title, value);
        // investments.createInvertion();
            
        return rent;
    }
}