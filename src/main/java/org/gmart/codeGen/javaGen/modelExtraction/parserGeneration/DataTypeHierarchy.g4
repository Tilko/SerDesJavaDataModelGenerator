grammar DataTypeHierarchy;

// the 4 following rules and the "typeExpression" generate the main rules (input rules).

typeNamePart
	:	stubbedMark='stubbed'? Identifier ('(' constructorParameters? ')')? ('is' qualifiedName)?
	;
propertyNamePart
	:	Identifier optionalMark='?'?
	;
onOneLineTypeName
	: 	stubbedMark='stubbed'? Identifier ('(' constructorParameters? ')')?
	;
onOneLineTypeDef
	:	'oneOf' '(' typeExpression (',' typeExpression)* ')'
	|	identifierList
	;
	
constructorParameters
	:	constructorParameter ( ',' constructorParameter)*
	;
constructorParameter
//	:	accessorMark='Accessor' '<' typeExpression (',' typeExpression)* '>' Identifier
	:	accessorMark='Accessor' '<' (qualifiedName ',')* typeExpression '>' Identifier
//	|	typeExpression Identifier
	;
//constructorParameterTypeExpression
//	|   mapTypeExpression
//	|	qualifiedName ArrayMarks?
//	;
//constructorParameterMapTypeExpression
//	:	'Dict' ('[' Identifier ']')? diamondOneArg ArrayMarks? 
//	|   'Map'  ('[' Identifier ']')? diamondTwoArg ArrayMarks? 
//	;
//diamondOneArg:	'<' constructorParameterTypeExpression '>';
//diamondTwoArg:	'<' qualifiedName ',' constructorParameter '>';


	
	
mapTypeExpression
	:	'Dict' ('[' Identifier ']')? diamondOneArg ArrayMarks? 
	|   'Map'  ('[' Identifier ']')? diamondTwoArg ArrayMarks? 
	;
typeExpression
	:   abstractFieldMark='abstract' (anonymousEnumField | qualifiedName?)
	|	anonymousEnumField
	|   mapTypeExpression
	|   'keysFor' '(' pathWithKeyHole ')'
	|	qualifiedName ('(' constructorArguments ')')? ArrayMarks?
	;
constructorArguments
	:	pathWithKeyHole (',' pathWithKeyHole)*
	;
//constructorArgument
//	:	'this'
//	|	('this' '.')?  qualifiedName
//	;
pathWithKeyHole
	:	(thisMark='this' '.')? Identifier ('.' idOrKeyHole)*
	|	thisMark='this'
	;
idOrKeyHole: '?' | Identifier;
	
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

identifierList
	: 	Identifier (',' Identifier)*
	;
qualifiedName
	:	Identifier ('.' Identifier)*
	;

WS  :  [ \t\r\n\u000C]+ -> skip
    ;
    
// Identifiers

Identifier
	:	Letter LetterOrDigit*
	;

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;
fragment Letter
    : [a-zA-Z$_] // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;
    