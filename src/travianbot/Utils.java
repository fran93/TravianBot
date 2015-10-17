package travianbot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Fran1488
 */
public class Utils {

    /**
     * Devuelve la mina que debería ser construida Prioridad Barro > Madera >
     * Cereal > Hierro
     *
     * @param enteros
     * @param edificios
     * @return
     */
    public static int getBuildingToBuild(int[] enteros, String[] edificios) {
        int position = 0;
        int min = enteros[0];
        for (int i = 0; i < enteros.length; i++) {
            //si encontramos un número menor, cambiamos la posición y el número
            if (min > enteros[i]) {
                min = enteros[i];
                position = i;
                //si encontramos un número igual comprobamos el tipo de edificio que es
            } else if (min == enteros[i]) {
                if (compararEdificios(edificios[position], edificios[i])) {
                    min = enteros[i];
                    position = i;
                }
            }
        }
        return position;
    }

    /**
     * Prioridad Barro > Madera > Hierro > Cereal
     *
     * @return true si el segundo edificio tiene prioridad
     */
    private static boolean compararEdificios(String build1, String build2) {
        //la fórmula es sencilla, voy comprobando por orden de prioridad si el edificio está en el primer o en el segundo lugar
        if (build1.startsWith("Barrera")) {
            return false;
        } else if (build2.startsWith("Barrera")) {
            return true;
        } else if (build1.startsWith("Leñador")) {
            return false;
        } else if (build2.startsWith("Leñador")) {
            return true;
        } else if (build1.startsWith("Mina")) {
            return false;
        } else if (build2.startsWith("Mina")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * @param madera
     * @param barro
     * @param hierro
     * @return el valor más bajo
     */
    public static String getLowerResource(int madera, int barro, int hierro){
        if((madera<barro) && (madera<hierro) ){
            return "1";
        }else if((barro<madera) && (barro<hierro)){
            return "2";
        }else{
            return "3";
        }
    }

    /**
     * Devuelve la hora actual
     *
     * @return
     */
    public static String getHour() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}
