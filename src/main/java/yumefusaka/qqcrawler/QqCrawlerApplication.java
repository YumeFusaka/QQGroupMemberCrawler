package yumefusaka.qqcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QqCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QqCrawlerApplication.class, args);
    }

}
