

// package com.codearena.backend.judge.driver;

// import java.util.stream.Collectors;

// public class JavaDriverGenerator {

//     public static String generate(Long problemId, String userCode) {

//         ProblemMeta meta = ProblemMetaRegistry.get(problemId);
//         if (meta == null) {
//             throw new RuntimeException("No problem meta found for problemId=" + problemId);
//         }

//         StringBuilder sb = new StringBuilder();

//         // Imports
//         sb.append("import java.util.*;\n");
//         sb.append("import java.io.*;\n\n");

//         // -------------------------
//         // Helper classes
//         // -------------------------

//         sb.append("class ListNode {\n");
//         sb.append("    int val;\n");
//         sb.append("    ListNode next;\n");
//         sb.append("    ListNode() {}\n");
//         sb.append("    ListNode(int v){val=v;}\n");
//         sb.append("    ListNode(int v,ListNode n){val=v;next=n;}\n");
//         sb.append("}\n\n");

//         sb.append("class TreeNode {\n");
//         sb.append("    int val;\n");
//         sb.append("    TreeNode left;\n");
//         sb.append("    TreeNode right;\n");
//         sb.append("    TreeNode() {}\n");
//         sb.append("    TreeNode(int v){val=v;}\n");
//         sb.append("    TreeNode(int v,TreeNode l,TreeNode r){val=v;left=l;right=r;}\n");
//         sb.append("}\n\n");

//         // -------------------------
//         // User Solution
//         // -------------------------
//         sb.append(userCode).append("\n\n");

//         // -------------------------
//         // Main Driver
//         // -------------------------
//         sb.append("public class Main {\n");

//         // read input
//         sb.append("static String readAll() throws Exception {\n");
//         sb.append("BufferedReader br=new BufferedReader(new InputStreamReader(System.in));\n");
//         sb.append("StringBuilder sb=new StringBuilder();\n");
//         sb.append("String l;\n");
//         sb.append("while((l=br.readLine())!=null) sb.append(l);\n");
//         sb.append("return sb.toString().trim();\n");
//         sb.append("}\n\n");

//         // get int
//         sb.append("static int getInt(String json,String key){\n");
//         sb.append("String k=\"\\\"\"+key+\"\\\":\";\n");
//         sb.append("int i=json.indexOf(k)+k.length();\n");
//         sb.append("return Integer.parseInt(json.substring(i).split(\"[,}]\" )[0]);\n");
//         sb.append("}\n\n");

//         // get string
//         sb.append("static String getString(String json,String key){\n");
//         sb.append("String k=\"\\\"\"+key+\"\\\":\";\n");
//         sb.append("int i=json.indexOf(k)+k.length()+1;\n");
//         sb.append("int e=json.indexOf('\"',i);\n");
//         sb.append("return json.substring(i,e);\n");
//         sb.append("}\n\n");

//         // int[]
//         sb.append("static int[] getIntArray(String json,String key){\n");
//         sb.append("String k=\"\\\"\"+key+\"\\\":[\";\n");
//         sb.append("int i=json.indexOf(k)+k.length();\n");
//         sb.append("int e=json.indexOf(\"]\",i);\n");
//         sb.append("String in=json.substring(i,e).trim();\n");
//         sb.append("if(in.isEmpty()) return new int[0];\n");
//         sb.append("String[] p=in.split(\",\");\n");
//         sb.append("int[] a=new int[p.length];\n");
//         sb.append("for(int j=0;j<p.length;j++) a[j]=Integer.parseInt(p[j].trim());\n");
//         sb.append("return a;\n");
//         sb.append("}\n\n");

