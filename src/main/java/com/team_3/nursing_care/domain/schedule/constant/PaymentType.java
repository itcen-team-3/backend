package com.team_3.nursing_care.domain.schedule.constant;

import java.util.Arrays;

public enum PaymentType {
    WASHING("방문목욕"),
    VISITING("방문급여"),
    NURSING("방문간호"),
    SHORT("단기보호"),
    DAY_NIGHT("주야간보호급여"),
    FAMILY("가족요양급여");


    private final String paymentType;

    PaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public static PaymentType from(String paymentType) {
        return Arrays.stream(PaymentType.values())
                .filter(type -> type.getPaymentType().equals(paymentType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown PaymentType: " + paymentType));
    }
}
