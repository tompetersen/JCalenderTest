import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class StartCalender
{
	private CalenderUI _ui;
	private List<tEvent> _eventList;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		StartCalender calender = new StartCalender();
	}
	
	public StartCalender() {
		_eventList = new LinkedList<tEvent>();
		_ui = new CalenderUI();
		
		createRandomEvents(20);
		
		createGreatCalender();
	}

	/**
	 * Belegt das Feld _eventList mit mehr oder weniger zufälligen Events
	 * @param n
	 */
	private void createRandomEvents(int n)
	{
		_eventList.clear();
		
		Random rdGen = new Random();
		
		for (int i = 0; i < n; ++i) {
			tTime start = new tTime(rdGen.nextInt(2)+i, rdGen.nextInt(60));
			tTime end = new tTime(rdGen.nextInt(3)+i, rdGen.nextInt(60));
//			
//			tTime start = new tTime(rdGen.nextInt(24), rdGen.nextInt(60));
//			tTime end = new tTime(rdGen.nextInt(24), rdGen.nextInt(60));
//			
			tEvent newEvent;
			if (start.compareTo(end) < 0) {
				newEvent = new tEvent(start,end,"",new Color(255 - i*10, 255 - i*10, 0));
			}
			else {
				newEvent = new tEvent(end,start,"",new Color(255 - i*10, 255 - i*10, 0));
			}
			_eventList.add(newEvent);
		}
		
		Collections.sort(_eventList);
		
		for (int i = 0; i < n; ++i) {
			_eventList.get(i).setTitel("Titel " + i);
		}
	}
	
	private void createGreatCalender()
	{
		tTime nullTime = new tTime(0,0); 
		tTime ep = nullTime; //Max. Endzeitpunkt zur Bestimmunng der sich überschneidenden Events
		List<tEvent> overlappingEvents = new LinkedList<tEvent>(); //Liste mit sich überschneidenden Events
		
		for (int i = 0; i < _eventList.size(); ++i) {
			
			tEvent currentEvent = _eventList.get(i);
			if ((overlappingEvents.size() == 0) || (currentEvent.startTime().compareTo(ep) == -1)) //Überschneidung
			{
				overlappingEvents.add(currentEvent);
				if (currentEvent.endTime().compareTo(ep) == 1) {
					ep = currentEvent.endTime();
				}
			}
			else { //Keine Überschneidung, bearbeite bis jetzt eingefügte Events
				
//				for (tEvent event : overlappingEvents) {
//					System.out.println(event.titel() + " " + event.startTime().toString() + " - " + event.endTime().toString());
//				}
//				System.out.println();
				
				drawOverlappingEvents(overlappingEvents);
				
				//"Aufräumen" und neue Menge erstellen
				overlappingEvents.clear();
				overlappingEvents.add(currentEvent);
				ep = currentEvent.endTime();
			}
			
			//Auch die letzten Events zeichnen
			if (i == _eventList.size() - 1) {
				drawOverlappingEvents(overlappingEvents);
				
//				for (tEvent event : overlappingEvents) {
		//			System.out.println(event.titel() + " " + event.startTime().toString() + " - " + event.endTime().toString());
		//		}
		//		System.out.println();
			}
		}
	}
	
	private void drawOverlappingEvents(List<tEvent> overlappingEvents) 
	{
		//Zeichnen pro überschneidender Menge
		List<tTime> endTimesPerColumn = new LinkedList<tTime>(); //Liste, die die aktuellen Endzeitpunkte pro Spalte enthält
		List<Integer> columnForEvent = new ArrayList<Integer>(); //Liste, die für jedes Event die berechnete Spalte enthält
		
		//Durchlaufe die Liste und finde für jedes Event die passende Spalte
		for (int j = 0; j < overlappingEvents.size(); ++j) {
			tEvent currEv = overlappingEvents.get(j); //Das zu zeichnende Event
			int column = -1; // Spalte für das betrachtete Event
			
			//Durchlaufe bestehende Spalten und suche nach passendem Platz
			for(int k = 0; k < endTimesPerColumn.size(); ++k) {
				//Falls Event in die Spalte "passt"
				if(endTimesPerColumn.get(k).compareTo(currEv.startTime()) < 1) {
					column = k;
					endTimesPerColumn.remove(k);
					endTimesPerColumn.add(k, currEv.endTime());
					break;
				}
			}
			
			//Wenn kein Platz gefunden wurde erstelle neue Spalte
			if (column == -1) {
				column = endTimesPerColumn.size();
				endTimesPerColumn.add(currEv.endTime());
			}
			
			columnForEvent.add(column+1);
		}
		
		//Zeichne Events  
		for (int j = 0; j < overlappingEvents.size(); ++j) {
			_ui.createBoxFromEvent(overlappingEvents.get(j), columnForEvent.get(j), endTimesPerColumn.size());
		}
	}

}
