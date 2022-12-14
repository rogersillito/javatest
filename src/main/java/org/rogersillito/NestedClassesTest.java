package org.rogersillito;

import lombok.*;

import java.util.function.Supplier;

public class NestedClassesTest {
    @Setter int multiplier = 1;
    public void doTestHarness() {
//        var tesHarness = new TestHarness();

        var threeSupplier = new Supplier<Integer>() {
            static final int value = 3;
            @Override public Integer get() {
                return  value * multiplier;
            }
        };
        Du.mp("threeSupplier", threeSupplier.get());

        var tb1 = new TestBase("Jon");
        tb1.sayHi();
        var tb2 = new TestBase("Tim") {
            @Override
            public void sayHi() {
                say("Bonjour");
            }
        };
        tb2.sayHi();

        NewOuter outer = new NewOuter();
        NewOuter.InnerClass inner = outer.new InnerClass();
        inner.run();
    }
}

class TestHarness {
}

@RequiredArgsConstructor
class TestBase {
    final String name;
    InnerStatic innerStatic1 = new InnerStatic();

    public void say(String what) {
        innerStatic1.setValue(1);
        System.out.println(String.format("%s: %s", name, what));
    }

    public void sayHi() {
        say("Hi" + innerStatic1.getSomeValue());
    }

    static class InnerStatic {
        @Getter @Setter int value;

        public String getSomeValue() {
            class SomeLocal {
                @Getter @Setter int number;
                String getValue() {
                    return "I'm local " + getNumber();
                }
            }

            var local = new SomeLocal();
            local.setNumber(2);
            return Integer.valueOf(value).toString() + local.getValue();
        }

    }
}

class NewOuter {

    @Setter public int a = 1;
    static int b = 2;

    public class InnerClass {
        int a = 3;
        static final int b = 4;

        public void run() {
            // inner class shadowing
            System.out.println("a = " + this.a);
            System.out.println("b = " + b);
            System.out.println("NewOuterTest.this.a = " + NewOuter.this.a);
            System.out.println("NewOuterTest.b = " + NewOuter.b);
            System.out.println("NewOuterTest.this.b = " + NewOuter.this.b);
        }
    }
}
