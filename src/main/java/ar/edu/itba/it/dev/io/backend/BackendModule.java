package ar.edu.itba.it.dev.io.backend;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.type.YesNoType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.YearMonth;
import org.joda.time.contrib.hibernate.PersistentDateTime;
import org.joda.time.contrib.hibernate.PersistentLocalDate;
import org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime;

import ar.edu.itba.it.dev.common.jpa.domain.Identifiable;
import ar.edu.itba.it.dev.common.jpa.domain.IO;
import ar.edu.itba.it.dev.common.jpa.filter.EntityStore;
import ar.edu.itba.it.dev.common.jpa.hibernate.HibernateAutoWireInterceptor;
import ar.edu.itba.it.dev.common.jpa.hibernate.HibernateEmbeddableInstantiatorInterceptor;
import ar.edu.itba.it.dev.common.jpa.hibernate.HibernateEntityResolver;
import ar.edu.itba.it.dev.common.jpa.hibernate.HibernateSessionManager;
import ar.edu.itba.it.dev.common.jpa.hibernate.HibernateTemplate;
import ar.edu.itba.it.dev.common.jpa.hibernate.PersistentYearMonth;
import ar.edu.itba.it.dev.common.jpa.session.PersistenceSessionManager;
import ar.edu.itba.it.dev.io.backend.domain.CreateSamples;
import ar.edu.itba.it.dev.io.backend.domain.HibernatePersonRepo;
import ar.edu.itba.it.dev.io.backend.domain.HibernateStudentRepo;
import ar.edu.itba.it.dev.io.backend.domain.HibernateSubjectRepo;
import ar.edu.itba.it.dev.io.backend.domain.PersonIO;
import ar.edu.itba.it.dev.io.backend.domain.PersonRepo;
import ar.edu.itba.it.dev.io.backend.domain.StudentIO;
import ar.edu.itba.it.dev.io.backend.domain.StudentRepo;
import ar.edu.itba.it.dev.io.backend.domain.SubjectIO;
import ar.edu.itba.it.dev.io.backend.domain.SubjectRepo;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import flexjson.JSONSerializer;
import flexjson.transformer.AbstractTransformer;

public class BackendModule extends AbstractModule {
	@Override
	protected void configure() {
		// Disabled due to a bug in Guice 3.0 servlet module
		// TODO: Enable when updating Guice to version >3.0
		// binder().requireExplicitBindings();
		binder().disableCircularProxies();

		Properties properties = new Properties();
		try {
			properties.load(BackendModule.class.getResourceAsStream("/setup.properties"));
		} catch (IOException e) {
			throw new RuntimeException("No global configuration setup.properties found!");
		}

		Names.bindProperties(binder(), properties);
			
		bind(PersistenceSessionManager.class).to(HibernateSessionManager.class).asEagerSingleton();
		bind(EntityStore.class).to(HibernateEntityResolver.class).asEagerSingleton();
		bind(StudentRepo.class).to(HibernateStudentRepo.class);
		bind(PersonRepo.class).to(HibernatePersonRepo.class);
		bind(SubjectRepo.class).to(HibernateSubjectRepo.class);
		bind(CreateSamples.class).asEagerSingleton();
	}
	
	@Provides @Singleton
	public HibernateTemplate template(SessionFactory sessionFactory) {
		return new HibernateTemplate(sessionFactory);
	}

	@Provides @Singleton 
	public Configuration maindDbConfig(
			@Named("jdbc.driverClassName") String driver,
			@Named("jdbc.url") String url,
			@Named("jdbc.username") String username,
			@Named("jdbc.password") String password,
			@Named("hibernate.dialect") String dialect,
			HibernateAutoWireInterceptor autoWireInterceptor) {
		

		Configuration config = new Configuration();
		config.addAnnotatedClass(IO.class);
		config.addAnnotatedClass(StudentIO.class);
		config.addAnnotatedClass(PersonIO.class);
		config.addAnnotatedClass(SubjectIO.class);
		
		config.setInterceptor(autoWireInterceptor);
		config.setInterceptor(new HibernateEmbeddableInstantiatorInterceptor());

		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
		hibernateProperties.put("hibernate.show_sql", "true");
		hibernateProperties.put("hibernate.dialect", dialect);
		hibernateProperties.put("hibernate.connection.driver_class", driver);
		hibernateProperties.put("hibernate.connection.url", url);
		hibernateProperties.put("hibernate.connection.username", username);
		hibernateProperties.put("hibernate.connection.password", password);
				
		hibernateProperties.put("hibernate.cache.use_second_level_cache", "true");
		hibernateProperties.put("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
		hibernateProperties.put("hibernate.cache.provider_configuration_file_resource_path", "/ehcache.xml");
		hibernateProperties.put("hibernate.cache.use_structured_entries", "false");
		hibernateProperties.put("hibernate.current_session_context_class", "managed");
		hibernateProperties.put("hibernate.generate_statistic", "true");
		hibernateProperties.put("hibernate.max_fetch_depth", "2");
		hibernateProperties.put("hibernate.jdbc.batch_size", "30");
		hibernateProperties.put("javax.persistence.validation.mode", "ddl");

		hibernateProperties.put("hibernate.c3p0.min_size" , "5");
		hibernateProperties.put("hibernate.c3p0.max_size" , "100");
		hibernateProperties.put("hibernate.c3p0.timeout", "1800");
		hibernateProperties.put("hibernate.c3p0.max_statements", "50");

		config.setProperties(hibernateProperties);

		config.getTypeResolver().registerTypeOverride(PersistentLocalDate.INSTANCE, new String[]{ LocalDate.class.getName() } );
		config.getTypeResolver().registerTypeOverride(PersistentDateTime.INSTANCE, new String[] { DateTime.class.getName() });
		config.getTypeResolver().registerTypeOverride(PersistentLocalTimeAsTime.INSTANCE, new String[] { LocalTime.class.getName() });
		config.getTypeResolver().registerTypeOverride(PersistentYearMonth.INSTANCE, new String[] {YearMonth.class.getName()});
		config.getTypeResolver().registerTypeOverride(new YesNoType() {
			@Override
			public String getName() {
				return "boolean";
			}
		});
		
		return config;
	}

	@Provides
	@Singleton
	public SessionFactory sessionFactory(Configuration config) {
		return config.buildSessionFactory();
	}
}