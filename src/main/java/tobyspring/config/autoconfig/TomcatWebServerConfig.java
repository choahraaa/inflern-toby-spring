package tobyspring.config.autoconfig;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
public class TomcatWebServerConfig {
    @Value("${contextPath:}")
    String contextPath;
    @Value("${port:8080}") //지정한 프로퍼티 값이 없으면 default값으로 설정 (: 사용)
    int port;
    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean // 동일한 타입의 bean이 있는지 체크하고 없으면 사용하겠다는 의미(사용자 정보에 저장된 bean을 우선 처리)
    public ServletWebServerFactory ServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        factory.setContextPath(this.contextPath);
        factory.setPort(this.port);

        return factory;
    }

}
