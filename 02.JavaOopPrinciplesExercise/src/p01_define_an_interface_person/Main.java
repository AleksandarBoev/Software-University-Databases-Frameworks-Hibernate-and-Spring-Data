package p01_define_an_interface_person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Class[] citizenInterfaces = Citizen.class.getInterfaces();

        if(Arrays.asList(citizenInterfaces).contains(Person.class)){
            Method[] fields = Person.class.getDeclaredMethods();
            System.out.println(fields.length);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String name = reader.readLine();
            int age = Integer.parseInt(reader.readLine());
            reader.close();

            Person person = new Citizen(name,age);

            System.out.println(person.getName());
            System.out.println(person.getAge());
        }
    }
}
