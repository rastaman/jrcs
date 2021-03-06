/* Generated By:JavaCC: Do not edit this line. ArchiveParser.java */
package org.apache.commons.jrcs.rcs;

import java.util.Map;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Parses an RCS/CVS style version control archive into an Archive.
 * This class is NOT thread safe.
 * 
 * @author <a href="mailto:juanco@suigeneris.org">Juanco Anez</a>
 * @version $Id: ArchiveParser.java 145971 2002-05-28 16:45:41Z jvanzyl $
 * @see Archive
 */
class ArchiveParser implements ArchiveParserConstants {

  static final String ident = "RCS ArchiveParser Parser $version$:";

  public static void main(String args[]) {
    ArchiveParser parser;
    if (args.length == 0)
    {
      System.out.println(ident + "  Reading from standard input . . .");
      parser = new ArchiveParser(System.in);
    }
    else if (args.length == 1)
    {
      System.out.println(ident + "  Reading from file " + args[0] + " . . .");
      try
      {
        parser = new ArchiveParser(new FileInputStream(args[0]));
      }
      catch (java.io.FileNotFoundException e)
      {
        System.out.println(ident + "  File " + args[0] + " not found.");
        return;
      }
    }
    else
    {
      System.out.println(ident+"  Usage is one of:");
      System.out.println("         java ArchiveParser < inputfile");
      System.out.println("OR");
      System.out.println("         java ArchiveParser inputfile");
      return;
    }
    parser.parse();
  }

  public static void load(Archive arc, InputStream input) throws ParseException
  {
      ArchiveParser parser = new ArchiveParser(input);
      parser.archive(arc);
  }

  public static void load(Archive arc, String fname) throws FileNotFoundException, ParseException
  {
    load(arc, new FileInputStream(fname) );
  }

  public void parse()
  {
    try
    {
      archive(null);
      System.out.println("RCS ArchiveParser Parser version 1.1:  RCS ArchiveParser parsed successfully.");
    }
    catch (ParseException e)
    {
      System.out.println("RCS ArchiveParser Parser version 1.1:  Encountered errors during parse.");
    }
  }

/**
* PARSER STARTS HERE
*/
  final public void archive(Archive arc) throws ParseException {
    trace_call("archive");
    try {
      admin(arc);
      label_1:
      while (true) {
        switch (jj_nt.kind) {
        case NUM:
          ;
          break;
        default:
          break label_1;
        }
        delta(arc);
      }
      desc(arc);
      label_2:
      while (true) {
        switch (jj_nt.kind) {
        case NUM:
          ;
          break;
        default:
          break label_2;
        }
        text(arc);
      }
      jj_consume_token(0);
    } finally {
      trace_return("archive");
    }
  }

  final public void admin(Archive arc) throws ParseException {
    trace_call("admin");
    try {
      head(arc);
      switch (jj_nt.kind) {
      case BRANCH:
        branch(arc);
        break;
      default:
        ;
      }
      access(arc);
      symbols(arc);
      locks(arc);
      optionals(arc);
    } finally {
      trace_return("admin");
    }
  }

