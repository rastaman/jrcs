package org.apache.commons.jrcs.rcs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RegExpTest extends TestCase {

	public String input = "$Id: KeywordsFormatTest.java 145971 2002-05-28 16:45:41Z jvanzyl $\n$Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//jrcs/src/test/org/apache/commons/jrcs/rcs/Attic/KeywordsFormatTest.java,v 1.1 2002/05/28 16:45:43 jvanzyl Exp $";
/*
 * 
Pattern=\$Id(:[^\$]*)?\$
Input=


	public String pattern = "\\$Id(:[^\$]*)?\\$/$Id$/sg";
	
			Input=$Id: KeywordsFormatTest.java 145971 2002-05-28 16:45:41Z jvanzyl $
			$Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//jrcs/src/test/org/apache/commons/jrcs/rcs/Attic/KeywordsFormatTest.java,v 1.1 2002/05/28 16:45:43 jvanzyl Exp $
			$Source: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//jrcs/src/test/org/apache/commons/jrcs/rcs/Attic/KeywordsFormatTest.java,v $
			$RCSfile: KeywordsFormatTest.java,v $
			$Revision: 1.1 $
			$Date: 2002-05-28 18:45:41 +0200 (Mar, 28 mai 2002) $
			$Author: jvanzyl $
			$State: Exp $
			$Locker:  $

			Substitution=$Id$
			Output=null	*/
	public void testRegex() {
		String pt = "\\$Id(:[^\\$]*)?\\$";
		String $ = Pattern.quote("$");
		//String pt2 = "^.*" + $ + "Id.*" + $ + ".*$";
		String pt2 = ".*Id.*";
		String input2= "$Id: KeywordsFormatTest.java 145971 2002-05-28 16:45:41Z jvanzyl $\n$Header:";
		Pattern p = Pattern.compile(pt2, Pattern.MULTILINE );
		out("Seed="+pt);
		out("Pattern="+p.toString());
		out("Input="+input);
		Matcher m = p.matcher(input);
		Assert.assertTrue( m.lookingAt() );
	}
	
    private void out( String s ) {
    	System.out.println( s );
    }
}
