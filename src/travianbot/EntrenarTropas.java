package travianbot;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;

/**
 *
 * @author Fran1488
 */
public class EntrenarTropas {

    final private WebClient webClient;
    private HtmlPage page;

    public EntrenarTropas(WebClient webClient) throws IOException {
        this.webClient = webClient;
    }

    public void EntrenarFalanges() throws IOException{
        try {
            page = this.webClient.getPage("http://ts2.travian.net/build.php?id=32");
            HtmlInput rayos = (HtmlInput) page.getElementByName("t1");
            rayos.setValueAttribute("20");
            HtmlButton entrenar = (HtmlButton) page.getElementById("s1");
            entrenar.click();
            System.out.println(Utils.getHour() + " - Entrenando falanges");
        } catch (ElementNotFoundException ex) {
            System.out.println(Utils.getHour() + " - No hay cuartel en esta aldea.");
        }
    }
    
    public void EntrenarRayos() throws IOException {
        try {
            page = this.webClient.getPage("http://ts2.travian.net/build.php?id=27");
            HtmlInput rayos = (HtmlInput) page.getElementByName("t4");
            rayos.setValueAttribute("20");
            HtmlButton entrenar = (HtmlButton) page.getElementById("s1");
            entrenar.click();
            System.out.println(Utils.getHour() + " - Entrenando rayos");
        } catch (ElementNotFoundException ex) {
            System.out.println(Utils.getHour() + " - No hay establo en esta aldea.");
        }
    }

    public void EntrenarDruidas() throws IOException {
        try {
            page = this.webClient.getPage("http://ts2.travian.net/build.php?id=27");
            HtmlInput druidas = (HtmlInput) page.getElementByName("t5");
            druidas.setValueAttribute("20");
            HtmlButton entrenar = (HtmlButton) page.getElementById("s1");
            entrenar.click();
            System.out.println(Utils.getHour() + " - Entrenando druidas");
        } catch (ElementNotFoundException ex) {
            System.out.println(Utils.getHour() + " - No hay establo en esta aldea.");
        }
    }

    public void EntrenarEduos() throws IOException {
        try {
            page = this.webClient.getPage("http://ts2.travian.net/build.php?id=27");
            HtmlInput eduos = (HtmlInput) page.getElementByName("t6");
            eduos.setValueAttribute("20");
            HtmlButton entrenar = (HtmlButton) page.getElementById("s1");
            entrenar.click();
            System.out.println(Utils.getHour() + " - Entrenando eduos");
        } catch (ElementNotFoundException ex) {
            System.out.println(Utils.getHour() + " - No hay establo en esta aldea.");
        }
    }
}
