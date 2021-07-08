package Level3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringBootApplication (exclude = { SecurityAutoConfiguration.class })
public class Level1MainClass {


    public static void main (String args[] )
    {
        /*SpringApplication.run(Level1MainClass.class, args);
        new RestControllerForCard();*/
        new Level1().LaunchLevel1Example();
    }

    @RestController
    @RequestMapping(value = "/")
    public class HomeController {

        @GetMapping
        public String index() {
            return "index";
        }
    }
}
