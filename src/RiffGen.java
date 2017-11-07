

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
 * använda try, catch för ny tab/chord row istället för en counter?		(fixat för chord row nu)
 *
 *nytt random object för varje gång istället för samma rand?
 *error med långa tabs, något med random (försvann efter att arpeggio inte utanför bounds?)
 *desirednotes inte samma som output alltid		(troligtvis i arpeggio något?)
 */
