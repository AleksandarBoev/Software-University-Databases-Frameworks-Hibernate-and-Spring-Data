package p04_telephony;

import p04_telephony.contracts.Engine;
import p04_telephony.contracts.Reader;
import p04_telephony.contracts.Writer;
import p04_telephony.entities.engines.TelephonyEngine;
import p04_telephony.entities.io.ConsoleReader;
import p04_telephony.entities.io.ConsoleWriter;

import java.io.IOException;

public class Main { //practising good code
    public static void main(String[] args) throws IOException {
        Reader consoleReader = new ConsoleReader();
        Writer consoleWriter = new ConsoleWriter();

        Engine telephonyEngine = new TelephonyEngine(consoleReader, consoleWriter);
        telephonyEngine.run();
    }
}
