package testers;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import provided.*;


public class Phase3Tester {
    ArrayList<TestCase> testCases;

    private static class TestCase{
        String testName;
        String fileName;
        boolean error;

        public TestCase(String testName, String fileName, boolean error) {
            this.testName = testName;
            this.fileName = fileName;
            this.error = error;
        }
    }

    private boolean tokensEqualNoFileData(Token t1, Token t2){
        return t1.getTokenType() == t2.getTokenType() &&
                t1.getToken().equals(t2.getToken());
    }

    private void createTestCases(){
        this.testCases = new ArrayList<>();
        testCases.add(new TestCase("elseIfElseReturnNoIf", "elseIfElseReturnNoIf.jott", true));
        testCases.add(new TestCase("emptyIf", "emptyIf.jott", true));
        testCases.add(new TestCase("emptyWhile", "emptyWhile.jott", true));
        testCases.add(new TestCase("funcCallParamInvalid", "funcCallParamInvalid.jott", true ));
        testCases.add(new TestCase("funcDefinedAfterCall", "funcDefinedAfterCall.jott", true ));
        testCases.add(new TestCase("funcNotDefined", "funcNotDefined.jott", true ));
        testCases.add(new TestCase("funReturnInExpr", "funcReturnInExpr.jott", true ));
        testCases.add(new TestCase("funcWrongParamType", "funcWrongParamType.jott", true ));
        testCases.add(new TestCase("helloWorld", "helloWorld.jott", false ));
        testCases.add(new TestCase("ifElseifReturnNoElse", "ifElseifReturnNoElse.jott", true ));
        testCases.add(new TestCase("ifElseReturnNoElseif", "ifElseReturnNoElseif.jott", true ));
        testCases.add(new TestCase("ifStmtReturns", "ifStmtReturns.jott", false ));
        testCases.add(new TestCase("invalidAddition", "invalidAddition.jott", true ));
        testCases.add(new TestCase("invalidAssignedType", "invalidAssignedType.jott", true ));
        testCases.add(new TestCase("invalidChainingMath", "invalidChainingMath.jott", true ));
        testCases.add(new TestCase("invalidConcat", "invalidConcat.jott", true ));
        testCases.add(new TestCase("invalidCondition", "invalidCondition.jott", true ));
        testCases.add(new TestCase("invalidDeclaration", "invalidDeclaration.jott", true ));
        testCases.add(new TestCase("invalidDivByZero", "invalidDivByZero.jott", true ));
        testCases.add(new TestCase("invalidLength", "invalidLength.jott", true ));
        testCases.add(new TestCase("invalidLengthParams", "invalidLengthParams.jott", true ));
        testCases.add(new TestCase("invalidPrint", "invalidPrint.jott", true ));
        testCases.add(new TestCase("largerValid", "largerValid.jott", false ));
        testCases.add(new TestCase("mainReturnNotInt", "mainReturnNotInt.jott", true ));
        testCases.add(new TestCase("mismatchedRelop", "mismatchedRelop.jott", true ));
        testCases.add(new TestCase("mismatchedReturn", "mismatchedReturn.jott", true ));
        testCases.add(new TestCase("missingFuncParams", "missingFuncParams.jott", true ));
        testCases.add(new TestCase("missingMain", "missingMain.jott", true ));
        testCases.add(new TestCase("missingReturn", "missingReturn.jott", true ));
        testCases.add(new TestCase("negativeNumberMath", "negativeNumberMath.jott", false ));
        testCases.add(new TestCase("nestedIf", "nestedIf.jott", false ));
        testCases.add(new TestCase("noReturnIf", "noReturnIf.jott", true ));
        testCases.add(new TestCase("noReturnWhile", "noReturnWhile.jott", true ));
        testCases.add(new TestCase("providedExample1", "providedExample1.jott", false ));
        testCases.add(new TestCase("returnId", "returnId.jott", true ));
        testCases.add(new TestCase("sameName", "sameName.jott", false ));
        testCases.add(new TestCase("undeclaredVariableUsed", "undeclaredVariableUsed.jott", true ));
        testCases.add(new TestCase("uninitializedFuncUsed", "uninitializedFuncUsed.jott", true ));
        //testCases.add(new TestCase("uninitializedVariableUsed", "uninitializedVariableUsed.jott", true ));    TODO
        testCases.add(new TestCase("validConcat", "validConcat.jott", false ));
        testCases.add(new TestCase("validLength", "validLength.jott", false ));
        testCases.add(new TestCase("validLoop", "validLoop.jott", false ));
        testCases.add(new TestCase("validPrint", "validPrint.jott", false ));
        testCases.add(new TestCase("validReturnIf", "validReturnIf.jott", false ));
        testCases.add(new TestCase("validVoid", "validVoid.jott", false ));
        testCases.add(new TestCase("voidReturn", "voidReturn.jott", true ));
        testCases.add(new TestCase("whileKeyword", "whileKeyword.jott", true ));
    }

