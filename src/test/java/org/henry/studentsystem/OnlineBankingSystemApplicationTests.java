package org.henry.studentsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OnlineBankingSystemApplicationTests {

    Calculator underTest = new Calculator();

    @Test
    void itShouldAddNumbers() {
//        given
        int num1 = 20;
        int num2 = 40;

//        when
        int result = underTest.add(num1, num2);

//        then
        assertThat(result).isEqualTo(60);
    }

    class Calculator{
        int add(int a, int b){
            return a + b;
        }
    }

}
