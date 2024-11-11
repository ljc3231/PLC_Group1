package symbolTable;

import exceptionFiles.*;
import java.util.*;

public class SymbolTable {
    static Map<String, ArrayList<String>> funcMap;
    static Map<String, Map<String, ArrayList<String>>> varMap;
    static String scope;
    public static void init() {
        funcMap = new HashMap<>();
        varMap = new HashMap<>();
        scope = "";
    }

    public static Map<String, ArrayList<String>> getFuncMap(){
        return funcMap;
    }

    public static void addFunction(String funcName, ArrayList<String> params, String returnType, String source, int lineNum) throws JottException {
        if (funcMap.containsKey(funcName)) {
            throw new JottException(false, "Function '" + funcName + "' is already defined", null, lineNum);
        }
        ArrayList<String> funcDef = new ArrayList<>(params);
        funcDef.add(returnType);
        funcMap.put(funcName, funcDef);
    }

    public static void addVariable(String varName, String varType, int lineNum) throws JottException {
        String funcName = scope;
        Map<String, ArrayList<String>> varPropMap = varMap.getOrDefault(funcName, new HashMap<>());
        if (varPropMap.containsKey(varName)) {
            throw new JottException(false, "Variable name '" + varName + "' is already defined in function '" + funcName + "'", null, lineNum);
        }
        ArrayList<String> varProperties = new ArrayList<>();
        varProperties.add(varType);
        varProperties.add(null);
        Map<String, ArrayList<String>> variable = new HashMap<>();
        variable.put(varName, varProperties);
        varMap.put(funcName, variable);
    }

    public static void updateVariable(String varName, String varValue) throws Exception {
        String funcName = scope;
        Map<String, ArrayList<String>> variableMap = varMap.getOrDefault(funcName, new HashMap<>());
        if (!(variableMap.containsKey(varName))) {
            throw new Exception("Variable name '" + varName + "' is not defined in function '" + funcName + "'");
        }
        ArrayList<String> varProperties = variableMap.get(varName);
        varProperties.set(1, varValue);
        variableMap.put(varName, varProperties);
        varMap.put(funcName, variableMap);
    }

    public static void updateScope(String funcName) {
        scope = funcName;
    }

    public static boolean isValidFunction(String fName, ArrayList<String> params) {
        String retType = params.get(params.size() - 1);
        if (fName.equals("print")) {
            if (params.size() != 2) {
                return false;
            }
            if (!retType.equals("Void")) {
                return false;
            }
            String param = params.get(0);
            return param.equals("String") || param.equals("Int") || param.equals("Double");
        }
        if (fName.equals("length")) {
            if (params.size() != 2) {
                return false;
            }
            if (!retType.equals("Int")) {
                return false;
            }
            String param = params.get(0);
            return param.equals("String");
        }
        if (fName.equals("concat")) {
            if (params.size() != 3) {
                return false;
            }
            if (!retType.equals("String")) {
                return false;
            }
            return params.get(0).equals("String") && params.get(1).equals("String");
        }

        ArrayList<String> func = funcMap.get(fName);
        
        if (func == null) {
            return false;
        }

        if (params.size() == func.size()) {
            return false;
        }

        for (int i = 0; i < params.size(); i++) {
            if (!params.get(i).equals(func.get(0))) {
                return false;
            }
        }
        
        return true;
    }

    public static List<String> getVariable(String s) throws JottException {
        // Check if the id is in the Symbol Table
        if (!varMap.containsKey(s)) {
            throw new JottException(true, "Symbol Table", "Variable '" + s + "' not found in the current scope.", 0);
        }
        return varMap.get(scope).get(s);
    }

    public static String getReturnType() {
        List<String> currFunc = funcMap.get(scope);
        return currFunc.get(currFunc.size() - 1);
    }
}
