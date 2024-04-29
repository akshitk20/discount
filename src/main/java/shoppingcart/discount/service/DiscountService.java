package shoppingcart.discount.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import shoppingcart.discount.*;
import shoppingcart.discount.model.Coupon;
import shoppingcart.discount.repository.DiscountRepository;

import java.util.Objects;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class DiscountService extends DiscountProtoServiceGrpc.DiscountProtoServiceImplBase {

    private final DiscountRepository discountRepository;

    @Override
    public void getDiscount(GetDiscountRequest request, StreamObserver<CouponModel> responseObserver) {
        Coupon coupon = discountRepository.findByProductName(request.getProductName());
        if (Objects.isNull(coupon)) {
            coupon = Coupon.builder()
                    .id(-1)
                    .amount(0)
                    .description("No discount desc")
                    .productName("No discount")
                    .build();
        }
        log.info("Discount is retrieved for product name {} description {} and amount {} " ,coupon.getProductName(), coupon.getDescription(), coupon.getAmount());
        CouponModel couponModel = mapToCouponMode(coupon);
        responseObserver.onNext(couponModel);
        responseObserver.onCompleted();
    }


    @Override
    public void createDiscount(CreateDiscountRequest request, StreamObserver<CouponModel> responseObserver) {
        Coupon coupon = mapToCoupon(request.getCoupon());
        if (Objects.isNull(coupon))
            throw new RuntimeException("Invalid request object");
        Coupon persisteCoupon = discountRepository.save(coupon);
        CouponModel couponModel = mapToCouponMode(persisteCoupon);
        responseObserver.onNext(couponModel);
        responseObserver.onCompleted();
    }

    @Override
    public void updateDiscount(UpdateDiscountRequest request, StreamObserver<CouponModel> responseObserver) {
        Coupon coupon = mapToCoupon(request.getCoupon());
        if (Objects.isNull(coupon))
            throw new RuntimeException("Invalid request object");
        Coupon persisteCoupon = discountRepository.save(coupon);
        CouponModel couponModel = mapToCouponMode(persisteCoupon);
        responseObserver.onNext(couponModel);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteDiscount(DeleteDiscountRequest request, StreamObserver<DeleteDiscountResponse> responseObserver) {
        Coupon coupon = discountRepository.findByProductName(request.getProductName());
        if (Objects.isNull(coupon))
            throw new RuntimeException("No Coupon exists");
        discountRepository.delete(coupon);
        DeleteDiscountResponse deleteDiscountResponse = DeleteDiscountResponse.newBuilder()
                .setSuccess(true)
                .build();
        responseObserver.onNext(deleteDiscountResponse);
        responseObserver.onCompleted();
    }

    private CouponModel mapToCouponMode(Coupon coupon) {
        return CouponModel.newBuilder()
                .setId(coupon.getId())
                .setDescription(coupon.getDescription())
                .setProductName(coupon.getProductName())
                .setAmount(coupon.getAmount())
                .build();

    }

    private Coupon mapToCoupon(CouponModel coupon) {
        return Coupon.builder()
                .id(coupon.getId())
                .productName(coupon.getProductName())
                .description(coupon.getDescription())
                .amount(coupon.getAmount())
                .build();
    }
}
