// Generated from C:\Users\marti\workingLowLevel\codeGen\src\main\java\org\gmart\codeGen\javaGen\modelExtraction\parserGeneration\DataTypeHierarchy.g4 by ANTLR 4.8
package org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DataTypeHierarchyParser}.
 */
public interface DataTypeHierarchyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#typeNamePart}.
	 * @param ctx the parse tree
	 */
	void enterTypeNamePart(DataTypeHierarchyParser.TypeNamePartContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#typeNamePart}.
	 * @param ctx the parse tree
	 */
	void exitTypeNamePart(DataTypeHierarchyParser.TypeNamePartContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#propertyNamePart}.
	 * @param ctx the parse tree
	 */
	void enterPropertyNamePart(DataTypeHierarchyParser.PropertyNamePartContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#propertyNamePart}.
	 * @param ctx the parse tree
	 */
	void exitPropertyNamePart(DataTypeHierarchyParser.PropertyNamePartContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#onOneLineTypeName}.
	 * @param ctx the parse tree
	 */
	void enterOnOneLineTypeName(DataTypeHierarchyParser.OnOneLineTypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#onOneLineTypeName}.
	 * @param ctx the parse tree
	 */
	void exitOnOneLineTypeName(DataTypeHierarchyParser.OnOneLineTypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#onOneLineTypeDef}.
	 * @param ctx the parse tree
	 */
	void enterOnOneLineTypeDef(DataTypeHierarchyParser.OnOneLineTypeDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#onOneLineTypeDef}.
	 * @param ctx the parse tree
	 */
	void exitOnOneLineTypeDef(DataTypeHierarchyParser.OnOneLineTypeDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(DataTypeHierarchyParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(DataTypeHierarchyParser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#mapTypeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMapTypeExpression(DataTypeHierarchyParser.MapTypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#mapTypeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMapTypeExpression(DataTypeHierarchyParser.MapTypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void enterTypeExpression(DataTypeHierarchyParser.TypeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#typeExpression}.
	 * @param ctx the parse tree
	 */
	void exitTypeExpression(DataTypeHierarchyParser.TypeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#anonymousEnumField}.
	 * @param ctx the parse tree
	 */
	void enterAnonymousEnumField(DataTypeHierarchyParser.AnonymousEnumFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#anonymousEnumField}.
	 * @param ctx the parse tree
	 */
	void exitAnonymousEnumField(DataTypeHierarchyParser.AnonymousEnumFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#diamondOneArg}.
	 * @param ctx the parse tree
	 */
	void enterDiamondOneArg(DataTypeHierarchyParser.DiamondOneArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#diamondOneArg}.
	 * @param ctx the parse tree
	 */
	void exitDiamondOneArg(DataTypeHierarchyParser.DiamondOneArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#diamondTwoArg}.
	 * @param ctx the parse tree
	 */
	void enterDiamondTwoArg(DataTypeHierarchyParser.DiamondTwoArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#diamondTwoArg}.
	 * @param ctx the parse tree
	 */
	void exitDiamondTwoArg(DataTypeHierarchyParser.DiamondTwoArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link DataTypeHierarchyParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(DataTypeHierarchyParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DataTypeHierarchyParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(DataTypeHierarchyParser.QualifiedNameContext ctx);
}