    private boolean phase3Test(TestCase test, String orginalJottCode){
        try {
            ArrayList<Token> tokens = JottTokenizer.tokenize("phase3testcases/" + test.fileName);

            if (tokens == null) {
                System.err.println("\tFailed Test: " + test.testName);
                System.err.println("\t\tExpected a list of tokens, but got null");
                System.err.println("\t\tPlease verify your tokenizer is working properly");
                return false;
            }
            System.out.println(tokenListString(tokens));
            ArrayList<Token> cpyTokens = new ArrayList<>(tokens);
            JottTree root = JottParser.parse(tokens);

            if (!test.error && root == null) {
                System.err.println("\tFailed Test: " + test.testName);
                System.err.println("\t\tExpected a JottTree and got null");
                return false;
            } else if (test.error && root == null) {
                return true;
            } else if (test.error) {
                System.err.println("\tFailed Test: " + test.testName);
                System.err.println("\t\tExpected a null and got JottTree");
                return false;
            }

            System.out.println("Orginal Jott Code:\n");
            System.out.println(orginalJottCode);
            System.out.println();

            String jottCode = root.convertToJott();
            System.out.println("Resulting Jott Code:\n");
            System.out.println(jottCode);

            try {
                FileWriter writer = new FileWriter("phase3testcases/phase3testtemp.jott");
                if (jottCode == null) {
                    System.err.println("\tFailed Test: " + test.testName);
                    System.err.println("Expected a program string; got null");
                    return false;
                }
                writer.write(jottCode);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<Token> newTokens = JottTokenizer.tokenize("phase3testcases/phase3testtemp.jott");

            if (newTokens == null) {
                System.err.println("\tFailed Test: " + test.testName);
                System.err.println("Tokenization of files dot not match.");
                System.err.println("Similar files should have same tokenization.");
                System.err.println("Expected: " + tokenListString(tokens));
                System.err.println("Got: null");
                return false;
            }

            if (newTokens.size() != cpyTokens.size()) {
                System.err.println("\tFailed Test: " + test.testName);
                System.err.println("Tokenization of files dot not match.");
                System.err.println("Similar files should have same tokenization.");
                System.err.println("Expected: " + tokenListString(cpyTokens));
                System.err.println("Got:    : " + tokenListString(newTokens));
                return false;
            }

            for (int i = 0; i < newTokens.size(); i++) {
                Token n = newTokens.get(i);
                Token t = cpyTokens.get(i);

                if (!tokensEqualNoFileData(n, t)) {
                    System.err.println("\tFailed Test: " + test.testName);
                    System.err.println("Token mismatch: Tokens do not match.");
                    System.err.println("Similar files should have same tokenization.");
                    System.err.println("Expected: " + tokenListString(cpyTokens));
                    System.err.println("Got     : " + tokenListString(newTokens));
                    return false;
                }
            }

        }catch (Exception e){
            System.err.println("\tFailed Test: " + test.testName);
            System.err.println("Unknown Exception occured.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String tokenListString(ArrayList<Token> tokens){
        StringBuilder sb = new StringBuilder();
        for (Token t: tokens) {
            sb.append(t.getToken());
            sb.append(":");
            sb.append(t.getTokenType().toString());
            sb.append(" ");
        }
        return sb.toString();
    }

    private boolean runTest(TestCase test){
        System.out.println("Running Test: " + test.testName);
        String orginalJottCode;
        try {
            orginalJottCode = new String(
                    Files.readAllBytes(Paths.get("phase3testcases/" + test.fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return phase3Test(test, orginalJottCode);

    }

    public static void main(String[] args) {
        System.out.println("NOTE: System.err may print at the end. This is fine.");
        Phase3Tester tester = new Phase3Tester();

        int numTests = 0;
        int passedTests = 0;
        tester.createTestCases();
        for(TestCase test: tester.testCases){
            numTests++;
            if(tester.runTest(test)){
                passedTests++;
                System.out.println("\tPassed\n");
            }
            else{
                System.out.println("\tFailed\n");
            }
        }

        System.out.printf("Passed: %d/%d%n", passedTests, numTests);
    }
}
