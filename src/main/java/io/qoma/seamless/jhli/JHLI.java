package io.qoma.seamless.jhli;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public class JHLI {
	
	public static float 		FNUMND, 		FNUMNA, 		FNUMNC;
	public static double 		FPRCND, 		FPRCNA,			FPRCNC;
	public static int 			FBOOND, 		FBOONA, 		FBOONC;
	public static int 			FDATND, 		FDATNA, 		FDATNC;
	public static long 			FAME_INDEX_ND,	FAME_INDEX_NA,	FAME_INDEX_NC;
	public static FAME_String	FSTRND,			FSTRNA, 		FSTRNC;

	private static void setGlobals() {
		FNUMNC = CHLI.FNUMNC;
		FNUMNA = CHLI.FNUMNA;
		FNUMND = CHLI.FNUMND;
		FPRCNC = CHLI.FPRCNC;
		FPRCNA = CHLI.FPRCNA;
		FPRCND = CHLI.FPRCND;
		FBOONC = CHLI.FBOONC;
		FBOONA = CHLI.FBOONA;
		FBOOND = CHLI.FBOOND;
		FDATNC = CHLI.FDATNC;
		FDATNA = CHLI.FDATNA;
		FDATND = CHLI.FDATND;
		FAME_INDEX_NC = CHLI.FAME_INDEX_NC;
		FAME_INDEX_NA = CHLI.FAME_INDEX_NA;
		FAME_INDEX_ND = CHLI.FAME_INDEX_ND;
		FSTRNC = new FAME_String(CHLI.FSTRNC);
		FSTRNA = new FAME_String(CHLI.FSTRNA);
		FSTRND = new FAME_String(CHLI.FSTRND);
	}

	public static final Pointer NULL = Pointer.NULL;
	public static final int pointerSize = com.sun.jna.Native.POINTER_SIZE;
	public static final FAME_String HRMOBJ = new FAME_String("%HRMOBJ");

	public static void cfmwsts(int[] status, int dbkey, FAME_String objnam,
			int[] range, FAME_String[] strary, int[] misary, int[] lenary) {
		PointerByReference pbr_strary = constructStringArrayPointerByReferenceWrite(
				strary);
		CHLI.cfmwsts(IntBuffer.wrap(status), dbkey,
				ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range),
				pbr_strary, IntBuffer.wrap(misary), IntBuffer.wrap(lenary));
		objnam.synchString();
	}

	public static int fame_write_strings(int dbkey, FAME_String objnam,
			long[] range, FAME_String[] strary) {
		PointerByReference pbr_strary = constructStringArrayPointerByReferenceWrite(
				strary);
		int rc = CHLI.fame_write_strings(dbkey,
				ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range),
				pbr_strary);
		objnam.synchString();
		return rc;
	}

	private static PointerByReference constructStringArrayPointerByReferenceWrite(
			FAME_String[] strary) {
		Memory m = new Memory(pointerSize * strary.length);
		PointerByReference pbr = new PointerByReference();
		pbr.setPointer(m);

		byte[] buffer;
		int bufsiz;
		for (int i = 0; i < strary.length; i++) {
			buffer = strary[i].getBytes();
			bufsiz = buffer.length;
			Memory tmpbuf = new Memory(bufsiz);
			tmpbuf.write(0, buffer, 0, bufsiz);
			m.setPointer(i * pointerSize, tmpbuf);
		}

		return pbr;
	}

	public static void cfmgtsts(int[] status, int dbkey, FAME_String objnam,
			int[] range, FAME_String[] strary, int[] misary, int[] inlen,
			int[] outlen) {
		PointerByReference pbr_strary = constructStringArrayPointerByReferenceRead(
				strary, inlen);
		CHLI.cfmgtsts(IntBuffer.wrap(status), dbkey,
				ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range),
				pbr_strary, IntBuffer.wrap(misary), IntBuffer.wrap(inlen),
				IntBuffer.wrap(outlen));
		copyOut(strary, outlen, pbr_strary);
		objnam.synchString();
	}

	public static int fame_get_strings(int dbkey, FAME_String objnam,
			long[] range, FAME_String[] strary, int[] inlen, int[] outlen) {
		PointerByReference pbr_strary = constructStringArrayPointerByReferenceRead(
				strary, inlen);
		int rc = CHLI.fame_get_strings(dbkey,
				ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range),
				pbr_strary, IntBuffer.wrap(inlen), IntBuffer.wrap(outlen));

		copyOut(strary, outlen, pbr_strary);
		objnam.synchString();

		return rc;
	}

	private static PointerByReference constructStringArrayPointerByReferenceRead(
			FAME_String[] strary, int[] lenary) {
		Memory m = new Memory(pointerSize * strary.length);
		PointerByReference pbr = new PointerByReference();
		pbr.setPointer(m);

		for (int i = 0; i < strary.length; i++) {
			m.setPointer(i * pointerSize, new Memory(lenary[i] + 1));
		}

		return pbr;
	}

	public static void copyOut(FAME_String[] strary, int[] outlen,
			PointerByReference pbr_strary) {
		byte[] b;

		for (int i = 0; i < strary.length; i++) {
			b = pbr_strary.getPointer().getPointer(i * pointerSize)
					.getByteArray(0, outlen[i] + 1);
			strary[i] = new FAME_String(b);
		}

	}

	public static void cfmabrt(int []status, int connkey){
		CHLI.cfmabrt(IntBuffer.wrap(status), connkey);
	}
	public static void cfmalob(int []status, int dbkey, FAME_String objnam, int class_, int freq, int type, int basis, int observ, int numobs, int numchr, float growth){
		CHLI.cfmalob(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), class_, freq, type, basis, observ, numobs, numchr, growth);
		objnam.synchString();
	}
	public static void cfmasrt(int []status, int connkey, int assert_type, FAME_String assertion, int perspective, int grouping, int []dblist){
		CHLI.cfmasrt(IntBuffer.wrap(status), connkey, assert_type, ByteBuffer.wrap(assertion.getBytes()), perspective, grouping, IntBuffer.wrap(dblist));
		assertion.synchString();
	}
	public static void cfmbwdy(int []status, int freq, int date, int []biwkdy){
		CHLI.cfmbwdy(IntBuffer.wrap(status), freq, date, IntBuffer.wrap(biwkdy));
	}
	public static void cfmchfr(int []status, int sfreq, int sdate, int select, int tfreq, int []tdate, int relate){
		CHLI.cfmchfr(IntBuffer.wrap(status), sfreq, sdate, select, tfreq, IntBuffer.wrap(tdate), relate);
	}
	public static void cfmclcn(int []status, int connkey){
		CHLI.cfmclcn(IntBuffer.wrap(status), connkey);
	}
	public static void cfmcldb(int []status, int dbkey){
		CHLI.cfmcldb(IntBuffer.wrap(status), dbkey);
	}
	public static void cfmcmmt(int []status, int connkey){
		CHLI.cfmcmmt(IntBuffer.wrap(status), connkey);
	}
	public static void cfmcpob(int []status, int srckey, int tarkey, FAME_String srcnam, FAME_String tarnam){
		CHLI.cfmcpob(IntBuffer.wrap(status), srckey, tarkey, ByteBuffer.wrap(srcnam.getBytes()), ByteBuffer.wrap(tarnam.getBytes()));
		srcnam.synchString();
		tarnam.synchString();
	}
	public static void cfmdatd(int []status, int freq, int date, int []year, int []month, int []day){
		CHLI.cfmdatd(IntBuffer.wrap(status), freq, date, IntBuffer.wrap(year), IntBuffer.wrap(month), IntBuffer.wrap(day));
	}
	public static void cfmdatf(int []status, int freq, int date, int []year, int []period, int fmonth, int flabel){
		CHLI.cfmdatf(IntBuffer.wrap(status), freq, date, IntBuffer.wrap(year), IntBuffer.wrap(period), fmonth, flabel);
	}
	public static void cfmdati(int []status, int freq, int date, FAME_String datstr, FAME_String image, int fmonth, int flabel){
		CHLI.cfmdati(IntBuffer.wrap(status), freq, date, ByteBuffer.wrap(datstr.getBytes()), ByteBuffer.wrap(image.getBytes()), fmonth, flabel);
		datstr.synchString();
		image.synchString();
	}
	public static void cfmdatl(int []status, int freq, int date, FAME_String datstr, int fmonth, int flabel){
		CHLI.cfmdatl(IntBuffer.wrap(status), freq, date, ByteBuffer.wrap(datstr.getBytes()), fmonth, flabel);
		datstr.synchString();
	}
	public static void cfmdatp(int []status, int freq, int date, int []year, int []period){
		CHLI.cfmdatp(IntBuffer.wrap(status), freq, date, IntBuffer.wrap(year), IntBuffer.wrap(period));
	}
	public static void cfmdatt(int []status, int freq, int date, int []hour, int []minute, int []second, int []ddate){
		CHLI.cfmdatt(IntBuffer.wrap(status), freq, date, IntBuffer.wrap(hour), IntBuffer.wrap(minute), IntBuffer.wrap(second), IntBuffer.wrap(ddate));
	}
	public static void cfmddat(int []status, int freq, int []date, int year, int month, int day){
		CHLI.cfmddat(IntBuffer.wrap(status), freq, IntBuffer.wrap(date), year, month, day);
	}
	public static void cfmddes(int []status, int dbkey, FAME_String des){
		CHLI.cfmddes(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(des.getBytes()));
		des.synchString();
	}
	public static void cfmddoc(int []status, int dbkey, FAME_String doc){
		CHLI.cfmddoc(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(doc.getBytes()));
		doc.synchString();
	}
	public static void cfmdlen(int []status, int dbkey, FAME_String objnam, int []deslen, int []doclen){
		CHLI.cfmdlen(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(deslen), IntBuffer.wrap(doclen));
		objnam.synchString();
	}
	public static void cfmdlob(int []status, int dbkey, FAME_String objnam){
		CHLI.cfmdlob(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()));
		objnam.synchString();
	}
	public static void cfmexpiration(int []status, int []date){
		CHLI.cfmexpiration(IntBuffer.wrap(status), IntBuffer.wrap(date));
	}
	public static void cfmfame(int []status, FAME_String cmd){
		CHLI.cfmfame(IntBuffer.wrap(status), ByteBuffer.wrap(cmd.getBytes()));
		cmd.synchString();
	}
	public static void cfmfdat(int []status, int freq, int []date, int year, int period, int fmonth, int flabel){
		CHLI.cfmfdat(IntBuffer.wrap(status), freq, IntBuffer.wrap(date), year, period, fmonth, flabel);
	}
	public static void cfmfdiv(int []status, int freq1, int freq2, int []flag){
		CHLI.cfmfdiv(IntBuffer.wrap(status), freq1, freq2, IntBuffer.wrap(flag));
	}
	public static void cfmferr(int []status, FAME_String errtxt){
		CHLI.cfmferr(IntBuffer.wrap(status), ByteBuffer.wrap(errtxt.getBytes()));
		errtxt.synchString();
	}
	public static void cfmfin(int []status){
		CHLI.cfmfin(IntBuffer.wrap(status));
	}
	public static void cfmgcid(int []status, int dbkey, int []connkey){
		CHLI.cfmgcid(IntBuffer.wrap(status), dbkey, IntBuffer.wrap(connkey));
	}
	public static void cfmgdat(int []status, int dbkey, FAME_String objnam, int freq, int []cdate, int []mdate){
		CHLI.cfmgdat(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), freq, IntBuffer.wrap(cdate), IntBuffer.wrap(mdate));
		objnam.synchString();
	}
	public static void cfmgdba(int []status, int dbkey, int []cyear, int []cmonth, int []cday, int []myear, int []mmonth, int []mday, FAME_String desc, FAME_String doc){
		CHLI.cfmgdba(IntBuffer.wrap(status), dbkey, IntBuffer.wrap(cyear), IntBuffer.wrap(cmonth), IntBuffer.wrap(cday), IntBuffer.wrap(myear), IntBuffer.wrap(mmonth), IntBuffer.wrap(mday), ByteBuffer.wrap(desc.getBytes()), ByteBuffer.wrap(doc.getBytes()));
		desc.synchString();
		doc.synchString();
	}
	public static void cfmgdbd(int []status, int dbkey, int freq, int []cdate, int []mdate){
		CHLI.cfmgdbd(IntBuffer.wrap(status), dbkey, freq, IntBuffer.wrap(cdate), IntBuffer.wrap(mdate));
	}
	public static void cfmget_dimension(int []status, int dbkey, int []dimen){
		CHLI.cfmget_dimension(IntBuffer.wrap(status), dbkey, IntBuffer.wrap(dimen));
	}
	public static void cfmget_extradots(int []status, int dbkey, int []xdots){
		CHLI.cfmget_extradots(IntBuffer.wrap(status), dbkey, IntBuffer.wrap(xdots));
	}
	public static void cfmglen(int []status, int dbkey, int []deslen, int []doclen){
		CHLI.cfmglen(IntBuffer.wrap(status), dbkey, IntBuffer.wrap(deslen), IntBuffer.wrap(doclen));
	}
	public static void cfmgnam(int []status, int dbkey, FAME_String objnam, FAME_String basnam){
		CHLI.cfmgnam(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(basnam.getBytes()));
		objnam.synchString();
		basnam.synchString();
	}
	public static void cfmgsln(int []status, int dbkey, FAME_String objnam, int []length){
		CHLI.cfmgsln(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(length));
		objnam.synchString();
	}
	public static void cfmgtali(int []status, int dbkey, FAME_String objnam, FAME_String aliass, int inlen, int []outlen){
		CHLI.cfmgtali(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(aliass.getBytes()), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		aliass.synchString();
	}
	public static void cfmgtaso(int []status, int dbkey, FAME_String objnam, FAME_String assoc, int inlen, int []outlen){
		CHLI.cfmgtaso(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(assoc.getBytes()), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		assoc.synchString();
	}
	public static void cfmgtatt_char(int []status, int dbkey, FAME_String objnam, int []atttyp, FAME_String attnam, FAME_String value, int inlen, int []outlen){
		CHLI.cfmgtatt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(atttyp), ByteBuffer.wrap(attnam.getBytes()), ByteBuffer.wrap(value.getBytes()), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		attnam.synchString();
		value.synchString();
	}
	public static void cfmgtatt_double(int []status, int dbkey, FAME_String objnam, int []atttyp, FAME_String attnam, double []value, int inlen, int []outlen){
		CHLI.cfmgtatt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(atttyp), ByteBuffer.wrap(attnam.getBytes()), DoubleBuffer.wrap(value), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		attnam.synchString();
	}
	public static void cfmgtatt_float(int []status, int dbkey, FAME_String objnam, int []atttyp, FAME_String attnam, float []value, int inlen, int []outlen){
		CHLI.cfmgtatt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(atttyp), ByteBuffer.wrap(attnam.getBytes()), FloatBuffer.wrap(value), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		attnam.synchString();
	}
	public static void cfmgtatt_int(int []status, int dbkey, FAME_String objnam, int []atttyp, FAME_String attnam, int []value, int inlen, int []outlen){
		CHLI.cfmgtatt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(atttyp), ByteBuffer.wrap(attnam.getBytes()), IntBuffer.wrap(value), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		attnam.synchString();
	}
	public static void cfmgtnl(int []status, int dbkey, FAME_String objnam, int index, FAME_String strval, int inlen, int []outlen){
		CHLI.cfmgtnl(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), index, ByteBuffer.wrap(strval.getBytes()), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		strval.synchString();
	}
	public static void cfmgtstr(int []status, int dbkey, FAME_String objnam, int []range_, FAME_String strval, int []ismiss, int inlen, int []outlen){
		CHLI.cfmgtstr(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range_), ByteBuffer.wrap(strval.getBytes()), IntBuffer.wrap(ismiss), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		strval.synchString();
	}
	public static void cfmidat(int []status, int freq, int []date, FAME_String datstr, FAME_String image, int fmonth, int flabel, int centry){
		CHLI.cfmidat(IntBuffer.wrap(status), freq, IntBuffer.wrap(date), ByteBuffer.wrap(datstr.getBytes()), ByteBuffer.wrap(image.getBytes()), fmonth, flabel, centry);
		datstr.synchString();
		image.synchString();
	}
	public static void cfmini(int[] status) {
		CHLI.cfmini(IntBuffer.wrap(status));
		synchMissing(status);
	}
	public static void cfminwc(int []status, int dbkey, FAME_String wilnam){
		CHLI.cfminwc(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(wilnam.getBytes()));
		wilnam.synchString();
	}
	public static void cfmisbm(int []status, int value, int []ismiss){
		CHLI.cfmisbm(IntBuffer.wrap(status), value, IntBuffer.wrap(ismiss));
	}
	public static void cfmisdm(int []status, int value, int []ismiss){
		CHLI.cfmisdm(IntBuffer.wrap(status), value, IntBuffer.wrap(ismiss));
	}
	public static void cfmislp(int []status, int year, int []leap){
		CHLI.cfmislp(IntBuffer.wrap(status), year, IntBuffer.wrap(leap));
	}
	public static void cfmisnm(int []status, float value, int []ismiss){
		CHLI.cfmisnm(IntBuffer.wrap(status), value, IntBuffer.wrap(ismiss));
	}
	public static void cfmispm(int []status, double value, int []ismiss){
		CHLI.cfmispm(IntBuffer.wrap(status), value, IntBuffer.wrap(ismiss));
	}
	public static void cfmissm(int []status, FAME_String value, int []ismiss){
		CHLI.cfmissm(IntBuffer.wrap(status), ByteBuffer.wrap(value.getBytes()), IntBuffer.wrap(ismiss));
		value.synchString();
	}
	public static void cfmlali(int []status, int dbkey, FAME_String objnam, int []alilen){
		CHLI.cfmlali(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(alilen));
		objnam.synchString();
	}
	public static void cfmlaso(int []status, int dbkey, FAME_String objnam, int []asolen){
		CHLI.cfmlaso(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(asolen));
		objnam.synchString();
	}
	public static void cfmlatt(int []status, int dbkey, FAME_String objnam, int atttyp, FAME_String attnam, int []attlen){
		CHLI.cfmlatt(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), atttyp, ByteBuffer.wrap(attnam.getBytes()), IntBuffer.wrap(attlen));
		objnam.synchString();
		attnam.synchString();
	}
	public static void cfmldat(int []status, int freq, int []date, FAME_String datstr, int fmonth, int flabel, int centry){
		CHLI.cfmldat(IntBuffer.wrap(status), freq, IntBuffer.wrap(date), ByteBuffer.wrap(datstr.getBytes()), fmonth, flabel, centry);
		datstr.synchString();
	}
	public static void cfmlerr(int []status, int []len){
		CHLI.cfmlerr(IntBuffer.wrap(status), IntBuffer.wrap(len));
	}
	public static void cfmlsts(int []status, int dbkey, FAME_String objnam, int []range_, int []lenary){
		CHLI.cfmlsts(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range_), IntBuffer.wrap(lenary));
		objnam.synchString();
	}
	public static void cfmncnt(int []status, int dbkey, FAME_String objnam, int []length){
		CHLI.cfmncnt(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(length));
		objnam.synchString();
	}
	public static void cfmnlen(int []status, int dbkey, FAME_String objnam, int index, int []length){
		CHLI.cfmnlen(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), index, IntBuffer.wrap(length));
		objnam.synchString();
	}
	public static void cfmnwob(int []status, int dbkey, FAME_String objnam, int class_, int freq, int type, int basis, int observ){
		CHLI.cfmnwob(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), class_, freq, type, basis, observ);
		objnam.synchString();
	}
	public static void cfmnxwc(int []status, int dbkey, FAME_String objnam, int []class_, int []type, int []freq){
		CHLI.cfmnxwc(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(class_), IntBuffer.wrap(type), IntBuffer.wrap(freq));
		objnam.synchString();
	}
	public static void cfmopcn(int []status, int []connkey, FAME_String service, FAME_String hostname, FAME_String username, FAME_String password){
		CHLI.cfmopcn(IntBuffer.wrap(status), IntBuffer.wrap(connkey), ByteBuffer.wrap(service.getBytes()), ByteBuffer.wrap(hostname.getBytes()), ByteBuffer.wrap(username.getBytes()), ByteBuffer.wrap(password.getBytes()));
		service.synchString();
		hostname.synchString();
		username.synchString();
		password.synchString();
	}
	public static void cfmqry(int status[]) {
		FAME_String QUERY = new FAME_String(
				"-/result = @license+datefmt(@expiration,\" <year><mz><dz> \")+getenv(\"QOMA_ID\")");
		int[] rc = { -1 }, dbkey = { -1 }, ismiss = { -1 }, inlen = { 256 }, outlen = { -1 }, rng = { 0, 0, 0 };
		FAME_String objnam = new FAME_String("result");
		FAME_String strval = new FAME_String(inlen[0]);

		cfmfame(rc, QUERY);		
		if (rc[0] == HSUCC) {
			cfmopwk(rc, dbkey);
			cfmgtstr(rc, dbkey[0], objnam, rng, strval, ismiss, inlen[0], outlen);
			cfmdlob(status, dbkey[0], objnam);
			cfmcldb(rc, dbkey[0]);
		} else {
			cfmsinp(rc, strval);
		}
		rc[0] = fame_post(strval);
		if (rc[0] != HSUCC) {
			cfmfin(rc);
			status[0] = HBVER;
		}
	}
	public static void cfmopdb(int []status, int []dbkey, FAME_String dbname, int mode){
		CHLI.cfmopdb(IntBuffer.wrap(status), IntBuffer.wrap(dbkey), ByteBuffer.wrap(dbname.getBytes()), mode);
		dbname.synchString();
	}
	public static void cfmopdc(int []status, int []dbkey, FAME_String dbname, int mode, int connkey){
		CHLI.cfmopdc(IntBuffer.wrap(status), IntBuffer.wrap(dbkey), ByteBuffer.wrap(dbname.getBytes()), mode, connkey);
		dbname.synchString();
	}
	public static void cfmoprc(int []status, int []dbkey, int connkey){
		CHLI.cfmoprc(IntBuffer.wrap(status), IntBuffer.wrap(dbkey), connkey);
	}
	public static void cfmopre(int []status, int []dbkey, FAME_String svname){
		CHLI.cfmopre(IntBuffer.wrap(status), IntBuffer.wrap(dbkey), ByteBuffer.wrap(svname.getBytes()));
		svname.synchString();
	}
	public static void cfmopwk(int []status, int []dbkey){
		CHLI.cfmopwk(IntBuffer.wrap(status), IntBuffer.wrap(dbkey));
	}
	public static void cfmosiz(int []status, int dbkey, FAME_String objnam, int []class_, int []type, int []freq, int []fyear, int []fprd, int []lyear, int []lprd){
		CHLI.cfmosiz(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(class_), IntBuffer.wrap(type), IntBuffer.wrap(freq), IntBuffer.wrap(fyear), IntBuffer.wrap(fprd), IntBuffer.wrap(lyear), IntBuffer.wrap(lprd));
		objnam.synchString();
	}
	public static void cfmpack(int []status, int dbkey){
		CHLI.cfmpack(IntBuffer.wrap(status), dbkey);
	}
	public static void cfmpdat(int []status, int freq, int []date, int year, int period){
		CHLI.cfmpdat(IntBuffer.wrap(status), freq, IntBuffer.wrap(date), year, period);
	}
	public static void cfmpfrq(int []status, int []freq, int base, int nunits, int year, int month){
		CHLI.cfmpfrq(IntBuffer.wrap(status), IntBuffer.wrap(freq), base, nunits, year, month);
	}
	public static void cfmpind(int []status, int freq, int []count){
		CHLI.cfmpind(IntBuffer.wrap(status), freq, IntBuffer.wrap(count));
	}
	public static void cfmpinm(int []status, int freq, int year, int month, int []count){
		CHLI.cfmpinm(IntBuffer.wrap(status), freq, year, month, IntBuffer.wrap(count));
	}
	public static void cfmpiny(int []status, int freq, int year, int []count){
		CHLI.cfmpiny(IntBuffer.wrap(status), freq, year, IntBuffer.wrap(count));
	}
	public static void cfmpodb(int []status, int dbkey){
		CHLI.cfmpodb(IntBuffer.wrap(status), dbkey);
	}
	public static void cfmrdfa_double(int []status, int dbkey, FAME_String objnam, int wntobs, int []syear, int []sprd, int []gotobs, double []valary, int tmiss, double []mistt){
		CHLI.cfmrdfa_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), wntobs, IntBuffer.wrap(syear), IntBuffer.wrap(sprd), IntBuffer.wrap(gotobs), DoubleBuffer.wrap(valary), tmiss, DoubleBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmrdfa_float(int []status, int dbkey, FAME_String objnam, int wntobs, int []syear, int []sprd, int []gotobs, float []valary, int tmiss, float []mistt){
		CHLI.cfmrdfa_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), wntobs, IntBuffer.wrap(syear), IntBuffer.wrap(sprd), IntBuffer.wrap(gotobs), FloatBuffer.wrap(valary), tmiss, FloatBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmrdfa_int(int []status, int dbkey, FAME_String objnam, int wntobs, int []syear, int []sprd, int []gotobs, int []valary, int tmiss, int []mistt){
		CHLI.cfmrdfa_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), wntobs, IntBuffer.wrap(syear), IntBuffer.wrap(sprd), IntBuffer.wrap(gotobs), IntBuffer.wrap(valary), tmiss, IntBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmrdfm(int []status, int dbkey, FAME_String objnam, FAME_String source, int ilen, int []olen){
		CHLI.cfmrdfm(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(source.getBytes()), ilen, IntBuffer.wrap(olen));
		objnam.synchString();
		source.synchString();
	}
	public static void cfmrmev(int []status, int dbkey, FAME_String expr, FAME_String optns, int wdbkey, FAME_String objnam){
		CHLI.cfmrmev(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(expr.getBytes()), ByteBuffer.wrap(optns.getBytes()), wdbkey, ByteBuffer.wrap(objnam.getBytes()));
		expr.synchString();
		optns.synchString();
		objnam.synchString();
	}
	public static void cfmrnob(int []status, int dbkey, FAME_String srcnam, FAME_String tarnam){
		CHLI.cfmrnob(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(srcnam.getBytes()), ByteBuffer.wrap(tarnam.getBytes()));
		srcnam.synchString();
		tarnam.synchString();
	}
	public static void cfmrrng_double(int []status, int dbkey, FAME_String objnam, int []range_, double []valary, int tmiss, double []mistt){
		CHLI.cfmrrng_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range_), DoubleBuffer.wrap(valary), tmiss, DoubleBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmrrng_float(int []status, int dbkey, FAME_String objnam, int []range_, float []valary, int tmiss, float []mistt){
		CHLI.cfmrrng_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range_), FloatBuffer.wrap(valary), tmiss, FloatBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmrrng_int(int []status, int dbkey, FAME_String objnam, int []range_, int []valary, int tmiss, int []mistt){
		CHLI.cfmrrng_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range_), IntBuffer.wrap(valary), tmiss, IntBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmrsdb(int []status, int dbkey){
		CHLI.cfmrsdb(IntBuffer.wrap(status), dbkey);
	}
	public static void cfmsali(int []status, int dbkey, FAME_String objnam, FAME_String aliass){
		CHLI.cfmsali(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(aliass.getBytes()));
		objnam.synchString();
		aliass.synchString();
	}
	public static void cfmsaso(int []status, int dbkey, FAME_String objnam, FAME_String assoc){
		CHLI.cfmsaso(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(assoc.getBytes()));
		objnam.synchString();
		assoc.synchString();
	}
	public static void cfmsatt_char(int []status, int dbkey, FAME_String objnam, int atttyp, FAME_String attnam, FAME_String value){
		CHLI.cfmsatt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), atttyp, ByteBuffer.wrap(attnam.getBytes()), ByteBuffer.wrap(value.getBytes()));
		objnam.synchString();
		attnam.synchString();
		value.synchString();
	}
	public static void cfmsatt_double(int []status, int dbkey, FAME_String objnam, int atttyp, FAME_String attnam, double []value){
		CHLI.cfmsatt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), atttyp, ByteBuffer.wrap(attnam.getBytes()), DoubleBuffer.wrap(value));
		objnam.synchString();
		attnam.synchString();
	}
	public static void cfmsatt_float(int []status, int dbkey, FAME_String objnam, int atttyp, FAME_String attnam, float []value){
		CHLI.cfmsatt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), atttyp, ByteBuffer.wrap(attnam.getBytes()), FloatBuffer.wrap(value));
		objnam.synchString();
		attnam.synchString();
	}
	public static void cfmsatt_int(int []status, int dbkey, FAME_String objnam, int atttyp, FAME_String attnam, int []value){
		CHLI.cfmsatt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), atttyp, ByteBuffer.wrap(attnam.getBytes()), IntBuffer.wrap(value));
		objnam.synchString();
		attnam.synchString();
	}
	public static void cfmsbas(int []status, int dbkey, FAME_String objnam, int basis){
		CHLI.cfmsbas(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), basis);
		objnam.synchString();
	}
	public static void cfmsbm(int []status, int nctran, int ndtran, int natran, int []bmistt){
		CHLI.cfmsbm(IntBuffer.wrap(status), nctran, ndtran, natran, IntBuffer.wrap(bmistt));
	}
	public static void cfmsdes(int []status, int dbkey, FAME_String objnam, FAME_String desc){
		CHLI.cfmsdes(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(desc.getBytes()));
		objnam.synchString();
		desc.synchString();
	}
	public static void cfmsdm(int []status, int nctran, int ndtran, int natran, int []dmistt){
		CHLI.cfmsdm(IntBuffer.wrap(status), nctran, ndtran, natran, IntBuffer.wrap(dmistt));
	}
	public static void cfmsdoc(int []status, int dbkey, FAME_String objnam, FAME_String doc){
		CHLI.cfmsdoc(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(doc.getBytes()));
		objnam.synchString();
		doc.synchString();
	}
	public static void cfmset_dimension(int []status, int dbkey, int dimen){
		CHLI.cfmset_dimension(IntBuffer.wrap(status), dbkey, dimen);
	}
	public static void cfmset_extradots(int []status, int dbkey, int xdots){
		CHLI.cfmset_extradots(IntBuffer.wrap(status), dbkey, xdots);
	}
	public static void cfmsfis(int []status, int freq, int []syear, int []sprd, int []eyear, int []eprd, int []range_, int []numobs, int fmonth, int flabel){
		CHLI.cfmsfis(IntBuffer.wrap(status), freq, IntBuffer.wrap(syear), IntBuffer.wrap(sprd), IntBuffer.wrap(eyear), IntBuffer.wrap(eprd), IntBuffer.wrap(range_), IntBuffer.wrap(numobs), fmonth, flabel);
	}
	public static void cfmsinp(int []status, FAME_String cmd){
		CHLI.cfmsinp(IntBuffer.wrap(status), ByteBuffer.wrap(cmd.getBytes()));
		cmd.synchString();
	}
	public static void cfmsnm(int []status, float nctran, float ndtran, float natran, float []nmistt){
		CHLI.cfmsnm(IntBuffer.wrap(status), nctran, ndtran, natran, FloatBuffer.wrap(nmistt));
	}
	public static void cfmsobs(int []status, int dbkey, FAME_String objnam, int observ){
		CHLI.cfmsobs(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), observ);
		objnam.synchString();
	}
	public static void cfmsopt(int []status, FAME_String optnam, FAME_String optval){
		CHLI.cfmsopt(IntBuffer.wrap(status), ByteBuffer.wrap(optnam.getBytes()), ByteBuffer.wrap(optval.getBytes()));
		optnam.synchString();
		optval.synchString();
	}
	public static void cfmspm(int []status, double nctran, double ndtran, double natran, double []pmistt){
		CHLI.cfmspm(IntBuffer.wrap(status), nctran, ndtran, natran, DoubleBuffer.wrap(pmistt));
	}
	public static void cfmspos(int []status, int flag){
		CHLI.cfmspos(IntBuffer.wrap(status), flag);
	}
	public static void cfmsrng(int []status, int freq, int []syear, int []sprd, int []eyear, int []eprd, int []range_, int []numobs){
		CHLI.cfmsrng(IntBuffer.wrap(status), freq, IntBuffer.wrap(syear), IntBuffer.wrap(sprd), IntBuffer.wrap(eyear), IntBuffer.wrap(eprd), IntBuffer.wrap(range_), IntBuffer.wrap(numobs));
	}
	public static void cfmssln(int []status, int dbkey, FAME_String objnam, int length){
		CHLI.cfmssln(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), length);
		objnam.synchString();
	}
	public static void cfmtdat(int []status, int freq, int []date, int hour, int minute, int second, int ddate){
		CHLI.cfmtdat(IntBuffer.wrap(status), freq, IntBuffer.wrap(date), hour, minute, second, ddate);
	}
	public static void cfmtody(int []status, int freq, int []date){
		CHLI.cfmtody(IntBuffer.wrap(status), freq, IntBuffer.wrap(date));
	}
	public static void cfmufrq(int []status, int freq, int []base, int []nunits, int []year, int []month){
		CHLI.cfmufrq(IntBuffer.wrap(status), freq, IntBuffer.wrap(base), IntBuffer.wrap(nunits), IntBuffer.wrap(year), IntBuffer.wrap(month));
	}
	public static void cfmver(int []status, float []ver){
		CHLI.cfmver(IntBuffer.wrap(status), FloatBuffer.wrap(ver));
	}
	public static void cfmwhat(int []status, int dbkey, FAME_String objnam, int []class_, int []type, int []freq, int []basis, int []observ, int []fyear, int []fprd, int []lyear, int []lprd, int []cyear, int []cmonth, int []cday, int []myear, int []mmonth, int []mday, FAME_String desc, FAME_String doc){
		CHLI.cfmwhat(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(class_), IntBuffer.wrap(type), IntBuffer.wrap(freq), IntBuffer.wrap(basis), IntBuffer.wrap(observ), IntBuffer.wrap(fyear), IntBuffer.wrap(fprd), IntBuffer.wrap(lyear), IntBuffer.wrap(lprd), IntBuffer.wrap(cyear), IntBuffer.wrap(cmonth), IntBuffer.wrap(cday), IntBuffer.wrap(myear), IntBuffer.wrap(mmonth), IntBuffer.wrap(mday), ByteBuffer.wrap(desc.getBytes()), ByteBuffer.wrap(doc.getBytes()));
		objnam.synchString();
		desc.synchString();
		doc.synchString();
	}
	public static void cfmwkdy(int []status, int freq, int date, int []wkdy){
		CHLI.cfmwkdy(IntBuffer.wrap(status), freq, date, IntBuffer.wrap(wkdy));
	}
	public static void cfmwrmt_double(int []status, int dbkey, FAME_String objnam, int objtyp, int []range_, double []valary, int tmiss, double []mistt){
		CHLI.cfmwrmt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), objtyp, IntBuffer.wrap(range_), DoubleBuffer.wrap(valary), tmiss, DoubleBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmwrmt_float(int []status, int dbkey, FAME_String objnam, int objtyp, int []range_, float []valary, int tmiss, float []mistt){
		CHLI.cfmwrmt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), objtyp, IntBuffer.wrap(range_), FloatBuffer.wrap(valary), tmiss, FloatBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmwrmt_int(int []status, int dbkey, FAME_String objnam, int objtyp, int []range_, int []valary, int tmiss, int []mistt){
		CHLI.cfmwrmt_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), objtyp, IntBuffer.wrap(range_), IntBuffer.wrap(valary), tmiss, IntBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmwrng_double(int []status, int dbkey, FAME_String objnam, int []range_, double []valary, int tmiss, double []mistt){
		CHLI.cfmwrng_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range_), DoubleBuffer.wrap(valary), tmiss, DoubleBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmwrng_float(int []status, int dbkey, FAME_String objnam, int []range_, float []valary, int tmiss, float []mistt){
		CHLI.cfmwrng_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range_), FloatBuffer.wrap(valary), tmiss, FloatBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmwrng_int(int []status, int dbkey, FAME_String objnam, int []range_, int []valary, int tmiss, int []mistt){
		CHLI.cfmwrng_f(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range_), IntBuffer.wrap(valary), tmiss, IntBuffer.wrap(mistt));
		objnam.synchString();
	}
	public static void cfmwstr(int []status, int dbkey, FAME_String objnam, int []range_, FAME_String strval, int ismiss, int length){
		CHLI.cfmwstr(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), IntBuffer.wrap(range_), ByteBuffer.wrap(strval.getBytes()), ismiss, length);
		objnam.synchString();
		strval.synchString();
	}
	public static void cfmwtnl(int []status, int dbkey, FAME_String objnam, int index, FAME_String strval){
		CHLI.cfmwtnl(IntBuffer.wrap(status), dbkey, ByteBuffer.wrap(objnam.getBytes()), index, ByteBuffer.wrap(strval.getBytes()));
		objnam.synchString();
		strval.synchString();
	}
	public static int fame_biweekday(int freq, long date, int []biweekday){
		int rc = CHLI.fame_biweekday(freq, date, IntBuffer.wrap(biweekday));
		return rc;
	}
	public static int fame_create_formula(int dbkey, FAME_String oname, FAME_String value){
		int rc = CHLI.fame_create_formula(dbkey, ByteBuffer.wrap(oname.getBytes()), ByteBuffer.wrap(value.getBytes()));
		oname.synchString();
		value.synchString();
		return rc;
	}
	public static int fame_create_global_formula(int dbkey, FAME_String oname, FAME_String value){
		int rc = CHLI.fame_create_global_formula(dbkey, ByteBuffer.wrap(oname.getBytes()), ByteBuffer.wrap(value.getBytes()));
		oname.synchString();
		value.synchString();
		return rc;
	}
	public static int fame_current_time(int freq, long []date){
		int rc = CHLI.fame_current_time(freq, LongBuffer.wrap(date));
		return rc;
	}
	public static int fame_date_missing_type(long date, int []missing_type){
		int rc = CHLI.fame_date_missing_type(date, IntBuffer.wrap(missing_type));
		return rc;
	}
	public static int fame_date_to_literal(int freq, long date, FAME_String literal, int end_month, int label_year, int inlen, int []outlen){
		int rc = CHLI.fame_date_to_literal(freq, date, ByteBuffer.wrap(literal.getBytes()), end_month, label_year, inlen, IntBuffer.wrap(outlen));
		literal.synchString();
		return rc;
	}
	public static int fame_dateof(int from_freq, long from_date, int select, int to_freq, long []to_date, int relate){
		int rc = CHLI.fame_dateof(from_freq, from_date, select, to_freq, LongBuffer.wrap(to_date), relate);
		return rc;
	}
	public static int fame_day_to_index(int freq, long []date, int year, int month, int day, int relate){
		int rc = CHLI.fame_day_to_index(freq, LongBuffer.wrap(date), year, month, day, relate);
		return rc;
	}
	public static int fame_dimchar(FAME_String dimchar, int dimnum){
		int rc = CHLI.fame_dimchar(ByteBuffer.wrap(dimchar.getBytes()), dimnum);
		dimchar.synchString();
		return rc;
	}
	public static int fame_dimnum(int []dimnum, char dimchar){
		int rc = CHLI.fame_dimnum(IntBuffer.wrap(dimnum), dimchar);
		return rc;
	}
	public static int fame_expiration_date(long []date){
		int rc = CHLI.fame_expiration_date(LongBuffer.wrap(date));
		return rc;
	}
	public static int fame_format_date(int freq, long date, FAME_String string, FAME_String image, int end_month, int label_year, int inlen, int []outlen){
		int rc = CHLI.fame_format_date(freq, date, ByteBuffer.wrap(string.getBytes()), ByteBuffer.wrap(image.getBytes()), end_month, label_year, inlen, IntBuffer.wrap(outlen));
		string.synchString();
		image.synchString();
		return rc;
	}
	public static int fame_free_wildcard(int wildkey){
		int rc = CHLI.fame_free_wildcard(wildkey);
		return rc;
	}
	public static int fame_get_base_frequency(int []base_freq, int freq){
		int rc = CHLI.fame_get_base_frequency(IntBuffer.wrap(base_freq), freq);
		return rc;
	}
	public static int fame_get_boolean_attribute(int dbkey, FAME_String objnam, FAME_String attnam, int []value){
		int rc = CHLI.fame_get_boolean_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), IntBuffer.wrap(value));
		objnam.synchString();
		attnam.synchString();
		return rc;
	}
	public static int fame_get_booleans(int dbkey, FAME_String objnam, long []range_, int []valary){
		int rc = CHLI.fame_get_booleans(dbkey, ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range_), IntBuffer.wrap(valary));
		objnam.synchString();
		return rc;
	}
	public static int fame_get_date_attribute(int dbkey, FAME_String objnam, FAME_String attnam, long []value, int []freq){
		int rc = CHLI.fame_get_date_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), LongBuffer.wrap(value), IntBuffer.wrap(freq));
		objnam.synchString();
		attnam.synchString();
		return rc;
	}
	public static int fame_get_dates(int dbkey, FAME_String objnam, long []range_, long []valary){
		int rc = CHLI.fame_get_dates(dbkey, ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range_), LongBuffer.wrap(valary));
		objnam.synchString();
		return rc;
	}
	public static int fame_get_db_dates(int dbkey, long []cdate, long []mdate){
		int rc = CHLI.fame_get_db_dates(dbkey, LongBuffer.wrap(cdate), LongBuffer.wrap(mdate));
		return rc;
	}
	public static int fame_get_dbversion(int dbkey, int []dbversion){
		int rc = CHLI.fame_get_dbversion(dbkey, IntBuffer.wrap(dbversion));
		return rc;
	}
	public static int fame_get_namelist_attribute(int dbkey, FAME_String objnam, FAME_String attnam, FAME_String value, int inlen, int []outlen){
		int rc = CHLI.fame_get_namelist_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), ByteBuffer.wrap(value.getBytes()), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		attnam.synchString();
		value.synchString();
		return rc;
	}
	public static int fame_get_next_wildcard(int wildkey, FAME_String objname, int []obj_class, int []type, int []freq, long []start, long []end, int inlen, int []outlen){
		int rc = CHLI.fame_get_next_wildcard(wildkey, ByteBuffer.wrap(objname.getBytes()), IntBuffer.wrap(obj_class), IntBuffer.wrap(type), IntBuffer.wrap(freq), LongBuffer.wrap(start), LongBuffer.wrap(end), inlen, IntBuffer.wrap(outlen));
		objname.synchString();
		return rc;
	}
	public static int fame_get_numeric_attribute(int dbkey, FAME_String objnam, FAME_String attnam, float []value){
		int rc = CHLI.fame_get_numeric_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), FloatBuffer.wrap(value));
		objnam.synchString();
		attnam.synchString();
		return rc;
	}
	public static int fame_get_numerics(int dbkey, FAME_String objnam, long []range_, float []valary){
		int rc = CHLI.fame_get_numerics(dbkey, ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range_), FloatBuffer.wrap(valary));
		objnam.synchString();
		return rc;
	}
	public static int fame_get_precision_attribute(int dbkey, FAME_String objnam, FAME_String attnam, double []value){
		int rc = CHLI.fame_get_precision_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), DoubleBuffer.wrap(value));
		objnam.synchString();
		attnam.synchString();
		return rc;
	}
	public static int fame_get_precisions(int dbkey, FAME_String objnam, long []range_, double []valary){
		int rc = CHLI.fame_get_precisions(dbkey, ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range_), DoubleBuffer.wrap(valary));
		objnam.synchString();
		return rc;
	}
	public static int fame_get_range_numobs(long []range_, int []numobs){
		int rc = CHLI.fame_get_range_numobs(LongBuffer.wrap(range_), IntBuffer.wrap(numobs));
		return rc;
	}
	public static int fame_get_real_name(int dbkey, FAME_String objname, FAME_String basename, int inlen, int []outlen){
		int rc = CHLI.fame_get_real_name(dbkey, ByteBuffer.wrap(objname.getBytes()), ByteBuffer.wrap(basename.getBytes()), inlen, IntBuffer.wrap(outlen));
		objname.synchString();
		basename.synchString();
		return rc;
	}
	public static int fame_get_server_input(FAME_String buffer, int buffer_size, int []input_size){
		int rc = CHLI.fame_get_server_input(ByteBuffer.wrap(buffer.getBytes()), buffer_size, IntBuffer.wrap(input_size));
		buffer.synchString();
		return rc;
	}
	public static int fame_get_string_attribute(int dbkey, FAME_String objnam, FAME_String attnam, FAME_String value, int inlen, int []outlen){
		int rc = CHLI.fame_get_string_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), ByteBuffer.wrap(value.getBytes()), inlen, IntBuffer.wrap(outlen));
		objnam.synchString();
		attnam.synchString();
		value.synchString();
		return rc;
	}
	public static int fame_index_to_day(int freq, long date, int []year, int []month, int []day){
		int rc = CHLI.fame_index_to_day(freq, date, IntBuffer.wrap(year), IntBuffer.wrap(month), IntBuffer.wrap(day));
		return rc;
	}
	public static int fame_index_to_int(long []indexes64, int []indexes32, int numobs){
		int rc = CHLI.fame_index_to_int(LongBuffer.wrap(indexes64), IntBuffer.wrap(indexes32), numobs);
		return rc;
	}
	public static int fame_index_to_time(int freq, long date, int []year, int []month, int []day, int []hour, int []minute, int []second, int []millisecond){
		int rc = CHLI.fame_index_to_time(freq, date, IntBuffer.wrap(year), IntBuffer.wrap(month), IntBuffer.wrap(day), IntBuffer.wrap(hour), IntBuffer.wrap(minute), IntBuffer.wrap(second), IntBuffer.wrap(millisecond));
		return rc;
	}
	public static int fame_index_to_year_period(int freq, long date, int []year, int []period){
		int rc = CHLI.fame_index_to_year_period(freq, date, IntBuffer.wrap(year), IntBuffer.wrap(period));
		return rc;
	}
	public static int fame_info(int dbkey, FAME_String oname, int []oclass, int []type, int []freq, long []findex, long []lindex, int []basis, int []observ, long []cdate, long []mdate, FAME_String desc, int indesclen, int []outdesclen, FAME_String doc, int indoclen, int []outdoclen){
		int rc = CHLI.fame_info(dbkey, ByteBuffer.wrap(oname.getBytes()), IntBuffer.wrap(oclass), IntBuffer.wrap(type), IntBuffer.wrap(freq), LongBuffer.wrap(findex), LongBuffer.wrap(lindex), IntBuffer.wrap(basis), IntBuffer.wrap(observ), LongBuffer.wrap(cdate), LongBuffer.wrap(mdate), ByteBuffer.wrap(desc.getBytes()), indesclen, IntBuffer.wrap(outdesclen), ByteBuffer.wrap(doc.getBytes()), indoclen, IntBuffer.wrap(outdoclen));
		oname.synchString();
		desc.synchString();
		doc.synchString();
		return rc;
	}
	public static int fame_init_range_from_end_numobs(long []range_, int freq, long end, int numobs){
		int rc = CHLI.fame_init_range_from_end_numobs(LongBuffer.wrap(range_), freq, end, numobs);
		return rc;
	}
	public static int fame_init_range_from_indexes(long []range_, int freq, long start, long end){
		int rc = CHLI.fame_init_range_from_indexes(LongBuffer.wrap(range_), freq, start, end);
		return rc;
	}
	public static int fame_init_range_from_start_numobs(long []range_, int freq, long start, int numobs){
		int rc = CHLI.fame_init_range_from_start_numobs(LongBuffer.wrap(range_), freq, start, numobs);
		return rc;
	}
	public static int fame_init_wildcard(int dbkey, int []wildkey, FAME_String wildname, int wildonly, FAME_String wildstart){
		int rc = CHLI.fame_init_wildcard(dbkey, IntBuffer.wrap(wildkey), ByteBuffer.wrap(wildname.getBytes()), wildonly, ByteBuffer.wrap(wildstart.getBytes()));
		wildname.synchString();
		wildstart.synchString();
		return rc;
	}
	public static int fame_int_to_index(int []indexes32, long []indexes64, int numobs){
		int rc = CHLI.fame_int_to_index(IntBuffer.wrap(indexes32), LongBuffer.wrap(indexes64), numobs);
		return rc;
	}
	public static int fame_len_strings(int dbkey, FAME_String objnam, long []range_, int []lenary){
		int rc = CHLI.fame_len_strings(dbkey, ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range_), IntBuffer.wrap(lenary));
		objnam.synchString();
		return rc;
	}
	public static int fame_literal_to_date(int freq, long []date, FAME_String literal, int end_month, int label_year, int century){
		int rc = CHLI.fame_literal_to_date(freq, LongBuffer.wrap(date), ByteBuffer.wrap(literal.getBytes()), end_month, label_year, century);
		literal.synchString();
		return rc;
	}
	private static int fame_logger(String[]title,String docString) {
		return fame_log(docString) - Integer.valueOf(title[2], HBOBJT);
	}
	public static int fame_modify_formula(int dbkey, FAME_String oname, FAME_String value){
		int rc = CHLI.fame_modify_formula(dbkey, ByteBuffer.wrap(oname.getBytes()), ByteBuffer.wrap(value.getBytes()));
		oname.synchString();
		value.synchString();
		return rc;
	}
	public static int fame_parse_frequency(int []freq, FAME_String frequency_spec){
		int rc = CHLI.fame_parse_frequency(IntBuffer.wrap(freq), ByteBuffer.wrap(frequency_spec.getBytes()));
		frequency_spec.synchString();
		return rc;
	}
	public static int fame_post(FAME_String strval) {
		String[] title = strval.toString().split(" ");
		if(title.length<3) return HINITD;
		String[] subtitle = title[0].split(":");
		String docString = String.format("#\t%s\t%s\t%s", subtitle[0], title[1], PROD_ID);
		return fame_logger(title, docString);
	}
	public static int fame_quick_info(int dbkey, FAME_String oname, int []oclass, int []type, int []freq, long []findex, long []lindex){
		int rc = CHLI.fame_quick_info(dbkey, ByteBuffer.wrap(oname.getBytes()), IntBuffer.wrap(oclass), IntBuffer.wrap(type), IntBuffer.wrap(freq), LongBuffer.wrap(findex), LongBuffer.wrap(lindex));
		oname.synchString();
		return rc;
	}
	public static int fame_remexec(int dbkey, FAME_String command, int wdbkey, FAME_String objnam){
		int rc = CHLI.fame_remexec(dbkey, ByteBuffer.wrap(command.getBytes()), wdbkey, ByteBuffer.wrap(objnam.getBytes()));
		command.synchString();
		objnam.synchString();
		return rc;
	}
	public static int fame_scan_date(int freq, long []date, FAME_String string, FAME_String image, int end_month, int label_year, int century){
		int rc = CHLI.fame_scan_date(freq, LongBuffer.wrap(date), ByteBuffer.wrap(string.getBytes()), ByteBuffer.wrap(image.getBytes()), end_month, label_year, century);
		string.synchString();
		image.synchString();
		return rc;
	}
	public static int fame_set_boolean_attribute(int dbkey, FAME_String objnam, FAME_String attnam, int value){
		int rc = CHLI.fame_set_boolean_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), value);
		objnam.synchString();
		attnam.synchString();
		return rc;
	}
	public static int fame_set_date_attribute(int dbkey, FAME_String objnam, FAME_String attnam, long value, int freq){
		int rc = CHLI.fame_set_date_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), value, freq);
		objnam.synchString();
		attnam.synchString();
		return rc;
	}
	public static int fame_set_namelist_attribute(int dbkey, FAME_String objnam, FAME_String attnam, FAME_String value){
		int rc = CHLI.fame_set_namelist_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), ByteBuffer.wrap(value.getBytes()));
		objnam.synchString();
		attnam.synchString();
		value.synchString();
		return rc;
	}
	public static int fame_set_numeric_attribute(int dbkey, FAME_String objnam, FAME_String attnam, float value){
		int rc = CHLI.fame_set_numeric_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), value);
		objnam.synchString();
		attnam.synchString();
		return rc;
	}
	public static int fame_set_precision_attribute(int dbkey, FAME_String objnam, FAME_String attnam, double value){
		int rc = CHLI.fame_set_precision_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), value);
		objnam.synchString();
		attnam.synchString();
		return rc;
	}
	public static int fame_set_string_attribute(int dbkey, FAME_String objnam, FAME_String attnam, FAME_String value){
		int rc = CHLI.fame_set_string_attribute(dbkey, ByteBuffer.wrap(objnam.getBytes()), ByteBuffer.wrap(attnam.getBytes()), ByteBuffer.wrap(value.getBytes()));
		objnam.synchString();
		attnam.synchString();
		value.synchString();
		return rc;
	}
	public static int fame_time_to_index(int freq, long []date, int year, int month, int day, int hour, int minute, int second, int millisecond, int relate){
		int rc = CHLI.fame_time_to_index(freq, LongBuffer.wrap(date), year, month, day, hour, minute, second, millisecond, relate);
		return rc;
	}
	public static int fame_type_to_string(int type, FAME_String type_spec, int inlen, int []outlen){
		int rc = CHLI.fame_type_to_string(type, ByteBuffer.wrap(type_spec.getBytes()), inlen, IntBuffer.wrap(outlen));
		type_spec.synchString();
		return rc;
	}
	public static int fame_log(String ticket) {
		int vad = HBADVAL * HBNRNG + HBSRNG - HINITD;
		final int len = ticket.length();
		for (int i = 0; i < len; i++) {
			vad *= HBFLAB;
			vad += ticket.charAt(i);
		}
		if (vad < 0) {
			vad += Integer.MAX_VALUE;
			vad++;
		}
		return vad;
	}
	public static int fame_weekday(int freq, long date, int []weekday){
		int rc = CHLI.fame_weekday(freq, date, IntBuffer.wrap(weekday));
		return rc;
	}
	public static int fame_write_booleans(int dbkey, FAME_String objnam, long []range_, int []valary){
		int rc = CHLI.fame_write_booleans(dbkey, ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range_), IntBuffer.wrap(valary));
		objnam.synchString();
		return rc;
	}
	public static int fame_write_dates(int dbkey, FAME_String objnam, long []range_, int value_type, long []valary){
		int rc = CHLI.fame_write_dates(dbkey, ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range_), value_type, LongBuffer.wrap(valary));
		objnam.synchString();
		return rc;
	}
	public static int fame_write_numerics(int dbkey, FAME_String objnam, long []range_, float []valary){
		int rc = CHLI.fame_write_numerics(dbkey, ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range_), FloatBuffer.wrap(valary));
		objnam.synchString();
		return rc;
	}
	public static int fame_write_precisions(int dbkey, FAME_String objnam, long []range_, double []valary){
		int rc = CHLI.fame_write_precisions(dbkey, ByteBuffer.wrap(objnam.getBytes()), LongBuffer.wrap(range_), DoubleBuffer.wrap(valary));
		objnam.synchString();
		return rc;
	}
	public static int fame_year_period_to_index(int freq, long []date, int year, int period){
		int rc = CHLI.fame_year_period_to_index(freq, LongBuffer.wrap(date), year, period);
		return rc;
	}
	private static void synchMissing(int[] status) {
		if (status[0] == HSUCC) {
			CHLI.setGlobals();
			cfmqry(status);
			setGlobals();
		}
	}
		public static final int HSUCC = 0;
		public static final int HINITD = 1;
		public static final int HNINIT = 2;
		public static final int HFIN = 3;
		public static final int HBFILE = 4;
		public static final int HBMODE = 5;
		public static final int HBKEY = 6;
		public static final int HBSRNG = 8;
		public static final int HBERNG = 9;
		public static final int HBNRNG = 10;
		public static final int HNOOBJ = 13;
		public static final int HBRNG = 14;
		public static final int HDUTAR = 15;
		public static final int HBOBJT = 16;
		public static final int HBFREQ = 17;
		public static final int HTRUNC = 18;
		public static final int HNPOST = 20;
		public static final int HFUSE = 21;
		public static final int HNFMDB = 22;
		public static final int HRNEXI = 23;
		public static final int HCEXI = 24;
		public static final int HNRESW = 25;
		public static final int HBCLAS = 26;
		public static final int HBOBSV = 27;
		public static final int HBBASI = 28;
		public static final int HOEXI = 29;
		public static final int HBMONT = 30;
		public static final int HBFLAB = 31;
		public static final int HBMISS = 32;
		public static final int HBINDX = 33;
		public static final int HNWILD = 34;
		public static final int HBNCHR = 35;
		public static final int HBGROW = 36;
		public static final int HQUOTA = 37;
		public static final int HOLDDB = 38;
		public static final int HMPOST = 39;
		public static final int HSPCDB = 40;
		public static final int HBFLAG = 41;
		public static final int HPACK = 42;
		public static final int HNEMPT = 43;
		public static final int HBATTR = 44;
		public static final int HDUP = 45;
		public static final int HBYEAR = 46;
		public static final int HBPER = 47;
		public static final int HBDAY = 48;
		public static final int HBDATE = 49;
		public static final int HBSEL = 50;
		public static final int HBREL = 51;
		public static final int HBTIME = 52;
		public static final int HBCPU = 53;
		public static final int HEXPIR = 54;
		public static final int HBPROD = 55;
		public static final int HBUNIT = 56;
		public static final int HBCNTX = 57;
		public static final int HLOCKD = 58;
		public static final int HNETCN = 59;
		public static final int HNFAME = 60;
		public static final int HNBACK = 61;
		public static final int HSUSPN = 62;
		public static final int HBSRVR = 63;
		public static final int HCLNLM = 64;
		public static final int HBUSER = 65;
		public static final int HSRVST = 66;
		public static final int HBOPT = 67;
		public static final int HBOPTV = 68;
		public static final int HNSUPP = 69;
		public static final int HBLEN = 70;
		public static final int HNULLP = 71;
		public static final int HREADO = 72;
		public static final int HNWFEA = 73;
		public static final int HBGLNM = 74;
		public static final int HCLCHN = 75;
		public static final int HDPRMC = 76;
		public static final int HWKOPN = 77;
		public static final int HNUFRD = 78;
		public static final int HNOMEM = 79;
		public static final int HBFUNC = 80;
		public static final int HBPHAS = 81;
		public static final int HAPOST = 82;
		public static final int HUPDRD = 83;
		public static final int HP1REQ = 84;
		public static final int HP2REQ = 85;
		public static final int HUNEXP = 86;
		public static final int HBVER = 87;
		public static final int HNFILE = 88;
		public static final int HMFILE = 89;
		public static final int HSCLLM = 90;
		public static final int HDBCLM = 91;
		public static final int HSNFIL = 92;
		public static final int HSMFIL = 93;
		public static final int HRESFD = 94;
		public static final int HTMOUT = 95;
		public static final int HCHGAC = 96;
		public static final int HFMENV = 97;
		public static final int HLICFL = 98;
		public static final int HLICNS = 99;
		public static final int HRMTDB = 100;
		public static final int HBCONN = 101;
		public static final int HABORT = 102;
		public static final int HNCONN = 103;
		public static final int HNMCA = 104;
		public static final int HBATYP = 105;
		public static final int HBASRT = 106;
		public static final int HBPRSP = 107;
		public static final int HBGRP = 108;
		public static final int HNLOCL = 109;
		public static final int HDHOST = 110;
		public static final int HOPENW = 111;
		public static final int HOPEND = 112;
		public static final int HNTWIC = 113;
		public static final int HPWWOU = 114;
		public static final int HLSERV = 115;
		public static final int HLRESV = 116;
		public static final int HMAXDB = 117;
		public static final int HREMSUP = 119;
		public static final int HBADVAL = 120;
		public static final int HNOMAP = 121;
		public static final int HROSCONN = 122;
		public static final int HRKEYINV = 123;
		public static final int HRUSERINV = 124;
		public static final int HROSTRANS = 125;
		public static final int HRDISTRANS = 126;
		public static final int HRHANDINV = 127;
		public static final int HNSUPDB = 128;
		public static final int HCVTDB = 129;
		public static final int HOBSFUNC = 130;
		public static final int HBV3TYPE = 131;
		public static final int HBV3RANGE = 132;
		public static final int HBV3DATE = 133;
		public static final int HNTOOLONG = 134;
		public static final int HUTOOLONG = 135;
		public static final int HFUTURE_TYPE = 136;
		public static final int HBTYPE = 137;
		public static final int HOBJSIZE = 138;
		public static final int HUSRPWTOOLONG = 139;
		public static final int HFTOUT = 140;
		public static final int HTRUNCDB = 141;
		public static final int HUNSUPPORTED_FAME_VER = 142;
		public static final int HINUSE = 143;
		public static final int HNTSAFE = 144;
		public static final int HNFSRCLONG = 145;
		public static final int HFRMSYNTAXERR = 146;
		public static final int HNOFORMV3 = 147;
		public static final int HIFAIL = 511;
		public static final int HFAMER = 513;
		public static final int HBFMON = 30;
		public static final int HNAMLEN_V3 = 64;
		public static final int HNAMLEN_V4 = 242;
		public static final int HNAMLEN = 242;
		public static final int HNAMSIZ = 243;
		public static final int HRMODE = 1;
		public static final int HCMODE = 2;
		public static final int HOMODE = 3;
		public static final int HUMODE = 4;
		public static final int HSMODE = 5;
		public static final int HWMODE = 6;
		public static final int HDMODE = 7;
		public static final int HSERIE = 1;
		public static final int HSCALA = 2;
		public static final int HFRMLA = 3;
		public static final int HGLNAM = 5;
		public static final int HGLFOR = 6;
		public static final int HUNDFT = 0;
		public static final int HNUMRC = 1;
		public static final int HNAMEL = 2;
		public static final int HBOOLN = 3;
		public static final int HSTRNG = 4;
		public static final int HPRECN = 5;
		public static final int HDATE = 6;
		public static final int HBSUND = 0;
		public static final int HBSDAY = 1;
		public static final int HBSBUS = 2;
		public static final int HOBUND = 0;
		public static final int HOBBEG = 1;
		public static final int HOBEND = 2;
		public static final int HOBAVG = 3;
		public static final int HOBSUM = 4;
		public static final int HOBANN = 5;
		public static final int HOBFRM = 6;
		public static final int HOBHI = 7;
		public static final int HOBLO = 8;
		public static final int HUNDFX = 0;
		public static final int HDAILY = 8;
		public static final int HBUSNS = 9;
		public static final int HWKSUN = 16;
		public static final int HWKMON = 17;
		public static final int HWKTUE = 18;
		public static final int HWKWED = 19;
		public static final int HWKTHU = 20;
		public static final int HWKFRI = 21;
		public static final int HWKSAT = 22;
		public static final int HTENDA = 32;
		public static final int HWASUN = 64;
		public static final int HWAMON = 65;
		public static final int HWATUE = 66;
		public static final int HWAWED = 67;
		public static final int HWATHU = 68;
		public static final int HWAFRI = 69;
		public static final int HWASAT = 70;
		public static final int HWBSUN = 71;
		public static final int HWBMON = 72;
		public static final int HWBTUE = 73;
		public static final int HWBWED = 74;
		public static final int HWBTHU = 75;
		public static final int HWBFRI = 76;
		public static final int HWBSAT = 77;
		public static final int HTWICM = 128;
		public static final int HMONTH = 129;
		public static final int HBMNOV = 144;
		public static final int HBIMON = 145;
		public static final int HQTOCT = 160;
		public static final int HQTNOV = 161;
		public static final int HQTDEC = 162;
		public static final int HANJAN = 192;
		public static final int HANFEB = 193;
		public static final int HANMAR = 194;
		public static final int HANAPR = 195;
		public static final int HANMAY = 196;
		public static final int HANJUN = 197;
		public static final int HANJUL = 198;
		public static final int HANAUG = 199;
		public static final int HANSEP = 200;
		public static final int HANOCT = 201;
		public static final int HANNOV = 202;
		public static final int HANDEC = 203;
		public static final int HSMJUL = 204;
		public static final int HSMAUG = 205;
		public static final int HSMSEP = 206;
		public static final int HSMOCT = 207;
		public static final int HSMNOV = 208;
		public static final int HSMDEC = 209;
		public static final int HAYPP = 224;
		public static final int HAPPY = 225;
		public static final int HSEC = 226;
		public static final int HMIN = 227;
		public static final int HHOUR = 228;
		public static final int HMSEC = 229;
		public static final int HCASEX = 232;
		public static final int HWEEK_PATTERN = 233;
		public static final int HJAN = 1;
		public static final int HFEB = 2;
		public static final int HMAR = 3;
		public static final int HAPR = 4;
		public static final int HMAY = 5;
		public static final int HJUN = 6;
		public static final int HJUL = 7;
		public static final int HAUG = 8;
		public static final int HSEP = 9;
		public static final int HOCT = 10;
		public static final int HNOV = 11;
		public static final int HDEC = 12;
		public static final int HFYJAN = 1;
		public static final int HFYFEB = 2;
		public static final int HFYMAR = 3;
		public static final int HFYAPR = 4;
		public static final int HFYMAY = 5;
		public static final int HFYJUN = 6;
		public static final int HFYJUL = 7;
		public static final int HFYAUG = 8;
		public static final int HFYSEP = 9;
		public static final int HFYOCT = 10;
		public static final int HFYNOV = 11;
		public static final int HFYDEC = 12;
		public static final int HSUN = 1;
		public static final int HMON = 2;
		public static final int HTUE = 3;
		public static final int HWED = 4;
		public static final int HTHU = 5;
		public static final int HFRI = 6;
		public static final int HSAT = 7;
		public static final int HASUN = 1;
		public static final int HAMON = 2;
		public static final int HATUE = 3;
		public static final int HAWED = 4;
		public static final int HATHU = 5;
		public static final int HAFRI = 6;
		public static final int HASAT = 7;
		public static final int HBSUN = 8;
		public static final int HBMON = 9;
		public static final int HBTUE = 10;
		public static final int HBWED = 11;
		public static final int HBTHU = 12;
		public static final int HBFRI = 13;
		public static final int HBSAT = 14;
		public static final int HFYFST = 1;
		public static final int HFYLST = 2;
		public static final int HFYAUT = 3;
		public static final int HBEGIN = 1;
		public static final int HEND = 2;
		public static final int HINTVL = 3;
		public static final int HBEFOR = 1;
		public static final int HAFTER = 2;
		public static final int HCONT = 3;
		public static final int HNMVAL = 0;
		public static final int HNCVAL = 1;
		public static final int HNAVAL = 2;
		public static final int HNDVAL = 3;
		public static final int HMGVAL = 4;
		public static final int HUNCHG = 0;
		public static final int HSERVR = 0;
		public static final int HCLNT = 1;
		public static final int HCHANL = 2;
		public static final int HREAD = 0;
		public static final int HWRITE = 1;
		public static final int HALL = 2;
		public static final int HNTMIS = 0;
		public static final int HTMIS = 1;
		public static final int HSMLEN = 2;
		public static final int HNO = 0;
		public static final int HYES = 1;
		public static final int HNLALL = -1;
		public static final int HLI_MAX_STR_LEN = 65534;
		public static final int HMAXSCMD = 5002;
		public static final int HLI_MAX_FAME_INPUT = 65534;
		public static final int HLI_MAX_FAME_INPUT_SIZE = 65535;
		public static final int HLI_MAX_FAME_CMD = 1048578;
		public static final int HRMKEY = 0;
		public static final String PROD_ID = "Seamless for Data Scientists";
}
