package music;

public class myexception extends Exception{

	private int status;
	private String str11;
		   myexception(String str2,int status) {
			   this.status=status;
			   str11=str2;
		   }
		   
		   
		   public String toString()
		   {
			   if(status==0) 
				   return(str11+ " is already present ");
			   else 
				   return(str11);
		   }
		   
}

