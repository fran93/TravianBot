package travianbot;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import java.io.IOException;

/**
 *
 * @author Fran1488
 */
public class MovimientosTropas {

    final private WebClient webClient;
    private HtmlPage page;
    private int falange, espadas, rayos;

    public MovimientosTropas(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Devuelve si las tropas están listas para atacar.
     *
     * @return
     */
    public boolean getTropasDisponibles() throws IOException {
        try {
            page = webClient.getPage("http://ts2.travian.net/build.php?tt=2&id=39");
            //obtener las falages disponibles
            HtmlTableDataCell t1 = (HtmlTableDataCell) page.getElementByName("t1").getParentNode();
            falange = Integer.parseInt(t1.asText().replaceAll("[\\D]", ""));
            //obtener los luchadores de espada disponibles
            HtmlTableDataCell t2 = (HtmlTableDataCell) page.getElementByName("t2").getParentNode();
            espadas = Integer.parseInt(t2.asText().replaceAll("[\\D]", ""));
            //obtener los rayos disponibles
            HtmlTableDataCell t4 = (HtmlTableDataCell) page.getElementByName("t4").getParentNode();
            rayos = Integer.parseInt(t4.asText().replaceAll("[\\D]", ""));
            //si tengo más de 50 rayos puedo atacar
            return rayos >= 50;
        } catch (ElementNotFoundException ex) {
            System.out.println(Utils.getHour()+ " - No hay plaza de reuniones en esta aldea.");
        }
        return false;
    }

    public int getEspadas() {
        return espadas;
    }

    public void setEspadas(int espadas) {
        this.espadas = espadas;
    }

    public int getFalange() {
        return falange;
    }

    public void setFalange(int falange) {
        this.falange = falange;
    }

    public int getRayos() {
        return rayos;
    }

    public void setRayos(int rayos) {
        this.rayos = rayos;
    }

}
