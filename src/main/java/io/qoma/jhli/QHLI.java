package io.qoma.jhli;

import static io.qoma.jhli.JHLI.*;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QHLI {
	private static final Long PRIME = (long) 9973;
	private static String qoma_support_URL;

	// qoma initialization hook
	public static void cfmini(int[] status) {
		status[0] = -1;
		CHLI.cfmini(IntBuffer.wrap(status));
		format_support_URL(status);
	}

	// qoma finalization hook
	public static void cfmfin(int[] status) {
		status[0] = -1;
		CHLI.cfmfin(IntBuffer.wrap(status));
	}

	private static void format_support_URL(int[] status) {
		int[] rc = { -1 };
		if (status[0] == 0) {
			String expiry_str = get_expiry_string();
			int days = get_days_to_expiry();
			int expiry_date = get_expiry_date();
			String nameid = get_nameid();
			Long client_id = hash(expiry_date, nameid);
			
			qoma_support_URL = String.format("https://qoma-hli-pin.appspot.com/?@clientid=%s&@expiry=%s&@days=%d\n",
					Long.toUnsignedString(client_id, 16), expiry_str, days);

			String qoma_pin = System.getenv("QOMA_PIN");
			if (qoma_pin == null) {
				status[0] = HBPROD;
			} else {
				status[0] = check_pin(expiry_date, nameid, Integer.parseInt(qoma_pin));
			}
			if (status[0] != HSUCC) {
				cfmfin(rc);
				System.out.printf("\nObtain or update your QOMA_PIN:\n\t%s\n\n", qoma_support_URL);
			}
		} else {
			qoma_support_URL = "";
		}
	}

	private static int check_pin(int expiry, String nameid, int pin) {
		Long hash_val = hash(expiry, nameid);
		int wanted = (int) Long.remainderUnsigned(hash_val, PRIME);

		return (wanted == pin) ? HSUCC : // hli success
				HBPROD; // hli unauthorized product
	}

	private static long hash(int expiry_date, String nameid) {
		long hash = (long) expiry_date;

		for (int i = 0; i < nameid.length(); i++) {
			char c = nameid.charAt(i);
			hash = ((hash << 5) + hash) + c;
		}

		return hash;
	}

	private static String get_nameid() {
		String FAME = System.getenv("FAME");
		String filename = String.format("%s/nameid.txt", FAME);
		try {
			return new String(Files.readAllBytes(Paths.get(filename)));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String get_expiry_string() {
		FAME_String datstr = new FAME_String(80);
		int[] status = { -1 };
		int expiry = get_expiry_date();
		cfmdati(status, HDAILY, expiry, datstr, new FAME_String("<year><mz><dz>"), HDEC, HFYAUT);

		return datstr.toString();
	}

	private static int get_expiry_date() {
		int[] status = { -1 }, expiry = { -1 };
		cfmexpiration(status, expiry);
		return expiry[0];
	}

	private static int get_days_to_expiry() {
		int[] status = { -1 }, expiry = { -1 }, now = { -1 };
		cfmtody(status, HDAILY, now);
		cfmexpiration(status, expiry);
		return get_expiry_date() - now[0];
	}

}
