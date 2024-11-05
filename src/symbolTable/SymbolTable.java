package symbolTable;

import java.util.*;

public class SymbolTable {
    Map<String, List<String>> funcMap;
    Map<String, Map<String, List<String>>> varMap;
    public SymbolTable() {
        this.funcMap = new HashMap<>();
        this.varMap = new HashMap<>();
    }

    public void addFunction(String funcName, List<String> params, String returnType) throws Exception {
        for (String name : funcMap.keySet()) {
            if (funcName.equals(name)) {
                throw new Exception("Function '" + funcName + "' is already defined");
            }
        }
        List<String> funcDef = new ArrayList<>(params);
        funcDef.add(returnType);
        funcMap.put(funcName, funcDef);
    }

    public void addVariable(String funcName, String varName, String varType, String varValue) throws Exception {
        Map<String, List<String>> varPropMap = varMap.getOrDefault(funcName, new HashMap<>());
        if (varPropMap.containsKey(varName)) {
            throw new Exception("Variable name '" + varName + "' is already defined in function '" + funcName + "'");
        }
        List<String> varProperties = Arrays.asList(varType, varValue);
        Map<String, List<String>> variable = new HashMap<>();
        variable.put(varName, varProperties);
        varMap.put(funcName, variable);
    }

    public void updateVariable(String funcName, String varName, String varValue) throws Exception {
        Map<String, List<String>> variableMap = varMap.getOrDefault(funcName, new HashMap<>());
        if (!(variableMap.containsKey(varName))) {
            throw new Exception("Variable name '" + varName + "' is not defined in function '" + funcName + "'");
        }
        List<String> varProperties = variableMap.get(varName);
        varProperties.set(1, varValue);
        variableMap.put(varName, varProperties);
        varMap.put(funcName, variableMap);
    }
}