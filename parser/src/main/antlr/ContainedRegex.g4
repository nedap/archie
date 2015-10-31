grammar ContainedRegex;

regex: '{' REGEX ( SEMICOLON STRING )? '}';
// String parsing

STRING : '"' STRING_CHAR*? '"' ;

//a regexp can only exist between {}. It can optionally have an assumed value, by adding ;"value"
REGEX:  (SLASH_REGEXP | CARET_REGEXP);
SEMICOLON: ';';
LPAREN: '{';
RPAREN: '}';

fragment SLASH_REGEXP: '/' SLASH_REGEXP_CHAR+ '/';
fragment SLASH_REGEXP_CHAR: ~[/\n\r] | ESCAPE_SEQ | '\\/';

fragment CARET_REGEXP: '^' CARET_REGEXP_CHAR+ '^';
fragment CARET_REGEXP_CHAR: ~[^\n\r] | ESCAPE_SEQ | '\\^';

fragment STRING_CHAR : ~["\\] | ESCAPE_SEQ | UTF8CHAR ; // strings can be multi-line

fragment ESCAPE_SEQ: '\\' ['"?abfnrtv\\] ;

fragment ALPHA_CHAR  : [a-zA-Z] ;
fragment ALPHA_UCHAR : [A-Z] ;
fragment ALPHA_LCHAR : [a-z] ;
fragment UTF8CHAR    : '\\u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT ;

fragment DIGIT     : [0-9] ;
fragment HEX_DIGIT : [0-9a-fA-F] ;

WS         : [ \t\r]+ -> skip;