//         // String[]
//         sb.append("static String[] getStringArray(String json,String key){\n");
//         sb.append("String k=\"\\\"\"+key+\"\\\":[\";\n");
//         sb.append("int i=json.indexOf(k)+k.length();\n");
//         sb.append("int e=json.indexOf(\"]\",i);\n");
//         sb.append("String in=json.substring(i,e).trim();\n");
//         sb.append("if(in.isEmpty()) return new String[0];\n");
//         sb.append("String[] p=in.split(\",\");\n");
//         sb.append("String[] a=new String[p.length];\n");
//         sb.append("for(int j=0;j<p.length;j++) a[j]=p[j].trim().replace(\"\\\"\",\"\");\n");
//         sb.append("return a;\n");
//         sb.append("}\n\n");

//         // build ListNode
//         sb.append("static ListNode buildListNode(int[] arr){\n");
//         sb.append("ListNode d=new ListNode(0);\n");
//         sb.append("ListNode c=d;\n");
//         sb.append("for(int v:arr){c.next=new ListNode(v);c=c.next;}\n");
//         sb.append("return d.next;\n");
//         sb.append("}\n\n");

//         // build TreeNode
//         sb.append("static TreeNode buildTree(Integer[] a){\n");
//         sb.append("if(a.length==0||a[0]==null) return null;\n");
//         sb.append("TreeNode r=new TreeNode(a[0]);\n");
//         sb.append("Queue<TreeNode> q=new LinkedList<>();\n");
//         sb.append("q.add(r);\n");
//         sb.append("int i=1;\n");
//         sb.append("while(!q.isEmpty()&&i<a.length){\n");
//         sb.append("TreeNode n=q.poll();\n");
//         sb.append("if(i<a.length&&a[i]!=null){n.left=new TreeNode(a[i]);q.add(n.left);}i++;\n");
//         sb.append("if(i<a.length&&a[i]!=null){n.right=new TreeNode(a[i]);q.add(n.right);}i++;\n");
//         sb.append("}\n");
//         sb.append("return r;\n");
//         sb.append("}\n\n");

//         // main
//         sb.append("public static void main(String[] args) throws Exception{\n");
//         sb.append("String json=readAll();\n");
//         sb.append("json=json.replaceAll(\"\\\\s+\",\"\");\n");
//         sb.append("Solution sol=new Solution();\n");

//         // -------------------------
//         // Param parsing
//         // -------------------------

//         for (ProblemMeta.Param p : meta.getParams()) {

//             switch (p.type) {

//                 case "int" ->
//                         sb.append("int ").append(p.name)
//                                 .append("=getInt(json,\"").append(p.name).append("\");\n");

//                 case "String" ->
//                         sb.append("String ").append(p.name)
//                                 .append("=getString(json,\"").append(p.name).append("\");\n");

//                 case "int[]" ->
//                         sb.append("int[] ").append(p.name)
//                                 .append("=getIntArray(json,\"").append(p.name).append("\");\n");

//                 case "String[]" ->
//                         sb.append("String[] ").append(p.name)
//                                 .append("=getStringArray(json,\"").append(p.name).append("\");\n");

//                 // case "ListNode" -> {
//                 //     sb.append("int[] ").append(p.name).append("_arr=getIntArray(json,\"")
//                 //             .append(p.name).append("\");\n");

//                 //     sb.append("ListNode ").append(p.name)
//                 //             .append("=buildListNode(").append(p.name).append("_arr);\n");
//                 // }
//                 case "ListNode" -> {

//                     sb.append("int[] ").append(p.name).append("_arr = getIntArray(json,\"")
//                     .append(p.name).append("\");\n");

//                     sb.append("ListNode ").append(p.name)
//                     .append(" = buildListNode(").append(p.name).append("_arr);\n");

//                     // create cycle if pos exists
//                     sb.append("int pos = -1;\n");
//                     sb.append("try { pos = getInt(json,\"pos\"); } catch(Exception e) {}\n");

//                     sb.append("if(pos >= 0){\n");
//                     sb.append("ListNode tail = ").append(p.name).append(";\n");
//                     sb.append("ListNode cycleNode = null;\n");
//                     sb.append("int idx = 0;\n");

