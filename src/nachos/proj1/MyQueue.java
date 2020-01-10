package nachos.proj1;

import nachos.threads.KThread;
import nachos.threads.ThreadQueue;

import java.util.ArrayList;

public class MyQueue extends ThreadQueue {
    ArrayList<KThread> threadList = new ArrayList<>();
    @Override
    public void waitForAccess(KThread thread) {
        threadList.add(thread);
    }

    @Override
    public KThread nextThread() {
        if (threadList.isEmpty()){
            return null;
        }else{
            return threadList.remove(0);
        }
    }

    @Override
    public void acquire(KThread thread) {

    }

    @Override
    public void print() {

    }
}
