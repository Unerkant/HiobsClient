package HiobsClient.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Den 12.10.2024
 */

@Entity
public class Auth {

    @Id
    @GeneratedValue
    private Integer id;
    private String datum;
    private String mail;
    private String other;
    private String pseudonym;
    private String role;
    private Long sperrdatum;
    private String token;

    public Auth(){}

    public Auth(Integer id, String datum, String mail, String other, String pseudonym, String role,
                    Long sperrdatum, String token){
        this.id         = id;
        this.datum      = datum;
        this.mail       = mail;
        this.other      = other;
        this.pseudonym  = pseudonym;
        this.role       = role;
        this.sperrdatum = sperrdatum;
        this.token      = token;


    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getOther() { return other;}
    public void setOther(String other) { this.other = other; }

    public String getPseudonym() { return pseudonym; }
    public void setPseudonym(String pseudonym) { this.pseudonym = pseudonym; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Long getSperrdatum() { return sperrdatum; }
    public void setSperrdatum(Long sperrdatum) { this.sperrdatum = sperrdatum; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }


    @Override
    public String toString() {
        return "Auth: {" +
                "id=" + id +
                ", datum='" + datum + '\'' +
                ", mail='" + mail + '\'' +
                ", other='" + other + '\'' +
                ", pseudonym='" + pseudonym + '\'' +
                ", role='" + role + '\'' +
                ", sperrdatum='" + sperrdatum + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
