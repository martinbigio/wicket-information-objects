package ar.edu.itba.it.dev.common.jpa.guice.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.concurrent.ThreadSafe;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;

/**
 * Test runner that creates a Guice injector
 * <p>The injector used is defined by the {@link GuiceModules} annotation
 * that specifies a factory for the module to use </p>
 * <p> Injector will be reused among all the tests in the same suite
 * that specify the same Module {@link Provider} class
 */
@ThreadSafe
public class GuiceTestRunner extends BlockJUnit4ClassRunner {
    private static ConcurrentMap<Class<? extends Provider<? extends Module>>, Injector> injectors =  
    		new ConcurrentHashMap<Class<? extends Provider<? extends Module>>, Injector>();
    private Injector injector;
 
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface GuiceModules {
        Class<? extends Provider<? extends Module>> value();
    }
 
    @Override
    public Object createTest() throws Exception {
        Object obj = super.createTest();
        injector.injectMembers(obj);
        return obj;
    }
    
    public GuiceTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        Class<? extends Provider<? extends Module>> provider = providerFor(klass);
        try {
	        if (!injectors.containsKey(provider)) {
	        	injectors.putIfAbsent(provider, createInjector(provider));
	        }
	        injector = injectors.get(provider);
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
    }
    
    private Class<? extends Provider<? extends Module>> providerFor(Class<?> klass) throws InitializationError {
        GuiceModules annotation = null;
        Class<?> current = klass;
        while(annotation == null && current != null) {
        	annotation =  current.getAnnotation(GuiceModules.class);
        	current = current.getSuperclass();
        }
        if (annotation == null) {
            throw new InitializationError("Missing @GuiceModules annotation for unit test '" + klass.getName() + "'");
        }
        return annotation.value();
    }

    private Injector createInjector(Class<? extends Provider<? extends Module>> moduleFactory) throws InitializationError {
        try {
            Provider<? extends Module> provider = moduleFactory.newInstance();
            return Guice.createInjector(provider.get());
        } catch (InstantiationException e) {
            throw new InitializationError(e);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
    }
}