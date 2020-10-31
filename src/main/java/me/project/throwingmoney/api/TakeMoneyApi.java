package me.project.throwingmoney.api;


import lombok.RequiredArgsConstructor;
import me.project.throwingmoney.domain.Token;
import me.project.throwingmoney.dto.TakeMoneyRequest;
import me.project.throwingmoney.dto.TakeMoneyResponse;
import me.project.throwingmoney.service.TakeMoneyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TakeMoneyApi {

    private final TakeMoneyService takeMoneyService;

    @PostMapping("/throw-moneys/{token}/take")
    public ResponseEntity<TakeMoneyResponse> throwMoney(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @RequestHeader(value = "X-ROOM-ID", required = false) String talkingRoom,
            @PathVariable(value = "token") String token
    ) {
        TakeMoneyRequest request = TakeMoneyRequest.of(userId, talkingRoom, Token.of(token));
        TakeMoneyResponse response = takeMoneyService.takeMoney(request);
        return ResponseEntity.ok(response);
    }
}
