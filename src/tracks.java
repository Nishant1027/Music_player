package music;

import javafx.scene.media.MediaPlayer;

public class tracks {

	private String Track;
	
  public tracks(String string)
	{
		this.Track = string;
	}
	
  public String getTrack()
	{
		return Track;
	}
	
  public void setTrack(String tr)
	{
		this.Track = tr;
	}

}
