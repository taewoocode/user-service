package com.example.user_service.point.repository;

import com.example.user_service.point.domain.Point;

public interface PointRepositoryCustom {
    Point findByUserIdWithLock(Long userId);
} 