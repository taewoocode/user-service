package com.example.user_service.payment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponUsage is a Querydsl query type for CouponUsage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponUsage extends EntityPathBase<CouponUsage> {

    private static final long serialVersionUID = -486847339L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponUsage couponUsage = new QCouponUsage("couponUsage");

    public final QCoupon coupon;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPayment payment;

    public final BooleanPath used = createBoolean("used");

    public final DateTimePath<java.time.LocalDateTime> usedAt = createDateTime("usedAt", java.time.LocalDateTime.class);

    public final com.example.user_service.user.domain.QUser user;

    public QCouponUsage(String variable) {
        this(CouponUsage.class, forVariable(variable), INITS);
    }

    public QCouponUsage(Path<? extends CouponUsage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponUsage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponUsage(PathMetadata metadata, PathInits inits) {
        this(CouponUsage.class, metadata, inits);
    }

    public QCouponUsage(Class<? extends CouponUsage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new QCoupon(forProperty("coupon")) : null;
        this.payment = inits.isInitialized("payment") ? new QPayment(forProperty("payment"), inits.get("payment")) : null;
        this.user = inits.isInitialized("user") ? new com.example.user_service.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

