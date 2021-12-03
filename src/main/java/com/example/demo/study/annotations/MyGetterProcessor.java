package com.example.demo.study.annotations;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

public class MyGetterProcessor extends AbstractProcessor {

    private Messager messager;
    private JavacTrees javacTrees; // 抽象语法树
    private TreeMaker treeMaker; // 封装了创建AST节点的方法
    private Names names; // 封装了创建标识符的方法

    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.messager = this.processingEnv.getMessager();
        this.javacTrees = JavacTrees.instance(this.processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnvironment).getContext();
        this.names = Names.instance(context);
        this.treeMaker = TreeMaker.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(MyGetter.class);
        elementsAnnotatedWith.forEach(e -> {
            JCTree jcTree = javacTrees.getTree(e);
            jcTree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCVariableDecl> jcTreeJCVariableDeclList = List.nil();
                    for (JCTree jcTree: jcClassDecl.defs) {
                        if (jcTree.getKind().equals(Tree.Kind.VARIABLE)) {
                            jcTreeJCVariableDeclList.append((JCTree.JCVariableDecl)jcTree);
                        }
                    }
                    jcTreeJCVariableDeclList.forEach(jcVariableDecl -> {
                        messager.printMessage(Diagnostic.Kind.NOTE, jcVariableDecl.getName() + "has bean processed");
                        jcClassDecl.defs = jcClassDecl.defs.prepend(makeGetterMethodDecl(jcVariableDecl));
                    });
                }
            });
        });
        return false;
    }

    private JCTree.JCMethodDecl makeGetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        // 生成表达式 例如 this.a = a
        JCTree.JCExpression jcExpression1 = treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName());
        JCTree.JCExpression jcExpression2 = treeMaker.Ident(jcVariableDecl.getName());
        JCTree.JCExpressionStatement aThis = makeAssignment(jcExpression1, jcExpression2);
        statements.append(aThis);
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());
        // 生成入参
        JCTree.JCVariableDecl param = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), jcVariableDecl.getName(), jcVariableDecl.vartype, null);
        List<JCTree.JCVariableDecl> parameters = List.of(param);

        // 生成返回对象
        JCTree.JCExpression methodType = treeMaker.Type(new Type.JCVoidType());
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC),
                getNewMethodName(jcVariableDecl.getName()), methodType, List.nil(), parameters, List.nil(), block, null);
    }

    private Name getNewMethodName(Name name) {
        String s = name.toString();
        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }

    private JCTree.JCExpressionStatement makeAssignment(JCTree.JCExpression jcExpression1, JCTree.JCExpression jcExpression2) {
        return treeMaker.Exec(
                treeMaker.Assign(jcExpression1, jcExpression2)
        );
    }
}
