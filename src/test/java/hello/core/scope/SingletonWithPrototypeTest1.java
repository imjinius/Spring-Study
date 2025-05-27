package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind (){
        ApplicationContext ac = new AnnotationConfigApplicationContext(Prototypebean.class);
        Prototypebean prototypebean1 = ac.getBean(Prototypebean.class);
        prototypebean1.addCount();
        assertThat(prototypebean1.getCount()).isEqualTo(1);

        Prototypebean prototypebean2 = ac.getBean(Prototypebean.class);
        prototypebean2.addCount();
        assertThat(prototypebean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, Prototypebean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {

        @Autowired
        private Provider<Prototypebean> prototypebeanProvider;

        public int logic() {
            Prototypebean prototypebean = prototypebeanProvider.get();
            prototypebean.addCount();
            int count = prototypebean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class Prototypebean {
        private int count = 0;

        public void addCount () {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("Prototypebean.init = " + this);
        }

        @PreDestroy
        public void destory (){
            System.out.println("Prototypebean.destory");
        }
    }
}
