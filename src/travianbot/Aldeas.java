package travianbot;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;
import java.io.IOException;
import java.util.ArrayList;

public class Aldeas {

    final private WebClient webClient;
    private HtmlPage page;
    private ArrayList<String> aldeas = new ArrayList();

    public Aldeas(WebClient webClient) throws IOException {
        this.webClient = webClient;
        
        page = this.webClient.getPage("http://ts2.travian.net/dorf1.php");
        
        HtmlDivision sidebarBox = (HtmlDivision) page.getElementById("sidebarBoxVillagelist");
        HtmlUnorderedList list = (HtmlUnorderedList) sidebarBox.getElementsByTagName("ul").get(0);
        //voy recorriendo el dom hasta llegar a la lista de aldeas
        DomNodeList<HtmlElement> elementsLi = list.getElementsByTagName("li");
        for(HtmlElement li: elementsLi){
            //obtener el enlace
            HtmlAnchor enlace = (HtmlAnchor)li.getFirstElementChild();
            //añadir la dirección al Array
            aldeas.add(enlace.getHrefAttribute());
        }
    }
    
    /**
     * Devuelve el número de aldeas
     * @return 
     */
    public int size(){
        return aldeas.size();
    }
    
    /**
     * Devuelve la aldea de la posición pasada por parámetro
     * @param i
     */
    public void get(int i) throws IOException{
        webClient.getPage("http://ts2.travian.net/dorf1.php"+aldeas.get(i));
    }
}
