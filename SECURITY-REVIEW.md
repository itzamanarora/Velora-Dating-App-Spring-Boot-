# Security Review

## Findings

### Medium: Refresh tokens remain usable after password reset

**Location:** `src/main/java/com/aman/Velora/auth_service/service/impl/AuthServiceImpl.java:181-200`

The refresh flow checks token expiry and its revoked flag, then returns the same refresh token. The token is created with a seven-day expiry in `src/main/java/com/aman/Velora/auth_service/service/impl/JwtServiceImpl.java:74-80`. A password reset does not invalidate outstanding refresh tokens.

If an attacker obtains a user's refresh token, changing the account password does not prevent the attacker from continuing to mint access tokens with that refresh token until it expires.

**Improvement:** Revoke the user's outstanding refresh tokens after a successful password reset. Rotate refresh tokens on use and invalidate the previously presented token.

**Confidence:** 8/10
