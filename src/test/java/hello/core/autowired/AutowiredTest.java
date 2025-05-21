package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        // Member는 스프링 빈이 아니라 테스트
        // 생성자 주입에서 특정 필드에만 @Nullable 사용해도됨
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) { // required false를 하면 자동 주입할 대상 자체가 없으면 이 메서드 자체가 호출x
            System.out.println("noBean1 = " + noBean1);
        }
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) { // 호출o, null일시 null 반환
            System.out.println("noBean2 = " + noBean2);
        }
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) { // java8문법 활용, bean 없을시 optional.empty로 반환
            System.out.println("noBean3 = " + noBean3);
        }

    }
}
