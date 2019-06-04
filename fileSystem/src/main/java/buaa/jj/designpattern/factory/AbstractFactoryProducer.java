package buaa.jj.designpattern.factory;

import java.util.HashMap;
import java.util.Map;

public class AbstractFactoryProducer {

    private static AbstractFactoryProducer abstractFactoryProducer;
    private Map<String, AbstractFactory> factoryMap;

    private AbstractFactoryProducer() {
        factoryMap = new HashMap<String, AbstractFactory>();
    }

    public AbstractFactory getAbstractFactory(String factoryName) {
        if (factoryMap.get(factoryName) == null) {
            switch (factoryName) {
                case "fileSystem":
                    FileSystemFactory factory = new FileSystemFactory();
                    factoryMap.put(factoryName, factory);
                    break;
                default:
                    throw new RuntimeException();
            }
        }
        return factoryMap.get(factoryName);
    }

    public static AbstractFactoryProducer getAbstractFactoryProducer() {
        if (abstractFactoryProducer == null)
            abstractFactoryProducer = new AbstractFactoryProducer();
        return abstractFactoryProducer;
    }
}
