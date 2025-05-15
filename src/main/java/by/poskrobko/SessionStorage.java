package by.poskrobko;

import by.poskrobko.dto.UserDTO;
import by.poskrobko.service.UserService;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SessionStorage {
    private static final int SESSION_TTL_SECONDS = ApplicationConfig.getInt("session.ttl.seconds");

    private static final UserService userService = new UserService();

    private static final Map<String, SessionInfo> sessions = new HashMap<>();

    public static void addSession(String sessionId, String userId) {
        Instant expiry = Instant.now().plusSeconds(SESSION_TTL_SECONDS);
        sessions.put(sessionId, new SessionInfo(userId, expiry));
    }

    public static String getUserId(String sessionId) {
        SessionInfo info =  sessions.get(sessionId);
        if (info == null) {
            return null;
        } else if (Instant.now().isAfter(info.expiresAt)) {
            removeSession(sessionId);
            return null;
        }
        return info.userId;
    }

    public static UserDTO getUser(String sessionId) {
        String userId = getUserId(sessionId);
        if (userId != null) {
            return userService.findById(userId);
        }
        return null;
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public static boolean isValid(String sessionId) {
        return getUserId(sessionId) != null;
    }

    private static class SessionInfo {
        String userId;
        Instant expiresAt;

        public SessionInfo(String userId, Instant expiresAt) {
            this.userId = userId;
            this.expiresAt = expiresAt;
        }
    }
}
