package me.project.throwingmoney.api;

import me.project.throwingmoney.domain.Token;
import me.project.throwingmoney.dto.ThrowingMoneyResponse;
import me.project.throwingmoney.service.ThrowMoneyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ThrowMoneyApi.class)
class ThrowMoneyApiTest {

    @MockBean
    private ThrowMoneyService throwMoneyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("뿌리기 요청 200")
    void throwMoney() throws Exception {

        //given
        given(throwMoneyService.register(null)).willReturn(new ThrowingMoneyResponse(Token.of("tes")));
        mockMvc.perform(post("/throw-moneys")
                .header("X-USER-ID", 1)
                .header("X-ROOM-ID", "test_room")
                .param("amount", "10000")
                .param("divideCount", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Room 파리미터 없을시 400")
    void throw_money_without_X_ROOM_ID() throws Exception {

        mockMvc.perform(post("/throw-moneys")
                .header("X-USER-ID", 1)
                .param("amount", "10000")
                .param("divideCount", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("사용자 ID 0보다 작을 경우 예외처리")
    void thssrow_money_userId_less_than_0() throws Exception {

        mockMvc.perform(post("/throw-moneys")
                .header("X-USER-ID", "test")
                .header("X-ROOM-ID", "test_room")
                .param("amount", "10000")
                .param("divideCount", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자 ID 0보다 작을 경우 예외처리")
    void throw_money_userId_less_than_0() throws Exception {

        mockMvc.perform(post("/throw-moneys")
                .header("X-USER-ID", 0)
                .header("X-ROOM-ID", "test_room")
                .param("amount", "10000")
                .param("divideCount", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("User Id 없을시 400")
    void throw_money_without_X_USER_ID() throws Exception {
        mockMvc.perform(post("/throw-moneys")
                .header("X-ROOM-ID", "test_room")
                .param("amount", "10000")
                .param("divideCount", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}