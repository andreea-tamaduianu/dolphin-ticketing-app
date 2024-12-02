package ro.ase.dolphin;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ana.maria.tamaduianu
 */
@ComponentScan("ro.ase.dolphin")

@Configuration
public class SpringConfiguration  {
    //implements WebApplicationInitializer
 
//    public void onStartup(ServletContext container) throws ServletException {
// 
//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(ApplicationConfig.class);
//        ctx.setServletContext(container);
// 
//        ServletRegistration.Dynamic servlet = container.addServlet("rest", new DispatcherServlet(ctx));
// 
//        servlet.setLoadOnStartup(1);
//        servlet.addMapping("/rest");
//    }
 
}