//                     sb.append("while(tail.next != null){\n");
//                     sb.append("if(idx == pos) cycleNode = tail;\n");
//                     sb.append("tail = tail.next;\n");
//                     sb.append("idx++;\n");
//                     sb.append("}\n");

//                     sb.append("if(idx == pos) cycleNode = tail;\n");
//                     sb.append("tail.next = cycleNode;\n");
//                     sb.append("}\n");
//                 }

//                 case "TreeNode" -> {
//                     sb.append("Integer[] ").append(p.name).append("_a=null;\n");
//                     sb.append("try{\n");
//                     sb.append("int[] tmp=getIntArray(json,\"").append(p.name).append("\");\n");
//                     sb.append(p.name).append("_a=new Integer[tmp.length];\n");
//                     sb.append("for(int i=0;i<tmp.length;i++) ").append(p.name).append("_a[i]=tmp[i];\n");
//                     sb.append("}catch(Exception e){}\n");

//                     sb.append("TreeNode ").append(p.name)
//                             .append("=buildTree(").append(p.name).append("_a==null?new Integer[0]:")
//                             .append(p.name).append("_a);\n");
//                 }

//                 default ->
//                         throw new RuntimeException("Unsupported param type: " + p.type);
//             }
//         }

//         String callParams = meta.getParams()
//                 .stream()
//                 .map(x -> x.name)
//                 .collect(Collectors.joining(","));

//         sb.append(meta.getReturnType())
//                 .append(" result=sol.")
//                 .append(meta.getMethodName())
//                 .append("(").append(callParams).append(");\n");

//         sb.append("System.out.println(result);\n");

//         sb.append("}\n}\n");

//         return sb.toString();
//     }
// }













// package com.codearena.backend.judge.driver;

// import java.util.stream.Collectors;

// public class JavaDriverGenerator {

//     public static String generate(Long problemId, String userCode) {

//         ProblemMeta meta = ProblemMetaRegistry.get(problemId);
//         if (meta == null) {
//             throw new RuntimeException("No problem meta found for problemId=" + problemId);
//         }

//         StringBuilder sb = new StringBuilder();

//         sb.append("import java.util.*;\n");
//         sb.append("import java.io.*;\n\n");

//         // ---------- Helper Classes ----------

//         sb.append("class ListNode {\n");
//         sb.append("    int val;\n");
//         sb.append("    ListNode next;\n");
//         sb.append("    ListNode(){}\n");
//         sb.append("    ListNode(int v){val=v;}\n");
//         sb.append("    ListNode(int v,ListNode n){val=v;next=n;}\n");
//         sb.append("}\n\n");

//         sb.append("class TreeNode {\n");
//         sb.append("    int val;\n");
//         sb.append("    TreeNode left;\n");
//         sb.append("    TreeNode right;\n");
//         sb.append("    TreeNode(){}\n");
//         sb.append("    TreeNode(int v){val=v;}\n");
//         sb.append("    TreeNode(int v,TreeNode l,TreeNode r){val=v;left=l;right=r;}\n");
//         sb.append("}\n\n");

//         // ---------- User Solution ----------
//         sb.append(userCode).append("\n\n");

//         // ---------- Driver ----------
//         sb.append("public class Main {\n");

//         sb.append("static String readAll() throws Exception {\n");
//         sb.append("BufferedReader br=new BufferedReader(new InputStreamReader(System.in));\n");
//         sb.append("StringBuilder sb=new StringBuilder();\n");
//         sb.append("String l;\n");
//         sb.append("while((l=br.readLine())!=null) sb.append(l);\n");
//         sb.append("return sb.toString().trim();\n");
//         sb.append("}\n\n");

//         // int
//         sb.append("static int getInt(String json,String key){\n");
//         sb.append("String k=\"\\\"\"+key+\"\\\":\";\n");
//         sb.append("int i=json.indexOf(k)+k.length();\n");
//         sb.append("return Integer.parseInt(json.substring(i).split(\"[,}]\" )[0]);\n");
//         sb.append("}\n\n");

