

public class RiffGen extends Modifiers {

	public static void main(String[]args){
		strings = 6;
		frets = 12;
		desiredNotes = 15;
		maxFretJump =3;
		maxDefStringJump = 3;
		key = Key.Am;
		Generator gen = new Generator();
		gen.generate(Type.Riff);
		gen.tab.print();
	}

}
/*to do:
 *
 * tab writer
 *
 * save/export
 * 
 * chord progressions
 * 
 * solo
 *
 *arpeggio går utanför frets
 *error med långa tabs, något med random
 *desirednotes inte samma som output alltid
 */
