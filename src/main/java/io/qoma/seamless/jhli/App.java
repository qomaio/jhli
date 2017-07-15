package io.qoma.seamless.jhli;

import static io.qoma.seamless.jhli.JHLI.*;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		int[] status = { 0 };
		float[] ver = { 0 };

		// Initialize the C HLI
		cfmini(status);
		System.out.printf("--\n  cfmini(status) status = %d\n", status[0]);
		if (status[0] != HSUCC)
			return;

		// get machine info
		String vm = String.format("%s %s", System.getProperty("java.runtime.name"),
				System.getProperty("java.runtime.version"));

		// Get the C HLI version number
		cfmver(status, ver);
		System.out.printf("  cfmver(status,ver) status = %d, ver = %.5f\n\n", status[0], ver[0]);
		if (status[0] != HSUCC)
			return;

		// say HELLO !
		System.out.printf("\tFIS MarketMap Analytic Platform\n" + "\tHello World from Java!\n");
		System.out.printf("\tFAME HLI %.5f\n\t%s\n\n", ver[0], vm);

		// Finish the C HLI
		cfmfin(status);
		System.out.printf("  cfmfin(status) status = %d\n--\n", status[0]);
		if (status[0] != HSUCC)
			return;
	}
}
