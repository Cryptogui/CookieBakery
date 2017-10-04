import java.util.ArrayList;

public class RiffGen {
	public static void main(String[]args){
		Fretboard fretboard = new Fretboard(3);
		Tab tab = new Tab(fretboard);
		tab.print();
	}
}
