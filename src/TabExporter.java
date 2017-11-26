import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

//
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Used for exporting tabs
 * @author C
 * @version 24.11.2017
 *
 */
public class TabExporter {
    
	/**
	 * 
	 */
	public TabExporter(){
		
	}
	

	/**
	 * Saves the tab as a text file    (credit to Jeremy Smyth https://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java)
	 * @param tab
	 * @param fileName
	 */
	public void save_to_text(Tab tab, String fileName){
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
	
	/**
	 * Saves the tab as an image file  (credit to MadProgrammer https://stackoverflow.com/questions/18800717/convert-text-content-to-image)
	 * @param tab
	 * @param fileName
	 */
   public void save_to_image(Tab tab, String fileName) throws IOException{
		/*
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
        default:
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
        default:
            break;
		}
        g2d.dispose();
        
        try{
        	ImageIO.write(img, "png", new File("tabs/"+fileName+".png"));
        } catch(IOException e){
        	e.printStackTrace();
        }
        */
		
PDDocument doc = new PDDocument();	//new pdf document
		
		PDPage page = new PDPage();	//create a new page
		doc.addPage(page);	//add the page to the document
		
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);
		
		contentStream.beginText();
		
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);	//set font to the content stream
		contentStream.newLineAtOffset(25, 700);	//set position for the line
		contentStream.setLeading(14.5f);	//set text leading
		
		switch(tab.type){
		case Riff:
			for(int i=0; i<tab.tab.size(); i++){	//for every tab row
				contentStream.newLine();	//print an empty line for visibility between tab rows
				for(StringBuilder str: tab.tab.get(i)){	//for every string on the tab row
					contentStream.showText(str.toString());	//print the string
					contentStream.newLine();
				}
			}
			break;
		case ChordProgression:
			for(int i=0; i<tab.chords.size(); i++){	//for every chord row
				contentStream.newLine();;	//print an empty line for visibility between chord rows
				contentStream.showText(tab.chords.get(i).toString());	//print the chord row
				contentStream.newLine();
			}
			break;
		}
		
		contentStream.endText();
		contentStream.close();
		
		PDFRenderer renderer = new PDFRenderer(doc);
		BufferedImage image = renderer.renderImage(0);
		ImageIO.write(image, "png", new File("tabs/"+fileName+".png"));
		
		doc.close();	//close the file
	}
	
	//saves the tab as a pdf document
	public void save_to_pdf(Tab tab, String fileName) throws IOException{
		
		PDDocument doc = new PDDocument();	//new pdf document
		
		PDPage page = new PDPage();	//create a new page
		doc.addPage(page);	//add the page to the document
		page.setMediaBox(PDRectangle.A4);
		
		PDPageContentStream contentStream = new PDPageContentStream(doc, page);
		
		contentStream.beginText();
		
		PDFont font = PDType1Font.TIMES_ROMAN;
		int fontsize = 12;
		Point2D.Float offset = new Point2D.Float(25, 700);
		contentStream.setFont(font, fontsize);	//set font to the content stream
		//contentStream.newLineAtOffset(offset.x, offset.y);
		
		Point2D.Float pageCenter = new Point2D.Float(page.getMediaBox().getWidth()/2F, page.getMediaBox().getHeight()/2F);	//center of the page, used for centering text
		float stringWidth = font.getStringWidth(tab.tab.get(0).get(0).toString())*fontsize/1000F;	//width of the tab row
		float textX = pageCenter.x - stringWidth / 2F + offset.x;	//for centering along the vertical axis
		float textY = pageCenter.y + offset.y;	//for centering along the horizontal axis
		contentStream.newLineAtOffset(textX, textY);	//set initial position for the line
		contentStream.setLeading(14.5f);	//set text leading
		
		
		switch(tab.type){
		case Riff:
			for(int i=0; i<tab.tab.size(); i++){	//for every tab row
				contentStream.newLine();	//print an empty line for visibility between tab rows
				for(StringBuilder str: tab.tab.get(i)){	//for every string on the tab row
					contentStream.showText(str.toString());	//print the string
					contentStream.newLine();
				}
			}
			break;
		case ChordProgression:
			for(int i=0; i<tab.chords.size(); i++){	//for every chord row
				contentStream.newLine();;	//print an empty line for visibility between chord rows
				contentStream.showText(tab.chords.get(i).toString());	//print the chord row
				contentStream.newLine();
			}
			break;
		}
		
		contentStream.endText();
		contentStream.close();
		
		doc.save("tabs/"+fileName+".pdf");	//save the file
		doc.close();	//close the file
		
	}
}
