// Objeker av klasser som implementerer dette grenesenittet
// har en skrivutmetode som er nyttig under testing

interface  Skrivbar {
     void skrivUt();
}

// BSTre-klasen har en parameter som både har en sammenligningsmetode
// og en skrivutmetode. Legg merke til syntaksen (& ikke ,)

class BSTre <T extends Comparable<T> & Skrivbar > {

    private Node rot;

    public void leggInn (T ny) {
	Node nynode = new Node(ny);
	if ( rot == null ) rot = nynode;
	else rot.leggTilTre(nynode);
    }

    public void skriv () {
	if ( rot != null ) rot.skrivUt();
    }

    public T finn (T t) {
	Node node = new Node(t);
	if ( rot != null )
	    return rot.finniDenneEllerUnder(node).denne;
	else return null;
    }

    private class Node {
	Node venstre, høyre;
	T denne;
	
	Node (T t){denne = t;}
	
	void leggTilTre(Node inn){
	    int smnlgn = denne.compareTo( inn.denne );
	    if ( smnlgn < 0 )
		if (høyre == null) høyre = inn;
		else høyre.leggTilTre(inn);
	    else
		if (venstre == null) venstre = inn;
		else venstre.leggTilTre(inn);
	}
	
	Node finniDenneEllerUnder (Node likDenne){
	    Node fantDette;
	    int smnlgn = denne.compareTo( likDenne.denne );
	    if ( smnlgn < 0 )
		if (høyre == null) fantDette = null;
		else fantDette = høyre.finniDenneEllerUnder(likDenne);
	    else if ( smnlgn > 0 )
		if (venstre == null) fantDette = null;
		else fantDette = venstre.finniDenneEllerUnder(likDenne);
	    else // smnlgn == 0, dvs. dette er noden det letes etter
		fantDette = this;
	    return fantDette;
	}
	
	// Metoden skriver «minste» node ut først. Det bestemmes av
	// rekkefølgen av kallene på skrivUt() fra T.
	void skrivUt(){
	    if (venstre != null) venstre.skrivUt();
	    denne.skrivUt();
	    if (høyre != null) høyre.skrivUt();
	}
    }
}

// Ord er en klasse som inneholder en tekststreng som
// identifikator og som gjør det mulig å definere en (alfabetisk)
// ordning av objektene samt metoden compareTo

class Ord implements Comparable<Ord>, Skrivbar {
    private String ordet;
    Ord (String s) { ordet = s; }

    public int compareTo(Ord o){
	return ordet.compareToIgnoreCase(o.ordet);
    }

    public void skrivUt() {
	System.out.print(ordet+" ");
    }
}

// I dette eksempelet lages et lite tre med 10 ord. Innsettingsrekkefølgen
// gjør at vi får et tre med (maks) dybde 4. Etter at treet er generert, skrives
// hele treet (ordene) ut. Finner deretter et Nodeobjekt som har ordet «hanske»
// og skriver det ut

public class E20130418{
    public static void main( String[] args ) {
	new EksempelBST();
    }
}

class EksempelBST {
    EksempelBST () {
 
	BSTre<Ord> treet = new BSTre<Ord>();
	
	treet.leggInn(new Ord("kryss"));
	treet.leggInn(new Ord("hanske"));
	treet.leggInn(new Ord("angre"));
	treet.leggInn(new Ord("nikkel"));
	treet.leggInn(new Ord("ansvar"));
	treet.leggInn(new Ord("juletre"));
	treet.leggInn(new Ord("trøffel"));
	treet.leggInn(new Ord("hylle"));
	treet.leggInn(new Ord("adrenalin"));
	treet.leggInn(new Ord("laser"));
	System.out.println("==========");
	treet.skriv(); System.out.println("");
	System.out.println("==========");

	Ord funnet = 
	    treet.finn(new Ord ("hanske"));

	System.out.println("==========");
	if (funnet != null) funnet.skrivUt(); System.out.println("");
	System.out.println("==========");

    }
}

