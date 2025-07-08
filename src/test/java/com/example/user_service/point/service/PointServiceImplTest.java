package com.example.user_service.point.service;

import com.example.user_service.point.domain.Point;
import com.example.user_service.point.domain.PointHistory;
import com.example.user_service.point.domain.PointHistoryType;
import com.example.user_service.point.repository.PointHistoryRepository;
import com.example.user_service.point.repository.PointRepository;
import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PointServiceImplTest {
    @Mock
    private PointHistoryRepository pointHistoryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointServiceImpl pointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addPoint_shouldIncreaseBalanceAndSaveHistory() {
        // given
        Long userId = 1L;
        Long amount = 1000L;
        User user = new User("test", "test@test.com", "pw", 0);
        Point point = new Point(user, 5000L);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(pointRepository.findByUserId(userId)).thenReturn(point);

        // when
        pointService.addPoint(userId, amount);

        // then
        ArgumentCaptor<Point> pointCaptor = ArgumentCaptor.forClass(Point.class);
        verify(pointRepository).save(pointCaptor.capture());
        Point savedPoint = pointCaptor.getValue();
        assertThat(savedPoint.getBalance()).isEqualTo(6000L);
        verify(pointHistoryRepository).save(any(PointHistory.class));
    }

    @Test
    void addPoint_shouldCreatePointIfNotExist() {
        // given
        Long userId = 2L;
        Long amount = 2000L;
        User user = new User("test2", "test2@test.com", "pw", 0);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(pointRepository.findByUserId(userId)).thenReturn(null);

        // when
        pointService.addPoint(userId, amount);

        // then
        ArgumentCaptor<Point> pointCaptor = ArgumentCaptor.forClass(Point.class);
        verify(pointRepository).save(pointCaptor.capture());
        Point savedPoint = pointCaptor.getValue();
        assertThat(savedPoint.getBalance()).isEqualTo(2000L);
        assertThat(savedPoint.getUser()).isEqualTo(user);
        verify(pointHistoryRepository).save(any(PointHistory.class));
    }
} 