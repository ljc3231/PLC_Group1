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
    public static Map<String, Map<String, ArrayList<String>>> getVarMap(){return varMap;}

    public static void addFunction(String funcName, ArrayList<String> params, String returnType, String source, int lineNum) throws JottException {
        if (funcMap.containsKey(funcName)) {
            throw new JottException(false, "Function '" + funcName + "' is already defined", null, lineNum);
        }
        ArrayList<String> funcDef = new ArrayList<>();
        for (String p : params) {
            funcDef.add(p);
        }
        funcDef.add(returnType);
        funcMap.put(funcName, funcDef);
        scope = funcName;
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
        varMap.get(funcName).put(varName, varProperties);
    }

    public static void updateVariable(String varName, String varValue) throws Exception {
        if (!(varMap.get(scope).containsKey(varName))) {
            throw new Exception("Variable name '" + varName + "' is not defined in function '" + scope + "'");
        }
        ArrayList<String> varProperties = varMap.get(scope).get(varName);
        varProperties.set(1, varValue);
        varMap.get(scope).put(varName, varProperties);
    }

    public static boolean isValidFunctionCall(String fName, ArrayList<String> params) {
        if (fName.equals("print")) {
            if (params.size() != 1) {
                return false;
            }
            String param = params.get(0);
            return param.equals("String") || param.equals("Integer") || param.equals("Double");
        }
        if (fName.equals("length")) {
            if (params.size() != 1) {
                return false;
            }
            String param = params.get(0);
            return param.equals("String");
        }
        if (fName.equals("concat")) {
            if (params.size() != 2) {
                return false;
            }
            return params.get(0).equals("String") && params.get(1).equals("String");
        }

        ArrayList<String> func = funcMap.get(fName);
        if (params == null){
            return true;
        }

        if (func == null) {
            return false;
        }

        if (params.size() != func.size()) {
            return false;
        }

        for (int i = 0; i < params.size() - 1; i++) {
            if (!params.get(i).equals(func.get(i))) {
                return false;
            }
        }
        
        return true;
    }

    public static List<String> getVariable(String s) throws JottException {
        // Check if the id is in the Symbol Table
        if (!varMap.get(scope).containsKey(s)) {
            throw new JottException(true, "Symbol Table", "Variable '" + s + "' not found in the current scope.", 0);
        }
        return varMap.get(scope).get(s);
    }

    public static String getReturnType() {
        List<String> currFunc = funcMap.get(scope);
        return currFunc.get(currFunc.size() - 1);
    }

    public static String getReturnType(String s, String source, int lineNum) throws JottException {
        if (s.equals("print")) {
            return "Void";
        }
        if (s.equals("length")) {
            return "Integer";
        }
        if (s.equals("concat")) {
            return "String";
        }
        List<String> currFunc = funcMap.get(s);
        if (currFunc == null) {
            throw new JottException(false, source, "Function must be defined before they are used", lineNum);
        }
        return currFunc.get(currFunc.size() - 1);
    }

    public static boolean mainExists() {
        ArrayList<String> func = funcMap.get("main");
        
        if (func.size() != 1) {
            return false;
        }

        return func.get(0).equals("Void");
    }
}
