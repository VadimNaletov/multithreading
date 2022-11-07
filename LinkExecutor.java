import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkExecutor extends RecursiveTask<String> {

    private String url;
    private String startUrl;
    private List<String> links = new ArrayList<>();
    public LinkExecutor(String url) {
        this.url = url;
    }
    public LinkExecutor(String url, String startUrl) {
        this.url = url;
        this.startUrl = startUrl;
    }
    @Override
    protected String compute() {
        StringBuffer sb = new StringBuffer(url + "\n\t");
        List<LinkExecutor> tasks = new ArrayList<>();
        getChildren(tasks);
        for (LinkExecutor task : tasks) {
            sb.append(task.join()).append("\t");
        }
        return sb.toString();
    }

    private void getChildren(List<LinkExecutor> tasks) {
        try {
            Thread.sleep(100);
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("a");
            for (Element el : elements) {
                String attr = el.attr("abs:href");
                if (!attr.isEmpty() && attr.startsWith(startUrl) && !links.contains(attr) && !attr
                        .contains("#")) {
                    LinkExecutor linkExecutor = new LinkExecutor(attr);
                    linkExecutor.fork();
                    tasks.add(linkExecutor);
                    links.add(attr);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}