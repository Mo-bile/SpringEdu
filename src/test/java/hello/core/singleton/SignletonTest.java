package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SignletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른것 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //두개가 다름
        // -> 객체는 2개에 그 안에 추가생성까지 해서 4개가 생성됨
//        memberService1 = hello.core.member.MemberServiceImpl@32a068d1
//        memberService2 = hello.core.member.MemberServiceImpl@33cb5951

        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체사용")
    void singletonServiceTest() {

        //생성한것을 가져다 쓰는것에 불과함
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        //이제 같은것이 출력됨
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
        // same : ==  (인스턴스 비교)
        // equla : equlas
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
//        AppConfig appConfig = new AppConfig();

        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = applicationContext.getBean("memberService", MemberService.class);
        // 2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = applicationContext.getBean("memberService", MemberService.class);

        //참조값이 다른것 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //두개가 다름
        // -> 객체는 2개에 그 안에 추가생성까지 해서 4개가 생성됨
//        memberService1 = hello.core.member.MemberServiceImpl@32a068d1
//        memberService2 = hello.core.member.MemberServiceImpl@33cb5951

        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
