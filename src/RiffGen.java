

public class RiffGen extends Modifiers {

	public static void main(String[]args){
		strings = 6;
		frets = 12;
		desiredNotes = 30;
		maxFretJump =3;
		maxDefStringJump = 3;
		key = Key.Am;
		desiredChords = 8;
		type = Type.Riff;
		//type = Type.ChordProgression;
		Generator gen = new Generator();
		gen.generate();
		gen.tab.print();
	}

}
/*to do:
 *
 * tab writer
 *
 * save/export, probably i Tab class
 * 
 * chord progressions	(fixat i Tab, resten borde vara att fixa magi i Generator)
 * 
 * solo
 * 
 * anv�nda try, catch f�r ny tab/chord row ist�llet f�r en counter?		(fixat f�r chord row nu)
 *
 *nytt random object f�r varje g�ng ist�llet f�r samma rand?
 *error med l�nga tabs, n�got med random (f�rsvann efter att arpeggio inte utanf�r bounds?)
 *desirednotes inte samma som output alltid		(troligtvis i arpeggio n�got?)
 */
