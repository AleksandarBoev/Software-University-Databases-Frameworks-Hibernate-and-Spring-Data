package p02_multiple_implementation;

import p02_multiple_implementation.contracts.Birthable;
import p02_multiple_implementation.contracts.Identifiable;
import p02_multiple_implementation.entities.Citizen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Class[] citizenInterfaces = Citizen.class.getInterfaces();
        if (Arrays.asList(citizenInterfaces).contains(Birthable.class)
                && Arrays.asList(citizenInterfaces).contains(Identifiable.class)) {
            Method[] methods = Birthable.class.getDeclaredMethods();
            System.out.println(methods.length);
            System.out.println(methods[0].getReturnType().getSimpleName());

            methods = Identifiable.class.getDeclaredMethods();
            System.out.println(methods.length);
            System.out.println(methods[0].getReturnType().getSimpleName());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String name = reader.readLine();
            int age = Integer.parseInt(reader.readLine());
            String id = reader.readLine();
            String birthdate = reader.readLine();
            reader.close();

            Identifiable identifiable = new Citizen(name,age,id,birthdate);
            Birthable birthable = new Citizen(name,age,id,birthdate);
        }

    }
}
