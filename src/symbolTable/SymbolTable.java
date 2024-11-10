package symbolTable;

import exceptionFiles.*;
import java.util.*;

public class SymbolTable {
    static Map<String, List<String>> funcMap;
    static Map<String, Map<String, List<String>>> varMap;
    static String scope;
    public static void init() {
        funcMap = new HashMap<>();
        varMap = new HashMap<>();
        scope = "";
    }

    public static Map<String, List<String>> getFuncMap(){
        return funcMap;
    }

    public static void addFunction(String funcName, List<String> params, String returnType, String source, int lineNum) throws JottException {
        if (funcMap.containsKey(funcName)) {
            throw new JottException(false, "Function '" + funcName + "' is already defined", null, lineNum);
        }
        List<String> funcDef = new ArrayList<>(params);
        funcDef.add(returnType);
        funcMap.put(funcName, funcDef);
    }

    public static void addVariable(String varName, String varType, int lineNum) throws JottException {
        String funcName = scope;
        Map<String, List<String>> varPropMap = varMap.getOrDefault(funcName, new HashMap<>());
        if (varPropMap.containsKey(varName)) {
            throw new JottException(false, "Variable name '" + varName + "' is already defined in function '" + funcName + "'", null, lineNum);
        }
        List<String> varProperties = Arrays.asList(varType, null);
        Map<String, List<String>> variable = new HashMap<>();
        variable.put(varName, varProperties);
        varMap.put(funcName, variable);
    }

    public static void updateVariable(String varName, String varValue) throws Exception {
        String funcName = scope;
        Map<String, List<String>> variableMap = varMap.getOrDefault(funcName, new HashMap<>());
        if (!(variableMap.containsKey(varName))) {
            throw new Exception("Variable name '" + varName + "' is not defined in function '" + funcName + "'");
        }
        List<String> varProperties = variableMap.get(varName);
        varProperties.set(1, varValue);
        variableMap.put(varName, varProperties);
        varMap.put(funcName, variableMap);
    }

    public static void updateScope(String funcName) {
        scope = funcName;
    }

    public static List<String> getFunction(String s) {
        return funcMap.get(s);
    }

    public static List<String> getVariable(String s) {
        return varMap.get(scope).get(s);
    }

    public static String getReturnType() {
        List<String> currFunc = funcMap.get(scope);
        return currFunc.get(currFunc.size() - 1);
    }
}
