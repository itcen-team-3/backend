package com.team_3.nursing_care.domain.sms.service;

import com.team_3.nursing_care.domain.sms.dto.ReqSmsDto;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface SmsService {

    SingleMessageSentResponse sendSms(ReqSmsDto dto);
    Balance getBalance();
    Object existCache(String toPhoneNumber);

}
