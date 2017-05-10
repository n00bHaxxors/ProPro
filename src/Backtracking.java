
import java.util.Set;
import java.util.TreeSet;

/** @file PuntInteres.java
    @brief Classe PuntInteres
*/

/** @class PuntInteres
    @brief Punt de Interès en el mapa
    @author Ismael El Habri
*/

public class Backtracking {
    private Mapa g;
    public Backtracking (Mapa mapa){
        g=mapa;
    }
//    public Circuit AlgBT(PuntInteres a, PuntInteres b, Set<PuntInteres> c) {
//        Circuit r = new Circuit();
//        if (c.isEmpty()) {
//            r = g.Dijkstra(a, b);
//        } else {
//            int l = Integer.MAX_VALUE; // Menor longitud coneguda
//            TreeSet<PuntInteres> c2 = new TreeSet(c); // Còpia
//            for (PuntInteres i : c) {
//                Circuit r1 = g.Dijkstra(a, b);
//                if (r1.isEmpty()) {
//                    break; // no hi ha camí -> pleguem
//                } else {
//                    c2.remove(i);
//                    c2.removeAll(r1);
//                    Circuit r2 = AlgBT(i, b, c2); // Solució del problema reduït
//                    c2.add(i);
//                    c2.addAll(r1);
//                    if (!r2.isEmpty() && r1.size() + r2.size() < l) {
//                        r = r1.concatenar(r2);
//                    }
//                }
//            }
//        }
//        return r;
//    }

}
