package httprequest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class UrlSpec  {
	String url;
	int sttCode,flag=0;
	long time;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getSttcode() {
		return sttCode;
	}
	public void setSttcode(int sttCode) {
		this.sttCode = sttCode;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Runnable ping() {
		FileHandler fh;
		Logger logger = Logger.getLogger("LogError");
		long dt;
		long begin=System.currentTimeMillis();
		
		while(true){
			try {
				
				//String surl=urlS.getUrl();
				URL url1=new URL(url);
				HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
				connection.setRequestMethod("GET");	
				connection.connect();
				
				
				int code=connection.getResponseCode();
				setSttcode(code);
				
				long end=System.currentTimeMillis();
				long time=getTime();
				
				if(time==0) {
					 dt =end-begin;
				}else {
					 dt = end-time;
				}
				
				setTime(end);
				
				int tkFlag =getFlag();
				if(code<500) {
					tkFlag=0;
				}else {
					tkFlag++;
				}
				setFlag(tkFlag);
				
				System.out.println(url+"\t\t---STATUS CODE: "+code+" |Times:"+dt+" |Flag:" +tkFlag+" |Thread: "+Thread.currentThread().getName());
				if(tkFlag>=2) {
					System.out.println("\t\t\t ****ERROR****");
					fh = new FileHandler("LogError.log", true);
					logger.addHandler(fh);
					SimpleFormatter sf = new SimpleFormatter();
					fh.setFormatter(sf);
					logger.setUseParentHandlers(false);
					logger.info(url + " | liên tiếp 3 lần request lỗi");
					fh.close();
				}

				if(code>=200 && code<=299) {
					//Thread.sleep(3000);
					TimeUnit.SECONDS.sleep(3);
				}else if(code>=300 && code<=499) {
					//Thread.sleep(5000);
					TimeUnit.SECONDS.sleep(5);
				}else if(code>=500 && code<=599) {
					//Thread.sleep(10000);
					TimeUnit.SECONDS.sleep(10);
				}	
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
