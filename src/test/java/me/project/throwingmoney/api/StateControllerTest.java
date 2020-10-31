package me.project.throwingmoney.api;

import me.project.throwingmoney.domain.Money;
import me.project.throwingmoney.domain.Token;
import me.project.throwingmoney.dto.ThrowMoneyStateResponse;
import me.project.throwingmoney.service.StateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StateController.class)
@DisplayName("뿌리기 읽어오기")
class StateControllerTest {

    private final static String STATE_URL = "/throw-moneys/tst";

    @MockBean
    private StateService stateService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("조회 요청")
    void throwMoney_state() throws Exception {

        ThrowMoneyStateResponse throwMoneyStateResponse = new ThrowMoneyStateResponse(1, 10000, 5, "test_room", Token.of("tst"), new ArrayList<Money>());

        //given
        given(stateService.getState(null)).willReturn(throwMoneyStateResponse);

        mockMvc.perform(get(STATE_URL)
                .header("X-USER-ID", 1)
                .header("X-ROOM-ID", "test_room")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Room 파리미터 없을시 400")
    void throw_money_without_X_ROOM_ID() throws Exception {

        mockMvc.perform(get(STATE_URL)
                .header("X-USER-ID", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("사용자 ID 0보다 작을 경우 예외처리")
    void throw_money_userId_less_than_0() throws Exception {

        mockMvc.perform(get(STATE_URL)
                .header("X-USER-ID", 0)
                .header("X-ROOM-ID", "test_room")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("User Id 없을시 400")
    void throw_money_without_X_USER_ID() throws Exception {
        mockMvc.perform(get(STATE_URL)
                .header("X-ROOM-ID", "test_room")
                .param("amount", "10000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}