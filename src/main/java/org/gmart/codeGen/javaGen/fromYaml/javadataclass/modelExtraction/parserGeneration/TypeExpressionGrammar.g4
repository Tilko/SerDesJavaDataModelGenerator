grammar TypeExpressionGrammar;

// the 3 following rules and the "typeExpression" generate the 4 main rules.

typeNamePart
	:	Identifier ('is' qualifiedName)?
	;
propertyNamePart
	:	Identifier optionalMark='?'?
	;
onOneLineTypeDef
	:	'oneOf' '(' typeExpression (',' typeExpression)* ')'
	|	identifierList
	;
	
identifierList
	: 	Identifier (',' Identifier)*
	;
	
	
mapTypeExpression
	:	'Dict' diamondOneArg ArrayMarks? 
	|   'Map'  diamondTwoArg ArrayMarks? 
	;
typeExpression
	:   abstractFieldMark='abstract' (anonymousEnumField | qualifiedName?)
	|	anonymousEnumField
	|   mapTypeExpression
	|	qualifiedName ArrayMarks?
	;
	
anonymousEnumField
	:	enumMark='enum' '(' identifierList ')'
	;

//mapValueTypeExpression
//	:   mapTypeExpression
//	|	qualifiedName ArrayMarks?
//	;
diamondOneArg
	:	'<' typeExpression '>'
	;
diamondTwoArg
	:	'<' qualifiedName ',' typeExpression '>'
	;
ArrayMarks
	:	'*'+
	;

qualifiedName
	:	Identifier ('.' Identifier)*
	;

WS  :  [ \t\r\n\u000C]+ -> skip
    ;
    
// Identifiers

Identifier:         Letter LetterOrDigit*;

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;
fragment Letter
    : [a-zA-Z$_] // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;
    