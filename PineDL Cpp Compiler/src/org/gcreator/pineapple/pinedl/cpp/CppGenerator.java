/*
Copyright (C) 2008-2009 Luís Reis<luiscubal@gmail.com>
Copyright (C) 2008-2009 Serge Humphrey<bob@bobtheblueberry.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package org.gcreator.pineapple.pinedl.cpp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.gcreator.pineapple.pinedl.AccessControlKeyword;
import org.gcreator.pineapple.pinedl.Argument;
import org.gcreator.pineapple.pinedl.Constructor;
import org.gcreator.pineapple.pinedl.Function;
import org.gcreator.pineapple.pinedl.Leaf;
import org.gcreator.pineapple.pinedl.NativeType;
import org.gcreator.pineapple.pinedl.PineClass;
import org.gcreator.pineapple.pinedl.PineDLLexer;
import org.gcreator.pineapple.pinedl.PineDLParser;
import org.gcreator.pineapple.pinedl.Type;
import org.gcreator.pineapple.pinedl.TypeCategory;
import org.gcreator.pineapple.pinedl.statements.Block;
import org.gcreator.pineapple.pinedl.statements.DeclAssign;
import org.gcreator.pineapple.pinedl.statements.DivisionOperation;
import org.gcreator.pineapple.pinedl.statements.DoubleConstant;
import org.gcreator.pineapple.pinedl.statements.EqualOperation;
import org.gcreator.pineapple.pinedl.statements.Expression;
import org.gcreator.pineapple.pinedl.statements.FunctionReference;
import org.gcreator.pineapple.pinedl.statements.IfStatement;
import org.gcreator.pineapple.pinedl.statements.IntConstant;
import org.gcreator.pineapple.pinedl.statements.LessOperation;
import org.gcreator.pineapple.pinedl.statements.MultiplyOperation;
import org.gcreator.pineapple.pinedl.statements.NewCall;
import org.gcreator.pineapple.pinedl.statements.RetrieverExpression;
import org.gcreator.pineapple.pinedl.statements.StringConstant;
import org.gcreator.pineapple.pinedl.statements.SubtractionOperation;
import org.gcreator.pineapple.pinedl.statements.SumOperation;
import org.gcreator.pineapple.pinedl.statements.VariableReference;

/**
 * Creates a H file from a PineDL context.
 * 
 * @author Luís Reis
 */
public class CppGenerator {

    OutputStream out = null;
    InputStream in = null;
    GameCompiler cmp = null;
    PineClass cls = null;
    String fname = "";
    boolean successful = true;
    Vector<String> context = null;

    public CppGenerator(InputStream in, OutputStream out, GameCompiler cmp, String fname,
            Vector<String> context) {
        try {
            this.in = in;
            this.out = out;
            this.cmp = cmp;
            this.context = context;
            parse();
            this.fname = cls.clsName;
            if (!this.fname.equals(fname)) {
                throw new Exception("Invalid class name!");
            }
            write();
        } catch (Exception e) {
            e.printStackTrace();
            throwError("Parsing exception: " + e.getMessage());
        }
    }

    public boolean wasSuccessful() {
        return successful;
    }

    private void parse() throws Exception {
        PineDLLexer lexer = new PineDLLexer(new ANTLRInputStream(in));
        CommonTokenStream ts = new CommonTokenStream(lexer);
        PineDLParser parser = new PineDLParser(ts);
        cls = parser.doc();
    }

    private void write() throws Exception {

        writeImports();
        writeClass();

        writeLine();
    }

    private void writeImports() throws Exception {
        Vector<String> iclass = new Vector<String>();

        writeLine("#include \"header.h\"");
        writeLine("using namespace Pineapple;");
        writeLine("using namespace Game;");

        Vector<String> s = new Vector<String>();
        for (Type t : cls.importStmt) {
            if (s.contains(t.type[t.type.length - 1])) {
                throwError("Found two import statements referencing same class name.");
                return;
            }
            if (context.contains(t.type[t.type.length - 1])) {
                throwWarning("Found import statement reference class name of same package");
                return;
            }
            s.add(t.type[t.type.length - 1]);
            writeLine("using " + typeToString(t, false) + ';');
        }
    }