//         // string
//         sb.append("static String getString(String json,String key){\n");
//         sb.append("String k=\"\\\"\"+key+\"\\\":\";\n");
//         sb.append("int i=json.indexOf(k)+k.length()+1;\n");
//         sb.append("int e=json.indexOf('\"',i);\n");
//         sb.append("return json.substring(i,e);\n");
//         sb.append("}\n\n");

//         // int[]
//         sb.append("static int[] getIntArray(String json,String key){\n");
//         sb.append("String k=\"\\\"\"+key+\"\\\":[\";\n");
//         sb.append("int i=json.indexOf(k)+k.length();\n");
//         sb.append("int e=json.indexOf(\"]\",i);\n");
//         sb.append("String in=json.substring(i,e).trim();\n");
//         sb.append("if(in.isEmpty()) return new int[0];\n");
//         sb.append("String[] p=in.split(\",\");\n");
//         sb.append("int[] a=new int[p.length];\n");
//         sb.append("for(int j=0;j<p.length;j++) a[j]=Integer.parseInt(p[j].trim());\n");
//         sb.append("return a;\n");
//         sb.append("}\n\n");

//         // String[]
//         sb.append("static String[] getStringArray(String json,String key){\n");
//         sb.append("String k=\"\\\"\"+key+\"\\\":[\";\n");
//         sb.append("int i=json.indexOf(k)+k.length();\n");
//         sb.append("int e=json.indexOf(\"]\",i);\n");
//         sb.append("String in=json.substring(i,e).trim();\n");
//         sb.append("if(in.isEmpty()) return new String[0];\n");
//         sb.append("String[] p=in.split(\",\");\n");
//         sb.append("String[] a=new String[p.length];\n");
//         sb.append("for(int j=0;j<p.length;j++) a[j]=p[j].trim().replace(\"\\\"\",\"\");\n");
//         sb.append("return a;\n");
//         sb.append("}\n\n");

//         // build linked list
//         sb.append("static ListNode buildListNode(int[] arr){\n");
//         sb.append("ListNode d=new ListNode(0);\n");
//         sb.append("ListNode c=d;\n");
//         sb.append("for(int v:arr){c.next=new ListNode(v);c=c.next;}\n");
//         sb.append("return d.next;\n");
//         sb.append("}\n\n");

//         // build tree
//         sb.append("static TreeNode buildTree(Integer[] a){\n");
//         sb.append("if(a.length==0||a[0]==null) return null;\n");
//         sb.append("TreeNode r=new TreeNode(a[0]);\n");
//         sb.append("Queue<TreeNode> q=new LinkedList<>();\n");
//         sb.append("q.add(r);\n");
//         sb.append("int i=1;\n");
//         sb.append("while(!q.isEmpty()&&i<a.length){\n");
//         sb.append("TreeNode n=q.poll();\n");
//         sb.append("if(i<a.length&&a[i]!=null){n.left=new TreeNode(a[i]);q.add(n.left);}i++;\n");
//         sb.append("if(i<a.length&&a[i]!=null){n.right=new TreeNode(a[i]);q.add(n.right);}i++;\n");
//         sb.append("}\n");
//         sb.append("return r;\n");
//         sb.append("}\n\n");

//         // convert listnode to string
//         sb.append("static String listNodeToString(ListNode head){\n");
//         sb.append("List<Integer> list=new ArrayList<>();\n");
//         sb.append("while(head!=null){list.add(head.val);head=head.next;}\n");
//         sb.append("return list.toString();\n");
//         sb.append("}\n\n");

//         sb.append("public static void main(String[] args) throws Exception{\n");
//         sb.append("String json=readAll();\n");
//         sb.append("json=json.replaceAll(\"\\\\s+\",\"\");\n");
//         sb.append("Solution sol=new Solution();\n");

//         // ---------- Parameter Parsing ----------
//         for (ProblemMeta.Param p : meta.getParams()) {

//             switch (p.type) {

//                 case "int" ->
//                         sb.append("int ").append(p.name)
//                                 .append("=getInt(json,\"").append(p.name).append("\");\n");

