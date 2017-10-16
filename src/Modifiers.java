import java.util.ArrayList;
import java.util.Random;

//for stuff to not get in the way
abstract class Modifiers {
	public enum Type{
		ChordProgression,
		Riff,
		Solo
	}
	public enum Key{
		A,Am,Bb,Bbm,B,Bm,C,Cm,Db,Dbm,D,Dm,Eb,Ebm,E,Em,F,Fm,Gb,Gbm,G,Gm,Ab,Abm
	}
	static Random rand = new Random();
	//variables used for riff
	static int strings;	//number of strings
	static int frets;	//number of frets
	static int desiredNotes;	//number of notes to be included in the riff
	static int maxFretJump;	//maximum fret change between successive notes
	static int maxStringJump;	//maximum string change between successive notes
	static int maxDefStringJump;	//maximum defined string change between successive notes
	static Key key;	//current key
	int[] lastNote = {0,0};	//the latest note added to the tab
	int riffLength;	//number of notes in riff, if this exceeds maximum capacity of a tab row, a new one should be created
	//
	Fretboard fretboard;
	Tab tab;
	//the notes in each key, remember that major uses the same notes as it's corresponding minor, e.g. C->Am
	int[][] AmNotes = {{0,0},{0,1},{0,3},{0,5},{0,7},{0,8},{0,10},{0,12},{0,13},{0,15},{0,17},{0,19},{0,20},{1,0},{1,1},{1,3},{1,5},{1,6},{1,8},{1,10},{1,12},{1,13},{1,15},{1,17},{1,18},{1,20},{2,0},{2,2},{2,4},{2,5},{2,7},{2,9},{2,10},{2,12},{2,14},{2,16},{2,17},{2,19},{3,0},{3,2},{3,3},{3,5},{3,7},{3,9},{3,10},{3,12},{3,14},{3,15},{3,17},{3,19},{4,0},{4,2},{4,3},{4,5},{4,7},{4,8},{4,10},{4,12},{4,14},{4,15},{4,17},{4,19},{4,20},{5,0},{5,1},{5,3},{5,5},{5,7},{5,8},{5,10},{5,12},{5,13},{5,15},{5,17},{5,19},{5,20}};
	int[][] EmNotes = {{0,0},{0,2},{0,3},{0,5},{0,7},{0,8},{0,10},{0,12},{0,14},{0,15},{0,17},{0,19},{0,20},{1,0},{1,1},{1,3},{1,5},{1,7},{1,8},{1,10},{1,12},{1,13},{1,15},{1,17},{1,19},{1,20},{2,0},{2,2},{2,4},{2,5},{2,7},{2,9},{2,11},{2,12},{2,14},{2,16},{2,17},{2,19},{3,0},{3,2},{3,4},{3,5},{3,7},{3,9},{3,10},{3,12},{3,14},{3,16},{3,17},{3,19},{4,0},{4,2},{4,3},{4,5},{4,7},{4,9},{4,10},{4,12},{4,14},{4,15},{4,17},{4,19},{5,0},{5,2},{5,3},{5,5},{5,7},{5,8},{5,10},{5,12},{5,14},{5,15},{5,17},{5,19},{5,20}};
	int[][] BmNotes = {{0,0},{0,2},{0,3},{0,5},{0,7},{0,9},{0,10},{0,12},{0,14},{0,15},{0,17},{0,19},{1,0},{1,2},{1,3},{1,5},{1,7},{1,8},{1,10},{1,12},{1,14},{1,15},{1,17},{1,19},{1,20},{2,0},{2,2},{2,4},{2,6},{2,7},{2,9},{2,11},{2,12},{2,14},{2,16},{2,18},{2,19},{3,0},{3,2},{3,4},{3,5},{3,7},{3,9},{3,11},{3,12},{3,14},{3,16},{3,17},{3,19},{4,0},{4,2},{4,4},{4,5},{4,7},{4,9},{4,10},{4,12},{4,14},{4,16},{4,17},{4,19},{5,0},{5,2},{5,3},{5,5},{5,7},{5,9},{5,10},{5,12},{5,14},{5,15},{5,17},{5,19}};
	int[][] DmNotes = {{0,0},{0,1},{0,3},{0,5},{0,6},{0,8},{0,10},{0,12},{0,13},{0,15},{0,17},{0,18},{0,20},{1,1},{1,3},{1,5},{1,6},{1,8},{1,10},{1,11},{1,13},{1,15},{1,17},{1,18},{1,20},{2,0},{2,2},{2,3},{2,5},{2,7},{2,9},{2,10},{2,12},{2,14},{2,15},{2,17},{2,19},{3,0},{3,2},{3,3},{3,5},{3,7},{3,8},{3,10},{3,12},{3,14},{3,15},{3,17},{3,19},{3,20},{4,0},{4,1},{4,3},{4,5},{4,7},{4,8},{4,10},{4,12},{4,13},{4,15},{4,17},{4,19},{4,20},{5,0},{5,1},{5,3},{5,5},{5,6},{5,8},{5,10},{5,12},{5,13},{5,15},{5,17},{5,18},{5,20}};
	int[][] currentKeyNotes;
	ArrayList <int[]> posNewNotes = new ArrayList<>();	//possible new notes
}
