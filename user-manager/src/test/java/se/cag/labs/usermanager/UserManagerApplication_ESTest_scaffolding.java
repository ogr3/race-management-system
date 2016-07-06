/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Wed Jul 06 18:47:40 GMT 2016
 */

package se.cag.labs.usermanager;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class UserManagerApplication_ESTest_scaffolding {

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);

  @BeforeClass 
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "se.cag.labs.usermanager.UserManagerApplication"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init(); 
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @AfterClass 
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before 
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("java.vm.vendor", "Oracle Corporation"); 
    java.lang.System.setProperty("java.specification.version", "1.8"); 
            java.lang.System.setProperty("java.home", "/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre"); 
        java.lang.System.setProperty("java.awt.headless", "true"); 
    java.lang.System.setProperty("user.home", "/Users/fredrik"); 
                                        java.lang.System.setProperty("user.dir", "/Users/fredrik/Documents/github/race-management-system/user-manager"); 
    java.lang.System.setProperty("java.io.tmpdir", "/var/folders/d5/cjqk01_159b0580b_872c3s00000gn/T/"); 
    java.lang.System.setProperty("line.separator", "\n"); 
              }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(UserManagerApplication_ESTest_scaffolding.class.getClassLoader() ,
      "org.springframework.core.convert.support.ObjectToStringConverter",
      "org.springframework.boot.SpringBootBanner",
      "org.springframework.validation.Validator",
      "org.springframework.core.env.PropertySource$ComparisonPropertySource",
      "org.springframework.boot.json.JsonParser",
      "org.springframework.context.ApplicationContextException",
      "org.springframework.validation.BindingErrorProcessor",
      "org.springframework.context.event.AbstractApplicationEventMulticaster$ListenerRetriever",
      "org.springframework.boot.context.event.ApplicationStartedEvent",
      "org.springframework.core.convert.support.NumberToNumberConverterFactory",
      "org.springframework.boot.logging.logback.LogbackLoggingSystem",
      "org.springframework.core.io.Resource",
      "org.springframework.util.PropertyPlaceholderHelper",
      "org.springframework.core.convert.support.ArrayToObjectConverter",
      "org.springframework.format.Parser",
      "org.springframework.core.convert.support.StringToBooleanConverter",
      "org.springframework.core.annotation.AnnotationUtils",
      "org.springframework.util.StopWatch",
      "org.springframework.core.PriorityOrdered",
      "org.springframework.core.convert.support.DefaultConversionService$Jsr310ConverterRegistrar",
      "org.springframework.beans.factory.support.BeanNameGenerator",
      "org.springframework.core.Ordered",
      "org.springframework.util.ConcurrentReferenceHashMap$Entry",
      "org.springframework.boot.context.config.DelegatingApplicationContextInitializer",
      "org.springframework.util.ConcurrentReferenceHashMap$TaskOption",
      "org.springframework.core.ResolvableTypeProvider",
      "org.springframework.format.Printer",
      "org.springframework.beans.factory.FactoryBean",
      "org.springframework.beans.factory.BeanFactoryAware",
      "org.springframework.boot.BeanDefinitionLoader",
      "org.springframework.core.env.EnvironmentCapable",
      "org.springframework.boot.context.ContextIdApplicationContextInitializer",
      "org.springframework.core.convert.ConversionService",
      "org.springframework.util.ConcurrentReferenceHashMap$Entries",
      "org.springframework.beans.BeansException",
      "org.springframework.core.convert.support.GenericConversionService$ConvertersForPair",
      "org.springframework.data.mongodb.repository.config.EnableMongoRepositories",
      "org.springframework.core.ResolvableType$1",
      "org.springframework.beans.factory.BeanFactory",
      "org.springframework.boot.context.event.ApplicationPreparedEvent",
      "com.fasterxml.jackson.core.Versioned",
      "org.springframework.context.annotation.ComponentScan$Filter",
      "org.springframework.boot.SpringApplication",
      "org.springframework.core.env.PropertySource$StubPropertySource",
      "org.springframework.beans.PropertyBatchUpdateException",
      "org.springframework.core.SpringProperties",
      "org.springframework.boot.ApplicationRunner",
      "org.springframework.core.convert.support.ArrayToArrayConverter",
      "org.springframework.boot.bind.DefaultPropertyNamePatternsMatcher",
      "org.springframework.core.ErrorCoded",
      "org.springframework.core.env.MutablePropertySources",
      "org.springframework.boot.json.JacksonJsonParser",
      "org.springframework.util.ConcurrentReferenceHashMap$SoftEntryReference",
      "org.springframework.beans.BeanUtils",
      "org.springframework.beans.BeanInstantiationException",
      "org.springframework.core.io.UrlResource",
      "org.springframework.core.convert.support.GenericConversionService$ConverterAdapter",
      "org.springframework.core.convert.support.CharacterToNumberFactory",
      "org.springframework.boot.env.YamlPropertySourceLoader",
      "org.springframework.boot.builder.ParentContextCloserApplicationListener",
      "org.springframework.core.annotation.AnnotationUtils$AnnotationCacheKey",
      "org.springframework.beans.PropertyAccessor",
      "org.springframework.aop.TargetClassAware",
      "org.springframework.validation.BeanPropertyBindingResult",
      "org.springframework.context.event.ApplicationContextEvent",
      "org.springframework.core.convert.converter.Converter",
      "org.springframework.boot.env.EnumerableCompositePropertySource",
      "org.springframework.boot.logging.LoggingInitializationContext",
      "org.springframework.context.event.GenericApplicationListener",
      "org.springframework.core.annotation.AliasFor",
      "org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean",
      "org.springframework.beans.FatalBeanException",
      "org.springframework.boot.Banner",
      "org.springframework.core.convert.converter.ConverterFactory",
      "org.springframework.core.convert.support.StringToCollectionConverter",
      "org.springframework.core.SerializableTypeWrapper$TypeProxyInvocationHandler",
      "org.springframework.context.event.ContextRefreshedEvent",
      "org.springframework.util.ReflectionUtils",
      "org.springframework.util.Assert",
      "org.springframework.core.convert.support.ConfigurableConversionService",
      "org.springframework.core.convert.support.StringToCharsetConverter",
      "org.springframework.util.ReflectionUtils$FieldCallback",
      "org.springframework.core.convert.support.StringToNumberConverterFactory",
      "org.springframework.core.annotation.SynthesizedAnnotationInvocationHandler",
      "org.springframework.beans.TypeConverter",
      "org.springframework.core.GenericTypeResolver",
      "org.springframework.util.ConcurrentReferenceHashMap$WeakEntryReference",
      "org.springframework.core.convert.support.StringToEnumConverterFactory",
      "org.springframework.validation.FieldError",
      "org.springframework.core.convert.converter.ConverterRegistry",
      "org.springframework.beans.TypeMismatchException",
      "org.springframework.boot.SpringBootExceptionHandler",
      "org.springframework.boot.bind.RelaxedDataBinder$BeanPath",
      "org.springframework.util.CollectionUtils",
      "org.springframework.core.convert.support.EnumToStringConverter",
      "org.springframework.core.convert.support.DefaultConversionService",
      "org.springframework.beans.factory.ListableBeanFactory",
      "org.springframework.boot.env.SpringApplicationJsonEnvironmentPostProcessor",
      "org.springframework.beans.BeanWrapper",
      "org.springframework.jndi.JndiAccessor",
      "org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer",
      "org.springframework.util.ConcurrentReferenceHashMap$ReferenceManager",
      "org.springframework.core.convert.ConverterNotFoundException",
      "org.springframework.context.event.ContextClosedEvent",
      "org.springframework.beans.ExtendedBeanInfo$SimplePropertyDescriptor",
      "org.springframework.core.env.MissingRequiredPropertiesException",
      "org.springframework.core.env.ConfigurableEnvironment",
      "org.springframework.format.Formatter",
      "org.springframework.core.convert.support.FallbackObjectToStringConverter",
      "org.springframework.boot.context.FileEncodingApplicationListener",
      "org.springframework.util.ConcurrentReferenceHashMap$1",
      "org.springframework.util.ConcurrentReferenceHashMap$2",
      "org.springframework.util.ConcurrentReferenceHashMap$3",
      "org.springframework.boot.DefaultApplicationArguments$Source",
      "com.fasterxml.jackson.core.TreeCodec",
      "org.springframework.boot.autoconfigure.SpringBootApplication",
      "org.springframework.util.ConcurrentReferenceHashMap$4",
      "org.springframework.core.convert.converter.ConditionalGenericConverter",
      "org.springframework.aop.SpringProxy",
      "org.springframework.boot.ExitCodeEvent",
      "org.apache.commons.logging.impl.SLF4JLocationAwareLog",
      "org.springframework.context.annotation.Import",
      "org.springframework.core.convert.support.GenericConversionService",
      "org.springframework.boot.logging.LogLevel",
      "org.springframework.web.context.ConfigurableWebEnvironment",
      "org.springframework.core.convert.support.IdToEntityConverter",
      "org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport",
      "org.springframework.util.ConcurrentReferenceHashMap$5",
      "org.springframework.beans.factory.HierarchicalBeanFactory",
      "org.springframework.beans.PropertyEditorRegistrySupport",
      "org.springframework.util.ConcurrentReferenceHashMap$Reference",
      "org.springframework.boot.context.config.DelegatingApplicationListener",
      "org.springframework.util.ConcurrentReferenceHashMap$Segment",
      "org.springframework.boot.Banner$Mode",
      "org.springframework.beans.factory.support.BeanDefinitionRegistry",
      "org.springframework.boot.context.config.AnsiOutputApplicationListener",
      "org.springframework.core.ResolvableType$SyntheticParameterizedType",
      "org.springframework.boot.logging.DeferredLog",
      "org.springframework.core.ResolvableType",
      "org.springframework.core.convert.support.StringToTimeZoneConverter",
      "org.springframework.core.convert.support.ZoneIdToTimeZoneConverter",
      "org.springframework.core.convert.support.ArrayToCollectionConverter",
      "org.springframework.web.context.support.StandardServletEnvironment",
      "org.springframework.util.ConcurrentReferenceHashMap$EntryIterator",
      "org.springframework.boot.logging.LogFile",
      "org.springframework.boot.bind.RelaxedNames$Variation$3",
      "org.springframework.data.repository.config.DefaultRepositoryBaseClass",
      "org.springframework.boot.bind.RelaxedNames$Variation$2",
      "org.springframework.boot.bind.RelaxedNames$Variation$1",
      "org.springframework.context.annotation.Configuration",
      "org.springframework.core.convert.support.StringToArrayConverter",
      "org.springframework.core.AliasRegistry",
      "org.springframework.core.annotation.AnnotationAttributeExtractor",
      "org.springframework.core.SerializableTypeWrapper$TypeProvider",
      "org.springframework.core.env.MapPropertySource",
      "org.springframework.beans.factory.BeanClassLoaderAware",
      "org.springframework.beans.ExtendedBeanInfo",
      "org.springframework.core.convert.support.ObjectToOptionalConverter$GenericTypeDescriptor",
      "org.springframework.core.SerializableTypeWrapper$MethodInvokeTypeProvider",
      "org.springframework.beans.factory.InitializingBean",
      "org.springframework.core.convert.support.StringToCurrencyConverter",
      "org.springframework.core.convert.converter.GenericConverter$ConvertiblePair",
      "org.springframework.core.io.AbstractFileResolvingResource",
      "org.springframework.core.convert.TypeDescriptor",
      "org.springframework.core.annotation.AnnotationUtils$AliasDescriptor",
      "org.springframework.core.SerializableTypeWrapper$SerializableTypeProxy",
      "org.springframework.core.annotation.Order",
      "org.springframework.beans.BeanMetadataElement",
      "org.springframework.core.NestedIOException",
      "org.springframework.boot.context.config.ConfigFileApplicationListener$Loader",
      "org.apache.commons.logging.impl.SLF4JLogFactory",
      "org.springframework.web.context.ConfigurableWebApplicationContext",
      "org.springframework.beans.factory.NoSuchBeanDefinitionException",
      "org.apache.commons.logging.Log",
      "org.springframework.boot.logging.AbstractLoggingSystem",
      "org.springframework.jndi.JndiPropertySource",
      "org.springframework.core.convert.support.CollectionToStringConverter",
      "org.springframework.util.ReflectionUtils$FieldFilter",
      "org.springframework.aop.support.AopUtils",
      "org.springframework.boot.context.event.ApplicationReadyEvent",
      "org.springframework.context.event.SimpleApplicationEventMulticaster",
      "org.springframework.boot.bind.PropertySourcesPropertyValues",
      "org.springframework.core.env.AbstractPropertyResolver$1",
      "org.springframework.context.ApplicationEventPublisher",
      "org.springframework.util.ConcurrentReferenceHashMap$Segment$1",
      "org.springframework.util.ClassUtils",
      "org.springframework.core.SerializableTypeWrapper$DefaultTypeProvider",
      "org.springframework.core.SerializableTypeWrapper$FieldTypeProvider",
      "org.springframework.validation.DirectFieldBindingResult",
      "org.springframework.core.AttributeAccessor",
      "org.springframework.boot.SpringApplicationRunListeners",
      "org.springframework.core.env.CommandLineArgs",
      "org.springframework.boot.bind.RelaxedNames$Manipulation",
      "org.springframework.boot.autoconfigure.BackgroundPreinitializer$1",
      "org.springframework.boot.ExitCodeGenerator",
      "org.springframework.core.annotation.AnnotationAwareOrderComparator",
      "org.springframework.context.ApplicationContextAware",
      "org.springframework.validation.Errors",
      "org.springframework.core.env.CommandLinePropertySource",
      "org.springframework.core.convert.support.StringToUUIDConverter",
      "org.springframework.util.ConcurrentReferenceHashMap$EntrySet",
      "org.springframework.boot.ApplicationArguments",
      "org.springframework.core.env.ConfigurablePropertyResolver",
      "org.springframework.validation.BindingResult",
      "org.springframework.core.convert.support.CollectionToObjectConverter",
      "com.fasterxml.jackson.core.ObjectCodec",
      "org.springframework.util.ReflectionUtils$MethodCallback",
      "org.springframework.util.ConcurrentReferenceHashMap",
      "org.springframework.beans.BeanMetadataAttributeAccessor",
      "org.springframework.core.io.support.EncodedResource",
      "org.springframework.boot.autoconfigure.BackgroundPreinitializer",
      "org.springframework.core.convert.support.MapToMapConverter",
      "org.springframework.boot.CommandLineRunner",
      "org.springframework.validation.DefaultBindingErrorProcessor",
      "org.springframework.context.MessageSource",
      "org.springframework.boot.autoconfigure.EnableAutoConfiguration",
      "org.springframework.data.repository.query.QueryLookupStrategy$Key",
      "org.springframework.beans.MutablePropertyValues",
      "org.springframework.context.annotation.ComponentScan",
      "org.springframework.context.Lifecycle",
      "org.springframework.context.ApplicationEvent",
      "org.springframework.core.env.SimpleCommandLineArgsParser",
      "org.springframework.validation.AbstractPropertyBindingResult",
      "org.springframework.core.annotation.AnnotationAttributes",
      "org.springframework.boot.cloud.CloudPlatform",
      "org.springframework.util.DefaultPropertiesPersister",
      "org.springframework.core.convert.support.StringToCharacterConverter",
      "org.springframework.boot.json.JsonParserFactory",
      "org.springframework.core.env.SimpleCommandLinePropertySource",
      "org.springframework.boot.cloud.CloudPlatform$1",
      "org.springframework.core.io.support.PropertiesLoaderUtils",
      "org.springframework.core.convert.support.ByteBufferConverter",
      "org.springframework.validation.ObjectError",
      "org.springframework.context.ConfigurableApplicationContext",
      "org.springframework.boot.bind.RelaxedDataBinder",
      "org.springframework.core.MethodParameter",
      "org.springframework.core.convert.converter.GenericConverter",
      "org.springframework.context.event.ApplicationEventMulticaster",
      "org.springframework.boot.cloud.CloudPlatform$2",
      "org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor",
      "org.springframework.aop.AopInvocationException",
      "org.springframework.core.NestedRuntimeException",
      "org.springframework.core.io.ResourceLoader",
      "org.springframework.validation.DataBinder",
      "org.springframework.core.convert.support.ObjectToCollectionConverter",
      "org.springframework.core.env.PropertySources",
      "org.springframework.core.convert.support.PropertiesToStringConverter",
      "org.springframework.core.convert.support.CollectionToArrayConverter",
      "org.springframework.boot.env.PropertySourceLoader",
      "org.springframework.core.convert.Property",
      "org.springframework.core.convert.support.ObjectToObjectConverter",
      "org.springframework.beans.ExtendedBeanInfo$SimpleIndexedPropertyDescriptor",
      "org.springframework.beans.TypeConverterSupport",
      "org.springframework.core.AttributeAccessorSupport",
      "org.springframework.core.env.Environment",
      "org.springframework.core.ResolvableType$VariableResolver",
      "org.springframework.boot.bind.OriginCapablePropertyValue",
      "org.apache.commons.logging.impl.SLF4JLog",
      "org.springframework.core.convert.support.NumberToCharacterConverter",
      "org.springframework.util.PropertyPlaceholderHelper$PlaceholderResolver",
      "org.springframework.core.io.ClassPathResource",
      "org.springframework.beans.factory.Aware",
      "org.springframework.core.env.AbstractPropertyResolver",
      "org.springframework.boot.logging.ClasspathLoggingApplicationListener",
      "org.springframework.core.SerializableTypeWrapper$2",
      "org.springframework.data.repository.core.support.RepositoryFactoryInformation",
      "org.springframework.context.event.AbstractApplicationEventMulticaster$ListenerCacheKey",
      "org.springframework.core.convert.support.ArrayToStringConverter",
      "org.springframework.boot.logging.Slf4JLoggingSystem",
      "org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener",
      "org.springframework.boot.logging.logback.LogbackLoggingSystem$1",
      "org.springframework.core.ResolvableType$DefaultVariableResolver",
      "org.springframework.core.convert.support.ZonedDateTimeToCalendarConverter",
      "org.springframework.boot.SpringApplicationRunListener",
      "org.springframework.core.env.StandardEnvironment",
      "org.springframework.core.SerializableTypeWrapper",
      "org.springframework.core.ResolvableType$TypeVariablesVariableResolver",
      "org.springframework.jndi.JndiLocatorSupport",
      "org.springframework.core.SerializableTypeWrapper$1",
      "org.springframework.core.OrderComparator$OrderSourceProvider",
      "org.springframework.boot.autoconfigure.logging.AutoConfigurationReportLoggingInitializer",
      "org.springframework.boot.bind.PropertiesConfigurationFactory",
      "org.springframework.core.io.support.ResourcePatternResolver",
      "org.springframework.core.env.AbstractEnvironment",
      "org.springframework.context.ApplicationContextInitializer",
      "org.springframework.beans.PropertyEditorRegistry",
      "org.springframework.boot.env.PropertySourcesLoader",
      "org.springframework.boot.env.PropertiesPropertySourceLoader",
      "org.springframework.core.convert.support.GenericConversionService$ConverterFactoryAdapter",
      "org.springframework.core.env.PropertyResolver",
      "org.springframework.boot.bind.PropertyNamePatternsMatcher",
      "org.springframework.core.io.support.SpringFactoriesLoader",
      "org.springframework.context.MessageSourceResolvable",
      "org.springframework.validation.AbstractBindingResult",
      "org.springframework.core.annotation.AnnotationConfigurationException",
      "org.springframework.validation.AbstractErrors",
      "org.springframework.beans.CachedIntrospectionResults",
      "org.springframework.beans.BeanInfoFactory",
      "org.springframework.boot.context.config.RandomValuePropertySource",
      "org.springframework.core.env.CompositePropertySource",
      "org.springframework.core.convert.support.GenericConversionService$NoOpConverter",
      "org.springframework.core.convert.support.ObjectToArrayConverter",
      "org.springframework.core.io.InputStreamSource",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$4",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$5",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$6",
      "org.apache.commons.logging.LogFactory",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$7",
      "org.springframework.util.ConcurrentReferenceHashMap$Task",
      "org.springframework.core.env.PropertySourcesPropertyResolver",
      "org.springframework.beans.PropertyValues",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$1",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$2",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$3",
      "org.springframework.boot.bind.RelaxedNames$Variation",
      "org.springframework.core.env.PropertySource",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$8",
      "org.springframework.context.event.GenericApplicationListenerAdapter",
      "org.springframework.jndi.JndiLocatorDelegate",
      "org.springframework.core.convert.support.CollectionToCollectionConverter",
      "org.springframework.core.env.EnumerablePropertySource",
      "org.springframework.core.env.PropertySourcesPropertyResolver$ClassConversionException",
      "org.springframework.util.ConcurrentReferenceHashMap$ReferenceType",
      "org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent",
      "org.springframework.boot.bind.RelaxedDataBinder$RelaxedBeanPropertyBindingResult",
      "org.springframework.core.convert.support.StringToLocaleConverter",
      "org.springframework.beans.PropertyAccessException",
      "org.springframework.context.event.SmartApplicationListener",
      "org.springframework.beans.factory.config.BeanFactoryPostProcessor",
      "org.springframework.util.ObjectUtils",
      "org.springframework.boot.logging.logback.SpringBootJoranConfigurator",
      "org.springframework.core.env.PropertiesPropertySource",
      "org.springframework.beans.InvalidPropertyException",
      "org.springframework.util.MultiValueMap",
      "org.springframework.core.OrderComparator",
      "org.springframework.util.ResourceUtils",
      "org.springframework.util.ReflectionUtils$MethodFilter",
      "org.apache.commons.logging.LogConfigurationException",
      "org.springframework.util.LinkedMultiValueMap",
      "org.springframework.boot.logging.LoggingApplicationListener",
      "org.springframework.beans.SimpleTypeConverter",
      "org.springframework.core.convert.ConversionFailedException",
      "org.springframework.core.convert.support.StringToPropertiesConverter",
      "org.springframework.context.support.DefaultMessageSourceResolvable",
      "org.springframework.util.ReflectionUtils$4",
      "org.springframework.util.ReflectionUtils$6",
      "org.springframework.boot.context.event.EventPublishingRunListener",
      "org.springframework.util.ReflectionUtils$5",
      "org.springframework.core.annotation.SynthesizedAnnotation",
      "org.springframework.boot.env.EnvironmentPostProcessor",
      "org.springframework.core.convert.support.GenericConversionService$Converters",
      "org.springframework.beans.ExtendedBeanInfoFactory",
      "org.springframework.web.context.WebApplicationContext",
      "org.springframework.context.ApplicationListener",
      "org.springframework.core.convert.ConversionException",
      "se.cag.labs.usermanager.UserManagerApplication",
      "org.springframework.boot.context.event.ApplicationFailedEvent",
      "org.springframework.core.convert.support.ObjectToOptionalConverter",
      "org.springframework.util.PropertiesPersister",
      "org.springframework.boot.bind.RelaxedPropertyResolver",
      "org.springframework.beans.GenericTypeAwarePropertyDescriptor",
      "org.springframework.util.ConcurrentReferenceHashMap$Restructure",
      "org.springframework.core.annotation.OrderUtils",
      "org.springframework.core.SerializableTypeWrapper$MethodParameterTypeProvider",
      "org.springframework.boot.context.config.ConfigFileApplicationListener$ConfigurationPropertySources",
      "org.springframework.core.ResolvableType$WildcardBounds",
      "org.springframework.boot.logging.LoggingSystem",
      "org.springframework.context.event.AbstractApplicationEventMulticaster",
      "org.springframework.core.BridgeMethodResolver",
      "org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer$Check",
      "org.springframework.core.io.DefaultResourceLoader",
      "org.springframework.validation.MessageCodesResolver",
      "org.springframework.validation.BindException",
      "org.springframework.data.repository.query.QueryLookupStrategy",
      "org.springframework.boot.context.config.ConfigFileApplicationListener",
      "org.springframework.boot.context.web.ServerPortInfoApplicationContextInitializer",
      "org.springframework.boot.bind.RelaxedNames",
      "org.springframework.boot.builder.ParentContextApplicationContextInitializer$ParentContextAvailableEvent",
      "org.springframework.context.MessageSourceAware",
      "org.springframework.beans.PropertyValue",
      "org.springframework.boot.DefaultApplicationArguments",
      "com.fasterxml.jackson.databind.ObjectMapper",
      "org.springframework.core.env.SystemEnvironmentPropertySource",
      "org.springframework.context.ApplicationContext",
      "org.springframework.core.io.AbstractResource",
      "org.springframework.core.convert.support.StreamConverter",
      "org.springframework.boot.context.event.SpringApplicationEvent",
      "org.springframework.boot.logging.DeferredLog$Line",
      "org.springframework.core.convert.converter.ConditionalConverter",
      "org.springframework.beans.ConfigurablePropertyAccessor",
      "org.springframework.util.StringUtils"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(UserManagerApplication_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "org.springframework.data.repository.query.QueryLookupStrategy$Key",
      "org.apache.commons.logging.impl.SLF4JLogFactory",
      "org.apache.commons.logging.LogFactory",
      "org.springframework.boot.SpringBootBanner",
      "org.apache.commons.logging.impl.SLF4JLocationAwareLog",
      "org.springframework.boot.SpringApplication",
      "org.springframework.boot.Banner$Mode",
      "org.springframework.util.ClassUtils",
      "org.springframework.core.io.support.SpringFactoriesLoader",
      "org.springframework.util.StringUtils",
      "org.springframework.core.io.support.PropertiesLoaderUtils",
      "org.springframework.util.ResourceUtils",
      "org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer",
      "org.springframework.boot.context.ContextIdApplicationContextInitializer",
      "org.springframework.boot.context.config.DelegatingApplicationContextInitializer",
      "org.springframework.core.OrderComparator",
      "org.springframework.core.annotation.AnnotationAwareOrderComparator",
      "org.springframework.core.annotation.OrderUtils",
      "org.springframework.util.ConcurrentReferenceHashMap$ReferenceType",
      "org.springframework.util.ConcurrentReferenceHashMap",
      "org.springframework.util.ConcurrentReferenceHashMap$Segment",
      "org.springframework.core.annotation.AnnotationUtils",
      "org.springframework.util.ConcurrentReferenceHashMap$Restructure",
      "org.springframework.boot.context.FileEncodingApplicationListener",
      "org.springframework.boot.context.config.ConfigFileApplicationListener",
      "org.springframework.core.convert.support.GenericConversionService",
      "org.springframework.core.convert.support.DefaultConversionService",
      "org.springframework.core.ResolvableType",
      "org.springframework.util.ObjectUtils",
      "org.springframework.core.SerializableTypeWrapper",
      "org.springframework.core.SerializableTypeWrapper$DefaultTypeProvider",
      "org.springframework.core.SerializableTypeWrapper$2",
      "org.springframework.core.SerializableTypeWrapper$TypeProxyInvocationHandler",
      "org.springframework.util.ConcurrentReferenceHashMap$TaskOption",
      "org.springframework.core.ResolvableType$DefaultVariableResolver",
      "org.springframework.core.SerializableTypeWrapper$MethodInvokeTypeProvider",
      "org.springframework.util.ReflectionUtils",
      "org.springframework.core.convert.support.StringToBooleanConverter",
      "org.springframework.core.convert.support.CollectionToStringConverter",
      "org.springframework.core.convert.TypeDescriptor",
      "org.springframework.core.convert.support.StreamConverter",
      "org.springframework.core.convert.support.ByteBufferConverter",
      "org.springframework.core.convert.support.ObjectToObjectConverter",
      "org.springframework.boot.context.config.DelegatingApplicationListener",
      "org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener",
      "org.springframework.boot.logging.ClasspathLoggingApplicationListener",
      "org.springframework.util.LinkedMultiValueMap",
      "org.springframework.boot.logging.LogLevel",
      "org.springframework.boot.logging.LoggingApplicationListener",
      "org.springframework.context.ApplicationEvent",
      "org.springframework.boot.context.event.SpringApplicationEvent",
      "org.springframework.boot.context.event.ApplicationStartedEvent",
      "org.springframework.core.SerializableTypeWrapper$1",
      "org.springframework.boot.logging.LoggingSystem",
      "org.springframework.boot.logging.Slf4JLoggingSystem",
      "org.springframework.boot.logging.logback.LogbackLoggingSystem",
      "org.springframework.core.env.CommandLinePropertySource",
      "org.springframework.core.env.AbstractEnvironment",
      "org.springframework.core.env.StandardEnvironment",
      "org.springframework.web.context.support.StandardServletEnvironment",
      "org.springframework.jndi.JndiLocatorSupport",
      "org.springframework.core.SpringProperties",
      "org.springframework.core.env.PropertySource$ComparisonPropertySource",
      "org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent",
      "org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor",
      "org.springframework.boot.env.SpringApplicationJsonEnvironmentPostProcessor",
      "org.springframework.util.PropertyPlaceholderHelper",
      "org.springframework.boot.cloud.CloudPlatform$1",
      "org.springframework.boot.cloud.CloudPlatform$2",
      "org.springframework.boot.cloud.CloudPlatform",
      "org.springframework.boot.context.config.RandomValuePropertySource",
      "org.springframework.boot.env.PropertySourcesLoader",
      "org.springframework.boot.bind.RelaxedNames",
      "org.springframework.boot.bind.RelaxedNames$Variation$1",
      "org.springframework.boot.bind.RelaxedNames$Variation$2",
      "org.springframework.boot.bind.RelaxedNames$Variation$3",
      "org.springframework.boot.bind.RelaxedNames$Variation",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$1",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$2",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$3",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$4",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$5",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$6",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$7",
      "org.springframework.boot.bind.RelaxedNames$Manipulation$8",
      "org.springframework.boot.bind.RelaxedNames$Manipulation",
      "org.springframework.boot.bind.PropertiesConfigurationFactory",
      "org.springframework.validation.DataBinder",
      "org.springframework.boot.bind.RelaxedDataBinder",
      "org.springframework.validation.DefaultBindingErrorProcessor",
      "org.springframework.beans.BeanUtils",
      "org.springframework.beans.CachedIntrospectionResults",
      "org.springframework.beans.ExtendedBeanInfo",
      "org.springframework.core.GenericTypeResolver",
      "org.springframework.core.SerializableTypeWrapper$MethodParameterTypeProvider",
      "org.springframework.boot.bind.PropertySourcesPropertyValues",
      "org.springframework.boot.context.event.ApplicationFailedEvent"
    );
  }
}
