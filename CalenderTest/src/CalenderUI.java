import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

/**
 * GUI-Klasse
 * 
 * @author m3
 * 
 */
public class CalenderUI extends JFrame
{
	private static final long serialVersionUID = 7590384532552034627L;
	private static final int OFF_X = 8;
	private static final int OFF_XLine = 30;
	private static final int OFF_Y = 30;
	private static final int FRAME_WIDTH = 400;
	private static final int WIDTH = FRAME_WIDTH - OFF_X - OFF_XLine;
	private static final int HEIGHT = 24 * 40; // Y:1px-->3min
	private static final double MINUTES_PER_PIXEL = 1.5; // Y:1px-->3min

	private List<tBox> _box;

	public CalenderUI()
	{
		this.setSize(FRAME_WIDTH + 30, HEIGHT + OFF_Y);
		this.setVisible(true);
		this.setTitle("Calender");
		_box = new LinkedList<tBox>();
	}

	public void createBox(int offsetX, int offsetY, int width, int height,
			Color color)
	{
		tBox box = new tBox(offsetX, offsetY, width, height, color);
		_box.add(box);
	}

	/**
	 * Erstellt eine Box auf dem Frame aus einem tEvent
	 * 
	 * @param event
	 *            Das zu zeichnende Event
	 * @param column
	 *            Spalte für das Event
	 * @param columnNumber
	 *            Gesamtspaltenanzahl
	 * 
	 * @require column <= columnNumber
	 */
	public void createBoxFromEvent(tEvent event, int column, int columnNumber)
	{
		assert column <= columnNumber : "Vorbedingung verletzt: column <= columnNumber";

		tTime start = event.startTime();
		int startMin = start.hour() * 60 + start.minute();
		tTime end = event.endTime();
		int endMin = end.hour() * 60 + end.minute();
		int duration = endMin - startMin;
		if(duration < 3) duration = 3;

		int boxWidth = WIDTH / columnNumber;

		int offX = (column - 1) * (boxWidth + 1);

		createBox(offX, (int) (startMin / MINUTES_PER_PIXEL), boxWidth,
				(int) (duration / MINUTES_PER_PIXEL), event.color());
	}

	/**
	 * Zeichnet die durch createBoxFromEvent(event,i,n) oder createBox()
	 * erstellten Boxen
	 */
	public void paint(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		g.drawLine(OFF_X + OFF_XLine / 2, 0, OFF_X + OFF_XLine / 2,
				this.getHeight());

		for (tBox box : _box) {
			g.setColor(box.color());
			g.fillRect(box.x() + OFF_X + OFF_XLine, box.y() + OFF_Y,
					box.width(), box.height());
		}
	}
}
