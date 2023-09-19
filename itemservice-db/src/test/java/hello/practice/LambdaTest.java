package hello.practice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LambdaTest {


    @Test
    void filterTest() {

        ArrayList<Integer> numberList = new ArrayList<>(Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> result = numberList.stream().filter(n -> {
                    if (n % 2 == 0) {
                        log.info("짝수에 필터링된 값={}", n);
                        return true;
                    }
                    log.info("짝수에 필터링되지 않은 수={}", n);
                    return n % 3 == 0;
                }).peek(n -> log.info("중간연산={}", n))
                .filter(n -> {
                    if (n > 0) {
                        log.info("양수에 필터링된 값={}", n);
                        return true;
                    }
                    log.info("양수에 필터링되지는 않은 값={}", n);
                    return n < -2;
                }).collect(Collectors.toList());
        log.info("짝수거나 3의 배수이면서 양수거나 -2보다 작은 결과 result={}", result);


    }
}
