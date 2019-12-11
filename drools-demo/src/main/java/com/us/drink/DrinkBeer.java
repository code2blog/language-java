package com.us.drink;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DrinkBeer {
    private static KieSession getSession() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        return kc.newKieSession("DrinkBeerKS");
    }

    private static void drink() {
        KieSession ks = getSession();
        Customer c1 = new Customer("Anil", 10);
        ks.insert(c1);
        int count = ks.fireAllRules();
        System.out.println("Always executed " + count + " Rules");
        System.out.println(c1.toString());
    }

    public static void main(String[] args) {
        drink();
    }
}
