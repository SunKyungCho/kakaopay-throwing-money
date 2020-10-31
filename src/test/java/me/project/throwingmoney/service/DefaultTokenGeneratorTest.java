package me.project.throwingmoney.service;

import me.project.throwingmoney.domain.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultTokenGeneratorTest {


    private DefaultTokenGenerator defaultTokenGenerator;

    @BeforeEach
    void setUp() {
        defaultTokenGenerator = new DefaultTokenGenerator();
    }

    @Test
    @DisplayName("Token은 3자리 문자열")
    void test() {
        Token token = defaultTokenGenerator.create();
        assertThat(token.getValue().length()).isEqualTo(3);
    }
}