//                 case "String" ->
//                         sb.append("String ").append(p.name)
//                                 .append("=getString(json,\"").append(p.name).append("\");\n");

//                 case "int[]" ->
//                         sb.append("int[] ").append(p.name)
//                                 .append("=getIntArray(json,\"").append(p.name).append("\");\n");

//                 case "String[]" ->
//                         sb.append("String[] ").append(p.name)
//                                 .append("=getStringArray(json,\"").append(p.name).append("\");\n");

//                 case "ListNode" -> {
//                     sb.append("int[] ").append(p.name).append("_arr=getIntArray(json,\"")
//                             .append(p.name).append("\");\n");
//                     sb.append("ListNode ").append(p.name)
//                             .append("=buildListNode(").append(p.name).append("_arr);\n");
//                 }

//                 case "TreeNode" -> {
//                     sb.append("int[] ").append(p.name).append("_arr=getIntArray(json,\"")
//                             .append(p.name).append("\");\n");
//                     sb.append("Integer[] ").append(p.name).append("_a=new Integer[")
//                             .append(p.name).append("_arr.length];\n");
//                     sb.append("for(int i=0;i<").append(p.name)
//                             .append("_arr.length;i++) ").append(p.name)
//                             .append("_a[i]=").append(p.name).append("_arr[i];\n");
//                     sb.append("TreeNode ").append(p.name)
//                             .append("=buildTree(").append(p.name).append("_a);\n");
//                 }

//                 default ->
//                         throw new RuntimeException("Unsupported param type: " + p.type);
//             }
//         }

//         String callParams = meta.getParams()
//                 .stream()
//                 .map(x -> x.name)
//                 .collect(Collectors.joining(","));

//         String returnType = meta.getReturnType();

//         if (returnType.equals("ListNode")) {

//             sb.append("ListNode result=sol.")
//                     .append(meta.getMethodName())
//                     .append("(").append(callParams).append(");\n");

//             sb.append("System.out.println(listNodeToString(result));\n");

//         } else {

//             sb.append(returnType).append(" result=sol.")
//                     .append(meta.getMethodName())
//                     .append("(").append(callParams).append(");\n");

//             sb.append("System.out.println(result);\n");
//         }

//         sb.append("}\n}\n");

//         return sb.toString();
//     }
// }














package com.codearena.backend.judge.driver;

import java.util.stream.Collectors;

public class JavaDriverGenerator {

