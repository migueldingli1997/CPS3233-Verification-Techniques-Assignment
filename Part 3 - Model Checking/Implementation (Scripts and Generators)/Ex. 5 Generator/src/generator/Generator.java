package generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Generator {

    private final int floors;

    Generator(final int floors, final String outputFile) throws IOException {
        this.floors = floors;
        Files.write(Paths.get(outputFile), generateModel().getBytes());
    }

    private String generateModel() {
        return String.format("-- %d floors\n\n", floors) +
                getMainModule() +
                getPositionModule() +
                getDestinationModule();
    }

    //---------------------------------------------------------------------------------------- Modules

    private String getMainModule() {

        final List<String> variables = Arrays.asList(
                getVariable("req", "array 1.." + floors + " of boolean"),
                getVariable("dst", "destination(req,pos.out)"),
                getVariable("pos", "position(req,dst.out)"));

        final StringBuilder reqs = new StringBuilder("req[1]");
        for (int i = 2; i <= floors; i++) {
            reqs.append(" | req[").append(i).append("]");
        }

        final StringBuilder properties = new StringBuilder();
        properties.append(getLtlSpecSection("\n    G ( G(!(" + reqs + ")) -> G dst.out=0 )"));
        properties.append(getLtlSpecSection("\n    G ( G(!(" + reqs + ")) -> F G dst.out=0 )"));
        properties.append(getLtlSpecSection("\n    G ( G(!(" + reqs + ")) -> (F dst.out=0 & (dst.out=0 -> G dst.out=0)) )"));
        properties.append(getCtlSpecSection("\n    AG ( pos.out=3 -> EF (pos.out < 3) )"));
        properties.append(getCtlSpecSection("\n    (AG !(req[1] & req[2])) -> !(AG ( pos.out=3 -> EF (pos.out < 3) ))"));

        return getModuleSection("main", "" +
                getVarSection(variables) +
                properties.toString());
    }

    private String getPositionModule() {

        final List<String> variables = Collections.singletonList(
                getVariable("out", "1.." + floors));

        final String initAndTrans = "" +
                getInitSection("\n    out", Integer.toString(1)) +
                getTransSection(Arrays.asList(
                        getImplies("(dst=out | dst=0)", "next(out)=out"),
                        getImplies("(dst > out)", "next(out)=out + 1"),
                        getImplies("(dst < out & dst > 0)", "next(out)=out - 1")));

        return getModuleSection("position(req,dst)", "" +
                getVarSection(variables) +
                initAndTrans);
    }

    private String getDestinationModule() {

        final List<String> variables = Collections.singletonList(
                getVariable("out", "0.." + floors));

        final StringBuilder reqs = new StringBuilder("!req[1]");
        for (int i = 2; i <= floors; i++) {
            reqs.append(" & !req[").append(i).append("]");
        }

        final StringBuilder ors = new StringBuilder("");
        for (int i = 1; i <= floors; i++) {
            ors.append("    | (req[").append(i).append("] & next(out)=").append(i).append(")\n");
        }

        final String initAndTrans = "" +
                getInitSection("\n    out", Integer.toString(0)) +
                getTransSection(Arrays.asList(
                        getImplies("out=pos", "next(out)=0"),
                        getImplies("(out!=pos & out>0)", "next(out)=out"),
                        "( (" + reqs + " & next(out)=0)\n" + ors + "    );"));

        return getModuleSection("destination(req,pos)", "" +
                getVarSection(variables) +
                initAndTrans);
    }

    //---------------------------------------------------------------------------------------- Sections

    private static String getModuleSection(final String nameAndParams, final String content) {
        return "MODULE " + nameAndParams + "\n    " + tabifyNewLines(content) + "\n";
    }

    private static String getVarSection(final List<String> variables) {
        return "VAR\n    " + variables.stream().collect(Collectors.joining("\n    ")) + "\n";
    }

    private static String getInitSection(final String identifier, final String expression) {
        return "INIT " + identifier + " = " + expression + ";\n";
    }

    private static String getTransSection(final List<String> conditions) {
        return "TRANS\n    " + conditions.stream().collect(Collectors.joining("\n  & ")) + "\n";
    }

    private static String getLtlSpecSection(final String property) {
        return "LTLSPEC " + property + "\n";
    }

    private static String getCtlSpecSection(final String property) {
        return "CTLSPEC " + property + "\n";
    }

    //---------------------------------------------------------------------------------------- Basics

    private static String getVariable(final String identifier, final String expression) {
        return identifier + ": " + expression + ";";
    }

    private static String getImplies(final String pred1, final String pred2) {
        return "(" + pred1 + " -> " + pred2 + ")";
    }

    //---------------------------------------------------------------------------------------- Tabify

    private static String tabifyNewLines(final String toTabify) {
        final String modified = toTabify.replaceAll("\n", "\n    ");
        if (modified.endsWith("    ")) {
            return modified.substring(0, modified.length() - 4);
        } else {
            return modified;
        }
    }
}
