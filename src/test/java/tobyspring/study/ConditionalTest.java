package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.Objects;

public class ConditionalTest {
    @Test
    void conditional() {
        //true
        ApplicationContextRunner contextRunner = new ApplicationContextRunner();
        contextRunner.withUserConfiguration(Config1.class)
                .run(context -> {
                    Assertions.assertThat(context).hasSingleBean(MyBean.class); //이런 타입의 빈을 가지고 있는지 체크
                    Assertions.assertThat(context).hasSingleBean(Config1.class); //이런 타입의 빈을 가지고 있는지 체크
                });

        //false
        new ApplicationContextRunner().withUserConfiguration(Config2.class)
                .run(context -> {
                    Assertions.assertThat(context).doesNotHaveBean(MyBean.class); //이런 타입의 빈을 포함되지 않는지 체크
                    Assertions.assertThat(context).doesNotHaveBean(Config1.class); //이런 타입의 빈을 포함되지 않는지 체크
                });
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class)
    @interface  BooleanConditional {
        boolean value();
    } //trueConditional 에노테이션 생성
    @Configuration
    @BooleanConditional(true)
    static class Config1 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    @Configuration
    @BooleanConditional(false)
    static class Config2 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    static class MyBean {}

    static class BooleanCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            //어노테이션의 값을 가지고 와서 condition을 처리하는 로직
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName()); //에노테이션 안에 있는 속성값들을 읽어옴
            Boolean value = (Boolean) annotationAttributes.get("value"); //name이 value인 값을 가져옴
            return value;
        }
    }

}
