import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

//used for exporting tabs
public class TabExporter {
	public TabExporter(){
		
	}
	
	//saves the tab as a text file	(credit to Jeremy Smyth https://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java)
	public void saveToText(Tab tab, String fileName){
		try (PrintStream out = new PrintStream(new FileOutputStream("tabs/"+fileName+".txt"))) {
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
	
	//saves the tab as an image file	(credit to MadProgrammer https://stackoverflow.com/questions/18800717/convert-text-content-to-image)
	public void saveToImage(Tab tab, String fileName){
		String text = new String();
		switch(tab.type){
		case Riff:
			for(int i=0; i<tab.tab.size(); i++){
				text += "\n";	//empty line between tab rows
				for(StringBuilder str: tab.tab.get(i)){
					text += str+"\n";
				}
			}
			break;
		case ChordProgression:
			for(int i=0; i<tab.chords.size(); i++){
				text += "\n";	//empty line between chord rows
				text += tab.chords.get(i)+"\n";
			}
			break;
		}
		
		BufferedImage img = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		Font font = new Font("Arial", Font.PLAIN, 48);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int width = 0;
		int height = 0;
		switch(tab.type){
		case Riff:
			for(int i=0; i<tab.tab.size(); i++){
				height += (tab.tab.get(i).size()+1)*fm.getHeight();	//+1 for the extra empty road between tab rows
				for(int k=0; k<tab.tab.get(i).size(); k++){
					if(width < fm.stringWidth(tab.tab.get(i).get(k).toString())){
						width = fm.stringWidth(tab.tab.get(i).get(k).toString());
					}
				}
			}
			break;
		case ChordProgression:
			for(int i=0; i<tab.chords.size(); i++){
				height += 2*fm.getHeight();	//+1 for chord row, +1 for empty line between chord rows
				if(width < fm.stringWidth(tab.chords.get(i).toString())){
					width = fm.stringWidth(tab.chords.get(i).toString());
				}
			}
			break;
		}
		g2d.dispose();
		
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        
        int lineNum = 0;	//current line to draw string on
        switch(tab.type){
		case Riff:
			for(int i=0; i<tab.tab.size(); i++){
				g2d.drawString("", 0, fm.getAscent()*lineNum+fm.getAscent());	//empty line between tab rows
				lineNum++;
				for(StringBuilder str: tab.tab.get(i)){
					g2d.drawString(str.toString(), 0, fm.getAscent()*lineNum+fm.getAscent());
					lineNum++;
				}
			}
			break;
		case ChordProgression:
			for(int i=0; i<tab.chords.size(); i++){
				g2d.drawString("", 0, fm.getAscent()*lineNum+fm.getAscent());	//empty line between chord rows
				lineNum++;
				g2d.drawString(tab.chords.get(i).toString(), 0, fm.getAscent()*lineNum+fm.getAscent());
				lineNum++;
			}
			break;
		}
        g2d.dispose();
        
        try{
        	ImageIO.write(img, "png", new File("tabs/"+fileName+".png"));
        } catch(IOException e){
        	e.printStackTrace();
        }
	}
}
