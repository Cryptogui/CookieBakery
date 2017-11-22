import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

//used for exporting tabs
public class TabExporter {
	public TabExporter(){
		
	}
	
	//saves the tab as a text file
	public void saveToText(Tab tab, String fileName){
		try (PrintStream out = new PrintStream(new FileOutputStream(fileName+".txt"))) {
			switch(tab.type){
			case Riff:
				for(int i=0; i<tab.tab.size(); i++){	//for every tab row
					out.println();	//print an empty line for visibility between tab rows
					for(StringBuilder str: tab.tab.get(i)){	//for every string on the tab row
						out.println(str.toString());	//print the string
					}
				}
				break;
			case ChordProgression:
				for(int i=0; i<tab.chords.size(); i++){	//for every chord row
					out.println();	//print an empty line for visibility between chord rows
					out.println(tab.chords.get(i));	//print the string
				}
				break;
			default:
				break;
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
