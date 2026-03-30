package pt.unl.fct.di.adc.firstwebapp.model;

public class AuthToken {

    private String tokenId;
    private String username;
    private String role;
    private long issuedAt;
    private long expiresAt;

    public AuthToken() {
    }

    public AuthToken(String tokenId, String userId, String role, long issuedAt, long expiresAt) {
        this.tokenId = tokenId;
        this.username = userId;
        this.role = role;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(long issuedAt) {
        this.issuedAt = issuedAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "tokenId='" + tokenId + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", issuedAt=" + issuedAt +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
