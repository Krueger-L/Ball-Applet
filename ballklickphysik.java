	//*************Mainklasse****************
	// Auffangen des Mausereignisses mouseDown in Mainklasse
	public boolean mouseDown (Event e, int x, int y){
		// Test ob Ball getroffen wurde
		if ((ball.getroffen(x, y))==true){
			//Kraefte berechnen
	       	ball.beschleunigen(x, y);
	    }
		return true;
	}
	
	//************Ballklasse***************
	//pos_x, pos_y, x_speed, y_speed : Zustandsvariablen
	//ballradius, klickflaeche, kraftProKlick: gesetzte Variablen
	public boolean getroffen(int maus_x, int maus_y){
		// Bestimmen der Verbindungsvektoren
		double x = maus_x - pos_x;
		double y = maus_y - pos_y;

		// Berechnen der Distanz (Skalarprodukt)
		double distance = Math.sqrt ((x*x) + (y*y));

		// Wenn Distanz kleiner/gleich Ballradius && Klick in der Klickflaeche erfolgt, gilt der Ball als getroffen
		if ((distance <= ballradius) && (maus_y <= klickflaeche)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private void beschleunigen(int maus_x, int maus_y){
		// Bestimmen der Abst�nde
		int x = maus_x - pos_x;
		int y = maus_y - pos_y;
		int x_positiv = x;
		int y_positiv = y;
		//Positive Werte bilden
		if (x_positiv<0){
			x_positiv = x_positiv*-1;
		}
		if (y_positiv<0){
			y_positiv = y_positiv*-1;
		}
		//Anteile der Kraft auf beide Achsen bestimmen
		int kraftAnteile = x_positiv+y_positiv;
		int x_kraft = (kraftProKlick/kraftAnteile)*x_positiv;
		int y_kraft = (kraftProKlick/kraftAnteile)*y_positiv;
		//Kraefte den x und y Geschwindigkeiten des Balles anrechnen 
		//Klick unten links
		if(x<=0 && y<=0){
			x_speed = x_speed + x_kraft;
			y_speed = y_speed + y_kraft;
		}
		//Klick unten rechts
		else if(x>=0 && y<=0){
			x_speed = x_speed - x_kraft;
			y_speed = y_speed + y_kraft;
		}
		//Klick oben rechts
		else if(x>=0 && y>=0){
			x_speed = x_speed - x_kraft;
			y_speed = y_speed - y_kraft;
		}
		//Klick oben links
		else{
			x_speed = x_speed + x_kraft;
			y_speed = y_speed - y_kraft;
		}
	}
		