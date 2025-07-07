package com.example.user_service.point.repository;

import com.example.user_service.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryCustom {
    Point findByUserId(Long userId);
}