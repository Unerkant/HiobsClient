package HiobsClient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Den 2.08.2025
 */

public class Messages {

    @Id
    @GeneratedValue
    private long id;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private String datum;
    private String freundetoken;
    private String gelesen;
    private String meintoken;
    private String messagetoken;
    private String name;
    private String other;
    private String pseudonym;
    private String role;
    @Column(columnDefinition="TEXT")
    private String text;
    private String vorname;

    public Messages(){
        //leer
    }

    public Messages(long id, String datum, String freundetoken, String gelesen, String meintoken, String messagetoken,
                    String name, String other, String pseudonym, String role, String text, String vorname) {
        this.id = id;
        this.datum = datum;
        this.freundetoken = freundetoken;
        this.gelesen = gelesen;
        this.meintoken = meintoken;
        this.messagetoken = messagetoken;
        this.name = name;
        this.other = other;
        this.pseudonym = pseudonym;
        this.role = role;
        this.text = text;
        this.vorname = vorname;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum; }

    public String getFreundetoken() { return freundetoken; }
    public void setFreundetoken(String freundetoken) { this.freundetoken = freundetoken; }

    public String getGelesen() { return gelesen; }
    public void setGelesen(String gelesen) { this.gelesen = gelesen; }

    public String getMeintoken() { return meintoken; }
    public void setMeintoken(String meintoken) { this.meintoken = meintoken; }

    public String getMessagetoken() { return messagetoken; }
    public void setMessagetoken(String messagetoken) { this.messagetoken = messagetoken; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOther() { return other; }
    public void setOther(String other) { this.other = other; }

    public String getPseudonym() { return pseudonym; }
    public void setPseudonym(String pseudonym) { this.pseudonym = pseudonym; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getVorname() { return vorname; }
    public void setVorname(String vorname) { this.vorname = vorname; }

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", datum='" + datum + '\'' +
                ", freundetoken='" + freundetoken + '\'' +
                ", gelesen='" + gelesen + '\'' +
                ", meintoken='" + meintoken + '\'' +
                ", messagetoken='" + messagetoken + '\'' +
                ", name='" + name + '\'' +
                ", other='" + other + '\'' +
                ", pseudonym='" + pseudonym + '\'' +
                ", role='" + role + '\'' +
                ", text='" + text + '\'' +
                ", vorname='" + vorname + '\'' +
                '}';
    }

}
