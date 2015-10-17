package travianbot;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Fran1488
 */
public class Vacas {

    private final ArrayList<String> vacas = new ArrayList();
    private final MovimientosTropas trops;
    final private WebClient webClient;
    private HtmlPage page;
    private static int index = 0;
    private final int TROPS_TO_SEND = 100;

    public Vacas(WebClient webClient, MovimientosTropas trops) {
        this.webClient = webClient;
        this.trops = trops;
    }

    /**
     * Método que lee las cordenadas de las vacas ejes x e y
     */
    private void readFile() {
        try (BufferedReader bf = new BufferedReader(new FileReader("vacas.txt"))) {
            while (bf.ready()) {
                vacas.add(bf.readLine());
            }
        } catch (IOException ex) {
            System.out.println("Error leyendo fichero vacas.txt " + ex.getMessage());
        }
    }

    /**
     * Ataca una granja
     *
     * @throws IOException
     */
    public void atacar() throws IOException {
        //leo el fichero, de esta forma es posible actualizarlo en caliente
        readFile();
        //mientras haya rayos, se atraca
        while (trops.getRayos() >= 50) {
            //compruebo que el índice no haya pasado el número de vacas
            if (index >= vacas.size()) {
                index = 0;
            }
            //obtenemos las cordenadas
            String[] buffer = vacas.get(index).split(" ");
            //atracamos
            enviarRayosAtraco(buffer[0], buffer[1]);
            //aumento el índice para que la próxima vez lea el siguiente objetivo
            index++;
        }
    }

    /**
     * Atraca una aldea con rayos
     *
     * @param x
     * @param y
     * @throws IOException
     */
    private void enviarRayosAtraco(String x, String y) throws IOException {
        page = webClient.getPage("http://ts2.travian.net/build.php?tt=2&id=39");
        //introducir cordenada x
        HtmlInput xinput = (HtmlInput) page.getElementById("xCoordInput");
        xinput.setValueAttribute(x);
        //introducir cordenada y
        HtmlInput yinput = (HtmlInput) page.getElementById("yCoordInput");
        yinput.setValueAttribute(y);

        //añadir rayos
        HtmlInput rayos = (HtmlInput) page.getElementByName("t4");
        //si tengo suficiente para llenar el límite
        if (trops.getRayos() > TROPS_TO_SEND) {
            rayos.setValueAttribute(TROPS_TO_SEND + "");
            trops.setRayos(trops.getRayos() - TROPS_TO_SEND);
            //cuando no tengo tropas para llenar el límite
        } else {
            rayos.setValueAttribute(trops.getRayos() + "");
            trops.setRayos(0);
        }
        //añadir héroe
        try {
            HtmlInput heroe = (HtmlInput) page.getElementByName("t11");
            heroe.setValueAttribute("1");
        } catch (ElementNotFoundException ex) {
            //el héroe no está
        }

        //seleccionar ataque normal
        HtmlInput ataque = (HtmlInput) page.getElementsByName("c").get(1);
        ataque.setChecked(true);
        //enviar
        HtmlButton enviar = (HtmlButton) page.getElementById("btn_ok");
        page = enviar.click();
        //confirmar
        HtmlButton confirmar = (HtmlButton) page.getElementById("btn_ok");
        page = confirmar.click();
        //mostramos el mensaje
        System.out.println(Utils.getHour() + " - Rayos atracando a " + x + " " + y);
    }
}
