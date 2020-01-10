package nachos.proj1;

public class File implements Runnable{
    private String name;
    private int size;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public File(String name, int size, String type) {
        this.name = name;
        this.size = size;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            //untuk kasih delay
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("----------------------------");
        System.out.println("File name : " + name);
        System.out.println("File size : " + size );
        System.out.println("File type : "+ type);
        System.out.println("----------------------------");
    }
}