    private void writeClass() throws Exception {


        writeConstructors();
        writeMethods();

    }

    private String retrieveType(Type t, boolean reference) {
        if (t.typeCategory == TypeCategory.NATIVE) {
            return typeToString(t, reference);
        }
        if (t.typeCategory == TypeCategory.ARRAY) {
            return retrieveType(t.arrayType, reference) + "*";
        }
        if (t.type.length != 1) {
            return typeToString(t, reference);
        }
        for (String s : context) {
            if (s.equals(t.type[t.type.length - 1])) {
                return s + (reference ? "*" : "");
            }
        }
        for (Type type : cls.importStmt) {
            if (type.type[type.type.length - 1].equals(t.type[0])) {
                return typeToString(type, reference);
            }
        }
        if (t.type.length == 1) {
            if (t.type[0].equals("Texture")) {
                return "Pineapple::Texture" + (reference ? "*" : "");
            } else if (t.type[0].equals("Actor")) {
                return "Pineapple::Actor" + (reference ? "*" : "");
            } else if (t.type[0].equals("Scene")) {
                return "Pineapple::Scene" + (reference ? "*" : "");
            } else if (t.type[0].equals("Math")) {
                return "Pineapple::Math" + (reference ? "*" : "");
            } else if (t.type[0].equals("Key")) {
                return "Pineapple::Key" + (reference ? "*" : "");
            } else if (t.type[0].equals("Keyboard")) {
                return "Pineapple::Keyboard" + (reference ? "*" : "");
            }
        }
        throwError("Unknown type " + t.toString());
        return "---";
    }
    
    private String merge(String[] x, char c){
        String res = x[0];
        for(int i = 1; i < x.length; i++){
            res += c;
            res += x[i];
        }
        return res;
    }
    
    public boolean isType(String t){
        for (Type type : cls.importStmt) {
            if(merge(type.type,'.').equals(t)){
                return true;
            }
            if(type.type[type.type.length - 1].equals(t)){
                return true;
            }
        }
        if(t.equals("Texture")||t.equals("Pineapple.Texture")){
            return true;
        }
        if(t.equals("Actor")||t.equals("Pineapple.Actor")){
            return true;
        }
        if(t.equals("Scene")||t.equals("Pineapple.Scene")){
            return true;
        }
        if(t.equals("Math")||t.equals("Pineapple.Math")){
            return true;
        }
        if(t.equals("Key")||t.equals("Pineapple.Key")){
            return true;
        }
        if(t.equals("Keyboard")||t.equals("Pineapple.Keyboard")){
            return true;
        }
        return false;
    }

    private void writeConstructors() throws Exception {
        for (Constructor c : cls.constructors) {
            String s = detokenize(cls.clsName) + "::" + detokenize(cls.clsName) + '(';

            boolean isFirst = true;
            for (Argument a : c.arguments) {
                if (!isFirst) {
                    s += ", ";
                }
                s += retrieveType(a.type, true);
                s += ' ';
                s += detokenize(a.name);
                isFirst = false;
            }

            s += ")";

            if (c.superArguments != null) {
                s += ": " + retrieveType(cls.superClass, false);
                s += '(';
                isFirst = true;
                for (Expression exp : c.superArguments) {
                    if (!isFirst) {
                        s += ", ";
                    }

                    s += leafToString(exp);

                    isFirst = false;
                }
                s += ')';
            }

            writeLine(s);

            writeLine(leafToString(c.content));

            writeLine();
        }
    }

    private void writeMethods() throws Exception {
        for (Function method : cls.functions) {
            String s = (method.isStatic ? " static " : " ") +
                    retrieveType(method.returnType, true) + ' ' +
                    detokenize(cls.clsName) + "::" + detokenize(method.name) + '(';

            boolean isFirst = true;
            for (Argument a : method.arguments) {
                if (!isFirst) {
                    s += ", ";
                }
                s += retrieveType(a.type, true);
                s += ' ';
                s += detokenize(a.name);
                isFirst = false;
            }

            s += ")";

            writeLine(s);

            System.out.println(method.content.toString());
            
            writeLine(leafToString(method.content));

            writeLine();
        }
    }

