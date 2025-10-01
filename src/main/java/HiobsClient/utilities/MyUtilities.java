package HiobsClient.utilities;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Den 5.12.2024
 */

@Component
public class MyUtilities {


    /**
     *   aktuelle datum in DE-Format
     *   =======================================================
     *   1. Deutsches Format, für die Allgemeine anzeige
     *   2. US-Format (in Datenbank speichern)
     *
     */
    public String deDatum(){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date de = new Date();
        return format.format(de);
    }



    /**
     * aktuelle Datum in US-Format
     * =========================================================
     * für MySql-Datenbank gedacht, weil die datum wird in US-Format gespeichert
     * @return
     */
    public String usDatum(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date us = new Date();
        return format.format(us);
    }

    /**
     * Aktuelle Tag + Monat + Jahr (Deutsche Format)
     * ==============================================================
     * @return
     */
    public String jahrTag() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date de = new Date();
        return format.format(de);
    }


    /**
     * Aktuelles Tages Zeit
     * ==============================================================
     * @return
     */
    public String tagZeit() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date de = new Date();
        return format.format(de);
    }


    /**
     * Landes Code
     * <br><br>
     * return:  ~de, uk oder us
     *
     * @return
     */
    public String getLanguage() {
        Locale locale =  Locale.getDefault();
        if (locale == null){
            // locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
            locale = Locale.of(System.getProperty("user.language"), System.getProperty("user.country"));
        }
        return locale.getLanguage();
    }


    /**
     * Land in String ausgeben
     * <br><br>
     * return:  Deutsch, USA oder EU
     *
     * @return
     */
    public String getLand() {
        Locale locale =  Locale.getDefault();
        if (locale == null){
           // locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
            locale = Locale.of(System.getProperty("user.language"), System.getProperty("user.country"));
        }

        String land = null;
        switch (locale.getLanguage()) {
            case"de":           land = "Deutsch"; break;
            case"us":           land = "USA"; break;
            case"uk":           land = "England"; break;
            default:            land = "WORLD";
        }
        //return locale.getLanguage();
        return land;
    }

}
