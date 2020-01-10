package nachos.proj1;

import nachos.machine.*;
import nachos.threads.KThread;
import nachos.threads.Semaphore;

import java.util.ArrayList;

public class Main {

    MyConsole mc = new MyConsole();
    NetworkLink nl = Machine.networkLink();
    Semaphore semaphore = new Semaphore(0);
    ArrayList<File> arrFile = new ArrayList<>();
    Timer timer = Machine.timer();

    public Main() {

        Runnable receive = new Runnable() {
            @Override
            public void run() {
                Packet packet = nl.receive();
                String content = new String(packet.contents);
                String[] fileInfo = content.split(";",0);

                File file = new File(fileInfo[0], Integer.parseInt(fileInfo[1]), fileInfo[2]);

                arrFile.add(file);

                System.out.println("New file Received : "+fileInfo[0]);
            }
        };

        Runnable send = new Runnable() {
            @Override
            public void run() {
                semaphore.V();
            }
        };

        nl.setInterruptHandlers(receive, send);

        int choose;
        do {
            menu();
            choose = mc.scanInt();
            switch (choose) {
                case 1:
                    sendFile();
                    break;
                case 2:
                    viewFile();
                    break;
                case 3:
                    mc.printLn("time of use " + timer.getTime());
                    break;
            }
        } while (choose != 3);
    }

    private void sendFile() {
        String name, type, msg;
        int size, destination;
        do {
            mc.print("Enter File Name [must contain '.'] : ");
            name = mc.scanLine();
        } while (name.isEmpty() || !name.contains("."));
        do {
            mc.print("Enter File Size [1 - 500 MB] : ");
            size = mc.scanInt();
        } while (size < 1 || size > 500);
        do {
            mc.print("Enter File Type : ");
            type = mc.scanLine();
        } while (type.isEmpty());

        mc.print("Send to Computer : ");
        destination = mc.scanInt();

        msg = name + ";" + size + ";" + type;

        byte[] message = msg.getBytes();
        try {
            Packet send = new Packet(destination, nl.getLinkAddress(), message);
            nl.send(send);
        } catch (MalformedPacketException e) {
            e.printStackTrace();
        }

        mc.print("File Sent!");

    }

    private void viewFile() {
        if (arrFile.size() == 0) {
            mc.print("No File");
        } else {
            mc.printLn("Files");
            mc.printLn("====================================");
            for (int i = 0; i < arrFile.size(); i++) {
                new KThread(arrFile.get(0)).fork();
            }
        }
    }

    private void menu() {
        mc.printLn("Computer No : " + nl.getLinkAddress());
        mc.printLn("File Manager");
        mc.printLn("1. Send File");
        mc.printLn("2. View File");
        mc.printLn("3. Exit");
        mc.print("Choose : ");

    }

}
