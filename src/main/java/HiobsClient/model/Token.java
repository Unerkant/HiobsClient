package HiobsClient.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Den 12.10.2024
 */

@Entity
public class Token {

    @Id
    @GeneratedValue
    private Long id;
    private String datum;
    private String token;
    private String mail;
    private String role;

    public Token(){}

    public Token(Long id, String datum, String token, String mail, String role){
        this.id         = id;
        this.datum      = datum;
        this.token      = token;
        this.mail       = mail;
        this.role       = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }


    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", datum='" + datum + '\'' +
                ", token='" + token + '\'' +
                ", mail='" + mail + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
