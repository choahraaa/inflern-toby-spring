package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HelloApiTest {

    @Test
    void helloApi() {
        //http localhost:8080/hello?name=spring
        TestRestTemplate rest = new TestRestTemplate();

        //응답 가져오기
        ResponseEntity<String> res =
                rest.getForEntity("http://localhost:9090/hello?name={name}", String.class, "Spring");

        //status code 200
        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK); //검증하고 싶은 것을 가져옴(비교타입 맞춰서)
        //header(content-type) text/plain
        Assertions.assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE); //res.getHeaders() 헤더 전체를 담은 collection return
        //body hello spring
        Assertions.assertThat(res.getBody()).isEqualTo("*Hello Spring*");
    }
}
