package com.dremio.daas.application.optimizationservice.server;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;

public class TestInjector {
  public static void main(String []args) {
    Injector injector = Guice.createInjector(new TestGuiceInjectModule());
    System.out.println("test injector");
//    injector.getInstance(Interface.class);
//    injector.getProvider(Interface.class);
    InjectionTesting injectionTesting = injector.getInstance(InjectionTesting.class);
    ClassWithNoBindingInAbstractModule classWithNoBindingInAbstractModule = injector.getInstance(ClassWithNoBindingInAbstractModule.class);
    System.out.println("test injectionTesting");
  }
}
interface InjectionTesting {

}
class TestInjection implements InjectionTesting {

  Provider<ClassWithProvidesAnnotationInAbstractModule> classWithProvidesAnnotationInAbstractModuleProvider;
  Interface anInterface;
  Provider<Interface> anInterfaceProvider;
  ClassWithNoBindingInAbstractModule classWithNoBindingInAbstractModule;
  Provider<ClassWithNoBindingInAbstractModule> classWithNoBindingInAbstractModuleProvider;

  @Inject
  public TestInjection (Provider<ClassWithProvidesAnnotationInAbstractModule> classWithProvidesAnnotationInAbstractModuleProvider,
                        Interface anInterface,
                        Provider<Interface> anInterfaceProvider,
                        ClassWithNoBindingInAbstractModule classWithNoBindingInAbstractModule,
                        Provider<ClassWithNoBindingInAbstractModule> classWithNoBindingInAbstractModuleProvider) {
    this.classWithProvidesAnnotationInAbstractModuleProvider = classWithProvidesAnnotationInAbstractModuleProvider;
    this.anInterface = anInterface;
    this.anInterfaceProvider = anInterfaceProvider;
    this.classWithNoBindingInAbstractModule = classWithNoBindingInAbstractModule;
    this.classWithNoBindingInAbstractModuleProvider = classWithNoBindingInAbstractModuleProvider;
  }

  public Provider<ClassWithProvidesAnnotationInAbstractModule> getClassWithProvidesAnnotationInAbstractModule() {
    return classWithProvidesAnnotationInAbstractModuleProvider;
  }

  public Provider<Interface> getInterfaceProvider() {
    return anInterfaceProvider;
  }

  public Interface getInterface() {
    return anInterface;
  }

  public ClassWithNoBindingInAbstractModule getClassWithNoBindingInAbstractModule() {
    return classWithNoBindingInAbstractModule;
  }
}

class IDInterface implements Interface{
  int id;
  public int getId() {
    return 0;
  }
}
interface Interface {
  public int getId();
}
class TestGuiceInjectModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(Interface.class).to(IDInterface.class);
//    bind(Interface.class).toInstance(new IDInterface());
    bind(InjectionTesting.class).to(TestInjection.class);
  }
  @Provides
//  @Singleton
  ClassWithProvidesAnnotationInAbstractModule getClassAPIProvider() {
    return new ClassWithProvidesAnnotationInAbstractModule();
  }
}

class ClassWithProvidesAnnotationInAbstractModule {
  public String testProvides() {
    return "testProvidesBinding";
  }
}


/**
 * Even this class doesn't have any bindings via Abstract Module but injection can be done of this class as other classes
 */
class ClassWithNoBindingInAbstractModule {
  Interface anInterface;
  Provider<Interface> anInterfaceProvider;
  @Inject
  public ClassWithNoBindingInAbstractModule(Interface anInterface,
                                            Provider<Interface> anInterfaceProvider) {
    this.anInterface = anInterface;
    this.anInterfaceProvider = anInterfaceProvider;
  }
  public Interface getAnInterface() {
    return anInterface;
  }
  public String testNoBinding() {
    return "testNoBinding";
  }
}
