package me.project.throwingmoney.repository;

import me.project.throwingmoney.domain.ThrowMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ThrowingMoneyRepository extends JpaRepository<ThrowMoney, Long> {

    @Query("select tm from ThrowMoney as tm where  tm.talkingRoom = :talkingRoom and tm.token.value = :token")
    List<ThrowMoney> findThrowMoneyByTokenAndTalkingRoom(String talkingRoom, String token);


    @Query("select tm from ThrowMoney as tm where tm.token.expireTime > ?1 and tm.token.value = ?2 and tm.talkingRoom = ?3")
    ThrowMoney findThrowMoneyByTokenAndTalkingRoomInValid(LocalDateTime now, String token, String talkingRoom);

}
