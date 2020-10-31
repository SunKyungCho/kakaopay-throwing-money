package me.project.throwingmoney.api;


import lombok.RequiredArgsConstructor;
import me.project.throwingmoney.dto.ThrowingMoneyResponse;
import me.project.throwingmoney.dto.ThrowingMoneyRequest;
import me.project.throwingmoney.service.ThrowMoneyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ThrowMoneyApi {

    private final ThrowMoneyService throwMoneyService;

    @PostMapping("/throw-moneys")
    public ResponseEntity<ThrowingMoneyResponse> throwMoney(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @RequestHeader(value = "X-ROOM-ID", required = false) String talkingRoom,
            @RequestParam Integer amount,
            @RequestParam Integer divideCount
    ) {
        ThrowingMoneyRequest request = ThrowingMoneyRequest.of(userId, talkingRoom, amount, divideCount);
        ThrowingMoneyResponse response = throwMoneyService.register(request);

        return ResponseEntity.ok(response);
    }
}
