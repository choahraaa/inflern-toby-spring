package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller //@Component를 metaAnnotation으로 가지고 있어 componentScan이 bean으로 등록시켜줌
@RestController
//restController : api의 기능을 가진 controller method에서는 리턴하는 값을 바디에 넣어서 처리하기 때문에 @ResponseBody가 필요
//class level에 requestMapping생략 가능(dispatcherServlet이 알아서 찾아줌, method level에 url을 넣어주면 됌)
public class HelloController  {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello(String name){
        if(name  == null || name.trim().length() == 0) throw new IllegalArgumentException();
        return helloService.sayHello(name);
    }
}
