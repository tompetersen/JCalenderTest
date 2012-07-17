public class tTime implements Comparable<tTime>
{
	private int _hour, _minute;

	/**
	 * 
	 * @param hour
	 * @param minute
	 * 
	 * @require hour >= 0 && hour < 24
	 * @require minute >= 0 && minute < 60
	 */
	public tTime(int hour, int minute)
	{
		assert hour >= 0 && hour < 24 : "Vorbedingung verletzt: hour >= 0 && hour < 24";
		assert minute >= 0 && minute < 60 : "Vorbedingung verletzt: minute >=0 && minute < 60";

		_hour = hour;
		_minute = minute;
	}

	public int hour()
	{
		return _hour;
	}

	public int minute()
	{
		return _minute;
	}

	public void setHour(int hour)
	{
		this._hour = hour;
	}

	public void setMinute(int minute)
	{
		this._minute = minute;
	}

	@Override
	/**
	 * Gibt 1 zurück, falls der Zeitpunkt später als das Argument ist
	 * Gibt -1 zurück, falls der Zeitpunkt vor dem Argument liegt
	 * Gibt 0 zurück, falls die Zeitpunkte gleichzeitig liegen
	 */
	public int compareTo(tTime arg0)
	{
		if (arg0 == null) {
			return 1;
		}
		
		int thisMinutes = _hour * 60 + _minute;
		int objMinutes = arg0._hour * 60 + arg0._minute;
		
		if (thisMinutes > objMinutes) {
			return 1;
		}
		else if(objMinutes > thisMinutes) {
			return -1;
		}
		else {
			return 0;
		}
	}

	@Override
	public String toString()
	{
		String result;
		if (_minute < 10) {
			result = _hour + ":0" + _minute;
		}
		else {
			result =  _hour + ":" + _minute;
		}
		return result;
	}
	
	
}