    public static String generate(Long problemId, String userCode) {

        ProblemMeta meta = ProblemMetaRegistry.get(problemId);
        if (meta == null) {
            throw new RuntimeException("No problem meta found for problemId=" + problemId);
        }

        StringBuilder sb = new StringBuilder();

        sb.append("import java.util.*;\n");
        sb.append("import java.io.*;\n\n");

        // ---------- Helper Classes ----------

        sb.append("class ListNode {\n");
        sb.append("    int val;\n");
        sb.append("    ListNode next;\n");
        sb.append("    ListNode(){}\n");
        sb.append("    ListNode(int v){val=v;}\n");
        sb.append("    ListNode(int v,ListNode n){val=v;next=n;}\n");
        sb.append("}\n\n");

        sb.append("class TreeNode {\n");
        sb.append("    int val;\n");
        sb.append("    TreeNode left;\n");
        sb.append("    TreeNode right;\n");
        sb.append("    TreeNode(){}\n");
        sb.append("    TreeNode(int v){val=v;}\n");
        sb.append("    TreeNode(int v,TreeNode l,TreeNode r){val=v;left=l;right=r;}\n");
        sb.append("}\n\n");

        // ---------- User Solution ----------
        sb.append(userCode).append("\n\n");

        // ---------- Driver ----------
        sb.append("public class Main {\n");

        sb.append("static String readAll() throws Exception {\n");
        sb.append("BufferedReader br=new BufferedReader(new InputStreamReader(System.in));\n");
        sb.append("StringBuilder sb=new StringBuilder();\n");
        sb.append("String l;\n");
        sb.append("while((l=br.readLine())!=null) sb.append(l);\n");
        sb.append("return sb.toString().trim();\n");
        sb.append("}\n\n");

        // ---------- JSON Helpers ----------

        sb.append("static int getInt(String json,String key){\n");
        sb.append("String k=\"\\\"\"+key+\"\\\":\";\n");
        sb.append("int i=json.indexOf(k)+k.length();\n");
        sb.append("return Integer.parseInt(json.substring(i).split(\"[,}]\" )[0]);\n");
        sb.append("}\n\n");

        sb.append("static String getString(String json,String key){\n");
        sb.append("String k=\"\\\"\"+key+\"\\\":\";\n");
        sb.append("int i=json.indexOf(k)+k.length()+1;\n");
        sb.append("int e=json.indexOf('\"',i);\n");
        sb.append("return json.substring(i,e);\n");
        sb.append("}\n\n");

        sb.append("static int[] getIntArray(String json,String key){\n");
        sb.append("String k=\"\\\"\"+key+\"\\\":[\";\n");
        sb.append("int i=json.indexOf(k)+k.length();\n");
        sb.append("int e=json.indexOf(\"]\",i);\n");
        sb.append("String in=json.substring(i,e).trim();\n");
        sb.append("if(in.isEmpty()) return new int[0];\n");
        sb.append("String[] p=in.split(\",\");\n");
        sb.append("int[] a=new int[p.length];\n");
        sb.append("for(int j=0;j<p.length;j++) a[j]=Integer.parseInt(p[j].trim());\n");
        sb.append("return a;\n");
        sb.append("}\n\n");

        sb.append("static String[] getStringArray(String json,String key){\n");
        sb.append("String k=\"\\\"\"+key+\"\\\":[\";\n");
        sb.append("int i=json.indexOf(k)+k.length();\n");
        sb.append("int e=json.indexOf(\"]\",i);\n");
        sb.append("String in=json.substring(i,e).trim();\n");
        sb.append("if(in.isEmpty()) return new String[0];\n");
        sb.append("String[] p=in.split(\",\");\n");
        sb.append("String[] a=new String[p.length];\n");
        sb.append("for(int j=0;j<p.length;j++) a[j]=p[j].trim().replace(\"\\\"\",\"\");\n");
        sb.append("return a;\n");
        sb.append("}\n\n");

        // ---------- Linked List Helpers ----------

        sb.append("static ListNode buildListNode(int[] arr){\n");
        sb.append("ListNode d=new ListNode(0);\n");
        sb.append("ListNode c=d;\n");
        sb.append("for(int v:arr){c.next=new ListNode(v);c=c.next;}\n");
        sb.append("return d.next;\n");
        sb.append("}\n\n");

        sb.append("static String listNodeToString(ListNode head){\n");
        sb.append("List<Integer> list=new ArrayList<>();\n");
        sb.append("while(head!=null){list.add(head.val);head=head.next;}\n");
        sb.append("return list.toString();\n");
        sb.append("}\n\n");

        // ---------- Tree Helpers ----------

        sb.append("static TreeNode buildTree(Integer[] a){\n");
        sb.append("if(a.length==0||a[0]==null) return null;\n");
        sb.append("TreeNode r=new TreeNode(a[0]);\n");
        sb.append("Queue<TreeNode> q=new LinkedList<>();\n");
        sb.append("q.add(r);\n");
        sb.append("int i=1;\n");
        sb.append("while(!q.isEmpty()&&i<a.length){\n");
        sb.append("TreeNode n=q.poll();\n");
        sb.append("if(i<a.length&&a[i]!=null){n.left=new TreeNode(a[i]);q.add(n.left);}i++;\n");
        sb.append("if(i<a.length&&a[i]!=null){n.right=new TreeNode(a[i]);q.add(n.right);}i++;\n");
        sb.append("}\n");
        sb.append("return r;\n");
        sb.append("}\n\n");

        sb.append("static String treeToLevelOrder(TreeNode root){\n");
        sb.append("if(root==null) return \"[]\";\n");
        sb.append("List<String> res=new ArrayList<>();\n");
        sb.append("Queue<TreeNode> q=new LinkedList<>();\n");
        sb.append("q.add(root);\n");
        sb.append("while(!q.isEmpty()){\n");
        sb.append("TreeNode n=q.poll();\n");
        sb.append("if(n==null){res.add(\"null\");continue;}\n");
        sb.append("res.add(String.valueOf(n.val));\n");
        sb.append("q.add(n.left);\n");
        sb.append("q.add(n.right);\n");
        sb.append("}\n");
        sb.append("int i=res.size()-1;\n");
        sb.append("while(i>=0 && res.get(i).equals(\"null\")) i--;\n");
        sb.append("return \"[\"+String.join(\",\",res.subList(0,i+1))+\"]\";\n");
        sb.append("}\n\n");

        sb.append("public static void main(String[] args) throws Exception{\n");
        sb.append("String json=readAll();\n");
        sb.append("json=json.replaceAll(\"\\\\s+\",\"\");\n");
        sb.append("Solution sol=new Solution();\n");

        // ---------- Parameter Parsing ----------
        for (ProblemMeta.Param p : meta.getParams()) {

            switch (p.type) {

                case "int" ->
                        sb.append("int ").append(p.name)
                                .append("=getInt(json,\"").append(p.name).append("\");\n");

                case "String" ->
                        sb.append("String ").append(p.name)
                                .append("=getString(json,\"").append(p.name).append("\");\n");

                case "int[]" ->
                        sb.append("int[] ").append(p.name)
                                .append("=getIntArray(json,\"").append(p.name).append("\");\n");

                case "String[]" ->
                        sb.append("String[] ").append(p.name)
                                .append("=getStringArray(json,\"").append(p.name).append("\");\n");

                case "ListNode" -> {
                    sb.append("int[] ").append(p.name).append("_arr=getIntArray(json,\"")
                            .append(p.name).append("\");\n");
                    sb.append("ListNode ").append(p.name)
                            .append("=buildListNode(").append(p.name).append("_arr);\n");
                }

                case "TreeNode" -> {
                    sb.append("int[] ").append(p.name).append("_arr=getIntArray(json,\"")
                            .append(p.name).append("\");\n");
                    sb.append("Integer[] ").append(p.name).append("_a=new Integer[")
                            .append(p.name).append("_arr.length];\n");
                    sb.append("for(int i=0;i<").append(p.name)
                            .append("_arr.length;i++) ").append(p.name)
                            .append("_a[i]=").append(p.name).append("_arr[i];\n");
                    sb.append("TreeNode ").append(p.name)
                            .append("=buildTree(").append(p.name).append("_a);\n");
                }

                default ->
                        throw new RuntimeException("Unsupported param type: " + p.type);
            }
        }

        String callParams = meta.getParams()
                .stream()
                .map(x -> x.name)
                .collect(Collectors.joining(","));

        String returnType = meta.getReturnType();

        if (returnType.equals("ListNode")) {

            sb.append("ListNode result=sol.")
                    .append(meta.getMethodName())
                    .append("(").append(callParams).append(");\n");

            sb.append("System.out.println(listNodeToString(result));\n");

        }
        else if (returnType.equals("TreeNode")) {

            sb.append("TreeNode result=sol.")
                    .append(meta.getMethodName())
                    .append("(").append(callParams).append(");\n");

            sb.append("System.out.println(treeToLevelOrder(result));\n");

        }
        else {

            sb.append(returnType).append(" result=sol.")
                    .append(meta.getMethodName())
                    .append("(").append(callParams).append(");\n");

            sb.append("System.out.println(java.util.Arrays.toString(result));\n");
        }

        sb.append("}\n}\n");

        return sb.toString();
    }
}