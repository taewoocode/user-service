package com.example.user_service.point.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.user_service.user.domain.User;

@Entity
@Table(name = "point_history")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer amount; // 충전/사용 금액

    @Enumerated(EnumType.STRING)
    private PointHistoryType type; // CHARGE, USE 등

    private LocalDateTime createdAt;

    // 생성자, getter, setter 등은 Lombok 적용 시 @Getter, @NoArgsConstructor 등으로 대체 가능
}
