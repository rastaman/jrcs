package org.apache.commons.jrcs.diff;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.jrcs.util.ToString;


/**
 * A Revision holds the series of deltas that describe the differences
 * between to revisions of a text.
 *
 * @version $Id: Revision.java 145971 2002-05-28 16:45:41Z jvanzyl $
 * @author <a href="mailto:juanco@suigeneris.org">Juanco Anez</a>
 * @see Delta
 * @see Diff
 * @see Chunk
 */
public class Revision
        extends ToString
{

    List deltas_ = new LinkedList();

    
    /**
     * Creates an empty Revision.
     */
    public Revision()
    {
    }


    /**
     * Adds a delta to this revision.
     * @param delta the {@link Delta Delta} to add.
     */
    public synchronized void addDelta(Delta delta)
    {
        if (delta == null)
        {
            throw new IllegalArgumentException("new delta is null");
        }
            /*!!!
        if (deltas_.size() > 0)
        {
            Delta prev = (Delta) deltas_.get(deltas_.size() - 1);
            if (prev.original != null && delta.original != null
            && (prev.original.from() > delta.original.from()))
                throw new IllegalArgumentException("original.from > new.from");

            if (prev.revised != null && delta.revised != null
            && (prev.revised.from() > delta.revised.from()))
                throw new IllegalArgumentException("revised.from > revised.to");
        }
            */
        deltas_.add(delta);
    }


    /**
     * Retrieves a delta from this revision by position.
     * @param i the position of the delta to retrieve.
     * @return the specified delta
     */
    public Delta getDelta(int i)
    {
        return (Delta) deltas_.get(i);
    }

    /**
     * Returns the number of deltas in this revision.
     */
    public int size()
    {
        return deltas_.size();
    }

    /**
     * Applies the series of deltas in this revision as patches to
     * the given text.
     * @param src the text to patch, which the method doesn't change. 
     * @return the resulting text after the patches have been applied.
     * @throws PatchFailedException if any of the patches cannot be applied.
     */
    public Object[] patch(Object[] src) throws PatchFailedException
    {
        List target = new ArrayList(Arrays.asList(src));
        applyTo(target);
        return target.toArray();
    }

    /**
     * Applies the series of deltas in this revision as patches to
     * the given text.
     * @param target the text to patch.
     * @throws PatchFailedException if any of the patches cannot be applied.
     */
    public synchronized void applyTo(List target) throws PatchFailedException
    {
        ListIterator i = deltas_.listIterator(deltas_.size());
        while (i.hasPrevious())
        {
            Delta delta = (Delta) i.previous();
            delta.patch(target);
        }
    }

    /**
     * Converts this revision into its Unix diff style string representation.
     * @param s a {@link StringBuffer StringBuffer} to which the string
     * representation will be appended.
     */
    public synchronized void toString(StringBuffer s)
    {
        Iterator i = deltas_.iterator();
        while (i.hasNext())
        {
            ((Delta) i.next()).toString(s);
        }
    }

    /**
     * Converts this revision into its RCS style string representation.
     * @param s a {@link StringBuffer StringBuffer} to which the string
     * representation will be appended.
     * @param EOL the string to use as line separator.
     */
    public synchronized void toRCSString(StringBuffer s, String EOL)
    {
        Iterator i = deltas_.iterator();
        while (i.hasNext())
        {
            ((Delta) i.next()).toRCSString(s, EOL);
        }
    }

    /**
     * Converts this revision into its RCS style string representation.
     * @param s a {@link StringBuffer StringBuffer} to which the string
     * representation will be appended.
     */
    public void toRCSString(StringBuffer s)
    {
        toRCSString(s, Diff.NL);
    }

    /**
     * Converts this delta into its RCS style string representation.
     * @param EOL the string to use as line separator.
     */
    public String toRCSString(String EOL)
    {
        StringBuffer s = new StringBuffer();
        toRCSString(s, EOL);
        return s.toString();
    }

    /**
     * Converts this delta into its RCS style string representation 
     * using the default line separator.
     */
    public String toRCSString()
    {
        return toRCSString(Diff.NL);
    }

}
