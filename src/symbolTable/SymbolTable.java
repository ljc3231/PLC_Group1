package symbolTable;

import exceptionFiles.*;
import java.util.*;
import parserNodes.FunctionDefNode;

public class SymbolTable {
    private static Map<String, ArrayList<String>> funcMap;
    private static Map<String, Map<String, ArrayList<String>>> varMap;
    public static Map<String, FunctionDefNode> funcDefinitions;
    private static Map<String, ArrayList<String>> funcParamNames;
    private static String scope;
    
    public static void init() {
        funcMap = new HashMap<>();
        varMap = new HashMap<>();
        funcDefinitions = new HashMap<>();
        funcParamNames = new HashMap<>();
        scope = "";
    }

    public static Map<String, ArrayList<String>> getFuncMap(){
        return funcMap;
    }
    public static Map<String, Map<String, ArrayList<String>>> getVarMap(){return varMap;}

    public static String executeFunction(String funcName, String params) throws JottException{
        if (funcName.equals("print")) {
            System.out.println(params);
            return null;
        }
        else if (funcName.equals("concat")) {
            System.out.println("MAJOR ISSUE (kinda) IF A STRING CONTAINS \", \" THIS WILL RETURN THE WRONG THING");
            return params.replace(", ", "");
        }
        else if (funcName.equals("length")) {
            return params.length() + "";
        }
        scope = funcName;
        if (params.equals("")) {
            System.out.println("TEST PRINT");
            System.out.println("IF THIS DOESN'T RUN THIS IF STATEMENT PROBABLY ISN'T NECESSARY");
            System.out.println("SymbolTable: updateVariableInFuncCall");
        }
        else {
            ArrayList<String> paramNames = funcParamNames.get(scope);
            String[] paramArr = params.split(", ");
            for (int i = 0; i < paramArr.length; i++) {
                updateValidVariable(paramNames.get(i), paramArr[i]);
            }
        }
        FunctionDefNode funcDefNode = funcDefinitions.get(funcName);
        String result = funcDefNode.execute();
        return result;
    }

    public static void addFunction(String funcName, ArrayList<String> params, String returnType, String source, int lineNum) throws JottException {
        if (funcMap.containsKey(funcName)) {
            throw new JottException(false, "Function '" + funcName + "' is already defined", null, lineNum);
        }

        ArrayList<String> funcDef = new ArrayList<>();
        ArrayList<String> paramNames = new ArrayList<>();

        for (String p : params) {
            String varName = p.substring(0, p.indexOf(":"));
            String varType = p.substring(p.indexOf(":") + 1);
            addVariable(varName, varType, lineNum);
            funcDef.add(varType);
            paramNames.add(varType);
        }

        funcParamNames.put(funcName, paramNames);

        funcDef.add(returnType);
        funcMap.put(funcName, funcDef);
        Map<String, ArrayList<String>> emptyMap = new HashMap<>();
        varMap.put(funcName, emptyMap);
        scope = funcName;
    }

    public static void addFuncDef(String fName, FunctionDefNode node) {
        funcDefinitions.put(fName, node);
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
        updateValidVariable(varName, varValue);
    }

    public static void updateValidVariable(String varName, String varValue) {
        ArrayList<String> varProperties = varMap.get(scope).get(varName);
        varProperties.set(1, varValue);
        varMap.get(scope).put(varName, varProperties);
    }

    public static boolean funcExists(String fName) {
        if (fName.equals("print") || fName.equals("length") || fName.equals("concat")) {
            return true;
        }
        
        return funcMap.get(fName) != null;
    }

    public static boolean isValidFunctionCall(String fName, ArrayList<String> params) {
        if (fName.equals("print")) {
            if (params == null || params.size() != 1) {
                return false;
            }
            String param = params.get(0);
            return param.equals("String") || param.equals("Integer") || param.equals("Double");
        }
        if (fName.equals("length")) {
            if (params == null || params.size() != 1) {
                return false;
            }
            String param = params.get(0);
            return param.equals("String");
        }
        if (fName.equals("concat")) {
            if (params == null || params.size() != 2) {
                return false;
            }
            return params.get(0).equals("String") && params.get(1).equals("String");
        }

        ArrayList<String> func = funcMap.get(fName);
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < func.size()-1; i++) {
            temp.add(func.get(i));
        }

        if(params == null && temp.isEmpty()) {
            return true;
        }

        if (params == null) {
            return false;
        }

        if (params.size() != temp.size()) {
            return false;
        }
        for (int i = 0; i < params.size(); i++) {
            if (!params.get(i).equals(temp.get(i))) {
                return false;
            }
        }
        
        return true;
    }

    public static String getVarType(String s, String source, int lineNum) throws JottException {
        // Check if the id is in the Symbol Table
        if (!varMap.get(scope).containsKey(s)) {
            throw new JottException(false, "source", "Variable '" + s + "' not found in the current scope.", lineNum);
        }
        return varMap.get(scope).get(s).get(0);
    }

    public static String getVarVal(String s, String source, int lineNum) throws JottException {
        if (!varMap.get(scope).containsKey(s)) {
            throw new JottException(false, "source", "Variable '" + s + "' not found in the current scope.", lineNum);
        }
        String val = varMap.get(scope).get(s).get(1);
        if (val == null) {
            throw new JottException(false, source, "Variable '" + s + "' is not initialized.", lineNum);
        }
        return val;
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
        return funcMap.get("main") != null;
    }

    public static void updateScope(String s) {
        scope = s;
    }
}
