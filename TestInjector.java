package com.dremio.dac.server;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class TestInjector {
  public static void main(String []args) {
    Injector injector = Guice.createInjector(new TestGuiceInjectModule());
    System.out.println("test injector");
//    injector.getInstance(Interface.class);
//    injector.getProvider(Interface.class);
    InjectionTesting injectionTesting = injector.getInstance(InjectionTesting.class);
    System.out.println("test injectionTesting");
  }
}
interface InjectionTesting {

}
class TestInjection implements InjectionTesting {

  Provider<ClassAPI> classAPIProvider;
  Interface anInterface;
  Provider<Interface> anInterfaceProvider;

  @Inject
  public TestInjection (Provider<ClassAPI> classAPIProvider, Interface anInterface, Provider<Interface> anInterfaceProvider) {
    this.classAPIProvider = classAPIProvider;
    this.anInterface = anInterface;
    this.anInterfaceProvider = anInterfaceProvider;
  }

  public Provider<ClassAPI> getClassAPIProvider() {
    return classAPIProvider;
  }

  public Provider<Interface> getInterfaceProvider() {
    return anInterfaceProvider;
  }

  public Interface getInterface() {
    return anInterface;
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
  ClassAPI getClassAPIProvider() {
    return new ClassAPI();
  }
}

class ClassAPI {
  public String api() {
    return "test";
  }
}
