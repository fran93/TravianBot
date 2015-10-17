package travianbot;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;

/**
 *
 * @author Fran1488
 */
public class Login {

    final private WebClient webClient;
    private HtmlPage page;

    public Login(WebClient webClient, String user, String password) throws IOException {
        this.webClient = webClient;
        page = this.webClient.getPage("http://ts2.travian.net/?lang=es");

        //pongo el usuario
        HtmlInput userInput = (HtmlInput) page.getElementByName("name");
        userInput.setValueAttribute(user);
        //pongo la contraseña
        HtmlInput passwordInput = (HtmlInput) page.getElementByName("password");
        passwordInput.setValueAttribute(password);

        //le doy al botón de logueo
        HtmlButton button = (HtmlButton) page.getElementById("s1");
        page = button.click();

        //comprobar si me he logueado
        if (page.getElementById("content").getAttribute("class").equals("village1")) {
            System.out.println(Utils.getHour() + " - Bienvenido mi rey, espero sus órdenes.");
        } else {
            System.out.println("El usuario o la contraseña no es correcto.");
            System.exit(0);
        }
    }

    public void salir() throws IOException {
        page = webClient.getPage("http://ts2.travian.net/logout.php");
        webClient.closeAllWindows();
    }
}