    private String leafToString(Leaf l) {
        return leafToString(l, false);
    }
    
    private String leafToString(Leaf l, boolean statement) {
        if (l instanceof Block) {
            String s = "{\n";

            for (Leaf leaf : ((Block) l).content) {
                s += leafToString(leaf, true) + "\n";
            }

            return s + "}";
        }
        if (l instanceof DeclAssign) {
            DeclAssign da = (DeclAssign) l;
            String s = retrieveType(da.type, true);
            s += ' ';
            s += detokenize(da.name);
            if (da.value != null) {
                s += '=';
                s += leafToString(da.value);
            }
            if(statement){
                s += ';';
            }
            return s;
        }
        if (l instanceof EqualOperation) {
            EqualOperation e = (EqualOperation) l;
            String s = leafToString(e.left) + "= (" + leafToString(e.right) + ")";
            if(statement){
                s += ';';
            }
            return s;
        }
        if (l instanceof SumOperation) {
            SumOperation s = (SumOperation) l;
            return "(" + leafToString(s.left) + ")+(" + leafToString(s.right) + ")";
        }
        if (l instanceof SubtractionOperation) {
            SubtractionOperation s = (SubtractionOperation) l;
            return "(" + leafToString(s.left) + ")-(" + leafToString(s.right) + ")";
        }
        if (l instanceof MultiplyOperation) {
            MultiplyOperation s = (MultiplyOperation) l;
            return "(" + leafToString(s.left) + ")*(" + leafToString(s.right) + ")";
        }
        if (l instanceof DivisionOperation) {
            DivisionOperation s = (DivisionOperation) l;
            return "(" + leafToString(s.left) + ")/(" + leafToString(s.right) + ")";
        }
        if (l instanceof LessOperation) {
            LessOperation s = (LessOperation) l;
            return "(" + leafToString(s.left) + ")<(" + leafToString(s.right) + ")";
        }
        else{
            System.out.println("not less");
        }
        if (l instanceof IntConstant) {
            return l.toString();
        }
        if (l instanceof DoubleConstant) {
            return l.toString();
        }
        if (l instanceof StringConstant) {
            return l.toString();
        }
        if (l instanceof FunctionReference) {
            FunctionReference f = (FunctionReference) l;
            String s = f.name;
            s += '(';
            boolean first = true;
            for(Expression e : f.arguments){
                if(!first){
                    s += ", ";
                }
                s += leafToString(e);
                first = false;
            }
            s += ')';
            if(statement){
                s += ';';
            }
            return s;
        }
        if (l instanceof VariableReference) {
            return ((VariableReference) l).name;
        }
        if (l instanceof NewCall) {
            NewCall n = (NewCall) l;
            String s = "new ";
            s += typeToString(n.type, false);
            s += '(';
            
            boolean first = true;
            for(Expression e : n.arguments){
                if(!first){
                    s += ", ";
                }
                s += leafToString(e);
                first = false;
            }
            
            s += ')';
            if(statement){
                s += ';';
            }
            return s;
        }
        if (l instanceof DeclAssign){
            DeclAssign a = (DeclAssign) l;
            String s = retrieveType(a.type, true);
            s += ' ';
            s += a.name;
            if(a.value!=null){
                s += " = (";
                s += leafToString(a.value);
                s += ')';
                if(statement){
                    s += ';';
                }
            }
            return s;
        }
        if (l instanceof IfStatement){
            IfStatement i = (IfStatement) l;
            String s = "if(";
            String le = leafToString(i.condition);
            s += le;
            s += "){";
            s += leafToString(i.then, true);
            s += "}";
            if(i.elseCase!=null){
                s += "else{";
                s += leafToString(i.elseCase, true);
                s += "}";
            }
            return s;
        }
        if(l instanceof RetrieverExpression){
            RetrieverExpression e = (RetrieverExpression) l;
            return "\"" + e.toString() + "\"";
        }
        return "";
    }

