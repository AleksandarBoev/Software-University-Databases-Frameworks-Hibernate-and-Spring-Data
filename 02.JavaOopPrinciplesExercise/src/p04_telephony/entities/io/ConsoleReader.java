package p04_telephony.entities.io;

import p04_telephony.contracts.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements Reader {
    private BufferedReader reader;

    public ConsoleReader() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public String readLine() {
        try {
            return this.reader.readLine();
        } catch (IOException ioe) {
            return ioe.getMessage();
        }
    }

    @Override
    public void close() {
        try {
            this.reader.close();
        } catch (IOException ioe) {

        }
    }
}
