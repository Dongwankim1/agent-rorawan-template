package co.irexnet.MINA.MINA_PlatformAgent.ws;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Component
public class SpringContext implements ApplicationContextAware{

	 private static ApplicationContext context;


	    public ApplicationContext getApplicationContext() {
	        return context;
	    }

	    // Generic method to return a beanClass
	    public static <T> T getBean(Class<T> beanClass)
	    {
	        return context.getBean(beanClass);
	    }

		@Override
		public void setApplicationContext(ApplicationContext applicationContext)
				throws BeansException {
			SpringContext.context = applicationContext;
			
		}
}
