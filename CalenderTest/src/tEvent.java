import java.awt.Color;

public class tEvent implements Comparable<tEvent>{

	private tTime _startTime;
	private tTime _endTime;
	private String _titel;
	private Color _color;
	
	public tEvent(tTime startTime, tTime endTime, String titel, Color color) {
		this._startTime = startTime;
		this._endTime = endTime;
		this._titel = titel;
		this._color = color;
	}

	public tTime startTime() {
		return _startTime;
	}

	public tTime endTime() {
		return _endTime;
	}

	public String titel() {
		return _titel;
	}

	public Color color() {
		return _color;
	}

	public void setStartTime(tTime startTime) {
		this._startTime = startTime;
	}

	public void setEndTime(tTime endTime) {
		this._endTime = endTime;
	}

	public void setTitel(String titel) {
		this._titel = titel;
	}

	public void setColor(Color color) {
		this._color = color;
	}
	
	@Override
	/**
	 * Sortiert die Events nach Startzeitpunkt
	 */
	public int compareTo(tEvent arg0)
	{
		if (arg0 == null) {
			return 1;
		}
		
		return _startTime.compareTo(arg0.startTime());
	}
}
