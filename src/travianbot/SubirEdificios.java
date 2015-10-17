package travianbot;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;

/**
 *
 * @author Fran1488
 */
public class SubirEdificios {

    final private WebClient webClient;
    private HtmlPage page;

    public SubirEdificios(WebClient webClient) {
        this.webClient = webClient;
    }

    public void start(int id) throws IOException {
        page = webClient.getPage("http://ts2.travian.net/build.php?id=" + id);
        //obtener los elementos
        HtmlDivision contract = (HtmlDivision) page.getElementById("contract");
        //si contract contiene ampliado completamente, no podemos subir la mina
        if (contract.asText().contains("ampliado completamente")) {
            System.out.println(Utils.getHour() + " - Mi rey, el edificio ya está a su máximo nivel.");
            //si necesitamos más espacio de almacenamiento no podemos subir la mina
            if (contract.asText().contains("Antes amplía almacén")) {
                System.out.println(Utils.getHour() + " - Mi rey, antes es necesario ampliar el almacén.");
                upStorage();
            }
        } else {
            HtmlButton up = (HtmlButton) contract.getElementsByTagName("button").get(0);
            up.click();
        }
    }

    /**
     * Método que te amplia el almacén
     */
    private void upStorage() throws IOException {
        try {
            page = webClient.getPage("http://ts2.travian.net/build.php?id=21");
            //obtener los elementos
            HtmlDivision contract = (HtmlDivision) page.getElementById("contract");
            HtmlButton up = (HtmlButton) contract.getElementsByTagName("button").get(0);
            up.click();
        } catch (ElementNotFoundException ex) {          
        }

    }
}
