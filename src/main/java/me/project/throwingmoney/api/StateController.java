package me.project.throwingmoney.api;

import lombok.RequiredArgsConstructor;
import me.project.throwingmoney.domain.Token;
import me.project.throwingmoney.dto.ThrowMoneyStatReadRequest;
import me.project.throwingmoney.dto.ThrowMoneyStateResponse;
import me.project.throwingmoney.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/throw-moneys")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

    @GetMapping("/{token}")
    public ResponseEntity<ThrowMoneyStateResponse> throwMoney(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @RequestHeader(value = "X-ROOM-ID", required = false) String talkingRoom,
            @PathVariable(value = "token") String token
    ) {
        ThrowMoneyStatReadRequest request = ThrowMoneyStatReadRequest.of(userId, talkingRoom, Token.of(token));
        ThrowMoneyStateResponse response = stateService.getState(request);
        return ResponseEntity.ok(response);
    }
}