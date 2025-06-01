/*package com.team_3.nursing_care.domain.sms.service;

import com.team_3.nursing_care.common.exception.SmsException;
import com.team_3.nursing_care.common.util.RedisUtil;
import com.team_3.nursing_care.domain.sms.dto.ReqSmsDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.MessageType;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.Random;

@Slf4j(topic = "message-service")
@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private DefaultMessageService messageService;
    private final RedisUtil redisUtil;
    @Value("${coolSMS.api_key}")
    private String apiKey;
    @Value("${coolSMS.api_secret}")
    private String apiSecret;
    @Value("${coolSMS.send_number}")
    private String sendNumber;

    @PostConstruct
    public void init() {
        try {
            this.messageService = NurigoApp.INSTANCE.initialize(this.apiKey, this.apiSecret, "https://api.coolsms.co.kr");
            log.info("Nuri goApp Initialize Success.");
        } catch (Exception e) {
            log.error("Nuri goApp Initializing Error!", e);
            throw new RuntimeException("Nuri goApp initialize Failure. API KEY/SECRET required confirm.", e);
        }
    }

    @Override
    @Transactional
    public SingleMessageSentResponse sendSms(ReqSmsDto dto) {
        try {
            String numStr = generateRandomNumber();

            Message message = new Message();
            message.setTo(dto.getToPhoneNumber());
            message.setFrom(sendNumber);
            message.setText("인증번호는 [" + numStr + "] 입니다.");
            message.setType(MessageType.SMS);

            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            redisUtil.set(dto.getToPhoneNumber(), numStr, 60);

            return response;
        } catch (Exception e) {
            throw new SmsException(HttpStatusCode.INTERNAL_SERVER_ERROR, "Failed to send SMS");
        }
    }

    @Override
    public Balance getBalance() {
        return messageService.getBalance();
    }

    @Override
    @Transactional(readOnly = true)
    public Object existCache(String toPhoneNumber) {
        return redisUtil.get(toPhoneNumber);
    }

    private String generateRandomNumber() {
        Random rand = new Random();
        StringBuilder numStr = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            numStr.append(rand.nextInt(10));
        }
        return numStr.toString();
    }
}
 */