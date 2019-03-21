package httprequest;

import httprequest.UrlSpec;

public class MulThread implements Runnable{
	UrlSpec urlO = new UrlSpec();
	
	public MulThread(UrlSpec urlO) {
		this.urlO=urlO;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		urlO.ping();
	}

}
