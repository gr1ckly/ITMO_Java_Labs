package server;

import commonModule.output.Outputable;

public class OutputUnit implements Outputable<String> {

    @Override
    public void writeln(String data) {
        System.out.println(data);
    }

    @Override
    public void write(String data) {
        System.out.print(data);
    }
}
