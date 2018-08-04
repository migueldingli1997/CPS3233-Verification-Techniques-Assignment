package generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Generator {

    private final int lifts;
    private final int floors;

    Generator(final int lifts, final int floors, final String outputFile) throws IOException {
        this.lifts = lifts;
        this.floors = floors;
        Files.write(Paths.get(outputFile), generateModel().getBytes());
    }

    private String generateModel() {
        return String.format("-- %d lifts\n", lifts) +
                String.format("-- %d floors\n\n", floors) +
                getMainModule() +
                getBuildingModule() +
                getControlPanelModule() +
                getRequestMemoryModule() +
                getLiftModule();
    }

    //---------------------------------------------------------------------------------------- Modules

    private String getMainModule() {

        final List<String> variables = Collections.singletonList(
                getVariable("building", "building"));
        final List<String> definitions = Arrays.asList(
                getDefinition("weight_sensor[1]", "building.lift1.wSensor"),
                getDefinition("goto[1]", "building.lift1.goto"),
                getDefinition("destination[1]", "building.lift1.dest"),
                getDefinition("door[1]", "building.lift1.door"),
                getDefinition("level[1]", "building.lift1.level"),
                getDefinition("OPENING", "OING"),
                getDefinition("CLOSING", "CING"),
                getDefinition("CLOSED", "CLOS"));

        final StringBuilder properties = new StringBuilder();
        properties.append(getLtlSpecSection("G ((weight_sensor[1] & goto[1]>0) -> X (destination[1]>0))"));
        properties.append(getLtlSpecSection("G (goto[1]=1 -> F (door[1][1]=OPEN))"));
        properties.append(getCtlSpecSection("AG !(door[1][1]=OPEN    & EX door[1][1]=OPENING)"));
        properties.append(getCtlSpecSection("AG !(door[1][1]=CLOSING & EX door[1][1]=OPENING)"));
        properties.append(getCtlSpecSection("AG !(door[1][1]=CLOSED  & EX door[1][1]=CLOSING)"));
        properties.append(getCtlSpecSection("AG !(door[1][1]=OPENING & EX door[1][1]=CLOSING)"));

        return getModuleSection("main", "" +
                getVarSection(variables) +
                getDefineSection(definitions) +
                properties.toString());
    }

    private String getBuildingModule() {

        final StringBuilder liftsList = new StringBuilder();
        for (int i = 1; i <= lifts; i++) {
            liftsList.append(", lift").append(i);
        }

        final List<String> variables = new ArrayList<>();
        variables.add(getVariable("ctrl", "controlPanel"));
        variables.add(getVariable("req", "requestMemory(ctrl.up, ctrl.down" + liftsList.toString() + ")"));
        for (int i = 1; i <= lifts; i++) {
            variables.add(getVariable("lift" + i, "lift(req.reqs)"));
        }

        return getModuleSection("building", getVarSection(variables));
    }

    private String getControlPanelModule() {

        final List<String> variables = Arrays.asList(
                getVariable("up", "array 1..FLOORS of boolean") + "   -- user lift-req btn [INPUT]",
                getVariable("down", "array 1..FLOORS of boolean") + " -- user lift-req btn [INPUT]");
        final List<String> definitions = Collections.singletonList(
                getDefinition("FLOORS", Integer.toString(floors)));
        return getModuleSection("controlPanel", "" +
                getVarSection(variables) +
                getDefineSection(definitions));
    }

    private String getRequestMemoryModule() {

        final StringBuilder liftsList = new StringBuilder();
        for (int i = 1; i <= lifts; i++) {
            liftsList.append(", lift").append(i);
        }

        final StringBuilder liftDoors = new StringBuilder();
        for (int i = 1; i <= lifts; i++) {
            liftDoors.append("lift").append(i).append(".door[%floor]=OPEN");
            if (i != lifts) {
                liftDoors.append(" | ");
            }
        }

        final List<String> variables = Collections.singletonList(
                getVariable("reqs", "array 1..FLOORS of {UP, DOWN, NONE}") + " -- memorised user requests");
        final List<String> definitions = Collections.singletonList(
                getDefinition("FLOORS", Integer.toString(floors)));

        final StringBuilder initsList = new StringBuilder();
        for (int i = 1; i <= floors; i++) {
            initsList.append(getInitSection("reqs[" + i + "]", "NONE"));
        }

        final List<String> transConditions = new ArrayList<>();
        for (int i = 1; i <= floors; i++) {
            transConditions.add(String.format(getImplies("up[%d]", "reqs[%d]=UP"), i, i));
        }
        for (int i = 1; i <= floors; i++) {
            transConditions.add(String.format(getImplies("!up[%d] & down[%d]", "reqs[%d]=DOWN"), i, i, i));
        }
        for (int i = 1; i <= floors; i++) {
            final String liftDoorsCondition = liftDoors.toString().replaceAll("%floor", Integer.toString(i));
            transConditions.add(String.format(getImplies("(" + liftDoorsCondition + ")", "reqs[%d]=NONE"), i));
        }

        return getModuleSection("requestMemory(up, down" + liftsList.toString() + ")", "" +
                getVarSection(variables) +
                getDefineSection(definitions) +
                initsList.toString() +
                getTransSection(transConditions));
    }

    private String getLiftModule() {

        final List<String> variables = Arrays.asList(
                getVariable("oSensor", "array 1..FLOORS of boolean") + "               -- door sensors [INPUT]",
                getVariable("wSensor", "boolean") + "                                  -- weight sensor [INPUT]",
                getVariable("goto", "0..FLOORS") + "                                   -- user buttons in lift (0=none) [INPUT]",
                getVariable("door", "array 1..FLOORS of {OPEN, OING, CLOS, CING}") + " -- state of door",
                getVariable("level", "1..FLOORS") + "                                  -- level of lift",
                getVariable("dest", "0..FLOORS") + "                                   -- dest. of lift (0=none)");

        final List<String> defintions = Arrays.asList(
                getDefinition("FLOORS", Integer.toString(floors)),
                getDefinition("goingUp", "dest != 0 & level < dest"),
                getDefinition("goingDn", "dest != 0 & level > dest"));

        final StringBuilder doorAssigns = new StringBuilder();
        for (int i = 1; i <= floors; i++) {
            final String cases = "" +
                    (i == 1 ? String.format("" +
                            "door[%d]=CLOS: (level=1 & dest=1)  -- already there\n" +
                            "            | (level=2 & goingDn) -- from above\n" +
                            "              ? OING : CLOS;\n", i) :
                            (i == floors ? String.format("" +
                                    "door[%d]=CLOS: (level=%d & dest=%d)  -- already there\n" +
                                    "            | (level=%d & goingUp) -- from below\n" +
                                    "              ? OING : CLOS;\n", i, floors, floors, floors - 1) :
                                    String.format("door[%d]=CLOS: (level=%d & dest=%d)                            -- already there\n" +
                                            "            | (level=%d & goingDn & (dest=%d | reqs[%d]=DOWN)) -- from above\n" +
                                            "            | (level=%d & goingUp & (dest=%d | reqs[%d]=UP))   -- from below\n" +
                                            "              ? OING : CLOS;\n", i, i, i, i + 1, i, i, i - 1, i, i))) +
                    String.format("door[%d]=OING: {OPEN, OING};\n", i) +
                    String.format("door[%d]=OPEN: (!oSensor[%d]? OPEN : CING);\n", i, i) +
                    String.format("door[%d]=CING: (oSensor[%d]? CING : {CLOS, CING});\n", i, i);
            doorAssigns.append(getAssignSection("door[" + i + "]", "CLOS", tabifyNewLines(tabifyNewLines("\n" + getCase(cases)))));
        }

        final String levelInitAndTrans = "" +
                getInitSection("level", Integer.toString(1)) +
                getTransSection(Arrays.asList(
                        getImplies("(dest=0\n" +
                                "    | (goingUp & reqs[level]=UP)\n" +
                                "    | (goingDn & reqs[level]=DOWN))", "next(level) = level"),
                        getImplies("(goingDn & reqs[level] != DOWN)", "next(level) = level - 1"),
                        getImplies("(goingUp & reqs[level] != UP  )", "next(level) = level + 1")));

        final StringBuilder floorChecks = new StringBuilder();
        for (int i = 1; i < floors; i++) {
            floorChecks.append(
                    String.format("((level)-%d >  0)      & (reqs[(level)-%d] != NONE): (level)-%d;\n", i, i, i)).append(
                    String.format("((level)+%d <= FLOORS) & (reqs[(level)+%d] != NONE): (level)+%d;\n", i, i, i));
        }

        final String cases = "" +
                "wSensor & dest=0: goto;\n" +
                "(reqs[level] != NONE): level;\n" +
                floorChecks.toString() +
                "TRUE: 0;\n";
        final String destAssign = getAssignSection("dest", Integer.toString(0), tabifyNewLines(tabifyNewLines("\n" + getCase(cases))));

        return getModuleSection("lift(reqs)", "" +
                getVarSection(variables) +
                getDefineSection(defintions) +
                doorAssigns.toString() +
                levelInitAndTrans +
                destAssign);
    }

    //---------------------------------------------------------------------------------------- Sections

    private static String getModuleSection(final String nameAndParams, final String content) {
        return "MODULE " + nameAndParams + "\n    " + tabifyNewLines(content) + "\n";
    }

    private static String getVarSection(final List<String> variables) {
        return "VAR\n    " + variables.stream().collect(Collectors.joining("\n    ")) + "\n";
    }

    private static String getDefineSection(final List<String> definitions) {
        return "DEFINE\n    " + definitions.stream().collect(Collectors.joining("\n    ")) + "\n";
    }

    private static String getAssignSection(final String identifier, final String initExpression, final String nextContent) {
        return "ASSIGN\n" +
                getDefinition("    init(" + identifier + ")", initExpression) + "\n" +
                getDefinition("    next(" + identifier + ")", nextContent) + "\n";
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

    private static String getDefinition(final String identifier, final String expression) {
        return identifier + " := " + expression + ";";
    }

    private static String getImplies(final String pred1, final String pred2) {
        return "(" + pred1 + " -> " + pred2 + ")";
    }

    private static String getCase(final String contents) {
        return "case\n    " + tabifyNewLines(contents) + "esac";
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
