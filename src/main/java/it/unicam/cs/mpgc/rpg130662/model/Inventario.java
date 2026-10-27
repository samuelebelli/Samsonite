package it.unicam.cs.mpgc.rpg130662.model;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;
    /** L'inventario magico dell'eroe: contiene oggetti fino a una capienza massima. */
public class Inventario {

    private final int Capienza;
    private final List<Oggetto> oggetti = new ArrayList<>();

    public Inventario(int capienza){
        if (capienza <= 0){
            throw new IllegalArgumentException("La capienza deve essere positiva");
        }
        this.Capienza = capienza;
    }

        public int getCapienza() {
            return Capienza;
        }

        public boolean isPieno() {
            return oggetti.size() >= Capienza;
        }

        /** Metodo che aggiunge un oggetto.
         * Restituisce false se l'inventario è pieno,
         * altrimenti lo aggiunge*/
        public boolean aggiungi(Oggetto oggetto) {
            if (oggetto == null) {
                throw new IllegalArgumentException("L'oggetto non può essere nullo");
            }
            if (isPieno()) {
                return false;
            }
            return oggetti.add(oggetto);
        }

        /** Metodo che rimuove un oggetto. Restituisce false se non era presente. */
        public boolean rimuovi(Oggetto oggetto) {
            return oggetti.remove(oggetto);
        }

        /** Vista in sola lettura degli oggetti contenuti. */
        public List<Oggetto> getOggetti() {
            return Collections.unmodifiableList(oggetti);
        }
}
