package nachos.proj1;

import nachos.machine.Machine;
import nachos.machine.SerialConsole;
import nachos.threads.Semaphore;

public class MyConsole {

    SerialConsole console = Machine.console();
    Semaphore semaphore = new Semaphore(0);

    public MyConsole() {
        Runnable resume = new Runnable() {
            @Override
            public void run() {
                semaphore.V();
            }
        };
        console.setInterruptHandlers(resume, resume);
    }

    public String scanLine() {
        String result = "";
        char bytes;
        do {
            semaphore.P();

            bytes = (char) console.readByte();
            if (bytes != '\n') {
                result += bytes;
            }

        } while (bytes != '\n');

        return result;
    }

    public Integer scanInt(){
        int result = Integer.parseInt(scanLine());
        return result;
    }

    public void print(String str){
        for (int i = 0; i < str.length(); i++) {
            console.writeByte(str.charAt(i));
            semaphore.P();
        }
    }

    public void printLn(String str){
        print(str + "\n");
    }
}
