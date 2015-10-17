package travianbot;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import java.io.Console;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

/**
 *
 * @author Fran1488
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //redirecciones permitidas, nada de excepciones en javascript, y css deshabilitado
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        //desactivar los warnings
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);

        //se piden los datos al usuario
        Console console = System.console();
        String user;
        String password;
        if (console != null) {
            System.out.print("Usuario: ");
            user = console.readLine();
            System.out.print("Contraseña: ");
            password = new String(console.readPassword());
        } else {
            Scanner teclado = new Scanner(System.in);
            System.out.print("Usuario: ");
            user = teclado.nextLine();
            System.out.print("Contraseña: ");
            password = teclado.nextLine();
        }

        while (true) {
            try {
                //me logueo
                Login log = new Login(webClient, user, password);

                //obtener todas las aldeas
                Aldeas aldeas = new Aldeas(webClient);

                for (int i = 0; i < aldeas.size(); i++) {
                    //entrar en una aldea
                    aldeas.get(i);
                    //compruebo que las tropas estén listas
                    MovimientosTropas trops = new MovimientosTropas(webClient);

                    if (trops.getTropasDisponibles()) {
                        Vacas vacas = new Vacas(webClient, trops);
                        vacas.atacar();
                    }

                    //obtengo los datos sobre los edificios
                    RecursosEdificios resources = new RecursosEdificios(webClient);

                    //si no se está construyendo pongo un edificio en la cola
                    if (!resources.isConstruyendo()) {

                        //obtengo el nivel de las minas
                        int[] lvlminas = resources.getLvlminas();
                        String[] typeminas = resources.getTypeminas();

                        //subo una mina 
                        SubirEdificios upmina = new SubirEdificios(webClient);
                        //subo la mina más baja
                        int position = Utils.getBuildingToBuild(lvlminas, typeminas);
                        //Muestro el edficio en construcción
                        System.out.println(Utils.getHour() + " - Subiendo " + typeminas[position]);
                        //aumento en 1 la posición, ya que la web no comienza en 0
                        position++;
                        upmina.start(position);
                    }

                    //obtengo los recursos para gastar los excedentes
                    resources.getResources();
                    EntrenarTropas entrenar;
                    //si sobra hierro se hacen eduos
                    if (resources.hierroOverflow()) {
                        entrenar = new EntrenarTropas(webClient);
                        entrenar.EntrenarEduos();
                    } //si sobra madera se hacen druidas
                    else if (resources.maderaOverflow()) {
                        entrenar = new EntrenarTropas(webClient);
                        entrenar.EntrenarDruidas();
                    } //si sobra barro se hacen rayos
                    else if (resources.barroOverflow()) {
                        entrenar = new EntrenarTropas(webClient);
                        entrenar.EntrenarRayos();
                        entrenar.EntrenarFalanges();
                        entrenar.EntrenarFalanges();
                    } //si sobra cereal se vende en el mercado
                    else if (resources.cerealOverflow()) {
                        resources.venderCereal();
                    }
                }
                //me deslogueo
                log.salir();

                //esperar 30 minutos
                System.out.println(Utils.getHour() + " - Líder de la tribu descansando durante 30 minutos.");
                Thread.sleep(60000 * 30);
            } catch (IOException | InterruptedException | NullPointerException ex) {
                System.out.println(Utils.getHour() + " - Excepción: " + ex + "\tMessage:" + ex.getMessage());
            }
        }
    }

}
