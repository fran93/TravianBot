package travianbot;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlArea;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlMap;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import java.io.IOException;

/**
 *
 * @author Fran1488
 */
public class RecursosEdificios {

    final private WebClient webClient;
    private HtmlPage page;
    private final int[] lvlminas = new int[18];
    private int madera, barro, cereal, hierro, almacen, granero;
    private final String[] typeminas = new String[18];
    private boolean construyendo = false;

    public RecursosEdificios(WebClient webClient) throws IOException {
        this.webClient = webClient;
        page = this.webClient.getPage("http://ts2.travian.net/dorf1.php");
        //obtengo si se está construyendo algún edificio
        construyendo = page.asText().contains("orden de construcción:");

        //si estoy construyendo, no hago nada
        if (!construyendo) {
            HtmlMap map = (HtmlMap) page.getElementById("rx");
            DomNodeList<HtmlElement> areas = map.getElementsByTagName("area");
            for (int i = 0; i < areas.getLength() - 1; i++) {
                //obtengo el area
                HtmlArea buff = (HtmlArea) areas.get(i);
                //obtengo el número a partir del atributo
                lvlminas[i] = Integer.parseInt(buff.getAttribute("alt").replaceAll("[\\D]", ""));
                typeminas[i] = buff.getAltAttribute();
            }
        }
    }

    /**
     * Método que obtiene los recursos
     */
    public void getResources() throws IOException {
        page = this.webClient.getPage("http://ts2.travian.net/dorf1.php");
        //almacén
        HtmlSpan alm = (HtmlSpan) page.getElementById("stockBarWarehouse");
        almacen = Integer.parseInt(alm.asText().replaceAll("\\D+", ""));
        //granero
        alm = (HtmlSpan) page.getElementById("stockBarGranary");
        granero = Integer.parseInt(alm.asText().replaceAll("\\D+", ""));
        //madera
        alm = (HtmlSpan) page.getElementById("l1");
        madera = Integer.parseInt(alm.asText().replaceAll("\\D+", ""));
        //barro
        alm = (HtmlSpan) page.getElementById("l2");
        barro = Integer.parseInt(alm.asText().replaceAll("\\D+", ""));
        //hierro
        alm = (HtmlSpan) page.getElementById("l3");
        hierro = Integer.parseInt(alm.asText().replaceAll("\\D+", ""));
        //cereal
        alm = (HtmlSpan) page.getElementById("l4");
        cereal = Integer.parseInt(alm.asText().replaceAll("\\D+", ""));
    }

    public void venderCereal() throws IOException {
        try {
            page = webClient.getPage("http://ts2.travian.net/build.php?t=2&id=30");
            //ofrecer 750 cereal
            HtmlInput ofrecer = (HtmlInput) page.getElementByName("m1");
            ofrecer.setValueAttribute("750");
            //seleccionar cereal
            HtmlSelect select = (HtmlSelect) page.getElementByName("rid1");
            HtmlOption option = select.getOptionByValue("4");
            select.setSelectedAttribute(option, true);
            //buscar 750 recursos
            HtmlInput buscar = (HtmlInput) page.getElementByName("m2");
            buscar.setValueAttribute("750");
            //seleccionar el recurso
            select = (HtmlSelect) page.getElementByName("rid2");
            option = select.getOptionByValue(Utils.getLowerResource(madera, barro, hierro));
            select.setSelectedAttribute(option, true);
            //enviar
            HtmlButton vender = (HtmlButton) page.getFirstByXPath("//button[@type='submit' and @value='OK']");
            vender.click();
            System.out.println(Utils.getHour() + " - Vendiendo cereal.");
        } catch (ElementNotFoundException ex) {
            System.out.println(Utils.getHour() + " - No hay mercado en esta aldea.");
        }
    }

    /**
     * Devuelve el Nivel de las minas
     *
     * @return
     */
    public int[] getLvlminas() {
        return lvlminas;
    }

    /**
     * Devuelve el tipo de minas
     *
     * @return
     */
    public String[] getTypeminas() {
        return typeminas;
    }

    public boolean isConstruyendo() {
        return construyendo;
    }

    public boolean maderaOverflow() {
        return almacen * 0.8 <= madera;
    }

    public boolean barroOverflow() {
        return almacen * 0.8 <= barro;
    }

    public boolean hierroOverflow() {
        return almacen * 0.8 <= hierro;
    }

    public boolean cerealOverflow() {
        return granero * 0.8 <= cereal;
    }

}
