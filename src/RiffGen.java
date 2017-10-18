

public class RiffGen extends Modifiers {
	
	public static void main(String[]args){
		strings = 6;
		frets = 12;
		desiredNotes = 8;
		maxFretJump =3;
		maxDefStringJump = 3;
		key = Key.Am;
		Generator gen = new Generator();
		gen.generate(Type.Riff);
		gen.tab.print();
	}
	
}