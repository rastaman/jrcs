/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache Maven" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache Maven", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package org.apache.commons.jrcs.rcs;

import java.text.Format;
import java.text.MessageFormat;
import java.util.regex.Pattern;
/*
import org.apache.oro.text.perl.Perl5Util;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.StringSubstitution;
import org.apache.oro.text.regex.Substitution;
import org.apache.oro.text.regex.Util;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.MalformedPatternException;
*/
/**
 * Formatter for the RCS keywords. It is intended as an helper class to
 * replace the use of gnu.regexp. This class is NOT threadsafe.
 *
 * @author <a href="mailto:sbailliez@apache.org">Stephane Bailliez</a>
 */
final class KeywordsFormatJdk
{
    //WARNING: Do not remove the string concatenations
    //         or CVS will mangle the strings on check in/out.
    final Format Header_FORMAT =
            new MessageFormat("$" + "Header: {1} {2} {3, date,yyyy/MM/dd HH:mm:ss} {4} {5} " + "$");
    final Format Id_FORMAT =
            new MessageFormat("$" + "Id: {1} {2} {3, date,yyyy/MM/dd HH:mm:ss} {4} {5} " + "$");
    final Format RCSFile_FORMAT =
            new MessageFormat("$" + "RCSfile: {1} " + "$");
    final Format Revision_FORMAT =
            new MessageFormat("$" + "Revision: {2} " + "$");
    final Format Date_FORMAT =
            new MessageFormat("$" + "Date: {3, date,yyyy/MM/dd HH:mm:ss} " + "$");
    final Format Author_FORMAT =
            new MessageFormat("$" + "Author: {4} " + "$");
    final Format State_FORMAT =
            new MessageFormat("$" + "State: {5} " + "$");
    final Format Locker_FORMAT =
            new MessageFormat("$" + "Locker: {6} " + "$");
    final Format Source_FORMAT =
            new MessageFormat("$" + "Source: {0} " + "$");

    private final Pattern ID_RE;
    private final Pattern HEADER_RE;
    private final Pattern SOURCE_RE;
    private final Pattern RCSFILE_RE;
    private final Pattern REVISION_RE;
    private final Pattern DATE_RE;
    private final Pattern AUTHOR_RE;
    private final Pattern STATE_RE;
    private final Pattern LOCKER_RE;

    KeywordsFormatJdk()
    {

            ID_RE = Pattern.compile("\\$Id(:[^\\$]*)?\\$");
            HEADER_RE = Pattern.compile("\\$Header(:[^\\$]*)?\\$");
            SOURCE_RE = Pattern.compile("\\$Source(:[^\\$]*)?\\$");
            RCSFILE_RE = Pattern.compile("\\$RCSfile(:[^\\$]*)?\\$");
            REVISION_RE = Pattern.compile("\\$Revision(:[^\\$]*)?\\$");
            DATE_RE = Pattern.compile("\\$Date(:[^\\$]*)?\\$");
            AUTHOR_RE = Pattern.compile("\\$Author(:[^\\$]*)?\\$");
            STATE_RE = Pattern.compile("\\$State(:[^\\$]*)?\\$");
            LOCKER_RE = Pattern.compile("\\$Locker(:[^\\$]*)?\\$");

    }

    /**
     * update the given text made of RCS keywords with the appropriate
     * revision info.
     * @param text the input text containing the RCS keywords.
     * @param revisionInfo the revision information.
     * @return the formatted text with the RCS keywords.
     */
    String update(String text, Object[] revisionInfo)
    {
        String data = text;
        data = substitute(data, ID_RE, Id_FORMAT.format(revisionInfo));
        data = substitute(data, HEADER_RE, Header_FORMAT.format(revisionInfo));
        data = substitute(data, SOURCE_RE, Source_FORMAT.format(revisionInfo));
        data = substitute(data, RCSFILE_RE, RCSFile_FORMAT.format(revisionInfo));
        data = substitute(data, REVISION_RE, Revision_FORMAT.format(revisionInfo));
        data = substitute(data, DATE_RE, Date_FORMAT.format(revisionInfo));
        data = substitute(data, AUTHOR_RE, Author_FORMAT.format(revisionInfo));
        data = substitute(data, STATE_RE, State_FORMAT.format(revisionInfo));
        data = substitute(data, LOCKER_RE, Locker_FORMAT.format(revisionInfo));
        //@TODO: should do something about Name and Log
        return data;
    }

    /**
     * Reinitialize all RCS keywords match.
     * @param text the text to look for RCS keywords.
     * @return the text with initialized RCS keywords.
     */
    String reset(String text)
    {
        //WARNING: Do not remove the string concatenations 
        //         or CVS will mangle the strings on check in/out.
        String data = text;
        data = substitute(data, ID_RE, '$' + "Id$");
        data = substitute(data, HEADER_RE, '$' + "Header$");
        data = substitute(data, SOURCE_RE, '$' + "Source$");
        data = substitute(data, RCSFILE_RE, '$' + "RCSfile$");
        data = substitute(data, REVISION_RE, '$' + "Revision$");
        data = substitute(data, DATE_RE, '$' + "Date$");
        data = substitute(data, AUTHOR_RE, '$' + "Author$");
        data = substitute(data, STATE_RE, '$' + "State$");
        data = substitute(data, LOCKER_RE, '$' + "Locker$");
        //@TODO: should do something about Name and Log
        return data;
    }


    /**
     * Helper method for substitution that will substitute all matches of
     * a given pattern.
     * @param input the text to look for substitutions.
     * @param pattern the pattern to replace in the input text.
     * @param substitution the string to use as a replacement for the pattern.
     * @return the text with the subsituted value.
     */
    private final String substitute(String input, Pattern pattern, String substitution)
    {
    	Pattern p = Pattern.compile(pattern.toString()+"/"+substitution+"/sg");
    	out("Pattern="+p.toString()+"/input="+input);
        final String output = p.matcher(input).group(1);
        return output;
    }

    private void out( String s ) {
    	System.out.println( s );
    }
}
