package me.project.throwingmoney.api;

import me.project.throwingmoney.dto.TakeMoneyResponse;
import me.project.throwingmoney.error.EmptyMoneyException;
import me.project.throwingmoney.service.TakeMoneyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TakeMoneyApi.class)
class TakeMoneyApiTest {

    private final static String RECEIVE_URL = "/throw-moneys/tst/take";

    @MockBean
    private TakeMoneyService takeMoneyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("이미 모두 소진되었을때 exception")
    void throw_money_receive_EmptyException() throws Exception {

        //given
        given(takeMoneyService.takeMoney(null)).willThrow(new EmptyMoneyException("test"));

        mockMvc.perform(post(RECEIVE_URL)
                .header("X-ROOM-ID", "test_room")
                .param("amount", "10000")
                .param("divideCount", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("받기 요청")
    void throwMoney() throws Exception {

        //given
        given(takeMoneyService.takeMoney(null)).willReturn(new TakeMoneyResponse(5000));

        mockMvc.perform(post(RECEIVE_URL)
                .header("X-USER-ID", 1)
                .header("X-ROOM-ID", "test_room")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Room 파리미터 없을시 400")
    void throw_money_without_X_ROOM_ID() throws Exception {

        mockMvc.perform(post(RECEIVE_URL)
                .header("X-USER-ID", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("사용자 ID 0보다 작을 경우 예외처리")
    void throw_money_userId_less_than_0() throws Exception {

        mockMvc.perform(post(RECEIVE_URL)
                .header("X-USER-ID", 0)
                .header("X-ROOM-ID", "test_room")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("User Id 없을시 400")
    void throw_money_without_X_USER_ID() throws Exception {
        mockMvc.perform(post(RECEIVE_URL)
                .header("X-ROOM-ID", "test_room")
                .param("amount", "10000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}