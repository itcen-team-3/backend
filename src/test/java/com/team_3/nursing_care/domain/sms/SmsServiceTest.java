package com.team_3.nursing_care.domain.sms;

import com.team_3.nursing_care.common.exception.SmsException;
import com.team_3.nursing_care.common.util.RedisUtil;
import com.team_3.nursing_care.domain.sms.dto.ReqSmsDto;
import com.team_3.nursing_care.domain.sms.service.SmsServiceImpl;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.MessageType;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.http.HttpStatusCode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SmsServiceTest {

    @Mock
    private DefaultMessageService messageService;

    @Mock
    private RedisUtil redisUtil;

    @InjectMocks
    private SmsServiceImpl smsService;

    private final String sendNumber = "01012345678";

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(smsService, "apiKey", "test_api_key");
        ReflectionTestUtils.setField(smsService, "apiSecret", "test_api_secret");
        ReflectionTestUtils.setField(smsService, "sendNumber", sendNumber);
        ReflectionTestUtils.setField(smsService, "messageService", messageService);
    }

    @Test
    @DisplayName("SMS 전송 성공 시, 메시지를 전송하고 Redis 인증번호 저장")
    void Send_SMS_Success() throws Exception {
        // GIVEN
        String recipientPhoneNumber = "01073191927";
        ReqSmsDto reqDto = new ReqSmsDto(recipientPhoneNumber);
        String generatedAuthNumber = "1234";

        SingleMessageSentResponse mockResponse = mock(SingleMessageSentResponse.class);
        when(messageService.sendOne(any(SingleMessageSendingRequest.class))).thenReturn(mockResponse);

        ArgumentCaptor<SingleMessageSendingRequest> requestCaptor = ArgumentCaptor.forClass(SingleMessageSendingRequest.class);
        ArgumentCaptor<String> redisKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> redisValueCaptor = ArgumentCaptor.forClass(String.class);

        // WHEN
        SingleMessageSentResponse actualResponse = smsService.sendSms(reqDto);

        // THEN
        verify(messageService, times(1)).sendOne(requestCaptor.capture());

        Message sentMessage = requestCaptor.getValue().getMessage();
        assertThat(sentMessage.getTo()).isEqualTo(recipientPhoneNumber);
        assertThat(sentMessage.getFrom()).isEqualTo(sendNumber);
        assertThat(sentMessage.getType()).isEqualTo(MessageType.SMS);
        assertThat(sentMessage.getText()).startsWith("인증번호는 [");
        assertThat(sentMessage.getText()).endsWith("] 입니다.");
        String authNumberInMessage = sentMessage.getText() != null ? sentMessage.getText().substring("인증번호는 [".length(), sentMessage.getText().length() - "] 입니다.".length()) : null;
        assertThat(authNumberInMessage).hasSize(4);
        assertThat(authNumberInMessage).matches("\\d{4}");

        verify(redisUtil, times(1)).set(redisKeyCaptor.capture(), redisValueCaptor.capture(), eq(60));
        assertThat(redisKeyCaptor.getValue()).isEqualTo(recipientPhoneNumber);
        String redisStoredNumber = redisValueCaptor.getValue();
        assertThat(redisStoredNumber).hasSize(4);
        assertThat(redisStoredNumber).matches("\\d{4}");

        assertThat(actualResponse).isEqualTo(mockResponse);
    }

    @Test
    @DisplayName("SMS 전송 실패 시, SmsException Throw")
    void Send_SMS_FAILURE() throws Exception {
        // GIVEN
        String recipientPhoneNumber = "01073191927";
        ReqSmsDto reqDto = new ReqSmsDto(recipientPhoneNumber);

        SmsException expectedException = new SmsException(HttpStatusCode.INTERNAL_SERVER_ERROR, "Mock SMS send failure");
        when(messageService.sendOne(any(SingleMessageSendingRequest.class))).thenThrow(RuntimeException.class);

        // WHEN
        SmsException actualException = assertThrows(SmsException.class, () -> smsService.sendSms(reqDto));

        // THEN
        assertThat(actualException.getMessage()).isEqualTo("Failed to send SMS");
        assertThat(actualException.getCode()).isEqualTo(expectedException.getCode());
        assertThat(actualException.getResult()).isEqualTo(expectedException.getResult());

        verify(messageService, times(1)).sendOne(any(SingleMessageSendingRequest.class));
        verify(redisUtil, never()).set(anyString(), anyString(), anyInt());
    }

    @Test
    @DisplayName("잔액 조회 요청 성공")
    void GET_BALANCE_TEST() {
        // GIVEN
        Balance mockBalance = mock(Balance.class);
        when(messageService.getBalance()).thenReturn(mockBalance);

        // WHEN
        Balance actualBalance = smsService.getBalance();

        // THEN
        verify(messageService, times(1)).getBalance();
        assertThat(actualBalance).isEqualTo(mockBalance);
    }

    @Test
    @DisplayName("인증번호 캐시 존재 확인 ( RedisUtil.get() method 성공 )")
    void testExistCache() {
        // GIVEN
        String phoneNumber = "01073191927";
        String cachedValue = "1234";

        when(redisUtil.get(phoneNumber)).thenReturn(cachedValue);

        // WHEN
        Object actualCachedValue = smsService.existCache(phoneNumber);

        // THEN
        verify(redisUtil, times(1)).get(phoneNumber);
        assertThat(actualCachedValue).isEqualTo(cachedValue);
    }
}
