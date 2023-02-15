package tobyspring.helloboot;

import org.springframework.stereotype.Service;

@Service //@Component를 metaAnnotation으로 가지고 있어 componentScan이 bean으로 등록시켜줌
public class SimpleHelloService implements HelloService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
