package web.pulso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "google_calendar_tokens")
public class GoogleCalendarToken extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 1000)
    private String accessToken;

    @Column(nullable = false, length = 1000)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
