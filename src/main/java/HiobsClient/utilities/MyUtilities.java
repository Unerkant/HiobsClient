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
     * Landes Code
     * <br><br>
     * return:  ~de
     *
     * @return
     */
    public String getLanguage() {
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
            default:            land = "EU";
        }
        //return locale.getLanguage();
        return land;
    }

}
