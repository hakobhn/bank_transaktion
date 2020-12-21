package am.neovision.admin.toolkit;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptableProperties
@SpringBootApplication
public class AdminToolkit {
    public static void main(String[] args) {
        SpringApplication.run(AdminToolkit.class, args);
    }
}