    private String accessToString(AccessControlKeyword k) {
        if (k == AccessControlKeyword.PRIVATE) {
            return "private";
        }
        if (k == AccessControlKeyword.PROTECTED) {
            return "protected";
        }
        return "public";
    }

    private String typeToString(Type t, boolean reference) {
        if (t.typeCategory == TypeCategory.ARRAY) {
            return typeToString(t.arrayType, true) + "*";
        }
        if (t.typeCategory == TypeCategory.NATIVE) {
            if (t.nativeType == NativeType.BOOL) {
                return "bool";
            }
            if (t.nativeType == NativeType.CHAR) {
                return "signed char";
            }
            if (t.nativeType == NativeType.DOUBLE) {
                return "double";
            }
            if (t.nativeType == NativeType.FLOAT) {
                return "float";
            }
            if (t.nativeType == NativeType.INT) {
                return "int";
            }
            if (t.nativeType == NativeType.VOID) {
                return "void";
            }
            if (t.nativeType == NativeType.STRING) {
                return "std::string";
            }
            if (t.nativeType == NativeType.UCHAR) {
                return "unsigned char";
            }
            if (t.nativeType == NativeType.UDOUBLE) {
                return "unsigned double";
            }
            if (t.nativeType == NativeType.UFLOAT) {
                return "unsigned float";
            }
            if (t.nativeType == NativeType.UINT) {
                return "unsigned int";
            }
        }
        String x = t.type[0];
        for (int i = 1; i < t.type.length; i++) {
            x += "::";
            x += t.type[i];
        }
        if (reference) {
            x += '*';
        }
        return x;
    }

    private String detokenize(String id) {
        if (id.startsWith("_")) {
            return "_P_" + id;
        }
        /*
         * The following aren't PineDL keywords, so the user
         * has the right to use them
         */
        if (id.equals("do")) {
            throwWarning("'do' is not a PineDL keyword, but may become in the future. Avoid using it");
            return "_K_do";
        }
        if (id.equals("unsigned")) {
            return "_K_unsigned";
        }
        if (id.equals("signed")) {
            return "_K_signed";
        }
        if (id.equals("switch")) {
            throwWarning("'switch' is not a PineDL keyword, but may become in the future. Avoid using it");
            return "_K_switch";
        }
        if (id.equals("case")) {
            throwWarning("'case' is not a PineDL keyword, but may become in the future. Avoid using it");
            return "_K_case";
        }
        if (id.equals("default")) {
            throwWarning("'default' is not a PineDL keyword, but may become in the future. Avoid using it");
            return "_K_default";
        }
        if (id.equals("NULL")) {
            return "_K_NULL";
        }
        if (id.equals("FILE")) {
            return "_K_FILE";
        }
        if (id.startsWith("SDL")) {
            return "_K_" + id;
        }
        if (id.equals("default")) {
            return "_K_default";
        }
        if (id.startsWith("GL") || id.startsWith("gl")) {
            return "_K_" + id;
        }
        if (id.equals("lambda")) {
            throwWarning("'lambda' is not a PineDL keyword, but may become in the future. Avoid using it");
            return id;
        }
        if (id.equals("repeat")) {
            throwWarning("'repeat' is not a PineDL keyword, but may become in the future. Avoid using it");
            return id;
        }
        if (id.equals("std")) {
            return "_K_" + id;
        }
        if (id.equals("virtual")) {
            return "_K_" + id;
        }
        if (id.equals("using")) {
            return "_K_" + id;
        }
        if (id.equals("namespace")) {
            return "_K_" + id;
        }

        return id;
    }

    private String getHeaderTitle(String name) {
        String s = "__";

        s += name.toUpperCase();

        s += "_H__";
        return s;
    }

    private void writeLine() throws IOException {
        out.write('\n');
    }

    private void writeLine(String line) throws IOException {
        out.write(line.getBytes());
        out.write('\n');
    }

    private void throwWarning(String warning) {
        String message = "[WARNING] ";
        message += warning;

        cmp.compFrame.writeLine(message);
    }

    private void throwError(String error) {
        String message = "[ERROR] ";
        message += error;
        successful = false;

        cmp.compFrame.writeLine(message);
    }
}