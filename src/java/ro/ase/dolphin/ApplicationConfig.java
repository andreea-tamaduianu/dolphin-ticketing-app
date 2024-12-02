package ro.ase.dolphin;

 
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 
//@Configuration
@ComponentScan(basePackages = "ro.ase.dolphin")
@EnableWebMvc
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    public ApplicationConfig() {
        System.out.println("ApplicationConfig-ApplicationConfig-ApplicationConfig-ApplicationConfig");
    }
 
    
//    @Bean
//    public InternalResourceViewResolver viewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/jsp/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }
 
}
