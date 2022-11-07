import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private final static String filePath = "siteMap.txt";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите адрес сайта:");
        String url = sc.nextLine();
        System.out.println("Начинаю составлять карту сайта");
        LinkExecutor linkExecutor = new LinkExecutor(url, url);
        String siteMap = new ForkJoinPool().invoke(linkExecutor);
        writeFiles(siteMap);
    }

    private static void writeFiles(String map) {
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Карта сайта готова!");
    }
}