package p04_say_hello;

import p04_say_hello.contracts.Person;
import p04_say_hello.entities.people.Bulgarian;
import p04_say_hello.entities.people.Chinese;
import p04_say_hello.entities.people.European;

public class Main {
    public static void main(String[] args) {
        Person bulgarian = new Bulgarian("Pesho");
        Person european = new European("Joan");
        Person chinese = new Chinese("Jackie");

        System.out.println(bulgarian.sayHello());
        System.out.println(european.sayHello());
        System.out.println(chinese.sayHello());
    }
}
