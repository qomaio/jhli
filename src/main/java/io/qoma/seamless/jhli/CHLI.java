package io.qoma.seamless.jhli;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import com.sun.jna.ptr.PointerByReference;

public class CHLI {
	private static NativeLibrary chli;

	static {
		String CHLI_LIBRARY = getCHLIfullPath();
		Native.register(CHLI_LIBRARY);
		chli = NativeLibrary.getInstance(CHLI_LIBRARY);
	}

	private static String getCHLIfullPath() {
		String CHLI_LIBRARY = System.getenv().get("FAME");
		if (Platform.isLinux()) {
			CHLI_LIBRARY += "/hli/64/libchli.so";
		} else if (Platform.isWindows()) {
			CHLI_LIBRARY += "/64/chli.dll";
		} else {
			throw new RuntimeException("Unsupported platform.");
		}
		return CHLI_LIBRARY;
	}

	static FAME_String	FSTRND,			FSTRNA,			FSTRNC;
	static double		FPRCND,			FPRCNA,			FPRCNC;
	static float		FNUMND,			FNUMNA,			FNUMNC;
	static int			FBOOND,			FBOONA,			FBOONC;
	static int			FDATND,			FDATNA,			FDATNC;
	static long			FAME_INDEX_ND,	FAME_INDEX_NA,	FAME_INDEX_NC;

	static void setGlobals() {
		FSTRNC = new FAME_String(chli.getGlobalVariableAddress("FSTRNC").
				getByteArray(0, JHLI.HSMLEN + 1));
		FSTRNA = new FAME_String(chli.getGlobalVariableAddress("FSTRNA").
				getByteArray(0, JHLI.HSMLEN + 1));
		FSTRND = new FAME_String(chli.getGlobalVariableAddress("FSTRND").
				getByteArray(0, JHLI.HSMLEN + 1));

		FNUMND = chli.getGlobalVariableAddress("FNUMND").getFloat(0);
		FNUMNA = chli.getGlobalVariableAddress("FNUMNA").getFloat(0);
		FNUMNC = chli.getGlobalVariableAddress("FNUMNC").getFloat(0);

		FPRCND = chli.getGlobalVariableAddress("FPRCND").getDouble(0);
		FPRCNA = chli.getGlobalVariableAddress("FPRCNA").getDouble(0);
		FPRCNC = chli.getGlobalVariableAddress("FPRCNC").getDouble(0);

		FBOOND = chli.getGlobalVariableAddress("FBOOND").getInt(0);
		FBOONA = chli.getGlobalVariableAddress("FBOONA").getInt(0);
		FBOONC = chli.getGlobalVariableAddress("FBOONC").getInt(0);

		FDATND = chli.getGlobalVariableAddress("FDATND").getInt(0);
		FDATNA = chli.getGlobalVariableAddress("FDATNA").getInt(0);
		FDATNC = chli.getGlobalVariableAddress("FDATNC").getInt(0);

		FAME_INDEX_ND = 
				chli.getGlobalVariableAddress("FAME_INDEX_ND").getLong(0);
		FAME_INDEX_NA = 
				chli.getGlobalVariableAddress("FAME_INDEX_NA").getLong(0);
		FAME_INDEX_NC = 
				chli.getGlobalVariableAddress("FAME_INDEX_NC").getLong(0);
	}

	static native void cfmgtsts(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range, PointerByReference strary, IntBuffer misary, IntBuffer inlen, IntBuffer outlen);
	static native void cfmwsts(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range, PointerByReference strary, IntBuffer misary, IntBuffer lenary);

	static native int fame_get_strings(int dbkey, ByteBuffer objnam, LongBuffer range, PointerByReference strary, IntBuffer inlen, IntBuffer outlen);
	static native int fame_write_strings(int dbkey, ByteBuffer objnam, LongBuffer range, PointerByReference strary);


