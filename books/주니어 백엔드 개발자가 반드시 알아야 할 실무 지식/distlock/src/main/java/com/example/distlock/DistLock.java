package com.example.distlock;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class DistLock {

    private final DataSource dataSource;

    public DistLock(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean tryLock(String name, String owner, Duration duration) {
        Connection conn = null;
        boolean owned = false;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            LockOwner lockOwner = getLockOwner(conn, name);
            if (lockOwner == null || lockOwner.owner() == null) {
                // 아직 소유자가 없음 - 잠금 소유 시도
                insertLockOwner(conn, name, owner, duration);
                owned = true;
            } else if (lockOwner.isOwnedBy(owner)) {
                // 소유자가 같음 - 만료 시간 연장
                updateLockOwner(conn, name, owner, duration);
                owned = true;
            } else if (lockOwner.isExpired()) {
                // 소유자 다름 && 만료 시간 지남 - 잠금 소유 시도
                updateLockOwner(conn, name, owner, duration);
                owned = true;
            } else {
                // 소유자 다름 && 만료 시간 안 지남 - 잠금 소유 실패
                owned = false;
            }
            conn.commit();
        } catch (Exception e) {
            owned = false;
            rollback(conn);
        } finally {
            close(conn);
        }

        return owned;
    }

    public void unlock(String name, String owner) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            LockOwner lockOwner = getLockOwner(conn, name);
            if (lockOwner == null || lockOwner.isOwnedBy(owner)) {
                throw new IllegalStateException("no lock owner");
            }
            if (lockOwner.isExpired()) {
                throw new IllegalStateException("lock is expired");
            }
            clearOwner(conn, name);
            conn.commit();
        } catch (Exception e) {
            rollback(conn);
            throw new RuntimeException(e);
        } finally {
            close(conn);
        }
    }

    private LockOwner getLockOwner(Connection conn, String name) throws SQLException {
        try (PreparedStatement psmt = conn.prepareStatement("select * from dist_lock where name = ? for update")) {
            psmt.setString(1, name);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return new LockOwner(
                        rs.getString("owner"),
                        rs.getTimestamp("expiry").toLocalDateTime()
                    );
                }
            }
        }

        return null;
    }

    private void insertLockOwner(Connection conn, String name, String owner, Duration duration) throws SQLException {
        try (PreparedStatement psmt = conn.prepareStatement("insert into dist_lock (name, owner, expiry) values (?, ?, ?)")) {
            psmt.setString(1, name);
            psmt.setString(2, owner);
            psmt.setTimestamp(3, getExpiry(duration));
            psmt.executeUpdate();
        }
    }

    private Timestamp getExpiry(Duration duration) {
        return Timestamp.valueOf(LocalDateTime.now().plus(duration));
    }

    private void updateLockOwner(Connection conn, String name, String owner, Duration duration) throws SQLException {
        try (PreparedStatement psmt = conn.prepareStatement("update dist_lock set owner = ?, expiry = ? where name = ?")) {
            psmt.setString(1, owner);
            psmt.setTimestamp(2, getExpiry(duration));
            psmt.setString(3, name);
            psmt.executeUpdate();
        }
    }

    private void clearOwner(Connection conn, String name) {
        try (PreparedStatement psmt = conn.prepareStatement("update dist_lock set owner = null, expiry = null where name = ?")) {
            psmt.setString(1, name);
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear lock owner", e);
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
            }
        }
    }

    private void close(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
            }
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

}
