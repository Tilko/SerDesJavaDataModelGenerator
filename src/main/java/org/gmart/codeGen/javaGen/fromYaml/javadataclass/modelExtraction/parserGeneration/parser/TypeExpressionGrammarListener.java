// Generated from C:\Users\marti\workingLowLevel\codeGen\src\main\java\org\gmart\codeGen\javaGen\fromYaml\javadataclass\typedefsExtraction\typeExpression\TypeExpressionGrammar.g4 by ANTLR 4.8
package org.gmart.codeGen.javaGen.fromYaml.javadataclass.modelExtraction.parserGeneration.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TypeExpressionGrammarParser}.
 */
public interface TypeExpressionGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#typeNamePart}.
	 * @param ctx the parse tree
	 */
	void enterTypeNamePart(TypeExpressionGrammarParser.TypeNamePartContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#typeNamePart}.
	 * @param ctx the parse tree
	 */
	void exitTypeNamePart(TypeExpressionGrammarParser.TypeNamePartContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#propertyNamePart}.
	 * @param ctx the parse tree
	 */
	void enterPropertyNamePart(TypeExpressionGrammarParser.PropertyNamePartContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#propertyNamePart}.
	 * @param ctx the parse tree
	 */
	void exitPropertyNamePart(TypeExpressionGrammarParser.PropertyNamePartContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#onOneLineTypeDef}.
	 * @param ctx the parse tree
	 */
	void enterOnOneLineTypeDef(TypeExpressionGrammarParser.OnOneLineTypeDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#onOneLineTypeDef}.
	 * @param ctx the parse tree
	 */
	void exitOnOneLineTypeDef(TypeExpressionGrammarParser.OnOneLineTypeDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(TypeExpressionGrammarParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(TypeExpressionGrammarParser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#mapTypeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMapTypeExpression(TypeExpressionGrammarParser.MapTypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#mapTypeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMapTypeExpression(TypeExpressionGrammarParser.MapTypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void enterTypeExpression(TypeExpressionGrammarParser.TypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void exitTypeExpression(TypeExpressionGrammarParser.TypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#anonymousEnumField}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousEnumField(TypeExpressionGrammarParser.AnonymousEnumFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#anonymousEnumField}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousEnumField(TypeExpressionGrammarParser.AnonymousEnumFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#diamondOneArg}.
	 * @param ctx the parse tree
	 */
	void enterDiamondOneArg(TypeExpressionGrammarParser.DiamondOneArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#diamondOneArg}.
	 * @param ctx the parse tree
	 */
	void exitDiamondOneArg(TypeExpressionGrammarParser.DiamondOneArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#diamondTwoArg}.
	 * @param ctx the parse tree
	 */
	void enterDiamondTwoArg(TypeExpressionGrammarParser.DiamondTwoArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#diamondTwoArg}.
	 * @param ctx the parse tree
	 */
	void exitDiamondTwoArg(TypeExpressionGrammarParser.DiamondTwoArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link TypeExpressionGrammarParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(TypeExpressionGrammarParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TypeExpressionGrammarParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(TypeExpressionGrammarParser.QualifiedNameContext ctx);
}