package org.syh.demo.tool;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws Exception {
        String outputDir = "";

        if (args.length > 1) {
            System.err.println("usage: generate_ast <output directory>");
            System.exit(64);
        } else if (args.length == 1) {
            outputDir = args[0];
        } else if (args.length == 0) {
            outputDir = "src/main/java/org/syh/demo/lox";
        } 

    
        defineAst(outputDir, "Expr", Arrays.asList(
            "Binary : Expr left, Token operator, Expr right",
            "Grouping : Expr expression",
            "Literal : Object value",
            "Unary : Token operator, Expr right"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws Exception {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package org.syh.demo.lox;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("public abstract class " + baseName + " {");
        defineVisitor(writer, baseName, types);
        writer.println();
        writer.println("    public abstract <R> R accept(Visitor<R> visitor);");
        writer.println();
        for (int i = 0; i < types.size(); i++) {
            String type = types.get(i);
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields, i != types.size() - 1);
        }
        writer.println("}");
        writer.close();
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("    public interface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println("        R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println("    }");
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList, boolean hasNewLine) {
        writer.println("    public static class " + className + " extends " + baseName + " {");
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            writer.println("        public final " + field + ";");
        }
        writer.println();
        writer.println("        public " + className + "(" + fieldList + ") {");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println("            this." + name + " = " + name + ";");
        }
        writer.println("        }");
        writer.println();
        writer.println("        @Override");
        writer.println("        public <R> R accept(Visitor<R> visitor) {");
        writer.println("            return visitor.visit" + className + baseName + "(this);");
        writer.println("        }");
        writer.println("    }");
        if (hasNewLine) {
            writer.println();
        }
    }
}