  final public void optionals(Archive arc) throws ParseException {
    trace_call("optionals");
    try {
      label_3:
      while (true) {
        switch (jj_nt.kind) {
        case COMMENT:
        case EXPAND:
        case ID:
          ;
          break;
        default:
          break label_3;
        }
        switch (jj_nt.kind) {
        case COMMENT:
          comment(arc);
          break;
        case EXPAND:
          expand(arc);
          break;
        case ID:
          newPhrase(arc.phrases);
          break;
        default:
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    } finally {
      trace_return("optionals");
    }
  }

  final public void newPhrases(Map map) throws ParseException {
    trace_call("newPhrases");
    try {
      label_4:
      while (true) {
        switch (jj_nt.kind) {
        case ID:
          ;
          break;
        default:
          break label_4;
        }
        newPhrase(map);
      }
    } finally {
      trace_return("newPhrases");
    }
  }

  final public void head(Archive arc) throws ParseException {
    trace_call("head");
    try {
    Version v;
      jj_consume_token(HEAD);
      switch (jj_nt.kind) {
      case NUM:
        v = version();
                           arc.setHead(v);
        break;
      default:
        ;
      }
      jj_consume_token(29);
    } finally {
      trace_return("head");
    }
  }

  final public void branch(Archive arc) throws ParseException {
    trace_call("branch");
    try {
  Version v;
      jj_consume_token(BRANCH);
      switch (jj_nt.kind) {
      case NUM:
        v = version();
                             arc.setBranch(v);
        break;
      default:
        ;
      }
      jj_consume_token(29);
    } finally {
      trace_return("branch");
    }
  }

  final public void access(Archive arc) throws ParseException {
    trace_call("access");
    try {
    String name;
      jj_consume_token(ACCESS);
      label_5:
      while (true) {
        switch (jj_nt.kind) {
        case ID:
          ;
          break;
        default:
          break label_5;
        }
        name = id();
                           arc.addUser(name);
      }
      jj_consume_token(29);
    } finally {
      trace_return("access");
    }
  }

  final public void symbols(Archive arc) throws ParseException {
    trace_call("symbols");
    try {
    String  s;
    Version v;
      jj_consume_token(SYMBOLS);
      label_6:
      while (true) {
        switch (jj_nt.kind) {
        case ID:
        case SYM:
          ;
          break;
        default:
          break label_6;
        }
        s = sym();
        jj_consume_token(30);
        v = version();
                                            arc.addSymbol(s, v);
      }
      jj_consume_token(29);
    } finally {
      trace_return("symbols");
    }
  }

  final public void locks(Archive arc) throws ParseException {
    trace_call("locks");
    try {
    String  name;
    Version v;
      jj_consume_token(LOCKS);
      label_7:
      while (true) {
        switch (jj_nt.kind) {
        case ID:
          ;
          break;
        default:
          break label_7;
        }
        name = id();
        jj_consume_token(30);
        v = version();
                                            arc.addLock(name, v);
      }
      jj_consume_token(29);
      switch (jj_nt.kind) {
      case STRICT:
        jj_consume_token(STRICT);
        jj_consume_token(29);
               arc.setStrictLocking(true);
        break;
      default:
        ;
      }
    } finally {
      trace_return("locks");
    }
  }

  final public void comment(Archive arc) throws ParseException {
    trace_call("comment");
    try {
  String s;
      jj_consume_token(COMMENT);
      switch (jj_nt.kind) {
      case STRING:
        s = string();
                            arc.setComment(s);
        break;
      default:
        ;
      }
      jj_consume_token(29);
    } finally {
      trace_return("comment");
    }
  }

  final public void expand(Archive arc) throws ParseException {
    trace_call("expand");
    try {
 String s;
      jj_consume_token(EXPAND);
      switch (jj_nt.kind) {
      case STRING:
        s = string();
                           arc.setExpand(s);
        break;
      default:
        ;
      }
      jj_consume_token(29);
    } finally {
      trace_return("expand");
    }
  }

  final public void newPhrase(Map map) throws ParseException {
    trace_call("newPhrase");
    try {
  String key;
  String value;
  StringBuffer values = new StringBuffer();
      key = id();
      label_8:
      while (true) {
        switch (jj_nt.kind) {
        case ID:
        case STRING:
        case NUM:
          ;
          break;
        default:
          break label_8;
        }
        value = word();
                     values.append(" " + value);
      }
      jj_consume_token(29);
    if (map != null) map.put(key, values.toString());
    } finally {
      trace_return("newPhrase");
    }
  }

  final public String word() throws ParseException {
    trace_call("word");
    try {
  String result;
      if (jj_2_1(2)) {
        result = pair();
      } else {
        switch (jj_nt.kind) {
        case ID:
        case STRING:
        case NUM:
          result = simpleWord();
          break;
        default:
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    {if (true) return result;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("word");
    }
  }

  final public String simpleWord() throws ParseException {
    trace_call("simpleWord");
    try {
    String  result;
    Version v;
      switch (jj_nt.kind) {
      case ID:
        result = id();
        break;
      case NUM:
        v = version();
                 result = v.toString();
        break;
      case STRING:
        result = string();
        break;
      default:
        jj_consume_token(-1);
        throw new ParseException();
      }
   {if (true) return result;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("simpleWord");
    }
  }

  final public String pair() throws ParseException {
    trace_call("pair");
    try {
    String left;
    String right;
      left = simpleWord();
      jj_consume_token(30);
      right = simpleWord();
      {if (true) return left + ":" + right;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("pair");
    }
  }

  final public void desc(Archive arc) throws ParseException {
    trace_call("desc");
    try {
  String s;
      jj_consume_token(DESC);
      s = string();
                          arc.setDesc(s);
    } finally {
      trace_return("desc");
    }
  }

  final public void delta(Archive arc) throws ParseException {
    trace_call("delta");
    try {
    Version   v;
    Node      node;
    int[]     d;
    String    s;
      v = version();
       node = arc.newNode(v);
      jj_consume_token(DATE);
      d = date();
                              node.setDate(d);
      jj_consume_token(29);
      jj_consume_token(AUTHOR);
      s = id();
                              node.setAuthor(s);
      jj_consume_token(29);
      jj_consume_token(STATE);
      switch (jj_nt.kind) {
      case ID:
        s = id();
                            node.setState(s);
        break;
      default:
        ;
      }
      jj_consume_token(29);
      jj_consume_token(BRANCHES);
      label_9:
      while (true) {
        switch (jj_nt.kind) {
        case NUM:
          ;
          break;
        default:
          break label_9;
        }
        v = version();
                                 node.addBranch(arc.newBranchNode(v));
      }
      jj_consume_token(29);
      jj_consume_token(NEXT);
      switch (jj_nt.kind) {
      case NUM:
        v = version();
                                 node.setRCSNext(arc.newNode(v));
        break;
      default:
        ;
      }
      jj_consume_token(29);
      newPhrases(node.phrases);
    } finally {
      trace_return("delta");
    }
  }

  final public void text(Archive arc) throws ParseException {
    trace_call("text");
    try {
  Version v;
  Node node;
  String log;
  String txt;
      v = version();
      node = arc.getNode(v);
      jj_consume_token(LOG);
      log = string();
      node.setLog(log);
      newPhrases(node.phrases);
      jj_consume_token(TEXT);
      txt = string();
       node.setText(txt);
    } finally {
      trace_return("text");
    }
  }

  final public String id() throws ParseException {
    trace_call("id");
    try {
                    Token t;
      t = jj_consume_token(ID);
                                             {if (true) return t.image;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("id");
    }
  }

  final public String sym() throws ParseException {
    trace_call("sym");
    try {
  Token t;
      switch (jj_nt.kind) {
      case SYM:
        t = jj_consume_token(SYM);
        break;
      case ID:
        t = jj_consume_token(ID);
        break;
      default:
        jj_consume_token(-1);
        throw new ParseException();
      }
    {if (true) return t.image;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("sym");
    }
  }

  final public Version version() throws ParseException {
    trace_call("version");
    try {
  Version v;
  int   n, r;
      n = num();
    v = new Version(n);
      label_10:
      while (true) {
        switch (jj_nt.kind) {
        case 31:
          ;
          break;
        default:
          break label_10;
        }
        jj_consume_token(31);
        n = num();
                    v.__addBranch(n);
      }
    {if (true) return v;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("version");
    }
  }

  final public int[] date() throws ParseException {
    trace_call("date");
    try {
  int[] n = new int[6];
      n[0] = num();
      jj_consume_token(31);
      n[1] = num();
      jj_consume_token(31);
      n[2] = num();
      jj_consume_token(31);
      n[3] = num();
      jj_consume_token(31);
      n[4] = num();
      jj_consume_token(31);
      n[5] = num();
   {if (true) return n;}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("date");
    }
  }

  final public int num() throws ParseException {
    trace_call("num");
    try {
              Token t;
      t = jj_consume_token(NUM);
                                      {if (true) return Integer.parseInt(t.image);}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("num");
    }
  }

  final public String string() throws ParseException {
    trace_call("string");
    try {
 Token t;
      t = jj_consume_token(STRING);
                 {if (true) return Archive.unquoteString(t.image);}
    throw new Error("Missing return statement in function");
    } finally {
      trace_return("string");
    }
  }

  final private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    return !jj_3_1();
  }

  final private boolean jj_3_1() {
    if (jj_3R_11()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_16() {
    if (jj_scan_token(ID)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_15() {
    if (jj_3R_18()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_14() {
    if (jj_3R_17()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_20() {
    if (jj_scan_token(31)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_13() {
    if (jj_3R_16()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_12() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_13()) {
    jj_scanpos = xsp;
    if (jj_3R_14()) {
    jj_scanpos = xsp;
    if (jj_3R_15()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_18() {
    if (jj_scan_token(STRING)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_17() {
    if (jj_3R_19()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_20()) { jj_scanpos = xsp; break; }
      if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    }
    return false;
  }

  final private boolean jj_3R_11() {
    if (jj_3R_12()) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    if (jj_scan_token(30)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  final private boolean jj_3R_19() {
    if (jj_scan_token(NUM)) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
    return false;
  }

  public ArchiveParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  public Token token, jj_nt;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  public boolean lookingAhead = false;
  private boolean jj_semLA;

  public ArchiveParser(java.io.InputStream stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new ArchiveParserTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  public void ReInit(java.io.InputStream stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  public ArchiveParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new ArchiveParserTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  public ArchiveParser(ArchiveParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  public void ReInit(ArchiveParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken = token;
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    if (token.kind == kind) {
      trace_token(token, "");
      return token;
    }
    jj_nt = token;
    token = oldToken;
    throw generateParseException();
  }

  final private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    return (jj_scanpos.kind != kind);
  }

  final public Token getNextToken() {
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
      trace_token(token, " (in getNextToken)");
    return token;
  }

  final public Token getToken(int index) {
    Token t = lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final public ParseException generateParseException() {
    Token errortok = token.next;
    int line = errortok.beginLine, column = errortok.beginColumn;
    String mess = (errortok.kind == 0) ? tokenImage[0] : errortok.image;
    return new ParseException("Parse error at line " + line + ", column " + column + ".  Encountered: " + mess);
  }

  private int trace_indent = 0;
  private boolean trace_enabled = true;

  final public void enable_tracing() {
    trace_enabled = true;
  }

  final public void disable_tracing() {
    trace_enabled = false;
  }

  final private void trace_call(String s) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Call:   " + s);
    }
    trace_indent = trace_indent + 2;
  }

  final private void trace_return(String s) {
    trace_indent = trace_indent - 2;
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Return: " + s);
    }
  }

  final private void trace_token(Token t, String where) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Consumed token: <" + tokenImage[t.kind]);
      if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
        System.out.print(": \"" + t.image + "\"");
      }
      System.out.println(">" + where);
    }
  }

  final private void trace_scan(Token t1, int t2) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Visited token: <" + tokenImage[t1.kind]);
      if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
        System.out.print(": \"" + t1.image + "\"");
      }
      System.out.println(">; Expected token: <" + tokenImage[t2] + ">");
    }
  }

}
