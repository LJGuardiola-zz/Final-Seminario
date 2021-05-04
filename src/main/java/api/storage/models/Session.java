package api.storage.models;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

import java.time.Instant;
import java.util.Objects;

public class Session {

    @NotNegative
    private Integer ID;

    @NotNull
    @NotNegative
    private final Integer userID;

    @NotNull
    @NotEmpty
    private final String IP;

    @NotNull
    @NotEmpty
    private String token;

    @NotNull
    private Instant updateAt;

    public Session(Integer userID, String IP, String token) {
        this(null, userID, IP, token, Instant.now());
    }

    public Session(Integer ID, Integer userID, String IP, String token, Instant updateAt) {
        this.ID = ID;
        this.userID = userID;
        this.IP = IP;
        this.token = token;
        this.updateAt = updateAt;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getIP() {
        return IP;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(ID, session.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

}
