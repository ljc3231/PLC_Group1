package symbolTable;

import exceptionFiles.*;
import java.util.*;

public class SymbolTable {
    static Map<String, List<String>> funcMap;
    static Map<String, Map<String, List<String>>> varMap;
    static String scope;
    public SymbolTable() {
        funcMap = new HashMap<>();
        varMap = new HashMap<>();
        scope = "";
    }

    public static String getScope(){
        return scope;
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

    public static void addVariable(String varName, String varType) throws Exception {
        String funcName = scope;
        Map<String, List<String>> varPropMap = varMap.getOrDefault(funcName, new HashMap<>());
        if (varPropMap.containsKey(varName)) {
            throw new Exception("Variable name '" + varName + "' is already defined in function '" + funcName + "'");
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

    public static Map<String, List<String>> getVariableMap(String funcName) {
        return varMap.getOrDefault(funcName, new HashMap<>());
    }
}
