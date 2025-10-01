package HiobsClient.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.springframework.stereotype.Component;

/**
 * Den 31.05.2025
 */

@Component
public class Friends {

    @Id
    @GeneratedValue
    private Long id;
    private String datum;
    private String friendsbild;
    private String friendsmail;
    private String friendsname;
    private String friendspseudonym;
    private String friendstelefon;
    private String friendstoken;
    private String friendsvorname;
    private String gespeichertestoken;
    private String hiobstoken;
    private String meinentoken;
    private String messagetoken;
    private String role;
    @Transient // Bedeutet: wird nicht für die Datenbank verwendet
    private String letzteNachricht;
    @Transient
    private String datumLetzteNachricht;



    public Friends(){}

    public Friends(Long id, String datum, String friendsbild, String friendsmail, String friendsname,
                   String friendspseudonym, String friendstelefon, String friendstoken, String friendsvorname,
                   String gespeichertestoken, String hiobstoken,
                   String meinentoken, String messagetoken, String letzteNachricht, String datumLetzteNachricht) {
        this.id                 = id;
        this.datum              = datum;
        this.friendsbild        = friendsbild;
        this.friendsmail        = friendsmail;
        this.friendsname        = friendsname;
        this.friendspseudonym   = friendspseudonym;
        this.friendstelefon     = friendstelefon;
        this.friendstoken       = friendstoken;
        this.friendsvorname     = friendsvorname;
        this.gespeichertestoken = gespeichertestoken;
        this.hiobstoken         = hiobstoken;
        this.meinentoken        = meinentoken;
        this.messagetoken       = messagetoken;
        this.letzteNachricht    = letzteNachricht;
        this.datumLetzteNachricht= datumLetzteNachricht;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum; }

    public String getFriendsbild() { return friendsbild; }
    public void setFriendsbild(String friendsbild) { this.friendsbild = friendsbild; }

    public String getFriendsmail() { return friendsmail; }
    public void setFriendsmail(String friendsmail) { this.friendsmail = friendsmail; }

    public String getFriendsname() { return friendsname; }
    public void setFriendsname(String friendsname) { this.friendsname = friendsname; }

    public String getFriendspseudonym() { return friendspseudonym; }
    public void setFriendspseudonym(String friendspseudonym) { this.friendspseudonym = friendspseudonym; }

    public String getFriendstelefon() { return friendstelefon; }
    public void setFriendstelefon(String friendstelefon) { this.friendstelefon = friendstelefon; }

    public String getFriendstoken() { return friendstoken; }
    public void setFriendstoken(String friendstoken) { this.friendstoken = friendstoken; }

    public String getFriendsvorname() { return friendsvorname; }
    public void setFriendsvorname(String friendsvorname) { this.friendsvorname = friendsvorname; }

    public String getGespeichertestoken() { return gespeichertestoken; }
    public void setGespeichertestoken(String gespeichertestoken) { this.gespeichertestoken = gespeichertestoken; }

    public String getHiobstoken() { return hiobstoken; }
    public void setHiobstoken(String hiobstoken) { this.hiobstoken = hiobstoken; }

    public String getMeinentoken() { return meinentoken; }
    public void setMeinentoken(String meinentoken) { this.meinentoken = meinentoken; }

    public String getMessagetoken() { return messagetoken; }
    public void setMessagetoken(String messagetoken) { this.messagetoken = messagetoken; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getLetzteNachricht() { return letzteNachricht; }
    public void setLetzteNachricht(String letzteNachricht) { this.letzteNachricht = letzteNachricht; }

    public String getDatumLetzteNachricht() { return datumLetzteNachricht; }
    public void setDatumLetzteNachricht(String datumLetzteNachricht) {
        this.datumLetzteNachricht = datumLetzteNachricht;
    }

    @Override
    public String toString() {
        return "friends{" +
                "id=" + id +
                ", datum='" + datum + '\'' +
                ", friendsbild='" + friendsbild + '\'' +
                ", friendsmail='" + friendsmail + '\'' +
                ", friendsname='" + friendsname + '\'' +
                ", friendspseudonym='" + friendspseudonym + '\'' +
                ", friendstelefon='" + friendstelefon + '\'' +
                ", friendstoken='" + friendstoken + '\'' +
                ", friendsvorname='" + friendsvorname + '\'' +
                ", gespeichertestoken='" + gespeichertestoken + '\'' +
                ", hiobstoken='" + hiobstoken + '\'' +
                ", meinentoken='" + meinentoken + '\'' +
                ", messagetoken='" + messagetoken + '\'' +
                ", role='" + role + '\'' +
                ", letzteNachricht='" + letzteNachricht + '\'' +
                ", datumLetzteNachricht='" + datumLetzteNachricht + '\'' +
                '}';
    }

}
