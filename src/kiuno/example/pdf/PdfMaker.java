package kiuno.example.pdf;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import kiuno.example.logger.MyLogger;
import kiuno.hibernate.HibernateDemo;

public class PdfMaker {
	public static final String leftPeace = "img/peace.png";
	public static final String rightPeace = "img/peace.png";
	public static final String DATA = "dataSet/sqlData.csv";
	private static OutputStream fos = null;
	private static PdfWriter writer = null;
	private static PdfDocument pdf = null;
	private static Document document = null;
	private static Logger log = null;

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub'
		
		try{
			log = (new MyLogger(HibernateDemo.class)).getLogger();
			
			// initialize parameter
			init();
			
			// Create dataSet from csv
			createDataSet(document);
			
			// Create some PdfImage
			createText(document);
			
			// Draw the axes
			drawAxes(pdf);
			
			// Draw Text
			drawText(pdf);
			
			log.debug("finish to make pdf file");
			pdf.close();
		}catch(FileNotFoundException e){
			log.debug(e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		document.close();
		
	}
	
	public static void init() throws FileNotFoundException{
		fos = new FileOutputStream("pdf/test.pdf");
		writer = new PdfWriter(fos);
		pdf = new PdfDocument(writer);
		document = new Document(pdf, PageSize.A4.rotate());
		document.setMargins(20, 20, 20, 20);
	}
	
	public static void process(Table table, String line, PdfFont font, boolean isHeader) {
	    StringTokenizer tokenizer = new StringTokenizer(line, ";");
	    while (tokenizer.hasMoreTokens()) {
	        if (isHeader) {
	            table.addHeaderCell(
	                new Cell().add(
	                    new Paragraph(tokenizer.nextToken()).setFont(font)));
	        } else {
	            table.addCell(
	                new Cell().add(
	                    new Paragraph(tokenizer.nextToken()).setFont(font)));
	        }
	    }
	}
	
	private static void createDataSet(Document document) throws IOException{
		// Create a PdfFont
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
		PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
		Table table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1});
		table.setWidthPercent(100);
		BufferedReader br = new BufferedReader(new FileReader(DATA));
		String line = br.readLine();
		process(table, line, bold, true);
		while ((line = br.readLine()) != null) {
		    process(table, line, font, false);
		}
		br.close();
		document.add(table);
	}
	
	private static void createText(Document document) throws IOException{
		PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
		Image lPeace = new Image(ImageDataFactory.create(leftPeace));
		Image rPeace = new Image(ImageDataFactory.create(rightPeace));
		Paragraph p = new Paragraph("this is left Peace.")
				.add(lPeace)
				.add("this is right Peace.")
				.add(rPeace)
				.setFont(font);
		document.add(p);
		// Add a Paragraph
		document.add(new Paragraph("iText is:").setFont(font));
		// Create a List
		List list = new List()
		    .setSymbolIndent(12)
		    .setListSymbol("\u2022")
		    .setFont(font);
		// Add ListItem objects
		list.add(new ListItem("Never gonna give you up"))
		    .add(new ListItem("Never gonna let you down"))
		    .add(new ListItem("Never gonna run around and desert you"))
		    .add(new ListItem("Never gonna make you cry"))
		    .add(new ListItem("Never gonna say goodbye"))
		    .add(new ListItem("Never gonna tell a lie and hurt you"));
		// Add the list
		document.add(list);
	}
	
	private static void drawAxes(PdfDocument pdf){
		Color grayColor = new DeviceCmyk(0.f, 0.f, 0.f, 0.875f);
		Color greenColor = new DeviceCmyk(1.f, 0.f, 1.f, 0.176f);
		Color blueColor = new DeviceCmyk(1.f, 0.156f, 0.f, 0.118f);
		
		PageSize ps = PageSize.A4.rotate();
		PdfPage page = pdf.addNewPage(ps);
		PdfCanvas canvas = new PdfCanvas(page);
		/* PdfCanvas.concatMatrix(a, b, c, d, e, f)
		 * [ a b 0 ]
		 * [ c d 0 ]
		 * [ e f 1 ]
		 * 0 0 1 is fixed value,because we're working in a two dimensional space.
		 * a, b, c and d can be used to scale, rotate, and skew the coordinate system.
		 * The elements e and f define the translation.
		 * 1. param a,d are scale.
		 * 2. param b is rotate.
		 * 3. param c is skew.
		 * 4. param e,f are the coordinate system.
		 */
		canvas.concatMatrix(1, 0, 0, 1, ps.getWidth() / 2, ps.getHeight() / 2);
		
		canvas.setLineWidth(0.5f).setStrokeColor(blueColor);
		for (int i = -((int) ps.getHeight() / 2 - 57);
		    i < ((int) ps.getHeight() / 2 - 56); i += 40) {
		    canvas.moveTo(-(ps.getWidth() / 2 - 15), i)
		            .lineTo(ps.getWidth() / 2 - 15, i);
		}
		for (int j = -((int) ps.getWidth() / 2 - 61);
		    j < ((int) ps.getWidth() / 2 - 60); j += 40) {
		    canvas.moveTo(j, -(ps.getHeight() / 2 - 15))
		            .lineTo(j, ps.getHeight() / 2 - 15);
		}
		canvas.stroke();
		
		canvas.setLineWidth(3).setStrokeColor(grayColor);
		//Draw X axis
		canvas.moveTo(-(ps.getWidth() / 2 - 15), 0)
		        .lineTo(ps.getWidth() / 2 - 15, 0)
		        .stroke();
		//Draw X axis arrow
		canvas.setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND)
		        .moveTo(ps.getWidth() / 2 - 25, -10)
		        .lineTo(ps.getWidth() / 2 - 15, 0)
		        .lineTo(ps.getWidth() / 2 - 25, 10).stroke()
		        .setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.MITER);
		//Draw Y axis
		canvas.moveTo(0, -(ps.getHeight() / 2 - 15))
		        .lineTo(0, ps.getHeight() / 2 - 15)
		        .stroke();
		//Draw Y axis arrow
		canvas.saveState()
		        .setLineJoinStyle(PdfCanvasConstants.LineJoinStyle.ROUND)
		        .moveTo(-10, ps.getHeight() / 2 - 25)
		        .lineTo(0, ps.getHeight() / 2 - 15)
		        .lineTo(10, ps.getHeight() / 2 - 25).stroke()
		        .restoreState();
		
		//Draw X serif
		for (int i = -((int) ps.getWidth() / 2 - 61);
		    i < ((int) ps.getWidth() / 2 - 60); i += 40) {
		    canvas.moveTo(i, 5).lineTo(i, -5);
		}
		//Draw Y serif
		for (int j = -((int) ps.getHeight() / 2 - 57);
		    j < ((int) ps.getHeight() / 2 - 56); j += 40) {
		    canvas.moveTo(5, j).lineTo(-5, j);
		}
		canvas.stroke();
		
		canvas.setLineWidth(2).setStrokeColor(greenColor)
	        .setLineDash(10, 10, 8)
	        .moveTo(-(ps.getWidth() / 2 - 15), -(ps.getHeight() / 2 - 15))
	        .lineTo(ps.getWidth() / 2 - 15, ps.getHeight() / 2 - 15).stroke();
		//Draw XY axis
		canvas.moveTo(-(ps.getWidth() / 2 - 15), -(ps.getHeight() / 2 - 15))
			.lineTo(ps.getWidth() / 2 - 15, ps.getHeight() / 2 - 15)
			.stroke();
	}
	
	private static void drawText(PdfDocument pdf) throws IOException{
		PageSize ps = PageSize.A4.rotate();
		PdfPage page = pdf.addNewPage(ps);
		PdfCanvas canvas = new PdfCanvas(page);
		
		ArrayList<String> text = new ArrayList();
		text.add("         Episode V         ");
		text.add("  THE EMPIRE STRIKES BACK  ");
		text.add("It is a dark time for the");
		text.add("Rebellion. Although the Death");
		text.add("Star has been destroyed,");
		text.add("Imperial troops have driven the");
		text.add("Rebel forces from their hidden");
		text.add("base and pursued them across");
		text.add("the galaxy.");
		text.add("Evading the dreaded Imperial");
		text.add("Starfleet, a group of freedom");
		text.add("fighters led by Luke Skywalker");
		text.add("has established a new secret");
		text.add("base on the remote ice world");
		text.add("of Hoth...");
		
		int maxStringWidth = 0;
        for (String fragment : text) {
            if (fragment.length() > maxStringWidth) maxStringWidth = fragment.length();
        }
		
		// Draw background using black
		canvas.rectangle(0, 0, ps.getWidth(), ps.getHeight())
	        .setColor(Color.BLACK, true)
	        .fill();
		
		canvas.concatMatrix(1, 0, 0, 1, 0, ps.getHeight());
		Color yellowColor = new DeviceCmyk(0.f, 0.0537f, 0.769f, 0.051f);
		float lineHeight = 5;
		float yOffset = -40;
		canvas.beginText()
			.setFontAndSize(PdfFontFactory.createFont(FontConstants.COURIER_BOLD), 1)
		    .setColor(yellowColor, true);
		
		for (int j = 0; j < text.size(); j++) {
		    String line = text.get(j);
		    float xOffset = ps.getWidth() / 2 - 45 - 8 * j;
		    float fontSizeCoeff = 6 + j;
		    float lineSpacing = (lineHeight + j) * j / 1.5f;
		    int stringWidth = line.length();
		    for (int i = 0; i < stringWidth; i++) {
		        float angle = (maxStringWidth / 2 - i) / 2f;
		        float charXOffset = (4 + (float) j / 2) * i;
		        canvas.setTextMatrix(fontSizeCoeff, 0,
		                angle, fontSizeCoeff / 1.5f,
		                xOffset + charXOffset, yOffset - lineSpacing)
		            .showText(String.valueOf(line.charAt(i)));
		    }
		}
		canvas.endText();
	}
}
