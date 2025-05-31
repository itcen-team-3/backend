package com.team_3.nursing_care.domain.schedule.constant;

import java.util.Arrays;

public enum PaymentType {
    WASHING("목욕 급여"),
    VISITING("방문 급여"),
    NURSING("간호 급여"),
    DAYNIGHT("주야간 급여");

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
