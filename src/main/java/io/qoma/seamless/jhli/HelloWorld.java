package io.qoma.seamless.jhli;

import static io.qoma.seamless.jhli.JHLI.*;

public class HelloWorld {

	public static void main(String[] args) {
		int[] status = { 0 };
		float[] ver = { 0 };

		// Initialize the C HLI
		cfmini(status);
		System.out.printf("cfmini(status)\n\tstatus = %d\n\n", status[0]);

		// Get the C HLI version number
		cfmver(status, ver);
		System.out.printf("cfmver(status,ver)\n\tstatus = %d, ver = %.5f\n\n",
				status[0], ver[0]);

		// say HELLO !
		System.out.printf("FIS MarketMap Analytic Platform / C Toolkit\n"
				+ "\tHello World from Java!\n\n");

		// Finish the C HLI
		cfmfin(status);
		System.out.printf("cfmfin(status)\n\tstatus = %d\n\n", status[0]);
		
		System.out.printf("Its me, %s\n",HelloWorld.class.getName());
	}

}
