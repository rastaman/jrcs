/* Generated By:JavaCC: Do not edit this line. ArchiveParserConstants.java */
package org.apache.commons.jrcs.rcs;

public interface ArchiveParserConstants {

  int EOF = 0;
  int ACCESS = 6;
  int AUTHOR = 7;
  int BRANCH = 8;
  int BRANCHES = 9;
  int COMMENT = 10;
  int DATE = 11;
  int DESC = 12;
  int EXPAND = 13;
  int HEAD = 14;
  int LOCKS = 15;
  int LOG = 16;
  int NEXT = 17;
  int STATE = 18;
  int STRICT = 19;
  int SYMBOLS = 20;
  int TEXT = 21;
  int ID = 22;
  int SYM = 23;
  int IDCHAR = 24;
  int STRING = 25;
  int LETTER = 26;
  int DIGIT = 27;
  int NUM = 28;

  int DEFAULT = 0;
  int PRE_DELTA = 1;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\n\"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\f\"",
    "\"access\"",
    "\"author\"",
    "\"branch\"",
    "\"branches\"",
    "\"comment\"",
    "\"date\"",
    "\"desc\"",
    "\"expand\"",
    "\"head\"",
    "\"locks\"",
    "\"log\"",
    "\"next\"",
    "\"state\"",
    "\"strict\"",
    "\"symbols\"",
    "\"text\"",
    "<ID>",
    "<SYM>",
    "<IDCHAR>",
    "<STRING>",
    "<LETTER>",
    "<DIGIT>",
    "<NUM>",
    "\";\"",
    "\":\"",
    "\".\"",
  };

}
