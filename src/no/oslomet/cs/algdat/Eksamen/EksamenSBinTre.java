package no.oslomet.cs.algdat.Eksamen;


import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        //Kontrollere for null verdi
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;
        int cmp = 0;

        while(p != null)
        {
            q = p;
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }

        p = new Node<>(verdi,q);

        if(q == null) rot = p;
        else if(cmp < 0) q.venstre = p;
        else q.høyre = p;

        antall++;
        return true;

        //Referanser til venstre barn, høyre barn, samt nodens forelder.
        //Forelder må få riktig verdi ved hver innlegging, men skal være null i rotnoden(altså hvis listen er tom?)
    }

    public boolean fjern(T verdi) {
        /*
        1. p har ingen barn. (p er en bladnode)
        2. p har nøyaktig ett barn (venstre eller høyre barn)
        3. p har to barn
        p løsrives fra treet ved at refereanse ned dit "nulles"

         */


        if(verdi == null) return false;

        Node<T> p = rot, q = null;


        while(p != null){
            int cmp = comp.compare(verdi, p.verdi);
            if(cmp < 0) {

                q = p; p = p.venstre;
            }
            else if (cmp > 0){
                q = p; p = p.høyre;
            }
            else break;
        }
        if(p == null) return false;

        if(p.venstre == null || p.høyre == null)
        {

            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            if(p == rot){
                rot = b;
                if(b != null){
                    b.forelder = null;
                }

            }
            else if (p == q.venstre) {
                q.venstre = b;
                if(q.venstre != null){
                    q.venstre.forelder = q;
                }
            }
            else {
                q.høyre = b;
                if(q.høyre != null){
                    q.høyre.forelder = q;
                }

            }
        }
        else
        {
            Node<T> s = p, r = p.høyre;
            while(r.venstre != null)
            {
                s = r;
                r = r.venstre;
            }

            p.verdi = r.verdi;

            if(s != p) {
                s.venstre = r.høyre;
            }
            else s.høyre = r.høyre;
        }
        antall--;
        return true;

    }

    public int fjernAlle(T verdi) {
        int antall = 0;
        boolean k = true;

        //kjør fjern(verdi) frem til den returnerer false
        //når fjern(verdi) returnerer false, sett k til true og returner antall

        while(k){
            k = fjern(verdi);
            if(k){
                antall++;
            }
        }
        return antall;
    }

    public int antall(T verdi) {

        //Bare en rask test for å sjekke om treet er tomt
        if(tom()) return 0;

        int antallVerdi = 0;
        Node<T> p = rot;

        while(p != null)
        {
            int cmp = comp.compare(verdi, p.verdi);
            if(cmp < 0) p = p.venstre;
            else
            {
                if (cmp == 0 ) antallVerdi++;
                p=p.høyre;
            }
        }
        return antallVerdi;
    }

    public void nullstill() {
        if(!tom()) nullstill(rot);
        rot = null;
        antall = 0;
    }

    public void nullstill(Node<T> p){
        if (p.venstre != null)
        {
            nullstill(p.venstre);
            p.venstre = null;
        }
        if (p.høyre != null)
        {
            nullstill(p.høyre);
            p.høyre = null;
        }
        p.verdi = null;
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        //Hvis noden har et venstrebarn kalles den metoden rekursivt på venstrebarnet
        if(p.venstre != null)
        {
            p = førstePostorden(p.venstre);
        }
        //Hvis noden ikke har et venstrebarn, men har et høyrebarn, kalles metoden rekursivt på høyrebarnet
        if(p.høyre != null) {
            p = førstePostorden(p.høyre);
        }

        return p;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        //hvis p ikke har en forelder (p er rotnoden), så er p den siste i postorden.
        if(p.forelder == null) return null;

        //Hvis p er høyre barn til sin forelder F, er forelderen F den neste.
        if(p == p.forelder.høyre) return p.forelder;


        //Hvis p er venstre barn til sin forelder F, gjelder:
            //Hvis p er enebarn (f.høyre er null), er forelderen f den neste
            //Hvis p ikke er enebarn (dvs. f.høyre ikke er null), så er den neste den noden som kommer først i postorden i subtreet med f.høyre som rot.
        if(p == p.forelder.venstre){
            if(p.forelder.høyre == null) return p.forelder;
            else{
                p = førstePostorden(p.forelder.høyre);
            }

        }
        return p;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = førstePostorden(rot);
        while (p != null){
            oppgave.utførOppgave(p.verdi);
            p=nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p.venstre != null)
        {
            postordenRecursive(p.venstre, oppgave);
        }
        if(p.høyre != null)
        {
            postordenRecursive(p.høyre, oppgave);
        }
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        ArrayList<T> liste = new ArrayList<>();
        ArrayDeque<Node<T>> queue = new ArrayDeque<>();
        queue.add(rot);

        while(!queue.isEmpty())
        {
            Node<T> current = queue.poll();

            if(current.forelder == null) liste.add(current.verdi);

            if(current.venstre != null){
                queue.add(current.venstre);
                liste.add(current.venstre.verdi);
            }
            if(current.høyre != null){
                queue.add(current.høyre);
                liste.add(current.høyre.verdi);
            }
        }

        return liste;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> tre = new EksamenSBinTre<>(c);

        tre.rot = new Node<>(data.get(0),null);

        for(int i = 1; i < data.size(); i++){
            tre.leggInn(data.get(i));
        }

        return tre;
    }


} // ObligSBinTre
