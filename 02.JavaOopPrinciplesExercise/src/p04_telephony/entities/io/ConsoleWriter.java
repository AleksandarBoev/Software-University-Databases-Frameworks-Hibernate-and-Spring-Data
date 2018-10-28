package p04_telephony.entities.io;

import p04_telephony.contracts.Writer;

public class ConsoleWriter implements Writer {
    @Override
    public void writeLine(String text) {
        System.out.println(text);
    }

    @Override
    public void write(String text) {
        System.out.print(text);
    }
}
