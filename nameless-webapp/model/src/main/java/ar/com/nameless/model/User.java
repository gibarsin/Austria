package ar.com.nameless.model;

public class User {
    private long userID;
    private String username;
    private String email;
    private String password;
    private boolean verified;

    //TODO: Facebook credentials


    public long getUserID() {
        return userID;
    }

    public void setUserID(final long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(final boolean verified) {
        this.verified = verified;
    }
}
