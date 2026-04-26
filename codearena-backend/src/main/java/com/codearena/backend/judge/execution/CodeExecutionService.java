// package com.codearena.backend.judge.execution;

// import org.springframework.stereotype.Service;

// import java.io.*;
// import java.nio.file.*;
// import java.util.concurrent.TimeUnit;

// @Service
// public class CodeExecutionService {

//     public ExecutionResult executeJava(String userCode, String driverCode) {

//         Path tempDir = null;

//         try {
//             tempDir = Files.createTempDirectory("codearena-");

//             // 1) Write Solution.java
//             Path solutionFile = tempDir.resolve("Solution.java");
//             Files.writeString(solutionFile, userCode);

//             // 2) Write Main.java (driver)
//             Path mainFile = tempDir.resolve("Main.java");
//             Files.writeString(mainFile, driverCode);

//             // 3) Compile
//             ProcessBuilder compilePb = new ProcessBuilder(
//                     "javac",
//                     "Main.java",
//                     "Solution.java"
//             );
//             compilePb.directory(tempDir.toFile());
//             compilePb.redirectErrorStream(true);

//             Process compileProcess = compilePb.start();
//             String compileOutput = readAll(compileProcess.getInputStream());

//             boolean compiled = compileProcess.waitFor(5, TimeUnit.SECONDS);

//             if (!compiled) {
//                 compileProcess.destroyForcibly();
//                 return ExecutionResult.compileError("Compilation timed out");
//             }

//             if (compileProcess.exitValue() != 0) {
//                 return ExecutionResult.compileError(compileOutput);
//             }

//             // 4) Run
//             ProcessBuilder runPb = new ProcessBuilder(
//                     "java",
//                     "Main"
//             );
//             runPb.directory(tempDir.toFile());
//             runPb.redirectErrorStream(true);

//             Process runProcess = runPb.start();
//             String runOutput = readAll(runProcess.getInputStream());

//             boolean finished = runProcess.waitFor(3, TimeUnit.SECONDS);

//             if (!finished) {
//                 runProcess.destroyForcibly();
//                 return ExecutionResult.runtimeError("Time Limit Exceeded");
//             }

//             if (runProcess.exitValue() != 0) {
//                 return ExecutionResult.runtimeError(runOutput);
//             }

//             return ExecutionResult.success(runOutput);

//         } catch (Exception e) {
//             return ExecutionResult.runtimeError("Server error: " + e.getMessage());
//         } finally {
//             // Cleanup
//             if (tempDir != null) {
//                 deleteFolder(tempDir.toFile());
//             }
//         }
//     }

//     private static String readAll(InputStream inputStream) throws IOException {
//         return new String(inputStream.readAllBytes());
//     }

//     private static void deleteFolder(File folder) {
//         if (!folder.exists()) return;
//         File[] files = folder.listFiles();
//         if (files != null) {
//             for (File f : files) {
//                 if (f.isDirectory()) deleteFolder(f);
//                 else f.delete();
//             }
//         }
//         folder.delete();
//     }
// }








package com.codearena.backend.judge.execution;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

@Service
public class CodeExecutionService {

    // Holds compiled folder
    public static class JavaSession {
        private final Path tempDir;

        public JavaSession(Path tempDir) {
            this.tempDir = tempDir;
        }

        public Path getTempDir() {
            return tempDir;
        }
    }

    // ✅ Compile ONLY Main.java (fullCode)
    public JavaSession compileJavaOnce(String fullCode) {

        try {
            Path tempDir = Files.createTempDirectory("codearena-");

            // Write ONLY Main.java
            Files.writeString(tempDir.resolve("Main.java"), fullCode);

            ProcessBuilder compilePb = new ProcessBuilder(
                    "javac",
                    "Main.java"
            );

            compilePb.directory(tempDir.toFile());
            compilePb.redirectErrorStream(true);

            Process compileProcess = compilePb.start();
            String compileOutput = readAll(compileProcess.getInputStream());

            boolean compiled = compileProcess.waitFor(8, TimeUnit.SECONDS);

            if (!compiled) {
                compileProcess.destroyForcibly();
                deleteFolder(tempDir.toFile());
                throw new RuntimeException("Compilation timed out");
            }

            if (compileProcess.exitValue() != 0) {
                deleteFolder(tempDir.toFile());
                throw new RuntimeException(compileOutput);
            }

            return new JavaSession(tempDir);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ✅ Run multiple times (per testcase)
    public ExecutionResult runJava(JavaSession session, String stdinInput) {

        try {
            ProcessBuilder runPb = new ProcessBuilder(
                    "java",
                    "Main"
            );

            runPb.directory(session.getTempDir().toFile());
            runPb.redirectErrorStream(true);

            Process runProcess = runPb.start();

            // Send testcase JSON to stdin
            try (BufferedWriter writer =
                         new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                writer.write(stdinInput);
                writer.flush();
            }

            String runOutput = readAll(runProcess.getInputStream());

            boolean finished = runProcess.waitFor(3, TimeUnit.SECONDS);

            if (!finished) {
                runProcess.destroyForcibly();
                return ExecutionResult.runtimeError("Time Limit Exceeded");
            }

            if (runProcess.exitValue() != 0) {
                return ExecutionResult.runtimeError(runOutput);
            }

            return ExecutionResult.success(runOutput);

        } catch (Exception e) {
            return ExecutionResult.runtimeError("Runtime error: " + e.getMessage());
        }
    }

    // Cleanup temp directory
    public void cleanup(JavaSession session) {
        if (session == null) return;
        deleteFolder(session.getTempDir().toFile());
    }

    private static String readAll(InputStream inputStream) throws IOException {
        return new String(inputStream.readAllBytes());
    }

    private static void deleteFolder(File folder) {
        if (!folder.exists()) return;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) deleteFolder(f);
                else f.delete();
            }
        }
        folder.delete();
    }
}