	static native void cfmabrt(IntBuffer status, int connkey);
	static native void cfmalob(IntBuffer status, int dbkey, ByteBuffer objnam, int class_, int freq, int type, int basis, int observ, int numobs, int numchr, float growth);
	static native void cfmappl(IntBuffer status, ByteBuffer product);
	static native void cfmasrt(IntBuffer status, int connkey, int assert_type, ByteBuffer assertion, int perspective, int grouping, IntBuffer dblist);
	static native void cfmbwdy(IntBuffer status, int freq, int date, IntBuffer biwkdy);
	static native void cfmchfr(IntBuffer status, int sfreq, int sdate, int select, int tfreq, IntBuffer tdate, int relate);
	static native void cfmclcn(IntBuffer status, int connkey);
	static native void cfmcldb(IntBuffer status, int dbkey);
	static native void cfmcmmt(IntBuffer status, int connkey);
	static native void cfmcpob(IntBuffer status, int srckey, int tarkey, ByteBuffer srcnam, ByteBuffer tarnam);
	static native void cfmdatd(IntBuffer status, int freq, int date, IntBuffer year, IntBuffer month, IntBuffer day);
	static native void cfmdatf(IntBuffer status, int freq, int date, IntBuffer year, IntBuffer period, int fmonth, int flabel);
	static native void cfmdati(IntBuffer status, int freq, int date, ByteBuffer datstr, ByteBuffer image, int fmonth, int flabel);
	static native void cfmdatl(IntBuffer status, int freq, int date, ByteBuffer datstr, int fmonth, int flabel);
	static native void cfmdatp(IntBuffer status, int freq, int date, IntBuffer year, IntBuffer period);
	static native void cfmdatt(IntBuffer status, int freq, int date, IntBuffer hour, IntBuffer minute, IntBuffer second, IntBuffer ddate);
	static native void cfmddat(IntBuffer status, int freq, IntBuffer date, int year, int month, int day);
	static native void cfmddes(IntBuffer status, int dbkey, ByteBuffer des);
	static native void cfmddoc(IntBuffer status, int dbkey, ByteBuffer doc);
	static native void cfmdlen(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer deslen, IntBuffer doclen);
	static native void cfmdlob(IntBuffer status, int dbkey, ByteBuffer objnam);
	static native void cfmexpiration(IntBuffer status, IntBuffer date);
	static native void cfmfame(IntBuffer status, ByteBuffer cmd);
	static native void cfmfdat(IntBuffer status, int freq, IntBuffer date, int year, int period, int fmonth, int flabel);
	static native void cfmfdiv(IntBuffer status, int freq1, int freq2, IntBuffer flag);
	static native void cfmferr(IntBuffer status, ByteBuffer errtxt);
	static native void cfmfin(IntBuffer status);
	static native void cfmgcid(IntBuffer status, int dbkey, IntBuffer connkey);
	static native void cfmgdat(IntBuffer status, int dbkey, ByteBuffer objnam, int freq, IntBuffer cdate, IntBuffer mdate);
	static native void cfmgdba(IntBuffer status, int dbkey, IntBuffer cyear, IntBuffer cmonth, IntBuffer cday, IntBuffer myear, IntBuffer mmonth, IntBuffer mday, ByteBuffer desc, ByteBuffer doc);
	static native void cfmgdbd(IntBuffer status, int dbkey, int freq, IntBuffer cdate, IntBuffer mdate);
	static native void cfmget_dimension(IntBuffer status, int dbkey, IntBuffer dimen);
	static native void cfmget_extradots(IntBuffer status, int dbkey, IntBuffer xdots);
	static native void cfmglen(IntBuffer status, int dbkey, IntBuffer deslen, IntBuffer doclen);
	static native void cfmgnam(IntBuffer status, int dbkey, ByteBuffer objnam, ByteBuffer basnam);
	static native void cfmgsln(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer length);
	static native void cfmgtali(IntBuffer status, int dbkey, ByteBuffer objnam, ByteBuffer aliass, int inlen, IntBuffer outlen);
	static native void cfmgtaso(IntBuffer status, int dbkey, ByteBuffer objnam, ByteBuffer assoc, int inlen, IntBuffer outlen);
	static native void cfmgtatt_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer atttyp, ByteBuffer attnam, ByteBuffer value, int inlen, IntBuffer outlen);
	static native void cfmgtatt_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer atttyp, ByteBuffer attnam, DoubleBuffer value, int inlen, IntBuffer outlen);
	static native void cfmgtatt_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer atttyp, ByteBuffer attnam, FloatBuffer value, int inlen, IntBuffer outlen);
	static native void cfmgtatt_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer atttyp, ByteBuffer attnam, IntBuffer value, int inlen, IntBuffer outlen);
	static native void cfmgtnl(IntBuffer status, int dbkey, ByteBuffer objnam, int index, ByteBuffer strval, int inlen, IntBuffer outlen);
	static native void cfmgtstr(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range_, ByteBuffer strval, IntBuffer ismiss, int inlen, IntBuffer outlen);
	static native void cfmidat(IntBuffer status, int freq, IntBuffer date, ByteBuffer datstr, ByteBuffer image, int fmonth, int flabel, int centry);
	static void cfmini(IntBuffer status) {
		FAME_String product = new FAME_String("fame_prodq");
		cfmappl(status,ByteBuffer.wrap(product.getBytes()));
	}
	static native void cfminwc(IntBuffer status, int dbkey, ByteBuffer wilnam);
	static native void cfmisbm(IntBuffer status, int value, IntBuffer ismiss);
	static native void cfmisdm(IntBuffer status, int value, IntBuffer ismiss);
	static native void cfmislp(IntBuffer status, int year, IntBuffer leap);
	static native void cfmisnm(IntBuffer status, float value, IntBuffer ismiss);
	static native void cfmispm(IntBuffer status, double value, IntBuffer ismiss);
	static native void cfmissm(IntBuffer status, ByteBuffer value, IntBuffer ismiss);
	static native void cfmlali(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer alilen);
	static native void cfmlaso(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer asolen);
	static native void cfmlatt(IntBuffer status, int dbkey, ByteBuffer objnam, int atttyp, ByteBuffer attnam, IntBuffer attlen);
	static native void cfmldat(IntBuffer status, int freq, IntBuffer date, ByteBuffer datstr, int fmonth, int flabel, int centry);
	static native void cfmlerr(IntBuffer status, IntBuffer len);
	static native void cfmlsts(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range_, IntBuffer lenary);
	static native void cfmncnt(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer length);
	static native void cfmnlen(IntBuffer status, int dbkey, ByteBuffer objnam, int index, IntBuffer length);
	static native void cfmnwob(IntBuffer status, int dbkey, ByteBuffer objnam, int class_, int freq, int type, int basis, int observ);
	static native void cfmnxwc(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer class_, IntBuffer type, IntBuffer freq);
	static native void cfmopcn(IntBuffer status, IntBuffer connkey, ByteBuffer service, ByteBuffer hostname, ByteBuffer username, ByteBuffer password);
	static native void cfmopdb(IntBuffer status, IntBuffer dbkey, ByteBuffer dbname, int mode);
	static native void cfmopdc(IntBuffer status, IntBuffer dbkey, ByteBuffer dbname, int mode, int connkey);
	static native void cfmoprc(IntBuffer status, IntBuffer dbkey, int connkey);
	static native void cfmopre(IntBuffer status, IntBuffer dbkey, ByteBuffer svname);
	static native void cfmopwk(IntBuffer status, IntBuffer dbkey);
	static native void cfmosiz(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer class_, IntBuffer type, IntBuffer freq, IntBuffer fyear, IntBuffer fprd, IntBuffer lyear, IntBuffer lprd);
	static native void cfmpack(IntBuffer status, int dbkey);
	static native void cfmpdat(IntBuffer status, int freq, IntBuffer date, int year, int period);
	static native void cfmpfrq(IntBuffer status, IntBuffer freq, int base, int nunits, int year, int month);
	static native void cfmpind(IntBuffer status, int freq, IntBuffer count);
	static native void cfmpinm(IntBuffer status, int freq, int year, int month, IntBuffer count);
	static native void cfmpiny(IntBuffer status, int freq, int year, IntBuffer count);
	static native void cfmpodb(IntBuffer status, int dbkey);
	static native void cfmrdfa_f(IntBuffer status, int dbkey, ByteBuffer objnam, int wntobs, IntBuffer syear, IntBuffer sprd, IntBuffer gotobs, DoubleBuffer valary, int tmiss, DoubleBuffer mistt);
	static native void cfmrdfa_f(IntBuffer status, int dbkey, ByteBuffer objnam, int wntobs, IntBuffer syear, IntBuffer sprd, IntBuffer gotobs, FloatBuffer valary, int tmiss, FloatBuffer mistt);
	static native void cfmrdfa_f(IntBuffer status, int dbkey, ByteBuffer objnam, int wntobs, IntBuffer syear, IntBuffer sprd, IntBuffer gotobs, IntBuffer valary, int tmiss, IntBuffer mistt);
	static native void cfmrdfm(IntBuffer status, int dbkey, ByteBuffer objnam, ByteBuffer source, int ilen, IntBuffer olen);
	static native void cfmrmev(IntBuffer status, int dbkey, ByteBuffer expr, ByteBuffer optns, int wdbkey, ByteBuffer objnam);
	static native void cfmrnob(IntBuffer status, int dbkey, ByteBuffer srcnam, ByteBuffer tarnam);
	static native void cfmrrng_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range_, DoubleBuffer valary, int tmiss, DoubleBuffer mistt);
	static native void cfmrrng_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range_, FloatBuffer valary, int tmiss, FloatBuffer mistt);
	static native void cfmrrng_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range_, IntBuffer valary, int tmiss, IntBuffer mistt);
	static native void cfmrsdb(IntBuffer status, int dbkey);
	static native void cfmsali(IntBuffer status, int dbkey, ByteBuffer objnam, ByteBuffer aliass);
	static native void cfmsaso(IntBuffer status, int dbkey, ByteBuffer objnam, ByteBuffer assoc);
	static native void cfmsatt_f(IntBuffer status, int dbkey, ByteBuffer objnam, int atttyp, ByteBuffer attnam, ByteBuffer value);
	static native void cfmsatt_f(IntBuffer status, int dbkey, ByteBuffer objnam, int atttyp, ByteBuffer attnam, DoubleBuffer value);
	static native void cfmsatt_f(IntBuffer status, int dbkey, ByteBuffer objnam, int atttyp, ByteBuffer attnam, FloatBuffer value);
	static native void cfmsatt_f(IntBuffer status, int dbkey, ByteBuffer objnam, int atttyp, ByteBuffer attnam, IntBuffer value);
	static native void cfmsbas(IntBuffer status, int dbkey, ByteBuffer objnam, int basis);
	static native void cfmsbm(IntBuffer status, int nctran, int ndtran, int natran, IntBuffer bmistt);
	static native void cfmsdes(IntBuffer status, int dbkey, ByteBuffer objnam, ByteBuffer desc);
	static native void cfmsdm(IntBuffer status, int nctran, int ndtran, int natran, IntBuffer dmistt);
	static native void cfmsdoc(IntBuffer status, int dbkey, ByteBuffer objnam, ByteBuffer doc);
	static native void cfmset_dimension(IntBuffer status, int dbkey, int dimen);
	static native void cfmset_extradots(IntBuffer status, int dbkey, int xdots);
	static native void cfmsfis(IntBuffer status, int freq, IntBuffer syear, IntBuffer sprd, IntBuffer eyear, IntBuffer eprd, IntBuffer range_, IntBuffer numobs, int fmonth, int flabel);
	static native void cfmsinp(IntBuffer status, ByteBuffer cmd);
	static native void cfmsnm(IntBuffer status, float nctran, float ndtran, float natran, FloatBuffer nmistt);
	static native void cfmsobs(IntBuffer status, int dbkey, ByteBuffer objnam, int observ);
	static native void cfmsopt(IntBuffer status, ByteBuffer optnam, ByteBuffer optval);
	static native void cfmspm(IntBuffer status, double nctran, double ndtran, double natran, DoubleBuffer pmistt);
	static native void cfmspos(IntBuffer status, int flag);
	static native void cfmsrng(IntBuffer status, int freq, IntBuffer syear, IntBuffer sprd, IntBuffer eyear, IntBuffer eprd, IntBuffer range_, IntBuffer numobs);
	static native void cfmssln(IntBuffer status, int dbkey, ByteBuffer objnam, int length);
	static native void cfmtdat(IntBuffer status, int freq, IntBuffer date, int hour, int minute, int second, int ddate);
	static native void cfmtody(IntBuffer status, int freq, IntBuffer date);
	static native void cfmufrq(IntBuffer status, int freq, IntBuffer base, IntBuffer nunits, IntBuffer year, IntBuffer month);
	static native void cfmver(IntBuffer status, FloatBuffer ver);
	static native void cfmwhat(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer class_, IntBuffer type, IntBuffer freq, IntBuffer basis, IntBuffer observ, IntBuffer fyear, IntBuffer fprd, IntBuffer lyear, IntBuffer lprd, IntBuffer cyear, IntBuffer cmonth, IntBuffer cday, IntBuffer myear, IntBuffer mmonth, IntBuffer mday, ByteBuffer desc, ByteBuffer doc);
	static native void cfmwkdy(IntBuffer status, int freq, int date, IntBuffer wkdy);
	static native void cfmwrmt_f(IntBuffer status, int dbkey, ByteBuffer objnam, int objtyp, IntBuffer range_, DoubleBuffer valary, int tmiss, DoubleBuffer mistt);
	static native void cfmwrmt_f(IntBuffer status, int dbkey, ByteBuffer objnam, int objtyp, IntBuffer range_, FloatBuffer valary, int tmiss, FloatBuffer mistt);
	static native void cfmwrmt_f(IntBuffer status, int dbkey, ByteBuffer objnam, int objtyp, IntBuffer range_, IntBuffer valary, int tmiss, IntBuffer mistt);
	static native void cfmwrng_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range_, DoubleBuffer valary, int tmiss, DoubleBuffer mistt);
	static native void cfmwrng_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range_, FloatBuffer valary, int tmiss, FloatBuffer mistt);
	static native void cfmwrng_f(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range_, IntBuffer valary, int tmiss, IntBuffer mistt);
	static native void cfmwstr(IntBuffer status, int dbkey, ByteBuffer objnam, IntBuffer range_, ByteBuffer strval, int ismiss, int length);
	static native void cfmwtnl(IntBuffer status, int dbkey, ByteBuffer objnam, int index, ByteBuffer strval);
	static native int fame_biweekday(int freq, long date, IntBuffer biweekday);
	static native int fame_create_formula(int dbkey, ByteBuffer oname, ByteBuffer value);
	static native int fame_create_global_formula(int dbkey, ByteBuffer oname, ByteBuffer value);
	static native int fame_current_time(int freq, LongBuffer date);
	static native int fame_date_missing_type(long date, IntBuffer missing_type);
	static native int fame_date_to_literal(int freq, long date, ByteBuffer literal, int end_month, int label_year, int inlen, IntBuffer outlen);
	static native int fame_dateof(int from_freq, long from_date, int select, int to_freq, LongBuffer to_date, int relate);
	static native int fame_day_to_index(int freq, LongBuffer date, int year, int month, int day, int relate);
	static native int fame_dimchar(ByteBuffer dimchar, int dimnum);
	static native int fame_dimnum(IntBuffer dimnum, char dimchar);
	static native int fame_expiration_date(LongBuffer date);
	static native int fame_format_date(int freq, long date, ByteBuffer string, ByteBuffer image, int end_month, int label_year, int inlen, IntBuffer outlen);
	static native int fame_free_wildcard(int wildkey);
	static native int fame_get_base_frequency(IntBuffer base_freq, int freq);
	static native int fame_get_boolean_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, IntBuffer value);
	static native int fame_get_booleans(int dbkey, ByteBuffer objnam, LongBuffer range_, IntBuffer valary);
	static native int fame_get_date_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, LongBuffer value, IntBuffer freq);
	static native int fame_get_dates(int dbkey, ByteBuffer objnam, LongBuffer range_, LongBuffer valary);
	static native int fame_get_db_dates(int dbkey, LongBuffer cdate, LongBuffer mdate);
	static native int fame_get_dbversion(int dbkey, IntBuffer dbversion);
	static native int fame_get_namelist_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, ByteBuffer value, int inlen, IntBuffer outlen);
	static native int fame_get_next_wildcard(int wildkey, ByteBuffer objname, IntBuffer obj_class, IntBuffer type, IntBuffer freq, LongBuffer start, LongBuffer end, int inlen, IntBuffer outlen);
	static native int fame_get_numeric_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, FloatBuffer value);
	static native int fame_get_numerics(int dbkey, ByteBuffer objnam, LongBuffer range_, FloatBuffer valary);
	static native int fame_get_precision_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, DoubleBuffer value);
	static native int fame_get_precisions(int dbkey, ByteBuffer objnam, LongBuffer range_, DoubleBuffer valary);
	static native int fame_get_range_numobs(LongBuffer range_, IntBuffer numobs);
	static native int fame_get_real_name(int dbkey, ByteBuffer objname, ByteBuffer basename, int inlen, IntBuffer outlen);
	static native int fame_get_server_input(ByteBuffer buffer, int buffer_size, IntBuffer input_size);
	static native int fame_get_string_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, ByteBuffer value, int inlen, IntBuffer outlen);
	static native int fame_index_to_day(int freq, long date, IntBuffer year, IntBuffer month, IntBuffer day);
	static native int fame_index_to_int(LongBuffer indexes64, IntBuffer indexes32, int numobs);
	static native int fame_index_to_time(int freq, long date, IntBuffer year, IntBuffer month, IntBuffer day, IntBuffer hour, IntBuffer minute, IntBuffer second, IntBuffer millisecond);
	static native int fame_index_to_year_period(int freq, long date, IntBuffer year, IntBuffer period);
	static native int fame_info(int dbkey, ByteBuffer oname, IntBuffer oclass, IntBuffer type, IntBuffer freq, LongBuffer findex, LongBuffer lindex, IntBuffer basis, IntBuffer observ, LongBuffer cdate, LongBuffer mdate, ByteBuffer desc, int indesclen, IntBuffer outdesclen, ByteBuffer doc, int indoclen, IntBuffer outdoclen);
	static native int fame_init_range_from_end_numobs(LongBuffer range_, int freq, long end, int numobs);
	static native int fame_init_range_from_indexes(LongBuffer range_, int freq, long start, long end);
	static native int fame_init_range_from_start_numobs(LongBuffer range_, int freq, long start, int numobs);
	static native int fame_init_wildcard(int dbkey, IntBuffer wildkey, ByteBuffer wildname, int wildonly, ByteBuffer wildstart);
	static native int fame_int_to_index(IntBuffer indexes32, LongBuffer indexes64, int numobs);
	static native int fame_len_strings(int dbkey, ByteBuffer objnam, LongBuffer range_, IntBuffer lenary);
	static native int fame_literal_to_date(int freq, LongBuffer date, ByteBuffer literal, int end_month, int label_year, int century);
	static native int fame_modify_formula(int dbkey, ByteBuffer oname, ByteBuffer value);
	static native int fame_parse_frequency(IntBuffer freq, ByteBuffer frequency_spec);
	static native int fame_quick_info(int dbkey, ByteBuffer oname, IntBuffer oclass, IntBuffer type, IntBuffer freq, LongBuffer findex, LongBuffer lindex);
	static native int fame_remexec(int dbkey, ByteBuffer command, int wdbkey, ByteBuffer objnam);
	static native int fame_scan_date(int freq, LongBuffer date, ByteBuffer string, ByteBuffer image, int end_month, int label_year, int century);
	static native int fame_set_boolean_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, int value);
	static native int fame_set_date_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, long value, int freq);
	static native int fame_set_namelist_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, ByteBuffer value);
	static native int fame_set_numeric_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, float value);
	static native int fame_set_precision_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, double value);
	static native int fame_set_string_attribute(int dbkey, ByteBuffer objnam, ByteBuffer attnam, ByteBuffer value);
	static native int fame_time_to_index(int freq, LongBuffer date, int year, int month, int day, int hour, int minute, int second, int millisecond, int relate);
	static native int fame_type_to_string(int type, ByteBuffer type_spec, int inlen, IntBuffer outlen);
	static native int fame_weekday(int freq, long date, IntBuffer weekday);
	static native int fame_write_booleans(int dbkey, ByteBuffer objnam, LongBuffer range_, IntBuffer valary);
	static native int fame_write_dates(int dbkey, ByteBuffer objnam, LongBuffer range_, int value_type, LongBuffer valary);
	static native int fame_write_numerics(int dbkey, ByteBuffer objnam, LongBuffer range_, FloatBuffer valary);
	static native int fame_write_precisions(int dbkey, ByteBuffer objnam, LongBuffer range_, DoubleBuffer valary);
	static native int fame_year_period_to_index(int freq, LongBuffer date, int year, int period);
}
