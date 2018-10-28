package p04_telephony.entities.engines;

import p04_telephony.contracts.*;
import p04_telephony.entities.classes.SmartphoneImpl;

public class TelephonyEngine implements Engine {
    private Reader reader;
    private Writer writer;

    public TelephonyEngine(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        String[] phoneNumbers = this.reader.readLine().split(" ");
        String[] urls = this.reader.readLine().split(" ");
        this.reader.close();

        Smartphone smartphone = new SmartphoneImpl();
        this.writer.writeLine(smartphone.call(phoneNumbers));
        this.writer.writeLine(smartphone.browse(urls));
    }
}
