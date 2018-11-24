package music;
import java.util.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;





class getall
{
	private String dir2="";
	private String file2;
	
	getall(String name)
	{
		String[] allinfo= name.split("/");
    	this.file2=allinfo[(allinfo.length)-1];
    	for(int i=0;i<allinfo.length-1;i++)
    	{
    		this.dir2+=(""+allinfo[i]+"/");
    	}
        
	}
	
	
	public String getdir2()
	{
		return dir2;
	}
	public String getfile2()
	{
		return file2;
	}
}
public class music extends Application {
	Slider volume ;
	 
	 final Label currentlyPlaying = new Label();
	  final ProgressBar progress = new ProgressBar();
	  private ChangeListener<Duration> progressChangeListener;
	  MediaView mediaView ;
    public void start(Stage primaryStage)  {
    	
    	 TableView<tracks> tracktable = new TableView<tracks>();
    	 tracktable.setStyle("-fx-background: #7CAFC2;");
    	TableColumn<tracks, String> trackcol  = new TableColumn<tracks, String>("Track");
    	 tracktable.getColumns().addAll(trackcol);
    	 trackcol.setCellValueFactory(new PropertyValueFactory<>("Track"));
    	 ObservableList<tracks> tlist = FXCollections.observableArrayList();
    	 BorderPane tbp = new BorderPane();
    	 tbp.setPrefSize(500,450);              //for initial windo
    	 tbp.setCenter(tracktable);
    	 Scene tablescene = new Scene(tbp);
    	 
    	
    	 ImageView play_image=new ImageView("pause_button1.jpg");
         play_image.setFitWidth(30);
         play_image.setFitHeight(30);           //for play button

         ImageView pause_image=new ImageView("play_button1.jpg");
         pause_image.setFitWidth(30);
         pause_image.setFitHeight(30);           //for pause button

         ImageView ff_image=new ImageView("fast_forward_button1.jpg");
         ff_image.setFitWidth(30);
         ff_image.setFitHeight(30);           //for fast forward button

         ImageView fb_image=new ImageView("fast_backward_button1.jpg");
         fb_image.setFitWidth(30);
         fb_image.setFitHeight(30);           //for fast backward button

         ImageView shuffle_image=new ImageView("shuffle_button1.jpg");
         shuffle_image.setFitWidth((30));
         shuffle_image.setFitHeight(30);       //for shuffle button

        

         ImageView menu_image=new ImageView("menu_button.jpg");
         menu_image.setFitWidth(30);
         menu_image.setFitHeight(30);         //for menu button

         ImageView prev_image=new ImageView("prev_button.jpg");
         prev_image.setFitWidth(30);
         prev_image.setFitHeight(30);         //for prev button

         ImageView next_image=new ImageView("next_button1.jpg");
         next_image.setFitWidth(30);
         next_image.setFitHeight(30);         //for next button

    	 Button shuffle_button = new Button();
         shuffle_button.setGraphic(shuffle_image);

         Button fast_backward_button = new Button();
         fast_backward_button.setGraphic(fb_image);

         Button play_button = new Button();
         play_button.setGraphic(play_image);

         Button fast_forward_button=new Button();
         fast_forward_button.setGraphic(ff_image);

       
         Button menu_button = new Button();
         menu_button.setGraphic(menu_image);

         Button prev_button = new Button();
         prev_button.setGraphic(prev_image);

         Button next_button=new Button();
         next_button.setGraphic(next_image);
         
    	 Stage window;
    	Scene scene,scene2;
    	 BorderPane borderPane1=new BorderPane();
    	 scene2=new Scene(borderPane1,660,180);
   
        window = primaryStage;
        window.setTitle("WelcomeScreen");
        final Button play = new Button("Pause");
        final StackPane layout = new StackPane();
        
       
        final List<String> params = getParameters().getRaw();
        final File dir = (params.size() > 0)
          ? new File(params.get(0))
          : new File("/home/sudhir/Desktop/music/");
        // create some media players.
        final List<MediaPlayer> players = new ArrayList<MediaPlayer>();
        for (String file : dir.list(new FilenameFilter() {
        	
           public boolean accept(File dir, String name) {
            return name.endsWith(".mp3");
           
          }
        })) players.add(createPlayer("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
      
       
    
        for(int i=0;i<players.size();i++)
        {
        	 String source = players.get(i).getMedia().getSource();
             source = source.substring(0, source.length() - ".mp3".length());
             source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
          tlist.add(new tracks(source));	
        }
       
         mediaView = new MediaView(players.get(0));
       
        for (int i = 0; i < players.size(); i++) {
            final MediaPlayer player     = players.get(i);
            final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
            player.setOnEndOfMedia(new Runnable() {
               public void run() {
                player.currentTimeProperty().removeListener(progressChangeListener);
                mediaView.setMediaPlayer(nextPlayer);
                nextPlayer.play();
              }
            });
          }
        
        
        Button edit_Button=new Button("EDIT");
        edit_Button.setPrefHeight(450);
        edit_Button.setPrefWidth(75);

        Button start_Button=new Button("START");
        start_Button.setPrefHeight(450);
        start_Button.setPrefWidth(75);

        Button close_Button=new Button("CLOSE");
        close_Button.setPrefWidth(500);
      

        TextField center=new TextField("Center");
        Label toptext=new Label("MUSIC PLAYER");
        toptext.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,20));

        BorderPane.setAlignment(toptext, Pos.TOP_CENTER);
        BorderPane.setAlignment(close_Button, Pos.BOTTOM_CENTER);
        /*BorderPane.setAlignment(centertext, Pos.CENTER );*/
        BorderPane.setAlignment(edit_Button, Pos.CENTER_LEFT);
        BorderPane.setAlignment(start_Button, Pos.CENTER_RIGHT);    //for alignment of buttons

        BorderPane leftborderPane=new BorderPane();
        leftborderPane.setLeft(edit_Button);             //for student button

        BorderPane rightborderPane=new BorderPane();
        rightborderPane.setRight(start_Button);        //for instructor button

        BorderPane bottomborderPane=new BorderPane();
        bottomborderPane.setRight(close_Button);            //for close button

        BorderPane s=new BorderPane();
        BorderPane borderPane=new BorderPane();
        borderPane.setRight(rightborderPane);
        borderPane.setLeft(leftborderPane);
        borderPane.setBottom(close_Button);
        borderPane.setTop(toptext);
        borderPane.setCenter(s);
        borderPane.setPrefSize(500,450);              //for initial window


        s.setStyle("-fx-background-color:linear-gradient(#373B44,#4286f4 )");
       // s.setStyle("-fx-background-image: url("background_image.jpg")");
       

        scene=new Scene(borderPane);
        window.setScene(scene);
        window.show();
   
        close_Button.setOnAction(new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent event) {
            	 window.close();
            }
        });
        
       
        next_button.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent actionEvent) {
              final MediaPlayer curPlayer = mediaView.getMediaPlayer();
              MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
              mediaView.setMediaPlayer(nextPlayer);
              curPlayer.currentTimeProperty().removeListener(progressChangeListener);
              curPlayer.stop();
              nextPlayer.play();
              play_button.setGraphic(play_image);
            }
          });
        prev_button.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent actionEvent) {
              final MediaPlayer curPlayer = mediaView.getMediaPlayer();
              int tn = players.indexOf(curPlayer) - 1;
              if(tn<0)
            	  tn = tn*-1;
              MediaPlayer nextPlayer = players.get((tn) % players.size());
              mediaView.setMediaPlayer(nextPlayer);
              curPlayer.currentTimeProperty().removeListener(progressChangeListener);
              curPlayer.stop();
              nextPlayer.play();
              play_button.setGraphic(play_image);
            }
          });
        shuffle_button.setOnAction(new EventHandler<ActionEvent>() {
                 public void handle(ActionEvent actionEvent) {
            	Random rand = new Random();
            	 MediaPlayer nextPlayer;
            	int n = rand.nextInt( players.size()-1) + 0;
              final MediaPlayer curPlayer = mediaView.getMediaPlayer();
              int tn = players.indexOf(curPlayer) - n;
              if(tn<0)
            	  tn = tn*-1;
            	  
               nextPlayer = players.get((tn) % players.size());
              mediaView.setMediaPlayer(nextPlayer);
              curPlayer.currentTimeProperty().removeListener(progressChangeListener);
              curPlayer.stop();
              nextPlayer.play();
              play_button.setGraphic(play_image);
            }
          });

        play_button.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent actionEvent) {
            	 MediaPlayer curPlayer = mediaView.getMediaPlayer();
            	 Status status = curPlayer.getStatus();
            	 if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {

            		 curPlayer.play();
            		 play_button.setGraphic(play_image);
            		 
            			 } else {
            				 curPlayer.pause();
            				 play_button.setGraphic(pause_image);
            			 }
            			 }         
             });
        menu_button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
            	 tracktable.setItems(tlist);
            	 window.setScene(tablescene);
            	
            }
        });
        
  
            	final FileChooser fileChooser = new FileChooser();
            	 
              
         //   }
        //});
        
        
        fast_forward_button.setOnAction((ActionEvent e) -> {
        	  MediaPlayer curPlayer = mediaView.getMediaPlayer();
        	 curPlayer.seek(curPlayer.getCurrentTime().multiply(1.5));
        	});
        fast_backward_button.setOnAction((ActionEvent e) -> {
            MediaPlayer curPlayer = mediaView.getMediaPlayer();
        	curPlayer.seek(curPlayer.getCurrentTime().divide(1.5));
        	});
        mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
             public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
              setCurrentlyPlaying(newPlayer);
            }
          });
        tracktable.setRowFactory(tv -> {
            TableRow<tracks> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()&& event.getClickCount() == 2) {
                	 MediaPlayer nextPlayer,curPlayer= mediaView.getMediaPlayer();
                	tracks track = row.getItem();
                	 for(int y=0;y<players.size();y++)
                     {
                     	 String source = players.get(y).getMedia().getSource();
                          source = source.substring(0, source.length() - ".mp3".length());
                          source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
                      if(source.equals(track.getTrack())) {
                     	 nextPlayer = players.get(y);
                     	 mediaView.setMediaPlayer(nextPlayer);
                          curPlayer.currentTimeProperty().removeListener(progressChangeListener);
                          curPlayer.stop();
                          nextPlayer.play();
                          window.setScene(scene2);
                          play_button.setGraphic(play_image);
                      }
                     }
                }
            });
            return row ;
        });
                                              
        
       
      
        Button invisiblePause = new Button("Pause");
        invisiblePause.setVisible(false);
        play.prefHeightProperty().bind(invisiblePause.heightProperty());
        play.prefWidthProperty().bind(invisiblePause.widthProperty());
        
        layout.setStyle("-fx-background-color:  #7CAFC2;; -fx-font-size: 10; -fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(
          invisiblePause,
          VBoxBuilder.create().spacing(10).children(
            currentlyPlaying,
            HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children( progress, mediaView).build()
          ).build()
        );
        progress.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progress, Priority.ALWAYS);

       
        TextField txf=new TextField();
        borderPane1.setPrefSize(650,175);
        Button back_button=new Button("Back");
        progress.setStyle("-fx-background: RED");
        borderPane1.setStyle("-fx-background: #7CAFC2;");
    
        
     
       
        HBox sldhb = new HBox();
        HBox bthb = new HBox();
        bthb.setPrefHeight(50);
        HBox bthb1 = new HBox();
        bthb1.setPrefHeight(50);
        HBox bthb2 = new HBox();
        bthb2.setPrefHeight(50);
        HBox bthb3 = new HBox();
        bthb3.setPrefHeight(50);
        bthb3.getChildren().add(shuffle_button);
        bthb3.getChildren().add(prev_button);
        bthb3.getChildren().add(next_button);
        bthb.getChildren().add(fast_backward_button);
        bthb.getChildren().add(play_button);
        bthb.getChildren().add(fast_forward_button);
        bthb1.getChildren().add(bthb3);
        bthb1.getChildren().add(bthb);
        bthb1.getChildren().add(bthb2);
        bthb2.getChildren().add(menu_button);
       
        shuffle_button.setStyle("-fx-background-color:  #7CAFC2;");
        fast_backward_button.setStyle("-fx-background-color:  #7CAFC2;");
        play_button.setStyle("-fx-background-color:  #7CAFC2;");
        fast_forward_button.setStyle("-fx-background-color:  #7CAFC2;");
      
        menu_button.setStyle("-fx-background-color:  #7CAFC2;");
        prev_button.setStyle("-fx-background-color:  #7CAFC2;");
        next_button.setStyle("-fx-background-color:  #7CAFC2;");
        shuffle_button.setPrefSize(30, 30);
        fast_backward_button.setPrefSize(30, 30);
        play_button.setPrefSize(30, 30);
        fast_forward_button.setPrefSize(30, 30);
      
        menu_button.setPrefSize(30, 30);
        prev_button.setPrefSize(30, 30);
        next_button.setPrefSize(30, 30);
 
       
        bthb2.setAlignment(Pos.BOTTOM_CENTER);
        bthb.setAlignment(Pos.BOTTOM_CENTER);
        bthb3.setAlignment(Pos.BOTTOM_CENTER);
        BorderPane back=new BorderPane();
        back.setLeft(back_button);
        bthb1.setSpacing(165);

        borderPane1.setLeft(back_button);
        borderPane1.setBottom(bthb1);
        borderPane1.setCenter(layout);
        borderPane1.setRight(volume);

       
       
       
        
        
       start_Button.setOnAction(new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent event) {
            	 window.setScene(scene2);
                  mediaView.getMediaPlayer().play();
                  setCurrentlyPlaying(mediaView.getMediaPlayer());
            }
        });
       back_button.setOnAction(new EventHandler<ActionEvent>() {
           
           public void handle(ActionEvent event) {
           	 window.setScene(scene);
           	 MediaPlayer curPlayer = mediaView.getMediaPlayer();
                 mediaView.getMediaPlayer().pause();
               
           }
       });
      
       edit_Button.setOnAction(
               new EventHandler<ActionEvent>() {
                
                   public void handle(final ActionEvent e) {
                	   window.setScene(scene2);
                       File file = fileChooser.showOpenDialog(window);
                       if (file != null) {
                       
                       	String name=file.getPath();
                       	getall all=new getall(name);
                       	 MediaPlayer Player1;
                       	int y = 1;
                       	
                       	 final MediaPlayer curPlayer = mediaView.getMediaPlayer();
                       	curPlayer.stop();
                     	 play_button.setGraphic(pause_image);
                       	 try
                       	 {
                       		 
                       		if(!all.getfile2().substring(all.getfile2().length()-3, all.getfile2().length()).equals("mp3"))
                       			{
                       			throw new myexception("Invalid File Type",1);
                       			}
                       	 Player1 = (createPlayer("file:///" + (all.getdir2() + "\\" + all.getfile2()).replace("\\", "/").replaceAll(" ", "%20")));
                       	 
                       		for(int i=0;i<tlist.size();i++)
                          	 {
                          		if(tlist.get(i).getTrack().equals(all.getfile2().substring(0, all.getfile2().length()-4))) 
                          			{y=0;
                          			throw new myexception(all.getfile2().substring(0, all.getfile2().length()-4),0);
                          			}
                          		  
                          	 }
                       	if(y==1) {	
                       	 players.add(Player1);
                       	 borderPane1.setPrefSize(650,175);
                       	
                    
                       	 tlist.add(new tracks(all.getfile2().substring(0, all.getfile2().length()-4)));
                       	
                            MediaPlayer nextPlayer = Player1;
                            mediaView.setMediaPlayer(nextPlayer);
                            curPlayer.currentTimeProperty().removeListener(progressChangeListener);
                            curPlayer.stop();
                            nextPlayer.play();
                            
                           
                       		  play_button.setGraphic(play_image);
                       	}
                       	 }
                       	 catch (myexception exp)
                       	
                       	{
                       		Alert alert = new Alert(AlertType.ERROR, exp + "" , ButtonType.OK);
                       		alert.showAndWait();

                       		if (alert.getResult() == ButtonType.OK) {
                       			curPlayer.play();
                             	 play_button.setGraphic(play_image);
                       		}
                       		
                       	 
                       	}
                       	 
                       }
                   }
               });
    }
   
    private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
        progress.setProgress(0);
        progressChangeListener = new ChangeListener<Duration>() {
           public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
            progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
          }
        };
        newPlayer.currentTimeProperty().addListener(progressChangeListener);

        String source = newPlayer.getMedia().getSource();
        source = source.substring(0, source.length() - ".mp3".length());
        source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
        currentlyPlaying.setText("Now Playing: " + source);
      }
    private MediaPlayer createPlayer(String asd) {
    	
        final MediaPlayer player = new MediaPlayer(new Media(asd));
        player.setOnError(new Runnable() {
           public void run() {
            System.out.println("Media error occurred: " + player.getError());
          }
        });
        return player;
      }
   
 
    public static void main(String[] args) {
        launch(args);
    }


}